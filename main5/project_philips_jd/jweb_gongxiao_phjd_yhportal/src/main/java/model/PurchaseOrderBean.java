package model;

/**
 * jd采购订单
 * @author yuping.lin
 */
public class PurchaseOrderBean {
    private int projectId;   //项目ID
    private String oddNumbers;  //单号
    private String documentType; //单据类型
    private String fileName;   //文件名称
    private String createTime;  //创建时间
    private String projectName;  //项目名称
    private String ImportState;  //导入状态
    private String describe;   //描述

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getOddNumbers() {
        return oddNumbers;
    }

    public void setOddNumbers(String oddNumbers) {
        this.oddNumbers = oddNumbers;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getImportState() {
        return ImportState;
    }

    public void setImportState(String importState) {
        ImportState = importState;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
