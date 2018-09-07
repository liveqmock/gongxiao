package com.yhglobal.gongxiao.transportataion.sendtotransportation.sales.model;

/**
 * 取消运单的请求
 */
public class CancelDispatchOrderRequest {

    private String custOrdNo;

    private String cargoCustId;

    private Integer ordSource;
    /**项目id*/
    private long projectId;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getCustOrdNo() {
        return custOrdNo;
    }

    public void setCustOrdNo(String custOrdNo) {
        this.custOrdNo = custOrdNo;
    }

    public String getCargoCustId() {
        return cargoCustId;
    }

    public void setCargoCustId(String cargoCustId) {
        this.cargoCustId = cargoCustId;
    }

    public Integer getOrdSource() {
        return ordSource;
    }

    public void setOrdSource(Integer ordSource) {
        this.ordSource = ordSource;
    }
}
