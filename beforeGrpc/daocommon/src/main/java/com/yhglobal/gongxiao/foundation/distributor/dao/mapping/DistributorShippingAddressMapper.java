package com.yhglobal.gongxiao.foundation.distributor.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.distributor.dao.provider.DistributorShippingAddressProvider;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorShippingAddress;
import com.yhglobal.gongxiao.sales.dao.mapping.SalesPlanSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DistributorShippingAddressMapper extends BaseMapper{
    @Delete({
        "delete from distributor_shipping_address",
        "where addressId = #{addressId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String addressId);

    @Insert({
        "insert into distributor_shipping_address (addressId, distributorId, ",
        "distributorName,  ",
        "receiver, provinceId, ",
        "provinceName, cityId, ",
        "cityName, districtId, ",
        "districtName, streetAddress, ",
        "phoneNumber, postCode, ",
        "auditStatus, createTime, ",
        "lastUpdateTime,isDefaultAddress, tracelog)",
        "values (#{addressId}, #{distributorId,jdbcType=BIGINT}, ",
        "#{distributorName,jdbcType=VARCHAR}, ",
        "#{receiver,jdbcType=VARCHAR}, #{provinceId,jdbcType=INTEGER}, ",
        "#{provinceName,jdbcType=VARCHAR}, #{cityId,jdbcType=INTEGER}, ",
        "#{cityName,jdbcType=VARCHAR}, #{districtId,jdbcType=INTEGER}, ",
        "#{districtName,jdbcType=VARCHAR}, #{streetAddress,jdbcType=VARCHAR}, ",
        "#{phoneNumber,jdbcType=VARCHAR}, #{postCode,jdbcType=VARCHAR}, ",
        "#{auditStatus,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{isDefaultAddress},#{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(DistributorShippingAddress record);


    @Select({
        "select",
        "addressId, distributorId, distributorName,  receiver, provinceId, provinceName, ",
        "cityId, cityName, districtId, districtName, streetAddress, phoneNumber, postCode, ",
        "auditStatus, createTime, lastUpdateTime,isDefaultAddress, tracelog",
        "from distributor_shipping_address",
        "where addressId = #{addressId}"
    })
    DistributorShippingAddress getByAddressId(String addressId);

    @Select({
            "select",
            "addressId, distributorId, distributorName,  receiver, provinceId, provinceName, ",
            "cityId, cityName, districtId, districtName, streetAddress, phoneNumber, postCode, ",
            "auditStatus, createTime, lastUpdateTime, isDefaultAddress,tracelog",
            "from distributor_shipping_address",
            "where distributorId = #{distributorId} and (auditStatus=2 ||auditStatus=1 ) order by createTime DESC"
    })
    List<DistributorShippingAddress> selectAddressListByAD(String distributorId);

    @Select({
            "select",
            "addressId, distributorId, distributorName,  receiver, provinceId, provinceName, ",
            "cityId, cityName, districtId, districtName, streetAddress, phoneNumber, postCode, ",
            "auditStatus, createTime, lastUpdateTime, isDefaultAddress,tracelog",
            "from distributor_shipping_address",
            "where distributorId = #{distributorId} and auditStatus=2 order by createTime DESC"
    })
    List<DistributorShippingAddress> selectValidAddressListById(String distributorId);



    @SelectProvider(type = DistributorShippingAddressProvider.class, method = "selectNeedAuditAddressList")
    List<DistributorShippingAddress> selectNeedAuditAddressList(@Param("projectId") String projectId,
                                                                @Param("distributorId") String distributorId,
                                                                @Param("distributorName") String distributorName,
                                                                @Param("auditStatus") int auditStatus);

    @Select({
            "select",
            "addressId, distributorId, distributorName,  receiver, provinceId, provinceName, ",
            "cityId, cityName, districtId, districtName, streetAddress, phoneNumber, postCode, ",
            "auditStatus, createTime, lastUpdateTime, isDefaultAddress,tracelog",
            "from distributor_shipping_address",
            "where distributorId = #{distributorId} and distributorName=#{distributorName}"
    })
    DistributorShippingAddress selectByIdAndName(@Param("distributorId") Long distributorId,
                                                     @Param("distributorName") String distributorName);


    @Update({
        "update distributor_shipping_address",
        "set distributorId = #{distributorId,jdbcType=BIGINT},",
          "distributorName = #{distributorName,jdbcType=VARCHAR},",
          "receiver = #{receiver,jdbcType=VARCHAR},",
          "provinceId = #{provinceId,jdbcType=INTEGER},",
          "provinceName = #{provinceName,jdbcType=VARCHAR},",
          "cityId = #{cityId,jdbcType=INTEGER},",
          "cityName = #{cityName,jdbcType=VARCHAR},",
          "districtId = #{districtId,jdbcType=INTEGER},",
          "districtName = #{districtName,jdbcType=VARCHAR},",
          "streetAddress = #{streetAddress,jdbcType=VARCHAR},",
          "phoneNumber = #{phoneNumber,jdbcType=VARCHAR},",
          "postCode = #{postCode,jdbcType=VARCHAR},",
          "auditStatus = #{auditStatus,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
        "where addressId = #{addressId}"
    })
    int updateByPrimaryKeyWithBLOBs(DistributorShippingAddress record);

    @Update({
        "update distributor_shipping_address",
        "set distributorId = #{distributorId,jdbcType=BIGINT},",
          "distributorName = #{distributorName,jdbcType=VARCHAR},",
          "receiver = #{receiver,jdbcType=VARCHAR},",
          "provinceId = #{provinceId,jdbcType=INTEGER},",
          "provinceName = #{provinceName,jdbcType=VARCHAR},",
          "cityId = #{cityId,jdbcType=INTEGER},",
          "cityName = #{cityName,jdbcType=VARCHAR},",
          "districtId = #{districtId,jdbcType=INTEGER},",
          "districtName = #{districtName,jdbcType=VARCHAR},",
          "streetAddress = #{streetAddress,jdbcType=VARCHAR},",
          "phoneNumber = #{phoneNumber,jdbcType=VARCHAR},",
          "postCode = #{postCode,jdbcType=VARCHAR},",
          "auditStatus = #{auditStatus,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where addressId = #{addressId}"
    })
    int updateByPrimaryKey(DistributorShippingAddress record);

    @Update({
            "update distributor_shipping_address",
            "set auditStatus = 9,",
            "lastUpdateTime = NOW()",
            "where addressId = #{addressId}"
    })
    int cancelAddress(@Param("addressId") int addressId);

    @Update({
            "update distributor_shipping_address",
            "set auditStatus = 2,",
            "lastUpdateTime = NOW()",
            "where addressId = #{addressId}"
    })
    int auditAddress(@Param("addressId")int addressId);

    @Update({
            "update distributor_shipping_address",
            "set auditStatus = 3,",
            "lastUpdateTime = NOW()",
            "where addressId = #{addressId}"
    })
    int rejectAddress(@Param("addressId")int addressId);

}