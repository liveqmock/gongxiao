/**
 * WSWMSOtherIssueBillFacadeSrvProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yhglobal.gongxiao.other.outware;

public class WSWMSOtherIssueBillFacadeSrvProxyServiceLocator extends org.apache.axis.client.Service implements com.yhglobal.gongxiao.other.outware.WSWMSOtherIssueBillFacadeSrvProxyService {

    public WSWMSOtherIssueBillFacadeSrvProxyServiceLocator() {
    }


    public WSWMSOtherIssueBillFacadeSrvProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSWMSOtherIssueBillFacadeSrvProxyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSWMSOtherIssueBillFacade
    private java.lang.String WSWMSOtherIssueBillFacade_address = "http://192.168.100.67:6888/ormrpc/services/WSWMSOtherIssueBillFacade";

    public java.lang.String getWSWMSOtherIssueBillFacadeAddress() {
        return WSWMSOtherIssueBillFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSWMSOtherIssueBillFacadeWSDDServiceName = "WSWMSOtherIssueBillFacade";

    public java.lang.String getWSWMSOtherIssueBillFacadeWSDDServiceName() {
        return WSWMSOtherIssueBillFacadeWSDDServiceName;
    }

    public void setWSWMSOtherIssueBillFacadeWSDDServiceName(java.lang.String name) {
        WSWMSOtherIssueBillFacadeWSDDServiceName = name;
    }

    public com.yhglobal.gongxiao.other.outware.WSWMSOtherIssueBillFacadeSrvProxy getWSWMSOtherIssueBillFacade() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSWMSOtherIssueBillFacade_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSWMSOtherIssueBillFacade(endpoint);
    }

    public com.yhglobal.gongxiao.other.outware.WSWMSOtherIssueBillFacadeSrvProxy getWSWMSOtherIssueBillFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.yhglobal.gongxiao.other.outware.WSWMSOtherIssueBillFacadeSoapBindingStub _stub = new com.yhglobal.gongxiao.other.outware.WSWMSOtherIssueBillFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSWMSOtherIssueBillFacadeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSWMSOtherIssueBillFacadeEndpointAddress(java.lang.String address) {
        WSWMSOtherIssueBillFacade_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.yhglobal.gongxiao.other.outware.WSWMSOtherIssueBillFacadeSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                com.yhglobal.gongxiao.other.outware.WSWMSOtherIssueBillFacadeSoapBindingStub _stub = new com.yhglobal.gongxiao.other.outware.WSWMSOtherIssueBillFacadeSoapBindingStub(new java.net.URL(WSWMSOtherIssueBillFacade_address), this);
                _stub.setPortName(getWSWMSOtherIssueBillFacadeWSDDServiceName());
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
        if ("WSWMSOtherIssueBillFacade".equals(inputPortName)) {
            return getWSWMSOtherIssueBillFacade();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://192.168.100.67:6888/ormrpc/services/WSWMSOtherIssueBillFacade", "WSWMSOtherIssueBillFacadeSrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://192.168.100.67:6888/ormrpc/services/WSWMSOtherIssueBillFacade", "WSWMSOtherIssueBillFacade"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSWMSOtherIssueBillFacade".equals(portName)) {
            setWSWMSOtherIssueBillFacadeEndpointAddress(address);
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
