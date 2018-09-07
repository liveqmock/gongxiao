package com.yhglobal.gongxiao.foundation.project.dao;

import com.yhglobal.gongxiao.foundation.project.dao.mapping.FoundationProjectMapper;
import com.yhglobal.gongxiao.foundation.project.model.FoundationProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FoundationProjectDao {

    @Autowired
    private FoundationProjectMapper projectMapper;

    /**
     * 插入新的项目
     * @param record
     * @return
     */
    public int insert(FoundationProject record){
        return projectMapper.insert(record);
    }

    /**
     * 获取所有项目列表
     * @return
     */
    public List<FoundationProject> selectAll(){
        return projectMapper.selectAllProject();
    }

    /**
     * 通过项目ID获取项目详情
     * @param projectId
     * @return
     */
    public FoundationProject getByProjectId(Long projectId){
        return projectMapper.getProjectById(projectId);
    }

    /**
     * 通过项目名称获取项目详情
     * @param projectName
     * @return
     */
    public FoundationProject getByProjectName(String projectName){
        return projectMapper.getProjectByName(projectName);
    }

    /**
     * 条件查询
     * @param easCode
     * @param projectName
     * @param company
     * @return
     */
    public List<FoundationProject> selectProjectByCondition(String easCode,
                                                            String projectName,
                                                            String company){
        return projectMapper.selectProjectCondition(easCode,projectName,company);
    }
}
