package com.yhglobal.gongxiao.foundation.DeliveryPreference.dao;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.delivery.model.DeliveryPreference;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface DeliveryPreferenceMapper extends BaseMapper{
    @Delete({
        "delete from t_delivery_preference",
        "where preferenceId = #{preferenceId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long preferenceId);

    @Insert({
        "insert into t_delivery_preference (preferenceId, targetProviceId, ",
        "targetProviceName, preferenceStatus, ",
        "preferredWarehouseId, preferredWarehouseName, ",
        "createTime, lastUpdateTime)",
        "values (#{preferenceId,jdbcType=BIGINT}, #{targetProviceId,jdbcType=BIGINT}, ",
        "#{targetProviceName,jdbcType=VARCHAR}, #{preferenceStatus,jdbcType=TINYINT}, ",
        "#{preferredWarehouseId,jdbcType=INTEGER}, #{preferredWarehouseName,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP})"
    })
    int insert(DeliveryPreference record);

    @Select({
        "select",
        "preferenceId, targetProviceId, targetProviceName, preferenceStatus, preferredWarehouseId, ",
        "preferredWarehouseName, createTime, lastUpdateTime",
        "from t_delivery_preference",
        "where preferenceId = #{preferenceId,jdbcType=BIGINT}"
    })
    DeliveryPreference selectByPrimaryKey(Long preferenceId);

    @Update({
        "update t_delivery_preference",
        "set targetProviceId = #{targetProviceId,jdbcType=BIGINT},",
          "targetProviceName = #{targetProviceName,jdbcType=VARCHAR},",
          "preferenceStatus = #{preferenceStatus,jdbcType=TINYINT},",
          "preferredWarehouseId = #{preferredWarehouseId,jdbcType=INTEGER},",
          "preferredWarehouseName = #{preferredWarehouseName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where preferenceId = #{preferenceId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DeliveryPreference record);
}