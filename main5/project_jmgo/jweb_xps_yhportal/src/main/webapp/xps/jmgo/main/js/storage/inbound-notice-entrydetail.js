$(function(){


    var URL = $.getfenxiaoURL();

    //明细信息表格
    var $stodeTable = $("#stodetable");
    //操作日志表格
    var $storagelog = $("#storagelog");


    var getProjectIdFromUrl = $.getUrlParam("projectId");
    var getInventoryNumFromUrl = $.getUrlParam("gongxiaoInboundOrderNo");
    var projectName = $.getUrlParam("projectName");
    $("#projecttext").val(projectName);
    var params = {
        projectId:getProjectIdFromUrl,
        //入库单号
        inventoryNum:getInventoryNumFromUrl
    };

    function getEntryDetailInfo(){
        // 基本信息
        var $storageInfo = $("#storageinfo");
        // 品牌商发货信息
        var $brandInfo = $("#brandinfo");
        // 入库单基本信息查询
        $.get( URL + "/warehouseManage/inbound/inboundOrder/selectByInboundNum",params,function(res){
            if(res.returnCode === 0){
                var infoData = res.data;
                // 基本信息
                var arrData = [infoData.gongxiaoInboundOrderNo,infoData.purchaseOrderNo,infoData.inboundType,infoData.orderStatus,infoData.warehouseName,infoData.batchNo,infoData.brandName,infoData.warehouseDetailAddress,$.transformTime(infoData.lastUpdateTime,"seconds"),$.transformTime(infoData.createTime,"seconds"),$.transformTime(infoData.createTime,"seconds")];
                // 品牌商发货信息
                var brandArrData = [infoData.contactsPeople,infoData.phoneNum,infoData.deliverAddress];
                // 运输商 运单号 运输状态
                var arrTransInfo = [infoData.transporter,infoData.transportNum,infoData.transportStatus];
                var $arrInputs = $storageInfo.find("input");
                $arrInputs.each(function (index) {
                    $(this).val(arrData[index]);
                });
                var $brandInputs = $brandInfo.find("input");
                $brandInputs.each(function(index){
                    $(this).val(brandArrData[index]);
                });
                // 运输信息
                var $transinfo = $("#transinfo").find("input");
                $transinfo.each(function (index) {
                   $(this).val(arrTransInfo[index]);
                });
                // 备注
                var $storeMark = $("#storemark");
                var noteText = infoData.note;
                $storeMark.html(noteText);
                logTableRender();
                var logData = infoData.tracelog;
                $storagelog.bootstrapTable('load',logData);
            }
        },"json");

        // 入库单明细查询
        $.get( URL+"/warehouseManage/inbound/inboundOrderItem/select",params,function(res,status,xhr){
            if(res.returnCode == 0){
                renderTable();
                var storageDetailData = res.data;
                $stodeTable.bootstrapTable('load',storageDetailData);
            }
        },"json");
    }


    getEntryDetailInfo();

    
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

    //渲染表格数据
    function renderTable() {
        var storageDetailData = [{}];
        $stodeTable.bootstrapTable({
            pageSize: 25,
            pageList: [60, 100, 200],
            undefinedText: '',
            showFooter: false,
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
                field: 'planInStockQuantity',
                title: '计划入库数量'
            }, {
                field: 'actualInStockQuantity',
                title: '实际入库数量'
            }, {
                field: 'nonImperfectQuantity',
                title: '良品'
            }, {
                //后台需要重新提供字段
                field: 'imperfectQuantity',
                title: '残次品'
            }, {
                //后台需要重新提供字段
                field: 'differentQuantity',
                title: '差异数量'
            }]
        })
    }
});