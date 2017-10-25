package com.mini.mvc.util;

import com.alibaba.fastjson.JSON;
import com.mini.mvc.domain.MediaType;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhanghaojie on 2017/10/29.
 * 参数处理工具
 */
public class ParamsUtil {

    private static final Logger log = LoggerFactory.getLogger(ParamsUtil.class);

    public static void parseRequestParams(List<Object> paramList, HttpServletRequest request, Class<?>[] paramTypes) {
        Enumeration<String> paramNames = request.getParameterNames();
        int i = 0;
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues != null) {
                // TODO 暂定参数名只有一个
                if (1 == paramValues.length) {
                    // 获取请求参数
                    String paramValue = paramValues[0];
                    Class<?> paramType = paramTypes[i];
                    // 支持四种类型：int/Integer、long/Long、double/Double、String
                    if (paramType.equals(int.class) || paramType.equals(Integer.class)) {
                        paramList.add(Integer.parseInt(paramValue));
                    } else if (paramType.equals(long.class) || paramType.equals(Long.class)) {
                        paramList.add(Long.parseLong(paramValue));
                    } else if (paramType.equals(double.class) || paramType.equals(Double.class)) {
                        paramList.add(Double.parseDouble(paramValue));
                    } else if (paramType.equals(String.class)) {
                        paramList.add(paramValue);
                    }
                }
            }
            i++;
        }
    }

    public static Object parsePostParam(HttpServletRequest request, Class<?> clazz) {
        InputStream is;
        Object result = null;
        try {
            is = request.getInputStream();
            log.debug("request.getContentType() : {}", request.getContentType());
            if (StringUtils.equalsIgnoreCase(request.getContentType(), MediaType.JSON_UTF_8.getMediaType())
                    || StringUtils.equalsIgnoreCase(request.getContentType(), MediaType.JSON.getMediaType())) {
                result = JSON.parseObject(is, clazz);
            } else {
                result = IOUtils.toString(is);
            }
        } catch (Exception e) {
            log.error("Post参数错误 {} ", clazz, e);
        }

        return result;
    }

}
