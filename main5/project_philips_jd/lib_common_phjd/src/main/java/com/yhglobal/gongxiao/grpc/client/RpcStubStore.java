package com.yhglobal.gongxiao.grpc.client;

import com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.*;


import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceGrpc;
import com.yhglobal.gongxiao.util.CommonUtil;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.AbstractStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class RpcStubStore {

    static Logger logger = LoggerFactory.getLogger(RpcStubStore.class);

    private final static String LocalRpcRouterFile = "localRpcRouter_$env.properties";

    private static Map<String, AbstractStub> stubStore;

    private static Map<String, RpcServer> serverMap = new HashMap<>();
    
    private static final int RPC_TIMEOUT = 1000; //rpc超时时间 单位毫秒

    /**
     * 从LocalRpcRouterFile中加载rpc服务和对应的url
     */
    private static void loadRpcUrl() {
        InputStream in = null;
        Properties properties = new Properties();
        String serverMode = CommonUtil.getServerMode();
        String targetFile = LocalRpcRouterFile.replace("$env", serverMode);
        try {
            in = RpcStubStore.class.getClassLoader().getResourceAsStream(targetFile);
            if (in == null) {
                logger.error("fail to load properties file: {}, inputStream={}", targetFile, in);
            }
            properties.load(in);
            Iterator<Map.Entry<Object, Object>> iter = properties.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<Object, Object> entry = iter.next();
                String serviceStringKey = ((String) entry.getKey()).trim();
                String serviceStringValue = ((String) entry.getValue()).trim();
                if (serviceStringValue == null) continue;
                String[] parts = serviceStringValue.split(":");
                String host = parts[0].trim();
                int port = Integer.parseInt(parts[1].trim());
                RpcServer rpcServer = new RpcServer(host, port);
                serverMap.put(serviceStringKey, rpcServer);
            }
        } catch (Exception e) {
            logger.error("fail to load rpc router: {}", targetFile);
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 初始化 rpc服务和对应的stub
     * 注: 默认rpc的超时都设为1秒
     */
    static {
        stubStore = new HashMap<>();
        loadRpcUrl(); //加载rpc路由策略
        String rpcClazzName;
        RpcServer rpcServer;
        ManagedChannel channel;

        // ******************************** 采购 BEGIN ********************************
//        rpcClazzName = PurchaseFileServiceGrpc.class.getCanonicalName();
//        rpcServer = getRpcServer(rpcClazzName);
//        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
//        PurchaseFileServiceGrpc.PurchaseFileServiceBlockingStub purchaseFileServiceBlockingStub = PurchaseFileServiceGrpc.newBlockingStub(channel);
//        stubStore.put(rpcClazzName, purchaseFileServiceBlockingStub);
//
//        rpcClazzName = PurchaseServiceGrpc.class.getCanonicalName();
//        rpcServer = getRpcServer(rpcClazzName);
//        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
//        PurchaseServiceGrpc.PurchaseServiceBlockingStub purchaseServiceBlockingStub = PurchaseServiceGrpc.newBlockingStub(channel);
//        stubStore.put(rpcClazzName, purchaseServiceBlockingStub);
//
//        rpcClazzName = NotifiInboundServiceGrpc.class.getCanonicalName();
//        rpcServer = getRpcServer(rpcClazzName);
//        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
//        NotifiInboundServiceGrpc.NotifiInboundServiceBlockingStub notifiInboundServiceBlockingStub = NotifiInboundServiceGrpc.newBlockingStub(channel);
//        stubStore.put(rpcClazzName, notifiInboundServiceBlockingStub);
        // ******************************** 采购 END ********************************


        // ******************************** 支付 BEGIN ********************************

        //初始化SupplierCouponPostingServiceGrpc  供应商上账界面接口
        rpcClazzName = SupplierCouponPostingServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        SupplierCouponPostingServiceGrpc.SupplierCouponPostingServiceBlockingStub supplierCouponPostingServiceBlockingStub =
                SupplierCouponPostingServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, supplierCouponPostingServiceBlockingStub);

        // 初始化 供应商冻结账户接口
        rpcClazzName = SupplierFrozenAccountServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        SupplierFrozenAccountServiceGrpc.SupplierFrozenAccountServiceBlockingStub supplierFrozenAccountServiceBlockingStub = SupplierFrozenAccountServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, supplierFrozenAccountServiceBlockingStub);

        // 初始化 供应商预留账户接口
        rpcClazzName = SupplierReservedAccountServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        SupplierReservedAccountServiceGrpc.SupplierReservedAccountServiceBlockingStub supplierReservedAccountServiceBlockingStub = SupplierReservedAccountServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, supplierReservedAccountServiceBlockingStub);

        // 初始化外部更新单价折扣冻结账户 采购预留冻结账户 销售差价账户 的调用
        rpcClazzName = GrpcUpdateAccountServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        GrpcUpdateAccountServiceGrpc.GrpcUpdateAccountServiceBlockingStub grpcUpdateAccountServiceBlockingStub = GrpcUpdateAccountServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, grpcUpdateAccountServiceBlockingStub);

