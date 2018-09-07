/**
 * @authors {len} (http://csshack.org)
 * @date    2018/4/10
 * @version $Id\$
 */
$(function () {


    init();

    function init() {
        // 全局接口
        var URL = $.getcustomerURL();
        renderDate();
        var $orderInfo = getPageElement();
        // 表格初始化
        renderTable(URL);
        // 查询
        $orderInfo.$orderListQueryBtn.click(function () {
            $orderInfo.refreshTable();
        });
        // 导出
        $orderInfo.$exportBtn.click(function () {
            var exportParams = $orderInfo.orderListParams;
            window.open(URL + "/order/export" + "?purchaseNo=" + exportParams.purchaseNo + "&productCode=" + exportParams.productCode + "&status" + exportParams.status + "&dateStart=" + exportParams.dateStart + "&dateEnd" + exportParams.dateEnd);
        })
    }

    // 时间控件
    function renderDate() {
        var $ = laydate.$;
        // var max = null;
        var start = laydate.render({
            elem: '#datestart',
            // max: nowTime,
            btns: ['clear', 'confirm'],
            done: function (value, date) {
                endMax = end.config.max;
                end.config.min = date;
                end.config.min.month = date.month - 1;
            }
        });
        var end = laydate.render({
            elem: '#dateend',
            // max: nowTime,
            done: function (value, date) {
                var curDate = new Date();
                date = {'date': curDate.getDate(), 'month': curDate.getMonth() + 1, 'year': curDate.getFullYear()};
                start.config.max = date;
                start.config.max.month = date.month - 1;
            }
        });
    }

    // 页面上的元素和信息
    function getPageElement() {
        var $purchaseNo = $("#purchaseno");
        var $productCode = $("#productcode");
        var $status = $("#status");
        var $dateStart = $("#datestart");
        var $dateEnd = $("#dateend");
        var $orderListQueryBtn = $("#orderlist-querybtn");
        var $table = $("#table");
        // 导出按钮
        var $exportBtn = $("#export-btn");
        var orderInfo = {
            orderListParams: {
                purchaseNo: $purchaseNo.val(),
                productCode: $productCode.val(),
                status: $status.val(),
                dateStart: $dateStart.val(),
                dateEnd: $dateEnd.val()
            },
            $orderListQueryBtn: $orderListQueryBtn,
            $table: $table,
            $exportBtn: $exportBtn,
            refreshTable: function () {
                this.$table.bootstrapTable("refreshOptions", {pageNumber: 1});
            }
        };
        return orderInfo
    }

    // 遮罩层
    function getMaskInfo() {
        var $orderMask = $("#ordermask");
        // 目录关闭按钮
        var $maskClose = $("#mask-close");
        var $orderDetailTable = $("#order-detailtable");
        var $maskCloseBtn = $("#maskclose-btn");
        var $orderDetailInfo = $("#order-detailinfo");
        var maskInfo = {
            orderMask: $orderMask,
            maskClose: $maskClose,
            orderDetailTable: $orderDetailTable,
            maskCloseBtn: $maskCloseBtn,
            orderDetailInfo: $orderDetailInfo,
            closeMask: function () {
                this.orderMask.addClass("mask-hide");
            }
        };
        $maskClose.click(function () {
            maskInfo.closeMask();
        });
        $maskCloseBtn.click(function () {
            maskInfo.closeMask();
        });
        return maskInfo;


    }


    // 返回成功渲染表格
    function responseHandler(res) {
        if (res.returnCode === 0 && res['data']['list']['length'] !== 0 && res['data']['total'] !== 0 && typeof res['data']['total'] !== 'undefined') {
            return {
                total: res.data.total, // 总页数,前面的key必须为"total"
                data: res.data.list // 行数据，前面的key要与之前设置的dataField的值一致.
            }
        }else{
            return{
                total: 0,
                data: [{}]
            }
        }
    }

    // 获取表格需要查询的参数
    function getQueryParams(params) {
        var $pageElement = getPageElement();
        var obj = $.extend($pageElement.orderListParams, {
            pageNumber: params.pageNumber,
            pageSize: params.pageSize
        });
        return obj;
    }

    function renderTable(URL) {
        var $table = $("#table");
        var $maskInfo = getMaskInfo();
        $table.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/order/getOrderList",
            queryParamsType: '',
            silent: true,
            undefinedText: '',
            striped: true,
            dataField: 'data',
            pagination: true,
            pageSize: 60,
            pageList: [60, 100, 200],
            pageNumber: 1,
            sidePagination: "server",
            queryParams: getQueryParams,
            responseHandler: responseHandler,
            columns: [{
                field: 'salesOrderNo',
                title: '采购单号',
                clickToSelect: false,
                formatter: function (value) {
                    if (value) {
                        return '<a class="salesdetaillink" href="javascript:void(0);">' + value + '</a>'
                    }
                },
                events: {
                    'click .salesdetaillink': function (e, value, row, index) {
                        $maskInfo.orderMask.removeClass("mask-hide");
                        $.get(URL + "/order/getOrderInfo", {
                            salesOrderNo: value
                        }, function (res) {
                            if (res.returnCode === 0) {
                                var resOrderDetailData = res.data;
                                // 采购单号，订单金额，收货金额，订单状态，收货负责人，收货电话，更新时间，收货地址
                                var arrInfoData = [value, resOrderDetailData.totalOrderAmountDouble, resOrderDetailData.totalOrderAmountDouble, resOrderDetailData.salesOrderStatusStr, resOrderDetailData.recipientName, resOrderDetailData.recipientMobile, resOrderDetailData.lastUpdateTime, resOrderDetailData.shippingAddress
                                ];
                                var $arrInfoSpan = $maskInfo.orderDetailInfo.find("span");
                                $arrInfoSpan.each(function (index) {
                                    $(this).html(arrInfoData[index]);
                                });
                                var orderDetailLoadData = resOrderDetailData.salesOrderItemInfos;
                                renderOrderDetailTable();
                                $maskInfo.orderDetailTable.bootstrapTable('load', orderDetailLoadData);
                            }
                        }, "json");
                    }
                }
            }, {
                field: 'salesOrderStatusStr',
                title: '订单状态'
            }, {
                field: 'totalQuantity',
                title: '采购数量'
            }, {
                field: 'discountAmountTotal',
                title: '优惠金额'
            }, {
                field: 'totalOrderAmountDouble',
                title: '采购金额'
            },{
                field: 'prepaidAmountDouble',
                title: '代垫金额'
            },{
                field: 'couponAmountDouble',
                title: '返利金额'
            }, {
                field: 'createTime',
                title: '下单时间',
                formatter: function (value, row, index) {
                    return $.transFormTime(value, "seconds");
                }
            }, {
                field: 'paidTime',
                title: '收款时间',
                formatter: function (value, row, index) {
                    if (value != null) {
                        return $.transFormTime(value, "seconds");
                    }
                }
            }, {
                title: '操作',
                formatter: function (value,row) {
                    if (row.salesOrderStatus == 1) {
                        return '<a class="teblecancel-opera">' + '取消订单' + '</a>';
                    }
                },
                events: {
                    'click .teblecancel-opera': function (event, value, row, index) {
                        var eventParamSalesOrderNo = row.salesOrderNo;
                        var cancelParams = {
                            salesOrderNo: eventParamSalesOrderNo
                        };
                        $.post(URL + "/order/cancelOrder", cancelParams, function (res) {
                            if (res.returnCode === 0) {
                                alert("取消订单成功！")
                                $table.bootstrapTable("refreshOptions", {pageNumber: 1});
                            } else {
                                alert(res.message);
                            }
                        },"json")
                    }
                }
            }]
        })
    }

    function renderOrderDetailTable() {
        var $orderDetailTable = $("#order-detailtable");
        var orderDetailData = [{}];
        $orderDetailTable.bootstrapTable({
            undefinedText: '',
            clickToSelect: true,
            striped: true,
            data: orderDetailData,
            columns: [{
                field: 'productCode',
                title: '货品编码'
            }, {
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'discountPercentStr',
                title: '折扣点'
            }, {
                field: 'wholesalePriceDouble',
                title: '进货价'
            }, {
                field: 'totalQuantity',
                title: '采购数量'
            }, {
                field: 'sendQuantity',
                title: '发货数量'
            }, {
                field: 'deliveredQuantity',
                title: '实收数量'
            }, {
                field: 'totalOrderAmountDouble',
                title: '金额小计'
            }]
        })
    }
});