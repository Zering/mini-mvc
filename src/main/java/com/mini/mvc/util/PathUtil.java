package com.mini.mvc.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhanghaojie on 2017/10/29.
 * 路径整理工具类
 */
public class PathUtil {

    private static final Logger log = LoggerFactory.getLogger(PathUtil.class);

    public static final String DEFAULT_REQUEST_PATH = "/";

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

    public static void redirectRequest(String path, HttpServletRequest req, HttpServletResponse resp) {
        try {
            // resp.sendRedirect(req.getContextPath() + path);
            req.getRequestDispatcher(req.getContextPath() + path).forward(req,resp);
            log.info(req.getContextPath() + path);
        } catch (Exception e) {
            log.error("重定向 {} 失败", path);
            throw new RuntimeException(e);
        }
    }

}
