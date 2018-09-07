import purchase from './purchase'
import sales from './sales'
import account from './account'
import baseData from './baseData'
import productCenter from './productCenter'
import stock from './stock'
import storage from './storage'
import EDICenter from "./ediCenter"
import channel from "./channel"

const children = [
  ...purchase,
  ...sales,
  ...account,
  ...baseData,
  ...productCenter,
  ...stock,
  ...storage,
  ...EDICenter,
  ...channel
];

export default children;
