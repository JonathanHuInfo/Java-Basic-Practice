package org.jonathan.web.mvc.header;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @description: 处理方法的信息类
 * @author: Jonathan.Hu
 * @since: 1.0
 * @create: 2021-03-07 10:23
 **/
public class HandlerMethodInfo {
    /**
     * 请求路径
     */
    private final String requestPath;
    /**
     * 处理方法
     */
    private final Method handlerMethod;
    /**
     * 支持Http请求的方法路径
     */
    private final Set<String> supportedHttpMethods;

    public HandlerMethodInfo(String requestPath, Method handlerMethod, Set<String> supportedHttpMethods) {
        this.requestPath = requestPath;
        this.handlerMethod = handlerMethod;
        this.supportedHttpMethods = supportedHttpMethods;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public Method getHandlerMethod() {
        return handlerMethod;
    }

    public Set<String> getSupportedHttpMethods() {
        return supportedHttpMethods;
    }
}
