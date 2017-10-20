package com.mini.mvc.core;

import com.mini.mvc.Config;
import com.mini.mvc.annotation.Controller;
import com.mini.mvc.domain.ControllerBody;
import com.mini.mvc.domain.RequestBody;
import com.mini.mvc.domain.RequestMethod;
import com.mini.mvc.util.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhanghaojie on 2017/10/18.
 *
 * @author zhanghaojie
 *         url消息分发器，将不同的url消息分给不同的controller处理
 *         1. init -->初始化需要扫描所有的controller
 *         2. 根据url信息 匹配对应的方法并返回
 */
@WebServlet(urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    /**
     * 扫描controller
     *
     * @throws ServletException 初始化异常
     */
    @Override
    public void init() throws ServletException {
        ControllerCollection.init();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(Config.DEFAULT_ENCODING);
        String method = req.getMethod();
        RequestMethod requestMethod = RequestMethod.getRequestMethod(method);
        String requestPath = PathUtil.getRequestPath(req);
        log.debug("[mini mvc] {} : {}", requestMethod, requestPath);

        // 默认路径，跳转index.html
        if (PathUtil.DEFAULT_REQUEST_PATH.equals(requestPath)) {
            PathUtil.redirectRequest(Config.DEFAULT_HOME_PATH, req, resp);
            return;
        }

        RequestBody requestBody = new RequestBody(requestMethod, requestPath);
        ControllerBody controller = ControllerCollection.getControllerBody(requestBody);
        if (controller == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            log.error("[mini mvc] {} : {}  NOT FOUND!!", requestMethod, requestPath);
            return;
        }
        HandlerInvoker.invoke(req, resp, controller);
    }

}
