/**
 * WSTestStockTransferFacadeSrvProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yhglobal.gongxiao.diaobo.order;

public class WSTestStockTransferFacadeSrvProxyServiceLocator extends org.apache.axis.client.Service implements com.yhglobal.gongxiao.diaobo.order.WSTestStockTransferFacadeSrvProxyService {

    public WSTestStockTransferFacadeSrvProxyServiceLocator() {
    }


    public WSTestStockTransferFacadeSrvProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSTestStockTransferFacadeSrvProxyServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSTestStockTransferFacade
    private String WSTestStockTransferFacade_address = "http://192.168.100.188:6888/ormrpc/services/WSTestStockTransferFacade";

    public String getWSTestStockTransferFacadeAddress() {
        return WSTestStockTransferFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private String WSTestStockTransferFacadeWSDDServiceName = "WSTestStockTransferFacade";

    public String getWSTestStockTransferFacadeWSDDServiceName() {
        return WSTestStockTransferFacadeWSDDServiceName;
    }

    public void setWSTestStockTransferFacadeWSDDServiceName(String name) {
        WSTestStockTransferFacadeWSDDServiceName = name;
    }

    public com.yhglobal.gongxiao.diaobo.order.WSTestStockTransferFacadeSrvProxy getWSTestStockTransferFacade() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSTestStockTransferFacade_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSTestStockTransferFacade(endpoint);
    }

    public com.yhglobal.gongxiao.diaobo.order.WSTestStockTransferFacadeSrvProxy getWSTestStockTransferFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.yhglobal.gongxiao.diaobo.order.WSTestStockTransferFacadeSoapBindingStub _stub = new com.yhglobal.gongxiao.diaobo.order.WSTestStockTransferFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSTestStockTransferFacadeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSTestStockTransferFacadeEndpointAddress(String address) {
        WSTestStockTransferFacade_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.yhglobal.gongxiao.diaobo.order.WSTestStockTransferFacadeSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                com.yhglobal.gongxiao.diaobo.order.WSTestStockTransferFacadeSoapBindingStub _stub = new com.yhglobal.gongxiao.diaobo.order.WSTestStockTransferFacadeSoapBindingStub(new java.net.URL(WSTestStockTransferFacade_address), this);
                _stub.setPortName(getWSTestStockTransferFacadeWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
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
        String inputPortName = portName.getLocalPart();
        if ("WSTestStockTransferFacade".equals(inputPortName)) {
            return getWSTestStockTransferFacade();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://192.168.100.188:6888/ormrpc/services/WSTestStockTransferFacade", "WSTestStockTransferFacadeSrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://192.168.100.188:6888/ormrpc/services/WSTestStockTransferFacade", "WSTestStockTransferFacade"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("WSTestStockTransferFacade".equals(portName)) {
            setWSTestStockTransferFacadeEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
