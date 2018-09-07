package com.yhglobal.gongxiao.warehouse.controller.report;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouse.model.PsiModel;
import com.yhglobal.gongxiao.warehouse.model.dto.ShaverReportPsiOverview;
import com.yhglobal.gongxiao.warehouse.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Controller
@RequestMapping("/report")
public class ReportController {

    private static Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    ReportService reportService;

    @RequestMapping("/getPSIData")
    @ResponseBody
    public GongxiaoResult getPSIData() {
        String traceId = "111";
        try {

            logger.info("#traceId={}# [IN][getPSIData] params:  ", traceId);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");

            //需传递的数据 数组是有序的
            PsiModel psiModel = new PsiModel();
            String[] time = psiModel.getTime();
            String[] inventoryAmount = psiModel.getInventoryAmount();
            String[] purchaseAmount = psiModel.getPurchaseAmount();
            String[] saleAmount = psiModel.getSaleAmount();
            String[] inventoryTurnoverRate = psiModel.getInventoryTurnoverRate();

            //获数据库取报表列表数据
            List<ShaverReportPsiOverview> shaverReportPsiOverviews = reportService.selectEffectiveReportList();
            Date date = new Date();
            int j = 0;
            for (int i = 6; i > 0; i--) {
                Long yearMonth = DateUtil.getBeforeYearMonth(date, i);
                time[j] = yearMonth.toString().substring(0, 4) + "年" + yearMonth.toString().substring(4, 6) + "月";
                boolean hasData = false;
                for (ShaverReportPsiOverview shaverReportPsiOverview : shaverReportPsiOverviews) {
                    if (yearMonth.toString().equals(shaverReportPsiOverview.getYearMonth().toString())) {
                        inventoryAmount[j] = DoubleScale.keepTwoBits(shaverReportPsiOverview.getInventoryAmount());
                        purchaseAmount[j] = DoubleScale.keepTwoBits(shaverReportPsiOverview.getPurchaseAmount());
                        saleAmount[j] = DoubleScale.keepTwoBits(shaverReportPsiOverview.getSaleAmount());
                        inventoryTurnoverRate[j] = DoubleScale.keepTwoBits(shaverReportPsiOverview.getInventoryTurnoverRate());
                        hasData = true;
                    }
                }
                //如果数据库没有指定月份的数据,给0
                if (!hasData) {
                    inventoryAmount[j] = "0.00";
                    purchaseAmount[j] = "0.00";
                    saleAmount[j] = "0.00";
                    inventoryTurnoverRate[j] = "0.00";
                }
                j++;
            }
            logger.info("#traceId={}# [OUT][getPSIData] params:  ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(),psiModel);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

}
