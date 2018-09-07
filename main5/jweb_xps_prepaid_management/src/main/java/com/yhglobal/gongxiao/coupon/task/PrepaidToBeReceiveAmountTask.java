package com.yhglobal.gongxiao.coupon.task;

import com.yhglobal.gongxiao.coupon.config.PortalConfig;
import com.yhglobal.gongxiao.coupon.dao.PrepaidToBeReceiveAmountReportDao;
import com.yhglobal.gongxiao.coupon.dao.YhglobalToReceivePrepaidLedgerDao;
import com.yhglobal.gongxiao.coupon.model.PrepaidToBeReceiveAmountReport;
import com.yhglobal.gongxiao.coupon.model.YhglobalToReceivePrepaidLedger;
import com.yhglobal.gongxiao.coupon.utils.GetRateUtil;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
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
 * 代垫报表1 代垫未收金额报表的定时任务类
 * @author 王帅
 */
@Component
@EnableScheduling
public class PrepaidToBeReceiveAmountTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    YhglobalToReceivePrepaidLedgerDao yhglobalToReceivePrepaidLedgerDao;

    @Autowired
    PrepaidToBeReceiveAmountReportDao prepaidToBeReceiveAmountReportDao;
    /**
     * 产生代垫未收金额报表数据
     * 每天中午12点触发
     */
    @Scheduled(cron = "0 0 1 * * ?")
    //@Scheduled(fixedDelay = 50000)  //测试使用
    public void get() {

            logger.info("task start");
            try {
                String traceId = "prepaidReceivedAmountScheduledTask";
                GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "admin", "admin123");
                ProjectStructure.SelectProjectListReq.Builder selectProjectListReq
                        = ProjectStructure.SelectProjectListReq.newBuilder().setRpcHeader(rpcHeader);
                ProjectServiceGrpc.ProjectServiceBlockingStub blockingStub = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
                ProjectStructure.SelectProjectListResp selectProjectListResp = null;
                try {
                    logger.info("#traceId={}# [ON][getProjectList] start get project list ", rpcHeader.getTraceId());
                    selectProjectListResp = blockingStub.selectProjectList(selectProjectListReq.build());
                    logger.info("#traceId={}# [ON][getProjectList] get project list end ", rpcHeader.getTraceId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<ProjectStructure.Project> projectListList = selectProjectListResp.getProjectListList();
                for (int j = 0; j < projectListList.size(); j++) {
                    try {
                        //long projectId = portalConfig.getProjectId();
                        long projectId = projectListList.get(j).getProjectId();
                        String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId); // 获取表前缀

                        //   logger.info("# [IN] Scheduled Task: get outbound amount summary.");
                        // 需求点：先把未收代垫分为四个阶段：30-=60天、60-=90天、90-=180天、180天以上，然后根据金额来判断占比。
                        //String tablePrefix = null;
                        // TablePrefixUtil.getTablePrefixByProjectId(projectId);
                        Long thirtyDaysTimes = 30 * 24 * 60 * 60 * 1000L; // 计算30天的毫秒数
                        Date now = new Date(); // 当前时间
                        List<BigDecimal> amountList = new ArrayList<>();
                        Date startDateStatus1 = new Date(now.getTime() - thirtyDaysTimes * 2);
                        Date endDateStatus1 = new Date(now.getTime() - thirtyDaysTimes);
                        BigDecimal number = new BigDecimal("100.0");
                        PrepaidToBeReceiveAmountReport report = new PrepaidToBeReceiveAmountReport();
                        YhglobalToReceivePrepaidLedger count1;
                        try {
                            count1 = yhglobalToReceivePrepaidLedgerDao.getDataCountByTime(tablePrefix, projectId, startDateStatus1, endDateStatus1);
                        } catch (Exception e) {
                            logger.error("the projectId table is not existed");
                            continue;
                        }
                        Long amount1;
                        if (count1 != null) {
                            amount1 = count1.getToBeConfirmAmount();
                        } else {
                            amount1 = 0L;
                        }
                        amountList.add(new BigDecimal(amount1 + "").divide(number));
                        report.setTimeTypeOne(amount1);

                        Date startDateStatus2 = new Date(now.getTime() - thirtyDaysTimes * 3);
                        Date endDateStatus2 = new Date(now.getTime() - thirtyDaysTimes * 2);
                        YhglobalToReceivePrepaidLedger count2 = yhglobalToReceivePrepaidLedgerDao.getDataCountByTime(tablePrefix, projectId, startDateStatus2, endDateStatus2);
                        Long amount2 = count2 != null ? count2.getToBeConfirmAmount() : 0L;
                        amountList.add(new BigDecimal(amount2 + "").divide(number));
                        report.setTimeTypeTwo(amount2);

                        Date startDateStatus3 = new Date(now.getTime() - thirtyDaysTimes * 6);
                        Date endDateStatus3 = new Date(now.getTime() - thirtyDaysTimes * 3);
                        YhglobalToReceivePrepaidLedger count3 = yhglobalToReceivePrepaidLedgerDao.getDataCountByTime(tablePrefix, projectId, startDateStatus3, endDateStatus3);
                        Long amount3 = count3 != null ? count3.getToBeConfirmAmount() : 0L;
                        amountList.add(new BigDecimal(amount3 + "").divide(number));
                        report.setTimeTypeThree(amount3);

                        Date endDateStatus4 = new Date(now.getTime() - thirtyDaysTimes * 6);
                        YhglobalToReceivePrepaidLedger count4 = yhglobalToReceivePrepaidLedgerDao.getDataCountByTime(tablePrefix, projectId, null, endDateStatus4);
                        Long amount4 = count4 != null ? count4.getToBeConfirmAmount() : 0L;
                        amountList.add(new BigDecimal(amount4 + "").divide(number));
                        report.setTimeTypeFour(amount4);

                        report.setTablePrefix(tablePrefix);
                        report.setId(1L);
                        // 插入定时统计的数据
                        int num;
                        PrepaidToBeReceiveAmountReport data = null;
                        try {
                            data = prepaidToBeReceiveAmountReportDao.getData(tablePrefix, 1L);
                        } catch (Exception e) {
                            continue;
                        }
                        if (data == null) {
                            num = prepaidToBeReceiveAmountReportDao.insertData(report);
                        } else {
                            num = prepaidToBeReceiveAmountReportDao.updateData(report);
                        }
                        if (num == 1) {
                            logger.info("# [OUT] update data success");
                        } else {
                            logger.error("# [OUT] update data failed", new Date());
                        }
                    }catch (Exception e){
                        continue;
                    }
                }
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
            //System.out.println("数据更新了");

    }


}
