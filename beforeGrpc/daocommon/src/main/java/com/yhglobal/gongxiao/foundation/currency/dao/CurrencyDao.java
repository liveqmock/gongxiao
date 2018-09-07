package com.yhglobal.gongxiao.foundation.currency.dao;

import com.yhglobal.gongxiao.foundation.currency.dao.mapping.CurrencyMapper;
import com.yhglobal.gongxiao.foundation.currency.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public class CurrencyDao {

    @Autowired
    CurrencyMapper currencyMapper;

    public Currency getCurrencyByCode(String currencyCode) {
        Currency currencyByCode = currencyMapper.getCurrencyByCode(currencyCode);
        return currencyByCode;
    }

    public Currency getCurrencyByName(String name) {
        Currency currencyByCode = currencyMapper.getCurrencyByName(name);
        return currencyByCode;
    }

    public Currency getCurrencyById(String currencyId) {
        Currency currencyByCode = currencyMapper.getCurrencyById(currencyId);
        return currencyByCode;
    }

}
