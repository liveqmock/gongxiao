package com.yhglobal.gongxiao.purchase.dao;


import com.yhglobal.gongxiao.model.YhglobalCouponLedger;
import com.yhglobal.gongxiao.model.YhglobalCouponLedgerItem;
import com.yhglobal.gongxiao.purchase.dao.mapper.YhglobalToReceiveCouponLedgerMapper;
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
    public List<YhglobalCouponLedger> getByPurchaseOrderNo(String purchaseOrderNo){
        return yhglobalToReceiveCouponLedgerMapper.getByPurchaseOrderNo(purchaseOrderNo);
    }
    public YhglobalCouponLedger selectByPrimaryKey(long id){
        return yhglobalToReceiveCouponLedgerMapper.selectByPrimaryKey(id);
    }

    public int update(YhglobalCouponLedger record){
        return yhglobalToReceiveCouponLedgerMapper.updateByPrimaryKeyWithBLOBs(record);
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

    public List<YhglobalCouponLedger> searchMany(@Param("ids") String ids){
        return yhglobalToReceiveCouponLedgerMapper.searchMany(ids);
    }
}
