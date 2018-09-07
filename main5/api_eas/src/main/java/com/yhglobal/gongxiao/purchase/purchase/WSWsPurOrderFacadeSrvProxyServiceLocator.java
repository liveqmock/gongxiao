/**
 * WSWsPurOrderFacadeSrvProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yhglobal.gongxiao.purchase.purchase;

public class WSWsPurOrderFacadeSrvProxyServiceLocator extends org.apache.axis.client.Service implements com.yhglobal.gongxiao.purchase.purchase.WSWsPurOrderFacadeSrvProxyService {

    public WSWsPurOrderFacadeSrvProxyServiceLocator() {
    }


    public WSWsPurOrderFacadeSrvProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSWsPurOrderFacadeSrvProxyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSWsPurOrderFacade
    private java.lang.String WSWsPurOrderFacade_address = "http://192.168.100.67:6888/ormrpc/services/WSWsPurOrderFacade";

    public java.lang.String getWSWsPurOrderFacadeAddress() {
        return WSWsPurOrderFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSWsPurOrderFacadeWSDDServiceName = "WSWsPurOrderFacade";

    public java.lang.String getWSWsPurOrderFacadeWSDDServiceName() {
        return WSWsPurOrderFacadeWSDDServiceName;
    }

    public void setWSWsPurOrderFacadeWSDDServiceName(java.lang.String name) {
        WSWsPurOrderFacadeWSDDServiceName = name;
    }

    public com.yhglobal.gongxiao.purchase.purchase.WSWsPurOrderFacadeSrvProxy getWSWsPurOrderFacade() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSWsPurOrderFacade_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSWsPurOrderFacade(endpoint);
    }

    public com.yhglobal.gongxiao.purchase.purchase.WSWsPurOrderFacadeSrvProxy getWSWsPurOrderFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.yhglobal.gongxiao.purchase.purchase.WSWsPurOrderFacadeSoapBindingStub _stub = new com.yhglobal.gongxiao.purchase.purchase.WSWsPurOrderFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSWsPurOrderFacadeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSWsPurOrderFacadeEndpointAddress(java.lang.String address) {
        WSWsPurOrderFacade_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.yhglobal.gongxiao.purchase.purchase.WSWsPurOrderFacadeSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                com.yhglobal.gongxiao.purchase.purchase.WSWsPurOrderFacadeSoapBindingStub _stub = new com.yhglobal.gongxiao.purchase.purchase.WSWsPurOrderFacadeSoapBindingStub(new java.net.URL(WSWsPurOrderFacade_address), this);
                _stub.setPortName(getWSWsPurOrderFacadeWSDDServiceName());
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
        if ("WSWsPurOrderFacade".equals(inputPortName)) {
            return getWSWsPurOrderFacade();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://192.168.100.67:6888/ormrpc/services/WSWsPurOrderFacade", "WSWsPurOrderFacadeSrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://192.168.100.67:6888/ormrpc/services/WSWsPurOrderFacade", "WSWsPurOrderFacade"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSWsPurOrderFacade".equals(portName)) {
            setWSWsPurOrderFacadeEndpointAddress(address);
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
