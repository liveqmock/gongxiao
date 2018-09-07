/**
 * WSXPSMoveIssueBillFacadeSrvProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yhglobal.gongxiao.diaobo.outboundorder;

public class WSXPSMoveIssueBillFacadeSrvProxyServiceLocator extends org.apache.axis.client.Service implements com.yhglobal.gongxiao.diaobo.outboundorder.WSXPSMoveIssueBillFacadeSrvProxyService {

    public WSXPSMoveIssueBillFacadeSrvProxyServiceLocator() {
    }


    public WSXPSMoveIssueBillFacadeSrvProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSXPSMoveIssueBillFacadeSrvProxyServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSXPSMoveIssueBillFacade
    private String WSXPSMoveIssueBillFacade_address = "http://192.168.100.188:6888/ormrpc/services/WSXPSMoveIssueBillFacade";

    public String getWSXPSMoveIssueBillFacadeAddress() {
        return WSXPSMoveIssueBillFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private String WSXPSMoveIssueBillFacadeWSDDServiceName = "WSXPSMoveIssueBillFacade";

    public String getWSXPSMoveIssueBillFacadeWSDDServiceName() {
        return WSXPSMoveIssueBillFacadeWSDDServiceName;
    }

    public void setWSXPSMoveIssueBillFacadeWSDDServiceName(String name) {
        WSXPSMoveIssueBillFacadeWSDDServiceName = name;
    }

    public com.yhglobal.gongxiao.diaobo.outboundorder.WSXPSMoveIssueBillFacadeSrvProxy getWSXPSMoveIssueBillFacade() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSXPSMoveIssueBillFacade_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSXPSMoveIssueBillFacade(endpoint);
    }

    public com.yhglobal.gongxiao.diaobo.outboundorder.WSXPSMoveIssueBillFacadeSrvProxy getWSXPSMoveIssueBillFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.yhglobal.gongxiao.diaobo.outboundorder.WSXPSMoveIssueBillFacadeSoapBindingStub _stub = new com.yhglobal.gongxiao.diaobo.outboundorder.WSXPSMoveIssueBillFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSXPSMoveIssueBillFacadeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSXPSMoveIssueBillFacadeEndpointAddress(String address) {
        WSXPSMoveIssueBillFacade_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.yhglobal.gongxiao.diaobo.outboundorder.WSXPSMoveIssueBillFacadeSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                com.yhglobal.gongxiao.diaobo.outboundorder.WSXPSMoveIssueBillFacadeSoapBindingStub _stub = new com.yhglobal.gongxiao.diaobo.outboundorder.WSXPSMoveIssueBillFacadeSoapBindingStub(new java.net.URL(WSXPSMoveIssueBillFacade_address), this);
                _stub.setPortName(getWSXPSMoveIssueBillFacadeWSDDServiceName());
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
        if ("WSXPSMoveIssueBillFacade".equals(inputPortName)) {
            return getWSXPSMoveIssueBillFacade();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://192.168.100.188:6888/ormrpc/services/WSXPSMoveIssueBillFacade", "WSXPSMoveIssueBillFacadeSrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://192.168.100.188:6888/ormrpc/services/WSXPSMoveIssueBillFacade", "WSXPSMoveIssueBillFacade"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("WSXPSMoveIssueBillFacade".equals(portName)) {
            setWSXPSMoveIssueBillFacadeEndpointAddress(address);
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
