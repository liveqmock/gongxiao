package com.yhglobal.gongxiao.foudation.basicdata.service;

import com.yhglobal.gongxiao.foudation.model.MaterialInfo;

import java.io.IOException;
import java.util.List;

/**
 * @author: 陈浩
 **/
public interface BasicDataSynService {

    void synProjectInfo(String keywords) throws IOException;
    //仓库信息不用同步,主数据里面的仓库信息跟eas的仓库信息不同
    void synWarehouseInfo(String keywords) throws IOException;

    void synSupplierInfo(String keywords) throws IOException;

    void synCustomerInfo(String keywords) throws IOException;

    List<MaterialInfo> synProductInfo(String keywords) throws IOException;
    //组织结构不用同步,项目编码的前4位就是财务组织
    void synOrganizeInfo(String keywords) throws IOException;
    //货币默认为CNY 不用同步
    void synCurrencyInfo(String keywords) throws IOException;

    void synUnitInfo(String keywords) throws IOException;
}
