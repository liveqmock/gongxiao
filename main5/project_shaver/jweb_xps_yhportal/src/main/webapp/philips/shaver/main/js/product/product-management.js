/**
 * @authors {len} (http://csshack.org)
 * @date    2018-03-05
 */

$(function () {

    init();

    function init() {
        // 全局接口
        var URL = $.getfenxiaoURL();
        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
            .then(function () {
                renderTable(URL);
            });

        // 查询
        $("#yhquery-btn").click(function () {
            $("#yhtable").bootstrapTable("refreshOption",{pageNumber:1});
        })
    }



    // 返回成功渲染表格
    function responseHandler(res) {
        var $pageElement = getPageElement();
        if (res.returnCode === 0 && res.data.list.length !== 0 && res.data.total !== 0 && typeof res.data.total !== 'undefined') {
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
            return {
                total: res.data.salesOrders.total, // 总页数,前面的key必须为"total"
                data: res.data.salesOrders.list // 行数据，前面的key要与之前设置的dataField的值一致.
            };
        }else {
            return {
                total: 0, // 总页数,前面的key必须为"total"
                data: [{}] // 行数据，前面的key要与之前设置的dataField的值一致.
            };
        }
    }

    // 获取表格需要查询的参数
    function getQueryParams(params) {
        // 单号
        var $productCode = $("#product-code");
        // 货品名称
        var $productName = $("#product-name");
        // 物料编码
        var $materialCode = $("#material-code");
        return {
            productCode: $productCode.val(),
            productName: $productName.val(),
            materialCode: $materialCode.val(),
            pageNumber: params.pageNumber,
            pageSize: params.pageSize
        }
    }

    // 渲染表格数据
    function renderTable(URL) {
        var $yhTable = $("#yhtable");
        $yhTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "",
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
                    return '<a class="skipreview" href="./product-detail.html?salesOrderNo=' + value + '">' + value + '</a>';
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
                title: '金额'
            }, {
                field: 'distributorName',
                title: '客户信息'
            }, {
                field: 'createTime',
                title: '下单时间',
                formatter: function (value) {
                    if (!value) {
                        return '';
                    } else {
                        return $.transformTime(value, "seconds");
                    }
                }
            }, {
                field: 'paymentDays',
                title: '账期天数'
            }, {
                field: 'paidTime',
                title: '收款时间',
                formatter: function (value) {
                    if (!value) {
                        return '';
                    } else {
                        return $.transformTime(value, "seconds");
                    }
                }
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
                        return '<a class="skipreview" href="/main/storage/storage-outboundmanagement.html">' + "出库详情" + '</a>';
                    } else if (manageState === 6) {
                        return '<a href="../canceltheorder/sales-createnote.html?salesOrderNo='+row.salesOrderNo+'">' + "退货" + '</a>';
                    }
                },
                events: {
                    'click .cancelview': function (e, value, row, index) {
                        var cancelParams = {
                            salesOrderNo: row.salesOrderNo
                        }
                        $.post(URL + "/sales/cancelApprove", cancelParams, function (res) {
                            if (res.returnCode === 0) {
                                $salesTable.bootstrapTable("refreshOptions", {pageNumber: 1});
                            } else {
                                alert("取消审核失败！");
                            }
                        }, "json")
                    }
                }
            }]
        })
    }
});