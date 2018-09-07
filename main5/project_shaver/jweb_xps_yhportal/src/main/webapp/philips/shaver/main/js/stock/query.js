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
        var $projectId = $("#projectlist");
        var obj = {
            productCode: $productCode.val(),
            productName: $productName.val(),
            projectId: $projectId.val(),
            pageNumber:1,
            pageSize:100,
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
            $.get(URL + "/inventoryQuerry/product", queryParams, function (res) {
                if (res.returnCode === 0) {
                    renderTable();
                    var entryTableLoadData = res.data;
                    $storageEntryTable.bootstrapTable('load', entryTableLoadData);
                }
            }, "json")
        }

        // 渲染projectlist
        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
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
                field: 'productCode',
                title: '货品编码'
            }, {
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'availableQuantity',
                title: '实物良品数量'
            }, {
                field: 'occupancyQuantity',
                title: '占用数量'
            }, {
                field: 'defectiveQuantity',
                title: '残次数量'
            }, {
                field: 'physicalInventory',
                title: '实物库存'
            }, {
                field: 'onWayQuantity',
                title: '在途数量'
            }, {
                field: 'frozenQuantity',
                title: '冻结数量'
            }]
        })
    }
});