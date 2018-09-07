package com.yhglobal.gongxiao.edi.entity;

import com.yhglobal.gongxiao.edi.entity.po.PurchaseOrder;
import com.yhglobal.gongxiao.edi.entity.ro.MainWarehouseReturnOrder;
import com.yhglobal.gongxiao.edi.entity.ro.SpareWarehouseReturnOrder;
import com.yhglobal.gongxiao.edi.entity.sc.OutBoundOrder;

import java.util.HashSet;
import java.util.Set;

/**
 * edi下载文件枚举
 *
 * @author 葛灿
 */
public enum EdiDownloadEnum {


    PO("po", "po", PurchaseOrder.class),
    SC("sc", "sc", OutBoundOrder.class),
    RO("ro", "ro", MainWarehouseReturnOrder.class),
    ROS("ros", "ro", SpareWarehouseReturnOrder.class);

    // 文件类型
    private String fileType;
    // 文件在京东ftp上的类型
    private String jdFileType;
    // class文件
    private Class clazz;

    EdiDownloadEnum(String fileType, String jdFileType, Class clazz) {
        this.fileType = fileType;
        this.jdFileType = jdFileType;
        this.clazz = clazz;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getJdFileType() {
        return jdFileType;
    }

    public void setJdFileType(String jdFileType) {
        this.jdFileType = jdFileType;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public static EdiDownloadEnum getEnumByFileType(String fileType) {
        for (EdiDownloadEnum downloadEnum : EdiDownloadEnum.values()) {
            if (downloadEnum.getFileType().equals(fileType)) {
                return downloadEnum;
            }
        }
        return null;
    }

    public static Set<String> getJdFileTypeSet() {
        HashSet<String> set = new HashSet<>();
        for (EdiDownloadEnum downloadEnum : EdiDownloadEnum.values()) {
            String jdFileType = downloadEnum.getJdFileType();
            set.add(jdFileType);
        }
        return set;
    }
}
