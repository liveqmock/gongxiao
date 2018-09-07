package com.yhglobal.gongxiao.payment.project.dao.yhglobal;



import com.yhglobal.gongxiao.payment.project.bean.YhglobalCouponLedger;
import com.yhglobal.gongxiao.payment.project.bean.YhglobalCouponLedgerItem;
import com.yhglobal.gongxiao.payment.project.dao.yhglobal.mapping.YhglobalToReceiveCouponLedgerMapper;
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

    public int insert(YhglobalCouponLedger record){
        return yhglobalToReceiveCouponLedgerMapper.insert(record);
    }

    public YhglobalCouponLedger getByPurchaseOrderNoAndCouponType(String purchaseOrderNo,Byte couponType){
        return yhglobalToReceiveCouponLedgerMapper.getByPurchaseOrderNoAndCouponType(purchaseOrderNo,couponType);
   }
    public YhglobalCouponLedger selectByPrimaryKey(long id){
        return yhglobalToReceiveCouponLedgerMapper.selectByPrimaryKey(id);
    }

    public YhglobalCouponLedger selectCouponLedgerForWriteoff(Long id){
        return yhglobalToReceiveCouponLedgerMapper.selectCouponLedgerForWriteoff(id);
    }

    public int update(YhglobalCouponLedger record){
        return yhglobalToReceiveCouponLedgerMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateCouponLedgerForWriteoff(Long purchaseCouponLedgerId, Long confirmedCouponAmount,Long receivedCouponAmount,Long toBeConfirmAmount,
                                  Byte couponStatus,Long dataVersion,String tracelog, Date lastUpdateTime ){
        return yhglobalToReceiveCouponLedgerMapper.updateCouponLedgerForWriteoff(purchaseCouponLedgerId, confirmedCouponAmount, receivedCouponAmount, toBeConfirmAmount,
                couponStatus, dataVersion, tracelog, lastUpdateTime);
    }

    public List<YhglobalCouponLedger> getByPurchaseOrderNo(String purchaseOrderNo){
        return yhglobalToReceiveCouponLedgerMapper.getByPurchaseOrderNo(purchaseOrderNo);
    }
    public List<YhglobalCouponLedger> selectAllByProjectId(long projectId){
        return yhglobalToReceiveCouponLedgerMapper.selectAllByProjectId(projectId);
    }

    public List<YhglobalCouponLedgerItem> searchByManyCondition(@Param("projectId") Long projectId,
                                                                @Param("purchaseOrderNo") String purchaseOrderNo,
                                                                @Param("supplierOrderNo") String supplierOrderNo,
                                                                @Param("flowNo") String flowNo,
                                                                @Param("dateStart") Date dateS,
                                                                @Param("dateEnd") Date dateE,
                                                                @Param("couponStatus") String couponStatus){
        return yhglobalToReceiveCouponLedgerMapper.searchByManyCondition(projectId,purchaseOrderNo,supplierOrderNo,flowNo,dateS,dateE,couponStatus);
    }
}
