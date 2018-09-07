package com.yhglobal.gongxiao.edi.dao;

import com.yhglobal.gongxiao.edi.dao.mapping.EdiDownloadRecordMapper;
import com.yhglobal.gongxiao.edi.entity.model.EdiRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * edi下载DAO
 *
 * @author 葛灿
 */
@Repository
public class EdiDownloadRecordDao {

    @Autowired
    private EdiDownloadRecordMapper ediDownloadRecordMapper;

    /**
     * 解析下载的文件后,进行插入
     *
     * @param fileType  文件类型(决定表名)
     * @param record  文件名称
     * @return 插入成功的条数
     */
    public int insert(String fileType, EdiRecord record) {
        return ediDownloadRecordMapper.insert(fileType,  record);
    }

    /**
     * 根据文件类型,查找已经下载到本地的文件名清单
     *
     * @param fileType 文件类型
     * @return Set<文件名>
     */
    public Set<String> selectFileNameListByType(String fileType) {
        return ediDownloadRecordMapper.selectFileNameListByType(fileType);
    }

    /**
     * 根据文件类型、主键id
     *
     * @param fileType 文件类型
     * @param lowerLimit  主键id下限
     * @return
     */
    public List<EdiRecord> selectFileByTypeAndLowerLimit(String fileType, long lowerLimit) {
        return ediDownloadRecordMapper.selectFileByTypeAndLowerLimit(fileType, lowerLimit);
    }
}
