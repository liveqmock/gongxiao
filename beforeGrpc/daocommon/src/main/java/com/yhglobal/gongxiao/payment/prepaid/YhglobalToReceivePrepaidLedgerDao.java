package com.yhglobal.gongxiao.payment.prepaid;

import com.yhglobal.gongxiao.payment.prepaid.mapping.YhglobalToReceivePrepaidLedgerMapper;
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
        return yhglobalToReceivePrepaidLedgerMapper.insert(record);
    }
    public YhglobalToReceivePrepaidLedger selectById(Long flowId){
        return yhglobalToReceivePrepaidLedgerMapper.selectById(flowId);
    }
    public YhglobalToReceivePrepaidLedger selectByOrderId(String orderId){
        return yhglobalToReceivePrepaidLedgerMapper.selectByOrderId(orderId);
    }
    public int updateById(YhglobalToReceivePrepaidLedger record){
        return yhglobalToReceivePrepaidLedgerMapper.updateById(record);
    }
    public List<YhglobalToReceivePrepaidLedger> selectByProjectId(Long projectId){
        return yhglobalToReceivePrepaidLedgerMapper.selectByProjectId(projectId);
    }
    public List<YhglobalToReceivePrepaidLedger> selectReceiveAndRecordByProjectId(@Param("projectId") Long projectId,
                                                                                  @Param("orderId") String orderId,
                                                                                  @Param("flowCode") String flowCode,
                                                                                  @Param("accountType") Integer accountType,
                                                                                  @Param("dateStart") Date dateStart,
                                                                                  @Param("dateEnd") Date dateEnd,
                                                                                  @Param("dateStartConfirm") Date dateStartConfirm,
                                                                                  @Param("dateEndConfirm") Date dateEndConfirm,
                                                                                  @Param("receiveStatus") String receiveStatus){
        return yhglobalToReceivePrepaidLedgerMapper.selectReceiveAndRecordByProjectId(projectId,orderId,flowCode,accountType,dateStart,dateEnd,dateStartConfirm,dateEndConfirm,receiveStatus);
    }
    public List<YhglobalToReceivePrepaidCount> selectReceiveAndRecordCount(@Param("projectId") Long projectId,
                                                                                  @Param("receiveIds") String receiveIds){
        return yhglobalToReceivePrepaidLedgerMapper.selectReceiveAndRecordCount(projectId,receiveIds);
    }

}
