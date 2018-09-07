package com.yhglobal.gongxiao.base.interceptor;

import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器，未登录跳转到登录页面
 *
 * @author: 葛灿
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
        /*******正式环境使用以下代码*******/
        PortalUserInfo sessionUserInfo = (PortalUserInfo) httpServletRequest.getSession().getAttribute("sessionInfo");
        if (sessionUserInfo != null) {
            portalUserInfo.setUserId(sessionUserInfo.getUserId());
            portalUserInfo.setUserName(sessionUserInfo.getUserName());
            portalUserInfo.setIpaddr(getUserIpAddr(httpServletRequest));
            portalUserInfo.setProjectId(sessionUserInfo.getProjectId());
            httpServletRequest.setAttribute("portalUserInfo", portalUserInfo);
            return true;
        }
        httpServletResponse.sendRedirect(portalConfig.getLoginUrl());
        return false;
        /*******正式环境使用以上代码*******/

        /***开发测试环境，使用以下代码***/
//        portalUserInfo.setUserId("13");
//        portalUserInfo.setUserName("gecan");
//        portalUserInfo.setProjectId(146798161L);
//        portalUserInfo.setIpaddr(getUserIpAddr(httpServletRequest));
//        System.out.println(portalUserInfo.toString());
//        httpServletRequest.setAttribute("portalUserInfo", portalUserInfo);
//        return true;
        /***开发测试环境，使用以上代码***/
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
