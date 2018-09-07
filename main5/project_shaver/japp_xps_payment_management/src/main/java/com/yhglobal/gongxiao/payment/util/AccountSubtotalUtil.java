package com.yhglobal.gongxiao.payment.util;

import com.yhglobal.gongxiao.constant.YhGlobalInoutFlowConstant;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;

/**
 * 账户流水小计 从数据库=》前台 数据模型
 *
 * @author: 葛灿
 */
public class AccountSubtotalUtil {

    private static Logger logger = LoggerFactory.getLogger(AccountSubtotalUtil.class);

    public static FrontPageFlowSubtotal getSubtotal(List<FlowSubtotal> flowSubtotals) {
        FrontPageFlowSubtotal frontPageFlowSubtotal = new FrontPageFlowSubtotal();
        for (FlowSubtotal flowSubtotal : flowSubtotals) {
            if (YhGlobalInoutFlowConstant.FLOW_TYPE_IN.getNum() == flowSubtotal.getRecordType()) {
                frontPageFlowSubtotal.setIncomeQuantity(flowSubtotal.getCount());
                frontPageFlowSubtotal.setIncomeAmount(1.0 * flowSubtotal.getAmountCount() / HUNDRED);
            } else if (YhGlobalInoutFlowConstant.FLOW_TYPE_OUT.getNum() == flowSubtotal.getRecordType()) {
                frontPageFlowSubtotal.setExpenditureQuantity(flowSubtotal.getCount());
                frontPageFlowSubtotal.setExpenditureAmount(1.0 * flowSubtotal.getAmountCount() / HUNDRED);
            }
        }
        return frontPageFlowSubtotal;
    }

    public static void main(String[] args) {
        System.out.println(4.015*100);
    }
}
