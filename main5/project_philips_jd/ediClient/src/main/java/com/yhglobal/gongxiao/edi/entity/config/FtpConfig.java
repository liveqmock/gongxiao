package com.yhglobal.gongxiao.edi.entity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ftp链接常量
 * @Author: gecan
 */
@Component
public class FtpConfig {
    /** ip地址*/
    @Value("${ftp.ip}")
    private String ipAddress;
    /**端口号 */
    @Value("${ftp.port}")
    private Integer port;
    /**用户名*/
    @Value("${ftp.userName}")
    private String userName;
    /**密码*/
    @Value("${ftp.password}")
    private String password;
    /**ftp路径*/
    @Value("${ftp.remotePath}")
    private String remotePath;
    /**本地路径*/
    @Value("${ftp.localDownloadPath}")
    private String localDownloadPath;
    /**本地路径*/
    @Value("${ftp.localUploadPath}")
    private String localUploadPath;
    /**远端编码格式*/
    @Value("${ftp.encoding}")
    private String encoding;

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getLocalDownloadPath() {
        return localDownloadPath;
    }

    public void setLocalDownloadPath(String localDownloadPath) {
        this.localDownloadPath = localDownloadPath;
    }

    public String getLocalUploadPath() {
        return localUploadPath;
    }

    public void setLocalUploadPath(String localUploadPath) {
        this.localUploadPath = localUploadPath;
    }
}