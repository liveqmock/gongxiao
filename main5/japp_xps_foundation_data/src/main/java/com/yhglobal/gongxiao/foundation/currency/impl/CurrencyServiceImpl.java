package com.yhglobal.gongxiao.foundation.currency.impl;

import com.yhglobal.gongxiao.foundation.currency.dao.FoundationCurrencyDao;
import com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyServiceGrpc;
import com.yhglobal.gongxiao.foundation.currency.microservice.CurrencyStructure;
import com.yhglobal.gongxiao.foundation.currency.model.FoundationCurrency;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.DateUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 货币服务类
 */
@Service
public class CurrencyServiceImpl extends CurrencyServiceGrpc.CurrencyServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    @Autowired
    FoundationCurrencyDao currencyDao;

    /**
     * 通过currencyCode查询货币
     *
     * rpcHeader rpc调用的header
     * currencyCode 货币代码 比如:CNY USD等
     * @return Currency 货币对象
     */
    @Override
    public void getCurrencyByCode(CurrencyStructure.GetCurrencyByCodeRequest request,
                                  StreamObserver<CurrencyStructure.GetCurrencyByCodeResponse> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();

        CurrencyStructure.GetCurrencyByCodeResponse response; //保存返回的值
        CurrencyStructure.GetCurrencyByCodeResponse.Builder respBuilder = CurrencyStructure.GetCurrencyByCodeResponse.newBuilder(); //每个proto对象都需要从builder构建出来

        logger.info("#traceId={}# [IN][getCurrencyByCode] params: currencyCode={}", rpcHeader.getTraceId(), currencyCode);
        try {
            FoundationCurrency currency = currencyDao.getCurrencyByCode(currencyCode);
            if (currency==null){
                logger.info("[getCurrencyByCode]没有拿到货币数据 #traceId={}, currencyCode={}",rpcHeader.getTraceId(), currencyCode);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            CurrencyStructure.Currency currency1 = CurrencyStructure.Currency.newBuilder()
                    .setCurrencyId(currency.getCurrencyId())
                    .setCurrencyCode(currency.getCurrencyCode())
                    .setCurrencyName(currency.getCurrencyName())
                    .setRecordStatus(currency.getRecordStatus())
                    .setCreateTime(DateUtil.getTime(currency.getCreateTime()))
                    .build();
            respBuilder.setCurrency(currency1);
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }
    }

    /**
     * 通过currencyName查询货币
     *
     * rpcHeader rpc调用的header
     * currencyName 货币名字 比如:RMB等
     * @return Currency 货币对象
     */
    @Override
    public void getCurrencyByName(CurrencyStructure.GetCurrencyByNameRequest request,
                                  StreamObserver<CurrencyStructure.GetCurrencyByNameResponse> responseObserver){
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyName = request.getCurrencyName();

        CurrencyStructure.GetCurrencyByNameResponse response; //保存返回的值
        CurrencyStructure.GetCurrencyByNameResponse.Builder respBuilder = CurrencyStructure.GetCurrencyByNameResponse.newBuilder(); //每个proto对象都需要从builder构建出来

        logger.info("#traceId={}# [IN][getCurrencyByName] params: currencyName={}", rpcHeader.getTraceId(), currencyName);
        try {
            FoundationCurrency currency = currencyDao.getCurrencyByName(currencyName);
            if (currency==null){
                logger.info("[getCurrencyByName] 没有拿到货币数据 #traceId={}, currencyCode={}",rpcHeader.getTraceId(), currencyName);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            CurrencyStructure.Currency currency1 = CurrencyStructure.Currency.newBuilder()
                    .setCurrencyId(currency.getCurrencyId())
                    .setCurrencyCode(currency.getCurrencyCode())
                    .setCurrencyName(currency.getCurrencyName())
                    .setRecordStatus(currency.getRecordStatus())
                    .setCreateTime(DateUtil.getTime(currency.getCreateTime()))
                    .build();

            response = respBuilder.build(); //通过build()方法来生成最终的getProjectByIdResp
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }
    }

    /**
     * 通过currencyId查询货币
     *
     * rpcHeader rpc调用的header
     * currencyId 货币id
     * @return Currency 货币对象
     */
    @Override
    public void getCurrencyById(CurrencyStructure.GetCurrencyByIdRequest request,
                                StreamObserver<CurrencyStructure.GetCurrencyByIdResponse> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        long currencyId = request.getCurrencyId();

        CurrencyStructure.GetCurrencyByIdResponse response; //保存返回的值
        CurrencyStructure.GetCurrencyByIdResponse.Builder respBuilder = CurrencyStructure.GetCurrencyByIdResponse.newBuilder(); //每个proto对象都需要从builder构建出来

        logger.info("#traceId={}# [IN][getCurrencyById] params: currencyId={}", rpcHeader.getTraceId(), currencyId);
        try {
            FoundationCurrency currency = currencyDao.getCurrencyById(currencyId);
            if (currency==null){
                logger.info("[getCurrencyById] 没有拿到货币数据 #traceId={}, currencyId={}",rpcHeader.getTraceId(), currencyId);
                responseObserver.onNext(respBuilder.build());
                responseObserver.onCompleted();
                return;
            }
            CurrencyStructure.Currency currency1 = CurrencyStructure.Currency.newBuilder()
                    .setCurrencyId(currency.getCurrencyId())
                    .setCurrencyCode(currency.getCurrencyCode())
                    .setCurrencyName(currency.getCurrencyName())
                    .setRecordStatus(currency.getRecordStatus())
                    .setCreateTime(DateUtil.getTime(currency.getCreateTime()))
                    .build();

            response = respBuilder.build(); //通过build()方法来生成最终的getProjectByIdResp
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }
    }
}
