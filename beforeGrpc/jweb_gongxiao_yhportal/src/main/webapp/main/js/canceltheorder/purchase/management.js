/**
 * @authors {len} (http://csshack.org)
 */
$(function () {


    // 全局接口
    var URL = $.getfenxiaoURL();

    init();
    
    // 获取表格信息
    function getTableInfo() {
        // 退换货管理表格
        var $returnedPurchaseManagementTable = $("#returned-purmanagement");
        // 发货仓库
        var $warehouse = $("#warehouseid").val();
        // 退换货类型
        var $orderReturnedType = $("#orderreturnedtype").val();
        // 单号
        var $orderNo = $("#orderno").val();
        // 退单创建时间
        var $createTime = $("#createtime").val();
        var $createTime2 = $("#createtime2").val();
        var queryParams = {
            projectId: $("#projectlist").val(),
            warehouseId: $warehouse,
            returnType: $orderReturnedType,
            orderNumber: $orderNo,
            // startDate: $createTime,
            // endDate: $createTime2
        };
        $.get(URL + "/purchaseReturn/getPurchaseReturnList", queryParams, function (res, status, xhr) {
            if (res.returnCode == 0) {
                returnedRenderTable();
                var loadTableData = res.data;
                $returnedPurchaseManagementTable.bootstrapTable('load', loadTableData);
            }
        }, "json")
    }

    // 初始化
    function init() {

        $.renderDateRange("createtime","createtime2");

        // 退货类型
        $("#orderreturnedtype").select2({
            minimumResultsForSearch: 6
        });

        // 获取project列表
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName");

        // 获取仓库列表
        $.getSelectOptions("warehouselist", URL + "/purchase/foundation/warehouseList", "warehouseId", "warehouseName");

        // 查询按钮
        var $queryBtn = $("#salesquery-btn");
        $queryBtn.click(function () {
            getTableInfo();
        })
    }

    // 退换货表格渲染
    function returnedRenderTable() {
        var $returnedPurchaseManagementTable = $("#returned-purmanagement");
        var returnedTable = [{}];
        $returnedPurchaseManagementTable.bootstrapTable({
            undefinedText: "",
            data: returnedTable,
            // 隔行变色
            striped: true,
            columns: [{
                checkbox: true
            }, {
                field: 'orderNumber',
                title: '单号信息',
                formatter: function (value, row, index) {
                    if (!value) {
                        return ''
                    } else {
                        var orderNumberInfo = value;
                        orderNumberInfo = orderNumberInfo.split(',');
                        return '采购退货单号: ' + '<a href="./purchase-detail.html?purchaseReturnedOrderNo=' + orderNumberInfo[0] + '">' + orderNumberInfo[0] + '</a>' + '<br/>' + '来源订单号: ' + orderNumberInfo[1];
                    }
                }
            }, {
                field: 'orderStatus',
                title: '退货状态'
            }, {
                field: 'customerInfo',
                title: '客户信息'
            }, {
                field: 'returnOrderType',
                title: '退货单类型'
            }, {
                field: 'logisticInfo',
                title: '物流相关信息'
            }, {
                field: 'updateTime',
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
                    if (!value) {
                        return ""
                    } else {
                        return $.transformTime(value);
                    }
                }
            }, {
                title: '操作',
                formatter: function (value, row, index) {
                    if (!row.orderNumber) {
                        return ""
                    }
                    return '<a class="pur-addbtn purchaseaddbtn salesadd" href="javascript:void(0)">' + '取消退单' + '</a>'
                }
            }]
        })
    }
});