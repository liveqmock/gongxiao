package com.yhglobal.gongxiao.constant;



/**
 * 定义越海三个流水表的表名 在调用dao层方法时传入表名即可操作对应的表
 * @author  王帅
 */
public enum YhGlobalInoutFlowTableNameConstant {

    YH_COUPON_INOUT_FLOW("yhglobal_coupon_inout_flow", "越海返利流水表"),

    YH_CASH_INOUT_FLOW("yhglobal_cash_inout_flow", "越海返利流水表"),

    YH_PREPAID_INOUT_FLOW("yhglobal_prepaid_inout_flow", "越海返利流水表");

    String tableName;

    String meaning;

    YhGlobalInoutFlowTableNameConstant(String tableName, String meaning) {
        this.tableName = tableName;
        this.meaning = meaning;
    }

    public String getTableName() {
        return tableName;
    }
}
