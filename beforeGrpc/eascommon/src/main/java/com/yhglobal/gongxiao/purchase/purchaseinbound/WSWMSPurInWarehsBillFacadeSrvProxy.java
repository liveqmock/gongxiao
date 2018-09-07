/**
 * WSWMSPurInWarehsBillFacadeSrvProxy.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yhglobal.gongxiao.purchase.purchaseinbound;

public interface WSWMSPurInWarehsBillFacadeSrvProxy extends java.rmi.Remote {
    public java.lang.String importData(java.lang.String json) throws java.rmi.RemoteException, com.yhglobal.gongxiao.purchase.purchaseinbound.WSInvokeException;
    public java.lang.String importData4SaleIssueBill(java.lang.String json) throws java.rmi.RemoteException, com.yhglobal.gongxiao.purchase.purchaseinbound.WSInvokeException;
    public java.lang.String importData4SaleReturns(java.lang.String json) throws java.rmi.RemoteException, com.yhglobal.gongxiao.purchase.purchaseinbound.WSInvokeException;
    public java.lang.String closePostRequisition(java.lang.String json) throws java.rmi.RemoteException, com.yhglobal.gongxiao.purchase.purchaseinbound.WSInvokeException;
    public java.lang.String importData4RedSaleIssueBill(java.lang.String json) throws java.rmi.RemoteException, com.yhglobal.gongxiao.purchase.purchaseinbound.WSInvokeException;
    public java.lang.String closePurOrder(java.lang.String json) throws java.rmi.RemoteException, com.yhglobal.gongxiao.purchase.purchaseinbound.WSInvokeException;
    public java.lang.String importData4OtherInWarehsBill(java.lang.String json) throws java.rmi.RemoteException, com.yhglobal.gongxiao.purchase.purchaseinbound.WSInvokeException;
    public java.lang.String importData4OtherOutWarehsBill(java.lang.String json) throws java.rmi.RemoteException, com.yhglobal.gongxiao.purchase.purchaseinbound.WSInvokeException;
    public java.lang.String importData4PurReturns(java.lang.String json) throws java.rmi.RemoteException, com.yhglobal.gongxiao.purchase.purchaseinbound.WSInvokeException;
    public java.lang.String importData4SaleOrder(java.lang.String json) throws java.rmi.RemoteException, com.yhglobal.gongxiao.purchase.purchaseinbound.WSInvokeException;
}
