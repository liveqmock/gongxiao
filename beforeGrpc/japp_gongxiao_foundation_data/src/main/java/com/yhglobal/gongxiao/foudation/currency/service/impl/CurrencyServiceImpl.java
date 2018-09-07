package com.yhglobal.gongxiao.foudation.currency.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.currency.dao.CurrencyDao;
import com.yhglobal.gongxiao.foundation.currency.model.Currency;
import com.yhglobal.gongxiao.foundation.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
@Service(interfaceName="com.yhglobal.gongxiao.foundation.currency.service.CurrencyService", timeout=500)
public class CurrencyServiceImpl implements CurrencyService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    @Autowired
    CurrencyDao currencyDao;

    @Override
    public Currency getCurrencyByCode(RpcHeader rpcHeader, String currencyCode) {
        logger.info("#traceId={}# [IN][getCurrencyByCode] params: currencyCode={}", rpcHeader.traceId, currencyCode);
        try {
            Currency currency = currencyDao.getCurrencyByCode(currencyCode);
            if (currency == null) {
                logger.info("#traceId={}# [OUT] fail to get currency: currencyCode={} Currency=NULL", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] get Currency success: currencyCode={} currencyId={}", rpcHeader.traceId, currencyCode, currency.getCurrencyId());
            }
            return currency;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }
    }

    @Override
    public Currency getCurrencyByName(RpcHeader rpcHeader, String currencyName){
        logger.info("#traceId={}# [IN][getCurrencyByName] params: currencyName={}", rpcHeader.traceId, currencyName);
        try {
            Currency currency = currencyDao.getCurrencyByName(currencyName);
            if (currency==null){
                logger.info("#traceId={}# [OUT] fail to getCurrencyByName: currencyName={} currency=NULL", rpcHeader.traceId, currencyName);
            }else {
                logger.info("#traceId={}# [OUT] getCurrencyByName success: currencyCode={}  currencyName={}", rpcHeader.traceId, currency.getCurrencyCode(), currency.getCurrencyName());
            }
            return currency;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }
    }

    @Override
    public Currency getCurrencyById(RpcHeader rpcHeader, String currencyId) {
        logger.info("#traceId={}# [IN][getCurrencyById] params: currencyId={}", rpcHeader.traceId, currencyId);
        try {
            Currency currency = currencyDao.getCurrencyById(currencyId);
            if (currency==null){
                logger.info("#traceId={}# [OUT] fail to getCurrencyById: currencyId={} currency=NULL", rpcHeader.traceId, currencyId);
            }else {
                logger.info("#traceId={}# [OUT] getCurrencyById success: currencyCode={}  currencyName={}", rpcHeader.traceId, currency.getCurrencyCode(), currency.getCurrencyName());
            }
            return currency;

        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }
    }


}
