package com.mini.mvc.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by zhanghaojie on 2017/10/29.
 * 反射工具
 */
public class ReflectUtil {

    public static Object invokeMethod(Class<?> clazz, Method method, List<Object> paramList) {
        try {
            return method.invoke(clazz.newInstance(), paramList.toArray());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }
}
