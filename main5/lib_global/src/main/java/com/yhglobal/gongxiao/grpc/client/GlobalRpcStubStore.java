package com.yhglobal.gongxiao.grpc.client;

import com.yhglobal.gongxiao.foundation.area.microservice.ShippingAddressServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.yhportal.microservice.YhportalUserServiceGrpc;
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

/**
 * Xps公共级别的RPC在本类中定义
 *
 * 项目级别的RPC在各自项目本地的RpcStubStore中定义
 *
 * 注1: 公共模块只使用GlobalRpcStubStore 项目级别模块只使用本地的RpcStubStore
 * 注2: GlobalRpcStubStore和本地RpcStubStore不能有重复的服务名字，因为会导致冲突，目前会强制退出
 */
public class GlobalRpcStubStore {

    static Logger logger = LoggerFactory.getLogger(GlobalRpcStubStore.class);

    private final static String GlobalRpcRouterFile = "globalRpcRouter_$env.properties";

    private static Map<String, AbstractStub> globalRpcStubStore;

    private static Map<String, RpcServer> serverMap = new HashMap<>();
    
    private static final int RPC_TIMEOUT = 1000; //默认rpc超时时间 单位毫秒

    /**
     * 从GlobalRpcRouterFile中加载rpc服务和对应的url
     */
    private static void loadGlobalRpcUrl() {
        InputStream in = null;
        Properties properties = new Properties();
        String serverMode = CommonUtil.getServerMode();
        String targetFile = GlobalRpcRouterFile.replace("$env", serverMode);
        try {
            in = GlobalRpcStubStore.class.getClassLoader().getResourceAsStream(targetFile);
            if (in == null) {
                logger.error("fail to load global RPC router file: {}, inputStream={}", targetFile, in);
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
            logger.error("fail to load global rpc router file: {}", targetFile);
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
     * 初始化 global rpc服务和对应的stub
     */
    static {
        globalRpcStubStore = new HashMap<>();
        loadGlobalRpcUrl(); //加载global rpc路由策略
        String rpcClazzName;
        RpcServer rpcServer;
        ManagedChannel channel;

        // ******************************** 基础资料 BEGIN ********************************
        //初始化ProjectServiceGrpc
        rpcClazzName = ProjectServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        ProjectServiceGrpc.ProjectServiceBlockingStub projectServiceStub = ProjectServiceGrpc.newBlockingStub(channel);
        globalRpcStubStore.put(rpcClazzName, projectServiceStub);
        //客户地址
        rpcClazzName = ShippingAddressServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        ShippingAddressServiceGrpc.ShippingAddressServiceBlockingStub shippingAddressServiceBlockingStub = ShippingAddressServiceGrpc.newBlockingStub(channel);
        globalRpcStubStore.put(rpcClazzName, shippingAddressServiceBlockingStub);
        //客户
        rpcClazzName = DistributorServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        DistributorServiceGrpc.DistributorServiceBlockingStub distributorServiceBlockingStub = DistributorServiceGrpc.newBlockingStub(channel);
        globalRpcStubStore.put(rpcClazzName, distributorServiceBlockingStub);
        //货品
        rpcClazzName = ProductServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub = ProductServiceGrpc.newBlockingStub(channel);
        globalRpcStubStore.put(rpcClazzName, productServiceBlockingStub);
        //供应商
        rpcClazzName = SupplierServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        SupplierServiceGrpc.SupplierServiceBlockingStub supplierServiceBlockingStub = SupplierServiceGrpc.newBlockingStub(channel);
        globalRpcStubStore.put(rpcClazzName, supplierServiceBlockingStub);
        //仓库
        rpcClazzName = WarehouseServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseServiceBlockingStub = WarehouseServiceGrpc.newBlockingStub(channel);
        globalRpcStubStore.put(rpcClazzName, warehouseServiceBlockingStub);
        //渠道
        rpcClazzName = ChannelServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        ChannelServiceGrpc.ChannelServiceBlockingStub channelServiceBlockingStub = ChannelServiceGrpc.newBlockingStub(channel);
        globalRpcStubStore.put(rpcClazzName, channelServiceBlockingStub);
        //yhportal
        rpcClazzName = YhportalUserServiceGrpc.class.getCanonicalName();
        rpcServer = getRpcServer(rpcClazzName);
        channel = ManagedChannelBuilder.forAddress(rpcServer.host, rpcServer.port).usePlaintext().build();
        YhportalUserServiceGrpc.YhportalUserServiceBlockingStub yhportalUserServiceBlockingStub = YhportalUserServiceGrpc.newBlockingStub(channel);
        globalRpcStubStore.put(rpcClazzName, yhportalUserServiceBlockingStub);

        // ******************************** 基础资料 END ********************************

    }

    /**
     * 获取指定rpc服务对应的url
     * <p>
     * 注：若获取失败的话 则强制退出
     */
    static RpcServer getRpcServer(String rpcClazzName) {
        RpcServer rpcServer = serverMap.get(rpcClazzName);
        if (rpcServer == null) {
            logger.error("fail to get global Rpc Server for microservice: {}", rpcClazzName);
            throw new RuntimeException("fail to get global Rpc Server: service=" + rpcClazzName);
        }
        logger.debug("target server to global rpc: className={} rpcUrl={}:{}", rpcClazzName, rpcServer.host, rpcServer.port);
        return rpcServer;
    }


    final static String sep = "Grpc$";

    /**
     * 该接口由调用global服务的发起方调用 获取指定rpc服务的stub
     *
     * 注：若获取失败的话 则 抛出RuntimeException
     */
    public static <T extends AbstractStub> T getRpcStub(Class<T> rpcStubClass) {
        String stubClazzName = rpcStubClass.getName();
        int idx = stubClazzName.indexOf(sep);
        String rpcClassName = stubClazzName.substring(0, idx + sep.length() - 1);
        AbstractStub stub = globalRpcStubStore.get(rpcClassName);
        if (stub == null) {
            throw new RuntimeException("global RPC stub not available: " + stubClazzName);
        }
        stub.withDeadlineAfter(RPC_TIMEOUT, TimeUnit.MILLISECONDS);
        return (T) stub;
    }

    /**
     * 该接口由本地RpcStub在本地rpc查找失败时调用, 尝试获取globalRpc服务的stub
     *
     * 注：若匹配失败的话 则返回null  *不会*抛出RuntimeException
     */
    public static <T extends AbstractStub> T tryGetGlobalRpcStub(Class<T> rpcStubClass) {
        String stubClazzName = rpcStubClass.getName();
        int idx = stubClazzName.indexOf(sep);
        String rpcClassName = stubClazzName.substring(0, idx + sep.length() - 1);
        return (T) globalRpcStubStore.get(rpcClassName);
    }


    /**
     * 该接口由本地RpcStub调用 来判断global级别是否有指定rpc的定义，以便检测冲突
     */
    public static boolean containsRpcStub(String rpcClassName) {
        return globalRpcStubStore.containsKey(rpcClassName);
    }
}
