package com.yhglobal.gongxiao.foundation.distributor.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorShippingPreference;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface DistributorShippingPreferenceMapper extends BaseMapper {
    @Delete({
        "delete from distributor_shipping_preference",
        "where rowId = #{rowId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String rowId);

    @Insert({
        "insert into distributor_shipping_preference ( distributorId, ",
        "distributorName, defaultAddressId, ",
        "lastSelectedAddressId, createTime, ",
        "lastUpdateTime)",
        "values ( #{distributorId,jdbcType=BIGINT}, ",
        "#{distributorName,jdbcType=VARCHAR}, #{defaultAddressId,jdbcType=INTEGER}, ",
        "#{lastSelectedAddressId,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP})"
    })
    int insert(DistributorShippingPreference record);

    @Select({
        "select",
        "rowId, distributorId, distributorName, defaultAddressId, lastSelectedAddressId, ",
        "createTime, lastUpdateTime",
        "from distributor_shipping_preference",
        "where rowId = #{rowId,jdbcType=VARCHAR}"
    })
    DistributorShippingPreference selectByPrimaryKey(String rowId);

    @Select({
            "select",
            "rowId, distributorId, distributorName, defaultAddressId, lastSelectedAddressId, ",
            "createTime, lastUpdateTime",
            "from distributor_shipping_preference",
            "where distributorId = #{distributorId}"
    })
    DistributorShippingPreference getPreferenceAddressById(String distributorId);

    @Update({
        "update distributor_shipping_preference",
        "set distributorId = #{distributorId,jdbcType=BIGINT},",
          "distributorName = #{distributorName,jdbcType=VARCHAR},",
          "defaultAddressId = #{defaultAddressId,jdbcType=INTEGER},",
          "lastSelectedAddressId = #{lastSelectedAddressId,jdbcType=INTEGER},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where rowId = #{rowId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(DistributorShippingPreference record);
}