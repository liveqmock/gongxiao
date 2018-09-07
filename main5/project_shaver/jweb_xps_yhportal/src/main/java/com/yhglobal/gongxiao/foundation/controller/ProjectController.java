package com.yhglobal.gongxiao.foundation.controller;

import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.viewobject.ProjectVo;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.ValidationUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Controller
@RequestMapping("/project")
public class ProjectController {

    private static Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;
    /**
     * 获取项目信息列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getProjectList", method = RequestMethod.GET)
    public GongxiaoResult getProjectInfoList(HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            //1. 接口调用准备三部曲 请求,存根,返回值
            ProjectStructure.SelectProjectListReq.Builder selectProjectListReq
                    = ProjectStructure.SelectProjectListReq.newBuilder().setRpcHeader(rpcHeader);
            ProjectServiceGrpc.ProjectServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.SelectProjectListResp selectProjectListResp=null;
            try {
                logger.info("#traceId={}# [ON][getProjectList] 开始调用项目接口,获取项目列表 ", rpcHeader.getTraceId());
                selectProjectListResp=blockingStub.selectProjectList(selectProjectListReq.build());
                logger.info("#traceId={}# [ON][getProjectList] 调用项目接口完成,获取项目列表 ", rpcHeader.getTraceId());
            }catch (Exception e){
                e.printStackTrace();
            }
            List<ProjectStructure.Project> projectListList = selectProjectListResp.getProjectListList();
            logger.info("#traceId={}# [OUT] getProjectList success: ", rpcHeader.getTraceId());
            //2. 组装参数
            List<ProjectVo> projectVoList = new ArrayList<>();
            //先给数组的第一个位置一个空值
            projectVoList.add(new ProjectVo());
            for (ProjectStructure.Project project:projectListList){
                long projectId = project.getProjectId();
                ProjectVo projectVo = new ProjectVo();
                projectVo.setProjectId(project.getProjectId());
                projectVo.setProjectName(project.getProjectName());
                projectVo.setStatus((byte)project.getRecordStatus());
                projectVo.setSupplierId(project.getSupplierId());
                projectVo.setSupplierName(project.getSupplierName());
                projectVo.setDistributionInfo(project.getDistributionInfo());
                projectVo.setPrepaidUseReferRate(DoubleScale.keepSixBits(project.getPrepaidUseReferRate()));
                projectVo.setCouponUseReferRate(DoubleScale.keepSixBits(project.getCouponUseReferRate()));
                projectVo.setMonthlyCouponGenerateRate(DoubleScale.keepSixBits(project.getMonthCouponRate()));
                projectVo.setQuarterlyCouponGenerateRate(DoubleScale.keepSixBits(project.getQuarterCouponRate()));
                projectVo.setAnnualCouponGenerateRate(DoubleScale.keepSixBits(project.getAnnualCouponRate()));
                if (projectId == portalConfig.getTargetProjectId()){
                    //如果项目ID跟当前项目ID一直,将第一个位置让给这个值
                    projectVoList.set(0,projectVo);
                }else {
                    projectVoList.add(projectVo);
                }
            }
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), projectVoList);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     *
     *
     * @param projectId 项目ID
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getProjectInfo", method = RequestMethod.GET)
    public GongxiaoResult getProjectInfo(String projectId, HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            //1. 校验参数
            boolean projectNotEmpty = ValidationUtil.isNotEmpty(projectId);
            if (!projectNotEmpty){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.PROJECT_NOT_NULL);
            }
            //2.调用接口
            ProjectStructure.GetByProjectIdReq.Builder getProjectReq
                    = ProjectStructure.GetByProjectIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId);
            ProjectServiceGrpc.ProjectServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdResp getProjectResp=null;
            try {
                getProjectResp=blockingStub.getByProjectId(getProjectReq.build());
            }catch (Exception e){
                e.printStackTrace();
            }
            //3.组装返回值
            ProjectStructure.Project project = getProjectResp.getProject();
            if (project == null){
                logger.info("#traceId={}# [OUT] fail to getProjectInfo: project=null", rpcHeader.getTraceId());
            }else {
                logger.info("#traceId={}# [OUT] getProjectInfo success: project = {}", rpcHeader.getTraceId(),project.toString());
            }
            ProjectVo projectVo = new ProjectVo();
            projectVo.setProjectId(project.getProjectId());
            projectVo.setProjectName(project.getProjectName());
            projectVo.setStatus((byte)project.getRecordStatus());
            projectVo.setSupplierId(project.getSupplierId());
            projectVo.setSupplierName(project.getSupplierName());
            projectVo.setDistributionInfo(project.getDistributionInfo());
            projectVo.setPrepaidUseReferRate(DoubleScale.keepSixBits(project.getPrepaidUseReferRate()));
            projectVo.setCouponUseReferRate(DoubleScale.keepSixBits(project.getCouponUseReferRate()));
            projectVo.setMonthlyCouponGenerateRate(DoubleScale.keepSixBits(project.getMonthCouponRate()));
            projectVo.setQuarterlyCouponGenerateRate(DoubleScale.keepSixBits(project.getQuarterCouponRate()));
            projectVo.setAnnualCouponGenerateRate(DoubleScale.keepSixBits(project.getAnnualCouponRate()));
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), projectVo);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }
}