//         初始化销退接口 salesOrderReturnServiceGrpcName
//       String salesOrderReturnServiceGrpcName = SalesOrderReturnServiceGrpc.class.getCanonicalName();
//       rpcServer = getRpcServer(salesOrderReturnServiceGrpcName);
//       ManagedChannel salesOrderReturnChannel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
//       SalesOrderReturnServiceGrpc.SalesOrderReturnServiceBlockingStub salesOrderReturnServiceBlockingStub =
//               SalesOrderReturnServiceGrpc.newBlockingStub(salesOrderReturnChannel);
//       stubStore.put(salesOrderReturnServiceGrpcName, salesOrderReturnServiceBlockingStub);


        // 初始化 越海账户Service
        rpcClazzName = YhglobalAccountServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountServiceStub = YhglobalAccountServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, yhglobalAccountServiceStub);
//
        // 初始化 供应商账户Service
        rpcClazzName = SupplierAccountServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub supplierAccountServiceBlockingStub = SupplierAccountServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, supplierAccountServiceBlockingStub);
//
//
//        // 初始化 现金确认Service
//        rpcClazzName = CashConfirmServiceGrpc.class.getCanonicalName();
//        rpcServer = getRpcServer(rpcClazzName);
//        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
//        CashConfirmServiceGrpc.CashConfirmServiceBlockingStub cashConfirmServiceBlockingStub = CashConfirmServiceGrpc.newBlockingStub(channel);
//        stubStore.put(rpcClazzName, cashConfirmServiceBlockingStub);
//
//        // 初始化 现金台账Service
//        rpcClazzName = CashLedgerServiceGrpc.class.getCanonicalName();
//        rpcServer = getRpcServer(rpcClazzName);
//        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
//        CashLedgerServiceGrpc.CashLedgerServiceBlockingStub cashLedgerServiceBlockingStub = CashLedgerServiceGrpc.newBlockingStub(channel);
//        stubStore.put(rpcClazzName, cashLedgerServiceBlockingStub);

        // ******************************** 支付 END ********************************


        // ******************************** 销售 BEGIN ********************************
        // 初始化 销售Service
        rpcClazzName = SalesOrderServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        SalesOrderServiceGrpc.SalesOrderServiceBlockingStub salesOrderStub = SalesOrderServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, salesOrderStub);

