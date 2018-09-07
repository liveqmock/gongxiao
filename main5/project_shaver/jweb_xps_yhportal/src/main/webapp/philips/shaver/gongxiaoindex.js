/**
 * @authors {len} (http://csshack.org)
 * @date    2018/4/27
 */
import jQuery from 'jquery';
import {Promise} from 'es6-promise';
import './npmjs/bootstrap.min.js';
import './npmjs/bootstrap-table.js';
import './npmjs/bootstrap-table-zh-CN.js';
import './npmjs/select2.min.js';
import './npmjs/icon-svg.js';

Promise.polyfill();

(function ($, w, doc) {
    //转换成两位数
    function transformDoubble(num, length) {
        return ('' + num).length < length ? ((new Array(length + 1)).join('0') + num).slice(-length) : '' + num;
    }
    function getCount(data,prop,fieldArr,index,sortMap){
        if(index == fieldArr.length-1){
            if(prop == fieldArr[index]){
                var key = data[prop];
                if(sortMap.hasOwnProperty(key)){
                    sortMap[key] = sortMap[key]+ 1;
                } else {
                    sortMap[key] = 1;
                }
            }
            return;
        }
        if(prop == fieldArr[index]){
            var sdata = data[prop];
            index=index+1;
            getCount(sdata,fieldArr[index],fieldArr,index,sortMap);
        }
    }
    $.extend({
        appendSideBar: function (el) {
            var sidebarStr = "<a href=\"\"><img src=\"../../main/images/logo.png\" alt=\"\"></a><h4><svg class=\"icon-sp icon-sp3\" aria-hidden=\"true\"><use xlink:href=\"#icon-caigou\"></use></svg>采购管理</h4><div class=\"side-box\"><ul><li><a href=\"../purchase/purchase-management.html\">采购订单管理</a></li></ul></div><h4><svg class=\"icon-sp icon-sp5\" aria-hidden=\"true\"><use xlink:href=\"#icon-xiaoshou\"></use></svg>销售管理</h4><div class=\"side-box\"><ul><li><a href=\"../sales/sales-planmanagement.html\">预售管理</a></li><li><a href=\"../sales/sales-customerplanmanagement.html\">客户预售计划查询</a></li><li><a href=\"../sales/sales-management.html\">订单管理</a></li><li><a href=\"../canceltheorder/sales-cancel-management.html\">退换货管理</a></li></ul></div><h4><svg class=\"icon-sp icon-sp4\" aria-hidden=\"true\"><use xlink:href=\"#icon-cangcun\"></use></svg>仓储管理</h4><div class=\"side-box\"><ul><li><a href=\"../storage/storage-notice-entrymanagement.html\">入库通知单管理</a></li><li><a href=\"../storage/storage-notice-outboundmanagement.html\">出库通知单管理</a></li><li><a href=\"../storage/storage-entrymanagement.html\">入库单管理</a></li><li><a href=\"../storage/storage-outboundmanagement.html\">出库单管理</a></li><li><a href=\"../storage/diaobo-management.html\">调拨单管理</a></li></ul></div><h4><svg class=\"icon-sp icon-sp2\" aria-hidden=\"true\"><use xlink:href=\"#icon-kucun\"></use></svg>库存中心</h4><div class=\"side-box\"><ul><li><a href=\"../stock/stock-productquery.html\">货品库存查询</a></li><li><a href=\"../stock/stock-warehousequery.html\">仓库库存查询</a></li><li><a href=\"../stock/stock-account.html\">进销存台账</a></li><li><a href=\"../stock/stock-inventoryflow.html\">出入库流水台账</a></li><li><a href=\"../stock/stock-inventoryquery.html\">核对库存</a></li><li><a href=\"../stock/stock-batch.html\">批次报表</a></li></ul></div><h4><svg class=\"icon-sp icon-sp1\" aria-hidden=\"true\"><use xlink:href=\"#icon-cangku\"></use></svg>货品管理</h4><div class=\"side-box side-show\"><ul><li><a href=\"../basicdata/customer-addressmanagement.html\">客户审批处理</a></li></ul></div><h4><svg class=\"icon-sp icon-sp7\" aria-hidden=\"true\"><use xlink:href=\"#icon-qian\"></use></svg>收付款确认</h4><div class=\"side-box\"><ul><li><a href=\"../receivablesconfirmation/prepaidmanagement.html\">代垫付款单管理</a></li><li><a href=\"../receivablesconfirmation/prepaid-confirm.html\">代垫确认处理</a></li><li><a href=\"../receivablesconfirmation/rebate-confirm.html\">返利确认处理</a></li><li><a href=\"../receivablesconfirmation/rate-confirm.html\">毛利确认处理</a></li><li><a href=\"../receivablesconfirmation/cash-confirm.html\">收款确认处理</a></li><li><a href=\"../receivablesconfirmation/prepaidbook.html\">代垫台帐</a></li><li><a href=\"../receivablesconfirmation/rebatebook.html\">返利台帐</a></li><li><a href=\"../receivablesconfirmation/ratebook.html\">毛利台帐</a></li><li><a href=\"../receivablesconfirmation/cash-ledger.html\">确认台帐</a></li></ul></div><h4><svg class=\"icon-sp icon-sp6\" aria-hidden=\"true\"><use xlink:href=\"#icon-zhanghugl\"></use></svg>账户管理</h4><div class=\"side-box\"><ul><li><a href=\"../accountmanagement/obligateaccount.html\">供应商预留账户</a></li><li><a href=\"../accountmanagement/entryaccount.html\">供应商上账账户</a></li><li><a href=\"../accountmanagement/yhaccount.html\">YH账户</a></li><li><a href=\"../accountmanagement/customeraccountmanagement.html\">客户账户管理</a></li></ul></div><h4><svg class=\"icon-sp icon-sp8\" aria-hidden=\"true\"><use xlink:href=\"#icon-baobiao\"></use></svg>报表中心</h4><div class=\"side-box\"><ul><li><a href=\"../reportforms/batch-report.html\">批次返利报表</a></li></ul></div>";
            $(el).html(sidebarStr);
            var $arrSideLi = $(".side-box").find("li");
            $.each($arrSideLi, function (index, arrSideLi) {
                if ($(arrSideLi).attr("sidebar-sign") == "true") {
                    $(this).addClass("curclick").siblings().removeClass("curclick");
                }
            });
            $arrSideLi.click(function () {
                $(this).attr("sidebar-sign", "true").siblings().attr("sidebar-sign", "false");
                $arrSideLi.removeClass("curclick");
                $(this).addClass("curclick").siblings().removeClass("curclick");
            })
        },
        mergeCells: function (data, fieldName, colspan, target, fieldList) {
            // 声明一个map计算相同属性值在data对象出现的次数和
            var sortMap = {};
            for (var i = 0; i < data.length; i++) {
                for (var prop in data[i]) {
                    //例如people.unit.name
                    var fieldArr = fieldName.split(".");
                    getCount(data[i], prop, fieldArr, 0, sortMap);
                }
            };
            var index = 0;
            for (var prop in sortMap) {
                var count = sortMap[prop];
                for (var i = 0; i < fieldList.length; i++) {
                    $(target).bootstrapTable('mergeCells', {
                        index: index,
                        field: fieldList[i],
                        colspan: colspan,
                        rowspan: count
                    });
                }
                index += count;
            }
        },
        // 动态获得select选项
        getSelectOptions: function (el, url, id, text) {
            return new Promise(function (resolve, reject) {
                $.ajax({
                    url: url,
                    dataType: "json",
                    success: function (res) {
                        resolve(res);
                        var selectData = [{}];
                        if (res.returnCode === 0) {
                            var resData = res.data;
                            $.each(resData, function (index, item) {
                                if (item["supplierCode"] || item["warehouseId"]) {
                                    selectData[0] = {
                                        id: "",
                                        text: "请选择"
                                    };
                                    selectData[index + 1] = {
                                        id: item[id],
                                        text: item[text]
                                    };
                                } else {
                                    selectData[index] = {
                                        id: item[id],
                                        text: item[text]
                                    };
                                }
                            });
                            $("#" + el).select2({
                                width: 200,
                                data: selectData,
                                minimumResultsForSearch: 10,
                                placeholder: '请选择',
                                language: "zh-CN"
                            })
                        }
                    },
                    error: function (xhr) {
                        reject(xhr);
                    }
                })
            })
        },
        // 获取url参数
        getUrlParam: function (name) {
            const locationSearch = document.location.search;
            const pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
            const matcher = pattern.exec(locationSearch);
            var items = null;
            if (null != matcher) {
                try {
                    items = decodeURIComponent(decodeURIComponent(matcher[1]));
                } catch (e) {
                    try {
                        items = decodeURIComponent(matcher[1]);
                    } catch (e) {
                        items = matcher[1];
                    }
                }
            }
            return items;
        },
        // 下单时间和支付时间的时间转换
        transformTime: function (time, seconds) {
            var tempTime = new Date(time);
            var confirmTime = tempTime.getFullYear() + "-" + transformDoubble((tempTime.getMonth() + 1), 2) + "-" + transformDoubble(tempTime.getDate(), 2);
            if (time && !seconds) {
                return confirmTime;
            } else if (time && seconds) {
                confirmTime += " " + transformDoubble(tempTime.getHours(), 2) + ":" + transformDoubble(tempTime.getMinutes(), 2) + ":" + transformDoubble(tempTime.getSeconds(), 2);
                return confirmTime;
            } else {
                return ""
            }
        },
        // layui的弹出框
        renderLayerMessage: function (str, iconnum) {
            layui.config({
                version: '1515376178709' //为了更新 js 缓存
            });
            if (iconnum) {
                layui.use(['layer'], function () {
                    var layer = layui.layer;
                    layer.msg(str, {icon: iconnum});
                });
            } else {
                layui.use(['layer'], function () {
                    var layer = layui.layer;
                    layer.msg(str);
                });
            }
        },
        renderLoading:function (str) {
            layui.use('layer', function () {
                var layer = layui.layer;
                if(!str){
                    layer.closeAll('loading');
                }else{
                    layer.load(2);
                }
            });
        }
    });
}(jQuery, window, document));


