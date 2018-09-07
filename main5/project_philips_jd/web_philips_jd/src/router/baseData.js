// 项目管理
import ProjectManagement from '../components/BasicDataManagement/ProjectManagement'
import ProjectInfo from '../components/BasicDataManagement/ProjectInfo'
// 仓库管理
import WarehouseManagement from '../components/BasicDataManagement/WarehouseManagement' //列表页面
import NewWarehouse from '../components/BasicDataManagement/NewWarehouse'                //新增页面
import EditorWarehouse from '../components/BasicDataManagement/EditorWarehouse'         //编辑页面
// 京东仓库地址维护
import JDWarehouseAddressMaintenance from '../components/BasicDataManagement/JDWarehouseAddressMaintenance'
// 客户信息
import CustomerInfoAudited from '../components/BasicDataManagement/CustomerInfoAudited'   //已审核客户信息
import NewCustomersAudited from '../components/BasicDataManagement/NewCustomersAudited'   //已审核客户新增
import EditCustomersAudited from '../components/BasicDataManagement/EditCustomersAudited' //已审核客户编辑
import InfoCustomersAudited from '../components/BasicDataManagement/InfoCustomersAudited' //已审核客户详情
import CollectGoodsAddress from '../components/BasicDataManagement/CollectGoodsAddress' //收货地址(已审核)
import CollectionAddress from '../components/BasicDataManagement/CollectionAddress' //收件地址(已审核)
import CustomerReviewMaintain from '../components/BasicDataManagement/CustomerReviewMaintain' //客户审核(维护)
import CustomerAddressApproval from '../components/BasicDataManagement/CustomerAddressApproval' //客户地址审批

const basicData = [
  {path: '/ProjectManagement', component: ProjectManagement, meta: {title: '项目管理'}},
  // {path: '/ProjectInfo/:orderNumber', component: ProjectInfo, meta: {title: '项目详情'}},
  {path: '/ProjectInfo', component: ProjectInfo, meta: {title: '项目详情'}},
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

export default basicData
