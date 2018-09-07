package com.yhglobal.gongxiao.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 错误码定义
 */
public enum ErrorCode {

    /**
     * 错误码号段分配
     *     没有出错：0000
     *     系统错误：0001 - 0999
     *     业务错误：
     *         基础数据错误码：1100 - 1199
     *         库存模块错误码：1200 - 1299
     *         仓储模块错误码：1300 - 1399
     *         支付模块错误码：1400 - 1499
     *         用户模块错误码：1500 - 1599
     *         采购模块错误码：2000 - 2999
     *         销售模块错误码：3000 - 3999
     */


    /**
     * 成功
     */
    SUCCESS(0, "NO ERROR"),


    /********************************* 系统错误码 ********************************************/


    /**
     * 通用未知错误
     */
    UNKNOWN_ERROR(1, "未知系统异常"),


    /**
     * 参数不合法
     */
    ARGUMENTS_INVALID(2, "参数不合法"),

    /**
     * 没有操作权限
     */
    PERMISSION_DENY(3, "没有操作权限"),

    /**
     * 请求超时
     */
    REQUEST_TIMEOUT(4, "请求超时"),

    /**
     * 请求重复
     */
    DUPLICATED_REQUEST(5, "请求重复"),

    /**
     * UID错误
     */
    UID_INVALID(6, "UID错误"),

    /**
     * sessionId错误
     */
    SESSIONID_INVALID(7, "sessionId错误"),

    /**
     * rpcHeader错误
     */
    RPC_HEADER_INVALID(8, "rpcHeader错误"),

    /**
     * 本地方法调用异常
     */
    METHOD_INVOKE_EXCEPTION(9, "本地方法调用异常"),

    /**
     * RPC调用异常
     */
    RPC_INVOKE_EXCEPTION(10, "RPC服务调用异常"),

    /**
     * 数据库连接错误
     */
    DB_CONNECTION_FAILURE(20, "数据库连接异常"),

    /**
     * 数据库写入错误
     */
    DB_WRITE_FAILURE(21, "数据库insert异常"),

    /**
     * 数据库更新错误
     */
    DB_UPDATE_FAILURE(22, "数据库update异常"),
    /**
     * 数据版本不一致
     */
    DATE_VERSION_DIFFER(23, "数据版本不一致"),

    /********************************* 基础数据错误码 ********************************************/


    /********************************* 库存模块错误码 ********************************************/


    /********************************* 仓储模块错误码 ********************************************/


    /********************************* 支付模块错误码 ********************************************/
    DISTRIBUTOR_CASH_ACCOUNT_BALANCE_NOT_ENOUGH(1400, "经销商现金账户余额不足"),
    DISTRIBUTOR_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH(1401, "经销商返利账户余额不足"),
    DISTRIBUTOR_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH(1402, "经销商代垫账户余额不足"),
    COUPON_BUFFER_TO_DISTRIBUTOR_BALANCE_NOT_ENOUGH(1403, "经销商返利过账账户余额不足"),
    PREPAID_BUFFER_TO_DISTRIBUTOR_BALANCE_NOT_ENOUGH(1404, "经销商代垫过账账户余额不足"),
    SUPPLIER_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH(1405, "供应商返利上账账户余额不足"),
    SUPPLIER_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH(1406, "供应商代垫上账账户余额不足"),
    SELL_HIGH_ACCOUNT_BALANCE_NOT_ENOUGH(1407, "差价账户余额不足"),
    YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH(1408, "越海实收返利账户余额不足"),
    YHGLOBAL_RECEIVED_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH(1409, "越海实收代垫账户余额不足"),

    ACCOUNT_PREPAID_RECEIPT_NOT_SUFFICIENT_FUNDS(1411, "代垫实收账户余额不足"),
    ACCOUNT_PREPAID_CONFIRM_AMOUNT_EXCESS(1412, "确认金额超出未确认金额"),

    LEDGER_CAN_NOT_CANCEL_APPROVE(1430, "因为已经有销售单开始进行发货，台账无法取消审批"),

    CASH_CONFIRM_CAN_NOT_CANCEL(1440, "收款已经确认,无法取消"),

    BANK_FLOW_NUMBER_REPEATED(1450, "银行流水号重复"),

    CASH_DATA_ALREADY_MODIFIED(1460,"收款数据发生变更,请返回确认页面并刷新后,重新操作!"),

    COUPON_RECORD_CANCEL_REPEAT(1470,"返利确认记录不能重复取消"),


    /********************************* 用户模块错误码 ********************************************/
    USER_NOT_EXIST(1500, "用户不存在"), //用户不存在
    PASSWORD_NOT_MATCH(1501, "密码不匹配"), //密码不匹配
    USER_NOT_LOGIN(1503, "用户未登录"),

