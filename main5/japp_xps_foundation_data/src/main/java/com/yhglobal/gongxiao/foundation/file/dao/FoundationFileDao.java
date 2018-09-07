package com.yhglobal.gongxiao.foundation.file.dao;

import com.yhglobal.gongxiao.foundation.file.dao.mapping.FoundationFileMapper;
import com.yhglobal.gongxiao.foundation.file.model.FoundationFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class FoundationFileDao {

    @Autowired
    FoundationFileMapper foundationFileMapper;

    /**
     * 插入新文件信息
     * @param foundationFile
     * @return
     */
    public int insertFile(FoundationFile foundationFile){
        return foundationFileMapper.insert(foundationFile);
    }

    /**
     * 获取所有文件列表
     * @return
     */
    public List<FoundationFile> selectFileList(){
        return foundationFileMapper.selectFileList();
    }

}
