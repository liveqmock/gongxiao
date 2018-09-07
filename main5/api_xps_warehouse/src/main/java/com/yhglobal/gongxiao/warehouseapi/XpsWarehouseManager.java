package com.yhglobal.gongxiao.warehouseapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouseapi.inbound.*;
import com.yhglobal.gongxiao.warehouseapi.model.*;
import com.yhglobal.gongxiao.warehouseapi.outbound.*;
import com.yhglobal.gongxiao.warehouseapi.purchase.PurchaseCreateOutboundRequest;
import com.yhglobal.gongxiao.warehouseapi.purchase.PurchaseCreateOutboundResponse;
import com.yhglobal.gongxiao.warehouseapi.sales.ReturnItem;
import com.yhglobal.gongxiao.warehouseapi.sales.SalesCreateOutboundRequest;
import com.yhglobal.gongxiao.coreutil.OkHttpManager;
import jdk.internal.dynalink.beans.StaticClass;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 负责组装报文 并把发送请求到xps warehouse
 */
public class XpsWarehouseManager {

    private static Logger logger = LoggerFactory.getLogger(XpsWarehouseManager.class);

    /**
     * 插入EAS即时库存
     */
    private static final String INSERT_INVENTORY_RECORD = "/inventory/insertInventory";
    /**
     * 核对EAS即时库存
     */
    private static final String CHECK_EAS_INVENTORY = "/inventory/checkEasInventory";
    /**
     * 查询核对EAS即时库存
     */
    private static final String SELECT_EAS_CHECK_ = "/inventory/selectEasInventoryCheck";
    /**
     * 通知wms关闭出库单
     */
    private static final String ClOSE_OUTBOUND_ORDER = "/close/outStock";
    /**
     * 通知wms关闭入库单
     */
    private static final String CLOSE_INBOUND_ORDER = "/close/outStock";
    /**
     * 通知wms取消出库单
     */
    private static final String CANCEL_OUTBOUND_ORDER = "/cancel/outStock";
    /**
     * 通知wms取消入库单
     */
    private static final String CANCEL_INBOUND_ORDER = "/cancel/inStock";

    /************************************WMS出入库通知单页面*****************************/
    /**
     * 查询所有的wms入库通知单
     */
    private static final String SELECT_WMS_INBOUND_ORDER = "/warehouseManage/selectWmsInboundNotifyOrder";
    /**
     * 查询所有的wms入库通知单基础信息
     */
    private static final String SELECT_WMS_INBOUND_BASIC_ORDER = "/warehouseManage/selectWmsInboundNotifyByOrderNo";
    /**
     * 根据wms入库通知单查询入库单明细
     */
    private static final String SELECT_WMS_INBOUND_ORDER_ITEM = "/warehouseManage/selectWmsInboundNotifyOrderItem";


    /************************************WMS出出库通知单页面*****************************/
    /**
     * 查询所有的wms出库通知单
     */
    private static final String SELECT_WMS_OUTBOUND_ORDER = "/warehouseManage/selectWmsOutboundOrder";
    /**
     * 查询所有的wms出库通知单基础信息
     */
    private static final String SELECT_WMS_OUTBOUND_BASIC_ORDER = "/warehouseManage/selectWmsByOutboundNum";
    /**
     * 根据wms出库通知单查询入库单明细
     */
    private static final String SELECT_WMS_OUTBOUND_ORDER_ITEM = "/warehouseManage/selectWmsOutboundOrderItem";


    /********************************采购和销退创建入库单**************************/
    /**
     * 采购模块 创建入库单
     */
    private static final String CREATE_INBOUND_ORDER = "/warehouse/createInboundOrder";
    /**
     * 销售模块 退货时创建入库单
     */
    private static final String CREATE_INBOUND_ORDER_2 = "/warehouse/createInboundOrder2";

    /**
     * 根据采购单号查询入库单明细
     */
    private static final String GET_INBOUND_ITEM_BY_PURCHASE_NO = "/warehouse/getInboundItemRecord";


    /************************************仓储管理的入库单管理页面***************************/
    /**
     * 查询所有的入库单
     */
    private static final String SELECT_INBOUND_ALL = "/inbound/selectInboundOrderselect";
    /**
     * 根据入库单号查询入库单
     */
    private static final String SELECT_INBOUND_BY_GONGXIAONO = "/inbound/selectInboundByInboundNum";
    /**
     * 根据入库单查询入库单明细
     */
    private static final String SELECT_INBOUND_ORDERITEM = "/inbound/selectInboundOrderItem";


    /************************************仓储管理的出库单管理页面***************************/
    /**
     * 查询所有的出库单
     */
    private static final String SELECT_OUTBOUND_ORDER = "/outbound/selectOutboundOrder";
    /**
     * 根据出库单号查询出库单
     */
    private static final String SELECT_BY_OUTBOUND_NUM = "/outbound/selectByOutboundNum";
    /**
     * 根据条件查询出库单明细
     */
    private static final String SELECT_OUTBOUND_ORDER_ITEM = "/outbound/selectOutboundOrderItem";

    /**
     * 根据销售单号查询出库单基本信息
     */
    private static final String SELECT_RECORD_BY_SALESNO = "/outbound/selectRecordBySalesNo";

    /**
     * 根据出库单号查询 出库单明细
     */
    private static final String SELECT_RECORDITEM_BY_OUTBOUND_ORDERNO = "/outbound/selectRecordItemByOutboundOrderNo";

    /**
     * 销售退货的时候 通知仓存模块修改可退货数量
     */
    private static final String NOTIFY_WAREHOUSE_RETURN = "/outbound/modifyReturnQantity";

    /**
     * 根据销售单号和商品编码 查询出库单
     */
    private static final String SELECT_RECORD_BY_SALSENO_AND_PRODUCT = "/outbound/selectRecordBySalseNoAndProduct";


    /************************************仓储管理的其他入库单管理页面***************************/
    /**
     * 查询所有的手工入库单（其它入库单）
     */
    private static final String getManualOrderList = "/manualInbound/getManualOrderList";
    /**
     * 添加手工入库单(其它入库单)
     */
    private static final String INSERT_MANUAL_INBOUND_ORDER = "/manualInbound/insertManualInboundOrder";

    /************************************仓储管理的其他出库单管理页面***************************/

    /**
     * 新增其他出库单(其它出库单)
     */
    private static final String INSERT_MANUAL_OUTBOUND_ORDER = "/manualOutbound/insertManualOutboundOrder";


    /************************************销售模块创建出库单 采购退货创建出库单***************************/
    /**
     * 采购退货 创建出库单
     */
    private static final String PURCHASE_CREATE_OUTBOUND_ORDER = "/warehouse/createOutboundOrder";

    /**
     * 销售出库 创建出库单
     */
    private static final String SALES_CREATE_OUTBOUND_ORDER2 = "/warehouse/createOutboundOrder2";

    /**
     * 出库单签收
     */
    private static final String SURE_SIGHIN = "/warehouse/sureSighIn";


    /**************************************WMS_notify 模块的接口************************/

    /**
     * wms入库通知单 确认信息
     */
    private static final String confirmWmsInbound = "/wms/confirmWmsInbound";
    /**
     * wms出库通知单 确认信息
     */
    private static final String confirmWmsOutbound = "/wms/confirmWmsOutbound";


    /***************************************库存模块的controller****************************/
    /**
     * 根据商品查询批次库存
     */
    private static final String getBatchDetail = "/inventory/getBatchDetail";

    /**
     * 根据仓库查询批次库存
     */
    private static final String getBatchDetailByWarehouse = "/inventory/getBatchDetailByWarehouse";

    /**
     * 库存模块，商品库存查询
     */
    private static final String selectProductInventoryInfo = "/inventoryQuerry/product";

    /**
     * 库存模块，仓库库存查询
     */
    private static final String selecWarehouseInventoryInfo = "/inventoryQuerry/stock";

    /**
     * 库存模块，进销存台账查询
     */
    private static final String selectAccountInfo = "/inventoryAccount/standFlow";

    /**
     * 库存模块，出入库流水查询
     */
    private static final String selectInventoryFlow = "/InventoryFlow/selectFlow";

