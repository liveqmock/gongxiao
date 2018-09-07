package com.yhglobal.gongxiao.foundation.currency.dao;

import com.yhglobal.gongxiao.foundation.currency.dao.mapping.FoundationCurrencyMapper;
import com.yhglobal.gongxiao.foundation.currency.model.FoundationCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class FoundationCurrencyDao {

    @Autowired
    FoundationCurrencyMapper foundationCurrencyMapper;

    /**
     * 插入新的货品信息
     * @param foundationCurrency
     * @return
     */
    public int insertCurrencyInfo(FoundationCurrency foundationCurrency){
        return foundationCurrencyMapper.insert(foundationCurrency);
    }

    /**
     * 获取所有的币种信息
     * @return
     */
    public List<FoundationCurrency> selectAllCurrency(){
        return foundationCurrencyMapper.selectAllCurrency();
    }

    /**
     * 通过货品编码获取货币明细
     * @param currencyCode
     * @return
     */
    public FoundationCurrency getCurrencyByCode(String currencyCode) {
        return foundationCurrencyMapper.getCurrencyByCode(currencyCode);
    }

    /**
     * 通过货品ID获取货币明细
     * @param currencyId
     * @return
     */
    public FoundationCurrency getCurrencyById(long currencyId) {
        return foundationCurrencyMapper.getCurrencyById(currencyId);
    }

    /**
     * 通过货品ID获取货币明细
     * @param currencyName
     * @return
     */
    public FoundationCurrency getCurrencyByName(String currencyName) {
        return foundationCurrencyMapper.getCurrencyByName(currencyName);
    }
}
