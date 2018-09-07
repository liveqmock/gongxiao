package com.yhglobal.gongxiao.foudation.model;

import java.util.List;

/**
 * 请求数据
 *
 * @author: 陈浩
 **/
public class RequestData {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 关键字 : 项目名称,仓库名称,目录名称等等
     */
    private String keywords;
    /**
     * 根据表的某一列过滤
     */
    private List<Filter> filterList;
    /**
     * 跳过多少数据
     */
    private int skipCount;
    /**
     * 结果集大小
     */
    private int maxResultCount;

    public RequestData() {
    }

    public RequestData(String tableName, int skipCount, int maxResultCount) {
        this.tableName = tableName;
        this.skipCount = skipCount;
        this.maxResultCount = maxResultCount;
    }



    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<Filter> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<Filter> filterList) {
        this.filterList = filterList;
    }

    public int getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(int skipCount) {
        this.skipCount = skipCount;
    }

    public int getMaxResultCount() {
        return maxResultCount;
    }

    public void setMaxResultCount(int maxResultCount) {
        this.maxResultCount = maxResultCount;
    }
}