    /********************************* 采购模块错误码 ********************************************/
    NOT_FINDING_PURCHASE_ORDER(1900,"未找到采购单"),
    PURCHASE_ORDER_NOT_SYN_EAS(1901,"采购单未同步给EAS"),
    NUMBER_EXCEPTION(2000, "可售数量不能少于已分配的数量"),
    INBOUND_EXCEPTION(2001, "预约入库数量不得超过可入库数量"),
    SUPPLIER_ORDER_REPEAT(20010, "供应商订单已录入，无法导入"),
    SUPPLIER_ORDER_NOT_NULL(20011, "存在供应商订单号为空的情况，无法导入"),
    CONTRACT_ORDER_REPEAT(20020, "合同参考订单已录入，无法导入"),
    NOT_PRODUCT_CODE(20030, "型号货品没有维护，无法导入"),
    KEY_INFORMATION_IS_NULL(20040, "的关键信息缺失，无法导入"),
    ORDER_DATA_NOT_CONSISTENT(20050, "订单号数据不一致，无法导入"),
    COUPON_AMOUNT_NOT_ENOUGH(20060, "返利余额不足,无法导入"),
    PREPAID_AMOUNT_NOT_ENOUGH(20070, "代垫月不足,无法导入"),
    ORDER_ALREADY_KEY_IN(20080, "的订单已经录入到系统,无法再导入"),
    PROJECT_NOT_NULL(20090, "项目信息不能为空"),
    SUPPLIER_NOT_NULL(20010, "供应商不能为空"),
    WAREHOUSE_NOT_NULL(20020, "仓库信息不能为空"),
    BUSINESS_DATE_NOT_NULL(20030, "业务日期不能为空"),
    REQUIRE_ARRIVAL_DATE_NOT_NULL(20040, "要求到货时间不能为空"),
    ARRIVAL_DEAD_LINE(20041, "到货截止时间不能为空"),
    BRAND_ORDER_NOT_NULL(20050, "品牌商订单号不能为空"),
    CONTRACT_NOT_NULL(20060, "合同参考号不能为空"),
    PRODUCT_ADDITION(20070, "单的货补订单的代垫不能为0"),
    CAN_NOT_CANCLE_STATUS(20080,"不能取消已预约发货的通知单"),
    PLAN_NUMMBER_NOT_NULL(20090,"预约数量不能为空"),
    AD_VALUE_GREATER_ZERO(20100,"AD返利,代垫金额不得为负"),
    YH_VALUE_GREATER_ZERO(20110,"越海返利,代垫金额不得为负"),
    PHONE_FORMAT_FALSE(20120,"电话号码格式不正确"),
    INBOUND_ALREADY_COMPLETE(20130,"已入库完成,无需再入库"),

    /*************ad模块************/
    ORDER_CANCEL_FAILD(2201, "订单取消失败"),

    /********************************* 销售模块错误码 ********************************************/
    PRODUCT_REPEAT(2100, "有效期内已有该货品"),
    DATE_NOT_NULL(2110, "有效期不能为空"),
    DATE_OUT_EXPIRE(2120, "分配给客户的货品的有效期,超过货品实际有效期"),
    EXPIRE_DATE_CUSTOMER_REPEAT(2130, "同一有效期内,有多个客户"),
    NOT_SELECT_DATA(2140, "没有选择数据"),
    NOT_VALID_PLAN_SALE_ORDER(2150, "没有有效的预售信息"),
    OVERTAKE_MAX_BUY_NUMBER(2160, "超过最大可购买数量"),
    OVERTAKE_MAX_ALLOCATE_NUMBER(2161, "超过最大可分配数量"),
    OVERTAKE_SALE_OCCUPATION_NUMBER(2162,"变更已售数量超过销售占用"),
    ORDER_STATUS_CAN_NOT_MODIFY(2170, "订单状态无法修改"),
    QUANTITY_VALIDATE(2180, "可销总数不得少于已分配数量"),
    AT_LEAST_ONE(2190, "可销总数至少有1个"),
    SALE_ITEM_UP_ADJUST(2200, "客户预售单上调数量不得超过未处理货品的数量"),
    SALE_ITEM_DOWN_ADJUST(2210, "客户预售单下调数量不得少于客户目前未处理的货品总数"),
    NOT_REPEAT_START_END_DATE(2220,"结束日期必须后于开始日期"),
    CAN_RETURN_QUANTITY_SCANTY(2221,"可退货数量不足"),
    RETURN_WAREHOUSE_NULL(2222,"退货仓库不能为空"),
    SHIPPING_ADDRESS(2223,"收货地址不能为空"),

    EAS_ID_EMPTY(2224,"easId为null,销售单未获取到eas回执"),
    ;


    private int errorCode;

    private String message;

    ErrorCode(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    private volatile static Map<Integer, ErrorCode> mapping = null;

    public static ErrorCode getErrorCodeByCode(int code) {
        if (mapping == null) {
            synchronized (ErrorCode.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (ErrorCode errorCode : ErrorCode.values()) {
                        mapping.put(errorCode.errorCode, errorCode);
                    }
                }
            }
        }
        return mapping.get(code);
    }


    @Override
    public String toString() {
        return "{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }

}
