/**
 * @authors {len} (http://csshack.org)
 * @date    2018/5/7
 */
$(function () {
    init();


    function init() {
        $.renderLoading(true);
        renderElement();
        var $pageElement = getPageElement();
        var $maskElement = getMaskElement();
        var URL = $.getfenxiaoURL();

        projectAccountAmount(URL);

        // 点击转出按钮触发事件
        function accountBalance(maskType) {
            $maskElement.$yhmask.removeClass("yhmask-hide");
            // var prepaidValue = el.val();
            // $maskElement.$intoAmount.val(prepaidValue);
            $maskElement.$yhmask.attr("transfer-type", maskType);
        }

        $("#yhmask-switch").click(function () {
            $maskElement.$intoAmount.val(0);
        });

        $("#yhmask-cancel").click(function () {
            $maskElement.$intoAmount.val(0);
        });



        var isAjaxFlag = false;
        var prepaidFlag = false;
        // 确认转入金额
        $maskElement.$yhmaskSubmit.click(function () {
            var $yhTable = $("#yhtable");
            var maskInputValue = $maskElement.$intoAmount.val();
            var maskTransfreType = $maskElement.$yhmask.attr("transfer-type");
            var postParams = {
                remark: $("#mask-remark").val(),
                postingAmount: maskInputValue,
                projectId: $("#projectlist").val()
            };
            if (maskTransfreType === "rebate") {
                // 返利
                if(isAjaxFlag){
                    return;
                }
                isAjaxFlag = true;
                $.ajax({
                    type: "POST",
                    url: URL + "/supplier/coupon/posting",
                    cache: false,
                    dataType: 'json',
                    data: postParams,
                    async: false,
                    beforeSend: function () {
                        $.renderLoading(true);
                        $maskElement.$yhmaskSubmit.attr("disabled", true);
                    },
                    complete: function () {
                        $.renderLoading(false);
                        $maskElement.$yhmaskSubmit.attr("disabled",false);
                    },
                    success: function (res) {
                        if (res.returnCode === 0) {
                            getCouponAndPrepaidAmount(URL);
                            $maskElement.$intoAmount.val(0);
                            $yhTable.bootstrapTable("refreshOptions",{pageNumber:1});
                            setTimeout(function(){
                                isAjaxFlag = false;
                            },2000);
                        } else {
                            setTimeout(function(){
                                $.renderLoading(false);
                            },2000);
                            isAjaxFlag = false;
                            $.renderLayerMessage(res.message);
                        }
                    },
                    error: function (err) {
                        isAjaxFlag = false;
                        $.renderLoading(false);
                        $.renderLayerMessage(err);
                    }
                });
            } else if (maskTransfreType === "prepaid") {
                // 代垫
                if(prepaidFlag){
                    return false;
                }
                prepaidFlag = true;
                $.ajax({
                    type: "POST",
                    url: URL + "/supplier/prepaid/posting",
                    cache: false,
                    dataType: 'json',
                    data: postParams,
                    async: false,
                    beforeSend: function () {
                        $.renderLoading(true);
                        $maskElement.$yhmaskSubmit.attr("disabled", true);
                    },
                    complete: function () {
                        $.renderLoading(false);
                        $maskElement.$yhmaskSubmit.attr("disabled",false);
                    },
                    success: function (res) {
                        if (res.returnCode === 0) {
                            getCouponAndPrepaidAmount(URL);
                            $maskElement.$intoAmount.val(0);
                            $yhTable.bootstrapTable("refreshOptions",{pageNumber:1});
                            setTimeout(function(){
                                prepaidFlag = false;
                            },2000);
                        } else {
                            setTimeout(function(){
                                $.renderLoading(false);
                            },2000);
                            prepaidFlag = false;
                            $.renderLayerMessage(res.message);
                        }
                    },
                    error: function (err) {
                        prepaidFlag = false;
                        $.renderLoading(false);
                        $.renderLayerMessage(err);
                    }
                });
            } else {
                return false;
            }
            $maskElement.$yhmask.addClass("yhmask-hide");
        });

        // 返利转入金额
        $pageElement.$rebateBtn.click(function () {
            accountBalance("rebate");
        });
        // 代垫转入金额
        $pageElement.$prepaidBtn.click(function () {
            accountBalance("prepaid");
        });

        // 收入支出统计信息
        $pageElement.$addupamountBtn.click(function () {
            var totalParams = getTableDataParams();
            var accountType = $("#accounttype").val();
            if (accountType == 2) {
                // 代垫统计
                $.get(URL + "/supplier/prepaid/subtotal", totalParams, function (res) {
                    if (res.returnCode === 0) {
                        var resData = res.data;
                        var payInfo = "已支出" + resData.expenditureQuantity + "笔" + "，共" + resData.expenditureAmountStr + "元";
                        var incomeInfo = "已收入" + resData.incomeQuantity + "笔" + "，共" + resData.incomeAmountStr + "元";
                        $pageElement.$addupInfo.removeClass("addup-infohide").find("li").html(incomeInfo).end().find("li:first-child").html(payInfo);
                    }
                }, "json");
            } else if (accountType == 1) {
                // 返利统计
                $.get(URL + "/supplier/coupon/subtotal", totalParams, function (res) {
                    if (res.returnCode === 0) {
                        var resData = res.data;
                        var payInfo = "已支出" + resData.expenditureQuantity + "笔" + "，共" + resData.expenditureAmountStr + "元";
                        var incomeInfo = "已收入" + resData.incomeQuantity + "笔" + "，共" + resData.incomeAmountStr + "元";
                        $pageElement.$addupInfo.removeClass("addup-infohide").find("li").html(incomeInfo).end().find("li:first-child").html(payInfo);
                    }
                }, "json");
            }
        });


        // 查询流水
        $pageElement.$queryBtn.click(function () {
            var accountType = $("#accounttype").val();
            if (accountType == 2) {
                // 代垫流水
                var flowUrl = URL + "/supplier/prepaid/flow";
                $pageElement.refreshTable(flowUrl);
            } else if (accountType == 1) {
                // 返利流水
                var couponoUrl = URL + "/supplier/coupon/supplierCouponFlow";
                $pageElement.refreshTable(couponoUrl);
            }
        });

        // 流水导出
        $pageElement.$exportBtn.click(function () {
            var accountType = $("#accounttype").val();
            if (accountType == 2) {
                // 代垫流水
            } else if (accountType == 1) {
                // 项目ID
                var projectId = $("#projectlist").val();
                // 账户类型
                var accountType = $("#accounttype").val();
                // 资金流向
                var moneyFlow = $("#moneyflow").val();
                // 开始时间
                var beginDateString = $("#starttime").val();
                // 结束时间
                var endDateString = $("#endtime").val();
                // 返利流水
                var couponoUrl = URL + "/supplier/coupon/export?projectId=" + projectId + "&accountType=" + accountType + "&moneyFlow="
                    + moneyFlow + "&beginDateString=" + beginDateString + "&endDateString=" + endDateString;
                window.open(couponoUrl);
            }
        })
    }


    // 统计信息查询参数
    function getTableDataParams() {
        return {
            projectId: $("#projectlist").val(),
            // 账户类型
            accountType: $("#accounttype").val(),
            // 资金流向
            moneyFlow: $("#moneyflow").val(),
            // 开始时间
            beginDateString: $("#starttime").val(),
            // 结束时间
            endDateString: $("#endtime").val()
        };
    }

    function getCouponAndPrepaidAmount(URL) {
        var $pageElement = getPageElement();
        $.get(URL + "/supplier/coupon/accountAccount", {
            projectId: $("#projectlist").val()
        }, function (res) {
            if (res.returnCode === 0) {
                var resData = res.data;
                $pageElement.$rebateInput.val(resData.couponAmountDoubleStr);
                $pageElement.$prepaidInput.val(resData.prepaidAmountDoubleStr);
            }
        }, "json")
    }

    // 获取返利和代垫金额
    function projectAccountAmount(URL) {
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName")
            .then(function () {
                getCouponAndPrepaidAmount(URL);
                renderTable(URL);
                $.renderLoading(false);
            });
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
        return {
            // 项目ID
            projectId: $("#projectlist").val(),
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
    }

    // 基本信息表格
    function renderTable(URL) {
        var $yhTable = $("#yhtable");
        $yhTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/supplier/coupon/supplierCouponFlow",
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
                }, {
                    field: "createByName",
                    title: "操作人"
                }, {
                    field: 'remark',
                    title: '备注'
                }]
        })
    }

    function renderElement() {
        $("#accounttype").select2({
            minimumResultsForSearch: 6
        });
        $("#moneyflow").select2({
            minimumResultsForSearch: 6
        });
        $.renderDateRange('starttime', 'endtime');
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
            refreshTable: function (url) {
                $yhTable.bootstrapTable("refresh", {url: url});
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

