/**
 * @authors {len} (http://csshack.org)
 * @date    2018-03-19 09:55:00
 */
$(function () {

    init();


    // 初始化
    function init() {
        var URL = $.getfenxiaoURL();
        renderElement(URL);
        getClassificationCount(URL);
        // 审核
        var $verify = $("#verify");
        $verify.click(function () {
            var $yhTable = $("#yhtable");
            var verifyData = $yhTable.bootstrapTable('getSelections');
            var verifyParams = [{}];
            $.each(verifyData, function (index, item) {
                verifyParams[index] = {
                    salesReturnedOrderId: item.salesReturnedOrderId,
                    dataVersion: item.dataVersion
                }
            });
            verifyParams = JSON.stringify(verifyParams);
            $.ajax({
                type: "post",
                url: URL + "/sales/return/check",
                data: verifyParams,
                dataType: "json",
                contentType: "application/json",
                success: function (data) {
                    console.log(data)
                }

            })
        })
    }

    function renderElement(URL) {
        // 项目列表
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName")
            .then(function () {
                returnedRenderTable(URL)
            });
        // 仓库列表
        $.getSelectOptions("warehouselist", URL + "/purchase/foundation/warehouseList", "warehouseId", "warehouseName");
        // 退货类型
        $("#ordertype").select2({
            width: 200,
            minimumResultsForSearch: 6,
            placeholder: '请选择',
            language: "zh-CN"
        });
        $.renderDateRange("createtime","createtime2")
    }

    // 退货单分类统计
    function getClassificationCount(URL) {
        var $salesReturnedInfo = $("#salesreturnedinfo");
        function renderStatusNumber(index, status,str, num) {
            var statusStr = str+ '<span>(' + num +')</span>';
            $salesReturnedInfo.children().eq(index).attr("salesReturnedType", status).html(statusStr);
        }
        var classParams = getClassParams();
        $.get(URL + "/sales/return/getClassificationCount",classParams, function (res) {
            if (res.returnCode === 0) {
                var resData = res.data;
                $.each(resData, function (index, resDataItem) {
                    renderStatusNumber(resDataItem.status,resDataItem.status,resDataItem.statusStr,resDataItem.count);
                });
            }
        }, "json")
    }


    // 获取表格需要查询的参数
    function getClassParams() {
        var projectId = $("#projectlist").val();
        var $warehouseList = $("#warehouselist");
        //发货仓库
        var warehouseId = $warehouseList.val();
        var warehouseName = $warehouseList.children("option:selected").text();
        // 退换货类型
        var orderType = $("#ordertype").val();
        // 单号
        var orderNo = $("#orderno").val();
        // 退单创建时间
        var startTime = $("#createtime").val();
        var endTime = $("#createtime2").val();
        var queryParams = {
            projectId: projectId,
            warehouseId: warehouseId,
            returnedType: orderType,
            salesReturnedOrderNo: orderNo,
            warehouseName: warehouseName,
            timeStart: startTime,
            timeEnd: endTime,
        };
        return queryParams;
    }


    // 查询
    $("#yhquery-btn").click(function () {
        $("#yhtable").bootstrapTable('refresh',{pageNumber: 1});
    });

    // 根据退货状态查询
    $("#salesreturnedinfo").on('click', 'a', function () {
        event.preventDefault();
        var salesStatus = $(this).attr("salesReturnedType");
        if(salesStatus === 0 || salesStatus === "0"){
            salesStatus = "";
        }
        $(this).addClass("managementoperalink").siblings().removeClass("managementoperalink");
        $("#yhtable").bootstrapTable("refresh", {query: {returnedOrderStatus: salesStatus}});
    });

    // 返回成功渲染表格
    function responseHandler(res) {
        if (res.returnCode === 0 && res.data.list.length !== 0 && res.data.total !== 0 && typeof res.data.total !== 'undefined') {
            return {
                total: res['data']['total'], // 总页数,前面的key必须为"total"
                data: res['data']['list'] // 行数据，前面的key要与之前设置的dataField的值一致.
            }
        } else {
            return {
                total: 0, // 总页数,前面的key必须为"total"
                data: [{}] // 行数据，前面的key要与之前设置的dataField的值一致.
            };
        }
    }

    // 获取表格需要查询的参数
    function getQueryParams(params) {
        var projectId = $("#projectlist").val();
        var $warehouseList = $("#warehouselist");
        //发货仓库
        var warehouseId = $warehouseList.val();
        var warehouseName = $warehouseList.children("option:selected").text();
        // 退换货类型
        var orderType = $("#ordertype").val();
        // 单号
        var orderNo = $("#orderno").val();
        // 退单创建时间
        var startTime = $("#createtime").val();
        var endTime = $("#createtime2").val();
        var queryParams = {
            projectId: projectId,
            warehouseId: warehouseId,
            returnedType: orderType,
            salesReturnedOrderNo: orderNo,
            warehouseName: warehouseName,
            timeStart: startTime,
            timeEnd: endTime,
            pageNumber: params.pageNumber,
            pageSize: params.pageSize
        };
        return queryParams;
    }

    // 退换货表格渲染
    function returnedRenderTable(URL) {
        var $yhTable = $("#yhtable");
        $yhTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/sales/return/getsByProjectId",
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
                field: 'salesReturnedOrderNo',
                title: '单号信息',
                formatter: function (value, row, index) {
                    if (!value) {
                        return ''
                    } else {
                        var orderNumberInfo = value;
                        return '销售退货单号: ' + '<a class="table-yh-link" href="./sales-detail.html?salesReturnOrderId=' + row.salesReturnedOrderId + '">' + orderNumberInfo + '</a>' + '<br/>' + '来源订单号: ' + row.originalSalesOrderNo;
                    }
                }
            }, {
                field: 'returnedOrderStatus',
                title: '退货状态',
                formatter: function (value, row, index) {
                    if (!value) {
                        return ""
                    } else if (value === 1) {
                        return "新建"
                    } else if (value === 2) {
                        return "等待退货到仓"
                    } else if (value === 3) {
                        return "退货完成"
                    }
                }
            }, {
                field: 'distributorName',
                title: '客户信息'
            }, {
                field: 'returnedType',
                title: '退货单类型',
                formatter: function (value, row, index) {
                    if (!value) {
                        return ""
                    } else if (value == 1) {
                        return "销售退货"
                    } else if (value == 2) {
                        return "换货退货"
                    }
                }
            }, {
                field: 'originalOutboundWarehouseName',
                title: '物流相关信息',
                formatter: function (value, row, index) {
                    if (!value) {
                        return ''
                    } else {
                        var originalOutboundWarehouseName = value;
                        return '发货仓库: ' + originalOutboundWarehouseName + originalOutboundWarehouseName + '<br/>' + '退库: ' + row.targetWarehouseName;
                    }
                }
            }, {
                field: 'lastUpdateTime',
                title: '退货单更新时间',
                formatter: function (value, row, index) {
                    if (!value) {
                        return ""
                    } else {
                        return $.transformTime(value);
                    }
                }
            }, {
                field: 'createTime',
                title: '退单创建时间',
                formatter: function (value, row, index) {
                    return $.transformTime(value);
                }
            }, {
                title: '操作',
                formatter: function (value, row) {
                    if (!row.orderNumber) {
                        return ""
                    }
                    return '<a class="pur-addbtn purchaseaddbtn salesadd" href="javascript:void(0)">' + '取消退单' + '</a>'
                }
            }]
        })
    }
});