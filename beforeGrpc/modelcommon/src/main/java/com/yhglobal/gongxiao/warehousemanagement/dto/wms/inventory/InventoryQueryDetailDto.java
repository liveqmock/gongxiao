package com.yhglobal.gongxiao.warehousemanagement.dto.wms.inventory;

import java.io.Serializable;
import java.util.List;

/**
 * 库存查询仓库信息列表
 * @author liukai
 */
public class InventoryQueryDetailDto implements Serializable {
    private String ckid;           //仓库编码
    private String ownerCode;       //货主ID
    private String projectCode;     //项目code
    private List<InventoryQueryDetailItemDto> items;   //商品信息列表

    public InventoryQueryDetailDto(String ckid, String ownerCode, String projectCode, List<InventoryQueryDetailItemDto> items) {
        this.ckid = ckid;
        this.ownerCode = ownerCode;
        this.projectCode = projectCode;
        this.items = items;
    }

    public String getCkid() {
        return ckid;
    }

    public void setCkid(String ckid) {
        this.ckid = ckid;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public List<InventoryQueryDetailItemDto> getItems() {
        return items;
    }

    public void setItems(List<InventoryQueryDetailItemDto> items) {
        this.items = items;
    }
}
