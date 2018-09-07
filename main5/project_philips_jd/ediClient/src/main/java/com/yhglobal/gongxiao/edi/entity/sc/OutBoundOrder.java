package com.yhglobal.gongxiao.edi.entity.sc;

import com.yhglobal.gongxiao.edi.entity.common.TransferSubject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * @author 葛灿
 */
@XmlRootElement(name = "ProductCirculationData")
public class OutBoundOrder {

    /**
     * 传送标题
     */
    @XmlElement(name = "TransferSubject")
    private TransferSubject transferSubject;

    @XmlElement(name = "ShipmentConfirmationSummary")
    private ShipmentNotificationSummary shipmentNotificationSummary;

    @XmlElement(name = "ShipmentConfirmationDetail")
    private List<ShipmentNotificationDetail> shipmentNotificationDetailList;

    @XmlTransient
    public TransferSubject getTransferSubject() {
        return transferSubject;
    }
    @XmlTransient
    public ShipmentNotificationSummary getShipmentNotificationSummary() {
        return shipmentNotificationSummary;
    }
    @XmlTransient
    public List<ShipmentNotificationDetail> getShipmentNotificationDetailList() {
        return shipmentNotificationDetailList;
    }

    public void setTransferSubject(TransferSubject transferSubject) {
        this.transferSubject = transferSubject;
    }

    public void setShipmentNotificationSummary(ShipmentNotificationSummary shipmentNotificationSummary) {
        this.shipmentNotificationSummary = shipmentNotificationSummary;
    }

    public void setShipmentNotificationDetailList(List<ShipmentNotificationDetail> shipmentNotificationDetailList) {
        this.shipmentNotificationDetailList = shipmentNotificationDetailList;
    }
}
