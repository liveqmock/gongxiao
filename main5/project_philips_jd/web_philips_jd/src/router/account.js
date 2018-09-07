// 账户管理
import YhGlobalAccountDetail from '../components/account/YhGlobalAccountDetail'
import SupplierFrozenAccount from '../components/account/SupplierFrozenAccount'
import SupplierReservedAccount from '../components/account/SupplierReservedAccount'
import SupplierTransferAccount from '../components/account/SupplierTransferAccount'

const account = [
  {path: '/YhGlobalAccountDetail', component: YhGlobalAccountDetail, meta: {title: 'YH账户管理'}},
  {path: '/SupplierFrozenAccount', component: SupplierFrozenAccount, meta: {title: '供应商冻结账户管理'}},
  {path: '/SupplierReservedAccount', component: SupplierReservedAccount, meta: {title: '供应商预留账户管理'}},
  {path: '/SupplierTransferAccount', component: SupplierTransferAccount, meta: {title: '供应商上账账户管理'}},
  {path: '/YhGlobalAccountDetail', component: YhGlobalAccountDetail, meta: {title: '越海账户明细'}}
];

export default account
