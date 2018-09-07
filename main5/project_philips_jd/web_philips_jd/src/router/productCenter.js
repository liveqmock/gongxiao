// 商品中心
import ProductManagement from '../components/CommodityCenter/ProductManagement'                //货品管理
import ProductAdded from '../components/CommodityCenter/ProductAdded'                           //货品新增
import ProductInfo from '../components/CommodityCenter/ProductInfo'                             //货品详情
import ProductEdit from '../components/CommodityCenter/ProductEdit'                             //货品编辑
import ProductAuditManagement from '../components/CommodityCenter/ProductAuditManagement'      //货品审核管理

const productCenter = [
  {path: '/ProductManagement', component: ProductManagement, meta: {title: '货品管理'}},
  {path: '/ProductAdded', component: ProductAdded, meta: {title: '货品新增'}},
  {path: '/ProductInfo', component: ProductInfo, meta: {title: '货品详情'}},
  {path: '/ProductEdit', component: ProductEdit, meta: {title: '货品编辑'}},
  {path: '/ProductAuditManagement', component: ProductAuditManagement, meta: {title: '货品审核管理'}},
];

export default productCenter
