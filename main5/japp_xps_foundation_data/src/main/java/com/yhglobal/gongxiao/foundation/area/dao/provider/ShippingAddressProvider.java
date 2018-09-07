package com.yhglobal.gongxiao.foundation.area.dao.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * 销售计划
 *
 * @Description:
 * @author: LUOYI
 * @Date: Created in 9:45 2018/4/17
 */
public class ShippingAddressProvider {

    public String selectShippingAddressList(@Param("distributorId") long distributorId,
                                            @Param("distributorName") String distributorName,
                                            @Param("auditStatus") int auditStatus){
        return new SQL(){{
            SELECT( "addressId, distributorId, distributorName, receiver, provinceId, provinceName ",
                    "cityId, cityName, districtId, districtName, streetAddress, phoneNumber, postCode ",
                    "auditStatus, isDefaultAddress, createTime, lastUpdateTime, tracelog");
            FROM("foundation_distributor_shipping_address");
            if (distributorId!=0){
                WHERE("distributorId =  #{distributorId} ");
            }
            if (distributorName!=null && !"".equals(distributorName)){
                WHERE("distributorName like concat('%', #{distributorName},'%')");
            }
            if (auditStatus!=0 ){
                WHERE("auditStatus = #{auditStatus}");
            }
        }}.toString();
    }
}
