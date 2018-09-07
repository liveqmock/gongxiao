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
public class MainWarehouseReturnOrder {


    /**
     * 传送标题
     */
    @XmlElement(name = "TransferSubject")
    private TransferSubject transferSubject;
    @XmlElement(name = "ReturnOrderSummary")
    private MainWarehouseReturnSummary mainWarehouseReturnSummary;
    @XmlElement(name = "ReturnOrderDetail")
    private List<MainWarehouseReturnDetail> mainWarehouseReturnDetailList;


    @XmlTransient
    public TransferSubject getTransferSubject() {
        return transferSubject;
    }
    @XmlTransient
    public MainWarehouseReturnSummary getMainWarehouseReturnSummary() {
        return mainWarehouseReturnSummary;
    }
    @XmlTransient
    public List<MainWarehouseReturnDetail> getMainWarehouseReturnDetailList() {
        return mainWarehouseReturnDetailList;
    }


    public void setTransferSubject(TransferSubject transferSubject) {
        this.transferSubject = transferSubject;
    }

    public void setMainWarehouseReturnSummary(MainWarehouseReturnSummary mainWarehouseReturnSummary) {
        this.mainWarehouseReturnSummary = mainWarehouseReturnSummary;
    }

    public void setMainWarehouseReturnDetailList(List<MainWarehouseReturnDetail> mainWarehouseReturnDetailList) {
        this.mainWarehouseReturnDetailList = mainWarehouseReturnDetailList;
    }


}
