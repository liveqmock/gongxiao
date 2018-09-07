package com.yhglobal.gongxiao.phjd.foundation.basics;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
import com.yhglobal.gongxiao.phjd.bean.ProjectBean;
import com.yhglobal.gongxiao.phjd.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目数据管理
 * @author yuping.lin
 */
@Controller
@RequestMapping("/base/project")
public class ProjectController {
    private static Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    /**
     * 修改
     */
    @RequestMapping("/updateProjectByid")
    @ResponseBody
    public GongxiaoResult updateProject(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam(defaultValue = "") String easProjectCode,
                                        @RequestParam(defaultValue = "") String supplierId,
                                        @RequestParam(defaultValue = "") String supplierName,
                                        @RequestParam(defaultValue = "") String beforeCouponAmount,
                                        @RequestParam(defaultValue = "") String afterCouponAmount,
                                        @RequestParam(defaultValue = "") String monthCouponRate,
                                        @RequestParam(defaultValue = "") String quarterCouponRate,
                                        @RequestParam(defaultValue = "") String annualCouponRate,
                                        @RequestParam(defaultValue = "") String operater,
                                        @RequestParam(defaultValue = "0") int lastUpdateTime) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][updateProject] params:", traceId, easProjectCode, supplierId, supplierName, beforeCouponAmount, afterCouponAmount,
                    monthCouponRate, quarterCouponRate, annualCouponRate, operater, lastUpdateTime);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ProjectStructure.UpdateProjectReq.Builder builder = ProjectStructure.UpdateProjectReq.newBuilder();
            builder.setRpcHeader(rpcHeader);
            builder.setEasProjectCode(easProjectCode);
            builder.setSupplierId(supplierId);
            builder.setSupplierName(supplierName);
            builder.setBeforeCouponAmount(beforeCouponAmount);
            builder.setAfterCouponAmount(afterCouponAmount);
            builder.setMonthCouponRate(monthCouponRate);
            builder.setQuarterCouponRate(quarterCouponRate);
            builder.setAnnualCouponRate(annualCouponRate);
            builder.setOperater(operater);
            builder.setLastUpdateTime(lastUpdateTime);
            builder.setProjectId(portalConfig.getPortalId());
            ProjectStructure.UpdateProjectReq req = builder.build();
            ProjectServiceGrpc.ProjectServiceBlockingStub stub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.UpdateProjectResp resp = stub.updateProject(req);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp);
            logger.info("#traceId={}# [updateProject][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据条件获取项目列表信息
     */
    /**
     * @param request
     * @param response
     * @param EASCode     EAS项目编码
     * @param projectName 项目名称
     * @param companyBody 公司主体
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("/selectProject")
    @ResponseBody
    public GongxiaoResult selectProjectList(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam(defaultValue = "0") String EASCode,
                                            @RequestParam(defaultValue = "") String projectName,
                                            @RequestParam(defaultValue = "") String companyBody,
                                            @RequestParam(defaultValue = "1") int pageNumber,
                                            @RequestParam(defaultValue = "60") int pageSize) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][selectProjectList] params: EASCode:{}, projectName:{}, companyBody:{}",
                    traceId, EASCode, projectName, companyBody);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ProjectStructure.SelectProjectByConditionReq.Builder builder = ProjectStructure.SelectProjectByConditionReq.newBuilder();
            builder.setRpcHeader(rpcHeader);
            builder.setProjectId(Long.parseLong(portalConfig.getPortalId()));
            builder.setProjectName(projectName);
            builder.setCompany(companyBody);
            builder.setPageNumber(pageNumber);
            builder.setPageSize(pageSize);
            ProjectStructure.SelectProjectByConditionReq req = builder.build();
            ProjectServiceGrpc.ProjectServiceBlockingStub stub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.SelectProjectByConditionResp reqp = stub.selectProjectByCondition(req);
            List<ProjectBean> projectBeanList = new ArrayList<>();

            for (ProjectStructure.Project list : reqp.getProjectList()) {
                ProjectBean projectBean = new ProjectBean();
                projectBean.setEasProjectCode(list.getEasProjectCode());
                projectBean.setProjectName(list.getProjectName());
                projectBean.setCompany(list.getCompany());
                projectBean.setMonthCouponRate(list.getMonthCouponRate());
                projectBean.setQuarterCouponRate(list.getQuarterCouponRate());
                projectBean.setAnnualCouponRate(list.getAnnualCouponRate());
                //TODO(缺少:返利使用比例,代垫使用比例,操作人 字段)
//                projectBean.setProportionOfRebateUse(list.getpro)
//                projectBean.setProportionOfSubstitute(list.get)
//                projectBean.setOperator(list.getop);
                projectBean.setLastModificationTime(list.getLastUpdateTime());
                projectBeanList.add(projectBean);
            }
            PageInfo<ProjectBean> pageinfo = new PageInfo<>();
            pageinfo.setList(projectBeanList);
            pageinfo.setPageNum(pageNumber);
            pageinfo.setPageSize(pageSize);
            pageinfo.setTotal(reqp.getTotal());
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), pageinfo);
            logger.info("#traceId={}# [selectProjectList][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
            return gongxiaoResult;
    }

    /**
     * 根据项目ID获取项目详情
     */
    @RequestMapping("/getProjectById")
    @ResponseBody
    public GongxiaoResult getProjectInfo(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(defaultValue = "") String EASCode) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getProjectInfo] params: EASCode:{}", traceId, EASCode);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            ProjectStructure.GetByProjectIdReq.Builder builder = ProjectStructure.GetByProjectIdReq.newBuilder();
            builder.setRpcHeader(rpcHeader);
            builder.setProjectId(EASCode);
            ProjectStructure.GetByProjectIdReq req = builder.build();
            ProjectServiceGrpc.ProjectServiceBlockingStub stub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdResp reqp = stub.getByProjectId(req);
            ProjectBean projectBean = new ProjectBean();
            projectBean.setEasProjectCode(reqp.getProject().getEasProjectCode());
            projectBean.setProjectName(reqp.getProject().getProjectName());
            projectBean.setCompany(reqp.getProject().getCompany());
            projectBean.setMonthCouponRate(reqp.getProject().getMonthCouponRate());
            projectBean.setProjectCode(reqp.getProject().getProjectId());  //项目编码
            projectBean.setQuarterCouponRate(reqp.getProject().getQuarterCouponRate());
            projectBean.setSupplierId(reqp.getProject().getSupplierId());
            projectBean.setSupplierName(reqp.getProject().getSupplierName());
            projectBean.setAnnualCouponRate(reqp.getProject().getAnnualCouponRate());
            projectBean.setBeforeCouponAmount(reqp.getProject().getBeforeCouponAmount());
            projectBean.setAfterCouponAmount(reqp.getProject().getAfterCouponAmount());
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), projectBean);
            logger.info("#traceId={}# [getProjectInfo][OUT]");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
