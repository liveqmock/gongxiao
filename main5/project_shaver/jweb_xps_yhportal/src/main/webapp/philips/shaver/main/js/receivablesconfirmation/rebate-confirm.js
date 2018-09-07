/**
 * @authors {len} (http://csshack.org)
 * @date    2018/4/24
 */


$(function () {

    var URL = $.getfenxiaoURL();

    confirmInit();

    // layui时间控件渲染
    function renderPageEle(){
        $.renderDateRange('orderstarttime','orderendtime');
    }

    function getPageElement() {
        return {
            $yhTable: $("#yhtable"),
            $prepaidBtn: $("#prepaid-btn"),
            $projectList: $("#projectlist")
        };
    }

    function confirmInit() {
        renderPageEle();
        var $pageElement = getPageElement();
        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
            .then(function () {
                prepaidTableRender();
            });

        // 触发查询事件
        $("#yhquery-btn").click(function(){
            $pageElement.$yhTable.bootstrapTable("refreshOptions",{pageNumber:1});
        });

        // 把查询结果导出
        $("#export-btn").click(function(){
            var $projectList = $("#projectlist");
            var confirmStatus = "";
            var $arrCheckboxs = $('#form-input').find("input[type='checkbox'][name='receiveStatus']");
            $arrCheckboxs.each(function () {
                if($(this).is(':checked')){
                    if(confirmStatus.length !== 0){
                        confirmStatus += "," ;
                    }
                    confirmStatus += $(this).val();
                }
            });
            var projectId= $projectList.val();
            var couponStatus= confirmStatus;
            var purchaseOrderNo=$("#orderno").val();
            var supplierOrderNo=$("#salesno").val();
            var dateStart=$("#orderstarttime").val();
            var dateEnd=$("#orderendtime").val();
            var flowNo=$("#serialnumber").val();
            // 返利流水
            var couponoUrl = URL+ "/yhglobal/coupon/export?projectId="+projectId+"&couponStatus="+couponStatus+"&purchaseOrderNo="
                +purchaseOrderNo+"&supplierOrderNo="+supplierOrderNo+"&dateStart="+dateStart+"&dateEnd"+dateEnd+"&flowNo"+flowNo;
            window.open(couponoUrl);
        });

        // 返利确认
        $pageElement.$prepaidBtn.click(function () {
           var selectedData = $pageElement.$yhTable.bootstrapTable('getSelections');
           var $projectList = $("#projectlist");
           var projectId = $projectList.val();
           var projectName = $projectList.children("option:selected").text();
           var arrReceiveId = "";
           $.each(selectedData,function (index,receiveItem) {
               if(arrReceiveId.length !== 0){
                   arrReceiveId += "," ;
               }
               arrReceiveId +=  receiveItem["purchaseCouponLedgerId"];
           });
           if(selectedData.length !== 0){
                window.location.href = './rebate-confirmlist.html?ids='+ encodeURIComponent(arrReceiveId)+'&projectId='+projectId+'&projectName='+projectName;
           }
        })



    }
    function couponTotal(URL){
        var $pageElement = getPageElement();
        var selectedData = $pageElement.$yhTable.bootstrapTable('getSelections');
        var $projectList = $("#projectlist");
        var arrReceiveId = "";
        $.each(selectedData,function (index,receiveItem) {
            if(arrReceiveId.length !== 0){
                arrReceiveId += "," ;
            }
            arrReceiveId +=  receiveItem["purchaseCouponLedgerId"];
        });
        var parms = {
            ids:arrReceiveId,
            projectId:$projectList.val()
        };
        $.post(
            URL + "/yhglobal/coupon/getTotal",
            parms,
            function (data) {
                if(data.returnCode === 0){
                    $("#receive-amount-total").html(data.data.estimatedAmountDoubleStr);
                    $("#to-receive-amount-total").html(data.data.toBeConfirmAmountDoubleStr);
                    $("#confirm-amount-total").html(data.data.confirmAmountDoubleStr);
                    $("#receipt-amount-total").html(data.data.receiptAmountDoubleStr);
                }
            },
            "json"
        );
    }

    function unCheckHidden(row) {

        // 根据取消勾选的行的ID 把所有与其相同ID的行状态修改
        var $pageElement = getPageElement();
        var selectedDataAll = $pageElement.$yhTable.bootstrapTable('getData');
        // 把符合条件的对象合并成数组 再次调用合并数据的方法
        for (var i in selectedDataAll){
            if (selectedDataAll[i].purchaseCouponLedgerId == row.purchaseCouponLedgerId){
                var $pageElement = getPageElement();
                $pageElement.$yhTable.bootstrapTable("updateCell",{
                    index: i,
                    field: 'selfcheckbox',
                    value: false
                });
            }
        }
        couponTotal(URL);
        union(selectedDataAll);
    }
    // 把数据再次合并
    function union(selectedDataAll) {
            var confirmLoadData = selectedDataAll;
            var $yhTable = $("#yhtable");
            var fieldList=["selfcheckbox","couponStatusString","purchaseOrderNo","supplierOrderNo","ylCreateTime","couponTypeString","ylCurrencyCode",
                "estimatedCouponAmountStr","toBeConfirmAmountStr"];
            $.mergeCells(confirmLoadData, "purchaseCouponLedgerId", 1, $yhTable,fieldList);
    }


    // 获取表格需要查询的参数
    function getQueryParams(params) {
        var $projectList = $("#projectlist");
        var confirmStatus = "";
        var $arrCheckboxs = $('#form-input').find("input[type='checkbox'][name='receiveStatus']");
        $arrCheckboxs.each(function () {
            if($(this).is(':checked')){
                if(confirmStatus.length !== 0){
                    confirmStatus += "," ;
                }
                confirmStatus += $(this).val();
            }
        });

        var couponType = "";
        var $arrCheckboxsType = $('#form-input').find("input[type='checkbox'][name='couponType']");
        $arrCheckboxsType.each(function () {
            if($(this).is(':checked')){
                if(couponType.length !== 0){
                    couponType += "," ;
                }
                couponType += $(this).val();
            }
        });
        return {
            projectId: $projectList.val(),
            couponStatus: confirmStatus,
            couponType:couponType,
            purchaseOrderNo:$("#orderno").val(),
            supplierOrderNo:$("#salesno").val(),
            dateStart:$("#orderstarttime").val(),
            dateEnd:$("#orderendtime").val(),
            flowNo:$("#serialnumber").val(),
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        }
    }

    // 返回成功渲染表格
    function responseHandler(res) {
        if (res.returnCode === 0 && res.data.list.length !== 0 && res.data.total !== 0 && typeof res.data.total !== 'undefined') {
            return {
                total: res['data']['total'], // 总页数,前面的key必须为"total"
                data: res['data']['list'] // 行数据，前面的key要与之前设置的dataField的值一致.
            }
        } else {
            return {
                total: 0, // 总页数,前面的key必须为"total"
                data: [{}] // 行数据，前面的key要与之前设置的dataField的值一致.
            };
        }
    }


    function prepaidTableRender() {
        var $yhTable = $("#yhtable");
        var $pageElement = getPageElement();

        function BtnStatus() {
            var tableSelectedData = $yhTable.bootstrapTable("getSelections");
            var statusFlag = {},
                count = 0,
                falseCount = 0;

            $.each(tableSelectedData, function (index, item) {
                if(item['couponStatus'] === 3){
                    statusFlag[item['purchaseCouponLedgerId']] = false;
                }else if(item['couponStatus'] === 1 || item['couponStatus'] === 2){
                    statusFlag[item['purchaseCouponLedgerId']] = true;
                }
            });

            for(var salesOrderStatus in statusFlag){
                if(statusFlag[salesOrderStatus]){
                    count++
                }else if(!statusFlag[salesOrderStatus]){
                    falseCount++
                }
            }

            if(count && !falseCount && tableSelectedData.length !== 0){
                $pageElement.$prepaidBtn.removeClass("yh-handle-btn").removeAttr("disabled");
            }else{
                $pageElement.$prepaidBtn.addClass("yh-handle-btn").attr("disabled", true);
            }
        }
        $yhTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/yhglobal/coupon/selectByManyConditions",
            queryParamsType: '',
            silent: true,
            undefinedText: '',
            striped: true,
            dataField: 'data',
            pagination: true,
            pageSize: 100, // 一页显示数据条数
            pageList: [60, 100, 200],
            pageNumber: 1, // 首页页码
            sidePagination: "server",
            queryParams: getQueryParams,
            responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法,
            onLoadSuccess: function (res) {
                var confirmLoadData = res.data;
                var fieldList=["selfcheckbox","couponStatusString","purchaseOrderNo","supplierOrderNo","ylCreateTime","couponTypeString","ylCurrencyCode",
                    "estimatedCouponAmountStr","toBeConfirmAmountStr"];
                $.mergeCells(confirmLoadData, "purchaseCouponLedgerId", 1, $yhTable,fieldList);
            },
            columns: [{
                checkbox: true,
                field: 'selfcheckbox'
            },{
                field: 'couponStatusString',
                title: '确认状态'
            }, {
                field: 'purchaseOrderNo',
                title: '单号'
            },{
                field: 'supplierOrderNo',
                title: '品牌商订单号'
            },{
                field: 'ylPurchaseTime',
                title: '生成日期'
            }, {
                field: 'couponTypeString',
                title: '返利类型'
            },{
                field: 'estimatedCouponAmountStr',
                title: '应收金额'
            },{
                field: 'toBeConfirmAmountStr',
                title: '未收金额'
            }, {
                field: 'confirmAmountStr',
                title: '确认金额'
            }, {
                field: 'receiptAmountStr',
                title: '使用金额'
            }, {
                field: 'accountTypeString',
                title: '使用账户'
            },  {
                field: 'ywCreateTime',
                title: '确认时间'
            }, {
                field: 'flowNo',
                title: '确认流水'
            },{
                field: 'philipDocumentNo',
                title: '飞利浦单据号'
            }],
            onCheck: function (rows) {
                BtnStatus();
                couponTotal(URL);
            },
            onUncheck: function (rows) {
                BtnStatus();
                unCheckHidden(rows);
            },
            onCheckAll: function () {
                BtnStatus();
                couponTotal(URL);
            },
            onUncheckAll: function () {
                $pageElement.$prepaidBtn.addClass("yh-handle-btn").attr("disabled", true);
                couponTotal(URL);
            }
        })
    }
});