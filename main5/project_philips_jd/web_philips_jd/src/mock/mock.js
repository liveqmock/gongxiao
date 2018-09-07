import Mock from 'mockjs'
import {ProjectList, warehouseList ,supplierList,LoginUsers} from './data/project'

// import express from 'express'
//
// const ROUTER = express.Router();
// var MockRandom=Mock.Random;
// var pageCount=MockRandom.integer(1, 10);
// var haseMore=true;//是否结束分页
// var ids=10000;//自增长id
// var bookListTemplate={};//数据模板

const getProjectList = Mock.mock("http://localhost:9000/getProjectList",'get',() => {
  return {
    "list": ProjectList,
    "returnCode":0,
    "message": null
  }
});

const getWarehouseList = Mock.mock("http://localhost:9000/getWarehouseList",'get',() => {
  return warehouseList
});

const getSupplierList = Mock.mock("http://localhost:9000/getSupplierList",'get',() => {
  return supplierList
});

// ROUTER.get('http://localhost:9000/getPurchaseList',function (req,res,next) {
//   var currPage=parseInt(req.query.currPage || 1);
//   console.log('当前请求page页：'+currPage);
//   ids=currPage * 10000;
//   if(currPage === pageCount){
//     haseMore=false;
//     bookListTemplate={
//       'hasMore': false,
//       'totalPage': pageCount,
//       "list|1-20": [{   // 随机生成1到3个数组元素
//         'purchaseOrderNumber': 'XPS_shaver_PUR2018081317110400',  // 中文名称
//         'supplierOrderNo|+1': 88,    // 属性值自动加 1，初始值为88
//         'contractReferenceOrderNo|18-28': 0,   // 18至28以内随机整数, 0只是用来确定类型
//         'supplierName': '@date("yyyy-MM-dd")',  // 日期
//         'orderAmount': '@city(true)',   // 中国城市
//         'warehouseName': '@color',  // 16进制颜色
//         'purchaseStatus|1': true,  // 布尔值
//         'businessDate|1-2': true,  // true的概率是1/3
//         'purchaseNumber|10-20': 12,  // 从obj对象中随机获取2个属性
//         'createPerson': '@cname',  // 从obj对象中随机获取1至3个属性
//         'brother|1': ['jack', 'jim'], // 随机选取 1 个元素
//         'sister|+1': ['jack', 'jim', 'lily'], // array中顺序选取元素作为结果
//         'friends|2': ['jack', 'jim'] // 重复2次属性值生成一个新数组
//       }],
//     }
//   }else{
//     bookListTemplate={
//       'hasMore': true,
//       'totalPage': pageCount,
//       "list|16": [{   // 随机生成1到3个数组元素
//         'purchaseOrderNumber': 'XPS_shaver_PUR2018081317110400',  // 中文名称
//         'supplierOrderNo|+1': 88,    // 属性值自动加 1，初始值为88
//         'contractReferenceOrderNo|18-28': 0,   // 18至28以内随机整数, 0只是用来确定类型
//         'supplierName': '@date("yyyy-MM-dd")',  // 日期
//         'orderAmount': '@city(true)',   // 中国城市
//         'warehouseName': '@color',  // 16进制颜色
//         'purchaseStatus|1': true,  // 布尔值
//         'businessDate|1-2': true,  // true的概率是1/3
//         'purchaseNumber|10-20': 12,  // 从obj对象中随机获取2个属性
//         'createPerson': '@cname',  // 从obj对象中随机获取1至3个属性
//         'brother|1': ['jack', 'jim'], // 随机选取 1 个元素
//         'sister|+1': ['jack', 'jim', 'lily'], // array中顺序选取元素作为结果
//         'friends|2': ['jack', 'jim'] // 重复2次属性值生成一个新数组
//       }],
//     }
//   }
//   var mockData=Mock.mock(bookListTemplate);
//   res.json({
//     status: true,
//     data: mockData,
//     msg: ''
//   });
// });

const getPurchaseList = Mock.mock('http://localhost:9000/getPurchaseList', 'get',() => {

  var list = {
    "data":{
      "list|16": [{   // 随机生成1到3个数组元素
        'purchaseOrderNumber': 'XPS_shaver_PUR201808',  // 中文名称
        'supplierOrderNo|+1': 15311142412620,    // 属性值自动加 1，初始值为88
        'contractReferenceOrderNo': 'SZYHXD-2491457-20180628',
        'supplierName': '飞利浦（中国）投资有限公司',  // 日期
        'orderAmount|10000-20000': 11000,
        'warehouseName|1': ['武汉普通仓','苏州望亭仓','平湖普通仓'],
        'purchaseStatus|1': ['草稿','交易成功','待收货','部分收货'],
        'businessDate': '@date("yyyy-MM-dd")',
        'purchaseNumber|100-200': 120,
        'createPerson': '@cname',
        'brother|1': ['jack', 'jim'], // 随机选取 1 个元素
        'sister|+1': ['jack', 'jim', 'lily'], // array中顺序选取元素作为结果
        'friends|2': ['jack', 'jim'] // 重复2次属性值生成一个新数组
      }],
      "total":16
    },
    "returnCode": 0,
    "message": null
  };

  return Mock.mock(list);
});

export default {
  getProjectList,
  getWarehouseList,
  getSupplierList,
  getPurchaseList
}

// module.exports=ROUTER;
