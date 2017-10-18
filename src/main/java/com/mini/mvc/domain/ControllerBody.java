package com.mini.mvc.domain;

import java.lang.reflect.Method;

/**
 * Created by zhanghaojie on 2017/10/28.
 * controller信息
 */
public class ControllerBody {

    private Class<?> controllerClass;
    private Method method;
    private MediaType mediaType;

    public ControllerBody(Class<?> controllerClass, Method method, MediaType mediaType) {
        this.controllerClass = controllerClass;
        this.method = method;
        this.mediaType = mediaType;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getMethod() {
        return method;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
}
