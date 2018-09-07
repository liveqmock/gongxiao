package com.yhglobal.gongxiao.user.controller;

import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.portal.user.model.YhPortalUser;
import com.yhglobal.gongxiao.user.service.UserService;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/user")
@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    PortalConfig portalConfig;

    @RequestMapping("/")
    public String redirect(HttpServletRequest request, HttpServletResponse response) {
//        ModelAndView modelAndView = new ModelAndView("procontrol");
        return "/WEB-INF/pages/login.html";
    }


    @RequestMapping("/login")
    @ResponseBody
    public GongxiaoResult loginYhPortal(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        //swagger配置
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId("00000", TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][loginYhPortal]: username={} passwd={}", traceId, username, password);
            YhPortalUser yhPortalUser = userService.getYhPortalUserByName(username);
            if (yhPortalUser == null) {  //用户不存在
                gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.USER_NOT_EXIST);
                logger.info("#traceId={}# [OUT] fail to login: username={} Error={}", traceId, username, ErrorCode.USER_NOT_EXIST);
            } else if (!password.equals(yhPortalUser.getPasswd())) {  //密码不匹配
                gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PASSWORD_NOT_MATCH);
                logger.info("#traceId={}# [OUT] fail to login: username={} Error={}", traceId, username, ErrorCode.PASSWORD_NOT_MATCH);
            } else {  //登录成功
                PortalUserInfo sessionUserInfo = new PortalUserInfo();
                sessionUserInfo.setUserId(yhPortalUser.getUserId());
                sessionUserInfo.setUserName(yhPortalUser.getUserName());
                //sessionUserInfo.setProjectId(??);//TODO
                request.getSession().setAttribute("sessionInfo",  sessionUserInfo);  //把用户信息存到session
                gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), sessionUserInfo);  //把用户信息返回去
                logger.info("#traceId={}# [OUT] login success: username={}", traceId, username);
            }
        } catch (Exception e) {
            logger.error("#traceId={}#  handleLogin: ", traceId);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
