package com.yhglobal.gongxiao.edi.dao;

import com.yhglobal.gongxiao.edi.dao.mapping.EdiUploadRecordMapper;
import com.yhglobal.gongxiao.edi.entity.model.EdiRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * edi上传DAO
 *
 * @author 葛灿
 */
@Repository
public class EdiUploadRecordDao {

    @Autowired
    private EdiUploadRecordMapper ediUploadRecordMapper;

    /**
     * 插入一条下载记录
     *
     * @param ediRecord 对象
     * @return 插入成功条数
     */
    public int insert(String fileType, EdiRecord ediRecord) {
        return ediUploadRecordMapper.insert(fileType, ediRecord);
    }

    /**
     * 根据文件类型查找未上传的文件
     *
     * @param fileType 文件类型
     * @return List<EdiRecord>
     */
    public List<EdiRecord> selectFileNameListByType(String fileType) {
        return ediUploadRecordMapper.selectFileNameListByType(fileType);
    }

    /**
     * 更新上传状态
     *
     * @param fileType 文件类型
     * @param recordId 主键id
     * @return 更新条数
     */
    public int updateUploadStatus(String fileType, long recordId) {
        return ediUploadRecordMapper.updateUploadStatus(fileType, recordId);
    }
}
