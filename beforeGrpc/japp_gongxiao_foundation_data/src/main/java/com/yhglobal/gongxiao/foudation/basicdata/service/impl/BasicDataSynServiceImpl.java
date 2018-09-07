package com.yhglobal.gongxiao.foudation.basicdata.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.foudation.basicdata.service.BasicDataSynService;
import com.yhglobal.gongxiao.foudation.constant.FoundationConstant;
import com.yhglobal.gongxiao.foudation.model.*;
import com.yhglobal.gongxiao.foudation.util.OKHttpUtil;
import com.yhglobal.gongxiao.foundation.distributor.dao.DistributorDao;
import com.yhglobal.gongxiao.foundation.distributor.model.Distributor;
import com.yhglobal.gongxiao.foundation.product.price.model.ProductPrice;
import com.yhglobal.gongxiao.foundation.product.productBasic.dao.ProductBasicDao;
import com.yhglobal.gongxiao.foundation.product.productprice.dao.ProductPriceDao;
import com.yhglobal.gongxiao.foundation.project.dao.ProjectDao;
import com.yhglobal.gongxiao.foundation.supplier.dao.SupplierDao;
import com.yhglobal.gongxiao.foundation.supplier.model.Supplier;
import com.yhglobal.gongxiao.foundation.unit.Unit;
import com.yhglobal.gongxiao.foundation.unit.dao.UnitDao;
import com.yhglobal.gongxiao.foundation.warehouse.dao.WarehouseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 主数据同步实现类
 *
 * @author: 陈浩
 **/
@Service
public class BasicDataSynServiceImpl implements BasicDataSynService {
    private static Logger logger = LoggerFactory.getLogger(BasicDataSynServiceImpl.class);

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private WarehouseDao warehouseDao;

    @Autowired
    private SupplierDao supplierDao;

    @Autowired
    private DistributorDao distributorDao;

    @Autowired
    private ProductPriceDao productPriceDao;

    @Autowired
    private ProductBasicDao productBasicDao;

    @Autowired
    private UnitDao unitDao;

    public void synProjectInfo(String keywords) throws IOException {
        logger.info("开始同步项目信息");
        //1)拼地址
        String url = FoundationConstant.EAS_URL;
        String method = FoundationConstant.GET_DATAS;
        String address = url + method;
        //2)拼请求数据
        RequestData requestData = new RequestData(FoundationConstant.PROJECT, FoundationConstant.SKIP_COUNT, FoundationConstant.MAX_RESULT_COUNT);
        if (keywords != null && !"".equals(keywords)) {
            requestData.setKeywords(keywords);
        }
        String jsonString = JSON.toJSONString(requestData);
        //3)发送请求
        ResultData resultData = OKHttpUtil.postJson(address, jsonString);
        if (resultData==null){
            logger.warn("主数据没有项目名={}的数据",keywords);
            return;
        }
        //4)数据转换
        String data = resultData.getData();
        JSONObject jsonObject = JSON.parseObject(data);
        Object items = jsonObject.get("items");
        String itemsString = items.toString();
        ArrayList<Project> projects
                = JSON.parseObject(itemsString, new TypeReference<ArrayList<Project>>() {
        });
        //5)数据处理
        List<com.yhglobal.gongxiao.foundation.project.model.Project> projectsDb = projectDao.selectAll();
        Scanner scanner = new Scanner(System.in);
        for (com.yhglobal.gongxiao.foundation.project.model.Project projectDb : projectsDb) {
            int projectId = projectDb.getProjectId();
            String projectName = projectDb.getProjectName();
            logger.info("是否跳过分销项目数据同步项目id={}项目name={}的数据? y/n", projectId, projectName);
            String synData = scanner.nextLine();
            if ("y".equals(synData)) {
                continue;
            }
            for (Project project : projects) {
                String easCode = project.getCode();
                String easName = project.getName();
                String orgCode = project.getOrgCode();
                String orgName = project.getOrgName();
                logger.info("是否将项目code={},项目name={} 的数据从主数据同步到分销系统的项目id={}项目name={}的数据里?(y/n)",
                        easCode, easName, projectId, projectName);
                String judgeInfo = scanner.nextLine();
                if ("y".equals(judgeInfo)) {
                    projectDao.updateProjectEasInfo(projectId, easName, easCode, orgCode, orgName);
                    break;
                }
            }
        }
    }

