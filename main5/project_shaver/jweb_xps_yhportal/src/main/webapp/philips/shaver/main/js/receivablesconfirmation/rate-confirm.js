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
        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
            .then(function () {
                prepaidTableRender();
            });

        // 触发查询事件
        $("#yhquery-btn").click(function(){
            $("#yhtable").bootstrapTable("refreshOptions",{pageNumber:1});
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
            var grossProfitStatus= confirmStatus;
            var purchaseOrderNo=$("#salesno").val();
            var salesOrderNo=$("#orderno").val();
            var dateStart=$("#orderstarttime").val();
            var dateEnd=$("#orderendtime").val();
            var flowNo=$("#serialnumber").val();
            // 返利流水 待定
            var couponoUrl = URL+ "/yhglobal/grossProfit/export?projectId="+projectId+"&grossProfitStatus="+grossProfitStatus+"&purchaseOrderNo="
                +purchaseOrderNo+"&salesOrderNo="+salesOrderNo+"&dateStart="+dateStart+"&dateEnd"+dateEnd+"&flowNo"+flowNo;
            window.open(couponoUrl);
        });

        // 毛利确认
        $("#prepaid-btn").click(function () {
           var selectedData = $("#yhtable").bootstrapTable('getSelections');
           var $projectList = $("#projectlist");
           var projectId = $projectList.val();
           var projectName = $projectList.children("option:selected").text();
           var arrReceiveId = "";
           $.each(selectedData,function (index,receiveItem) {
               if(arrReceiveId.length !== 0){
                   arrReceiveId += "," ;
               }
               arrReceiveId +=  receiveItem["grossProfitId"];
           });
           if(selectedData.length !== 0){
                window.location.href = './rate-confirmlist.html?ids='+ encodeURIComponent(arrReceiveId)+'&projectId='+projectId+'&projectName='+projectName;
           }
        })



    }
    function couponTotal(URL){
        var selectedData = $("#yhtable").bootstrapTable('getSelections');
        var $projectList = $("#projectlist");
        var arrReceiveId = "";
        $.each(selectedData,function (index,receiveItem) {
            if(arrReceiveId.length !== 0){
                arrReceiveId += "," ;
            }
            arrReceiveId +=  receiveItem["grossProfitId"];
        });
        var parms = {
            ids:arrReceiveId,
            projectId:$projectList.val()
        };
        $.post(
            URL + "/yhglobal/grossProfit/getTotal",
            parms,
            function (data) {
                if(data.returnCode === 0){
                    $("#receive-amount-total").html(data.data.estimatedGrossProfitAmount);
                    $("#to-receive-amount-total").html(data.data.toBeConfirmAmount);
                    $("#confirm-amount-total").html(data.data.confirmedAmount);
                    $("#receipt-amount-total").html(data.data.receivedAmount);
                }
            },
            "json"
        );
    }

    function unCheckHidden(row) {

        // 根据取消勾选的行的ID 把所有与其相同ID的行状态修改
        var $yhTable = $("#yhtable");
        var selectedDataAll = $yhTable.bootstrapTable('getData');
        // 把符合条件的对象合并成数组 再次调用合并数据的方法
        for (var i in selectedDataAll){
            if (selectedDataAll[i].grossProfitId == row.grossProfitId){
                $yhTable.bootstrapTable("updateCell",{
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
            var fieldList=["selfcheckbox","confirmStatusStr","salesOrderNo","purchaseOrderNo","estimatedGrossProfitAmount","toBeConfirmAmount"];
            $.mergeCells(confirmLoadData, "grossProfitId", 1, $yhTable,fieldList);
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

        // var couponType = "";
        // var $arrCheckboxsType = $('#form-input').find("input[type='checkbox'][name='couponType']");
        // $arrCheckboxsType.each(function () {
        //     if($(this).is(':checked')){
        //         if(couponType.length !== 0){
        //             couponType += "," ;
        //         }
        //         couponType += $(this).val();
        //     }
        // });
        return {
            projectId: $projectList.val(),
            grossProfitStatus: confirmStatus,
            salesOrderNo:$("#orderno").val(),
            purchaseOrderNo:$("#salesno").val(),
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
        var $prepaidBtn = $("#prepaid-btn");

        function BtnStatus() {
            var tableSelectedData = $yhTable.bootstrapTable("getSelections");
            var statusFlag = {},
                count = 0,
                falseCount = 0;

            $.each(tableSelectedData, function (index, item) {
                if(item['confirmStatus'] === 3){
                    statusFlag[item['grossProfitId']] = false;
                }else if(item['confirmStatus'] === 1 || item['confirmStatus'] === 2){
                    statusFlag[item['grossProfitId']] = true;
                }
            });

            for(var salesOrderStatus in statusFlag){
                if(statusFlag[salesOrderStatus]){
                    count++
                }else if(!statusFlag[salesOrderStatus]){
                    falseCount++
                }
            }

            if(count && tableSelectedData.length !== 0){
                $prepaidBtn.removeClass("yh-handle-btn").removeAttr("disabled");
            }else{
                $prepaidBtn.addClass("yh-handle-btn").attr("disabled", true);
            }
        }

        $yhTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/yhglobal/grossProfit/selectByManyConditions",
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
                var fieldList=["selfcheckbox","confirmStatusStr","salesOrderNo","purchaseOrderNo","estimatedGrossProfitAmount","toBeConfirmAmount"];
                $.mergeCells(confirmLoadData, "grossProfitId", 1, $yhTable,fieldList);
            },
            columns: [{
                checkbox: true,
                field: 'selfcheckbox'
            },{
                field: 'confirmStatusStr',
                title: '确认状态'
            }, {
                field: 'salesOrderNo',
                title: '单号'
            },{
                field: 'purchaseOrderNo',
                title: '品牌商订单号'
            },{
                field: 'estimatedGrossProfitAmount',
                title: '应收金额'
            },{
                field: 'toBeConfirmAmount',
                title: '未收金额'
            }, {
                field: 'confirmedAmount',
                title: '确认金额'
            }, {
                field: 'receivedAmount',
                title: '使用金额'
            },{
                field: 'useDateStr',
                title: '转入日期'
            }, {
                field: 'ywCreateTime',
                title: '确认时间'
            }, {
                field: 'flowNo',
                title: '确认流水'
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
                $prepaidBtn.addClass("yh-handle-btn").attr("disabled", true);
                couponTotal(URL);
            }
        })
    }
});