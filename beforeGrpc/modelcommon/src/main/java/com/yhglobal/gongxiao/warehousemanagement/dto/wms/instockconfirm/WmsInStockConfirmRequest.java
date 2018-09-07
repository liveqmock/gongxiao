package com.yhglobal.gongxiao.warehousemanagement.dto.wms.instockconfirm;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * WMS入库确认信息
 * @author liukai
 */
@XmlRootElement(name="request")
public class WmsInStockConfirmRequest implements Serializable {
    private String method;                              //方法名
    private String data;                                  //请求参数

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

