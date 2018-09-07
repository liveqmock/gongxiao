package com.yhglobal.gongxiao.payment.util;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.util.DoubleScale;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author: 葛灿
 */
public class AccountProtoUtil {

    /**
     * 转换AccountAmount(Java) -> AccountAmount(proto)
     *
     * @param accountAmount java对象
     * @param respBuilder   proto对象builder
     * @return
     */
    public static void transAccountAmount(AccountAmount accountAmount, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.AccountAmountResponse.Builder respBuilder) {
        respBuilder.setCashAmount(accountAmount.getCashAmount());
        respBuilder.setCashAmountDouble(accountAmount.getCashAmountDouble());
        respBuilder.setCouponAmount(accountAmount.getCouponAmount());
        respBuilder.setCouponAmountDouble(accountAmount.getCouponAmountDouble());
        respBuilder.setPrepaidAmount(accountAmount.getPrepaidAmount());
        respBuilder.setPrepaidAmountDouble(accountAmount.getPrepaidAmountDouble());
    }

    public static AccountAmount getJavaAccountAmount(PaymentCommonGrpc.AccountAmountResponse protoModel) {
        AccountAmount javaModel = new AccountAmount();
        javaModel.setCashAmount(protoModel.getCashAmount());
        javaModel.setCashAmountDouble(protoModel.getCashAmountDouble());
        javaModel.setCouponAmount(protoModel.getCouponAmount());
        javaModel.setCouponAmountDouble(protoModel.getCouponAmountDouble());
        javaModel.setPrepaidAmount(protoModel.getPrepaidAmount());
        javaModel.setPrepaidAmountDouble(protoModel.getPrepaidAmountDouble());
        return javaModel;
    }

    /**
     * 转换PageInfo<FrontPageFlow>(Java) -> FrontPageFlow(proto)
     *
     * @param pageInfo java对象
     * @param builder  proto对象builder
     * @return
     */
    public static void transFrontPageFlow(PageInfo<FrontPageFlow> pageInfo, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowPageInfo.Builder builder) {
        builder.setPageNum(pageInfo.getPageNum());
        builder.setPageSize(pageInfo.getPageSize());
        builder.setTotal(pageInfo.getTotal());
        if (pageInfo.getList() != null) {
            for (FrontPageFlow javaObject : pageInfo.getList()) {
                com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlow.Builder protoObject = com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlow.newBuilder()
                        .setFlowNo(javaObject.getFlowNo())
                        .setType(javaObject.getType())
                        .setCurrencyCode(javaObject.getCurrencyCode())
                        .setTransactionAmount(javaObject.getTransactionAmount())
                        .setAmountAfterTransaction(javaObject.getAmountAfterTransaction())
                        .setCreateTime(javaObject.getCreateTime().getTime())
                        .setCreateByName(javaObject.getCreateByName());
                builder.addFlows(protoObject);
            }
        }
    }

    public static PageInfo<FrontPageFlow> getJavaPageFlow(PaymentCommonGrpc.FrontPageFlowPageInfo protoPageInfo) {
        PageInfo<FrontPageFlow> javaPageInfo = new PageInfo<>();
        javaPageInfo.setTotal(protoPageInfo.getTotal());
        javaPageInfo.setPageNum(protoPageInfo.getPageNum());
        javaPageInfo.setPageSize(protoPageInfo.getPageSize());
        ArrayList<FrontPageFlow> list = new ArrayList<>();
        javaPageInfo.setList(list);
        for (PaymentCommonGrpc.FrontPageFlow protoModel : protoPageInfo.getFlowsList()) {
            FrontPageFlow javaModel = new FrontPageFlow();
            javaModel.setFlowNo(protoModel.getFlowNo());
            javaModel.setType(protoModel.getType());
            javaModel.setCurrencyCode(protoModel.getCurrencyCode());
            javaModel.setTransactionAmount(protoModel.getTransactionAmount());
            javaModel.setAmountAfterTransaction(protoModel.getAmountAfterTransaction());
            javaModel.setCreateTime(new Date(protoModel.getCreateTime()));
            javaModel.setCreateByName(protoModel.getCreateByName());
            list.add(javaModel);
        }
        return javaPageInfo;
    }


    public static FrontPageFlowSubtotal getJavaPageSubtotal(PaymentCommonGrpc.FrontPageFlowSubtotal protoModel) {
        FrontPageFlowSubtotal javaModel = new FrontPageFlowSubtotal();
        javaModel.setExpenditureAmount(protoModel.getExpenditureAmount());
        javaModel.setExpenditureQuantity(protoModel.getExpenditureQuantity());
        javaModel.setIncomeAmount(protoModel.getIncomeAmount());
        javaModel.setIncomeQuantity(protoModel.getIncomeQuantity());
        return javaModel;
    }


    /**
     * 转换FrontPageSubtotal(Java) -> FrontPageSubtotal(proto)
     *
     * @param subtotal java对象
     * @param builder  proto对象builder
     * @return
     */
    public static void transFrontPageSubtotal(FrontPageFlowSubtotal subtotal, com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc.FrontPageFlowSubtotal.Builder builder) {
        builder.setIncomeQuantity(subtotal.getIncomeQuantity());
        builder.setExpenditureQuantity(subtotal.getExpenditureQuantity());
        builder.setIncomeAmount(subtotal.getIncomeAmount());
        builder.setExpenditureAmount(subtotal.getExpenditureAmount());
    }
}
