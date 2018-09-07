/**
 * @authors {len} (http://csshack.org)
 * @date    2018/5/7
 */
$(function () {
    init();

    function renderElement() {
        $("#accounttype").select2({
            minimumResultsForSearch: 6
        });
        $("#moneyflow").select2({
            minimumResultsForSearch: 6
        });

        $.renderDateRange("starttime","endtime");
    }


    function getPageElement() {
        var $projectList = $("#projectlist");
        // 账户类型
        var $accountType = $("#accounttype");
        // 资金流向
        var $moneyFlow = $("#moneyflow");
        // 开始时间
        var $startTime = $("#starttime");
        // 结束时间
        var $endTime = $("#endtime");
        // 查询按钮
        var $queryBtn = $("#proquery-btn");
        // 返利转入金额
        var $rebateInput = $("#rebate-input");
        // 代垫转入金额
        var $prepaidInput = $("#prepaid-input");
        // 采购预留折扣
        var $purchaseObligateInput = $("#purchaseobligate-input");
        // 导出按钮
        var $exportBtn = $("#export-btn");
        // 统计按钮
        var $addupAmountBtn = $("#addupamount-btn");
        // 表格
        var $entryAccountTable = $("#entryaccount-table");
        // 统计信息
        var $addupInfo = $("#addup-info");
        return {
            $projectList: $projectList,
            $accountType: $accountType,
            $moneyFlow: $moneyFlow,
            $startTime: $startTime,
            $endTime: $endTime,
            $queryBtn: $queryBtn,
            $rebateInput: $rebateInput,
            $prepaidInput: $prepaidInput,
            $purchaseObligateInput: $purchaseObligateInput,
            $exportBtn: $exportBtn,
            $entryAccountTable: $entryAccountTable,
            $addupAmountBtn: $addupAmountBtn,
            $addupInfo: $addupInfo,
        }

    }



    function init() {
        renderElement();
        var URL = $.getfenxiaoURL();
        var $pageElement = getPageElement();


        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName")
            .then(function () {
                getGatherAmount("/payment/supplier/reserve/sellHighAccount", $pageElement.$rebateInput, "totalAmountDouble");
                // 暂时注释
                // getGatherAmount("/payment/yhglobal/receivedAccount", $pageElement.$prepaidInput, "prepaidAmountDouble");
                // getGatherAmount("/payment/yhglobal/bufferAccount", $pageElement.$purchaseObligateInput, "couponAmountDouble");
                renderTable(URL);
            });


        // 收入支出统计信息
        $pageElement.$addupAmountBtn.click(function () {
            var totalParams = getTableDataParams();
            $.get(URL + "/payment/supplier/reserve/flowSubtotal", totalParams, function (res) {
                if (res.returnCode === 0) {
                    var resData = res.data;
                    var payInfo = "已支出" + resData.expenditureQuantity + "笔" + "，共" + resData.expenditureAmount + "元";
                    var incomeInfo = "已收入" + resData.incomeQuantity + "笔" + "，共" + resData.incomeAmount + "元";
                    $pageElement.$addupInfo.removeClass("addup-infohide").find("li").html(incomeInfo).end().find("li:first-child").html(payInfo);
                }
            }, "json");

        });

        // 条件查询
        $pageElement.$queryBtn.click(function () {
            $pageElement.$entryAccountTable.bootstrapTable("refreshOptions", {pageNumber: 1});
        });

        // 查询参数
        function getTableDataParams(params) {
            var projectId = $pageElement.$projectList.val();
            var accountTypeValue = $pageElement.$accountType.val();
            var moneyFlow = $pageElement.$moneyFlow.val();
            var beginDateString = $pageElement.$startTime.val();
            var endDateString = $pageElement.$endTime.val();
            if (params) {
                return {
                    accountType: accountTypeValue,
                    projectId: projectId,
                    moneyFlow: moneyFlow,
                    beginDateString: beginDateString,
                    endDateString: endDateString,
                    // 首页页码
                    pageNumber: params.pageNumber,
                    // 数据条数
                    pageSize: params.pageSize
                };
            } else {
                return {
                    accountType: accountTypeValue,
                    projectId: projectId,
                    moneyFlow: moneyFlow,
                    beginDateString: beginDateString,
                    endDateString: endDateString,
                };
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

        // 得到账户余额
        function getGatherAmount(url, el, amountValue) {
            var projectId = $pageElement.$projectList.val();
            var amountParams = {
                projectId: projectId
            };
            $.get(URL + url, amountParams, function (res) {
                if (res.returnCode === 0) {
                    var resValue = res.data[amountValue];
                    el.val(resValue);
                }
            }, "json")
        }

        function renderTable() {
            $pageElement.$entryAccountTable.bootstrapTable({
                contentType: "application/x-www-form-urlencoded",
                url: URL + "/payment/supplier/reserve/flowList",
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
                queryParams: getTableDataParams,
                responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法
                columns: [
                    {
                        field: 'createTime',
                        title: '时间',
                        formatter: function (value) {
                            return $.transformTime(value, "seconds");
                        }
                    }, {
                        field: 'type',
                        title: '支付方式',
                        formatter: function (value) {
                            if (value === 305) {
                                return "支出"
                            } else if (value === 306) {
                                return "收入"
                            } else {
                                return ""
                            }
                        }
                    }, {
                        field: 'currencyCode',
                        title: '币种'
                    }, {
                        field: 'transactionAmountStr',
                        title: '交易金额',
                    }, {
                        field: 'amountAfterTransactionStr',
                        title: '交易后账户余额'
                    },{
                        field:"createByName",
                        title:"操作人"
                    }, {
                        field: 'remark',
                        title: '备注'
                    }]
            })
        }
    }
});

