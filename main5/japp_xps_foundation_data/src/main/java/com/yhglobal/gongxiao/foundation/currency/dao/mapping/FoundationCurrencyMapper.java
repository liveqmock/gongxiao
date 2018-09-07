package com.yhglobal.gongxiao.foundation.currency.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.currency.model.FoundationCurrency;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FoundationCurrencyMapper extends BaseMapper {
    @Delete({
        "delete from foundation_currency",
        "where currencyId = #{currencyId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long currencyId);

    @Insert({
        "insert into foundation_currency (currencyId, currencyName, ",
        "currencyCode, recordStatus, ",
        "createTime, lastUpdateTime)",
        "values (#{currencyId,jdbcType=BIGINT}, #{currencyName,jdbcType=VARCHAR}, ",
        "#{currencyCode,jdbcType=VARCHAR}, #{recordStatus,jdbcType=TINYINT}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP})"
    })
    int insert(FoundationCurrency record);


    @Select({
        "select",
        "currencyId, currencyName, currencyCode, recordStatus, createTime, lastUpdateTime",
        "from foundation_currency",
        "where currencyId = #{currencyId,jdbcType=BIGINT}"
    })
    FoundationCurrency selectByPrimaryKey(Long currencyId);

    @Select({
            "select",
            "currencyId, currencyName, currencyCode, recordStatus, createTime, lastUpdateTime",
            "from foundation_currency"
    })
    List<FoundationCurrency> selectAllCurrency();

    @Select({
            "select",
            "currencyId, currencyName, currencyCode, recordStatus, createTime, lastUpdateTime",
            "from foundation_currency",
            "where currencyCode = #{currencyCode}"
    })
    FoundationCurrency getCurrencyByCode(String  currencyCode);

    @Select({
            "select",
            "currencyId, currencyName, currencyCode, recordStatus, createTime, lastUpdateTime",
            "from foundation_currency",
            "where currencyName = #{currencyName}"
    })
    FoundationCurrency getCurrencyByName(String  currencyName);

    @Select({
            "select",
            "currencyId, currencyName, currencyCode, recordStatus, createTime, lastUpdateTime",
            "from foundation_currency",
            "where currencyId = #{currencyId}"
    })
    FoundationCurrency getCurrencyById(long  currencyId);

    @Update({
        "update foundation_currency",
        "set currencyName = #{currencyName,jdbcType=VARCHAR},",
          "currencyCode = #{currencyCode,jdbcType=VARCHAR},",
          "recordStatus = #{recordStatus,jdbcType=TINYINT},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where currencyId = #{currencyId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(FoundationCurrency record);
}