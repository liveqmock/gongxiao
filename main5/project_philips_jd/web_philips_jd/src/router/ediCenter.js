// EDI中心
import JDPurchaseOrderImportQuery from '../components/EDICenter/JDPurchaseOrderImportQuery'
import JDDeliveryConfirmationListImportQuery from '../components/EDICenter/JDDeliveryConfirmationListImportQuery'
import ReturnsManagement from '../components/EDICenter/ReturnsManagement'
import JDSettlementManagement from '../components/EDICenter/JDSettlementManagement'
import StatementOfDetails from '../components/EDICenter/StatementOfDetails'

const EDICenter = [
  {path: '/JDPurchaseOrderImportQuery', component: JDPurchaseOrderImportQuery, meta: {title: '京东采购订单导入查询'}},
  {path: '/JDDeliveryConfirmationListImportQuery', component: JDDeliveryConfirmationListImportQuery, meta: {title: '京东发货确认单导入查询'}},
  {path: '/ReturnsManagement', component: ReturnsManagement, meta: {title: '退货单导入管理'}},
  {path: '/JDSettlementManagement', component: JDSettlementManagement, meta: {title: '京东结算单导入管理'}},
  {path: '/StatementOfDetails', component: StatementOfDetails, meta: {title: '结算单明细'}}
];

export default EDICenter
