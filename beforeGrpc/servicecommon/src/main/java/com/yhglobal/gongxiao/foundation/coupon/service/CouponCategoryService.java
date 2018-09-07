package com.yhglobal.gongxiao.foundation.coupon.service;


import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.couponcategory.model.CouponCategory;

public interface CouponCategoryService {


    /**
     * 创建所有项目共用的返利类别
     *
     * @param rpcHeader rpc调用的header
     * @return
     */
    int createGlobalLevelCouponCategory(RpcHeader rpcHeader);


    /**
     * 创建某项目专有的返利类别
     *
     * @param rpcHeader rpc调用的header
     * @return
     */
    int createProjectLevelCouponCategory(RpcHeader rpcHeader);


    /**
     * 通过种类id获取返利种类
     *
     * @param rpcHeader rpc调用的header
     * @return
     */
    CouponCategory getCouponCategoryById(RpcHeader rpcHeader);


}
