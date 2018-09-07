package com.yhglobal.gongxiao.payment.flow.dao;

import com.yhglobal.gongxiao.model.YhglobalCouponWriteoffRecord;
import com.yhglobal.gongxiao.payment.flow.YhglobalCouponWriteoffFlow;
import com.yhglobal.gongxiao.payment.record.dao.mapping.YhglobalCouponLedgerWriteoffRecordMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 越海返利流水表Dao
 *
 * @author  王帅
 */
@Repository
public class YhglobalCouponWriteoffRecordDao {

    @Autowired
    private YhglobalCouponLedgerWriteoffRecordMapper yhglobalCouponLedgerWriteoffRecordMapper;

    public int update(YhglobalCouponWriteoffRecord record){
        return yhglobalCouponLedgerWriteoffRecordMapper.updateByPrimaryKey(record);
    }

    /**
     * 插入一条越海返利流水记录
     *
     * @param record 返利记录模型
     * @return int 插入成功条数
     * @author 王帅
     */
    public int insert(YhglobalCouponWriteoffRecord record) {
        return yhglobalCouponLedgerWriteoffRecordMapper.insert(record);
    }

    /**
     *  更新记录表数据的流水号字段
     *
     *  @param flowNo     流水号
     *  @param confirmId  确认ID
     *  @return int 插入成功条数
     *  @author 王帅
     * */
    public int updateFlowNo(String flowNo, long confirmId){
        return yhglobalCouponLedgerWriteoffRecordMapper.updateFlowNoByPrimaryKey(flowNo, confirmId);
    }


    public List<YhglobalCouponWriteoffRecord> selectByToReceiveCouponId(long toReceiveCouponId,String flowNo){
        return yhglobalCouponLedgerWriteoffRecordMapper.selectByToReceiveCouponId(toReceiveCouponId,flowNo);
    }

    public List<YhglobalCouponWriteoffRecord> selectByFlowCode(String flowNo){
        return yhglobalCouponLedgerWriteoffRecordMapper.selectByFlowId(flowNo);
    }

    public List<YhglobalCouponWriteoffRecord> searchCouponConfirm(@Param("projectId") Long projectId,
                                                                @Param("flowNo") String flowNo,
                                                                @Param("accountType") Integer accountType,
                                                                @Param("useDateStart") Date useDateStart,
                                                                @Param("useDateEnd") Date useDateEnd,
                                                                @Param("dateStart") Date dateStart,
                                                                @Param("dateEnd") Date dateEnd){
        return yhglobalCouponLedgerWriteoffRecordMapper.searchCouponConfirm(projectId,flowNo,accountType,useDateStart,useDateEnd,dateStart,dateEnd);
    }
}
