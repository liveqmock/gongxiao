package com.yhglobal.gongxiao.transport.servlet;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RewriteDispatcherServlet extends DispatcherServlet {
    @Override
    protected void noHandlerFound(HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        response.sendRedirect(request.getContextPath() + "/transport/notFound");
    }
}
