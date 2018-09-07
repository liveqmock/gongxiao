package com.yhglobal.gongxiao.foundation.file.dao;

import com.yhglobal.gongxiao.foundation.file.File;
import com.yhglobal.gongxiao.foundation.file.dao.mapping.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 文件上传下载
 *
 * @author: 陈浩
 **/
@Repository
public class FileDao {

    @Autowired
    FileMapper fileMapper;

    /**
     * 添加新文件
     * @param record 文件信息
     * @return
     */
    public int addFileRecord(File record){
        return fileMapper.insert(record);
    }

    /**
     * 更新文件状态
     * @param id  记录ID
     * @param recordStatus 0无效 1有效
     * @return
     */
    public int updateFileStatus(long id,byte recordStatus){
        return fileMapper.updateFileStatus(id,recordStatus);
    }

}
