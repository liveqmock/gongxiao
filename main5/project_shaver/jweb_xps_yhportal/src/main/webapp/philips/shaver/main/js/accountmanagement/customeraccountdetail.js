/**
 * @authors {len} (http://csshack.org)
 * @date    2018/5/7
 */
$(function () {
    init();

    function init() {
        var $pageElement = getPageElement();
        var $maskElement = getMaskElement();
        var urlParams = getUrlParams();
        $("#projectid").val(urlParams.projectName);

        var URL = $.getfenxiaoURL();
        getCouponAndPrepaidAmount(URL);
        renderTable(URL);

        renderElement();
        // 点击转出按钮触发事件
        function accountBalance(el, maskType) {
            $maskElement.$yhmask.removeClass("yhmask-hide");
            $maskElement.$yhmask.attr("transfer-type", maskType);
        }

        // 返利转入金额
        $pageElement.$rebateBtn.click(function () {
            accountBalance($pageElement.$rebateInput, "rebate");
        });

        // 代垫转入金额
        $pageElement.$prepaidBtn.click(function () {
            accountBalance($maskElement.$intoAmount, "prepaid");
        });

        // 收入支出统计信息
        $pageElement.$addupamountBtn.click(function () {
            var totalParams = getQueryParams();
            var accountType = $("#accounttype").val();

            function renderTotalInfo(url) {
                $.get(url, totalParams, function (res) {
                    if (res.returnCode === 0) {
                        var resData = res.data;
                        var payInfo = "已支出" + resData.expenditureQuantity + "笔" + "，共" + resData.expenditureAmount + "元";
                        var incomeInfo = "已收入" + resData.incomeQuantity + "笔" + "，共" + resData.incomeAmount + "元";
                        $pageElement.$addupInfo.removeClass("addup-infohide").find("li").html(incomeInfo).end().find("li:first-child").html(payInfo);
                    }
                }, "json");
            }

            if (accountType == 1) {
                // 现金小计
                renderTotalInfo(URL + "/distributor/cash/subtotal");
            } else if (accountType == 2) {
                // 返利小计
                renderTotalInfo(URL + "/distributor/coupon/subtotal");
            } else if (accountType == 3) {
                // 代垫小计
                renderTotalInfo(URL + "/distributor/prepaid/subtotal");
            }
        });

        // 确认转入金额
        $maskElement.$yhmaskSubmit.click(function () {
            var maskInputValue = $maskElement.$intoAmount.val();
            var maskTransfreType = $maskElement.$yhmask.attr("transfer-type");
            var urlParams = getUrlParams();
            var postParams = {
                distributorId: urlParams.distributorId,
                remark: $("#mask-remark").val(),
                receivedAmount: maskInputValue,
                projectId: $.getUrlParam("projectId")
            };
            if (maskTransfreType === "rebate") {
                $.get(URL + "/distributor/coupon/transfer", postParams, function (res) {
                    if (res.returnCode === 0) {
                        getCouponAndPrepaidAmount(URL);
                        $pageElement.refreshTable();
                    }else {
                        $.renderLayerMessage(res.message);
                    }
                }, "json");
            } else if (maskTransfreType === "prepaid") {
                $.get(URL + "/distributor/prepaid/transfer", postParams, function (res) {
                    if (res.returnCode === 0) {
                        getCouponAndPrepaidAmount(URL);
                        $pageElement.refreshTable();
                    }
                }, "json");
            } else {
                return;
            }
            $maskElement.$yhmask.addClass("yhmask-hide");
        });

        // 查询流水
        $pageElement.$queryBtn.click(function () {
            var accountType = $("#accounttype").val();
            if (accountType === "1") {
                // 现金
                var flowUrl = URL + "/distributor/cash/flowList";
                $pageElement.$yhTable.bootstrapTable("refresh", {url: flowUrl})
            } else if (accountType === "2") {
                // 返利
                var couponoUrl = URL + "/distributor/coupon/flowList";
                $pageElement.$yhTable.bootstrapTable("refresh", {url: couponoUrl})
            } else if (accountType === "3") {
                // 代垫
                var prepaidUrl = URL + "/distributor/prepaid/flowList";
                $pageElement.$yhTable.bootstrapTable("refresh", {url: prepaidUrl})
            }
        });

        // 查询流水导出
        $pageElement.$exportBtn.click(function () {
            var accountType = $("#accounttype").val();
            if (accountType === "1") {
                // 现金
                var flowUrl = URL + "/distributor/cash/flowList";

            } else if (accountType === "2") {
                    var projectId= urlParams.projectId;
                    // 经销商ID
                    var distributorId= urlParams.distributorId;
                    // 资金流向
                    var moneyFlow= $("#moneyflow").val();
                    // 开始时间
                    var beginDateString= $("#starttime").val();
                    // 结束时间
                    var endDateString= $("#endtime").val();
                // 返利
                var couponoUrl = URL + "/distributor/coupon/export?projectId="+projectId+"&distributorId="+distributorId+"&moneyFlow="+moneyFlow+
                "&beginDateString="+beginDateString+"&endDateString="+endDateString;
                window.open(couponoUrl);
            } else if (accountType === "3") {
                // 代垫
                var prepaidUrl = URL + "/distributor/prepaid/flowList";
                $pageElement.$yhTable.bootstrapTable("refresh", {url: prepaidUrl})
            }
        })
    }


    function renderElement(){

        $("#accounttype").select2({
            minimumResultsForSearch: 6
        });

        $("#moneyflow").select2({
            minimumResultsForSearch: 6
        });

        $.renderDateRange('yhstart-time','yhend-time');
    }



    function getUrlParams() {
        var projectId = $.getUrlParam("projectId");
        var projectName = $.getUrlParam("projectName");
        var distributorId = $.getUrlParam("distributorId");
        return {
            projectId: projectId,
            projectName: projectName,
            distributorId: distributorId
        }
    }

    function getCouponAndPrepaidAmount(URL) {
        var urlParams = getUrlParams();
        var $pageElement = getPageElement();
        $.get(URL + "/distributor/coupon/accountAmount", {
            projectId: urlParams.projectId,
            distributorId: urlParams.distributorId
        }, function (res) {
            if (res.returnCode === 0) {
                var resData = res.data;
                $pageElement.$cashInput.val(resData.cashAmountDoubleStr);
                $pageElement.$rebateInput.val(resData.couponAmountDoubleStr);
                $pageElement.$prepaidInput.val(resData.prepaidAmountDoubleStr);
            }
        }, "json")
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
        var urlParams = getUrlParams();
        if (params) {
            return {
                projectId: urlParams.projectId,
                // 经销商ID
                distributorId: urlParams.distributorId,
                // 账户类型
                accountType: $("#accounttype").val(),
                // 资金流向
                moneyFlow: $("#moneyflow").val(),
                // 开始时间
                beginDateString: $("#starttime").val(),
                // 结束时间
                endDateString: $("#endtime").val(),
                // 首页页码
                pageNumber: params.pageNumber,
                // 数据条数
                pageSize: params.pageSize
            }
        } else {
            return {
                projectId: urlParams.projectId,
                // 经销商ID
                distributorId: urlParams.distributorId,
                // 账户类型
                accountType: $("#accounttype").val(),
                // 资金流向
                moneyFlow: $("#moneyflow").val(),
                // 开始时间
                beginDateString: $("#starttime").val(),
                // 结束时间
                endDateString: $("#endtime").val()
            }
        }

    }

    // 基本信息表格
    function renderTable(URL) {
        var $yhTable = $("#yhtable");
        $yhTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/distributor/cash/flowList",
            queryParamsType: '',
            striped: true,
            silent: true,
            undefinedText: '',
            dataField: 'data',
            pagination: true,
            pageSize: 60, // 一页显示数据条数
            pageList: [60, 100, 200],
            pageNumber: 1, // 首页页码
            sidePagination: "server",
            queryParams: getQueryParams,
            responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法
            columns: [
                {
                    field: 'createTime',
                    title: '时间'
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
                    title: '交易金额'
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

    function getPageElement() {
        // 账户类型
        var $accountType = $("#accounttype");
        // 资金流向
        var $moneyFlow = $("#moneyflow");
        // 开始时间
        var $startTime = $("#starttime");
        // 结束时间
        var $endTime = $("#endtime");
        // 查询按钮
        var $queryBtn = $("#yhquery-btn");
        // 现金账户可用金额
        var $cashInput = $("#cash-input");
        // 返利转入按钮
        var $rebateBtn = $("#rebate-btn");
        // 代垫转入按钮
        var $prepaidBtn = $("#prepaid-btn");
        // 返利转入金额
        var $rebateInput = $("#rebate-input");
        // 代垫转入金额
        var $prepaidInput = $("#prepaid-input");
        var $addupamountBtn = $("#addupamount-btn");
        // 统计信息
        var $addupInfo = $("#addup-info");
        // 导出按钮
        var $exportBtn = $("#export-btn");
        // 表格
        var $yhTable = $("#yhtable");
        return {
            $accountType: $accountType,
            $moneyFlow: $moneyFlow,
            $startTime: $startTime,
            $endTime: $endTime,
            $queryBtn: $queryBtn,
            $rebateBtn: $rebateBtn,
            $prepaidBtn: $prepaidBtn,
            $rebateInput: $rebateInput,
            $prepaidInput: $prepaidInput,
            $exportBtn: $exportBtn,
            $addupamountBtn: $addupamountBtn,
            $addupInfo: $addupInfo,
            $yhTable: $yhTable,
            $cashInput: $cashInput,
            refreshTable: function () {
                $yhTable.bootstrapTable("refreshOptions", {pageNumber: 1});
            }
        }
    }

    function getMaskElement() {
        var $yhmask = $("#yhmask");
        // 金额类型
        var $cnyType = $("#cnytype");
        // 转入金额输入框
        var $intoAmount = $("#intoamount");
        // 备注
        var $maskRemark = $("#mask-remark");
        // 确认按钮
        var $yhmaskSubmit = $("#yhmask-submit");
        return {
            $yhmask: $yhmask,
            $cnyType: $cnyType,
            $intoAmount: $intoAmount,
            $maskRemark: $maskRemark,
            $yhmaskSubmit: $yhmaskSubmit
        }
    }

});