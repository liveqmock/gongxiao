$(function(){


    var URL = $.getfenxiaoURL();

    //明细信息表格
    var $stodeTable = $("#stodetable");
    //操作日志表格
    var $storagelog = $("#storagelog");


    var getProjectIdFromUrl = $.getUrlParam("projectId");
    var getInventoryNumFromUrl = $.getUrlParam("allocateNo");
    var params = {
        projectId:getProjectIdFromUrl,
        allocateNo:getInventoryNumFromUrl
    };

    function getEntryDetailInfo(){
        // 基本信息
        var $storageInfo = $("#storageinfo");

        // 入库单基本信息查询
        $.get( URL + "/allocte/selectByAllocateNo",params,function(res,status,xhr){
            if(res.returnCode == 0){
                var infoData = res.data;
                // 基本信息
                var arrData = [infoData.allocateNo,infoData.projectIdOut,infoData.projectIdEnter,infoData.companyNameOut,infoData.companyNameEnter,infoData.warehouseOut,infoData.warehouseEnter,infoData.deliverAddress,infoData.receiveAddress,infoData.alloteWay,$.transformTime(infoData.createTime,"seconds"),$.transformTime(infoData.requireTime,"seconds"),$.transformTime(infoData.deadline,"seconds")];
                var $arrInputs = $storageInfo.find("input");
                $arrInputs.each(function (index) {
                    $(this).val(arrData[index]);
                });

                var $storeMark = $("#storemark");
                var noteText = infoData.note;
                $storeMark.html(noteText);
                logTableRender();
                var logData = infoData.tracelog;
                // $storagelog.bootstrapTable('load',logData);
            }
        },"json");

        // 入库单明细查询
        $.get( URL+"/allocte/selectItemByAllocateNo",params,function(res){
            if(res.returnCode == 0){
                renderTable();
                var storageDetailData = res.data;
                $stodeTable.bootstrapTable('load',storageDetailData);
            }
        },"json");
    }


    getEntryDetailInfo();

    
    //渲染表格数据
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
                title: '操作时间'
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
            align: 'left',
            halign: 'left',
            showFooter: false,
            locale: 'zh-CN',
            striped: true,
            data: storageDetailData,
            columns: [{
                field: 'productCode',
                title: '型号'
            }, {
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'inventoryQuantity',
                title: '可发库存数量'
            }, {
                field: 'alloteQuantity',
                title: '调拨数量'
            }, {
                title: '操作'
            }]
        })
    };
});