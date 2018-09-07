package com.yhglobal.gongxiao.edi.entity.ro;

import com.yhglobal.gongxiao.edi.entity.common.TransferSubject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * @author 葛灿
 */
@XmlRootElement(name = "ProductCirculationData")
public class SpareWarehouseReturnOrder {


    /**
     * 传送标题
     */
    @XmlElement(name = "TransferSubject")
    private TransferSubject transferSubject;
    @XmlElement(name = "ReturnOrderSummary")
    private SpareWarehouseReturnSummary spareWarehouseReturnSummary;
    @XmlElement(name = "ReturnOrderDetail")
    private List<SpareWarehouseReturnDetail> spareWarehouseReturnDetails;


    @XmlTransient
    public TransferSubject getTransferSubject() {
        return transferSubject;
    }
    @XmlTransient
    public SpareWarehouseReturnSummary getSpareWarehouseReturnSummary() {
        return spareWarehouseReturnSummary;
    }
    @XmlTransient
    public List<SpareWarehouseReturnDetail> getSpareWarehouseReturnDetails() {
        return spareWarehouseReturnDetails;
    }


    public void setTransferSubject(TransferSubject transferSubject) {
        this.transferSubject = transferSubject;
    }

    public void setSpareWarehouseReturnSummary(SpareWarehouseReturnSummary spareWarehouseReturnSummary) {
        this.spareWarehouseReturnSummary = spareWarehouseReturnSummary;
    }

    public void setSpareWarehouseReturnDetails(List<SpareWarehouseReturnDetail> spareWarehouseReturnDetails) {
        this.spareWarehouseReturnDetails = spareWarehouseReturnDetails;
    }
}
