package com.yhglobal.gongxiao.foundation.currency.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.currency.model.Currency;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CurrencyMapper extends BaseMapper {

    @Select({
        "select",
        "currencyId, currencyName, currencyCode, status, createTime",
        "from t_currency",
        "where currencyCode = #{currencyCode}"
    })
    Currency getCurrencyByCode(@Param("currencyCode") String currencyCode);

    @Select({
            "select",
            "currencyId, currencyName, currencyCode, status, createTime",
            "from t_currency",
            "where currencyName = #{currencyName}"
    })
    Currency getCurrencyByName(@Param("currencyName") String currencyName);

    @Select({
            "select",
            "currencyId, currencyName, currencyCode, status, createTime",
            "from t_currency",
            "where currencyId = #{currencyId}"
    })
    Currency getCurrencyById(@Param("currencyId") String currencyId);
}