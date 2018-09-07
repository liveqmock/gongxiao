package com.yhglobal.gongxiao.foudation.project.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.project.dao.ProjectDao;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(timeout = 5000)public class ProjectServiceImpl implements ProjectService {
    private static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    private ProjectDao projectDao;

    @Override
    public int insertProject(RpcHeader rpcHeader,Project record) {
        logger.info("#traceId={}# [IN][insertProject] params: Project={}", rpcHeader.traceId, record.toString());
       try {
           int insert = projectDao.insert(record);
           logger.info("#traceId={}# [OUT] insertProject success: insertNum={}", rpcHeader.traceId, insert);
           return insert;
       }catch (Exception e){
           logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
           throw e;
       }
    }

    @Override
    public List<Project> selectProjectList(RpcHeader rpcHeader) {
        logger.info("#traceId={}# [IN][selectProjectList] params: ", rpcHeader.traceId);
       try {
           List<Project> projects = projectDao.selectAll();
           if (projects.size()==0) {
               logger.info("#traceId={}# [OUT] fail to selectProjectList: projects=NULL", rpcHeader.traceId);
           } else {
               logger.info("#traceId={}# [OUT] selectProjectList success: projects size()={}", rpcHeader.traceId, projects.size());
           }
           return projects;
       }catch (Exception e){
           logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
           throw e;
       }
    }

    @Override
    public Project getByProjectId(RpcHeader rpcHeader,String projectId) {
        logger.info("#traceId={}# [IN][getByProjectId] params: projectId={}", rpcHeader.traceId,projectId);
       try {
           Project project = projectDao.getByProjectId(Long.parseLong(projectId));
           if (project == null) {
               logger.info("#traceId={}# [OUT] fail to getByProjectId: warehouses=NULL", rpcHeader.traceId);
           } else {
               logger.info("#traceId={}# [OUT] getByProjectId success: project={}", rpcHeader.traceId, project.toString());
           }
            return project;
       }catch (Exception e){
           logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
           throw e;
       }
    }

    @Override
    public Project getByEASProjectName(String easProjectName) {
        logger.info("[IN][getByProjectName] params: easProjectName={}", easProjectName);
        try {
            Project project = projectDao.getByEASProjectName(easProjectName);
            if (project == null) {
                logger.info("[OUT] fail to getByProjectName: warehouses=NULL");
            } else {
                logger.info("[OUT] getByProjectName success: project={}",project.toString());
            }
            return project;
        }catch (Exception e){
            logger.error("# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }
    }


}