//        // 初始化 销退Service
//        rpcClazzName = SalesOrderReturnServiceGrpc.class.getCanonicalName();
//        rpcServer = getRpcServer(rpcClazzName);
//        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
//        SalesOrderReturnServiceGrpc.SalesOrderReturnServiceBlockingStub salesOrderReturnStub = SalesOrderReturnServiceGrpc.newBlockingStub(channel);
//        stubStore.put(rpcClazzName, salesOrderReturnStub);
//
//        // 初始化 销售Service
//        rpcClazzName = SalesScheduleDeliveryServiceGrpc.class.getCanonicalName();
//        rpcServer = getRpcServer(rpcClazzName);
//        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
//        SalesScheduleDeliveryServiceGrpc.SalesScheduleDeliveryServiceBlockingStub scheduleStub = SalesScheduleDeliveryServiceGrpc.newBlockingStub(channel);
//        stubStore.put(rpcClazzName, scheduleStub);
//
//        // 初始化 销售Service
//        rpcClazzName = PlanSaleCustomerServiceGrpc.class.getCanonicalName();
//        rpcServer = getRpcServer(rpcClazzName);
//        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
//        PlanSaleCustomerServiceGrpc.PlanSaleCustomerServiceBlockingStub planSaleCustomerServiceBlockingStub = PlanSaleCustomerServiceGrpc.newBlockingStub(channel);
//        stubStore.put(rpcClazzName, planSaleCustomerServiceBlockingStub);

//        rpcClazzName = PlanSaleItemServiceGrpc.class.getCanonicalName();
//        rpcServer = getRpcServer(rpcClazzName);
//        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
//        PlanSaleItemServiceGrpc.PlanSaleItemServiceBlockingStub planSaleItemServiceBlockingStub = PlanSaleItemServiceGrpc.newBlockingStub(channel);
//        stubStore.put(rpcClazzName, planSaleItemServiceBlockingStub);
//
//        rpcClazzName = PlanSaleServiceGrpc.class.getCanonicalName();
//        rpcServer = getRpcServer(rpcClazzName);
//        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
//        PlanSaleServiceGrpc.PlanSaleServiceBlockingStub planSaleServiceBlockingStub = PlanSaleServiceGrpc.newBlockingStub(channel);
//        stubStore.put(rpcClazzName, planSaleServiceBlockingStub);
        // ******************************** 销售 END ********************************


        //检查是否有和global级别重复定义的rpc服务
        for (String rpcClassName : stubStore.keySet()) {
            if (GlobalRpcStubStore.containsRpcStub(rpcClassName)) {
                logger.error("duplicated entry found in global RPC router: rpcClassName={}...exit", rpcClassName);
                throw new RuntimeException("duplicated entry found in global scope: rpcClassName={}" + rpcClassName);
            }
        }

    }

    /**
     * 获取指定rpc服务对应的url
     * <p>
     * 注：若获取失败的话 则强制退出
     */
    static RpcServer getRpcServer(String rpcClazzName) {
        RpcServer rpcServer = serverMap.get(rpcClazzName);
        if (rpcServer == null) {
            logger.error("fail to get Rpc Server for microservice: {}", rpcClazzName);
            throw new RuntimeException("fail to get rpc Server: service=" + rpcClazzName);
        }
        logger.debug("target server to rpc: className={} rpcUrl={}:{}", rpcClazzName, rpcServer.host, rpcServer.port);
        return rpcServer;
    }

    /**
     * 获取指定rpc服务的stub
     * 注：若获取失败的话 则抛出RuntimeException
     */
    final static String sep = "Grpc$";

    public static <T extends AbstractStub> T getRpcStub(Class<T> rpcStubClass) {
        String stubClazzName = rpcStubClass.getName();
        int idx = stubClazzName.indexOf(sep);
        String rpcClassName = stubClazzName.substring(0, idx + sep.length() - 1);
        AbstractStub stub = stubStore.get(rpcClassName); //先尝试从本地获取
        if (stub == null) { //本地获取失败则尝试从global获取
            stub = GlobalRpcStubStore.tryGetGlobalRpcStub(rpcStubClass); //先尝试从本地获取
        }
        if (stub == null) { //两个地方都获取失败则抛异常
            throw new RuntimeException("gRpc stub not available: " + stubClazzName);
        }
        stub.withDeadlineAfter(RPC_TIMEOUT, TimeUnit.MILLISECONDS);
        return (T) stub;
    }


}
