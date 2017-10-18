package com.mini.mvc.core;

import com.google.common.collect.Maps;
import com.mini.mvc.Config;
import com.mini.mvc.annotation.Controller;
import com.mini.mvc.annotation.RequestMapping;
import com.mini.mvc.domain.ControllerBody;
import com.mini.mvc.domain.MediaType;
import com.mini.mvc.domain.RequestBody;
import com.mini.mvc.domain.RequestMethod;
import com.mini.mvc.util.ClassUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghaojie on 2017/10/18.
 * controller扫描工具
 */
public class ControllerCollection {

    /**
     * 扫描包信息
     */
    private static final String scanpackage = Config.getScanPackage();

    /**
     * 请求,controller映射
     */
    private static final Map<RequestBody, ControllerBody> MAPPING = Maps.newLinkedHashMap();

    /**
     * 初始化
     * 1. 加载class信息
     * 2. 加载映射信息
     */
    public static void init() {
        List<Class<?>> classLists = ClassUtil.getClassLists(scanpackage, Controller.class);
        if (!classLists.isEmpty()) {
            for (Class<?> clazz : classLists) {
                Method[] methods = clazz.getMethods();
                if (methods != null && methods.length > 0) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                            RequestMethod requestMethod = annotation.method();
                            String requestPath = annotation.value();
                            MediaType mediaType = annotation.produces();
                            MAPPING.put(new RequestBody(requestMethod, requestPath), new ControllerBody(clazz, method, mediaType));
                        }
                    }
                }
            }
        }
    }

    public static ControllerBody getControllerBody(RequestBody requestBody) {
        return MAPPING.get(requestBody);
    }

}
