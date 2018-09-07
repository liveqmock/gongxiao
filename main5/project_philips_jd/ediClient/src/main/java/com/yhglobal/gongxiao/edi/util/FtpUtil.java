package com.yhglobal.gongxiao.edi.util;

import com.yhglobal.gongxiao.edi.entity.config.FtpConfig;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ftp上传下载工具类
 */
@Component
public class FtpUtil {

    @Autowired
    FtpConfig ftpConfig;

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param host     FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param basePath FTP服务器基础目录
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String host, int port, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath + filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    /**
     * Description: 从FTP服务器下载文件
     *
     * @param host       FTP服务器hostname
     * @param port       FTP服务器端口
     * @param username   FTP登录账号
     * @param password   FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName   要下载的文件名
     * @param localPath  下载后保存到本地的路径
     * @return
     */
    public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
                                       String fileName, String localPath) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.setControlEncoding("GBK");
            ftp.enterLocalPassiveMode();
            ftp.setFileTransferMode(FTPClient.STREAM_TRANSFER_MODE);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                String ffName = ff.getName();
                if (ffName.equals(fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());
                    InputStream inputStream = ftp.retrieveFileStream(remotePath);
                    OutputStream outputStream = new FileOutputStream(localFile);
                    result = ftp.retrieveFile((new String(fileName.getBytes("GBK"), "ISO-8859-1")), outputStream);
                    outputStream.close();
                    result = true;
                }
            }

            ftp.logout();
//            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        try {
//            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/WEB-INF/mvc-dispatcher-servlet.xml");
//            ApplicationContext applicationContext = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml");
//            FtpTransport ftpTransport = (FtpTransport) applicationContext.getBean("ftpTransport");
//            Object ftpTransport1 = applicationContext.getBean("ftpTransport");
//            /********************      download          ***********************/
//            boolean flag = ftpTransport.download();
////            boolean flag = ftpConfig.upload();
//            /********************      upload          ***********************/
////            FileInputStream inputStream = new FileInputStream("E:\\WMS全库存.xlsx");
////            String fileName = "WMS全库存.xlsx";
////            boolean flag = uploadFile("172.29.56.6", 21, "ftpTest", "123", "\\", "0625\\", new String(fileName.getBytes("GBK"), "iso-8859-1"), inputStream);
            System.out.println("hha");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}