package com.yhglobal.gongxiao.foundation.currency.service;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.currency.model.Currency;

/**
 * 货币服务
 */
public interface CurrencyService {

    /**
     * 通过currencyCode查询货币
     *
     * @param rpcHeader rpc调用的header
     * @param currencyCode 货币代码 比如:CNY USD等
     * @return Currency 货币对象
     */
    Currency getCurrencyByCode(RpcHeader rpcHeader, String currencyCode);

    /**
     * 通过currencyName查询货币
     *
     * @param rpcHeader rpc调用的header
     * @param currencyName 货币名字 比如:RMB等
     * @return Currency 货币对象
     */
    Currency getCurrencyByName(RpcHeader rpcHeader, String currencyName);

    /**
     * 通过currencyId查询货币
     *
     * @param rpcHeader rpc调用的header
     * @param currencyId 货币id
     * @return Currency 货币对象
     */
    Currency getCurrencyById(RpcHeader rpcHeader, String currencyId);

}
