// 销售
import SalesManagement from '../components/sales/SalesManagement'
import JDOrdersReturnAudit from '../components/sales/JDOrdersReturnAudit'
import OrderDetails from '../components/sales/OrderDetails'
import NoticeOfDelivery from '../components/sales/NoticeOfDelivery'

import SalesReturnsManagement from '../components/sales/SalesReturnsManagement'
import SalesReturnOrderEditor from '../components/sales/SalesReturnOrderEditor'
import SalesReturnsDetails from '../components/sales/SalesReturnsDetails'
import RejectionOfReturnDetails from '../components/sales/RejectionOfReturnDetails'

import AfterSaleReturnManagement from '../components/sales/AfterSaleReturnManagement'
import PostSalesOrderEditor from '../components/sales/PostSalesOrderEditor'
import AfterSalesOrderEditorPrice from '../components/sales/AfterSalesOrderEditorPrice'
import AfterSalesOrderEditorException from '../components/sales/AfterSalesOrderEditorException'
import DetailsOfAfterSaleReturns from '../components/sales/DetailsOfAfterSaleReturns'

import PurchaseReplacementEditorIncluding from '../components/sales/PurchaseReplacementEditorIncluding'
import PurchaseReplacementEditorExcluding from '../components/sales/PurchaseReplacementEditorExcluding'

const sales = [
  {path: '/SalesManagement', component: SalesManagement, meta: {title: '订单管理'}},
  {path: '/JDOrdersReturnAudit', component: JDOrdersReturnAudit, meta: {title: '京东订单回告审核(JD)'}},
  {path: '/OrderDetails', component: OrderDetails, meta: {title: '订单详情'}},
  {path: '/NoticeOfDelivery', component: NoticeOfDelivery, meta: {title: '发货通知'}},

  {path: '/SalesReturnsManagement', component: SalesReturnsManagement, meta: {title: '销售退换货管理(JD)'}},
  {path: '/SalesReturnOrderEditor', component: SalesReturnOrderEditor, meta: {title: '销售退货单编辑'}},
  {path: '/SalesReturnsDetails', component: SalesReturnsDetails, meta: {title: '销售退货单详情'}},
  {path: '/RejectionOfReturnDetails', component: RejectionOfReturnDetails, meta: {title: '拒收退货单详情'}},

  {path: '/AfterSaleReturnManagement', component: AfterSaleReturnManagement, meta: {title: '售后退货管理'}},
  {path: '/PostSalesOrderEditor', component: PostSalesOrderEditor, meta: {title: '售后退货单编辑(可匹配批次)'}},
  {path: '/AfterSalesOrderEditorPrice', component: AfterSalesOrderEditorPrice, meta: {title: '售后退货单编辑(价格异常)'}},
  {path: '/AfterSalesOrderEditorException', component: AfterSalesOrderEditorException, meta: {title: '售后退货单编辑(型号异常)'}},
  {path: '/DetailsOfAfterSaleReturns', component: DetailsOfAfterSaleReturns, meta: {title: '售后退货单详情'}},

  {path: '/PurchaseReplacementEditorIncluding', component: PurchaseReplacementEditorIncluding, meta: {title: '采购退换货编辑(含批次)'}},
  {path: '/PurchaseReplacementEditorExcluding', component: PurchaseReplacementEditorExcluding, meta: {title: '采购退换货编辑(不含批次)'}}
];

export default sales



