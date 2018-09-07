package com.yhglobal.gongxiao.coupon.Service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.coupon.model.PrepaidPaymentOrder.YhglobalPrepaidInfo;
import com.yhglobal.gongxiao.coupon.model.RpcHeader;
import com.yhglobal.gongxiao.coupon.model.YhglobalPrepaidLedgerWriteoffRecord;
import com.yhglobal.gongxiao.coupon.model.YhglobalToReceivePrepaidCount;
import com.yhglobal.gongxiao.coupon.model.YhglobalToReceivePrepaidLedger;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.model.WriteOffReturnObject;

import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
public interface PrepaidService {

    /***********************************************代垫付款单相关**********************************************************/


    RpcResult addPrepaidInfo(GongxiaoRpc.RpcHeader rpcHeader, Long projectId, String projectName, Integer supplierId, String supplierName, String payer,
                                    String receivables, String settlementNo,Integer settlementType, String dateBusiness, String taxNo,
                                    String contactInfo, Integer provinceId, String provinceName, Integer cityId,
                                    String cityName, Integer districtId, String districtName, String streetAddress,
                                    String accountCNY, String bankNameCNY, String remark, String itemJson);


    PageInfo<YhglobalPrepaidInfo> getsPrepaidInfoList(GongxiaoRpc.RpcHeader rpcHeader, Long projectId, String prepaidNo, String receivables,
                                                      Date dateStart, Date dateEnd, Integer page, Integer pageSize);


    YhglobalPrepaidInfo getPrepaidInfoById(GongxiaoRpc.RpcHeader rpcHeader,Long projectId, Integer id);


/***************************************************应收代垫相关********************************************************/
    // 生成代垫的接口
    RpcResult generateYhglobalPrepaidLedger(GongxiaoRpc.RpcHeader rpcHeader, String orderId, Long projectId, String projectName, Integer supplierId,
                                           String supplierName, String salesContractNo, Long receiveAmount, String currencyCode, Date outWarehouseDate);
    // 核销
    RpcResult yhPrepaidWriteroff(  GongxiaoRpc.RpcHeader rpcHeader,Long projectId,
                                 String useDate ,
                                 Integer accountType,
                                 String confirmInfoJson ,
                                 String projectName,
                                 String philipDocumentNo, List<WriteOffReturnObject> list);

    List<YhglobalToReceivePrepaidLedger> selectPreapidById(GongxiaoRpc.RpcHeader rpcHeader, Long projectId,  List<String> idsList);

    // 台账
    PageInfo<YhglobalPrepaidLedgerWriteoffRecord> searchPrepaidConfirm(GongxiaoRpc.RpcHeader rpcHeader,
                                                                       Long projectId,
                                                                       String flowNo,
                                                                       Integer accountType,
                                                                       Date useDateStart,
                                                                       Date useDateEnd,
                                                                       Date dateStart,
                                                                       Date dateEnd,
                                                                       Integer pageNumber,
                                                                       Integer pageSize);

    PageInfo<YhglobalToReceivePrepaidLedger> selectByManyConditionsHasPage(GongxiaoRpc.RpcHeader rpcHeader, Long projectId, String orderId,
                                                                           Date dateStart, Date dateEnd,Date dateStartConfirm, Date dateEndConfirm, String receiveStatus,
                                                                           Integer accountType,
                                                                           String flowCode, Integer pageNumber,
                                                                           Integer pageSize);
    // 取消确认
    RpcResult receiveCancelConfirmBatch(GongxiaoRpc.RpcHeader rpcHeader,Long projectId, String flowCode, WriteOffReturnObject object,
                                        String flow);

    YhglobalToReceivePrepaidCount selectReceiveAndRecordCount(GongxiaoRpc.RpcHeader rpcHeader, Long projectId,  String ids);

    RpcResult getReportLeftOneData(GongxiaoRpc.RpcHeader rpcHeader,Long projectId);

    RpcResult getReportRightOneData(GongxiaoRpc.RpcHeader rpcHeader,Long projectId);
}
