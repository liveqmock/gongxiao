package com.yhglobal.gongxiao.distributor.purchase.service;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.distributor.DistributorAccountForOrder;
import com.yhglobal.gongxiao.foundation.product.dto.ProductDto;


/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 10:46 2018/4/13
 */
public interface OrderService {
    /**
     * 新建采购单
     *
     * @param rpcHeader        rpc调用的header
     * @param shippingAddress  经销商的收货地址
     * @param provinceId       省id
     * @param provinceName     省
     * @param cityId           市id
     * @param cityName         市
     * @param districtId       区id
     * @param districtName     区
     * @param recipientName    收件人名字
     * @param recipientMobile  收件人手机号
     * @param currencyId       结算货币ID
     * @param currencyCode     结算货币码
     * @param cashAmount       现金金额
     * @param couponAmount     返利金额
     * @param prepaidAmount    代垫金额
     * @param totalOrderAmount 总金额
     * @param totalPaidAmount  总金额
     * @param salesOrderItem             销售单中的商品信息(页面只用传 productCode、totalQuantity、totalOrderAmountDouble)
     * @return int 插入条数
     */
    public int createOrder(RpcHeader rpcHeader,
                           String shippingAddress,
                           String provinceId, String provinceName,
                           String cityId, String cityName,
                           String districtId, String districtName,
                           String recipientName, String recipientMobile,
                           String currencyId, String currencyCode,
                           double cashAmount, double totalOrderAmount, double totalPaidAmount, String salesOrderItem);

    /**
     * 拉取当前订单可用金额（预存）
     * @return
     */
    public DistributorAccountForOrder getAvailableBalance(RpcHeader rpcHeader);

    /**
     * 获取商品详情
     * @param rpcHeader
     * @param productId
     * @return
     */
    ProductDto getProductByAd(RpcHeader rpcHeader, Long productId);
}
