// 渠道管理
import ChannelManagement from '../components/Channel/ChannelManagement'
import CommodityPriceStrategy from '../components/Channel/CommodityPriceStrategy'
import PriceEditor from '../components/Channel/PriceEditor'
import CustomerMaintenance from '../components/Channel/CustomerMaintenance'
import CustomerEditor from '../components/Channel/CustomerEditor'
import CustomerDetails from '../components/Channel/CustomerDetails'
import AddressOfGoodsReceived from '../components/Channel/AddressOfGoodsReceived'
import ReceivingAddress from '../components/Channel/ReceivingAddress'

const Channel = [
  {path: '/ChannelManagement', component: ChannelManagement, meta: {title: '渠道管理'}},
  {path: '/CommodityPriceStrategy', component: CommodityPriceStrategy, meta: {title: '货品价格策略'}},
  {path: '/PriceEditor', component: PriceEditor, meta: {title: '货品价格编辑'}},
  {path: '/CustomerMaintenance', component: CustomerMaintenance, meta: {title: '客户维护'}},
  {path: '/CustomerEditor', component: CustomerEditor, meta: {title: '客户编辑'}},
  {path: '/CustomerDetails', component: CustomerDetails, meta: {title: '客户详情'}},
  {path: '/AddressOfGoodsReceived', component: AddressOfGoodsReceived, meta: {title: '收货地址'}},
  {path: '/ReceivingAddress', component: ReceivingAddress, meta: {title: '收件地址'}}
];

export default Channel
