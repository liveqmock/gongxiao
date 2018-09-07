package com.yhglobal.gongxiao.dao;//package com.yhglobal.gongxiao.dao;
//
//
//import com.yhglobal.gongxiao.dao.mapper.YhglobalToReceiveCouponLedgerMapper;
//import com.yhglobal.gongxiao.model.YhglobalCouponLedger;
//import jdk.nashorn.internal.ir.annotations.Reference;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * 采购应收返利
// *
// * @author: 王帅
// **/
//@Repository
//public class YhglobalCouponLedgerDao {
//
//    @Autowired
//    YhglobalToReceiveCouponLedgerMapper yhglobalCouponLedgerMapper;
//
//    public int insert(YhglobalCouponLedger record){
//        return yhglobalCouponLedgerMapper.insert(record);
//    }
//
//    /**
//     * 通过采购返利记录ID获取采购返利值
//     * @param purchaseCouponLedgerId
//     * @return
//     */
//    public YhglobalCouponLedger getById(Long purchaseCouponLedgerId){
//        return yhglobalCouponLedgerMapper.selectByPrimaryKey(purchaseCouponLedgerId);
//    }
//
//    /**
//     * 获取该项目下所有返利记录
//     * @param projectId 项目
//     * @return
//     */
//    public List<YhglobalCouponLedger> selectCouponByProjectId(String projectId){
//        return yhglobalCouponLedgerMapper.selectByProjectId(projectId);
//    }
//
//    /**
//     * 根据订单号 和 返利类型 获取返利对象
//     * @param supplierOrderNo   订单号
//     * @param couponType        返利类型
//     * @return
//     */
//    public YhglobalCouponLedger selectBySupplierOrderNoAndCouponType(String supplierOrderNo, int couponType){
//        return yhglobalCouponLedgerMapper.selectBySupplierOrderNoAndCouponType(supplierOrderNo, couponType);
//    }
//
//    /**
//     * 更新返利分类对象的数据
//     * @param yhglobalCouponLedger   返利分类对象
//     * @return
//     */
//    public  int updateCouponLedger(YhglobalCouponLedger yhglobalCouponLedger){
//        return yhglobalCouponLedgerMapper.updateByPrimaryKey(yhglobalCouponLedger);
//    }
//}
