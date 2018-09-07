package com.yhglobal.gongxiao.edi.entity;

import java.util.List;

/**
 * edi数据拉取结果对象
 *
 * @author 葛灿
 */
public class EdiResult {

    /**
     * 本次检索数据时遍历的最大id值,
     * 下一次查询时,将该id作为参数传入
     */
    private long maxId;

    /**
     * 存储了文件解析后的json
     */
    private List<String> jsonList;

    public long getMaxId() {
        return maxId;
    }

    public void setMaxId(long maxId) {
        this.maxId = maxId;
    }

    public List<String> getJsonList() {
        return jsonList;
    }

    public void setJsonList(List<String> jsonList) {
        this.jsonList = jsonList;
    }
}
