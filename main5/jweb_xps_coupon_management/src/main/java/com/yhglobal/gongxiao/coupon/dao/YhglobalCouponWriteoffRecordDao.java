package com.yhglobal.gongxiao.coupon.dao;



import com.yhglobal.gongxiao.coupon.dao.mapper.YhglobalCouponLedgerWriteoffRecordMapper;
import com.yhglobal.gongxiao.coupon.model.YhglobalCouponWriteoffRecord;
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

    public int update(String tablePrefix,YhglobalCouponWriteoffRecord record){
        return yhglobalCouponLedgerWriteoffRecordMapper.updateByPrimaryKey(tablePrefix, record);
    }

    public int updateRollbackStatus(String tablePrefix, int isRollback,Long confirmId){
        return yhglobalCouponLedgerWriteoffRecordMapper.updateRollbackStatus(tablePrefix,isRollback,confirmId);
    }

    /**
     * 插入一条越海返利流水记录
     *
     * @param record 返利记录模型
     * @return int 插入成功条数
     * @author 王帅
     */
    public int insert(YhglobalCouponWriteoffRecord record) {
        return yhglobalCouponLedgerWriteoffRecordMapper.insert( record);
    }

    /**
     *  更新记录表数据的流水号字段
     *
     *  @param flowNo     流水号
     *  @param confirmId  确认ID
     *  @return int 插入成功条数
     *  @author 王帅
     * */
    public int updateFlowNo(String tablePrefix,String flowNo, long confirmId){
        return yhglobalCouponLedgerWriteoffRecordMapper.updateFlowNoByPrimaryKey(tablePrefix,flowNo, confirmId);
    }


    public List<YhglobalCouponWriteoffRecord> selectByToReceiveCouponId(String tablePrefix,long toReceiveCouponId,String flowNo){
        return yhglobalCouponLedgerWriteoffRecordMapper.selectByToReceiveCouponId(tablePrefix, toReceiveCouponId,flowNo);
    }

    public List<YhglobalCouponWriteoffRecord> selectByFlowCode(String tablePrefix,String flowNo){
        return yhglobalCouponLedgerWriteoffRecordMapper.selectByFlowId(tablePrefix,flowNo);
    }

    public List<YhglobalCouponWriteoffRecord> searchCouponConfirm(@Param("tablePrefix")String tablePrefix,@Param("projectId") Long projectId,
                                                                @Param("flowNo") String flowNo,
                                                                @Param("accountType") Integer accountType,
                                                                @Param("useDateStart") Date useDateStart,
                                                                @Param("useDateEnd") Date useDateEnd,
                                                                @Param("dateStart") Date dateStart,
                                                                @Param("dateEnd") Date dateEnd){
        return yhglobalCouponLedgerWriteoffRecordMapper.searchCouponConfirm(tablePrefix,projectId,flowNo,accountType,useDateStart,useDateEnd,dateStart,dateEnd);
    }
}
