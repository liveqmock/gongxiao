package com.yhglobal.gongxiao.edi.entity.model;

import java.io.Serializable;

/**
 * edi下载记录
 *
 * @author 葛灿
 */
public class EdiRecord implements Serializable {

    private Long recordId;
    // 文件名
    private String fileName;
    // json
    private String fileMd5;

    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }


}
