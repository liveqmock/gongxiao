/**
 * WSImportSaleIssueFacadeSrvProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yhglobal.gongxiao.sale.out;

public class WSImportSaleIssueFacadeSrvProxyServiceLocator extends org.apache.axis.client.Service implements com.yhglobal.gongxiao.sale.out.WSImportSaleIssueFacadeSrvProxyService {

    public WSImportSaleIssueFacadeSrvProxyServiceLocator() {
    }


    public WSImportSaleIssueFacadeSrvProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSImportSaleIssueFacadeSrvProxyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSImportSaleIssueFacade
    private java.lang.String WSImportSaleIssueFacade_address = "http://192.168.100.67:6888/ormrpc/services/WSImportSaleIssueFacade";

    public java.lang.String getWSImportSaleIssueFacadeAddress() {
        return WSImportSaleIssueFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSImportSaleIssueFacadeWSDDServiceName = "WSImportSaleIssueFacade";

    public java.lang.String getWSImportSaleIssueFacadeWSDDServiceName() {
        return WSImportSaleIssueFacadeWSDDServiceName;
    }

    public void setWSImportSaleIssueFacadeWSDDServiceName(java.lang.String name) {
        WSImportSaleIssueFacadeWSDDServiceName = name;
    }

    public com.yhglobal.gongxiao.sale.out.WSImportSaleIssueFacadeSrvProxy getWSImportSaleIssueFacade() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSImportSaleIssueFacade_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSImportSaleIssueFacade(endpoint);
    }

    public com.yhglobal.gongxiao.sale.out.WSImportSaleIssueFacadeSrvProxy getWSImportSaleIssueFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.yhglobal.gongxiao.sale.out.WSImportSaleIssueFacadeSoapBindingStub _stub = new com.yhglobal.gongxiao.sale.out.WSImportSaleIssueFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSImportSaleIssueFacadeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSImportSaleIssueFacadeEndpointAddress(java.lang.String address) {
        WSImportSaleIssueFacade_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.yhglobal.gongxiao.sale.out.WSImportSaleIssueFacadeSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                com.yhglobal.gongxiao.sale.out.WSImportSaleIssueFacadeSoapBindingStub _stub = new com.yhglobal.gongxiao.sale.out.WSImportSaleIssueFacadeSoapBindingStub(new java.net.URL(WSImportSaleIssueFacade_address), this);
                _stub.setPortName(getWSImportSaleIssueFacadeWSDDServiceName());
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
        if ("WSImportSaleIssueFacade".equals(inputPortName)) {
            return getWSImportSaleIssueFacade();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://192.168.100.67:6888/ormrpc/services/WSImportSaleIssueFacade", "WSImportSaleIssueFacadeSrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://192.168.100.67:6888/ormrpc/services/WSImportSaleIssueFacade", "WSImportSaleIssueFacade"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSImportSaleIssueFacade".equals(portName)) {
            setWSImportSaleIssueFacadeEndpointAddress(address);
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
