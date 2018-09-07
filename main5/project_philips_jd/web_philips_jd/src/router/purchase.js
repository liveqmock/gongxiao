// 采购
import PurchaseManagement from '../components/purchase/PurchaseManagement'
import PurchasePlanManagement from '../components/purchase/PurchasePlanManagement'
import NewPurchasePlan from '../components/purchase/NewPurchasePlan'
import DetailsOfPurchasePlanBeforeClosing from '../components/purchase/DetailsOfPurchasePlanBeforeClosing'
import DetailsAfterClosingOfPurchasePlan from '../components/purchase/DetailsAfterClosingOfPurchasePlan'
import ProcurementPlanWindControlAudit from '../components/purchase/ProcurementPlanWindControlAudit'
import PurchasePlanDetailsForApproval from '../components/purchase/PurchasePlanDetailsForApproval'
import PurchasePaymentApplicationInquiry from '../components/purchase/PurchasePaymentApplicationInquiry'
import PurchaseOrderManagement from '../components/purchase/PurchaseOrderManagement'
import OrderImportEditor from '../components/purchase/OrderImportEditor'
import NewPurchaseOrder from '../components/purchase/NewPurchaseOrder'
import PurchaseOrderDetails from '../components/purchase/PurchaseOrderDetails'
import NoticeOfReceiptOfGoods from '../components/purchase/NoticeOfReceiptOfGoods'

const purchase = [
  {path: '/PurchaseManagement', component: PurchaseManagement, meta: {title: '采购管理'}},

  {path: '/PurchasePlanManagement', component: PurchasePlanManagement, meta: {title: '采购计划单管理'}},
  {path: '/NewPurchasePlan', component: NewPurchasePlan, meta: {title: '新增采购计划单'}},
  {path: '/DetailsOfPurchasePlanBeforeClosing', component: DetailsOfPurchasePlanBeforeClosing, meta: {title: '采购计划单完结前详情'}},
  {path: '/DetailsAfterClosingOfPurchasePlan', component: DetailsAfterClosingOfPurchasePlan, meta: {title: '采购计划单完结后详情'}},
  {path: '/ProcurementPlanWindControlAudit', component: ProcurementPlanWindControlAudit, meta: {title: '采购计划单风控审核'}},
  {path: '/PurchasePlanDetailsForApproval', component: PurchasePlanDetailsForApproval, meta: {title: '采购计划单详情审批'}},
  {path: '/PurchasePaymentApplicationInquiry', component: PurchasePaymentApplicationInquiry, meta: {title: '采购付款申请单查询'}},
  {path: '/PurchaseOrderManagement', component: PurchaseOrderManagement, meta: {title: '采购订单管理'}},
  {path: '/OrderImportEditor', component: OrderImportEditor, meta: {title: '订单导入编辑'}},
  {path: '/NewPurchaseOrder', component: NewPurchaseOrder, meta: {title: '新增采购订单'}},
  {path: '/PurchaseOrderDetails', component: PurchaseOrderDetails, meta: {title: '采购订单详情'}},
  {path: '/NoticeOfReceiptOfGoods', component: NoticeOfReceiptOfGoods, meta: {title: '收货通知'}}

];

export default purchase
