const SIDEBARMENU = {
  // 采购
  "purchase":[
    {"menuPath":"/PurchaseManagement","menuName":"采购管理"},
    {"menuPath":"/PurchaseEdit","menuName":"采购编辑"},
  ],
  // 销售
  "sales":[
    {"menuPath":"/SalesManagement","menuName":"订单管理"},
  ],
  // 账户管理
  "account":[
    {"menuPath":"/SupplierFrozenAccount","menuName":"供应商冻结账户管理"},
    {"menuPath":"/SupplierReservedAccount","menuName":"供应商预留账户"},
    {"menuPath":"/SupplierTransferAccount","menuName":"供应商上账账户"},
    {"menuPath":"/YhGlobalAccountDetail","menuName":"越海账户明细"},
  ],
  // 仓储
  "storage":[
    {"menuPath":"/ManagementOfInformingNotice","menuName":"入库通知单管理"},
    {"menuPath":"/ManagementOfOutgoingNoticeSheet","menuName":"出库通知单管理"},
    {"menuPath":"/WarehouseReceiptAdministration","menuName":"入库单管理"},
    {"menuPath":"/OutboundOrderAdministration","menuName":"出库单管理"},
    {"menuPath":"/AllocationManagement","menuName":"调拨单管理"},
  ],
  // 库存
  "stock":[
    {"menuPath": '/GoodsInventoryQuery',"menuName": '货品库存查询'},
    {"menuPath": '/WarehouseInventoryQuery',"menuName": '仓库库存查询'},
    {"menuPath": '/EntryAndDepositAccount', "menuName": '进销存台账'},
    {"menuPath": '/OutOfWarehouseLedger',"menuName": '出入库流水台账'},
    {"menuPath": '/InventoryCheck',"menuName": '库存核对'},
    {"menuPath": '/BatchInventoryReport',"menuName": '批次库存报表'}
  ],
  // 收付款管理
  "pay":[

  ],
  // EDI中心
  "EDI":[
    {"menuPath": '/JDPurchaseOrderImportQuery',"menuName": '采购订单导入查询'},
    {"menuPath": '/JDDeliveryConfirmationListImportQuery',"menuName": '发货确认导入查询'},
    {"menuPath": '/ReturnsManagement',"menuName": '退货单导入管理'},
    {"menuPath": '/StatementOfDetails',"menuName": '结算单导入管理'}
  ],

  // 基础数据
  "basicData":[
    {"menuPath": '/ProjectManagement',"menuName": '项目管理'},
    {"menuPath": '/WarehouseManagement',"menuName": '仓库管理'},
    {"menuPath": '/JDWarehouseAddressMaintenance',"menuName": '仓库地址维护'},
    {"menuPath": '/CustomerInfoAudited',"menuName": '客户信息'},
    {"menuPath": '/CustomerReviewMaintain',"menuName": '客户审核'},
    {"menuPath": '/CustomerAddressApproval',"menuName": '客户地址审批'}
  ]
};


export default{
	SIDEBARMENU
}
