package com.yhglobal.gongxiao.foundation.viewobject;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class ProductVo {

    private long productBusinessId;

    private String productCode;

    private String productName;

    private String easCode;

    private String wmsCode;

    private String dataStatus;

    private int packageNumber;

    public long getProductBusinessId() {
        return productBusinessId;
    }

    public void setProductBusinessId(long productBusinessId) {
        this.productBusinessId = productBusinessId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getEasCode() {
        return easCode;
    }

    public void setEasCode(String easCode) {
        this.easCode = easCode;
    }

    public String getWmsCode() {
        return wmsCode;
    }

    public void setWmsCode(String wmsCode) {
        this.wmsCode = wmsCode;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public int getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(int packageNumber) {
        this.packageNumber = packageNumber;
    }
}
