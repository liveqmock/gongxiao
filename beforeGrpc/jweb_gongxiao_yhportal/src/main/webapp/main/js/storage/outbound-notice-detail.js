$(function(){


    var URL = $.getfenxiaoURL();
    outboundDetailInit();
    // 明细信息表格
    var $stodeTable = $("#stodetable");

    //操作日志表格
    var $storagelog = $("#storagelog");

    function getParamsFromURL(){
        var getProjectIdFromUrl = $.getUrlParam("projectId");
        var getInventoryNumFromUrl = $.getUrlParam("gongxiaoOutboundOrderNo");
        var getProductCodeFromUrl = $.getUrlParam("productCode");
        var getInventoryTypeFromUrl = $.getUrlParam("inventoryType");
        return {
            projectId:getProjectIdFromUrl,
            //出库单号
            gongxiaoOutboundOrderNo:getInventoryNumFromUrl,
            productCode:getProductCodeFromUrl,
            inventoryType:getInventoryTypeFromUrl
        }
    }

    function getStorageOutboundDetailInfo(){
        // 基本信息
        var $storageInfo = $("#storageinfo").find("input");
        // 客户收货信息
        var $distributorInfo = $("#distributorInfo").find("input");
        var $storemark = $("#storemark");
        // 运输信息
        var $transpInfo = $("#transpinfo").find("input");
        var detailParams = getParamsFromURL();
        // 出库单基本信息查询
        $.get( URL+"/warehouseManage/outbound/outboundOrder/selectByOutboundNum",detailParams,function(res){
            if(res.returnCode === 0){
                var infoData = res.data;
                // 出库单号 销售单号 出库类型 单据状态 发货仓库 批次号 供应商 发货地址 客户 创建时间 预计出库时间 更新时间
                var arrInfo = [infoData.gongxiaoOutboundOrderNo,infoData.salesOrderNo,infoData.outStorageType,infoData.orderStatus,infoData.deliverWarehouse,infoData.bachNo,infoData.supplier,infoData.deliverAddress,infoData.customer,$.transformTime(infoData.createTime),$.transformTime(infoData.estimateOutTime),$.transformTime(infoData.lastUpdateTime)];
                var arrDistributorInfo = [infoData.contectPeople,infoData.phoneNum,infoData.receiveAdrress];
                // 运输商 运单号 运输状态
                var arrTransInfo = [infoData.transporter,infoData.tmsOrdNo,infoData.transportStatus,infoData.tmsSignedBy,infoData.tmsSignedPhone,$.transformTime(infoData.tmsSingedTime,"seconds"),infoData.remark];
                $storageInfo.each(function (index) {
                    $(this).val(arrInfo[index]);
                });
                $distributorInfo.each(function (index) {
                    $(this).val(arrDistributorInfo[index]);
                });
                $transpInfo.each(function (index) {
                    $(this).val(arrTransInfo[index]);
                });
                $storemark.html(infoData.remark);
                var logData = res.data.traceLog;
                logTableRender();
                $storagelog.bootstrapTable('load',logData);
            }
        },"json");

        // 出库单明细查询
        $.get( URL+"/warehouseManage/outbound/selectOutboundOrderItem",detailParams,function(res,status,xhr){
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



//渲染操作日志表格数据
    function logTableRender() {
        var logData = [{}];
        $storagelog.bootstrapTable({
            pageSize: 25,
            pageList: [60, 100, 200],
            undefinedText: '',
            locale: 'zh-CN',
            striped: true,
            data: logData,
            columns: [{
                field: 'opName',
                title: '操作人'
            }, {
                field: 'content',
                title: '操作功能'
            }, {
                field: 'opTime',
                title: '操作时间',
                formatter: function (value) {
                    return $.transformTime(value,"seconds");
                }
            }, {
                field: 'opUid',
                title: '操作备注'
            }]
        })
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
            field: 'outboundOrderItemNo',
            title: '出库明细单号'
        }, {
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'batchNo',
                title: '批次'
            },{
                field: 'productCode',
                title: '型号'
            }, {
                field: 'inventoryType',
                title: '库存类型'
            }, {
                field: 'planOutStockQuantity',
                title: '计划出库数量'
            }, {
                field: 'actualOutStockQuantity',
                title: '实际出库数量'
            }, {
                field: 'differentOutStockQuantity',
                title: '差异数量'
            }]
        })
    }
});