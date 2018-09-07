package com.yhglobal.gongxiao.edi.entity.poa;

import com.yhglobal.gongxiao.edi.entity.common.TransferSubject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * @author 葛灿
 */
@XmlRootElement(name = "ProductCirculationData")
public class PurchaseConfirmOrder {

    /**
     * 传送标题
     */
    @XmlElement(name = "TransferSubject")
    private TransferSubject transferSubject;
    @XmlElement(name = "POAcknowledgeSummary")
    private POAcknowledgeSummary poAcknowledgeSummary;
    @XmlElement(name = "POAcknowledgeDetail")
    private List<POAcknowledgeDetail> poAcknowledgeDetails;

    @XmlTransient
    public TransferSubject getTransferSubject() {
        return transferSubject;
    }
    @XmlTransient
    public POAcknowledgeSummary getPoAcknowledgeSummary() {
        return poAcknowledgeSummary;
    }
    @XmlTransient
    public List<POAcknowledgeDetail> getPoAcknowledgeDetails() {
        return poAcknowledgeDetails;
    }


    public void setTransferSubject(TransferSubject transferSubject) {
        this.transferSubject = transferSubject;
    }

    public void setPoAcknowledgeSummary(POAcknowledgeSummary poAcknowledgeSummary) {
        this.poAcknowledgeSummary = poAcknowledgeSummary;
    }

    public void setPoAcknowledgeDetails(List<POAcknowledgeDetail> poAcknowledgeDetails) {
        this.poAcknowledgeDetails = poAcknowledgeDetails;
    }
}
