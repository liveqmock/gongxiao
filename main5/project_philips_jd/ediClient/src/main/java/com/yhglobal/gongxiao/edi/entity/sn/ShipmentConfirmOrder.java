package com.yhglobal.gongxiao.edi.entity.sn;

import com.yhglobal.gongxiao.edi.entity.common.TransferSubject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * @author 葛灿
 */
@XmlRootElement(name = "ProductCirculationData")
public class ShipmentConfirmOrder {

    /**
     * 传送标题
     */
    @XmlElement(name = "TransferSubject")
    private TransferSubject transferSubject;

    /**
     * 发货确认单据总目
     */
    @XmlElement(name = "ShipmentNotificationSummary")
    private ShipmentConfirmationSummary shipmentConfirmationSummary;

    /**
     * 发货确认单据细目
     */
    @XmlElement(name = "ShipmentNotificationDetail")
    private List<ShipmentConfirmationDetail> shipmentConfirmationDetailList;

    @XmlTransient
    public TransferSubject getTransferSubject() {
        return transferSubject;
    }
    @XmlTransient
    public ShipmentConfirmationSummary getShipmentConfirmationSummary() {
        return shipmentConfirmationSummary;
    }
    @XmlTransient
    public List<ShipmentConfirmationDetail> getShipmentConfirmationDetailList() {
        return shipmentConfirmationDetailList;
    }

    public void setTransferSubject(TransferSubject transferSubject) {
        this.transferSubject = transferSubject;
    }

    public void setShipmentConfirmationSummary(ShipmentConfirmationSummary shipmentConfirmationSummary) {
        this.shipmentConfirmationSummary = shipmentConfirmationSummary;
    }

    public void setShipmentConfirmationDetailList(List<ShipmentConfirmationDetail> shipmentConfirmationDetailList) {
        this.shipmentConfirmationDetailList = shipmentConfirmationDetailList;
    }
}
