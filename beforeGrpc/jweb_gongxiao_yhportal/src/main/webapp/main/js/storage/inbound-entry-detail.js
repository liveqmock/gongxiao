$(function () {

    var URL = $.getfenxiaoURL();

    //明细信息表格
    var $yhtable = $("#yhtable")

    var getProjectIdFromUrl = $.getUrlParam("projectId");
    var getInventoryNumFromUrl = $.getUrlParam("gongxiaoInboundOrderNo");
    var projectName = $.getUrlParam("projectName");
    var getWmsInboundOrderNoFromUrl = $.getUrlParam("wmsInboundOrderNo");
    $("#projecttext").val(projectName);
    var params = {
        projectId:getProjectIdFromUrl,
        //入库单号
        gongxiaoInboundOrderNo:getInventoryNumFromUrl,
        wmsInboundOrderNo: getWmsInboundOrderNoFromUrl
    };

    function getEntryDetailInfo(){
        // 基本信息
        var $storageInfo = $("#storageinfo");

        // 入库单基本信息查询
        $.get( URL + "/warehouseManage/inboundNotify/inboundOrder/selectByOrderNo",params,function(res){
            if(res.returnCode === 0){
                var infoData = res.data;
                // 基本信息
                var arrData = [infoData.gongxiaoInboundOrderNo,infoData.purchaseOrderNo,infoData.inboundType,infoData.wmsInboundOrderNo,infoData.purchaseOrderNo,infoData.supplier,infoData.brandName,infoData.easInboundOrderNo,$.transformTime(infoData.createTime,"seconds"),infoData.warehouseName,infoData.batchNo,$.transformTime(infoData.createTime,"seconds"),$.transformTime(infoData.createTime,"seconds"),infoData.warehouseName];
                var $arrInputs = $storageInfo.find("input");
                $arrInputs.each(function (index) {
                    $(this).val(arrData[index]);
                });

            }
        },"json");

        // 入库通知单明细查询
        $.get( URL+"/warehouseManage/inboundNotify/inboundOrderItem/select",params,function(res,status,xhr){
            if(res.returnCode == 0){
                renderTable();
                var storageDetailData = res.data;
                $yhtable.bootstrapTable('load',storageDetailData);
            }
        },"json");
    }


    getEntryDetailInfo();


    //渲染表格数据
    function renderTable() {
        var storageDetailData = [{}];
        $yhtable.bootstrapTable({
            pageSize: 25,
            pageList: [60, 100, 200],
            undefinedText: '',
            align: 'left',
            halign: 'left',
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
                field: 'inventoryType',
                title: '品质',
                formatter: function (value,row) {
                    if (row.inventoryType === 1){
                        return "普通好机";
                    }else if (row.inventoryType === 101){
                        return "残次";
                    }else if (row.inventoryType === 102){
                        return "机损";
                    }else if (row.inventoryType === 103){
                        return "箱损";
                    }else if (row.inventoryType === 201){
                        return "冻结库存";
                    }else if (row.inventoryType === 301){
                        return "在途库存";
                    }else {
                        return "不详";
                    }
                }
            }, {
                field: 'inStockQuantity',
                title: '入库数量'
            }]
        })
    };


});