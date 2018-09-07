package com.yhglobal.gongxiao.grossProfit.dao;

import com.yhglobal.gongxiao.grossProfit.dao.mapper.YhglobalToReceiveGrossProfitLedgerMapper;
import com.yhglobal.gongxiao.grossProfit.model.GrossProfitItem;
import com.yhglobal.gongxiao.grossProfit.model.YhglobalToReceiveGrossProfitLedger;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
@Repository
public class YhglobalToReceiveGrossProfitLedgerDao {

    @Autowired
    YhglobalToReceiveGrossProfitLedgerMapper yhglobalToReceiveGrossProfitLedgerMapper;

    public int insertLedger(YhglobalToReceiveGrossProfitLedger yhglobalToReceiveGrossProfitLedger){
        return yhglobalToReceiveGrossProfitLedgerMapper.insert(yhglobalToReceiveGrossProfitLedger);
    }
    public YhglobalToReceiveGrossProfitLedger getById(@Param("tablePrefix")String tablePrefix, @Param("id") Long id){
        return yhglobalToReceiveGrossProfitLedgerMapper.selectByPrimaryKey(tablePrefix, id);
    }

    public List<YhglobalToReceiveGrossProfitLedger> selectByIds(@Param("tablePrefix")String tablePrefix, @Param("ids") String ids){
        return yhglobalToReceiveGrossProfitLedgerMapper.selectByIds(tablePrefix, ids);
    }

    public YhglobalToReceiveGrossProfitLedger getForWriteoff(@Param("tablePrefix")String tablePrefix, @Param("id") Long id){
        return yhglobalToReceiveGrossProfitLedgerMapper.getForWriteoff(tablePrefix, id);
    }

    public List<GrossProfitItem> selectByManyConditions(@Param("tablePrefix")String tablePrefix,
                                                        @Param("projectId") Long projectId,
                                                        @Param("purchaseOrderNo") String purchaseOrderNo,
                                                        @Param("salesOrderNo") String salesOrderNo,
                                                        @Param("flowNo") String flowNo,
                                                        @Param("dateStart") Date dateS,
                                                        @Param("dateEnd") Date dateE,
                                                        @Param("confirmStatus") String confirmStatus){

        return yhglobalToReceiveGrossProfitLedgerMapper.selectByManyCondition(tablePrefix, projectId, purchaseOrderNo, salesOrderNo, flowNo, dateS, dateE, confirmStatus);
    }

    public int updateLedger(@Param("tablePrefix") String tablePrefix,
                            @Param("grossProfitId")Long grossProfitId,
                            @Param("confirmStatus") Byte confirmStatus,
                            @Param("toBeConfirmAmount")BigDecimal toBeConfirmAmount,
                            @Param("confirmedAmount")BigDecimal confirmedAmount,
                            @Param("receivedAmount")BigDecimal receivedAmount,
                            @Param("dataVersion")Long dataVersion,
                            @Param("tracelog")String tracelog,
                            @Param("lastUpdateTime")Date lastUpdateTime,
                            @Param("confirmRecord")String confirmRecord){
        return yhglobalToReceiveGrossProfitLedgerMapper.updateLedger(tablePrefix, grossProfitId, confirmStatus, toBeConfirmAmount, confirmedAmount, receivedAmount,
                dataVersion, tracelog, lastUpdateTime, confirmRecord);
    }

}
