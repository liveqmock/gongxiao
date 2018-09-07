package com.yhglobal.gongxiao.edi.service;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.edi.dao.EdiDownloadRecordDao;
import com.yhglobal.gongxiao.edi.entity.EdiDownloadEnum;
import com.yhglobal.gongxiao.edi.entity.EdiResult;
import com.yhglobal.gongxiao.edi.entity.model.EdiRecord;
import com.yhglobal.gongxiao.edi.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.LinkedList;
import java.util.List;

/**
 * 下载ftp的service
 *
 * @author 葛灿
 */
@Service
public class DownloadEdiServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(DownloadEdiServiceImpl.class);

    @Autowired
    private EdiDownloadRecordDao ediDownloadRecordDao;


    public EdiResult selectEdiListByFileType(String fileType, long id) {
        logger.info("[IN][selectEdiListByFileType] fileType={}, id={}", fileType, id);
        //创建结果对象
        EdiResult ediResult = new EdiResult();
        long maxId = 0L;
        LinkedList<String> jsonList = new LinkedList<>();
        EdiDownloadEnum downloadEnum = EdiDownloadEnum.getEnumByFileType(fileType);
        // 该文件类型不存在
        if (downloadEnum == null) {
            // 抛出运行时异常
            throw new RuntimeException();
        }
        Class clazz = downloadEnum.getClazz();
        String jdFileType = downloadEnum.getJdFileType();
        // 查询出对方需要解析的文件
        List<EdiRecord> ediRecords = ediDownloadRecordDao.selectFileByTypeAndLowerLimit(jdFileType, id);
        // 进行解析
        for (EdiRecord record : ediRecords) {
            Long recordId = record.getRecordId();
            String filePath = record.getFilePath();
            maxId = Math.max(maxId, recordId);
            String jsonStr;
            try {
                Object object = XmlUtil.xml2Object(filePath, clazz);
                jsonStr = JSON.toJSONString(object);
                jsonList.add(jsonStr);
            } catch (JAXBException e) {
//                e.printStackTrace();
//                jsonStr = null;
//                continue;
            }
        }
        // 返回结果对象
        ediResult.setMaxId(maxId);
        ediResult.setJsonList(jsonList);
        return ediResult;
    }
}
