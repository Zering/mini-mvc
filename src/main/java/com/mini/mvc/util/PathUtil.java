package com.mini.mvc.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhanghaojie on 2017/10/29.
 * 路径整理工具类
 */
public class PathUtil {

    public static String getRequestPath(HttpServletRequest req) {
        // 项目路径
        String servletPath = req.getServletPath();
        // 请求路径
        String pathInfo = req.getPathInfo();

        String path = "";

        if (StringUtils.isNotBlank(servletPath)) {
            path += servletPath;
        }

        if (StringUtils.isNotBlank(pathInfo)) {
            path += pathInfo;
        }

        return path;
    }

}
