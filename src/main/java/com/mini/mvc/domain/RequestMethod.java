package com.mini.mvc.domain;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by zhanghaojie on 2017/10/27.
 * 请求方法
 */
public enum RequestMethod {

    GET,
    POST,
    PUT,
    DELETE,;

    private static Map<String, RequestMethod> methodMap = Maps.newHashMap();

    static {
        for (RequestMethod method : values()) {
            methodMap.put(method.name(), method);
        }
    }

    public static RequestMethod getRequestMethod(String name) {
        return methodMap.get(name);
    }

}
