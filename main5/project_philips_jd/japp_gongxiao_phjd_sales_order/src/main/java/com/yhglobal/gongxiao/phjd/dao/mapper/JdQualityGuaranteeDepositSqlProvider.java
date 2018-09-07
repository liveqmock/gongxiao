package com.yhglobal.gongxiao.phjd.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author 王帅
 */
public class JdQualityGuaranteeDepositSqlProvider {

    public String selectByManyConditions(@Param("settlementNo") String settlementNo,
                                        @Param("documentCode") Integer documentCode,
                                        @Param("depositStatus") Integer depositStatus,
                                        @Param("jdProjectId") Long jdProjectId) {
        return new SQL() {{
            SELECT(
                    "depositId, depositStatus, documentCode, amount, documentTime, buyerName, projectId, "+
                    "projectName, supplierId, supplierName, settlementNo, createdById, createdByName, "+
                    "createTime, remark, jdProjectId");
            FROM("jd_quality_guarantee_deposit");
            if (!StringUtils.isEmpty(settlementNo)) {
                WHERE("settlementNo like '%' #{settlementNo} '%'");
            }
            if (jdProjectId != null) {
                WHERE("jdProjectId = #{jdProjectId}");
            }
            if (documentCode != null) {
                WHERE("documentCode = #{documentCode}");
            }
            if (depositStatus != null) {
                WHERE("depositStatus = #{depositStatus}");
            }
            ORDER_BY("createTime DESC");
        }}.toString();
    }
}
