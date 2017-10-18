package com.mini.mvc.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by zhanghaojie on 2017/10/29.
 * 参数处理工具
 */
public class ParamsUtil {

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

}
