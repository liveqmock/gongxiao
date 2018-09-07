package com.yhglobal.gongxiao.payment.prepaid.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalPrepaidInfo;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalPrepaidLedgerWriteoffRecord;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalToReceivePrepaidCount;
import com.yhglobal.gongxiao.payment.prepaid.YhglobalToReceivePrepaidLedger;

import java.util.Date;
import java.util.List;

/**
 * @Description: 越海代垫service
 * @author: LUOYI
 * @Date: Created in 16:45 2018/4/27
 */
public interface YhglobalPrepaidService {
    /**
     * 新增应收记录
     * @param rpcHeader
     * @param orderId 订单号
     * @param projectId 项目ID
     * @param projectName 项目名称
     * @param supplierId    供应商ID
     * @param supplierName 供应商名称
     * @param salesContractNo 销售合同号
     * @param receiveAmount 应收金额
     * @param currencyCode 币种
     * @return 新增记录主键
     */
    public int addReceive(RpcHeader rpcHeader, String orderId,Long projectId,String projectName,
                          Integer supplierId, String supplierName,String salesContractNo,
                          Long receiveAmount,
                          String currencyCode);

    /**
     * 应收确认
     * @param rpcHeader
     */
    public RpcResult receiveConfirm(RpcHeader rpcHeader, Long projectId, String projectName, Date useDate, Integer accountType,String philipNo, String confirmInfo);

    /**
     * 批量取消确认
     * @param rpcHeader
     * @param flowCodes
     * @return
     */
    public boolean receiveCancelConfirmBatch(RpcHeader rpcHeader,String[] flowCodes);

    /**
     * 按项目查应收代垫
     * @param projectId 项目ID
     * @return
     */
    public List<YhglobalToReceivePrepaidLedger> selectByProjectId(RpcHeader rpcHeader,Long projectId);

    /**
     * 按ID列表查询应收代垫
     * @param ids ID数组
     * @return
     */
    public List<YhglobalToReceivePrepaidLedger> selectByIds(RpcHeader rpcHeader,String[] ids);

    /**
     * 按应收ID查确认记录
     * @param rpcHeader
     * @param receiveId
     * @return
     */
    public List<YhglobalPrepaidLedgerWriteoffRecord> selectWriteoffRecordByReceiveId(RpcHeader rpcHeader,Long receiveId);

    /**
     * 按项目查询应收实收对照
     * @param rpcHeader
     * @param projectId
     * @return
     */
    public PageInfo<YhglobalToReceivePrepaidLedger> selectReceiveAndRecordByProjectId(RpcHeader rpcHeader,Long projectId,
                                                                                  String orderId,
                                                                                  String flowCode,
                                                                                  Integer accountType,
                                                                                  Date dateStart,
                                                                                  Date dateEnd,
                                                                                  Date dateStartConfirm,
                                                                                  Date dateEndConfirm,
                                                                                  String receiveStatus,
                                                                                  Integer pageNumber,
                                                                                  Integer pageSize);
    /**
     * 按项目查询应收实收对照
     * @param rpcHeader
     * @param projectId
     * @return
     */
    public YhglobalToReceivePrepaidCount selectReceiveAndRecordCount(RpcHeader rpcHeader, Long projectId,
                                                                     String receiveIds);

    /**
     * 新增代垫付款单
     * @param rpcHeader
     * @param projectId 项目ID
     * @param projectName 项目名称
     * @param supplierId 供应商ID
     * @param supplierName 供应商名称
     * @param payer 付款方
     * @param receivables 收款方
     * @param settlementNo 结算单号
     * @param dateBusiness 业务日期
     * @param taxNo 税号
     * @param contactInfo 联系方式
     * @param provinceId 省ID
     * @param provinceName 省名称
     * @param cityId 市ID
     * @param cityName 市名称
     * @param districtId 区ID
     * @param districtName 区名称
     * @param streetAddress 街道地址
     * @param accountCNY 人民币帐户
     * @param bankNameCNY 人民币开户行
     * @param itemJson 明细json
     * @return
     */
    public int addPrepaidInfo(RpcHeader rpcHeader,Long projectId,String projectName,Integer supplierId,String supplierName,String payer,String receivables,
                              String settlementNo,Integer settlementType,Date dateBusiness,String taxNo,String contactInfo,Integer provinceId,
                              String provinceName,Integer cityId,String cityName,Integer districtId,String districtName,
                              String streetAddress,String accountCNY,String bankNameCNY,String remark,String itemJson);

    /**
     *  查询代垫付款单列表
     * @param rpcHeader
     * @param projectId 项目ID
     * @param prepaidNo 代垫付款单号
     * @param receivables 收款方
     * @param dateStart 创建时间起
     * @param dateEnd 创建时间止
     * @param page 页码
     * @param pageSize 每页显示条数
     * @return
     */
    public PageInfo<YhglobalPrepaidInfo> getsPrepaidInfoList(RpcHeader rpcHeader,Long projectId,String prepaidNo,String receivables,Date dateStart,Date dateEnd,Integer page, Integer pageSize);

    /**
     * 按ID查询付款单信息
     * @param rpcHeader
     * @param id
     * @return
     */
    public YhglobalPrepaidInfo getPrepaidInfoById(RpcHeader rpcHeader,Integer id);

    /**
     *
     * @param rpcHeader
     * @param projectId
     * @param flowCode
     * @param accountType
     * @param useDateStart
     * @param useDateEnd
     * @param dateStart
     * @param dateEnd
     * @return
     */
    public PageInfo<YhglobalPrepaidLedgerWriteoffRecord> searchPrepaidConfirm(RpcHeader rpcHeader,Long projectId,
                                                                              String flowCode,
                                                                              Integer accountType,
                                                                              Date useDateStart,
                                                                              Date useDateEnd,
                                                                              Date dateStart,
                                                                              Date dateEnd,
                                                                              Integer page,
                                                                              Integer pageSize);
}
