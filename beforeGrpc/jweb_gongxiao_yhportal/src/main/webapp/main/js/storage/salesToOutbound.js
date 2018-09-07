$(function () {



    var URL = $.getfenxiaoURL();

    function getParams() {
        //project
        var $storageItem = $("#query-item");
        //出库单号
        var $inventoryNum = $("#gongxiaono");
        //出库单号
        var $salseNo = $("#xiaoshouno");
        //创建时间
        var $createTimeBegin = $("#createtime");
        //创建时间
        var $createTimeTo = $("#createtime");
        //仓库名称
        var $warehouseId = $("#warehouseinput");
        //货品编码
        var $productCode = $("#productcode");
        //出库完成时间
        var $finishTimeBegin = $("#procode");
        //出库完成时间
        var $finishTimeTo = $("#procode");
        //供应商
        var $supplierName = $("#supplier");
        //客户
        var $customer = $("#customer");
        var obj = {
            projectId: $storageItem.val(),
            inventoryNum: $inventoryNum.val(),
            salseNo: $salseNo.val(),
            createTimeBegin: $createTimeBegin.val(),
            createTimeTo: $createTimeTo.val(),
            warehouseId: $warehouseId.val(),
            productCode: $productCode.val(),
            finishTimeBegin: $finishTimeBegin.val(),
            finishTimeTo: $finishTimeTo.val(),
            supplierName: $supplierName.val(),
            customer: $customer.val()
        }
        return obj;
    }

    var $storageEntryTable = $("#storageentrytable");

    function getAjax(params, state) {
        $.get(URL + "/warehouseManage/outbound/salseToOutbound", params, function (res, status, xhr) {
            if (state) {
                if (res.returnCode == 0) {
                    entryData = res.data;
                    renderTable();
                }
            } else {
                if (res.returnCode == 0) {
                    entryData = res.data;
                    $storageEntryTable.bootstrapTable('load', entryData);
                }
            }
        }, "json")
    }


    function checkTime(i) {
        return i < 10 ? "0" + i : i;
    }


    //初始化
    function init() {
        // $.selectFn(".pur-select");
        //project
        var $storageItem = $("#query-item");
        var $storageItemVal = $storageItem.val();
        //初始化表格数据
        var initPorjectId = {
            projectId: $storageItemVal
        }
        getAjax(initPorjectId, "true")
        $storageItem.change(function () {
            var projectId = $(this).val();
            var changeProjectId = {
                projectId: projectId
            }
            getAjax(changeProjectId);
        })
        //查询表格
        var $queryBtn = $("#query-btn");
        $queryBtn.click(function () {
            var storageQueryParams = getParams();
            getAjax(storageQueryParams);
        });
    }

    init();

    //渲染表格数据
    function renderTable() {
        $("#storageentrytable").bootstrapTable({
            pageSize: 25,
            pageList: [60, 100, 200],
            undefinedText: '',
            align: 'left',
            halign: 'left',
            showFooter: false,
            locale: 'zh-CN',
            striped: true,
            //api
            data: entryData,
            columns: [{
                field: 'gongxiaoOutboundOrderNo',
                title: '出库单号',
                formatter: function (value, row, index) {
                    return '<a class="skipreview" href="./storage-detail.html?gongxiaoOutboundOrderNo=' + row.gongxiaoOutboundOrderNo + '&projectId=' + row.projectId + '">' + row.gongxiaoOutboundOrderNo + '</a>';
                }
            }, {
                field: 'salesOrderNo',
                title: '销售单号'
            }, {
                field: 'outboundType',
                title: '单据类型'
            }, {
                field: 'orderStatus',
                title: '单据状态'
            }, {
                field: 'warehouseName',
                title: '发货仓库'
            }, {
                field: 'totalQuantity',
                title: '计划出库数量'
            }, {
                field: 'outStockQuantity',
                title: '实际出库数量'
            }, {
                field: 'differentQuantity',
                title: '差异数量'
            }, {
                field: 'supplierName',
                title: '供应商'
            }, {
                field: 'customer',
                title: '客户'
            }, {
                field: 'createTime',
                title: '创建时间',
                formatter: function (value, row, idnex) {
                    if (value) {
                        var tempTime = new Date(value);
                        var confirmTime = tempTime.getFullYear() + "/" + checkTime((tempTime.getMonth() + 1)) + "/" + checkTime(tempTime.getDate()) + " " + checkTime(tempTime.getHours()) + ":" + checkTime(tempTime.getMinutes()) + ":" + checkTime(tempTime.getSeconds());
                        return confirmTime;
                    } else {
                        return "没有时间"
                    }
                }
            }]
        })
    }
});