    @Override
    public void synWarehouseInfo(String keywords) throws IOException {

    }

    @Override
    public void synSupplierInfo(String keywords) throws IOException {
        logger.info("开始同步供应商信息");
        //1)拼地址
        String url = FoundationConstant.EAS_URL;
        String method = FoundationConstant.GET_DATAS;
        String address = url + method;
        //2)拼请求数据
        RequestData requestData = new RequestData(FoundationConstant.SUPPLIER, FoundationConstant.SKIP_COUNT, FoundationConstant.MAX_RESULT_COUNT);
        if (keywords != null && !"".equals(keywords)) {
            requestData.setKeywords(keywords);
        }
        String jsonString = JSON.toJSONString(requestData);
        //3)发送请求
        ResultData resultData = OKHttpUtil.postJson(address, jsonString);
        if (resultData==null){
            logger.warn("主数据没有供应商={}的数据",keywords);
            return;
        }
        //4)数据转换
        String data = resultData.getData();
        JSONObject jsonObject = JSON.parseObject(data);
        Object items = jsonObject.get("items");
        String itemsString = items.toString();
        ArrayList<ResultDataItem> suppliers
                = JSON.parseObject(itemsString, new TypeReference<ArrayList<ResultDataItem>>() {
        });
        //5)数据处理
        List<com.yhglobal.gongxiao.foundation.supplier.model.Supplier> supplierListDb = supplierDao.selectAll();
        Scanner scanner = new Scanner(System.in);
        for (com.yhglobal.gongxiao.foundation.supplier.model.Supplier supplierDb : supplierListDb) {
            String supplierCodeDb = supplierDb.getSupplierCode();
            String supplierNameDb = supplierDb.getSupplierName();
            logger.info("是否跳过分销供应商数据同步: supplierCode={}supplierName={}的数据? y/n", supplierCodeDb, supplierNameDb);
            String synData = scanner.nextLine();
            if ("y".equals(synData)) {
                continue;
            }
            for (ResultDataItem resultDataItem : suppliers) {
                String easCode = resultDataItem.getCode();
                String easName = resultDataItem.getName();
                logger.info("是否将项目code={},项目name={} 的数据从主数据同步到分销系统的供应商 code={}name={}的数据里?(y/n)",
                        easCode, easName, supplierCodeDb, supplierNameDb);
                String judgeInfo = scanner.nextLine();
                if ("y".equals(judgeInfo)) {
                    supplierDao.updateSupplierInfo(supplierCodeDb, easCode, easName);
                    break;
                }
            }
        }
    }

    @Override
    public void synCustomerInfo(String keywords) throws IOException {
        logger.info("开始同步客户信息信息");
        //1)拼地址
        String url = FoundationConstant.EAS_URL;
        String method = FoundationConstant.GET_DATAS;
        String address = url + method;
        //2)拼请求数据
        RequestData requestData = new RequestData(FoundationConstant.CUSTOMER, FoundationConstant.SKIP_COUNT, FoundationConstant.MAX_RESULT_COUNT);
        if (keywords != null && !"".equals(keywords)) {
            requestData.setKeywords(keywords);
        }
        String jsonString = JSON.toJSONString(requestData);
        //3)发送请求
        ResultData resultData = OKHttpUtil.postJson(address, jsonString);
        if (resultData==null){
            logger.warn("主数据没有客户={}的数据",keywords);
            return;
        }
        //4)数据转换
        String data = resultData.getData();
        JSONObject jsonObject = JSON.parseObject(data);
        Object items = jsonObject.get("items");
        String itemsString = items.toString();
        ArrayList<ResultDataItem> resultDataItems
                = JSON.parseObject(itemsString, new TypeReference<ArrayList<ResultDataItem>>() {
        });
        //5)数据处理
        Scanner scanner = new Scanner(System.in);
        for (ResultDataItem resultDataItem : resultDataItems) {
            String easCode = resultDataItem.getCode();
            String easName = resultDataItem.getName();
            logger.info("是否将向分销系统新增客户信息 code={}name={}的数据里?(y/n)",
                    easCode, easName);
            String judgeInfo = scanner.nextLine();
            if ("y".equals(judgeInfo)) {
                distributorDao.insertDistributor(easCode, easName);
                break;
            }
        }
    }

