package com.yhglobal.gongxiao.edi.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.edi.entity.model.EdiRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * edi下载Mapper
 *
 * @author 葛灿
 */
public interface EdiDownloadRecordMapper extends BaseMapper {

    @Insert({
            "insert into edi_download_record_${fileType}",
            "(fileName, filePath, fileMd5)",
            "values (#{record.fileName}, #{record.filePath}, #{record.fileMd5})"
    })
    int insert(
            @Param("fileType") String fileType,
            @Param("record") EdiRecord record);

    @Select({
            "select fileName from edi_download_record_${fileType}"
    })
    Set<String> selectFileNameListByType(@Param("fileType") String fileType);


    @Select({
            " select ",
            " recordId, fileName, filePath, fileMd5",
            " from",
            " edi_download_record_${fileType}",
            " where ",
            " recordId > #{lowerLimit} "
    })
    List<EdiRecord> selectFileByTypeAndLowerLimit(@Param("fileType") String fileType,
                                                  @Param("lowerLimit") long lowerLimit);
}