    /**
     * 库存模块，核对库存查询
     */
    private static final String selectCheckInventory = "/duizhan/checkinventory";

    /**
     * 库存模块，核对库存查询
     */
    private static final String getproductQuqntity = "/inventoryQuerry/productQuantity";

    /**
     * 库存模块，查询批次库存库龄
     */
    private static final String INVENTORY_AGE = "/inventoryAge/getAgeInfo";


    /**********************************调拨单管理**************************************/
    /**
     * 调拨单管理,查询调拨单
     */
    private static final String SELECT_ALLOCATE_ORDER = "/allocte/selectAllocateOrder";
    /**
     * 调拨单管理,根据单号查询调拨单
     */
    private static final String selectInfoByAllocateNo = "/allocte/selectByAllocateNo";

    /**
     * 调拨单管理,查询调拨单明细  新增调拨单
     */
    private static final String selectItemByAllocateNo = "/allocte/selectItemByAllocateNo";

    /**
     * 新增调拨单
     */
    private static final String INSERT_ALLOCATE_ORDER = "/allocte/insertAllocateOrder";

    /**
     * 获取psi数据
     */
    private static final String getPsiData = "/report/getPSIData";

    /**
     *
     */
    private static final String GET_WHOLE_INVENTORY_AGE = "/wholeinventoryAge/getWholeinventoryAge";

    /**
     *
     */
    private static final String GET_180_INVENTORY_AGE = "/inventory180Age/get180Age";