    @Override
    public List<MaterialInfo> synProductInfo(String keywords) throws IOException {
        logger.info("开始同步客户信息信息");
        //1)拼地址
        String url = FoundationConstant.EAS_URL;
        String method = FoundationConstant.GET_DATAS;
        String address = url + method;
        //2)拼请求数据
        RequestData requestData = new RequestData(FoundationConstant.PRODUCT, FoundationConstant.SKIP_COUNT, FoundationConstant.MAX_RESULT_COUNT);
        if (keywords != null && !"".equals(keywords)) {
            requestData.setKeywords(keywords);
        }
        String jsonString = JSON.toJSONString(requestData);
        //3)发送请求
        ResultData resultData = OKHttpUtil.postJson(address, jsonString);
        if (resultData==null){
            logger.warn("主数据没有货品={}的数据",keywords);
            return null;
        }
        //4)数据转换
        String data = resultData.getData();
        JSONObject jsonObject = JSON.parseObject(data);
        Object items = jsonObject.get("items");
        String itemsString = items.toString();
        List<MaterialInfo> materialInfos
                = JSON.parseObject(itemsString, new TypeReference<List<MaterialInfo>>() {
        });
        return materialInfos;
//        //5)数据处理
//        List<ProductPrice> productPriceListDb = productPriceDao.selectAll();
//        Scanner scanner = new Scanner(System.in);
//        for (ProductPrice productPriceDb:productPriceListDb){
//            String productCodeDb = productPriceDb.getProductCode();
//            String productNameDb = productPriceDb.getProductName();
//            logger.info("是否跳过货品数据同步: productCodeDb={}productNameName={}的数据? y/n", productCodeDb, productNameDb);
//            String judgeSymble = scanner.nextLine();
//            if ("y".equals(judgeSymble)){
//                continue;
//            }
//            for (MaterialInfo materialInfo:materialInfos){
//                String materialCode = materialInfo.getCode();
//                String materialName = materialInfo.getName();
//                String model = materialInfo.getModel();
//                logger.info("是否将materialCode={},materialName={} 的数据从主数据同步到分销系统的供应商 code={}name={}的数据里?(y/n)",
//                        materialCode,materialName,productCodeDb,productNameDb);
//
//                String judgeInfo = scanner.nextLine();
//                if ("y".equals(judgeInfo)) {
//                    productPriceDao.updateEasInfo(productCodeDb,materialCode,materialName,model);
//                    break;
//                }
//            }
//        }
    }

    @Override
    public void synOrganizeInfo(String keywords) throws IOException {

    }

    @Override
    public void synCurrencyInfo(String keywords) throws IOException {

    }

    @Override
    public void synUnitInfo(String keywords) throws IOException {
        logger.info("开始同步客户信息信息");
        //1)拼地址
        String url = FoundationConstant.EAS_URL;
        String method = FoundationConstant.GET_DATAS;
        String address = url + method;
        //2)拼请求数据
        RequestData requestData = new RequestData(FoundationConstant.UNIT, FoundationConstant.SKIP_COUNT, FoundationConstant.MAX_RESULT_COUNT);
        if (keywords != null && !"".equals(keywords)) {
            requestData.setKeywords(keywords);
        }
        String jsonString = JSON.toJSONString(requestData);
        //3)发送请求
        ResultData resultData = OKHttpUtil.postJson(address, jsonString);
        if (resultData==null){
            logger.warn("主数据没有货品={}的数据",keywords);
            return;
        }
        //4)数据转换
        String data = resultData.getData();
        JSONObject jsonObject = JSON.parseObject(data);
        Object items = jsonObject.get("items");
        String itemsString = items.toString();
        ArrayList<EasUnit> units
                = JSON.parseObject(itemsString, new TypeReference<ArrayList<EasUnit>>() {
        });
        //5)数据处理
        Scanner scanner = new Scanner(System.in);
        for (EasUnit unit:units){
            String unitCode = unit.getCode();
            String unitName = unit.getName();
            logger.info("是否将unitCode={},unitName={} 的数据从主数据同步到分销系统?(y/n)",
                    unitCode,unitName);
            String judgeInfo = scanner.nextLine();
            if ("y".equals(judgeInfo)) {
                Unit unitDb = new Unit();
                unitDb.setUnitCode(unitCode);
                unitDb.setUnitName(unitName);
                unitDb.setEasUnitCode(unitCode);
                unitDb.setEasUnitName(unitName);
                unitDb.setRecordStatus(1);
                unitDao.insertUnitInfo(unitDb);
            }
        }
    }
}
