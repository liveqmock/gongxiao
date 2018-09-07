package com.yhglobal.gongxiao.foundation.area.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.area.model.FoundationDistributorFileAddress;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FoundationDistributorFileAddressMapper extends BaseMapper {
    @Delete({
        "delete from foundation_distributor_file_address",
        "where addressId = #{addressId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long addressId);

    @Insert({
        "insert into foundation_distributor_file_address (addressId, recordStatus, ",
        "distributorId, distributorName, ",
        "receiver, provinceId, ",
        "provinceName, cityId, ",
        "cityName, districtId, ",
        "districtName, streetAddress, ",
        "phoneNumber, postCode, ",
        "isDefaultAddress, createTime, ",
        "lastUpdateTime, tracelog)",
        "values (#{addressId,jdbcType=BIGINT}, #{recordStatus,jdbcType=TINYINT}, ",
        "#{distributorId,jdbcType=BIGINT}, #{distributorName,jdbcType=VARCHAR}, ",
        "#{receiver,jdbcType=VARCHAR}, #{provinceId,jdbcType=INTEGER}, ",
        "#{provinceName,jdbcType=VARCHAR}, #{cityId,jdbcType=INTEGER}, ",
        "#{cityName,jdbcType=VARCHAR}, #{districtId,jdbcType=INTEGER}, ",
        "#{districtName,jdbcType=VARCHAR}, #{streetAddress,jdbcType=VARCHAR}, ",
        "#{phoneNumber,jdbcType=VARCHAR}, #{postCode,jdbcType=VARCHAR}, ",
        "#{isDefaultAddress,jdbcType=TINYINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(FoundationDistributorFileAddress record);

    @Select({
        "select",
        "addressId, recordStatus, distributorId, distributorName, receiver, provinceId, ",
        "provinceName, cityId, cityName, districtId, districtName, streetAddress, phoneNumber, ",
        "postCode, isDefaultAddress, createTime, lastUpdateTime, tracelog",
        "from foundation_distributor_file_address",
        "where addressId = #{addressId,jdbcType=BIGINT}"
    })
    FoundationDistributorFileAddress selectByPrimaryKey(Long addressId);

    @Select({
            "select",
            "addressId, recordStatus, distributorId, distributorName, receiver, provinceId, ",
            "provinceName, cityId, cityName, districtId, districtName, streetAddress, phoneNumber, ",
            "postCode, isDefaultAddress, createTime, lastUpdateTime, tracelog",
            "from foundation_distributor_file_address",
            "where addressId = #{addressId,jdbcType=BIGINT} and isDefaultAddress=1"
    })
    FoundationDistributorFileAddress getDefaultAddress(Long addressId);

    @Select({
            "select",
            "addressId, recordStatus, distributorId, distributorName, receiver, provinceId, ",
            "provinceName, cityId, cityName, districtId, districtName, streetAddress, phoneNumber, ",
            "postCode, isDefaultAddress, createTime, lastUpdateTime, tracelog",
            "from foundation_distributor_file_address",
    })
    List<FoundationDistributorFileAddress> getAllFileAddressList();

    @Update({
        "update foundation_distributor_file_address",
        "set recordStatus = #{recordStatus,jdbcType=TINYINT},",
          "distributorId = #{distributorId,jdbcType=BIGINT},",
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
          "isDefaultAddress = #{isDefaultAddress,jdbcType=TINYINT},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
        "where addressId = #{addressId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(FoundationDistributorFileAddress record);

    @Update({
        "update foundation_distributor_file_address",
        "set recordStatus = #{recordStatus,jdbcType=TINYINT},",
          "distributorId = #{distributorId,jdbcType=BIGINT},",
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
          "isDefaultAddress = #{isDefaultAddress,jdbcType=TINYINT},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where addressId = #{addressId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(FoundationDistributorFileAddress record);
}