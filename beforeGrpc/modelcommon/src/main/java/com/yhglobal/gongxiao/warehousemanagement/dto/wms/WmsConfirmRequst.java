package com.yhglobal.gongxiao.warehousemanagement.dto.wms;

import java.io.Serializable;

/**
 * WMS返回的确认信息()
 * @author liukai
 */
public class WmsConfirmRequst implements Serializable {
    private String method;                              //方法名(入库确认、出库确认)
    private String data;                                //请求参数

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
