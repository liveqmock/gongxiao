// 仓储
//1.1 入库通知单管理
import ManagementOfInformingNotice from '../components/WarehouseManagement/ManagementOfInformingNotices/ManagementOfInformingNotice'
//1.2 入库通知单详情
import DetailsOfTheEntryNotice from '../components/WarehouseManagement/ManagementOfInformingNotices/DetailsOfTheEntryNotice'
//1.3 新增入库通知单
import NewEntryNotice from '../components/WarehouseManagement/ManagementOfInformingNotices/NewEntryNotice'
//1.4 入库通知单修改
import UpdateEntryNotice from '../components/WarehouseManagement/ManagementOfInformingNotices/UpdateEntryNotice'
//1.5 采购单跳转入库通知单
import PurchaseOrderSlip from '../components/WarehouseManagement/ManagementOfInformingNotices/PurchaseOrderSlip'
//2.1 出库通知单管理
import ManagementOfOutgoingNoticeSheet from '../components/WarehouseManagement/ManagementOfOutgoingNoticeSheets/ManagementOfOutgoingNoticeSheet'
//2.2 出库通知单详情
import DetailsOfTheAnnouncement from '../components/WarehouseManagement/ManagementOfOutgoingNoticeSheets/DetailsOfTheAnnouncement'
//2.3 新增出库通知单
import NewAnnouncementList from '../components/WarehouseManagement/ManagementOfOutgoingNoticeSheets/NewAnnouncementList'
//2.4 出库通知单修改
import UpdateAnnouncementList from '../components/WarehouseManagement/ManagementOfOutgoingNoticeSheets/UpdateAnnouncementList'
//2.5 订单跳转出库通知单
import OrderJumpOutOfTheWarehouseNotice from '../components/WarehouseManagement/ManagementOfOutgoingNoticeSheets/OrderJumpOutOfTheWarehouseNotice'
//3.1 入库单管理
import WarehouseReceiptAdministration from '../components/WarehouseManagement/WarehouseReceiptAdministrations/WarehouseReceiptAdministration'
//3.2 入库单详情
import DetailsOfAWarehouseReceipt from '../components/WarehouseManagement/WarehouseReceiptAdministrations/DetailsOfAWarehouseReceipt'
//4.1 出库单管理
import OutboundOrderAdministration from '../components/WarehouseManagement/OutboundOrderAdministrations/OutboundOrderAdministration'
//4.2 出库单详情
import DetailsOfTheTreasuryBill from '../components/WarehouseManagement/OutboundOrderAdministrations/DetailsOfTheTreasuryBill'
//5.1 调拨单管理
import AllocationManagement from '../components/WarehouseManagement/AllocationManagements/AllocationManagement'
//5.2 新增调拨单
import NewAllocationList from '../components/WarehouseManagement/AllocationManagements/NewAllocationList'
//5.3 调拨单详情
import DetailsOfATransferList from '../components/WarehouseManagement/AllocationManagements/DetailsOfATransferList'


const storage = [
  // 仓库管理
  // 入库通知单管理
  {path: '/ManagementOfInformingNotice', component: ManagementOfInformingNotice, meta: {title: '入库通知单管理'}},
  {path: '/DetailsOfTheEntryNotice', component: DetailsOfTheEntryNotice, meta: {title: '入库通知单详情'}},
  {path: '/NewEntryNotice', component: NewEntryNotice, meta: {title: '新增入库通知单'}},
  {path: '/UpdateEntryNotice', component: UpdateEntryNotice, meta: {title: '入库通知单修改'}},
  {path: '/PurchaseOrderSlip', component: PurchaseOrderSlip, meta: {title: '采购单跳转入库通知单'}},
  // 出库通知单管理
  {path: '/ManagementOfOutgoingNoticeSheet', component: ManagementOfOutgoingNoticeSheet, meta: {title: '出库通知单管理'}},
  {path: '/DetailsOfTheAnnouncement', component: DetailsOfTheAnnouncement, meta: {title: '出库通知单详情'}},
  {path: '/NewAnnouncementList', component: NewAnnouncementList, meta: {title: '新增出库通知单'}},
  {path: '/UpdateAnnouncementList', component: UpdateAnnouncementList, meta: {title: '出库通知单修改'}},
  {path: '/OrderJumpOutOfTheWarehouseNotice', component: OrderJumpOutOfTheWarehouseNotice, meta: {title: '订单跳转出库通知单'}},
  // 入库单管理
  {path: '/WarehouseReceiptAdministration', component: WarehouseReceiptAdministration, meta: {title: '入库单管理'}},
  {path: '/DetailsOfAWarehouseReceipt', component: DetailsOfAWarehouseReceipt, meta: {title: '入库单详情'}},
  // 出库单管理
  {path: '/OutboundOrderAdministration', component: OutboundOrderAdministration, meta: {title: '出库单管理'}},
  {path: '/DetailsOfTheTreasuryBill', component: DetailsOfTheTreasuryBill, meta: {title: '出库单详情'}},
  // 调拨单管理
  {path: '/AllocationManagement', component: AllocationManagement, meta: {title: '调拨单管理'}},
  {path: '/NewAllocationList', component: NewAllocationList, meta: {title: '新增调拨单'}},
  {path: '/DetailsOfATransferList', component: DetailsOfATransferList, meta: {title: '调拨单详情'}}
];

export default storage
