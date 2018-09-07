package com.yhglobal.gongxiao.foundation.product.dao.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 9:25 2018/4/12
 */
public class ProductProvider {

    public String selectBusinessByCondition(@Param("projectTablePrefix") String projectTablePrefix,
                                            @Param("productCode") String productCode,
                                            @Param("productName") String productName,
                                            @Param("productEasCode") String productEasCode,
                                            @Param("recordStatus") int recordStatus) {
        return new SQL() {{
            SELECT("productBusinessId, productBasicId, projectId, projectName, recordStatus, easSynStatus ",
                    "productName, productCode, easCode,easUnitCode, wmsCode, customerSKUCode, customerProductCode ",
                    "purchaseGuidePrice, taxRate, taxCode, saleGuidePrice, actualSaleReturn, shouldSaleReturn ",
                    "factorySendReturn, costPrice, outPrice, generateCoupon, createTime, lastUpdateTime ",
                    "traceLog");
            FROM(projectTablePrefix+"_product_business");
            if (productEasCode != null && !"".equals(productEasCode)) {
                WHERE("easCode  like concat('%', #{productEasCode},'%')");
            }
            if (productCode != null && !"".equals(productCode)) {
                WHERE("productCode like concat('%', #{productCode},'%')");
            }
            if (productName != null && !"".equals(productName)) {
                WHERE("productName like concat('%', #{productName},'%')");
            }
            if (recordStatus != 0) {
                WHERE("recordStatus = #{recordStatus}");
            }
        }}.toString();
    }
}
