package com.yhglobal.gongxiao.edi.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.edi.entity.model.EdiRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * edi上传Mapper
 *
 * @author 葛灿
 */
public interface EdiUploadRecordMapper extends BaseMapper {

    @Insert({
            "insert into edi_upload_record_${fileType} ",
            "(fileName,filePath, fileMd5, uploadStatus)",
            "values ",
            "(#{record.fileName}, #{record.filePath}, #{record.fileMd5}, false)"
    })
    int insert(@Param("fileType") String fileType,
               @Param("record") EdiRecord ediRecord);

    @Select({
            "select recordId, fileName, filePath",
            "from edi_upload_record_${fileType}",
            "where uploadStatus = false"
    })
    List<EdiRecord> selectFileNameListByType(@Param("fileType") String fileType);

    @Update({
            "update edi_upload_record_${fileType} set",
            "uploadStatus = true",
            "where",
            "recordId = #{recordId}"
    })
    int updateUploadStatus(@Param("fileType") String fileType,
                           @Param("recordId") long recordId);
}
