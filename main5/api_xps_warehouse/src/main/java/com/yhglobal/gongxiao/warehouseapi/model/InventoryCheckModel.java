package com.yhglobal.gongxiao.warehouseapi.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存核对模型
 * @author liukai
 */
public class InventoryCheckModel implements Serializable {
    private Date dateTime;
    private String projectId;
    private String projectName;
    private String warehouseId;
    private String warehouseName;
    private String productId;
    private String productCode;
    private String productName;
    private int fenxiaoPerfectQuantity;
    private int wmsPerfectQuantity;
    private int perfectDifferent;
    private int fenxiaoImperfectQuantity;
    private int wmsImperfectQuantity;
    private int imPerfectDifferent;
    private int fenxiaoEngineDamage;
    private int wmsEngineDamage;
    private int engineDamageDifferent;
    private int fenxiaoBoxDamage;
    private int wmsBoxDamage;
    private int boxDamageDifferent;
    private int fenxiaoFrozenStock;
    private int wmsFrozenStock;
    private int frozenStockDifferent;

    public int getPerfectDifferent() {
        return perfectDifferent;
    }

    public void setPerfectDifferent(int perfectDifferent) {
        this.perfectDifferent = perfectDifferent;
    }

    public int getImPerfectDifferent() {
        return imPerfectDifferent;
    }

    public void setImPerfectDifferent(int imPerfectDifferent) {
        this.imPerfectDifferent = imPerfectDifferent;
    }

    public int getEngineDamageDifferent() {
        return engineDamageDifferent;
    }

    public void setEngineDamageDifferent(int engineDamageDifferent) {
        this.engineDamageDifferent = engineDamageDifferent;
    }

    public int getBoxDamageDifferent() {
        return boxDamageDifferent;
    }

    public void setBoxDamageDifferent(int boxDamageDifferent) {
        this.boxDamageDifferent = boxDamageDifferent;
    }

    public int getFrozenStockDifferent() {
        return frozenStockDifferent;
    }

    public void setFrozenStockDifferent(int frozenStockDifferent) {
        this.frozenStockDifferent = frozenStockDifferent;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public int getFenxiaoPerfectQuantity() {
        return fenxiaoPerfectQuantity;
    }

    public void setFenxiaoPerfectQuantity(int fenxiaoPerfectQuantity) {
        this.fenxiaoPerfectQuantity = fenxiaoPerfectQuantity;
    }

    public int getWmsPerfectQuantity() {
        return wmsPerfectQuantity;
    }

    public void setWmsPerfectQuantity(int wmsPerfectQuantity) {
        this.wmsPerfectQuantity = wmsPerfectQuantity;
    }

    public int getFenxiaoImperfectQuantity() {
        return fenxiaoImperfectQuantity;
    }

    public void setFenxiaoImperfectQuantity(int fenxiaoImperfectQuantity) {
        this.fenxiaoImperfectQuantity = fenxiaoImperfectQuantity;
    }

    public int getWmsImperfectQuantity() {
        return wmsImperfectQuantity;
    }

    public void setWmsImperfectQuantity(int wmsImperfectQuantity) {
        this.wmsImperfectQuantity = wmsImperfectQuantity;
    }

    public int getFenxiaoEngineDamage() {
        return fenxiaoEngineDamage;
    }

    public void setFenxiaoEngineDamage(int fenxiaoEngineDamage) {
        this.fenxiaoEngineDamage = fenxiaoEngineDamage;
    }

    public int getWmsEngineDamage() {
        return wmsEngineDamage;
    }

    public void setWmsEngineDamage(int wmsEngineDamage) {
        this.wmsEngineDamage = wmsEngineDamage;
    }

    public int getFenxiaoBoxDamage() {
        return fenxiaoBoxDamage;
    }

    public void setFenxiaoBoxDamage(int fenxiaoBoxDamage) {
        this.fenxiaoBoxDamage = fenxiaoBoxDamage;
    }

    public int getWmsBoxDamage() {
        return wmsBoxDamage;
    }

    public void setWmsBoxDamage(int wmsBoxDamage) {
        this.wmsBoxDamage = wmsBoxDamage;
    }

    public int getFenxiaoFrozenStock() {
        return fenxiaoFrozenStock;
    }

    public void setFenxiaoFrozenStock(int fenxiaoFrozenStock) {
        this.fenxiaoFrozenStock = fenxiaoFrozenStock;
    }

    public int getWmsFrozenStock() {
        return wmsFrozenStock;
    }

    public void setWmsFrozenStock(int wmsFrozenStock) {
        this.wmsFrozenStock = wmsFrozenStock;
    }
}
