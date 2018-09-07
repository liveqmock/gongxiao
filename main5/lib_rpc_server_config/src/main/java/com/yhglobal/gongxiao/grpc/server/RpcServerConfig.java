package com.yhglobal.gongxiao.grpc.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * grpc server 启动时需要的相关配置信息（比如端口/线程数/backlog/...)等等
 * 每个japp应用下面中配置文件 需定义对应的属性
 */
@Component
public class RpcServerConfig {

    @Value("${rpcServer.port}")
    private int serverPort;

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

}
