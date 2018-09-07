package com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstockconfirm;


import java.io.Serializable;

/**
 * WMS出库确认信息
 * @author liukai
 */
public class WmsOutStockConfirmRequest implements Serializable {
    private String method;                                          //方法名
    private String data;                                              //出库确认请求

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
