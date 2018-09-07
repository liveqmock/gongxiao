package com.yhglobal.gongxiao.foundation.file.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.file.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface FileMapper extends BaseMapper {
    @Delete({
        "delete from t_file",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into t_file (id, fileRealName, ",
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
    int insert(File record);

    @Select({
        "select",
        "id, fileRealName, fileName, filePath, relatedId, recordStatus, createdById, ",
        "createdByName, createTime, lastUpdateTime",
        "from t_file",
        "where id = #{id,jdbcType=BIGINT}"
    })

    File selectByPrimaryKey(Long id);

    @Update({
            "update t_file",
            "set recordStatus = #{recordStatus},",
            "lastUpdateTime = NOW()",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateFileStatus(long id,byte recordStatus );


}