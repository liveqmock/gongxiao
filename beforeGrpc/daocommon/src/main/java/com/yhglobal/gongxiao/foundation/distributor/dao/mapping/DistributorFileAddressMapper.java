package com.yhglobal.gongxiao.foundation.distributor.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorFileAddress;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DistributorFileAddressMapper extends BaseMapper{
    @Delete({
        "delete from distributor_file_address",
        "where addressId = #{addressId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String addressId);


    @Insert({
            "insert into distributor_file_address (addressId, distributorId, ",
            "distributorName, status, ",
            "receiver, provinceId, ",
            "provinceName, cityId, ",
            "cityName, districtId, ",
            "districtName, streetAddress, ",
            "phoneNumber, postCode, ",
            "createTime, lastUpdateTime, ",
            "tracelog)",
            "values (#{addressId,jdbcType=VARCHAR}, #{distributorId,jdbcType=BIGINT}, ",
            "#{distributorName,jdbcType=VARCHAR}, #{status,jdbcType=TINYINT}, ",
            "#{receiver,jdbcType=VARCHAR}, #{provinceId,jdbcType=INTEGER}, ",
            "#{provinceName,jdbcType=VARCHAR}, #{cityId,jdbcType=INTEGER}, ",
            "#{cityName,jdbcType=VARCHAR}, #{districtId,jdbcType=INTEGER}, ",
            "#{districtName,jdbcType=VARCHAR}, #{streetAddress,jdbcType=VARCHAR}, ",
            "#{phoneNumber,jdbcType=VARCHAR}, #{postCode,jdbcType=VARCHAR}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(DistributorFileAddress record);

    @Select({
        "select",
        "addressId, distributorId, distributorName, status, receiver, provinceId, provinceName, ",
        "cityId, cityName, districtId, districtName, streetAddress, phoneNumber, postCode, ",
        "createTime, lastUpdateTime, tracelog",
        "from distributor_file_address",
        "where addressId = #{addressId,jdbcType=VARCHAR}"
    })
    DistributorFileAddress selectByPrimaryKey(String addressId);


    @Select({
            "select",
            "addressId, distributorId, distributorName, status, receiver, provinceId, provinceName, ",
            "cityId, cityName, districtId, districtName, streetAddress, phoneNumber, postCode, ",
            "createTime, lastUpdateTime, tracelog",
            "from distributor_file_address",
            "where distributorId = #{distributorId} and distributorName=#{distributorName}"
    })
    List<DistributorFileAddress> selectByIdAndName(@Param("distributorId") Long distributorId,
                                                   @Param("distributorName") String distributorName);

    @Update({
        "update distributor_file_address",
        "set distributorId = #{distributorId,jdbcType=BIGINT},",
          "distributorName = #{distributorName,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT},",
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
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
        "where addressId = #{addressId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKeyWithBLOBs(DistributorFileAddress record);

    @Update({
        "update distributor_file_address",
        "set distributorId = #{distributorId,jdbcType=BIGINT},",
          "distributorName = #{distributorName,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT},",
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
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where addressId = #{addressId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(DistributorFileAddress record);
}