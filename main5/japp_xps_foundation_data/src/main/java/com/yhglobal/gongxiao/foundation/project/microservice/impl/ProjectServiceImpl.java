package com.yhglobal.gongxiao.foundation.project.microservice.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.foundation.project.dao.FoundationProjectDao;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.project.model.FoundationProject;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl extends ProjectServiceGrpc.ProjectServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    private FoundationProjectDao projectDao;

    /**
     * 通过项目id获取该项目的信息
     * <p>
     * rpcHeader rpc调用的header
     * projectId  项目id
     *
     * @return
     */
    @Override
    public void getByProjectId(ProjectStructure.GetByProjectIdReq request,
                               StreamObserver<ProjectStructure.GetByProjectIdResp> responseObserver) {
        String traceId = request.getRpcHeader().getTraceId();
        String projectId = request.getProjectId();
        logger.info("#traceId={}# [IN][getProjectById] params: projectId={}", traceId, projectId);
        ProjectStructure.GetByProjectIdResp getProjectByIdResp; //保存返回的值
        ProjectStructure.GetByProjectIdResp.Builder respBuilder = ProjectStructure.GetByProjectIdResp.newBuilder(); //每个proto对象都需要从builder构建出来
        try {

            FoundationProject project = projectDao.getByProjectId(new Long(projectId));
            if (project == null) {
                logger.info("#traceId={}# [OUT] fail to getProjectById: projectId={}", traceId, projectId);
                //RpcErrorResponseBuilder.buildWithEnumError(respBuilder, ErrorCode.TARGET_DATA_NOT_EXIST);
                String msg = ErrorCode.TARGET_DATA_NOT_EXIST.getMessage() + ": projectId=" + projectId; //定制返回去的错误信息
//                RpcErrorResponseBuilder.buildWithErrorCodeAndMsg(respBuilder, ErrorCode.TARGET_DATA_NOT_EXIST.getErrorCode(), msg); //这里通过工具类生成对应的proto错误对象
            } else {
                logger.info("#traceId={}# [OUT] getByProjectId success: projectId={} projectName={}", traceId, projectId, project.getProjectName());
//                trans.javaToMessage(project, respBuilder); //把java bean转换为protobuf对象
                ProjectStructure.Project project1 = ProjectStructure.Project.newBuilder()
                        .setProjectId(GrpcCommonUtil.getProtoParam(project.getProjectId()))
                        .setProjectCode(GrpcCommonUtil.getProtoParam(project.getProjectCode()))
                        .setProjectName(GrpcCommonUtil.getProtoParam(project.getProjectName()))
                        .setEasProjectCode(GrpcCommonUtil.getProtoParam(project.getEasProjectCode()))
                        .setEasProjectName(GrpcCommonUtil.getProtoParam(project.getEasProjectName()))
                        .setProjectTablePrefix(GrpcCommonUtil.getProtoParam(GrpcCommonUtil.getProtoParam(project.getProjectTablePrefix())))
                        .setRecordStatus(GrpcCommonUtil.getProtoParam(project.getRecordStatus()))
                        .setSupplierId(GrpcCommonUtil.getProtoParam(Integer.parseInt(project.getSupplierId())))
                        .setSupplierName(GrpcCommonUtil.getProtoParam(project.getSupplierName()))
                        .setMonthCouponRate(GrpcCommonUtil.getProtoParam(project.getMonthCouponRate()))
                        .setQuarterCouponRate(GrpcCommonUtil.getProtoParam(project.getQuarterCouponRate()))
                        .setAnnualCouponRate(GrpcCommonUtil.getProtoParam(project.getAnnualCouponRate()))
                        .setBeforeCouponAmount(GrpcCommonUtil.getProtoParam(project.getBeforeCouponAmount()))
                        .setAfterCouponAmount(GrpcCommonUtil.getProtoParam(project.getAfterCouponAmount()))
                        .setCreatedById(GrpcCommonUtil.getProtoParam((int) (long) project.getCreatedById()))
                        .setCreatedByName(GrpcCommonUtil.getProtoParam(project.getCreatedByName()))
                        .setCreateTime(GrpcCommonUtil.getProtoParam(DateUtil.getTime(project.getCreateTime())))
                        .setLastUpdateTime(GrpcCommonUtil.getProtoParam(DateUtil.getTime(project.getLastUpdateTime())))
                        .setTracelog(GrpcCommonUtil.getProtoParam(project.getTracelog()))
                        .setWmdProjectCode(GrpcCommonUtil.getProtoParam(project.getWmsProjectCode()))
                        .setWmsProjectName(GrpcCommonUtil.getProtoParam(project.getWmsProjectName()))
                        .setTmsProjectCode(GrpcCommonUtil.getProtoParam(project.getTmsProjectCode()))
                        .setTmsProjectName(GrpcCommonUtil.getProtoParam(project.getTmsProjectName()))
                        .build();
                respBuilder.setProject(project1);
            }
            logger.info("#traceId={}# [OUT][selectProjectByCondition] success ", traceId);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 查询所有的项目
     * <p>
     * rpcHeader rpc调用的header
     *
     * @return
     */
    @Override
    public void selectProjectList(ProjectStructure.SelectProjectListReq request,
                                  StreamObserver<ProjectStructure.SelectProjectListResp> responseObserver) {
        String traceId = request.getRpcHeader().getTraceId();
        logger.info("#traceId={}# [IN][getProjectById] params: ", traceId);
        ProjectStructure.SelectProjectListResp response; //保存返回的值
        ProjectStructure.SelectProjectListResp.Builder respBuilder = ProjectStructure.SelectProjectListResp.newBuilder(); //每个proto对象都需要从builder构建出来

        logger.info("#traceId={}# [IN][selectProjectList] params: ", traceId);
        try {
            List<FoundationProject> projects = projectDao.selectAll();
            if (projects.size() == 0) {
                logger.info("[selectProjectList] 没有拿到项目数据 #traceId={}", traceId);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            for (FoundationProject project : projects) {
                ProjectStructure.Project project1 = ProjectStructure.Project.newBuilder()
                        .setProjectId(GrpcCommonUtil.getProtoParam(project.getProjectId()))
                        .setProjectCode(GrpcCommonUtil.getProtoParam(project.getProjectCode()))
                        .setProjectName(GrpcCommonUtil.getProtoParam(project.getProjectName()))
                        .setEasProjectCode(GrpcCommonUtil.getProtoParam(project.getEasProjectCode()))
                        .setEasProjectName(GrpcCommonUtil.getProtoParam(project.getEasProjectName()))
                        .setProjectTablePrefix(GrpcCommonUtil.getProtoParam(GrpcCommonUtil.getProtoParam(project.getProjectTablePrefix())))
                        .setRecordStatus(GrpcCommonUtil.getProtoParam(project.getRecordStatus()))
                        .setSupplierId(GrpcCommonUtil.getProtoParam(Integer.parseInt(project.getSupplierId())))
                        .setSupplierName(GrpcCommonUtil.getProtoParam(project.getSupplierName()))
                        .setMonthCouponRate(GrpcCommonUtil.getProtoParam(project.getMonthCouponRate()))
                        .setQuarterCouponRate(GrpcCommonUtil.getProtoParam(project.getQuarterCouponRate()))
                        .setAnnualCouponRate(GrpcCommonUtil.getProtoParam(project.getAnnualCouponRate()))
                        .setBeforeCouponAmount(GrpcCommonUtil.getProtoParam(project.getBeforeCouponAmount()))
                        .setAfterCouponAmount(GrpcCommonUtil.getProtoParam(project.getAfterCouponAmount()))
                        .setCreatedById(GrpcCommonUtil.getProtoParam((int) (long) project.getCreatedById()))
                        .setCreatedByName(GrpcCommonUtil.getProtoParam(GrpcCommonUtil.getProtoParam(project.getCreatedByName())))
                        .setCreateTime(GrpcCommonUtil.getProtoParam(DateUtil.getTime(project.getCreateTime())))
                        .setLastUpdateTime(GrpcCommonUtil.getProtoParam(DateUtil.getTime(project.getLastUpdateTime())))
                        .setTracelog(GrpcCommonUtil.getProtoParam(GrpcCommonUtil.getProtoParam(project.getTracelog())))
                        .setWmdProjectCode(GrpcCommonUtil.getProtoParam(project.getWmsProjectCode()))
                        .setWmsProjectName(GrpcCommonUtil.getProtoParam(project.getWmsProjectName()))
                        .setTmsProjectCode(GrpcCommonUtil.getProtoParam(project.getTmsProjectCode()))
                        .setTmsProjectName(GrpcCommonUtil.getProtoParam(project.getTmsProjectName()))
                        .build();
                respBuilder.addProjectList(project1);
            }
            logger.info("#traceId={}# [OUT][selectProjectList] success projects.size={}", traceId,projects.size());
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    public void selectProjectByCondition(ProjectStructure.SelectProjectByConditionReq req,
                                         StreamObserver<ProjectStructure.SelectProjectByConditionResp> responseObserver) {
        ProjectStructure.SelectProjectByConditionResp.Builder respBuilder = ProjectStructure.SelectProjectByConditionResp.newBuilder(); //每个proto对象都需要从builder构建出来
        String traceId = req.getRpcHeader().getTraceId();
        String projectName = req.getProjectName();
        String company = req.getCompany();
        String easCode = req.getEasCode();
        int pageNumber = req.getPageNumber();
        int pageSize = req.getPageSize();
        logger.info("#traceId={}# [IN][selectProjectByCondition] params: projectName={}, company={}, easCode={}, pageNumber={}, pageSize={}",
                traceId,projectName,company,easCode,pageNumber,pageSize);
        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<FoundationProject> foundationProjects = projectDao.selectProjectByCondition(easCode, projectName, company);
            PageInfo<FoundationProject> purchaseOrderPageInfo = new PageInfo<>(foundationProjects);
            if (foundationProjects.size()==0){
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                logger.warn("#traceId={} [selectProjectByCondition] 没有找到对应的记录");
                return;
            }
            for (FoundationProject project : foundationProjects) {
                ProjectStructure.Project project1 = ProjectStructure.Project.newBuilder()
                        .setProjectId(GrpcCommonUtil.getProtoParam(project.getProjectId()))
                        .setProjectCode(GrpcCommonUtil.getProtoParam(project.getProjectCode()))
                        .setProjectName(GrpcCommonUtil.getProtoParam(project.getProjectName()))
                        .setEasProjectCode(GrpcCommonUtil.getProtoParam(project.getEasProjectCode()))
                        .setEasProjectName(GrpcCommonUtil.getProtoParam(project.getEasProjectName()))
                        .setProjectTablePrefix(GrpcCommonUtil.getProtoParam(GrpcCommonUtil.getProtoParam(project.getProjectTablePrefix())))
                        .setRecordStatus(GrpcCommonUtil.getProtoParam(project.getRecordStatus()))
                        .setSupplierId(GrpcCommonUtil.getProtoParam(Integer.parseInt(project.getSupplierId())))
                        .setSupplierName(GrpcCommonUtil.getProtoParam(project.getSupplierName()))
                        .setMonthCouponRate(GrpcCommonUtil.getProtoParam(project.getMonthCouponRate()))
                        .setQuarterCouponRate(GrpcCommonUtil.getProtoParam(project.getQuarterCouponRate()))
                        .setAnnualCouponRate(GrpcCommonUtil.getProtoParam(project.getAnnualCouponRate()))
                        .setBeforeCouponAmount(GrpcCommonUtil.getProtoParam(project.getBeforeCouponAmount()))
                        .setAfterCouponAmount(GrpcCommonUtil.getProtoParam(project.getAfterCouponAmount()))
                        .setCreatedById(GrpcCommonUtil.getProtoParam((int) (long) project.getCreatedById()))
                        .setCreatedByName(GrpcCommonUtil.getProtoParam(GrpcCommonUtil.getProtoParam(project.getCreatedByName())))
                        .setCreateTime(GrpcCommonUtil.getProtoParam(DateUtil.getTime(project.getCreateTime())))
                        .setLastUpdateTime(GrpcCommonUtil.getProtoParam(DateUtil.getTime(project.getLastUpdateTime())))
                        .setTracelog(GrpcCommonUtil.getProtoParam(GrpcCommonUtil.getProtoParam(project.getTracelog())))
                        .setWmdProjectCode(GrpcCommonUtil.getProtoParam(project.getWmsProjectCode()))
                        .setWmsProjectName(GrpcCommonUtil.getProtoParam(project.getWmsProjectName()))
                        .setTmsProjectCode(GrpcCommonUtil.getProtoParam(project.getTmsProjectCode()))
                        .setTmsProjectName(GrpcCommonUtil.getProtoParam(project.getTmsProjectName()))
                        .build();
                respBuilder.addProject(project1);
            }
            logger.info("#traceId={}# [OUT][selectProjectByCondition] success foundationProjects.size={}", traceId,foundationProjects.size());
            respBuilder.setTotal(purchaseOrderPageInfo.getTotal());
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

}
