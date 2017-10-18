package com.mini.mvc.core;

import com.alibaba.fastjson.JSON;
import com.mini.mvc.domain.ControllerBody;
import com.mini.mvc.util.PathUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhanghaojie on 2017/10/29.
 * 视图处理
 */
public class ViewResolver {

    public static void view(HttpServletResponse response, Object result, ControllerBody controller) {

        try {
            switch (controller.getMediaType()) {
                case JSON_UTF_8: {
                    String resultStr = JSON.toJSONString(result);
                    response.getWriter().print(resultStr);
                    break;
                }
                case TEXT_PLAIN_UTF_8:
                    response.getWriter().print(result);
                    break;
                default:
                    response.getWriter().print(result);
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
