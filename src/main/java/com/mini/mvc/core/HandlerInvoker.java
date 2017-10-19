package com.mini.mvc.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.mini.mvc.domain.ControllerBody;
import com.mini.mvc.util.ParamsUtil;
import com.mini.mvc.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by zhanghaojie on 2017/10/29.
 * 反射调用对应方法
 */
public class HandlerInvoker {

    private static final Logger log = LoggerFactory.getLogger(HandlerInvoker.class);

    public static void invoke(HttpServletRequest request, HttpServletResponse response, ControllerBody controller) {
        Method method = controller.getMethod();
        List<Object> paramList = Lists.newArrayList();
        ParamsUtil.parseRequestParams(paramList, request, method.getParameterTypes());
        Object result = ReflectUtil.invokeMethod(controller.getControllerClass(), method, paramList);
        log.debug("返回结果： {}", JSON.toJSONString(result));
        ViewResolver.view(response, result, controller);
    }
}
