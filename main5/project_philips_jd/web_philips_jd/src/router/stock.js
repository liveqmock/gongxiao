// 库存
// 货品库存查询
import GoodsInventoryQuery from '../components/InventoryCenter/GoodsInventoryQuery'
// 仓库库存查询
import WarehouseInventoryQuery from '../components/InventoryCenter/WarehouseInventoryQuery'
// 进销存台账
import EntryAndDepositAccount from '../components/InventoryCenter/EntryAndDepositAccount'
// 出入库流水台账
import OutOfWarehouseLedger from '../components/InventoryCenter/OutOfWarehouseLedger'
// 批次库存报表
import BatchInventoryReport from '../components/InventoryCenter/BatchInventoryReport'
// 库存核对
import InventoryCheck from '../components/InventoryCenter/InventoryCheck'

const stock = [
  {path: '/GoodsInventoryQuery', component: GoodsInventoryQuery, meta: {title: '货品库存查询'}},
  {path: '/WarehouseInventoryQuery', component: WarehouseInventoryQuery, meta: {title: '仓库库存查询'}},
  {path: '/EntryAndDepositAccount', component: EntryAndDepositAccount, meta: {title: '进销存台账'}},
  {path: '/OutOfWarehouseLedger', component: OutOfWarehouseLedger, meta: {title: '出入库流水台账'}},
  {path: '/BatchInventoryReport', component: BatchInventoryReport, meta: {title: '批次库存报表'}},
  {path: '/InventoryCheck', component: InventoryCheck, meta: {title: '库存核对'}}
];

export default stock;
