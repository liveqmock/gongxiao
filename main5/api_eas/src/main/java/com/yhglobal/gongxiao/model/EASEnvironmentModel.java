package com.yhglobal.gongxiao.model;

/**
 * EAS 环境模型
 *
 * @author: 陈浩
 **/
public class EASEnvironmentModel {
    private String url ;
    private String user ;
    private String pwd ;
    private String slame ;
    private String dcname ;
    private String language ;
    private String dbtype ;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSlame() {
        return slame;
    }

    public void setSlame(String slame) {
        this.slame = slame;
    }

    public String getDcname() {
        return dcname;
    }

    public void setDcname(String dcname) {
        this.dcname = dcname;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    @Override
    public String toString() {
        return "EASEnvironmentModel{" +
                "url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", pwd='" + pwd + '\'' +
                ", slame='" + slame + '\'' +
                ", dcname='" + dcname + '\'' +
                ", language='" + language + '\'' +
                ", dbtype='" + dbtype + '\'' +
                '}';
    }
}
