package com.yhglobal.gongxiao.edi.service.impl;

import com.yhglobal.gongxiao.edi.dao.EdiDownloadRecordDao;
import com.yhglobal.gongxiao.edi.dao.EdiUploadRecordDao;
import com.yhglobal.gongxiao.edi.entity.EdiDownloadEnum;
import com.yhglobal.gongxiao.edi.entity.EdiUploadEnum;
import com.yhglobal.gongxiao.edi.entity.config.FtpConfig;
import com.yhglobal.gongxiao.edi.entity.model.EdiRecord;
import com.yhglobal.gongxiao.edi.util.Md5Util;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * FTP下载/上传 定时任务累
 *
 * @author 葛灿
 */
@Component
public class FtpSchduledTask {

    private static final Logger logger = LoggerFactory.getLogger(FtpSchduledTask.class);

    @Autowired
    private EdiDownloadRecordDao ediDownloadRecordDao;
    @Autowired
    private EdiUploadRecordDao ediUploadRecordDao;


    @Autowired
    private FtpConfig ftpConfig;

    //    @Scheduled(fixedDelayString = "${ftp.downloadRate}")
    public void download() {
        LocalDateTime localDateTime = LocalDateTime.now();
        logger.info("[IN][download edi] start downloading ! datetime={}", localDateTime);
        //读取配置文件
        String host = ftpConfig.getIpAddress();
        Integer port = ftpConfig.getPort();
        String username = ftpConfig.getUserName();
        String password = ftpConfig.getPassword();
        String localPath = ftpConfig.getLocalDownloadPath();
        String remotePath = ftpConfig.getRemotePath();
        LocalDate now = LocalDate.now();
        String dateStr = "/" + now.getYear() + "/" + now.getMonthValue() + "/" + now.getDayOfMonth() + "/";
        boolean downloadSuccess;
        FTPClient ftp = new FTPClient();
        OutputStream outputStream = null;
        try {
            //连接ftp
            int reply;
            ftp.connect(host, port);
            ftp.setControlEncoding("GBK");
            ftp.enterLocalPassiveMode();

            ftp.isRemoteVerificationEnabled();
//             设置默认超时时间
            ftp.setDefaultTimeout(10 * 60 * 1000);
//             设置连接超时时间
            ftp.setConnectTimeout(10 * 60 * 1000);
//             设置数据超时时间
            ftp.setDataTimeout(10 * 60 * 1000);
            ftp.setKeepAlive(true);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
//            ftp.setFileTransferMode(FTPClient.STREAM_TRANSFER_MODE);
            // socket连接，设置socket连接超时时间
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                logger.error("[out] can NOT connect!");
                return;
            }
            Set<String> existedFiles;
            //根据文件夹名称判断,分别处理
            int total = 0;
            Set<String> jdFileTypeSet = EdiDownloadEnum.getJdFileTypeSet();
            for (String directory : jdFileTypeSet) {
                int subtotal = 0;
                logger.info("start downloading directory [{}].", directory);
                String furtherRemotePath = remotePath + directory;
                // 转移到FTP服务器目录
                boolean changeWorkingDirectory = ftp.changeWorkingDirectory(furtherRemotePath);
                if (!changeWorkingDirectory) {
                    logger.error("directory {} is NOT existed!", furtherRemotePath);
                    continue;
                }
                //查询出已经下载过的该类型文件
                existedFiles = ediDownloadRecordDao.selectFileNameListByType(directory);
                FTPFile[] fs = ftp.listFiles();
                for (FTPFile ftpFile : fs) {
                    String remoteFileName = ftpFile.getName();
                    if (existedFiles.contains(remoteFileName)) {
                        //如果已经下载过,遍历下一个文件
                        continue;
                    }
                    String furtherLocalPath = localPath + directory + dateStr;
                    File poLocalPathDirectory = new File(furtherLocalPath);
                    if (!poLocalPathDirectory.exists()) {
                        poLocalPathDirectory.mkdirs();
                    }
                    String localCompletePath = furtherLocalPath + remoteFileName;
                    File localFile = new File(localCompletePath);
                    try {
                        outputStream = new FileOutputStream(localFile);
                        downloadSuccess = ftp.retrieveFile(new String(remoteFileName.getBytes("GBK"), "ISO-8859-1"), outputStream);
                    } catch (ConnectException | SocketTimeoutException e) {
                        // 文件传输失败
                        logger.error("{} download FAILED ! errorMessage:" + e, remoteFileName);
                        continue;
                    } catch (SocketException e) {
                        // 连接中断
                        logger.error("[out] lost connection" + e);
                        e.printStackTrace();
                        return;
                    }
                    ftp.sendCommand("Keep current connection");
                    if (!downloadSuccess) {
                        //下载失败
                        logger.error("{} download FAILED !", remoteFileName);
                    } else {
                        logger.debug("{} download success", remoteFileName);
                        EdiRecord downloadRecord = new EdiRecord();
                        downloadRecord.setFileName(remoteFileName);
                        downloadRecord.setFilePath(localFile.getPath());
                        downloadRecord.setFileMd5(Md5Util.getFileMD5(localFile));
                        ediDownloadRecordDao.insert(directory, downloadRecord);
                        total++;
                        subtotal++;
                    }
                    if (total % 100 == 0) {
                        //每下载一百个文件打一次日志
                        logger.info("already downloaded {} files", total);
                    }
//                    ftp.completePendingCommand();
//                    outputStream.flush();
                }
                logger.info("directory {} downloaded success! subtotal {}", directory, subtotal);
            }
            ftp.logout();
            logger.info("[out] download completed. total {}", total);
        } catch (IOException e) {
            logger.error("[out] download IoException" + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Scheduled(fixedDelayString = "${ftp.uploadRate}")
    public void upload() {
        LocalDateTime localDateTime = LocalDateTime.now();
        logger.info("[IN][upload edi] start uploading ! datetime={}", localDateTime);
        String host = ftpConfig.getIpAddress();
        Integer port = ftpConfig.getPort();
        String username = ftpConfig.getUserName();
        String password = ftpConfig.getPassword();
        String localPath = ftpConfig.getLocalUploadPath();
        String remotePath = ftpConfig.getRemotePath();
        boolean result;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.setKeepAlive(true);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                logger.error("[out] can NOT connect!");
                return;
            }
            //切换到上传目录
            InputStream inputStream;
            LocalDate now = LocalDate.now();
            String dateStr = "/" + now.getYear() + "/" + now.getMonthValue() + "/" + now.getDayOfMonth() + "/";
            int count = 0;
            Set<String> jdFileTypeSet = EdiUploadEnum.getJdFileTypeSet();
            for (String fileType : jdFileTypeSet) {
                logger.info("start uploading directory [{}].", fileType);
                //查询未上传过的文件
                List<EdiRecord> ediRecords = ediUploadRecordDao.selectFileNameListByType(fileType);

                // 切换到远程ftp目录
                String furtherRemotePath = remotePath + fileType;
                if (!ftp.changeWorkingDirectory(furtherRemotePath)) {
                    logger.error("{} directory does NOT exist on remote", furtherRemotePath);
                    continue;
                }

                for (EdiRecord ediRecord : ediRecords) {
                    String filePath = ediRecord.getFilePath();
                    File file = new File(filePath);
                    if (!file.exists()) {
                        // 文件未找到
                        continue;
//                        throw new RuntimeException();
                    }
                    inputStream = new FileInputStream(file);
                    String fileName = file.getName();
                    ftp.sendCommand("Keep current connection");
                    //上传文件
                    try {
                        result = ftp.storeFile((new String(fileName.getBytes("GBK"), "ISO-8859-1")), inputStream);
                    } catch (ConnectException | SocketTimeoutException e) {
                        logger.error("{} upload FAILED ! errorMessage:" + e, fileName);
                        continue;
                    } catch (SocketException e) {
                        logger.error("[out] lost connection" + e);
                        e.printStackTrace();
                        return;
                    } finally {
                        inputStream.close();
                    }
                    //失败就打日志,成功就修改上传状态
                    if (!result) {
                        logger.error("{} upload FAILED !", fileName);
                    } else {
                        ediUploadRecordDao.updateUploadStatus(fileType, ediRecord.getRecordId());
                        count++;
                        logger.debug("{} upload success", fileName);
                    }
                    if (count % 50 == 0) {
                        //每下载一百个文件打一次日志
                        logger.info("already upload {} files", count);
                    }
                }
                logger.info("directory {} upload finished {}.", fileType);
            }
            ftp.logout();
            logger.info("[out] upload completed.");
        } catch (IOException e) {
            logger.error("[out] upload IoException" + e.getMessage(), e);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
    }


}
