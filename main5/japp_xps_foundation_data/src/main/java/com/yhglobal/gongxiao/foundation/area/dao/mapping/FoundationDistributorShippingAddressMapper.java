package com.yhglobal.gongxiao.foundation.area.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.area.dao.provider.ShippingAddressProvider;
import com.yhglobal.gongxiao.foundation.area.model.FoundationDistributorShippingAddress;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface FoundationDistributorShippingAddressMapper extends BaseMapper {
    @Delete({
            "delete from foundation_distributor_shipping_address",
            "where addressId = #{addressId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long addressId);

    @Insert({
            "insert into foundation_distributor_shipping_address (addressId, distributorId, ",
            "distributorName, receiver, ",
            "provinceId, provinceName, ",
            "cityId, cityName, ",
            "districtId, districtName, ",
            "streetAddress, phoneNumber, ",
            "postCode, auditStatus, ",
            "isDefaultAddress, createTime, ",
            "lastUpdateTime, tracelog)",
            "values (#{addressId,jdbcType=BIGINT}, #{distributorId,jdbcType=BIGINT}, ",
            "#{distributorName,jdbcType=VARCHAR}, #{receiver,jdbcType=VARCHAR}, ",
            "#{provinceId,jdbcType=INTEGER}, #{provinceName,jdbcType=VARCHAR}, ",
            "#{cityId,jdbcType=INTEGER}, #{cityName,jdbcType=VARCHAR}, ",
            "#{districtId,jdbcType=INTEGER}, #{districtName,jdbcType=VARCHAR}, ",
            "#{streetAddress,jdbcType=VARCHAR}, #{phoneNumber,jdbcType=VARCHAR}, ",
            "#{postCode,jdbcType=VARCHAR}, #{auditStatus}, ",
            "#{isDefaultAddress,jdbcType=TINYINT}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(FoundationDistributorShippingAddress record);

    @Select({
            "select",
            "addressId, distributorId, distributorName, receiver, provinceId, provinceName, ",
            "cityId, cityName, districtId, districtName, streetAddress, phoneNumber, postCode, ",
            "auditStatus, isDefaultAddress, createTime, lastUpdateTime, tracelog",
            "from foundation_distributor_shipping_address",
            "where addressId = #{addressId,jdbcType=BIGINT}"
    })
    FoundationDistributorShippingAddress selectByPrimaryKey(Long addressId);

    @Select({
            "select",
            "addressId, distributorId, distributorName, receiver, provinceId, provinceName, ",
            "cityId, cityName, districtId, districtName, streetAddress, phoneNumber, postCode, ",
            "auditStatus, isDefaultAddress, createTime, lastUpdateTime, tracelog",
            "from foundation_distributor_shipping_address",
            "where distributorId = #{distributorId,jdbcType=BIGINT} and isDefaultAddress=1"
    })
    FoundationDistributorShippingAddress getDefaultAddress(Long distributorId);

    @Select({
            "select",
            "addressId, distributorId, distributorName, receiver, provinceId, provinceName, ",
            "cityId, cityName, districtId, districtName, streetAddress, phoneNumber, postCode, ",
            "auditStatus, isDefaultAddress, createTime, lastUpdateTime, tracelog",
            "from foundation_distributor_shipping_address",
            "where addressId = #{addressId,jdbcType=BIGINT}"
    })
    FoundationDistributorShippingAddress getAddressByAddressId(Long addressId);

    @Select({
            "select",
            "addressId, distributorId, distributorName, receiver, provinceId, provinceName, ",
            "cityId, cityName, districtId, districtName, streetAddress, phoneNumber, postCode, ",
            "auditStatus, isDefaultAddress, createTime, lastUpdateTime, tracelog",
            "from foundation_distributor_shipping_address",
            "where distributorID = #{distributorID,jdbcType=BIGINT} and auditStatus=3"
    })
    List<FoundationDistributorShippingAddress> selectAuditedAddressListByDistributor(Long distributorID);

    @Select({
            "select",
            "addressId, distributorId, distributorName, receiver, provinceId, provinceName, ",
            "cityId, cityName, districtId, districtName, streetAddress, phoneNumber, postCode, ",
            "auditStatus, isDefaultAddress, createTime, lastUpdateTime, tracelog",
            "from foundation_distributor_shipping_address",
            "where distributorId = #{distributorId} and auditStatus!=9"
    })
    List<FoundationDistributorShippingAddress> selectShippingAddressByDistributorId(Long distributorId);

    @SelectProvider(type = ShippingAddressProvider.class, method = "selectShippingAddressList")
    List<FoundationDistributorShippingAddress> selectCustomerSalePlanList(@Param("distributorId") long distributorId,
                                                                          @Param("distributorName") String distributorName,
                                                                          @Param("auditStatus") int auditStatus);


    @Update({
            "update foundation_distributor_shipping_address",
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
            "auditStatus = #{auditStatus},",
            "isDefaultAddress = #{isDefaultAddress,jdbcType=TINYINT},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where addressId = #{addressId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(FoundationDistributorShippingAddress record);

    @Update({
            "update foundation_distributor_shipping_address",
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
            "auditStatus = #{auditStatus},",
            "isDefaultAddress = #{isDefaultAddress,jdbcType=TINYINT},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
            "where addressId = #{addressId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(FoundationDistributorShippingAddress record);

    @Update({
            "update foundation_distributor_shipping_address",
            "set  ",
            "auditStatus = #{auditStatus},",
            "lastUpdateTime = NOW()",
            "where addressId = #{addressId}"
    })
    int updateShippingAddressStatus( @Param("addressId")long addressId, @Param("auditStatus") int auditStatus);

    @Update({
            "update foundation_distributor_shipping_address",
            "set  ",
            "isDefaultAddress = #{isDefaultAddress,jdbcType=TINYINT},",
            "lastUpdateTime = NOW()",
            "where addressId = #{addressId}"
    })
    int setDefaultAddress(@Param("addressId") long addressId,@Param("isDefaultAddress") byte isDefaultAddress);
}