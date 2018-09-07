package com.yhglobal.gongxiao.grpc.client;


import com.yhglobal.gongxiao.inventory.account.microservice.InventoryLedgerServiceGrpc;
import com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure;
import com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventoryBatchServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventoryCheckServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryFlowServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmoundServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncServiceGrpc;
import com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeServiceGrpc;
import com.yhglobal.gongxiao.inventory.wholeinventoryage.microservice.WholeInventoryAgeServiceGrpc;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.wmsconfirm.WmsConfirmServiceGrpc;
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

public class WarehouseRpcStubStore {

    static Logger logger = LoggerFactory.getLogger(WarehouseRpcStubStore.class);

    private final static String warehouseRpcRouterFile = "warehouserouter_$env.properties";

    private static Map<String, AbstractStub> stubStore;

    private static Map<String, RpcServer> serverMap = new HashMap<>();
    
    private static final int RPC_TIMEOUT = 1000; //rpc超时时间 单位毫秒

    /**
     * 从router_$env.properties中加载rpc服务和对应的url
     */
    private static void loadRpcUrl() {
        InputStream in = null;
        Properties properties = new Properties();
        String serverMode = CommonUtil.getServerMode();
        String targetFile = warehouseRpcRouterFile.replace("$env", serverMode);
        try {
            in = WarehouseRpcStubStore.class.getClassLoader().getResourceAsStream(targetFile);
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


       /*********************************************销售模块****************************************************************/

        rpcClazzName = WmsConfirmServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        WmsConfirmServiceGrpc.WmsConfirmServiceBlockingStub wmsConfirmServiceBlockingStub = WmsConfirmServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, wmsConfirmServiceBlockingStub);

       /*************************************库存模块*************************************/
        // 初始化InventoryBatchService
        rpcClazzName = InventoryBatchServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        InventoryBatchServiceGrpc.InventoryBatchServiceBlockingStub inventoryBatchServiceBlockingStub = InventoryBatchServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, inventoryBatchServiceBlockingStub);
        // 初始化inventoryLedgerService
        rpcClazzName = InventoryLedgerServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        InventoryLedgerServiceGrpc.InventoryLedgerServiceBlockingStub inventoryLedgerServiceBlockingStub = InventoryLedgerServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, inventoryLedgerServiceBlockingStub);
        // 初始化inventoryCheckService
        rpcClazzName = InventoryCheckServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        InventoryCheckServiceGrpc.InventoryCheckServiceBlockingStub inventoryCheckServiceBlockingStub = InventoryCheckServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, inventoryCheckServiceBlockingStub);
        // 初始化inventoryFlowService
        rpcClazzName = InventoryFlowServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        InventoryFlowServiceGrpc.InventoryFlowServiceBlockingStub inventoryFlowServiceBlockingStub = InventoryFlowServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, inventoryFlowServiceBlockingStub);
        // 初始化inventoryquerryService
        rpcClazzName = InventoryquerryServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub inventoryquerryServiceBlockingStub = InventoryquerryServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, inventoryquerryServiceBlockingStub);
        // 初始化inventorysyncService
        rpcClazzName = InventorysyncServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        InventorysyncServiceGrpc.InventorysyncServiceBlockingStub inventorysyncServiceBlockingStub = InventorysyncServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, inventorysyncServiceBlockingStub);
        //初始化inventoryAgeService
        rpcClazzName = InventoryAgeServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        InventoryAgeServiceGrpc.InventoryAgeServiceBlockingStub inventoryAgeServiceBlockingStub = InventoryAgeServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, inventoryAgeServiceBlockingStub);

        //初始化inventoryMonthAmoundService
        rpcClazzName = InventoryMonthAmoundServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        InventoryMonthAmoundServiceGrpc.InventoryMonthAmoundServiceBlockingStub inventoryMonthAmoundServiceBlockingStub = InventoryMonthAmoundServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, inventoryMonthAmoundServiceBlockingStub);

        //初始化wholeInventoryAgeService
        rpcClazzName = WholeInventoryAgeServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        WholeInventoryAgeServiceGrpc.WholeInventoryAgeServiceBlockingStub wholeInventoryAgeServiceBlockingStub = WholeInventoryAgeServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, wholeInventoryAgeServiceBlockingStub);

        //初始化productInventory180AgeService
        rpcClazzName = ProductInventory180AgeServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        ProductInventory180AgeServiceGrpc.ProductInventory180AgeServiceBlockingStub productInventory180AgeServiceBlockingStub = ProductInventory180AgeServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, productInventory180AgeServiceBlockingStub);

        //初始化insertInventoryService
        rpcClazzName = InsertInventoryServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        InsertInventoryServiceGrpc.InsertInventoryServiceBlockingStub insertInventoryServiceBlockingStub = InsertInventoryServiceGrpc.newBlockingStub(channel);
        stubStore.put(rpcClazzName, insertInventoryServiceBlockingStub);

    }

    /**
     * 获取指定rpc服务对应的url
     * <p>
     * 注：若获取失败的话 则强制退出
     */
    static RpcServer getRpcServer(String rpcClazzName) {
        RpcServer rpcServer = serverMap.get(rpcClazzName);
        if (rpcServer == null) {
            String msg = String.format("fail to get Rpc Server for microservice: %s", rpcClazzName);
            logger.error(msg);
            throw new RuntimeException(msg);
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
        AbstractStub stub = stubStore.get(rpcClassName);
        stub.withDeadlineAfter(RPC_TIMEOUT, TimeUnit.MILLISECONDS);
        if (stub != null) {
            return (T) stub;
        }
        throw new RuntimeException("gRpc stub not available: " + stubClazzName);
    }


}
