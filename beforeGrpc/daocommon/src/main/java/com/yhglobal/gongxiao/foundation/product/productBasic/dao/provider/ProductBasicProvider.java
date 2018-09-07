package com.yhglobal.gongxiao.foundation.product.productBasic.dao.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 9:25 2018/4/12
 */
public class ProductBasicProvider {
    public String getList(@Param("queryStr") String queryStr) {
        return new SQL() {{
            SELECT("id, productName, productCode");
            FROM("t_product_basic");
            if (queryStr != null && !"".equals(queryStr)) {
                WHERE("productName like '%' #{queryStr} '%' or productCode  like '%' #{queryStr} '%' ");
            }

        }}.toString();
    }


    public String selectProductByCondition(@Param("projectId") Long projectId,
                                           @Param("productCode") String productCode,
                                           @Param("productName") String productName) {
        return new SQL() {{
            SELECT("id, productName, status, productCode, productModel, easCode, easName, easModel ",
                    "p12NC, easMaterialCode, guidePrice,saleGuidePrice, listedPrice, barCode, productType, brandId ",
                    "brandName, categoryId, categoryName, productLine, productLength, productWidth ",
                    "productHeight, productNetWeight, productGrossWeight, boxLength, boxWidth, boxHeight ",
                    "boxWeight, guaranteePeriod, packageNumber, createTime, lastUpdateTime");
            FROM("t_product_basic");
//            if (projectId != null && !"".equals(projectId)) {
//                WHERE("projectId = #{projectId}");
//            }
            if (productCode != null && !"".equals(productCode)) {
                WHERE("productCode  like '%' #{productCode} '%'");
            }
            if (productName != null && !"".equals(productName)) {
                WHERE("productName  like '%' #{productName} '%'");
            }

        }}.toString();
    }
}
