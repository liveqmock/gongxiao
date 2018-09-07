package com.yhglobal.gongxiao.phjd.sales.dao.mapper.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;


/**
 * 销售订单地址SQL
 *
 * @author weizecheng
 * @date 2018/8/28 11:58
 */
public class SalesOrderAddressSqlProvider {

    /**
     * 更新销售地址相关
     *
     * @param prefix 表前缀
     * @param dataVersion 版本号
     * @param id 关联的销售订单号
     * @param receivingAddress 详细收货地址
     * @param receiverTel 收货电话
     * @param receiver 收货人姓名
     * @param arrived 最终抵达的省
     * @return SQL
     */
    public String updateSalesOrderAddress(@Param("prefix") String prefix,
                                          @Param("dataVersion")Long dataVersion,
                                          @Param("id") Long id,
                                          @Param("receivingAddress")String receivingAddress,
                                          @Param("receiverTel")String receiverTel,
                                          @Param("receiver")String receiver,
                                          @Param("arrived")String arrived){
        return new SQL() {{
            UPDATE(prefix+"_sales_order_address");
            if (StringUtils.isNotBlank(receivingAddress)) {
                SET("receivingAddress = #{receivingAddress}");
            }
            if (StringUtils.isNotBlank(receiverTel)) {
                SET("receiverTel = #{receiverTel}");
            }
            if (StringUtils.isNotBlank(receiver)) {
                SET("receiver = #{receiver}");
            }
            if (StringUtils.isNotBlank(arrived)) {
                SET("arrived = #{arrived}");
            }
            SET("dataVersion=dataVersion+1");
            WHERE("id = #{id} AND dataVersion = #{dataVersion}");

        }}.toString();
    }

}