    /**
     * 插入EAS即时库存
     */
    public static GongxiaoResult insertInventory(String xpsWarehouseEntryUrl,
                                                        String channelId,
                                                        String channelToken,
                                                        String projectId) {

        logger.debug("[insertInventory] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, warehouseId={}, gongxiaoOutboundOrderNo={}, productCode={}", xpsWarehouseEntryUrl, channelId, projectId);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s projectId=%s", channelId, projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        if (channelId == null) { //项目id不能为空
            String msg = String.format("channelId can not be empty: channelId=%s projectId=%s", channelId, projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        //2. 组装请求
        String jsonString = "?projectId=" + projectId;
        logger.info("#xps warehouse request#: {}", jsonString);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + INSERT_INVENTORY_RECORD + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 核对EAS即时库存
     */
    public static GongxiaoResult checkEasInventory(String xpsWarehouseEntryUrl,
                                                 String channelId,
                                                 String channelToken,
                                                 String projectId) {

        logger.debug("[checkEasInventory] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, warehouseId={}, gongxiaoOutboundOrderNo={}, productCode={}", xpsWarehouseEntryUrl, channelId, projectId);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s projectId=%s", channelId, projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        if (channelId == null) { //项目id不能为空
            String msg = String.format("channelId can not be empty: channelId=%s projectId=%s", channelId, projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        //2. 组装请求
        String jsonString = "?projectId=" + projectId;
        logger.info("#xps warehouse request#: {}", jsonString);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + CHECK_EAS_INVENTORY + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 查询核对EAS即时库存
     */
    public static GongxiaoResult selectEasInventoryCheck(String xpsWarehouseEntryUrl,
                                                   String channelId,
                                                   String channelToken,
                                                   String projectId) {

        logger.debug("[checkEasInventory] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, warehouseId={}, gongxiaoOutboundOrderNo={}, productCode={}", xpsWarehouseEntryUrl, channelId, projectId);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s projectId=%s", channelId, projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        if (channelId == null) { //项目id不能为空
            String msg = String.format("channelId can not be empty: channelId=%s projectId=%s", channelId, projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        //2. 组装请求
        String jsonString = "?projectId=" + projectId;
        logger.info("#xps warehouse request#: {}", jsonString);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_EAS_CHECK_ + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 销售模块 关闭出库订单
     */
    public static CloseOrderResponse closeOutboundOrder(String xpsWarehouseEntryUrl,
                                                        String channelId,
                                                        String channelToken,
                                                        String projectId,
                                                        String warehouseId,
                                                        String gongxiaoOutboundOrderNo,
                                                        String productCode) {

        logger.debug("[closeOutboundOrder] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, warehouseId={}, gongxiaoOutboundOrderNo={}, productCode={}", xpsWarehouseEntryUrl, channelId, projectId, warehouseId, gongxiaoOutboundOrderNo, productCode);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s gongxiaoOutboundOrderNo=%s", channelId, gongxiaoOutboundOrderNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        if (channelId == null) { //项目id不能为空
            String msg = String.format("channelId can not be empty: channelId=%s gongxiaoOutboundOrderNo=%s", channelId, gongxiaoOutboundOrderNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        //2. 组装请求
        String jsonString = "?projectId=" + projectId + "&warehouseId=" + warehouseId + "&gongxiaoOutboundOrderNo=" + gongxiaoOutboundOrderNo + "&productCode=" + productCode;
        logger.info("#xps warehouse request#: {}", jsonString);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + ClOSE_OUTBOUND_ORDER + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, CloseOrderResponse.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 销售模块 关闭入库订单
     */
    public static CloseOrderResponse closeInboundOrder(String xpsWarehouseEntryUrl,
                                                       String channelId,
                                                       String channelToken,
                                                       String projectId,
                                                       String warehouseId,
                                                       String gongxiaoInboundOrderNo) {

        logger.debug("[closeInboundOrder] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, warehouseId={}, gongxiaoOutboundOrderNo={}, productCode={}", xpsWarehouseEntryUrl, channelId, projectId, warehouseId, gongxiaoInboundOrderNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s gongxiaoOutboundOrderNo=%s", channelId, gongxiaoInboundOrderNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        //组装请求JSON
        String jsonString = "?projectId=" + projectId + "&warehouseId=" + warehouseId + "&gongxiaoInboundOrderNo=" + gongxiaoInboundOrderNo;
        logger.info("#xps warehouse request#: {}", jsonString);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + CLOSE_INBOUND_ORDER + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, CloseOrderResponse.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 通知wms取消出库单
     */
    public static CancelOrderResponse cancelOutboundOrder(String xpsWarehouseEntryUrl,
                                                          String channelId,
                                                          String channelToken,
                                                          String projectId,
                                                          String warehouseId,
                                                          String gongxiaoOutboundOrderNo) {

        logger.debug("[cancelOutboundOrder] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, gongxiaoOutboundOrderNo={}, productCode={}", xpsWarehouseEntryUrl, channelId, projectId, gongxiaoOutboundOrderNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s gongxiaoOutboundOrderNo=%s", channelId, gongxiaoOutboundOrderNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        //TODO: 校验channelId/channelToken
        //组装请求JSON
        String jsonString = "?projectId=" + projectId + "&warehouseId=" + warehouseId + "&gongxiaoOutboundOrderNo=" + gongxiaoOutboundOrderNo;

        logger.info("#xps warehouse request#: {}", jsonString);
        MediaType jsonMediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + CANCEL_OUTBOUND_ORDER + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, CancelOrderResponse.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
        //*/
    }


    /**
     * 查询所有的wms入库通知单
     */
    public static GongxiaoResult selectWmsInboundNotifyOrder(String xpsWarehouseEntryUrl, String channelId, String channelToken,
                                                             String projectId, String gonxiaoInboundNo, String batchNo,
                                                             String createTimeBegin, String createTimeTo,
                                                             String warehouseId, String supplier, int pageNumber, int pageSize) {

        logger.debug("[selectWmsInboundNotifyOrder] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, gongxiaoOutboundOrderNo={}, productCode={}", xpsWarehouseEntryUrl, channelId, projectId, gonxiaoInboundNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s gongxiaoOutboundOrderNo=%s", channelId, gonxiaoInboundNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String jsonString = "?projectId=" + projectId + "&gonxiaoInboundNo=" + gonxiaoInboundNo + "&batchNo=" + batchNo + "&createTimeBegin=" + createTimeBegin + "&createTimeTo=" + createTimeTo + "&warehouseId=" + warehouseId + "&supplier=" + supplier + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_WMS_INBOUND_ORDER + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
        //*/
    }

    /**
     * 查询所有的wms入库通知单基础信息
     */
    public static GongxiaoResult selectWmsInboundNotifyByOrderNo(String xpsWarehouseEntryUrl,
                                                                 String channelId,
                                                                 String channelToken,
                                                                 String projectId,
                                                                 String gongxiaoInboundOrderNo,
                                                                 String wmsInboundOrderNo) {

        logger.debug("[selectWmsInboundNotifyByOrderNo] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, gongxiaoOutboundOrderNo={}, wmsInboundOrderNo={}", xpsWarehouseEntryUrl, channelId, projectId, gongxiaoInboundOrderNo, wmsInboundOrderNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s gongxiaoInboundOrderNo=%s", channelId, gongxiaoInboundOrderNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String jsonString = "?projectId=" + projectId + "&gongxiaoInboundOrderNo=" + gongxiaoInboundOrderNo + "&wmsInboundOrderNo=" + wmsInboundOrderNo;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_WMS_INBOUND_BASIC_ORDER + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
        //*/
    }

    /**
     * 根据wms入库通知单查询入库单明细
     */
    public static GongxiaoResult selectWmsInboundNotifyOrderItem(String xpsWarehouseEntryUrl,
                                                                 String channelId,
                                                                 String channelToken,
                                                                 String projectId,
                                                                 String gongxiaoInboundOrderNo,
                                                                 String wmsInboundOrderNo) {

        logger.debug("[selectWmsInboundNotifyOrderItem] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, gongxiaoInboundOrderNo={},wmsInboundOrderNo={}", xpsWarehouseEntryUrl, channelId, projectId, gongxiaoInboundOrderNo, wmsInboundOrderNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s gongxiaoInboundOrderNo=%s", channelId, gongxiaoInboundOrderNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String jsonString = "?projectId=" + projectId + "&gongxiaoInboundOrderNo=" + gongxiaoInboundOrderNo + "&wmsInboundOrderNo=" + wmsInboundOrderNo;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_WMS_INBOUND_ORDER_ITEM + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
        //*/
    }

    /**
     * 查询所有的wms出库通知单
     */
    public static GongxiaoResult selectWmsOutboundOrder(String xpsWarehouseEntryUrl,
                                                        String channelId,
                                                        String channelToken,
                                                        String projectId,
                                                        String gongxiaoOutNo,
                                                        String salesNo,
                                                        String orderType,
                                                        String customer,
                                                        String createTimeBeging,
                                                        String createTimeLast,
                                                        int pageNumber,
                                                        int pageSize) {

        logger.debug("[selectWmsOutboundOrder] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, gongxiaoOutboundOrderNo={}", xpsWarehouseEntryUrl, channelId, projectId, gongxiaoOutNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s gongxiaoOutNo=%s", channelId, gongxiaoOutNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String jsonString = "?projectId=" + projectId + "&gongxiaoOutNo=" + gongxiaoOutNo + "&salesNo=" + salesNo + "&orderType=" + orderType + "&customer=" + customer + "&createTimeBeging=" + createTimeBeging + "&createTimeLast=" + createTimeLast + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_WMS_OUTBOUND_ORDER + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
        //*/
    }

    /**
     * 查询所有的wms出库通知单基础信息
     */
    public static GongxiaoResult selectWmsByOutboundNum(String xpsWarehouseEntryUrl,
                                                        String channelId,
                                                        String channelToken,
                                                        String projectId,
                                                        String gongxiaoOutboundOrderNo,
                                                        String wmsOutboundOrderNo) {

        logger.debug("[selectWmsByOutboundNum] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, gongxiaoOutboundOrderNo={}, productCode={}", xpsWarehouseEntryUrl, channelId, projectId, gongxiaoOutboundOrderNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s gongxiaoOutboundOrderNo=%s", channelId, gongxiaoOutboundOrderNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String jsonString = "?projectId=" + projectId + "&gongxiaoOutboundOrderNo=" + gongxiaoOutboundOrderNo + "&wmsOutboundOrderNo=" + wmsOutboundOrderNo;

        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_WMS_OUTBOUND_BASIC_ORDER + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
        //*/
    }

    /**
     * 根据wms出库通知单查询出库单明细
     */
    public static GongxiaoResult selectWmsOutboundOrderItem(String xpsWarehouseEntryUrl,
                                                            String channelId,
                                                            String channelToken,
                                                            String projectId,
                                                            String gongxiaoOutboundOrderNo,
                                                            String wmsOutboundOrderNo) {

        logger.debug("[selectWmsOutboundOrderItem] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, gongxiaoOutboundOrderNo={}, productCode={}", xpsWarehouseEntryUrl, channelId, projectId, gongxiaoOutboundOrderNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s gongxiaoOutboundOrderNo=%s", channelId, gongxiaoOutboundOrderNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        //TODO: 校验channelId/channelToken
        String jsonString = "?projectId=" + projectId + "&gongxiaoOutboundOrderNo=" + gongxiaoOutboundOrderNo + "&wmsOutboundOrderNo=" + wmsOutboundOrderNo;

        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_WMS_OUTBOUND_ORDER_ITEM + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
        //*/
    }

    /**
     * 通知wms取消入库单
     */
    public static CancelOrderResponse cancelInboundOrder(String xpsWarehouseEntryUrl,
                                                         String channelId,
                                                         String channelToken,
                                                         String projectId,
                                                         String warehouseId,
                                                         String gongxiaoInboundOrderNo) {

        logger.debug("[cancelInboundOrder] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={},warehouseId={},gongxiaoOutboundOrderNo={}", xpsWarehouseEntryUrl, channelId, projectId, warehouseId, gongxiaoInboundOrderNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s gongxiaoInboundOrderNo=%s", channelId, gongxiaoInboundOrderNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        //TODO: 校验channelId/channelToken

        //组装请求JSON


        String jsonString = "?projectId=" + projectId + "&warehouseId=" + warehouseId + "&gongxiaoInboundOrderNo=" + gongxiaoInboundOrderNo;
        logger.info("#xps warehouse request#: {}", jsonString);
        MediaType jsonMediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + CANCEL_INBOUND_ORDER + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, CancelOrderResponse.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
        //*/
    }


    /**
     * 根据销售单号查询出库单基本信息
     */
    public static List<OutboundOrder> selectRecordBySalesNo(String xpsWarehouseEntryUrl,
                                                            String channelId,
                                                            String channelToken,
                                                            String projectId,
                                                            String salesNo) {

        logger.debug("[cancelInboundOrder] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, salesNo={}", xpsWarehouseEntryUrl, channelId, projectId, salesNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s salesNo=%s", channelId, salesNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }
        if (salesNo == null) { //项目id不能为空
            String msg = String.format("salesNo can not be empty: channelId=%s salesNo=%s", channelId, salesNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String jsonString = "?projectId=" + projectId + "&salesNo=" + salesNo;

        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_RECORD_BY_SALESNO + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseArray(content, OutboundOrder.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
        //*/
    }

    /**
     * 销售模块 退货时创建入库单
     */
    public static GongxiaoResult createInboundOrder2(String xpsWarehouseEntryUrl,
                                                     String channelId,
                                                     String channelToken,
                                                     String orderSourceNo,
                                                     String sourceChannelId,
                                                     int inboundType,
                                                     String projectId,
                                                     String traceNo,
                                                     String senderName,
                                                     String senderPhoneNo,
                                                     String warehouseId,
                                                     String warehouseName,
                                                     String deliverAddress,
                                                     int shippingMode,
                                                     String logisticsCompanyName,
                                                     String logisticsNo,
                                                     String note,
                                                     int totalQuantity,
                                                     List<PlanInboundOrderItem> itemList,
                                                     String signature,
                                                     String uniqueNo) {

        logger.debug("[createInboundOrder2] params: xpsWarehouseEntryUrl={}, channelId={}, orderSourceNo={},sourceChannelId={},projectId={},traceNo={},senderName={},senderPhoneNo={},warehouseId={},warehouseName={},phoneNum={},deliverAddress={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={}，uniqueNo={}",
                xpsWarehouseEntryUrl, channelId, projectId, orderSourceNo, sourceChannelId, projectId, traceNo, senderName, senderPhoneNo, warehouseId, warehouseName, deliverAddress, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, JSON.toJSONString(itemList), signature, uniqueNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s gongxiaoOutboundOrderNo=%s", channelId, orderSourceNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }
        CreateInboundOrder2Request createInboundOrder2Request = new CreateInboundOrder2Request();
        createInboundOrder2Request.setOrderSourceNo(orderSourceNo);
        createInboundOrder2Request.setSourceChannelId(sourceChannelId);
        createInboundOrder2Request.setInboundType(inboundType);
        createInboundOrder2Request.setProjectId(projectId);
        createInboundOrder2Request.setTraceNo(traceNo);
        createInboundOrder2Request.setSenderName(senderName);
        createInboundOrder2Request.setSenderPhoneNo(senderPhoneNo);
        createInboundOrder2Request.setWarehouseId(warehouseId);
        createInboundOrder2Request.setWarehouseName(warehouseName);
        createInboundOrder2Request.setDeliverAddress(deliverAddress);
        createInboundOrder2Request.setShippingMode(shippingMode);
        createInboundOrder2Request.setLogisticsNo(logisticsNo);
        createInboundOrder2Request.setLogisticsCompanyName(logisticsCompanyName);
        createInboundOrder2Request.setNote(note);
        createInboundOrder2Request.setTotalQuantity(totalQuantity);
        createInboundOrder2Request.setItemList(itemList);
        createInboundOrder2Request.setSignature(signature);
        String jsonString = JSON.toJSONString(createInboundOrder2Request);
        logger.info("#xps warehouse request#: {}", jsonString);
        MediaType jsonMediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + CREATE_INBOUND_ORDER_2)
                .post(requestBody)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);

            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

        return null;
    }

    /**
     * 根据出库单号查询 出库单明细
     */
    public static List<OutboundOrderItem> selectRecordItemByOutboundOrderNo(String xpsWarehouseEntryUrl,
                                                                            String channelId,
                                                                            String channelToken,
                                                                            String projectId,
                                                                            String gongxiaoOutboundOrderNo) {

        logger.debug("[selectRecordItemByOutboundOrderNo] params: xpsWarehouseEntryUrl={}, channelId={}, orderSourceNo={},sourceChannelId={},projectId={}, gongxiaoOutboundOrderNo={}, ",
                xpsWarehouseEntryUrl, channelId, channelToken, projectId, gongxiaoOutboundOrderNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s gongxiaoOutboundOrderNo=%s", channelId, gongxiaoOutboundOrderNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String jsonString = "?projectId=" + projectId + "&gongxiaoOutboundOrderNo=" + gongxiaoOutboundOrderNo;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_RECORDITEM_BY_OUTBOUND_ORDERNO + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseArray(content, OutboundOrderItem.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 销售退货的时候 通知仓存模块修改可退货数量
     */
    public static GongxiaoResult modifyReturnQuantity(String xpsWarehouseEntryUrl,
                                                      String channelId,
                                                      String channelToken,
                                                      List<ReturnItem> returnItemList) {

        logger.debug("[selectRecordItemByOutboundOrderNo] params: xpsWarehouseEntryUrl={}, channelId={}, orderSourceNo={},returnItemList={} ",
                xpsWarehouseEntryUrl, channelId, channelToken, JSON.toJSONString(returnItemList));

        String jsonString = JSON.toJSONString(returnItemList);
        logger.info("#xps warehouse request#: {}", jsonString);
        MediaType jsonMediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + NOTIFY_WAREHOUSE_RETURN)
                .post(requestBody)
                .build();

        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 添加手工入库单(其它入库单)
     */
    public static GongxiaoResult insertManualInboundOrder(String xpsWarehouseEntryUrl,
                                                          String channelId,
                                                          String channelToken,
                                                          String projectId,
                                                          String warehouseId,
                                                          String warehouseName,
                                                          String recieveAddress,
                                                          String supplierName,
                                                          String businessDate,
                                                          String remark,
                                                          int inboundType,
                                                          String purchaseItemInfoJson) {

        logger.debug("[insertInbound] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={},warehouseId={},warehouseName={}, supplierName={}, businessDate={},remark={},inboundType={},purchaseItemInfoJson={}",
                xpsWarehouseEntryUrl, channelId, channelToken, projectId, warehouseId, warehouseName, recieveAddress, supplierName, businessDate, remark, inboundType, purchaseItemInfoJson);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s ", channelId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        //组装请求JSON
        InsertManualInboundRequest insertManualInboundRequest = new InsertManualInboundRequest();
        insertManualInboundRequest.setProjectId(projectId);
        insertManualInboundRequest.setWarehouseId(warehouseId);
        insertManualInboundRequest.setWarehouseName(warehouseName);
        insertManualInboundRequest.setRecieveAddress(recieveAddress);
        insertManualInboundRequest.setSupplierName(supplierName);
        insertManualInboundRequest.setBusinessDate(businessDate);
        insertManualInboundRequest.setRemark(remark);
        insertManualInboundRequest.setInboundType(inboundType);
        insertManualInboundRequest.setPurchaseItemInfoJson(purchaseItemInfoJson);

        String jsonString = JSON.toJSONString(insertManualInboundRequest);
        logger.info("#xps warehouse request#: {}", jsonString);
        MediaType jsonMediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + INSERT_MANUAL_INBOUND_ORDER)
                .post(requestBody)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 新增其他出库单(其它出库单)
     */
    public static GongxiaoResult insertManualOutboundOrder(String xpsWarehouseEntryUrl,
                                                           String channelId,
                                                           String channelToken,
                                                           String projectId,
                                                           String warehouseId,
                                                           String warehouseName,
                                                           String delieverAddress,
                                                           String supplierName,
                                                           String businessDate,
                                                           int outboundType,
                                                           String remark,
                                                           String itemsInfo) {

        logger.debug("[insertInbound] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, warehouseId={}, warehouseName={}, delieverAddress={}, supplierName={}, businessDate={}, outboundType={}, remark={}, itemsInfo={}",
                xpsWarehouseEntryUrl, channelId, channelToken, projectId, warehouseId, warehouseName, delieverAddress, supplierName, businessDate, outboundType, remark, itemsInfo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s ", channelId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        //组装请求JSON
        InsertManualOutboundRequest insertManualOutboundRequest = new InsertManualOutboundRequest();
        insertManualOutboundRequest.setProjectId(projectId);
        insertManualOutboundRequest.setWarehouseId(warehouseId);
        insertManualOutboundRequest.setWarehouseName(warehouseName);
        insertManualOutboundRequest.setDelieverAddress(delieverAddress);
        insertManualOutboundRequest.setSupplierName(supplierName);
        insertManualOutboundRequest.setBusinessDate(businessDate);
        insertManualOutboundRequest.setRemark(remark);
        insertManualOutboundRequest.setOutboundType(outboundType);
        insertManualOutboundRequest.setSupplierName(supplierName);
        insertManualOutboundRequest.setRemark(remark);
        insertManualOutboundRequest.setItemsInfo(itemsInfo);

        String jsonString = JSON.toJSONString(insertManualOutboundRequest);
        logger.info("#xps warehouse request#: {}", jsonString);
        MediaType jsonMediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + INSERT_MANUAL_OUTBOUND_ORDER)
                .post(requestBody)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }


    /**
     * 根据销售单号和商品编码 查询出库单
     */
    public static OutboundOrderItem selectRecordBySalseNoAndProduct(String xpsWarehouseEntryUrl,
                                                                    String channelId,
                                                                    String channelToken,
                                                                    String projectId,
                                                                    String salesOrderNo,
                                                                    String productCode) {

        logger.debug("[selectRecordItemByOutboundOrderNo] params: xpsWarehouseEntryUrl={}, channelId={}, orderSourceNo={},sourceChannelId={},projectId={}, salesOrderNo={}, productCode={}",
                xpsWarehouseEntryUrl, channelId, channelToken, projectId, salesOrderNo, productCode);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s salesOrderNo=%s", channelId, salesOrderNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String jsonString = "?projectId=" + projectId + "&salesOrderNo=" + salesOrderNo + "&productCode=" + productCode;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_RECORD_BY_SALSENO_AND_PRODUCT + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, OutboundOrderItem.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 采购模块 创建入库单
     */
    public static GongxiaoResult createInboundOrder(String xpsWarehouseEntryUrl, String channelId, String channelToken,
                                                    Date expectArrivalTime, int purchaseType,
                                                    String orderSourceNo,
                                                    int inboundType, String projectId,
                                                    String traceNo, String senderName,
                                                    String senderPhoneNo, String warehouseId,
                                                    String warehouseName, String deliverAddress,
                                                    int shippingMode, String logisticsCompanyName,
                                                    String logisticsNo, String note, int totalQuantity,
                                                    List<PlanInboundOrderItem> itemList, String signature,
                                                    String uniqueNo) {

        logger.debug("[createInboundOrder] params: xpsWarehouseEntryUrl={}, channelId={}, expectArrivalTime={},purchaseType={},orderSourceNo={},sourceChannelId={},projectId={},traceNo={},senderName={},senderPhoneNo={},warehouseId={},warehouseName={},phoneNum={},deliverAddress={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={}，uniqueNo={}",
                xpsWarehouseEntryUrl, channelId, expectArrivalTime, purchaseType, projectId, orderSourceNo, channelId, projectId, traceNo, senderName, senderPhoneNo, warehouseId, warehouseName, deliverAddress, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, JSON.toJSONString(itemList), signature, uniqueNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s gongxiaoOutboundOrderNo=%s", channelId, orderSourceNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }
        CreateInboundOrderRequest createInboundOrderRequest = new CreateInboundOrderRequest();
        createInboundOrderRequest.setExpectArrivalTime(expectArrivalTime);
        createInboundOrderRequest.setOrderSourceNo(orderSourceNo);
        createInboundOrderRequest.setPurchaseType(purchaseType);
        createInboundOrderRequest.setSourceChannelId(channelId);
        createInboundOrderRequest.setInboundType(inboundType);
        createInboundOrderRequest.setProjectId(projectId);
        createInboundOrderRequest.setTraceNo(traceNo);
        createInboundOrderRequest.setSenderName(senderName);
        createInboundOrderRequest.setSenderPhoneNo(senderPhoneNo);
        createInboundOrderRequest.setWarehouseId(warehouseId);
        createInboundOrderRequest.setWarehouseName(warehouseName);
        createInboundOrderRequest.setDeliverAddress(deliverAddress);
        createInboundOrderRequest.setShippingMode(shippingMode);
        createInboundOrderRequest.setLogisticsNo(logisticsNo);
        createInboundOrderRequest.setLogisticsCompanyName(logisticsCompanyName);
        createInboundOrderRequest.setNote(note);
        createInboundOrderRequest.setTotalQuantity(totalQuantity);
        createInboundOrderRequest.setItemList(itemList);
        createInboundOrderRequest.setSignature(signature);
        createInboundOrderRequest.setUniqueNo(uniqueNo);
        String jsonString = JSON.toJSONString(createInboundOrderRequest);
        logger.info("#xps warehouse request#: {}", jsonString);
        MediaType jsonMediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + CREATE_INBOUND_ORDER)
                .post(requestBody)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return null;
    }

    /**
     * 根据采购单号查询入库单明细
     */
    public static List<InboundOrderItem> getInboundItemRecord(String xpsWarehouseEntryUrl, String channelId, String channelToken,
                                                              String projectId, String purchaseOrderNo) {

        logger.debug("[getInboundItemRecord] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={},purchaseOrderNo={}",
                xpsWarehouseEntryUrl, channelId, projectId, purchaseOrderNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s purchaseOrderNo=%s", channelId, purchaseOrderNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String jsonString = "?projectId=" + projectId + "&purchaseOrderNo=" + purchaseOrderNo;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + GET_INBOUND_ITEM_BY_PURCHASE_NO + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseArray(content, InboundOrderItem.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 查询所有的入库单
     */
    public static GongxiaoResult selectInboundOrderselect(String xpsWarehouseEntryUrl, String channelId, String channelToken,
                                                          String projectId,
                                                          String gonxiaoInboundNo,
                                                          String purchaseNo,
                                                          String productCode,
                                                          String goodCode,
                                                          String dateTime,
                                                          String warehouseId,
                                                          String supplier,
                                                          int pageNumber,
                                                          int pageSize) {

        logger.debug("[selectInboundOrderselect] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={},gonxiaoInboundNo={}, purchaseNo={}, productCode={}, goodCode={}, dateTime={}, warehouseId={}, supplier={},pageNumber={},pageSize={}",
                xpsWarehouseEntryUrl, channelId, projectId, gonxiaoInboundNo, purchaseNo, productCode, goodCode, dateTime, warehouseId, supplier, pageNumber, pageSize);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s purchaseOrderNo=%s", channelId, gonxiaoInboundNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String jsonString = "?projectId=" + projectId + "&gonxiaoInboundNo=" + gonxiaoInboundNo + "&purchaseNo=" + purchaseNo + "&productCode=" + productCode + "&goodCode=" + goodCode + "&dateTime=" + dateTime + "&warehouseId=" + warehouseId + "&supplier=" + supplier + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_INBOUND_ALL + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 根据入库单号查询入库单
     */
    public static GongxiaoResult selectInboundByInboundNum(String xpsWarehouseEntryUrl, String channelId, String channelToken,
                                                           String projectId, String gonxiaoInboundOrderNo) {
        logger.debug("[selectInboundByInboundNum] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={},gonxiaoInboundOrderNo={}",
                xpsWarehouseEntryUrl, channelId, projectId, gonxiaoInboundOrderNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s gonxiaoInboundOrderNo=%s", channelId, gonxiaoInboundOrderNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_INBOUND_BY_GONGXIAONO + "?projectId=" + projectId + "&inventoryNum=" + gonxiaoInboundOrderNo)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 根据入库单查询入库单明细
     */
    public static GongxiaoResult selectInboundOrderItem(String xpsWarehouseEntryUrl, String channelId, String channelToken,
                                                        String projectId, String inventoryNum) {

        logger.debug("[selectInboundOrderItem] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={},inventoryNum={}",
                xpsWarehouseEntryUrl, channelId, projectId, inventoryNum);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s inventoryNum=%s", channelId, inventoryNum);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String jsonString = "?projectId=" + projectId + "&inventoryNum=" + inventoryNum;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_INBOUND_ORDERITEM + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 查询所有的出库单
     */
    public static GongxiaoResult selectOutboundOrder(String xpsWarehouseEntryUrl, String channelId, String channelToken,
                                                     String projectId,
                                                     String gongxiaoOutNo,
                                                     String salseNo,
                                                     String createTimeBeging,
                                                     String createTimeLast,
                                                     String warehouseId,
                                                     String productCode,
                                                     String finishTimeBegin,
                                                     String finishTimeLast,
                                                     String supplier,
                                                     String customer,
                                                     int pageNumber,
                                                     int pageSize) {

        logger.debug("[selectOutboundOrder] params: xpsWarehouseEntryUrl={},channelId={},projectId={},gongxiaoOutNo={},salseNo={},createTimeBeging={},createTimeLast={},warehouseId={},productCode={},finishTimeBegin={},finishTimeLast={},supplier={},customer={},pageNumber={}pageSize={}",
                xpsWarehouseEntryUrl, channelId, projectId, gongxiaoOutNo, salseNo, createTimeBeging, createTimeLast, warehouseId, productCode, finishTimeBegin, finishTimeLast, supplier, customer, pageNumber, pageSize);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s gongxiaoOutNo=%s", channelId, gongxiaoOutNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String jsonString = "?projectId=" + projectId + "&gongxiaoOutNo=" + gongxiaoOutNo + "&salseNo=" + salseNo + "&createTimeBeging=" + createTimeBeging + "&createTimeLast=" + createTimeLast + "&warehouseId=" + warehouseId + "&productCode=" + productCode + "&finishTimeBegin=" + finishTimeBegin + "&finishTimeLast=" + finishTimeLast + "&supplier=" + supplier + "&customer=" + customer + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;

        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_OUTBOUND_ORDER + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }


    /**
     * 根据出库单号查询出库单
     */
    public static GongxiaoResult selectByOutboundNum(String xpsWarehouseEntryUrl, String channelId, String channelToken,
                                                     String projectId, String gongxiaoOutboundOrderNo) {

        logger.debug("[selectInboundOrderItem] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={},gongxiaoOutboundOrderNo={}",
                xpsWarehouseEntryUrl, channelId, projectId, gongxiaoOutboundOrderNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s gongxiaoOutboundOrderNo=%s", channelId, gongxiaoOutboundOrderNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String jsonString = "?projectId=" + projectId + "&gongxiaoOutboundOrderNo=" + gongxiaoOutboundOrderNo;

        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_BY_OUTBOUND_NUM + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 根据条件查询出库单明细
     */
    public static GongxiaoResult selectOutboundOrderItem(String xpsWarehouseEntryUrl, String channelId, String channelToken,
                                                         String projectId, String gongxiaoOutboundOrderNo) {

        logger.debug("[selectInboundOrderItem] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={},gongxiaoOutboundOrderNo={}",
                xpsWarehouseEntryUrl, channelId, projectId, gongxiaoOutboundOrderNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s purchaseOrderNo=%s", channelId, gongxiaoOutboundOrderNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String jsonString = "?projectId=" + projectId + "&gongxiaoOutboundOrderNo=" + gongxiaoOutboundOrderNo;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_OUTBOUND_ORDER_ITEM + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 采购退货 创建出库单
     */
    public static PurchaseCreateOutboundResponse purchaseCreateOutboundOrder(String xpsWarehouseEntryUrl, String channelId, String channelToken,
                                                                             String sourceChannelId, int outboundType,
                                                                             String projectId, String customerId,
                                                                             String traceNo, String recipientName,
                                                                             String recipientPhoneNumber, String recipientAddress,
                                                                             String warehouseId, String warehouseName,
                                                                             int shippingMode, String logisticsCompanyName,
                                                                             String logisticsNo, String note,
                                                                             int totalQuantity, List<PlanOutboundOrderItem> itemList,
                                                                             String signature, String uniqueNo,
                                                                             long userId, String userName) {

        logger.debug("[purchaseCreateOutboundOrder] params: xpsWarehouseEntryUrl={}, channelId={}, sourceChannelId={},outboundType={},projectId={},customerId={},traceNo={},recipientName={},recipientPhoneNumber={},recipientAddress={},warehouseId={},warehouseName={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={}，uniqueNo={}",
                xpsWarehouseEntryUrl, channelId, sourceChannelId, outboundType, projectId, customerId, traceNo, recipientName, recipientPhoneNumber, recipientAddress, warehouseId, warehouseName, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, JSON.toJSONString(itemList), signature, uniqueNo);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s purchaseOrderNo=%s", channelId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }
        PurchaseCreateOutboundRequest purchaseCreateOutboundRequest = new PurchaseCreateOutboundRequest(sourceChannelId, outboundType, projectId, customerId, traceNo, recipientName, recipientPhoneNumber, recipientAddress, warehouseId, warehouseName, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, itemList, signature, uniqueNo);
        String jsonString = JSON.toJSONString(purchaseCreateOutboundRequest);
        logger.info("#xps warehouse request#: {}", jsonString);
        MediaType jsonMediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + PURCHASE_CREATE_OUTBOUND_ORDER)
                .post(requestBody)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, PurchaseCreateOutboundResponse.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 销售出库 创建出库单
     */
    public static Map<String, List<OutboundOrderItem>> SalesCreateOutboundOrder2(String xpsWarehouseEntryUrl, String channelId, String channelToken,
                                                                                 String sourceChannelId, int outboundType,
                                                                                 String projectId, String customerId,
                                                                                 String traceNo, String recipientName,
                                                                                 String recipientPhoneNumber,
                                                                                 String recipientAddress, String provinceName,
                                                                                 String cityName, int shippingMode,
                                                                                 String logisticsCompanyName, String logisticsNo,
                                                                                 String note, int totalQuantity,
                                                                                 List<PlanOutboundOrderItem> itemList, String signature,
                                                                                 String uniqueNo, Date arrivalDate,
                                                                                 long userId, String userName) {

        logger.debug("[SalesCreateOutboundOrder2] params: xpsWarehouseEntryUrl={}, channelId={}, sourceChannelId={},outboundType={},projectId={},customerId={},traceNo={},recipientName={},recipientPhoneNumber={},recipientAddress={},provinceName={},cityName={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={}，uniqueNo={}，arrivalDate={}, userId={}, userName={}",
                xpsWarehouseEntryUrl, channelId, sourceChannelId, outboundType, projectId, customerId, traceNo, recipientName, recipientPhoneNumber, recipientAddress, provinceName, cityName, shippingMode, logisticsCompanyName, logisticsNo, note, totalQuantity, JSON.toJSONString(itemList), signature, uniqueNo, arrivalDate, userId, userName);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s purchaseOrderNo=%s", channelId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }
        SalesCreateOutboundRequest salesCreateOutboundRequest = new SalesCreateOutboundRequest();
        salesCreateOutboundRequest.setSourceChannelId(channelId);
        salesCreateOutboundRequest.setOutboundType(outboundType);
        salesCreateOutboundRequest.setProjectId(projectId);
        salesCreateOutboundRequest.setCustomerId(customerId);
        salesCreateOutboundRequest.setTraceNo(traceNo);
        salesCreateOutboundRequest.setRecipientName(recipientName);
        salesCreateOutboundRequest.setRecipientPhoneNumbe(recipientPhoneNumber);
        salesCreateOutboundRequest.setRecipientAddress(recipientAddress);
        salesCreateOutboundRequest.setProvinceName(provinceName);
        salesCreateOutboundRequest.setCityName(cityName);
        salesCreateOutboundRequest.setShippingMode(shippingMode);
        salesCreateOutboundRequest.setLogisticsCompanyName(logisticsCompanyName);
        salesCreateOutboundRequest.setLogisticsNo(logisticsNo);
        salesCreateOutboundRequest.setNote(note);
        salesCreateOutboundRequest.setTotalQuantity(totalQuantity);
        salesCreateOutboundRequest.setItemLis(itemList);
        salesCreateOutboundRequest.setSignature(signature);
        salesCreateOutboundRequest.setUniqueNo(uniqueNo);
        salesCreateOutboundRequest.setArrivalDate(arrivalDate);
        salesCreateOutboundRequest.setUserId(userId);
        salesCreateOutboundRequest.setUserName(userName);

        String jsonString = JSON.toJSONString(salesCreateOutboundRequest);
        logger.info("#xps warehouse request#: {}", jsonString);
        MediaType jsonMediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SALES_CREATE_OUTBOUND_ORDER2)
                .post(requestBody)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, new TypeReference<Map<String, List<OutboundOrderItem>>>() {
                });
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }


    /**
     * 出库单签收
     */
    public static GongxiaoResult sureSighIn(String xpsWarehouseEntryUrl, String channelId, String channelToken,
                                            String transporter, String custOrdNo, String tmsOrdNo, String remark,
                                            String signedBy, String postedBy, String signedPhone, Date signedTime) {

        logger.debug("[SalesCreateOutboundOrder2] params: xpsWarehouseEntryUrl={}, channelId={}, sourceChannelId={},outboundType={},projectId={},customerId={},traceNo={},recipientName={},recipientPhoneNumber={},recipientAddress={},provinceName={},cityName={},shippingMode={},logisticsCompanyName={},logisticsNo={},note={},totalQuantity={},itemList={},signature={}，uniqueNo={}，arrivalDate={}",
                xpsWarehouseEntryUrl, channelId, transporter, custOrdNo, tmsOrdNo, remark, signedBy, postedBy, signedPhone, signedTime);

        //1. 参数校验
        if (custOrdNo == null) { //项目id不能为空
            String msg = String.format("custOrdNo can not be empty: channelId=%s purchaseOrderNo=%s", custOrdNo);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String jsonString = "?transporter=" + transporter + "&custOrdNo=" + custOrdNo + "&tmsOrdNo=" + tmsOrdNo + "&remark=" + remark + "&signedBy=" + signedBy + "&postedBy=" + postedBy + "&signedPhone=" + signedPhone + "&signedTime=" + signedTime;
        logger.info("#xps warehouse request#: {}", jsonString);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SURE_SIGHIN + jsonString)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * wms入库通知单 确认信息
     */
    public static ConfirmWmsInboundRespond confirmWmsInbound(String xpsWarehouseEntryUrl, String channelId, String channelToken, String wmsInboundInfo) {

        logger.debug("[confirmWmsInbound] params: xpsWarehouseEntryUrl={}, channelId={}, wmsInboundInfo={}",
                xpsWarehouseEntryUrl, channelId, wmsInboundInfo);

        //1. 参数校验
//        if (custOrdNo == null) { //项目id不能为空
//            String msg = String.format("custOrdNo can not be empty: channelId=%s purchaseOrderNo=%s", custOrdNo);
//            logger.error(msg);
//            new IllegalArgumentException(msg);
//        }

        MediaType jsonMediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, wmsInboundInfo);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + confirmWmsInbound)
                .post(requestBody)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, ConfirmWmsInboundRespond.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }


    /**
     * wms出库通知单 确认信息
     */
    public static ConfirmWmsOutboundRespond confirmWmsOutbound(String xpsWarehouseEntryUrl, String channelId, String channelToken, String wmsOutboundInfo) {

        logger.debug("[confirmWmsOutbound] params: xpsWarehouseEntryUrl={}, channelId={}, wmsOutboundInfo={}",
                xpsWarehouseEntryUrl, channelId, wmsOutboundInfo);

        //1. 参数校验
//        if (custOrdNo == null) { //项目id不能为空
//            String msg = String.format("custOrdNo can not be empty: channelId=%s purchaseOrderNo=%s", custOrdNo);
//            logger.error(msg);
//            new IllegalArgumentException(msg);
//        }

        MediaType jsonMediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, wmsOutboundInfo);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + confirmWmsOutbound)
                .post(requestBody)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, ConfirmWmsOutboundRespond.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 根据商品查询批次库存
     */
    public static PageInfo<InventoryBatch> getBatchDetail(String xpsWarehouseEntryUrl, String channelId, String channelToken, String projectId, String productCode, int pageNumber, int pageSize) {

        logger.debug("[confirmWmsOutbound] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, productCode={}, pageNumber={}, pageSize={}",
                xpsWarehouseEntryUrl, channelId, projectId, productCode, pageNumber, pageSize);

        //1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s purchaseOrderNo=%s", projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }
        String requestJson = "?projectId=" + projectId + "&productCode=" + productCode + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + getBatchDetail + requestJson)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, new TypeReference<PageInfo<InventoryBatch>>() {
                });
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 根据仓库查询批次库存
     */
    public static GongxiaoResult getBatchDetailByWarehouse(String xpsWarehouseEntryUrl, String channelId, String channelToken, String projectId, String warehouseId, String productCode, int pageNumber, int pageSize) {

        logger.debug("[confirmWmsOutbound] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, projectId={}, warehouseId={}, productCode={}, pageNumber={}, pageSize={}",
                xpsWarehouseEntryUrl, channelId, projectId, warehouseId, productCode, pageNumber, pageSize);

//        1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s purchaseOrderNo=%s", projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String requestJson = "?projectId=" + projectId + "&warehouseId=" + warehouseId + "&productCode=" + productCode + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + getBatchDetailByWarehouse + requestJson)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 库存模块，商品库存查询
     */
    public static GongxiaoResult selectProductInventoryInfo(String xpsWarehouseEntryUrl, String channelId, String channelToken, String projectId, String productCode, String productName, int pageNumber, int pageSize) {

        logger.debug("[confirmWmsOutbound] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, projectId={}, productCode={}, productName={}, pageNumber={}, pageSize={}",
                xpsWarehouseEntryUrl, channelId, projectId, productCode, productName, pageNumber, pageSize);

//        1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s purchaseOrderNo=%s", projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String requestJson = "?projectId=" + projectId + "&productCode=" + productCode + "&productName=" + productName + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + selectProductInventoryInfo + requestJson)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 库存模块，仓库库存查询
     */
    public static GongxiaoResult selecWarehouseInventoryInfo(String xpsWarehouseEntryUrl, String channelId, String channelToken, String projectId, String productCode, String productName, String warehouseId, String batchNo) {

        logger.debug("[confirmWmsOutbound] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, productCode={},productName={}, warehouseId={}, batchNo = {}",
                xpsWarehouseEntryUrl, channelId, projectId, productCode, productName, warehouseId, batchNo);

//        1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s purchaseOrderNo=%s", projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String requestJson = "?projectId=" + projectId + "&productCode=" + productCode + "&productName=" + productName + "&warehouseId=" + warehouseId + "&batchNo=" + batchNo;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + selecWarehouseInventoryInfo + requestJson)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 库存模块，仓库库存查询
     */
    public static PageInfo<InventoryLedger> selectAccountInfo(String xpsWarehouseEntryUrl, String channelId, String channelToken, String projectId, String productCode, String productName, String warehouseId, String startDate, String endDate, int pageNumber, int pageSize) {

        logger.debug("[selectAccountInfo] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, productCode={},productName={}, warehouseId={},pageNumber={}, int pageSize={}",
                xpsWarehouseEntryUrl, channelId, projectId, productCode, productName, warehouseId, pageNumber, pageSize);

//        1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s purchaseOrderNo=%s", projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String requestJson = "?projectId=" + projectId + "&productCode=" + productCode + "&productName=" + productName + "&warehouseId=" + warehouseId + "&startDate=" + startDate + "&endDate=" + endDate + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + selectAccountInfo + requestJson)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, new TypeReference<PageInfo<InventoryLedger>>() {
                });
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 库存模块，出入库流水查询
     */
    public static PageInfo<ProductInventoryFlowShow> selectInventoryFlow(String xpsWarehouseEntryUrl, String channelId, String channelToken, String projectId, String productCode, String productName, String warehouseId, String orderNo, String startDate, String endDate, int pageNumber, int pageSize) {

        logger.debug("[confirmWmsOutbound] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, productCode={},productName={}, warehouseId={},pageNumber={}, int pageSize={}",
                xpsWarehouseEntryUrl, channelId, projectId, productCode, productName, warehouseId, pageNumber, pageSize);

//        1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s purchaseOrderNo=%s", projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String requestJson = "?projectId=" + projectId + "&productCode=" + productCode + "&productName=" + productName + "&warehouseId=" + warehouseId + "&orderNo=" + orderNo + "&startDate=" + startDate + "&endDate=" + endDate + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + selectInventoryFlow + requestJson)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, new TypeReference<PageInfo<ProductInventoryFlowShow>>() {
                });
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 库存模块，核对库存查询
     */
    public static PageInfo<InventoryCheckModel> selectCheckInventory(String xpsWarehouseEntryUrl, String channelId, String channelToken, String projectId, String warehouseId, String productCode, String productName, int pageNumber, int pageSize) {

        logger.debug("[confirmWmsOutbound] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, productCode={},productName={}, warehouseId={},pageNumber={}, int pageSize={}",
                xpsWarehouseEntryUrl, channelId, projectId, productCode, productName, warehouseId, pageNumber, pageSize);

//        1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s purchaseOrderNo=%s", projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String requestJson = "?projectId=" + projectId + "&productCode=" + productCode + "&productName=" + productName + "&warehouseId=" + warehouseId + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + selectCheckInventory + requestJson)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, new TypeReference<PageInfo<InventoryCheckModel>>() {
                });
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }


    /**
     * 库存模块，核对库存查询
     */
    public static GongxiaoResult getproductQuqntity(String xpsWarehouseEntryUrl, String channelId, String channelToken, String projectId, String productCode) {
        logger.debug("[confirmWmsOutbound] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, productCode={}",
                xpsWarehouseEntryUrl, channelId, projectId, productCode);

//        1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s purchaseOrderNo=%s", projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String requestJson = "?projectId=" + projectId + "&productCode=" + productCode;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + getproductQuqntity + requestJson)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 库存模块，查询批次库存库龄
     */
    public static PageInfo<InventoryAge> getInventoryAgeInfo(String xpsWarehouseEntryUrl, String channelId, String channelToken, String projectId, String batchNo, String inboundOrderNo, String outboundOrderNo, String startDate, String endDate, int pageNumber, int pageSize) {
        logger.debug("[getInventoryAgeInfo] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, batchNo={}, inboundOrderNo={}, outboundOrderNo={}, pageNumber={}, pageSize={}",
                xpsWarehouseEntryUrl, channelId, projectId, batchNo, inboundOrderNo, outboundOrderNo, pageNumber, pageSize);

//        1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s purchaseOrderNo=%s", projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String requestJson = "?projectId=" + projectId + "&batchNo=" + batchNo + "&inboundOrderNo=" + inboundOrderNo + "&outboundOrderNo=" + outboundOrderNo + "&startDate=" + startDate + "&endDate=" + endDate + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + INVENTORY_AGE + requestJson)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, new TypeReference<PageInfo<InventoryAge>>() {
                });
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 调拨单管理,查询调拨单
     */
    public static GongxiaoResult selectAllocateOrder(String xpsWarehouseEntryUrl, String channelId, String channelToken, int pageNumber, int pageSize,
                                                     String projectId, String allocateNo, String gongxiaoOutboundOrderNo, String gongxiaoInboundOrderNo,
                                                     String warehouseOut, String warehouseEnter, String createBeginTime, String createEndTime) {

        logger.debug("[confirmWmsOutbound] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, productCode={},productName={}, warehouseId={}",
                xpsWarehouseEntryUrl, channelId, projectId, allocateNo);

//        1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s projectId=%s", projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String requestJson = "?projectId=" + projectId + "&allocateNo=" + allocateNo + "&gongxiaoOutboundOrderNo=" + gongxiaoOutboundOrderNo + "&gongxiaoInboundOrderNo=" + gongxiaoInboundOrderNo + "&warehouseOut=" + warehouseOut + "&warehouseEnter=" + warehouseEnter + "&createBeginTime=" + createBeginTime + "&createEndTime=" + createEndTime + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + SELECT_ALLOCATE_ORDER + requestJson)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }


    /**
     * 调拨单管理,查询所有调拨单
     */
    public static GongxiaoResult selectInfoByAllocateNo(String xpsWarehouseEntryUrl, String channelId, String channelToken, String projectId, String allocateNo) {

        logger.debug("[confirmWmsOutbound] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, productCode={},productName={}, warehouseId={}",
                xpsWarehouseEntryUrl, channelId, projectId, allocateNo);

//        1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s projectId=%s", projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String requestJson = "?projectId=" + projectId + "&allocateNo=" + allocateNo;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + selectInfoByAllocateNo + requestJson)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 调拨单管理,查询调拨单明细
     */
    public static GongxiaoResult selectItemByAllocateNo(String xpsWarehouseEntryUrl, String channelId, String channelToken, String projectId, String allocateNo) {

        logger.debug("[confirmWmsOutbound] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, productCode={},productName={}, warehouseId={}",
                xpsWarehouseEntryUrl, channelId, projectId, allocateNo);

//        1. 参数校验
        if (projectId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s purchaseOrderNo=%s", projectId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

        String requestJson = "?projectId=" + projectId + "&allocateNo=" + allocateNo;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + selectItemByAllocateNo + requestJson)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 新增调拨单
     */
    public static GongxiaoResult insertAllocateOrder(String xpsWarehouseEntryUrl, String channelId,
                                                     String channelToken, String companyout, String companyentry,
                                                     String projectoutId, String projectEntryId, String warehouseOutId,
                                                     String warehouseEntryId, String deliveraddress, String receiveaddress,
                                                     String allocateway, String yhbusiness_date, String ask_date,
                                                     String dead_date, String remask, String itemsInfo) {

        logger.debug("[insertInbound] params: xpsWarehouseEntryUrl={}, channelId={}, projectId={}, warehouseId={}, warehouseName={}, delieverAddress={}, supplierName={}, businessDate={}, outboundType={}, remark={}, itemsInfo={}",
                xpsWarehouseEntryUrl, channelId, channelToken, companyout, companyentry, projectoutId, projectEntryId, warehouseOutId, warehouseEntryId, deliveraddress, receiveaddress, allocateway, yhbusiness_date, ask_date, dead_date, remask, itemsInfo);

        //1. 参数校验
        if (projectoutId == null || projectEntryId == null) { //项目id不能为空
            String msg = String.format("projectId can not be empty: channelId=%s ", channelId);
            logger.error(msg);
            new IllegalArgumentException(msg);
        }

//        组装请求JSO
        CreateAllocateOrderRequest createAllocateOrderRequest = new CreateAllocateOrderRequest();
        createAllocateOrderRequest.setCompanyout(companyout);
        createAllocateOrderRequest.setCompanyentry(companyentry);
        createAllocateOrderRequest.setProjectoutId(projectoutId);
        createAllocateOrderRequest.setProjectEntryId(projectEntryId);
        createAllocateOrderRequest.setWarehouseOutId(warehouseOutId);
        createAllocateOrderRequest.setWarehouseEntryId(warehouseEntryId);
        createAllocateOrderRequest.setDeliveraddress(deliveraddress);
        createAllocateOrderRequest.setReceiveaddress(receiveaddress);
        createAllocateOrderRequest.setAllocateway(allocateway);
        createAllocateOrderRequest.setYhbusiness_date(yhbusiness_date);
        createAllocateOrderRequest.setAsk_date(ask_date);
        createAllocateOrderRequest.setDead_date(dead_date);
        createAllocateOrderRequest.setRemask(remask);
        createAllocateOrderRequest.setItemsInfo(itemsInfo);

        String jsonString = JSON.toJSONString(createAllocateOrderRequest);
        logger.info("#xps warehouse request#: {}", jsonString);
        MediaType jsonMediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + INSERT_ALLOCATE_ORDER)
                .post(requestBody)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    public static GongxiaoResult getPsiData(String xpsWarehouseEntryUrl, String channelId, String channelToken) {

        logger.info("[confirmWmsOutbound] params: xpsWarehouseEntryUrl={}, channelId={}",
                xpsWarehouseEntryUrl, channelId);
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + getPsiData)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;

    }

    public static GongxiaoResult getWholeInventoryAge(String xpsWarehouseEntryUrl, String channelId, String channelToken, String projectId) {

        logger.info("[getWholeInventoryAge] params: xpsWarehouseEntryUrl={}, channelId={}",
                xpsWarehouseEntryUrl, channelId);
        String requestJson = "?projectId=" + projectId;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + GET_WHOLE_INVENTORY_AGE + requestJson)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;

    }

    public static GongxiaoResult get180InventoryAge(String xpsWarehouseEntryUrl, String channelId, String channelToken, String projectId) {

        logger.info("[get180InventoryAge] params: xpsWarehouseEntryUrl={}, channelId={}",
                xpsWarehouseEntryUrl, channelId);
        String requestJson = "?projectId=" + projectId;
        Request request = new Request.Builder()
                .url(xpsWarehouseEntryUrl + GET_180_INVENTORY_AGE + requestJson)
                .build();
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#xps warehouse response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from xps warehouse: statusCode=%d", statusCode);
                logger.error(msg);
                new RuntimeException(msg);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;

    }

    public static void main(String[] args) {
        String xpsWarehouseEntryUrl = "http://127.0.0.1:11020";
        String channelId = "1";
        String channelToken = "9527";
        String projectId = "146798161";
        String productCode = "884202005710";
        int pageNumber = 1;
        int pageSize = 100;


        PageInfo<InventoryBatch> gongxiaoResult = XpsWarehouseManager.getBatchDetail(xpsWarehouseEntryUrl, channelId, channelToken, projectId, productCode, pageNumber, pageSize);
        System.out.println("=========================end===================" + JSON.toJSONString(gongxiaoResult));
    }

}
