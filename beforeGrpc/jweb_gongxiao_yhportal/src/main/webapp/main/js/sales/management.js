/**
 * @authors {len} (http://csshack.org)
 * @date    2018-03-05
 */

$(function () {

    salesInit();


    function salesInit() {

        // 全局接口
        var URL = $.getfenxiaoURL();
        var $maskElement = getMaskElement();
        renderDate();
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName")
            .then(function () {
                renderSalesTable(URL);
            });

        var $pageElement = getPageElement();

        // 查询
        $pageElement.$salesQueryBtn.click(function () {
            $pageElement.refreshSalesTable();
        });
        // 根据销售状态查询
        $pageElement.$salesReturnedInfo.on('click', 'a', function (index) {
            event.preventDefault();
            var salesStatus = $(this).attr("salesReturnedType");
            $(this).addClass("managementoperalink").siblings().removeClass("managementoperalink");
            $pageElement.salesStatusRenderTable(salesStatus);
        });


        // 修改结算模式
        $maskElement.$yhmaskSubmit.click(function () {
            var tableSelectData = $pageElement.$salesTable.bootstrapTable("getSelections");
            var arrReceiveId = "";
            $.each(tableSelectData, function (index, tableDataItem) {
                if (arrReceiveId.length !== 0) {
                    arrReceiveId += ",";
                }
                arrReceiveId += tableDataItem["salesOrderNo"];
            });
            var postParams = {
                remark: $maskElement.$remark.val(),
                days: $maskElement.$days.val(),
                salesOrderNoList: arrReceiveId
            };
            $.get(URL + "/sales/settlementMode", postParams, function (res) {
                if (res.returnCode === 0) {
                    $.renderLayerMessage("修改成功");
                    $pageElement.$salesTable.bootstrapTable("refreshOptions", {pageNumber: 1});
                } else {
                    $.renderLayerMessage(res.message);
                }
            }, "json");
            $maskElement.$yhmask.addClass("yhmask-hide");
        });

        //点击“改变结算模式”，打开遮罩层
        $pageElement.$settlementBtn.click(function () {
            var tableSelectData = $pageElement.$salesTable.bootstrapTable("getSelections");
            if (tableSelectData.length != 0) {
                $maskElement.$yhmask.removeClass("yhmask-hide");
            } else {
                $.renderLayerMessage("请选择单号！");
            }
        });
    }

    function renderDate() {
        layui.config({
            version: '1515376178709' //为了更新 js 缓存
        });
        layui.use(['laydate'], function () {
            var laydate = layui.laydate;
            laydate.render({
                elem: '#yhdate',
                format: 'yyyy-MM-dd'
            });
            laydate.render({
                elem: '#yhcreate-date',
                format: 'yyyy-MM-dd'
            });
        });
    }


    // 获取页面元素
    function getPageElement() {
        // 销售表格
        var $salesTable = $("#salestable");
        // 销售查询按钮
        var $salesQueryBtn = $("#salesquery-btn");
        // 状态
        var $salesReturnedInfo = $("#salesinfo");
        // 更改结算
        var $settlementBtn = $("#settlement-btn");
        var pageElement = {
            $salesQueryBtn: $salesQueryBtn,
            $salesTable: $salesTable,
            $salesReturnedInfo: $salesReturnedInfo,
            $settlementBtn: $settlementBtn,
            refreshSalesTable: function () {
                this.$salesTable.bootstrapTable("refreshOptions", {pageNumber: 1});
            },
            salesStatusRenderTable(status) {
                this.$salesTable.bootstrapTable("refresh", {query: {orderStatus: status}});
            }
        };
        return pageElement;
    }

    // 返回成功渲染表格
    function responseHandler(res) {
        var $pageElement = getPageElement();
        function renderStatusNumber(index, status, num) {
            $pageElement.$salesReturnedInfo.children().eq(index).attr("salesReturnedType", status).find("span").html("(" + num + ")");
        }

        var countMap = res.data.countMap;
        var allNum = 0;
        $.each(countMap, function (index, countMapItem) {
            var saleOrderStatus = countMapItem.salesOrderStatus;
            var statusNum = countMapItem.num;
            allNum += statusNum;
            if (saleOrderStatus === 1) {
                renderStatusNumber(2, saleOrderStatus, statusNum);
            } else if (saleOrderStatus === 2) {
                renderStatusNumber(3, saleOrderStatus, statusNum);
            } else if (saleOrderStatus === 3) {
                renderStatusNumber(4, saleOrderStatus, statusNum);
            } else if (saleOrderStatus === 4) {
                renderStatusNumber(5, saleOrderStatus, statusNum);
            } else if (saleOrderStatus === 5) {
                renderStatusNumber(6, saleOrderStatus, statusNum);
            } else if (saleOrderStatus === 6) {
                renderStatusNumber(7, saleOrderStatus, statusNum);
            } else if (saleOrderStatus === 8) {
                renderStatusNumber(9, saleOrderStatus, statusNum);
            } else if (saleOrderStatus === 9) {
                renderStatusNumber(1, saleOrderStatus, statusNum);
            }
            renderStatusNumber(0, "", allNum);
        });
        if (res.returnCode === 0) {
            return {
                total: res['data']['salesOrders']['total'], // 总页数,前面的key必须为"total"
                data: res['data']['salesOrders']['list'] // 行数据，前面的key要与之前设置的dataField的值一致.
            }
        }else{
            return {
                total: 0, // 总页数,前面的key必须为"total"
                data: [{}] // 行数据，前面的key要与之前设置的dataField的值一致.
            };
        }
    }

    // 获取表格需要查询的参数
    function getQueryParams(params) {
        // 订单号
        var $orderNo = $("#orderno");
        // 出库时间
        var $outDate = $("#yhdate");
        // 创建时间
        var $createDate = $("#yhcreate-date");
        // 项目
        var projectId = $("#projectlist option:selected").val();
        return {
            orderNo: $orderNo.val(),
            outDate: $outDate.val(),
            createTime: $createDate.val(),
            projectId: projectId,
            pageNumber: params.pageNumber,
            pageSize: params.pageSize
        }
    }

    //获取遮罩层
    function getMaskElement() {
        var $yhmask = $("#yhmask");
        // 天数
        var $intoAmount = $("#intoamount");
        // 备注
        var $maskRemark = $("#mask-remark");
        // 确认按钮
        var $yhmaskSubmit = $("#yhmask-submit");
        return {
            $days: $intoAmount,
            $remark: $maskRemark,
            $yhmaskSubmit: $yhmaskSubmit,
            $yhmask: $yhmask
        }
    }

    // 渲染表格数据
    function renderSalesTable(URL) {
        var $salesTable = $("#salestable");
        var projectId = $("#projectlist").children("option:selected").val();
        $salesTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/sales/getOrderList",
            queryParamsType: '',
            silent: true,
            undefinedText: '',
            striped: true,
            dataField: 'data',
            pagination: true,
            pageSize: 60, // 一页显示数据条数
            pageList: [60, 100, 200],
            pageNumber: 1, // 首页页码
            sidePagination: "server",
            queryParams: getQueryParams,
            responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法
            columns: [{
                checkbox: true
            }, {
                field: 'salesOrderNo',
                title: '单号',
                formatter: function (value) {
                    return '<a class="table-yh-link skipreview" href="./sales-detail.html?salesOrderNo=' + value + '">' + value + '</a>';
                }
            }, {
                field: 'settlementMode',
                title: '结算模式',
                formatter: function (value) {
                    if (value === 1) {
                        return "先款后货";
                    } else if (value === 2) {
                        return "账期付款";
                    }
                }
            }, {
                field: 'salesOrderStatus',
                title: '订单状态',
                formatter: function (value) {
                    if (value === 1) {
                        return "待审核";
                    } else if (value === 2) {
                        return "待收款";
                    } else if (value === 3) {
                        return "待下发仓库";
                    } else if (value === 4) {
                        return "待出库";
                    } else if (value === 5) {
                        return "待签收";
                    } else if (value === 6) {
                        return "已签收";
                    } else if (value === 8) {
                        return "已取消";
                    } else if (value === 9) {
                        return "已驳回";
                    }
                }
            }, {
                field: 'totalOrderAmountDouble',
                title: '订单金额'
            }, {
                field: 'cashAmountDoubleStr',
                title: '应收金额'
            }, {
                field: 'distributorName',
                title: '客户信息'
            }, {
                field: 'createTime',
                title: '下单时间',
            }, {
                field: 'paymentDays',
                title: '账期天数'
            }, {
                field: 'paidTime',
                title: '收款时间',
            }, {
                field: 'totalQuantity',
                title: '订单货品数量'
            }, {
                field: 'unhandledQuantity',
                title: '剩余发货数量'
            }, {
                title: '操作',
                width: 90,
                formatter: function (value, row) {
                    var manageState = row.salesOrderStatus;
                    if (manageState === 1) {
                        return '<a class="skipreview" href="./sales-review.html?salesOrderNo=' + row.salesOrderNo + '">' + "审核" + '</a>';
                    } else if (manageState === 2) {
                        return '<a class="cancelview">' + "取消审核" + '</a>';
                    } else if (manageState === 3) {
                        return '<a class="skipreview" href="./sales-reservations.html?salesOrderNo=' + row.salesOrderNo + '&projectId=' + projectId + '">' + "发货通知" + '</a>';
                    } else if (manageState === 4 || manageState === 5) {
                        return '<a class="skipreview" href="/main/storage/storage-notice-outboundmanagement.html">' + "出库详情" + '</a>';
                    } else if (manageState === 6) {
                        return '<a href="../canceltheorder/sales-createnote.html?salesOrderNo=' + row.salesOrderNo + '">' + "退货" + '</a>';
                    }
                },
                events: {
                    'click .cancelview': function (e, value, row, index) {
                        var cancelParams = {
                            salesOrderNo: row.salesOrderNo
                        };
                        $.post(URL + "/sales/cancelApprove", cancelParams, function (res) {
                            if (res.returnCode === 0) {
                                $salesTable.bootstrapTable("refreshOptions", {pageNumber: 1});
                            } else {
                                $.renderLayerMessage("取消审核失败！");
                            }
                        }, "json")
                    }
                }
            }]
        })
    }

});