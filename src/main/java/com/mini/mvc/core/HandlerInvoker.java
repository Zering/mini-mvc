package com.mini.mvc.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.mini.mvc.annotation.PostParam;
import com.mini.mvc.domain.ControllerBody;
import com.mini.mvc.domain.RequestMethod;
import com.mini.mvc.util.ParamsUtil;
import com.mini.mvc.util.ReflectUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
        // GET 请求
        switch (RequestMethod.getRequestMethod(StringUtils.upperCase(request.getMethod()))) {
            // TODO 只支持一个@PostParam参数
            case POST: {
                List<Class<?>> paramTypes = Lists.newArrayList();
                Class<?> postParamType = null;
                for (Parameter parameter : method.getParameters()) {
                    if (parameter.isAnnotationPresent(PostParam.class)) {
                        postParamType = parameter.getType();
                    } else {
                        paramTypes.add(parameter.getType());
                    }
                }
                ParamsUtil.parseRequestParams(paramList, request, paramTypes.toArray(new Class<?>[0]));
                if (postParamType != null) {
                    Object postParam = ParamsUtil.parsePostParam(request, postParamType);
                    paramList.add(0, postParam);
                }

            }
            case PUT:
            case DELETE:
            case GET:
                ParamsUtil.parseRequestParams(paramList, request, method.getParameterTypes());
            default: // 默认当作get请求处理
                break;
        }

        Object result = ReflectUtil.invokeMethod(controller.getControllerClass(), method, paramList);
        log.debug("返回结果： {}", JSON.toJSONString(result));
        ViewResolver.view(response, result, controller);
    }
}
