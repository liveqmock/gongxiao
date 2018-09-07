package model;

import java.util.Date;

/**
 * 供应商表 模型
 */
public class Tsupplier {
    private Long supplierid;

    private String suppliername;

    private String suppliercode;

    private Byte status;

    private String address;

    private String email;

    private String contactname;

    private String countrycode;

    private String phonenumber;

    private Long createdbyid;

    private String createdbyname;

    private Date createtime;

    private Date lastupdatetime;

    private String tracelog;

    public Long getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(Long supplierid) {
        this.supplierid = supplierid;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername == null ? null : suppliername.trim();
    }

    public String getSuppliercode() {
        return suppliercode;
    }

    public void setSuppliercode(String suppliercode) {
        this.suppliercode = suppliercode == null ? null : suppliercode.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname == null ? null : contactname.trim();
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode == null ? null : countrycode.trim();
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber == null ? null : phonenumber.trim();
    }

    public Long getCreatedbyid() {
        return createdbyid;
    }

    public void setCreatedbyid(Long createdbyid) {
        this.createdbyid = createdbyid;
    }

    public String getCreatedbyname() {
        return createdbyname;
    }

    public void setCreatedbyname(String createdbyname) {
        this.createdbyname = createdbyname == null ? null : createdbyname.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(Date lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog == null ? null : tracelog.trim();
    }
}