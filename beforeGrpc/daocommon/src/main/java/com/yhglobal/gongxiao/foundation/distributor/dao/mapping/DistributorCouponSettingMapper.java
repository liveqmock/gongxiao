package com.yhglobal.gongxiao.foundation.distributor.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorCouponSetting;
import org.apache.ibatis.annotations.*;

public interface DistributorCouponSettingMapper extends BaseMapper{
    @Delete({
        "delete from t_distributor_coupon_setting",
        "where rowId = #{rowId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer rowId);

    @Insert({
        "insert into t_distributor_coupon_setting (rowId, status, ",
        "projectId, projectName, ",
        "distributorId, distributorName, ",
        "prepaidReferenceRate, prepaidCouponReferenceRate, ",
        "createTime)",
        "values (#{rowId,jdbcType=INTEGER}, #{status,jdbcType=TINYINT}, ",
        "#{projectId,jdbcType=VARCHAR}, #{projectName,jdbcType=VARCHAR}, ",
        "#{distributorId,jdbcType=INTEGER}, #{distributorName,jdbcType=VARCHAR}, ",
        "#{prepaidReferenceRate,jdbcType=INTEGER}, #{prepaidCouponReferenceRate,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(DistributorCouponSetting record);


    @Select({
        "select",
        "rowId, status, projectId, projectName, distributorId, distributorName, prepaidReferenceRate, ",
        "prepaidCouponReferenceRate, createTime",
        "from t_distributor_coupon_setting",
        "where rowId = #{rowId,jdbcType=INTEGER}"
    })
    DistributorCouponSetting selectByPrimaryKey(Integer rowId);

    @Select({
            "select",
            "rowId, status, projectId, projectName, distributorId, distributorName, prepaidReferenceRate, ",
            "prepaidCouponReferenceRate, createTime",
            "from t_distributor_coupon_setting",
            "where projectId = #{projectId} and distributorId = #{distributorId} "
    })
    DistributorCouponSetting getDistributorCoupon(@Param("projectId") String projectId, @Param("distributorId")String distributorId);

    @Update({
        "update t_distributor_coupon_setting",
        "set status = #{status,jdbcType=TINYINT},",
          "projectId = #{projectId,jdbcType=VARCHAR},",
          "projectName = #{projectName,jdbcType=VARCHAR},",
          "distributorId = #{distributorId,jdbcType=INTEGER},",
          "distributorName = #{distributorName,jdbcType=VARCHAR},",
          "prepaidReferenceRate = #{prepaidReferenceRate,jdbcType=INTEGER},",
          "prepaidCouponReferenceRate = #{prepaidCouponReferenceRate,jdbcType=INTEGER},",
          "createTime = #{createTime,jdbcType=TIMESTAMP}",
        "where rowId = #{rowId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(DistributorCouponSetting record);
}