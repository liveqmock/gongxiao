package com.yhglobal.gongxiao.warehousemanagement.dto.wms;

import java.io.Serializable;

public class DriverInfo implements Serializable{
    private String driverName;
    private String driverMobile;
    private String dlateNumber;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    public String getDlateNumber() {
        return dlateNumber;
    }

    public void setDlateNumber(String dlateNumber) {
        this.dlateNumber = dlateNumber;
    }
}
