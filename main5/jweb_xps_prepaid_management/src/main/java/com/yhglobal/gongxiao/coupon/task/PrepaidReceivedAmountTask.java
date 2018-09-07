package com.yhglobal.gongxiao.coupon.task;

import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.coupon.config.PortalConfig;
import com.yhglobal.gongxiao.coupon.dao.PrepaidReceivedAmountReportDao;
import com.yhglobal.gongxiao.coupon.dao.YhglobalToReceivePrepaidLedgerDao;
import com.yhglobal.gongxiao.coupon.model.FirstAndLastDayOfMonth;
import com.yhglobal.gongxiao.coupon.model.PrepaidReceivedAmountReport;
import com.yhglobal.gongxiao.coupon.model.YhglobalToReceivePrepaidLedger;
import com.yhglobal.gongxiao.coupon.utils.GetFirstAndLastDayOfMonthUtil;
import com.yhglobal.gongxiao.coupon.utils.GetPastSixMonthUtil;
import com.yhglobal.gongxiao.coupon.utils.GetRateUtil;
import com.yhglobal.gongxiao.coupon.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * 代垫报表2 近半年代垫实收及应收金额统计
 * @author 王帅
 */
@Component
@EnableScheduling
public class PrepaidReceivedAmountTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    YhglobalToReceivePrepaidLedgerDao yhglobalToReceivePrepaidLedgerDao;

    @Autowired
    PrepaidReceivedAmountReportDao prepaidReceivedAmountReportDao;

    /**
     * 产生代垫未收金额报表数据
     * 每天中午12点触发
     */
    @Scheduled(cron = "0 0 1 * * ?")
    //@Scheduled(fixedDelay = 50000)  //测试使用
    public void get() {

            logger.info("task start");
           // String traceId= YhPortalTraceIdGenerator.genTraceId(userId, TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            String traceId = "prepaidReceivedAmountScheduledTask";
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "admin", "admin123");
            ProjectStructure.SelectProjectListReq.Builder selectProjectListReq
                    = ProjectStructure.SelectProjectListReq.newBuilder().setRpcHeader(rpcHeader);
            ProjectServiceGrpc.ProjectServiceBlockingStub blockingStub = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.SelectProjectListResp selectProjectListResp=null;
            try {
                logger.info("#traceId={}# [ON][getProjectList] start get project list ", rpcHeader.getTraceId());
                selectProjectListResp=blockingStub.selectProjectList(selectProjectListReq.build());
                logger.info("#traceId={}# [ON][getProjectList] get project list end ", rpcHeader.getTraceId());
            }catch (Exception e){
                e.printStackTrace();
            }
            List<ProjectStructure.Project> projectListList = selectProjectListResp.getProjectListList();
            // 遍历所有项目ID 更新所有项目代垫报表数据
            for (int j = 0;j<projectListList.size();j++) {
                long projectId = projectListList.get(j).getProjectId();
                String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId); // 获取表前缀
                // 先清空数据库数据 然后统计数据并插入
                try {
                    // 如果该项目ID对应的数据表不存在 则会在此处抛出表不存在的异常 此时跳过此次循环
                    prepaidReceivedAmountReportDao.deleteAllData(tablePrefix);
                }catch (Exception e){
                    continue;
                }
                // 需求点：显示以往半年的每个月应收代垫产生多少金额（蓝色柱状），实际回收多少（红色柱状），回收率（曲线，回收金额/应收金额）。
                List<Date> sixMonthDate = GetPastSixMonthUtil.getSixMonthDate();
                //  查询这六个月的数据
                for (int i = 0; i < sixMonthDate.size(); i++) {
                    String dateStr = DateUtil.dateToString(sixMonthDate.get(i)) + "-01"; // 设置正确的日期格式 初始为 yyyy-MM-01
                    FirstAndLastDayOfMonth day = GetFirstAndLastDayOfMonthUtil.getDay(dateStr);
                    Date dateStart = day.getFirtDate();
                    Date dateEnd = day.getLastDate();
                    YhglobalToReceivePrepaidLedger count = yhglobalToReceivePrepaidLedgerDao.getDataCountByTime(tablePrefix, projectId, dateStart, dateEnd);
                    PrepaidReceivedAmountReport report = new PrepaidReceivedAmountReport();
                    if (count != null) {
                        Long receiveLong = count.getReceiveAmount(); // 应收额度
                        Long receiptLong = count.getReceiptAmount() == null ? 0 : count.getReceiptAmount(); // 实收额度
                        Double rateDouble = GetRateUtil.getRateDouble(receiptLong, receiveLong); // 回收率 实收/应收
                        report.setToReceiveAmountTotal(receiveLong);
                        report.setReceiptAmountTotal(receiptLong);
                        report.setRate(rateDouble);
                    } else {
                        // 查询数据为空时这些数据为0
                        report.setToReceiveAmountTotal(0L);
                        report.setReceiptAmountTotal(0L);
                        report.setRate(0d);
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateStart);
                    //monthList.add(calendar.get(Calendar.MONTH)+1+"月");
                    report.setMonth(calendar.get(Calendar.MONTH) + 1);
                    report.setProjectId(projectId);
                    report.setTablePrefix(tablePrefix);
                    report.setLastUpdateTime(new Date());

                    int num = prepaidReceivedAmountReportDao.insertData(report);
                    if (num == 1) {
                        logger.info("# [OUT] update data successful!");
                    } else {
                        logger.info("# [OUT] update data FAILED! month={}", report);
                    }
                }
            }
            //System.out.println("数据更新了");

    }
}
