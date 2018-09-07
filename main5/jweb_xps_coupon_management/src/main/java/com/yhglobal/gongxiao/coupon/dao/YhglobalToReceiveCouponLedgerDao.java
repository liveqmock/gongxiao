package com.yhglobal.gongxiao.coupon.dao;




import com.yhglobal.gongxiao.coupon.dao.mapper.YhglobalToReceiveCouponLedgerMapper;
import com.yhglobal.gongxiao.coupon.model.YhglobalCouponLedger;
import com.yhglobal.gongxiao.coupon.model.YhglobalCouponLedgerItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 越海应收返利生成的dao
 * @author 王帅
 * */
@Repository
public class YhglobalToReceiveCouponLedgerDao {

    @Autowired
    YhglobalToReceiveCouponLedgerMapper yhglobalToReceiveCouponLedgerMapper;

    public List<YhglobalCouponLedger> searchByIds(String tablePrefix, String ids){
        return yhglobalToReceiveCouponLedgerMapper.searchByIds(tablePrefix, ids);
    }

    public int insert(YhglobalCouponLedger record){
        return yhglobalToReceiveCouponLedgerMapper.insert(record);
    }

    public YhglobalCouponLedger getByPurchaseOrderNoAndCouponType(String tablePrefix, String purchaseOrderNo,Byte couponType){
        return yhglobalToReceiveCouponLedgerMapper.getByPurchaseOrderNoAndCouponType(tablePrefix, purchaseOrderNo,couponType);
   }
    public YhglobalCouponLedger selectByPrimaryKey(String tablePrefix,long id){
        return yhglobalToReceiveCouponLedgerMapper.selectByPrimaryKey(tablePrefix, id);
    }

    public YhglobalCouponLedger selectCouponLedgerForWriteoff(String tablePrefix, Long id){
        return yhglobalToReceiveCouponLedgerMapper.selectCouponLedgerForWriteoff(tablePrefix, id);
    }

    public int update(String tablePrefix, YhglobalCouponLedger record){
        return yhglobalToReceiveCouponLedgerMapper.updateByPrimaryKeyWithBLOBs(tablePrefix, record);
    }

    public int updateCouponLedgerForWriteoff(String tablePrefix, Long purchaseCouponLedgerId, Long confirmedCouponAmount,Long receivedCouponAmount,Long toBeConfirmAmount,
                                  Byte couponStatus,Long dataVersion,String tracelog,Date lastUpdateTime){
        return yhglobalToReceiveCouponLedgerMapper.updateCouponLedgerForWriteoff(tablePrefix, purchaseCouponLedgerId, confirmedCouponAmount, receivedCouponAmount, toBeConfirmAmount,
                couponStatus, dataVersion, tracelog, lastUpdateTime);
    }

    public List<YhglobalCouponLedger> getByPurchaseOrderNo(String tablePrefix, String purchaseOrderNo){
        return yhglobalToReceiveCouponLedgerMapper.getByPurchaseOrderNo(tablePrefix, purchaseOrderNo);
    }
    public List<YhglobalCouponLedger> selectAllByProjectId(String tablePrefix, long projectId){
        return yhglobalToReceiveCouponLedgerMapper.selectAllByProjectId(tablePrefix, projectId);
    }

    public List<YhglobalCouponLedgerItem> searchByManyCondition(@Param("tablePrefix")String tablePrefix,
                                                                @Param("projectId") Long projectId,
                                                                @Param("purchaseOrderNo") String purchaseOrderNo,
                                                                @Param("supplierOrderNo") String supplierOrderNo,
                                                                @Param("flowNo") String flowNo,
                                                                @Param("dateStart") Date dateS,
                                                                @Param("dateEnd") Date dateE,
                                                                @Param("couponStatus") String couponStatus, @Param("couponTypes") String couponTypes){
        return yhglobalToReceiveCouponLedgerMapper.searchByManyCondition(tablePrefix, projectId,purchaseOrderNo,supplierOrderNo,flowNo,dateS,dateE,couponStatus, couponTypes);
    }
}
