// 项目管理
import ProjectManagement from '../components/BasicDataManagement/ProjectManagement'
import ProjectInfo from '../components/BasicDataManagement/ProjectInfo'
//
import WarehouseManagement from '../components/BasicDataManagement/WarehouseManagement' //列表页面
import NewWarehouse from '../components/BasicDataManagement/NewWarehouse'                //新增页面
import EditorWarehouse from '../components/BasicDataManagement/EditorWarehouse'         //编辑页面


const project = [
  {path: '/ProjectManagement', component: ProjectManagement, meta: {title: '项目管理'}},
  {path: '/ProjectInfo/:orderNumber', component: ProjectInfo, meta: {title: '项目详情'}},
  {path: '/WarehouseManagement', component: WarehouseManagement, meta: {title: '仓库管理'}},
  {path: '/NewWarehouse', component: NewWarehouse, meta: {title: '新增仓库'}},
  {path: '/EditorWarehouse', component: EditorWarehouse, meta: {title: '编辑仓库'}},
  {path: '/JDWarehouseAddressMaintenance', component: JDWarehouseAddressMaintenance, meta: {title: '京东仓库地址维护'}},
  {path: '/CustomerInfoAudited', component: CustomerInfoAudited, meta: {title: '客户信息(已审核)'}},
  {path: '/NewCustomersAudited', component: NewCustomersAudited, meta: {title: '客户新增(已审核)'}},
  {path: '/EditCustomersAudited', component: EditCustomersAudited, meta: {title: '客户编辑(已审核)'}},
  {path: '/InfoCustomersAudited', component: InfoCustomersAudited, meta: {title: '客户编辑(已审核)'}},
  {path: '/CollectGoodsAddress', component: CollectGoodsAddress, meta: {title: '收货地址(已审核)'}},
  {path: '/CollectionAddress', component: CollectionAddress, meta: {title: '收件地址(已审核)'}},
  {path: '/CustomerReviewMaintain', component: CustomerReviewMaintain, meta: {title: '客户审核(维护)'}},
  {path: '/CustomerAddressApproval', component: CustomerAddressApproval, meta: {title: '客户地址审批'}},
];

export default project
