/**
 * @authors {len} (http://csshack.org)
 * @date    2018/4/24
 */


$(function () {

    var URL = $.getfenxiaoURL();

    confirmInit();



    // layui时间控件渲染
    function renderPageEle(){
        layui.config({
            version: '1515376178709'
        });

        $.renderDateRange("dateStart","dateEnd");
        
        $.renderDateRange("date-start-confirm","date-end-confirm");

        $("#accountType").select2({
            minimumResultsForSearch: 6
        });
    }

    function getPageElem() {
        return {
            $prepaidConfirmTable: $("#prepaid-confirmtable"),
            $prepaidBtn: $("#prepaid-btn")
        };
    }

    // 动态渲染下拉表单
    function renderSelectList(URL) {
        // 获取project列表
        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
            .then(function () {
                prepaidTableRender(URL);
                //prepaidCount(URL);
            });
    }
    function prepaidCount(URL){
        var $pageElem = getPageElem();
        var selectedData = $pageElem.$prepaidConfirmTable.bootstrapTable('getSelections');
        var arrReceiveId = "";
        $.each(selectedData,function (index,receiveItem) {
            if(arrReceiveId.length !== 0){
                arrReceiveId += "," ;
            }
            arrReceiveId +=  receiveItem["receiveId"];
        });
        var parms = {
            projectId:$("#projectlist").val(),
            receiveIds:arrReceiveId
        };
        $.post(
            URL + "/prepaid/receive/getsMergeCount",
            parms,
            function (data) {
                $("#receiveAmount").html(data.data['receiveAmountStr']);
                $("#receiptAmount").html(data.data['receiptAmountStr']);
                $("#toBeConfirmAmount").html(data.data['toBeConfirmAmountStr']);
                $("#confirmAmount").html(data.data['confirmAmountStr']);
            },
            "json"
        );
    }

    function unCheckHidden(row) {

        // 根据取消勾选的行的ID 把所有与其相同ID的行状态修改
        var $pageElement = getPageElem();
        var selectedDataAll = $pageElement.$prepaidConfirmTable.bootstrapTable('getData');
        // 把符合条件的对象合并成数组 再次调用合并数据的方法
        for (var i in selectedDataAll){
            if (selectedDataAll[i].receiveId == row.receiveId){
                $pageElement.$prepaidConfirmTable.bootstrapTable("updateCell",{
                    index: i,
                    field: 'selfcheckbox',
                    value: false
                });
            }
        }
        prepaidCount(URL);
        union(selectedDataAll);

    }
    // 把数据再次合并
    function union(selectedDataAll) {
        var confirmLoadData = selectedDataAll;
        var $prepaidConfirmTable =$("#prepaid-confirmtable");
        // 需要合并的列字段名
        var fieldList=["selfcheckbox","receiveStatusStr","orderId","createTime","receiveAmountStr","toBeConfirmAmountStr"];
        // 用于区分单号的唯一Id
        $.mergeCells(confirmLoadData, "receiveId", 1, $prepaidConfirmTable,fieldList);
    }


    function confirmInit() {
        var URL = $.getfenxiaoURL();
        renderPageEle();

        renderSelectList(URL);
        var $pageElem = getPageElem();
        $("#yhquery-btn").click(function () {
            $pageElem.$prepaidConfirmTable.bootstrapTable("refreshOptions",{pageNumber:1});
            //prepaidCount(URL);
        })

        
        // 代垫确认(将receiveId传到确认列表页)
        $pageElem.$prepaidBtn.on('click',function () {
            var $projectList = $("#projectlist");
            var projectId = $projectList.val();
            var projectName =  $projectList.children("option:selected").text();
            var selectedData = $pageElem.$prepaidConfirmTable.bootstrapTable('getSelections');
            var arrReceiveId = "";
            $.each(selectedData,function (index,receiveItem) {
                if(arrReceiveId.length !== 0){
                    arrReceiveId += "," ;
                }
                arrReceiveId +=  receiveItem["receiveId"];
            });
            if(selectedData.length !== 0){
                window.location.href = './prepaid-confirmlist.html?receiveId='+ encodeURIComponent(arrReceiveId)+"&projectId="+projectId+"&projectName="+projectName;
            }
        })
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

    // 获取表格需要查询的参数
    function getQueryParams(params) {
        var receiveStatus = "";
        $('input[name="receiveStatus"]:checked').each(function(){
            if(receiveStatus == ""){
                receiveStatus = ""+$(this).val();
            }else{
                receiveStatus += ","+$(this).val();
            }
        });
        return {
            // 项目ID
            projectId:$("#projectlist").val(),
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize,
            orderId:$("#orderId").val(),
            flowCode:$("#flowCode").val(),
            accountType:$("#accountType").val(),
            dateStart:$("#dateStart").val(),
            dateEnd:$("#dateEnd").val(),
            dateStartConfirm:$("#date-start-confirm").val(),
            dateEndConfirm:$("#date-end-confirm").val(),
            receiveStatus:receiveStatus
        }
    }


    function prepaidTableRender(URL) {
        var $pageElem = getPageElem();
        var $prepaidConfirmTable = $("#prepaid-confirmtable");


        function BtnStatus() {
            var tableSelectedData = $prepaidConfirmTable.bootstrapTable("getSelections");
            var statusFlag = {},
                count = 0,
                falseCount = 0;

            $.each(tableSelectedData, function (index, item) {
                if(item['receiveStatus'] === 3){
                    statusFlag[item['receiveId']] = false;
                }else if(item['receiveStatus'] === 1 || item['receiveStatus'] === 2){
                    statusFlag[item['receiveId']] = true;
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
                $pageElem.$prepaidBtn.removeClass("yh-handle-btn").removeAttr("disabled");
            }else{
                $pageElem.$prepaidBtn.addClass("yh-handle-btn").attr("disabled", true);
            }
        }

        $prepaidConfirmTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/prepaid/receive/getsMergeByProjectId",
            queryParamsType: '',
            silent: true,
            undefinedText: '',
            striped: true,
            dataField: 'data',
            pagination: true,
            pageSize: 60, // 一页显示数据条数
            pageList: [60, 100, 200],
            pageNumber: 1, // 首页页码
            sidePagination: "server",
            queryParams: getQueryParams,
            responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法
            onCheck: function (rows) {
                BtnStatus();
                prepaidCount(URL);
            },
            onUncheck: function (rows) {
                BtnStatus();
                unCheckHidden(rows);
            },
            onCheckAll: function () {
                BtnStatus();
                prepaidCount(URL);
            },
            onUncheckAll: function () {
                $pageElem.$prepaidBtn.addClass("yh-handle-btn").attr("disabled", true);
                prepaidCount(URL);
            },
            onLoadSuccess: function(res){
                var confirmLoadData = res.data;
                    // 需要合并的列字段名
                    var fieldList=["selfcheckbox","receiveStatusStr","orderId","createTime","receiveAmountStr","toBeConfirmAmountStr"];
                    // 用于区分单号的唯一Id
                    $.mergeCells(confirmLoadData, "receiveId", 1, $prepaidConfirmTable,fieldList);
            },
            columns: [{
                checkbox: true,
                field: 'selfcheckbox'
            },{
                field: 'receiveStatusStr',
                title: '确认状态'
            }, {
                field: 'orderId',
                title: '单号'
            }, {
                field: 'createTime',
                title: '生成日期'
            }, {
                field: 'receiveAmountStr',
                title: '应收金额'
            }, {
                field: 'toBeConfirmAmountStr',
                title: '未收金额'
            },{
                field: 'confirmAmountStr',
                title: '确认金额',
                cellStyle: function () {
                    return {
                        css:{
                            "border-left":"1px solid #e8eaed"
                        }
                    }
                }
            },  {
                field: 'receiptAmountStr',
                title: '实收金额'
            },  {
                field: 'accountTypeStr',
                title: '使用帐户'
            }, {
                field: 'useDate',
                title: '使用日期'
            }, {
                field: 'confirmTime',
                title: '确认时间'
            }, {
                field: 'flowCode',
                title: '确认流水号'
            }]
        })
    }
});