/**
 * @authors {len} (http://csshack.org)
 * @date    2018/4/24
 */


$(function () {

    confirmInit();

    function confirmInit() {
        var URL = $.getfenxiaoURL();
        renderPageEle();
        var $pageElem = getPageElem();

        projectAccountAmount(URL);

        //点击查询按钮，执行查询
        $pageElem.$salesQueryBtn.click(function () {
            $pageElem.$yhTable.bootstrapTable("refreshOptions", {pageNumber: 1});
        });

        $pageElem.$prepaidBtn.click(function () {
            var selectedData = $pageElem.$yhTable.bootstrapTable('getSelections');
            var arrReceiveId = "";
            $.each(selectedData, function (index, receiveItem) {
                if (arrReceiveId.length !== 0) {
                    arrReceiveId += ",";
                }
                arrReceiveId += receiveItem["salesOrderNo"];
            });

            if (selectedData.length !== 0) {
                window.location.href = './cash-confirmlist.html?salesOrderNoList=' + encodeURIComponent(arrReceiveId);
            } else {
                $.renderLayerMessage("请勾选！")
            }
        })
    }

    function projectAccountAmount(URL) {
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName")
            .then(function () {
                prepaidTableRender(URL);
            });
    }


    // layui时间控件渲染
    function renderPageEle() {
        $("#bankName").select2({
            minimumResultsForSearch: 6
        });
        $.renderDateRange("orderstarttime","confirmstarttime");

        $.renderDateRange("orderendtime","confirmendtime");
    }

    function getPageElem() {
        var $projectList = $("#projectlist");
        //订单起始时间
        var $orderStartTime = $("#orderstarttime");
        //订单截止时间
        var $orderEndTime = $("#orderendtime");
        //销售单号
        var $salesNo = $("#salesno");
        //收款银行
        var $bankName = $("#bankName");
        //客户名称
        var $switchNumber = $("#switchnumber");
        //确认起始时间
        var $confirmStartTime = $("#confirmstarttime");
        //确认截止时间
        var $confirmEndTime = $("#confirmendtime");
        //查询按钮
        var $salesQueryBtn = $("#salesquery-btn");

        return {
            $yhTable: $("#yhtable"),
            $prepaidBtn: $("#prepaid-btn"),
            $projectList: $projectList,
            $orderStartTime: $orderStartTime,
            $orderEndTime: $orderEndTime,
            $salesNo: $salesNo,
            $bankName: $bankName,
            $switchNumber: $switchNumber,
            $confirmStartTime: $confirmStartTime,
            $confirmEndTime: $confirmEndTime,
            $salesQueryBtn: $salesQueryBtn
        };
    }

    function couponTotal(){
        var $yhTable = $("#yhtable");
        // 被选中数据
        var selectedData = $yhTable.bootstrapTable('getSelections');
        var tableAllData =  $yhTable.bootstrapTable('getData');

        var _shouldReceiveAmountDouble = 0,
            _unreceiveAmountDouble = 0,
            _confirmAmountDouble = 0,
            _recipientAmountDouble = 0;


        var arrSalesOrderNo = [];

        function judgeArr(arr,str){
            var flag = null;
            $.each(arr,function (index,arrItem) {
                if(arrItem === str){
                    flag = true;
                    return flag;
                }else{
                    flag = false;
                }
            });
            return flag;
        }


        $.each(selectedData,function (index,salesItem) {
            var  boolenFlag = judgeArr(arrSalesOrderNo,salesItem["salesOrderNo"]);
            if(!boolenFlag){
                arrSalesOrderNo.push(salesItem["salesOrderNo"]);
            }
        });

        $.each(arrSalesOrderNo,function (index,arrSalesOrderNoItem) {
            var i = 0;
            $.each(tableAllData,function (index,tableAllDataItem) {
                if(arrSalesOrderNoItem === tableAllDataItem["salesOrderNo"]){
                    if(i<1){
                        _shouldReceiveAmountDouble += Number(tableAllDataItem["shouldReceiveAmountDouble"]);
                        _unreceiveAmountDouble += Number(tableAllDataItem["unreceiveAmountDouble"]);
                    }
                    _confirmAmountDouble += Number(tableAllDataItem["confirmAmountDouble"]);
                    _recipientAmountDouble += Number(tableAllDataItem["recipientAmountDouble"]);
                    i++;
                }
            })
        });

        $("#to-receive-amount-total").html(_unreceiveAmountDouble.toFixed(2));
        $("#receive-amount-total").html(_shouldReceiveAmountDouble.toFixed(2));
        $("#confirm-amount-total").html(_confirmAmountDouble.toFixed(2));
        $("#receipt-amount-total").html(_recipientAmountDouble.toFixed(2));
    }



    function unCheckHidden(row) {
        var $yhTable = $("#yhtable");
        // 根据取消勾选的行的ID 把所有与其相同ID的行状态修改
        var selectedDataAll = $yhTable.bootstrapTable('getData');
        // 把符合条件的对象合并成数组 再次调用合并数据的方法
        for (var i in selectedDataAll){
            if (selectedDataAll[i]["salesOrderNo"] == row["salesOrderNo"]){
                $yhTable.bootstrapTable("updateCell",{
                    index: i,
                    field: 'selfcheckbox',
                    value: false
                });
            }
        }
        couponTotal();
        union(selectedDataAll);
    }

    // 把数据再次合并
    function union(selectedDataAll) {
        var confirmLoadData = selectedDataAll;
        var $yhTable = $("#yhtable");
        // 需要合并的列字段名
        var fieldList = ["selfcheckbox", "confirmId", "confirmStatus", "recipientStatus", "salesOrderNo", "distributorName", "shouldReceiveCurrency", "shouldReceiveAmountDouble", "unreceiveCurrency", "unreceiveAmountDouble"];
        $.mergeCells(confirmLoadData, "salesOrderNo", 0, $yhTable, fieldList);
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
    function getQueryParams(params) {
        var $pageElem = getPageElem();
        return {
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize,
            projectId: $pageElem.$projectList.val(),
            salesOrderNo: $pageElem.$salesNo.val(),
            distributorName: $pageElem.$switchNumber.val(),
            bankName: $pageElem.$bankName.val(),
            confirmBegin: $pageElem.$confirmStartTime.val(),
            confirmEnd: $pageElem.$confirmEndTime.val(),
            orderBegin: $pageElem.$orderStartTime.val(),
            orderEnd: $pageElem.$orderEndTime.val()

        }
    }

    function prepaidTableRender(URL) {
        var $yhTable = $("#yhtable");
        $yhTable.bootstrapTable({
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/payment/cashConfirm/list",
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
            onLoadSuccess: function (res) {
                var confirmLoadData = res.data;
                var fieldList = ["selfcheckbox", "confirmId", "confirmStatus", "recipientStatus", "salesOrderNo", "distributorName", "shouldReceiveCurrency", "shouldReceiveAmountDouble", "unreceiveCurrency", "unreceiveAmountDouble"];
                $.mergeCells(confirmLoadData, "salesOrderNo", 0, $yhTable, fieldList);
            },
            columns: [{
                checkbox: true,
                field: 'selfcheckbox'
            }, {
                field: 'confirmId',
                title: 'id',
                visible: false
            }, {
                field: 'confirmStatus',
                title: '确认状态',
                formatter: function (value) {
                    if (value === 1) {
                        return "未确认"
                    } else if (value === 2) {
                        return "部分确认"
                    } else if (value === 3) {
                        return "已确认"
                    }
                }
            }, {
                field: 'recipientStatus',
                title: '收款状态',
                formatter: function (value) {
                    if (value === true) {
                        return "已收款"
                    } else if (value === false) {
                        return "未收款"
                    } else {
                        return ""
                    }
                }
            }, {
                field: 'salesOrderNo',
                title: '销售单号'
            }, {
                field: 'distributorName',
                title: '客户名称'
            },
                //     {
                //     field: 'shouldReceiveCurrency',
                //     title: '应收货币',
                //     align: 'center',
                //     valign: 'middle'
                // },
                {
                    field: 'shouldReceiveAmountDouble',
                    title: '应收金额'
                },
                //     {
                //     field: 'unreceiveCurrency',
                //     title: '未收货币',
                //     align: 'center',
                //     valign: 'middle'
                // },
                {
                    field: 'unreceiveAmountDouble',
                    title: '未收金额'
                },
                //     {
                //     field: 'confirmCurrency',
                //     title: '确认收款币种',
                //     align: 'center',
                //     valign: 'middle'
                // },
                {
                    field: 'confirmAmountDouble',
                    title: '确认收款金额'
                },
                //     {
                //     field: 'recipientCurrency',
                //     title: '实收币种',
                //     align: 'center',
                //     valign: 'middle'
                // },
                {
                    field: 'recipientAmountDouble',
                    title: '实收金额',
                },
                {
                    title: '收款日期',
                    field: 'receiveTime',
                    formatter: function (value) {
                        return $.transformTime(value);
                    }
                },
                {
                    title: '收款银行流水号',
                    field: 'bankFlowNo'
                },
                {
                    title: '确认时间',
                    field: 'confirmTime',
                    formatter: function (value) {
                        return $.transformTime(value, 'seconds');
                    }
                }, {
                    field: 'flowNo',
                    title: '确认流水号'
                }],
                onCheck: function (rows) {
                    couponTotal();
                },
                onUncheck: function (rows) {
                    unCheckHidden(rows);
                },
                onCheckAll: function (rows) {
                   couponTotal();
                },
                onUncheckAll: function (rows) {
                   couponTotal();
                }
        })
    }
});