$(function () {
    globalInit();

    function switchMask(mask, maskSwitch, maskCancel) {
        const $yhmask = $("#" + mask);
        const $yhmaskSwitch = $("#" + maskSwitch);
        const $yhmaskCancel = $("#" + maskCancel);

        $yhmaskSwitch.click(function () {
            $yhmask.addClass("yhmask-hide");
        });

        $yhmaskCancel.click(function () {
            $yhmask.addClass("yhmask-hide");
        });
    }

    function setSidebarStyle() {
        var $arrSideLi = $(".side-box").find("li");
        var $arrSideLink = $(".side-box").find("a");
        function getHtmlDocName() {
            var str = window.location.href;
            str = str.substring(str.lastIndexOf("/") + 1);
            str = str.substring(0, str.lastIndexOf("."));
            return str;
        }
        var urlHtmlName = getHtmlDocName();
        $.each($arrSideLink,function (index,linkItem) {
            var _href = $(linkItem).attr("href");
            _href = _href.substring(_href.lastIndexOf("/") + 1);
            _href = _href.substring(0, _href.lastIndexOf("."));
            if(_href == urlHtmlName){
                $arrSideLi.eq(index).addClass("curclick").siblings().removeClass("curclick")
            }
        })
    }

    function globalInit() {
        $.appendSideBar("#sidebar");

        setSidebarStyle();
        $.fn.bootstrapTable.defaults['len'] = {
            valign: 'middle',
            align: 'left'
        };

        $.extend($.fn.bootstrapTable.columnDefaults, $.fn.bootstrapTable.defaults['len']);
        // 关闭遮罩层
        switchMask("yhmask", "yhmask-switch", "yhmask-cancel");
    }

});

