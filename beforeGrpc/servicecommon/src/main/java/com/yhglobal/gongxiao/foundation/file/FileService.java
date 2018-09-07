package com.yhglobal.gongxiao.foundation.file;

import com.yhglobal.gongxiao.common.RpcHeader;

/**
 * 文件服务类
 *
 * @author: 陈浩
 **/
public interface FileService {

    /**
     * 添加新文件
     * @param rpcHeader
     * @param realFileName 实际文件名
     * @param fileName  转换后的文件名
     * @param filePath  文件存储路径
     * @param creatorId 创建人
     * @return
     */
    int addFileInfo (RpcHeader rpcHeader,
                     String realFileName,
                     String fileName,
                     String filePath,
                     String creatorId,
                     String creatorName);

}
