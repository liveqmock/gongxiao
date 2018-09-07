package com.yhglobal.gongxiao.foundation.distributor.service;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.distributor.model.DistributorCouponSetting;

/**
 * @Description: ad返利代垫配置
 * @author: LUOYI
 * @Date: Created in 11:18 2018/4/20
 */
public interface DistributorCouponSettingService {
    /**
     * 获取AD返利代垫比例
     * @param projectId
     * @param distributorId
     * @return
     */
    DistributorCouponSetting getDistributorCoupon(RpcHeader rpcHeader,
                                                  String projectId,
                                                  String distributorId);
}
