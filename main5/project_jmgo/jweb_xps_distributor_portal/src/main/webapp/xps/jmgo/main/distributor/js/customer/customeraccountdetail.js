/**
 * @authors {len} (http://csshack.org)
 * @date    2018/5/7
 */
$(function () {
    init();

    function init() {
        var $pageElement = getPageElement();
        var $maskElement = getMaskElement();
        var URL = $.getfenxiaoURL();
        renderTable(URL);

        var URL = $.getfenxiaoURL();
        getCouponAndPrepaidAmount(URL);
        renderTable(URL)


        // 收入支出统计信息
        $pageElement.$addupamountBtn.click(function () {
            var totalParams = getQueryParams();
            $.get(URL + "/account/subtotal", totalParams, function (res) {
                if (res.returnCode === 0) {
                    var resData = res.data;
                    var payInfo = "已支出" + resData.expenditureQuantity + "笔" + "，共" + resData.expenditureAmount + "元";
                    var incomeInfo = "已收入" + resData.incomeQuantity + "笔" + "，共" + resData.incomeAmount + "元";
                    $pageElement.$addupInfo.removeClass("addup-infohide").find("li").html(incomeInfo).end().find("li:first-child").html(payInfo);
                }
            }, "json");
        });


        // 根据账户类型加载账户详情
        $pageElement.$accountType.change(function () {
            $pageElement.$yhTable.bootstrapTable("refreshOptions", {pageNumber: 1});
        });
        // 条件查询
        $pageElement.$queryBtn.click(function () {
            $pageElement.$yhTable.bootstrapTable("refreshOptions", {pageNumber: 1});
        });
    }

    function getCouponAndPrepaidAmount(URL) {
        // var urlParams = getUrlParams();
        var $pageElement = getPageElement();
        $.get(URL + "/account/accountAmount", {}, function (res) {
            if (res.returnCode === 0) {
                var resData = res.data;
                $pageElement.$cashInput.val(resData.cashAmountDouble);
                $pageElement.$rebateInput.val(resData.couponAmountDouble);
                $pageElement.$prepaidInput.val(resData.prepaidAmountDouble);
            }
        }, "json")
    }

    // 返回成功渲染表格
    function responseHandler(res) {
        if (res.returnCode !== 0) {
            return;
        }
        if (res['data']) {
            return {
                total: res.data.total, // 总页数,前面的key必须为"total"
                data: res.data.list // 行数据，前面的key要与之前设置的dataField的值一致.
            };
        }
    }

    // 获取表格需要查询的参数
    function getQueryParams(params) {
        // var urlParams = getUrlParams();
        if (params) {
            return {
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
            url: URL + "/account/flowList",
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
                }, {
                    field: "createByName",
                    title: "操作人"
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