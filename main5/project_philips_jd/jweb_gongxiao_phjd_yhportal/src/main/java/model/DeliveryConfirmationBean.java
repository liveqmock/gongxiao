package model;

/**
 * 京东发货确认单
 * @author yuping.lin
 */
public class DeliveryConfirmationBean {
    private int projectId;   //项目ID
    private String jdPurchaseOrderNo;   //京东采购单号
    private String businessAttributes;   //业务属性
    private String fileName;    //文件名称
    private String creationTime;   //创建时间
    private String projectName;   //项目名称
    private String importState;   //导入状态
    private String describe;   //描述
    private String orderAttribute;   //订单属性

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getJdPurchaseOrderNo() {
        return jdPurchaseOrderNo;
    }

    public void setJdPurchaseOrderNo(String jdPurchaseOrderNo) {
        this.jdPurchaseOrderNo = jdPurchaseOrderNo;
    }

    public String getBusinessAttributes() {
        return businessAttributes;
    }

    public void setBusinessAttributes(String businessAttributes) {
        this.businessAttributes = businessAttributes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getImportState() {
        return importState;
    }

    public void setImportState(String importState) {
        this.importState = importState;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getOrderAttribute() {
        return orderAttribute;
    }

    public void setOrderAttribute(String orderAttribute) {
        this.orderAttribute = orderAttribute;
    }

}
