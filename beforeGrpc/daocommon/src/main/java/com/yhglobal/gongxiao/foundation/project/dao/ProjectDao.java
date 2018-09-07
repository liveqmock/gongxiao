package com.yhglobal.gongxiao.foundation.project.dao;

import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.dao.mapping.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectDao {
    @Autowired
    private ProjectMapper projectMapper;

    public int updateProjectEasInfo(int projectId,String easProjectName,String easProjectCode,String orgCode,String orgName){
        return projectMapper.updateProjectEasInfo(projectId,easProjectName,easProjectCode, orgCode, orgName);
    }

    public int insert(Project record){
        return projectMapper.insert(record);
    }

    public List<Project> selectAll(){
        return projectMapper.selectAll();
    }

    public Project getByProjectId(Long projectId){
        return projectMapper.selectByProjectId(projectId);
    }

    public Project getByEASProjectName(String easProjectName){
        return projectMapper.getByEASProjectName(easProjectName);
    }

    public String selectSupplierNameById(String projectId){
        return projectMapper.selectSupplierNameById(projectId);
    }

    public String getProjectCodeById(String projectId){
        return projectMapper.getProjectCodeById(projectId);
    }
}
