$(function () {
    var URL = $.getfenxiaoURL();
    outboundDetailInit();
    // 明细信息表格
    var $stodeTable = $("#yhtable");

    function getParamsFromURL(){
        var getProjectIdFromUrl = $.getUrlParam("projectId");
        var getInventoryNumFromUrl = $.getUrlParam("gongxiaoOutboundOrderNo");
        var getWmsOutboundOrderNoFromUrl = $.getUrlParam("wmsOutboundOrderNo");
        return {
            projectId:getProjectIdFromUrl,
            //出库单号
            gongxiaoOutboundOrderNo:getInventoryNumFromUrl,
            wmsOutboundOrderNo:getWmsOutboundOrderNoFromUrl
        }
    }

    function getStorageOutboundDetailInfo(){
        // 基本信息
        var $storageInfo = $("#storageinfo").find("input");
        var detailParams = getParamsFromURL();
        // 出库单基本信息查询
        $.get( URL+"/warehouseManage/outboundNotify/selectByOutboundNum",detailParams,function(res){
            if(res.returnCode === 0){
                var infoData = res.data;
                // 出库单号 销售单号 出库类型 单据状态 发货仓库 批次号 供应商 发货地址 客户 创建时间 预计出库时间 更新时间
                var arrInfo = [infoData.gongxiaoOutboundOrderNo,infoData.salesOrderNo,infoData.outboundType,infoData.wmsOutboundOrderNo,infoData.purchaseOrderNo,infoData.customer,infoData.tmsOutboundOrderNo,infoData.easOutboundOrderNo,$.transformTime(infoData.createTime),infoData.warehouseName,infoData.batchNo,$.transformTime(infoData.createTime),$.transformTime(infoData.createTime),infoData.warehouseName];
                $storageInfo.each(function (index) {
                    $(this).val(arrInfo[index]);
                });

            }
        },"json");

        // 出库单明细查询
        $.get( URL+"/warehouseManage/outboundNotify/selectOutboundOrderItem",detailParams,function(res,status,xhr){
            if(res.returnCode === 0){
                renderTable();
                var storageDetailData = res.data;
                $stodeTable.bootstrapTable('load',storageDetailData);
            }
        },"json");
    }

    function outboundDetailInit(){

        var projectName = $.getUrlParam("projectName");
        $("#projecttext").val(projectName);
        getStorageOutboundDetailInfo();
    }


    // 渲染表格数据
    function renderTable() {
        var storageDetailData = [{}];
        $stodeTable.bootstrapTable({
            undefinedText: '',
            locale: 'zh-CN',
            striped: true,
            data: storageDetailData,
            columns: [{
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'productCode',
                title: '型号'
            }, {
                field: 'batchNo',
                title: '批次'
            }, {
                field: 'inventoryType',
                title: '品质',
                formatter: function (value,row) {
                    if (row.inventoryType === 1) {
                        return "普通好机";
                    } else if (row.inventoryType === 101) {
                        return "残次";
                    } else if (row.inventoryType === 102) {
                        return "机损";
                    } else if (row.inventoryType === 103) {
                        return "箱损";
                    } else if (row.inventoryType === 201) {
                        return "冻结库存";
                    } else if (row.inventoryType === 301) {
                        return "在途库存";
                    } else {
                        return "不详";
                    }
                }
                },{
                field: 'outStockQuantity',
                title: '实际出库数量'
            }]
        })
    }
});