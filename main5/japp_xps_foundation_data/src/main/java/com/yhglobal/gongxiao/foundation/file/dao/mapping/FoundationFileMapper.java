package com.yhglobal.gongxiao.foundation.file.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.file.model.FoundationFile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FoundationFileMapper extends BaseMapper {
    @Delete({
        "delete from foundation_file",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into foundation_file (id, fileRealName, ",
        "fileName, filePath, ",
        "relatedId, recordStatus, ",
        "createdById, createdByName, ",
        "createTime, lastUpdateTime)",
        "values (#{id,jdbcType=BIGINT}, #{fileRealName,jdbcType=VARCHAR}, ",
        "#{fileName,jdbcType=VARCHAR}, #{filePath,jdbcType=VARCHAR}, ",
        "#{relatedId,jdbcType=BIGINT}, #{recordStatus,jdbcType=TINYINT}, ",
        "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP})"
    })
    int insert(FoundationFile record);

    @Select({
        "select",
        "id, fileRealName, fileName, filePath, relatedId, recordStatus, createdById, ",
        "createdByName, createTime, lastUpdateTime",
        "from foundation_file",
        "where id = #{id,jdbcType=BIGINT}"
    })
    FoundationFile selectByPrimaryKey(Long id);

    @Select({
            "select",
            "id, fileRealName, fileName, filePath, relatedId, recordStatus, createdById, ",
            "createdByName, createTime, lastUpdateTime",
            "from foundation_file",
    })
    List<FoundationFile> selectFileList();


    @Update({
        "update foundation_file",
        "set fileRealName = #{fileRealName,jdbcType=VARCHAR},",
          "fileName = #{fileName,jdbcType=VARCHAR},",
          "filePath = #{filePath,jdbcType=VARCHAR},",
          "relatedId = #{relatedId,jdbcType=BIGINT},",
          "recordStatus = #{recordStatus,jdbcType=TINYINT},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(FoundationFile record);
}