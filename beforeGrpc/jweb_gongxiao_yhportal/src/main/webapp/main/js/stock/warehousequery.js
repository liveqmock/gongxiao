/**
 * @authors {Pinocchio} (http://csshack.org)
 * @date    2018-03-06 10:40:05
 * @version $Id$
 */

$(function() {

    var URL = $.getfenxiaoURL();

    function getParams() {
        var $productCode = $("#productCode");
        var $productName = $("#productName");
        var $warehouseId = $("#warehouselist");
        var $projectId = $("#projectlist");
        var obj = {
            productCode: $productCode.val(),
            productName: $productName.val(),
            warehouseId: $warehouseId.val(),
            projectId: $projectId.val()
        }
        return obj;
    }

    //初始化
    function init() {

        var $stockquerytable = $("#stockquerytable");

        var $queryBtn = $("#query-btn");

        function queryTableData() {
            var queryParams = getParams();
            $.get(URL + "/inventory/stock", queryParams, function (res) {
                if (res.returnCode === 0) {
                    renderTable();
                    var entryTableLoadData = res.data;
                    $stockquerytable.bootstrapTable('load', entryTableLoadData);
                }
            }, "json")
        }

        // 渲染projectlist
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName")
            .then(function () {
                // 获取收货仓库列表
                $.getSelectOptions("warehouselist", URL + "/purchase/foundation/warehouseList", "warehouseId", "warehouseName");
                queryTableData();
            });

        $queryBtn.click(function () {
            queryTableData();
        });
    }
    init();


    //渲染表格数据
    function renderTable() {
        var $stockQueryTable = $("#stockquerytable");
        var queryData = [{}];
        $stockQueryTable.bootstrapTable({
            undefinedText: '',
            striped: true,
            data: queryData,
            columns: [{
                field: 'productCode',
                title: '型号'
            }, {
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'warehouseName',
                title: '仓库名称'
            }, {
                field: 'status',
                title: '库存状态'
            }, {
                field: 'batchNo',
                title: '批次号'
            }, {
                field: 'purchaseType',
                title: '采购方式'
            }, {
                field: 'availableQuantity',
                title: '可用数量'
            }, {
                field: 'occupancyQuantity',
                title: '占用数量'
            }]
        })
    };
})