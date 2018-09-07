package com.yhglobal.gongxiao.foundation.area.dao.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;


public class DistributorShippingAddressProvider {

    public String selectNeedAuditAddressList(@Param("projectId") String projectId,
                                             @Param("distributorId") String distributorId,
                                             @Param("distributorName") String distributorName,
                                             @Param("auditStatus") int auditStatus) {
        return new SQL(){{
            SELECT("addressId, distributorId, distributorName,  receiver, provinceId, provinceName",
                    "cityId, cityName, districtId, districtName, streetAddress, phoneNumber, postCode ",
                    "auditStatus, createTime, lastUpdateTime, isDefaultAddress,tracelog");
            FROM("distributor_shipping_address");
            if (projectId!=null && !"".equals(projectId)){
                WHERE("projectId=#{projectId}");
            }
            if (auditStatus!=0){
                WHERE("auditStatus=#{auditStatus}");
            }
            if (distributorId!=null && !"".equals(distributorId)){
                WHERE("distributorId like '%' #{distributorId}'%' ");
            }
            if (distributorName!=null && !"".equals(distributorName)){
                WHERE("distributorName like concat('%', #{distributorName},'%')");
            }
        }}.toString();
    }
}
