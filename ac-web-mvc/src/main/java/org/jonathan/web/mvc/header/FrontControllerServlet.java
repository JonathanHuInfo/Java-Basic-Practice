package org.jonathan.web.mvc.header;

import org.apache.commons.lang.StringUtils;
import org.jonathan.context.ClassicComponentContext;
import org.jonathan.context.ComponentContext;
import org.jonathan.web.mvc.controller.Controller;
import org.jonathan.web.mvc.controller.PageController;
import org.jonathan.web.mvc.controller.RestController;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import static java.util.Arrays.asList;

/**
 * @description: 前端控制器Servlet
 * @author: Jonathan.Hu
 * @since: 1.0
 * @create: 2021-03-07 10:06
 **/
public class FrontControllerServlet extends HttpServlet {


    /**
     * 请求路径和 Controller 的映射关系缓存
     */
    private Map<String, Controller> controllersMapping = new HashMap<>();

    /**
     * 请求路径和 {@link HandlerMethodInfo} 映射关系缓存
     */
    private Map<String, HandlerMethodInfo> handleMethodInfoMapping = new HashMap<>();

    private ComponentContext context = null;
    /**
     * object 方法名称集合
     */
    private static Set<String> objectMethod = new HashSet<>();

    static {
        objectMethod.add("wait");
        objectMethod.add("equals");
        objectMethod.add("toString");
        objectMethod.add("hashCode");
        objectMethod.add("getClass");
        objectMethod.add("notify");
        objectMethod.add("notifyAll");
    }

    /**
     * 初始化
     *
     * @param servletConfig
     */
    public void init(ServletConfig servletConfig) {
        ComponentContext context = (ComponentContext) servletConfig.getServletContext().getAttribute(ClassicComponentContext.CONTEXT_NAME);
        this.context = context;
        initHandleMethods();
    }

    /**
     * 读取所有的RestController 上标记的@Path注解 利用 ServiceLoader 技术（Java SPI）
     * <p>
     * 缓存 路径与处理方法
     * 缓存 路径与controller
     */
    private void initHandleMethods() {
        //SPI读取的实例
        for (Controller controller : ServiceLoader.load(Controller.class)
        ) {
            //获取实例的Class
            Class<?> controllerClass = controller.getClass();

            //获取注解中的value 值
            Path pathFromClass = controllerClass.getAnnotation(Path.class);
            String classRequestPath = pathFromClass.value();
            //获取 实例class的方法
            Method[] publicMathods = controllerClass.getMethods();
            //处理方法支持的Http方法集合
            for (Method method : publicMathods) {

                String realRequestPath = "/".equals(classRequestPath) ? "" : classRequestPath;
                Set<String> supportedHttpMethods = findSupportedHttpMethods(method);
                Path pathFromMethod = method.getAnnotation(Path.class);
                if (pathFromMethod == null) {
                    continue;
                }
                realRequestPath += pathFromMethod.value();
                handleMethodInfoMapping.put(realRequestPath,
                        new HandlerMethodInfo(realRequestPath, method, supportedHttpMethods));
                controllersMapping.put(realRequestPath, controller);
            }
            injectControllerField(controller);
        }
    }

    /**
     * 注入Cotroller 字段
     *
     * @param controller
     */
    private void injectControllerField(Controller controller) {
        Arrays.stream(controller.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Resource.class))
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        field.set(controller, this.context.getComponent(field.getAnnotation(Resource.class).name()));
                    } catch (IllegalAccessException e) {
                        new RuntimeException(e);
                    }
                });
    }

    /**
     * 获取处理方法中标注的 注解集合(Http方法集合)
     *
     * @param method 处理方法
     * @return
     */
    private Set<String> findSupportedHttpMethods(Method method) {
        Set<String> supportedHttpMethods = new LinkedHashSet<>();
        for (Annotation annotationFormMethod : method.getAnnotations()) {
            HttpMethod httpMethod = annotationFormMethod.annotationType().getAnnotation(HttpMethod.class);
            if (httpMethod != null) {
                supportedHttpMethods.add(httpMethod.value());
            }
        }
        if (supportedHttpMethods.isEmpty()) {
            supportedHttpMethods.addAll(asList(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT,
                    HttpMethod.DELETE, HttpMethod.HEAD, HttpMethod.OPTIONS));
        }
        return supportedHttpMethods;
    }

    /**
     * Sun Certified Web Component Developer
     * SCWCD
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //示例路径 http://127.0.0.1:8080/user-web/hello/world

        //端口后面完整路径
        //得到的 /user-web/hello/world
        String requestURI = req.getRequestURI();

        //启动时候的根路径
        // /user-web
        String servletContextPath = req.getContextPath();

        String prefixPath = servletContextPath;
        //子路径 /hello/world
        String requestMappingPath = StringUtils.substringAfter(requestURI,
                StringUtils.replace(prefixPath, "//", "/"));

        //获取路径对应的 controller
        Controller controller = controllersMapping.get(requestMappingPath);

        if (controller != null) {
            //获取路径对应的 处理方法信息
            HandlerMethodInfo handlerMethodInfo = handleMethodInfoMapping.get(requestMappingPath);
            try {
                if (handlerMethodInfo != null) {
                    String httpMethod = req.getMethod();
                    //不是Http支持的 请求
                    if (!handlerMethodInfo.getSupportedHttpMethods().contains(httpMethod)) {
                        resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                        return;
                    }
                    Method handlerMethod = handlerMethodInfo.getHandlerMethod();
                    String viewPath = (String) handlerMethod.invoke(controller, req, resp);
                    if (StringUtils.isBlank(viewPath)){
                        return;
                    }
                    ServletContext servletContext = req.getServletContext();
                    if (!viewPath.startsWith("/")) {
                        viewPath = "/" + viewPath;
                    }
                    RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(viewPath);
                    requestDispatcher.forward(req, resp);
                    //页面渲染请求
//                    if (controller instanceof PageController) {
//                        PageController pageController = PageController.class.cast(controller);
//                        //执行到业务代码上 得到返回到页面的路径
//                        String viewPath = pageController.execute(req, resp);
//
//                        ServletContext servletContext = req.getServletContext();
//                        if (!viewPath.startsWith("/")) {
//                            viewPath = "/" + viewPath;
//                        }
//                        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(viewPath);
//                        requestDispatcher.forward(req, resp);
//                        return;
//                    } else if (controller instanceof RestController) {
//                        //TODO RestController实现
//                    }
                }
            } catch (Throwable throwable) {
                if (throwable.getCause() instanceof IOException) {
                    throw (IOException) throwable.getCause();
                } else {
                    throw new ServletException(throwable.getCause());
                }
            }
        }
    }

    private boolean isObjectMethod(Method method) {
        return objectMethod.contains(method.getName());
    }
}
