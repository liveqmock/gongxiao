/**
 * @authors {Pinocchio} (http://csshack.org)
 * @date    2018-03-06 10:40:05
 * @version $Id$
 */

$(function() {


    var URL = $.getfenxiaoURL();

    function getParams() {
        //project
        var $storageItem = $("#query-item");
        //入库单号
        var $inventoryNum = $("#gongxiaono");
        //货品编码
        var $productCode = $("#productcode");
        //创建时间
        var $dateTime = $("#createtime");
        //仓库名称
        var $warehouseId = $("#warehouseinput");
        //采购单号
        var $purchaseno = $("#purchaseno");
        //供应商
        var $supplier = $("#supplier");
        var obj = {
            projectId: $storageItem.val(),
            inventoryNum: $inventoryNum.val(),
            productCode: $productCode.val(),
            dateTime: $dateTime.val(),
            purchaseNo: $purchaseno.val(),
            warehouseId: $warehouseId.val(),
            supplierName: $supplier.val()
        }
        return obj;
    }

    //初始化
    function init() {

        //初始化表格数据
        var $storageEntryTable = $("#storageentrytable");
        var $queryBtn = $("#query-btn");
        function queryTableData() {
            var queryParams = getParams();
            $.get( URL+"/warehouseManage/inbound/purchseToInstock", queryParams, function (res) {
                if (res.returnCode === 0) {
                    renderTable();
                    var entryTableLoadData = res.data;
                    $storageEntryTable.bootstrapTable('load', entryTableLoadData);
                }
            }, "json")
        }

        // 渲染projectlist
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName")
            .then(function () {
                queryTableData();
            });

        $queryBtn.click(function () {
            queryTableData();
        });
    }
    init();


    //渲染表格数据
    function renderTable() {
        var $stockQueryTable = $("#storageentrytable");
        var queryData = [{}];
        $stockQueryTable.bootstrapTable({
            undefinedText: '',
            striped: true,
            data: queryData,
            columns: [{
                field: 'gongxiaoInboundOrderNo',
                title: '入库单号',
                formatter: function(value, row) {
                    return '<a class="skipreview" href="./storage-detail.html?gongxiaoInboundOrderNo=' + row.gongxiaoInboundOrderNo + '&projectId='+row.projectId+'">' + row.gongxiaoInboundOrderNo + '</a>';
                }
            }, {
                field: 'purchaseOrderNo',
                title: '采购单号'
            }, {
                field: 'supplierName',
                title: '供应商'
            }, {
                field: 'inboundType',
                title: '单据类型'
            }, {
                field: 'warehouseName',
                title: '收货仓库'
            }, {
                field: 'totalQuantity',
                title: '计划入库数量'
            }, {
                field: 'inStockQuantity',
                title: '实际入库数量'
            }, {
                field: 'differentQuantity',
                title: '差异数量'
            }, {
                field: 'estimateArriveTime',
                title: '预计到仓时间',
                formatter: function (value) {
                    if (value) {
                        return $.transformTime(value,"seconds");
                    } else {
                        return ""
                    }
                }
            }, {
                field: 'createTime',
                title: '创建时间',
                formatter: function (value) {
                    if (value) {
                        return $.transformTime(value,"seconds");
                    } else {
                        return ""
                    }
                }
            }]
        })
    };
})