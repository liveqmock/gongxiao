package com.yhglobal.gongxiao.base.interceptor;

import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器，未登录跳转到登录页面
 */
public class PortalUserInterceptor implements HandlerInterceptor {

    @Autowired
    PortalUserInfo portalUserInfo;

    @Autowired
    PortalConfig portalConfig;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        PortalUserInfo sessionUserInfo = (PortalUserInfo) httpServletRequest.getSession().getAttribute("sessionInfo");
        if (sessionUserInfo != null) {
            portalUserInfo.setUserId(sessionUserInfo.getUserId());
            portalUserInfo.setUserName(sessionUserInfo.getUserName());
            portalUserInfo.setIpaddr(getUserIpAddr(httpServletRequest));
            portalUserInfo.setProjectId(sessionUserInfo.getProjectId());
            portalUserInfo.setDistributorId(sessionUserInfo.getDistributorId());
            portalUserInfo.setDistributorName(sessionUserInfo.getDistributorName());
            httpServletRequest.setAttribute("portalUserInfo", portalUserInfo);
            return true;
        }
        String contextPath = httpServletRequest.getContextPath();
        httpServletRequest.setAttribute("address",contextPath);
        httpServletResponse.sendRedirect(portalConfig.getLoginUrl());
        //String contextPath=httpServletRequest.getContextPath();
        //GongxiaoResult gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.USER_NOT_LOGIN);
        //String s = JSON.toJSONString(gongxiaoResult);
        //httpServletResponse.getWriter().append(s);
        return false;
//        /***开发测试环境，使用以下代码***/
//        portalUserInfo.setUserId("1");
//        portalUserInfo.setUserName("gecan");
//        portalUserInfo.setIpaddr(getUserIpAddr(httpServletRequest));
//        portalUserInfo.setDistributorId(2);
//        portalUserInfo.setProjectId(146798161);
//        httpServletRequest.setAttribute("portalUserInfo", portalUserInfo);
//        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public static String getUserIpAddr(HttpServletRequest request) {
        String userIp;
        if (request.getHeader("x-forwarded-for") == null) {
            userIp = request.getRemoteAddr();
        } else {
            userIp = request.getHeader("x-forwarded-for");
        }
        return userIp;
    }

}
