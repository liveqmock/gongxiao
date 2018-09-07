package com.yhglobal.gongxiao.foudation.file.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foudation.product.service.impl.BrandServiceImpl;
import com.yhglobal.gongxiao.foundation.file.File;
import com.yhglobal.gongxiao.foundation.file.FileService;
import com.yhglobal.gongxiao.foundation.file.dao.FileDao;
import com.yhglobal.gongxiao.foundation.product.brand.model.Brand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 文件上传 下载服务类
 *
 * @author: 陈浩
 **/
@Service(timeout = 2000)
public class FileServiceImpl implements FileService {

    private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    FileDao fileDao;

    @Override
    public int addFileInfo(RpcHeader rpcHeader, String realFileName, String fileName, String filePath, String creatorId,String creatorName) {
        logger.info("#traceId={}# [IN][addFileInfo] params: realFileName={} ,fileName={}, filePath={}, creatorId={}, creatorName={}",
                rpcHeader.traceId, realFileName,fileName,filePath,creatorId,creatorName);
        try {
            File file = new File();
            file.setCreatedById(Long.parseLong(creatorId));
            file.setCreatedByName(creatorName);
            file.setFileName(fileName);
            file.setFileRealName(realFileName);
            file.setFilePath(filePath);
            int i = fileDao.addFileRecord(file);
            logger.info("#traceId={}# [OUT] addFileInfo success ", rpcHeader.traceId);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
