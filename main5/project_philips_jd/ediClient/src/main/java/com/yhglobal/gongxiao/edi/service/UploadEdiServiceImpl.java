package com.yhglobal.gongxiao.edi.service;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.edi.dao.EdiUploadRecordDao;
import com.yhglobal.gongxiao.edi.entity.EdiDownloadEnum;
import com.yhglobal.gongxiao.edi.entity.EdiUploadEnum;
import com.yhglobal.gongxiao.edi.entity.config.FtpConfig;
import com.yhglobal.gongxiao.edi.entity.model.EdiRecord;
import com.yhglobal.gongxiao.edi.util.Md5Util;
import com.yhglobal.gongxiao.edi.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Map;

/**
 * 下载ftp的service
 *
 * @author 葛灿
 */
@Service
public class UploadEdiServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(UploadEdiServiceImpl.class);

    @Autowired
    private EdiUploadRecordDao ediUploadRecordDao;

    @Autowired
    private FtpConfig ftpConfig;


    public LinkedList<String> selectEdiListByFileType(String fileType, Map<String, String> jsonMap) {
        LinkedList<String> failedFileName = new LinkedList<>();
        logger.info("[IN][selectEdiListByFileType] fileType={}, jsonMap={}", fileType, jsonMap);
        // 查找文件对应的class
        EdiUploadEnum upEnum = EdiUploadEnum.getEnumByFileType(fileType);
        // 该文件类型不存在
        if (upEnum == null) {
            // 抛出运行时异常
            throw new RuntimeException();
        }
        Class clazz = upEnum.getClazz();
        LocalDate now = LocalDate.now();
        String localUploadPath = ftpConfig.getLocalUploadPath();
        String dateStr = "/" + now.getYear() + "/" + now.getMonthValue() + "/" + now.getDayOfMonth() + "/";
        // 进行持久化
        for (String fileName : jsonMap.keySet()) {
            // 转换为xml
            String path =  localUploadPath + dateStr;
            File file = new File(path);
            if (!file.exists()){
                file.mkdirs();
            }
            String filePath = localUploadPath + dateStr + fileName;
            String json = jsonMap.get(fileName);
            Object object = JSON.parseObject(json, clazz);
            XmlUtil.object2Xml(filePath, object);
            // 生成传输记录
            EdiRecord ediRecord = new EdiRecord();
            ediRecord.setFileName(fileName);
            ediRecord.setFilePath(filePath);
            try {
                String fileMD5 = Md5Util.getFileMD5(new File(filePath));
                ediRecord.setFileMd5(fileMD5);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                failedFileName.add(fileName);
            }
            ediUploadRecordDao.insert(fileType, ediRecord);
        }
        return failedFileName;
    }
}
