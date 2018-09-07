package com.yhglobal.gongxiao.grossProfit.Service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.grossProfit.model.GrossProfitItem;
import com.yhglobal.gongxiao.grossProfit.model.YhglobalToReceiveGrossProfitLedger;
import com.yhglobal.gongxiao.grossProfit.model.YhglobalToReceiveGrossProfitLedgerWriteoffRecord;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.model.WriteOffReturnObject;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
public interface GrossProfitService {

    // 生成毛利的接口
    RpcResult generateGrossProfitLedger( GongxiaoRpc.RpcHeader rpcHeader, Long projectId,
                                            String currencyCode ,
                                            String purchaseOrderNo ,
                                            BigDecimal estimatedGrossProfitAmount,
                                            BigDecimal confirmedAmount ,
                                            String projectName ,
                                            BigDecimal receivedAmount,
                                            String salesOrderNo ,
                                            Date salesTime ,
                                            String tablePrefix ,
                                            BigDecimal toBeConfirmAmount);
    // 核销
    RpcResult yhGrossProfitWriteroff(  GongxiaoRpc.RpcHeader rpcHeader, String confirmInfoJson ,
                                                                    String currencyCode ,
                                                                    Byte differenceAmountAdjustPattern ,
                                                                    Long projectId,
                                                                    Byte transferIntoPattern ,
                                                                    String useDate, String flowNo, List<WriteOffReturnObject> list);
    // 根据ID查询
    List<YhglobalToReceiveGrossProfitLedger> selectGrossProfitLedgerByIds(Long projectId, List<String> idList);

    // 台账
    PageInfo<YhglobalToReceiveGrossProfitLedgerWriteoffRecord> searchGrossProfitConfirm( GongxiaoRpc.RpcHeader rpcHeader,Integer accountType ,
                                                                                    String flowCode ,
                                                                                    Long projectId,
                                                                                    Integer pageNumber,
                                                                                    Integer pageSize ,
                                                                                    Date dateS ,
                                                                                    Date dateE,
                                                                                    Date useDateS,
                                                                                    Date useDateE);


    // 条件查询毛利
    PageInfo<GrossProfitItem> selectByManyConditionsHasPage( GongxiaoRpc.RpcHeader rpcHeader,String grossProfitStatus ,
                                                                String flowNo ,
                                                                Long projectId ,
                                                                String purchaseOrderNo ,
                                                                Integer pageNumber ,
                                                                Integer pageSize,
                                                                String salesOrderNo ,
                                                                Date dateS ,
                                                                Date dateE);
   // 导出
List<GrossProfitItem> selectByManyConditionsNoPage( GongxiaoRpc.RpcHeader rpcHeader,String grossProfitStatus ,
                                                         String flowNo ,
                                                         Long projectId ,
                                                         String purchaseOrderNo ,
                                                         String salesOrderNo ,
                                                         Date dateS ,
                                                         Date dateE);

    // 取消确认
    RpcResult receiveCancelConfirmBatch(GongxiaoRpc.RpcHeader rpcHeader,Long projectId, String flowCodes, WriteOffReturnObject object , String flow);


}
