package org.jonathan.user.context;


import javax.naming.*;
import javax.servlet.ServletContext;
import java.util.logging.Logger;

/**
 * 组件上下文（Web 应用全局使用）
 */
public class ComponentContext {

    public static final String CONTEXT_NAME = ComponentContext.class.getName();

    private static final String COMPONENT_ENV_CONTEXT_NAME = "java:comp/env";

    private static final Logger logger = Logger.getLogger(CONTEXT_NAME);

    private static ServletContext servletContext;

    private Context envContext; // Component Env Context


    public void init(ServletContext servletContext) throws RuntimeException {
        try {
            this.envContext = (Context) new InitialContext().lookup(COMPONENT_ENV_CONTEXT_NAME);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        servletContext.setAttribute(CONTEXT_NAME, this);
        ComponentContext.servletContext = servletContext;
    }

    /**
     * 通过名称进行依赖查找
     *
     * @param name
     * @param <C>
     * @return
     */
    public <C> C getComponent(String name) {
        C component = null;
        try {
            component = (C) envContext.lookup(name);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return component;
    }

    /**
     * 关闭方法
     *
     * @throws RuntimeException
     */
    public void destroy() throws RuntimeException {
        close(this.envContext);
    }

    private static void close(Context context) {
        if (context != null) {
            try {
                context.close();
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获取 ComponentContext
     * @return
     */
    public static ComponentContext getInstance() {
        return (ComponentContext) servletContext.getAttribute(CONTEXT_NAME);
    }
}
