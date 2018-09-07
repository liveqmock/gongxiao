/**
 * WSWMSPurInWarehsBillFacadeSrvProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yhglobal.gongxiao.purchase.purchaseinbound;

public class WSWMSPurInWarehsBillFacadeSrvProxyServiceLocator extends org.apache.axis.client.Service implements com.yhglobal.gongxiao.purchase.purchaseinbound.WSWMSPurInWarehsBillFacadeSrvProxyService {

    public WSWMSPurInWarehsBillFacadeSrvProxyServiceLocator() {
    }


    public WSWMSPurInWarehsBillFacadeSrvProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSWMSPurInWarehsBillFacadeSrvProxyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSWMSPurInWarehsBillFacade
    private java.lang.String WSWMSPurInWarehsBillFacade_address = "http://192.168.100.67:6888/ormrpc/services/WSWMSPurInWarehsBillFacade";

    public java.lang.String getWSWMSPurInWarehsBillFacadeAddress() {
        return WSWMSPurInWarehsBillFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSWMSPurInWarehsBillFacadeWSDDServiceName = "WSWMSPurInWarehsBillFacade";

    public java.lang.String getWSWMSPurInWarehsBillFacadeWSDDServiceName() {
        return WSWMSPurInWarehsBillFacadeWSDDServiceName;
    }

    public void setWSWMSPurInWarehsBillFacadeWSDDServiceName(java.lang.String name) {
        WSWMSPurInWarehsBillFacadeWSDDServiceName = name;
    }

    public com.yhglobal.gongxiao.purchase.purchaseinbound.WSWMSPurInWarehsBillFacadeSrvProxy getWSWMSPurInWarehsBillFacade() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSWMSPurInWarehsBillFacade_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSWMSPurInWarehsBillFacade(endpoint);
    }

    public com.yhglobal.gongxiao.purchase.purchaseinbound.WSWMSPurInWarehsBillFacadeSrvProxy getWSWMSPurInWarehsBillFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.yhglobal.gongxiao.purchase.purchaseinbound.WSWMSPurInWarehsBillFacadeSoapBindingStub _stub = new com.yhglobal.gongxiao.purchase.purchaseinbound.WSWMSPurInWarehsBillFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSWMSPurInWarehsBillFacadeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSWMSPurInWarehsBillFacadeEndpointAddress(java.lang.String address) {
        WSWMSPurInWarehsBillFacade_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.yhglobal.gongxiao.purchase.purchaseinbound.WSWMSPurInWarehsBillFacadeSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                com.yhglobal.gongxiao.purchase.purchaseinbound.WSWMSPurInWarehsBillFacadeSoapBindingStub _stub = new com.yhglobal.gongxiao.purchase.purchaseinbound.WSWMSPurInWarehsBillFacadeSoapBindingStub(new java.net.URL(WSWMSPurInWarehsBillFacade_address), this);
                _stub.setPortName(getWSWMSPurInWarehsBillFacadeWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WSWMSPurInWarehsBillFacade".equals(inputPortName)) {
            return getWSWMSPurInWarehsBillFacade();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://192.168.100.67:6888/ormrpc/services/WSWMSPurInWarehsBillFacade", "WSWMSPurInWarehsBillFacadeSrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://192.168.100.67:6888/ormrpc/services/WSWMSPurInWarehsBillFacade", "WSWMSPurInWarehsBillFacade"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSWMSPurInWarehsBillFacade".equals(portName)) {
            setWSWMSPurInWarehsBillFacadeEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
