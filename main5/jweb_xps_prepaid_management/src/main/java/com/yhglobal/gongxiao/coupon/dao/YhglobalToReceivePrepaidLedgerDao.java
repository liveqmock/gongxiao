package com.yhglobal.gongxiao.coupon.dao;

import com.yhglobal.gongxiao.coupon.dao.mapper.YhglobalToReceivePrepaidLedgerMapper;

import com.yhglobal.gongxiao.coupon.model.YhglobalToReceivePrepaidCount;
import com.yhglobal.gongxiao.coupon.model.YhglobalToReceivePrepaidLedger;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Description: 越海代垫应收
 * @author: LUOYI
 * @Date: Created in 9:50 2018/4/26
 */
@Repository
public class YhglobalToReceivePrepaidLedgerDao {

    @Autowired
    private YhglobalToReceivePrepaidLedgerMapper yhglobalToReceivePrepaidLedgerMapper;
    public int addReceive(YhglobalToReceivePrepaidLedger record){
        return yhglobalToReceivePrepaidLedgerMapper.insert( record);
    }
    public YhglobalToReceivePrepaidLedger selectById(String tablePrefix,Long flowId){
        return yhglobalToReceivePrepaidLedgerMapper.selectById(tablePrefix, flowId);
    }

    public  List<YhglobalToReceivePrepaidLedger>  selectByIdsStr(String tablePrefix,String ids){
        return yhglobalToReceivePrepaidLedgerMapper.selectByIdsStr(tablePrefix, ids);
    }
    public YhglobalToReceivePrepaidLedger selectByOrderId(String tablePrefix,String orderId){
        return yhglobalToReceivePrepaidLedgerMapper.selectByOrderId(tablePrefix, orderId);
    }
    public int updateById(String tablePrefix,YhglobalToReceivePrepaidLedger record){
        return yhglobalToReceivePrepaidLedgerMapper.updateById(tablePrefix, record);
    }

    public int updatePrepaidLedgerForWriteoff(String tablePrefix,Long receiveId, Long confirmAmount, Long receiptAmount,Long toBeConfirmAmount,
                                              Integer receiveStatus, Long dataVersion,String appendTraceLog, Date lastUpdateTime){
        return yhglobalToReceivePrepaidLedgerMapper.updatePrepaidLedgerForWriteoff(tablePrefix, receiveId, confirmAmount, receiptAmount,
                toBeConfirmAmount, receiveStatus, dataVersion, appendTraceLog, lastUpdateTime);
    }
    public List<YhglobalToReceivePrepaidLedger> selectByProjectId(String tablePrefix,Long projectId){
        return yhglobalToReceivePrepaidLedgerMapper.selectByProjectId(tablePrefix, projectId);
    }
    public List<YhglobalToReceivePrepaidLedger> selectReceiveAndRecordByProjectId(@Param("tablePrefix") String tablePrefix,
                                                                                  @Param("projectId") Long projectId,
                                                                                  @Param("orderId") String orderId,
                                                                                  @Param("flowCode") String flowCode,
                                                                                  @Param("accountType") Integer accountType,
                                                                                  @Param("dateStart") Date dateStart,
                                                                                  @Param("dateEnd") Date dateEnd,
                                                                                  @Param("dateStartConfirm") Date dateStartConfirm,
                                                                                  @Param("dateEndConfirm") Date dateEndConfirm,
                                                                                  @Param("receiveStatus") String receiveStatus){
        return yhglobalToReceivePrepaidLedgerMapper.selectReceiveAndRecordByProjectId(tablePrefix,projectId,orderId,flowCode,accountType,
                dateStart,dateEnd,dateStartConfirm,dateEndConfirm,receiveStatus);
    }

    public YhglobalToReceivePrepaidLedger getDataCountByTime(@Param("tablePrefix") String tablePrefix,
                                                                                  @Param("projectId") Long projectId,@Param("dateStart") Date dateStart,
                                                                      @Param("dateEnd") Date dateEnd){
        return yhglobalToReceivePrepaidLedgerMapper.getCountByTime(tablePrefix, projectId, dateStart, dateEnd);
    }
    public List<YhglobalToReceivePrepaidCount> selectReceiveAndRecordCount(@Param("tablePrefix") String tablePrefix,
                                                                           @Param("projectId") Long projectId,
                                                                           @Param("receiveIds") String receiveIds){
        return yhglobalToReceivePrepaidLedgerMapper.selectReceiveAndRecordCount(tablePrefix,projectId,receiveIds);
    }
    public int updateForConfirm(@Param("tablePrefix") String tablePrefix,
                                @Param("receiveId") Long receiveId,
                         @Param("dataVersion") Long dataVersion,
                         @Param("confirmRecord") String confirmRecord,
                         @Param("receiveStatus") Integer receiveStatus,
                         @Param("toBeConfirmAmount") Long toBeConfirmAmount,
                         @Param("receiptAmount") Long receiptAmount,
                         @Param("confirmAmount") Long confirmAmount){
        return yhglobalToReceivePrepaidLedgerMapper.updateForConfirm(tablePrefix, receiveId,dataVersion,confirmRecord,receiveStatus,
                toBeConfirmAmount,receiptAmount,confirmAmount);
    }
}
