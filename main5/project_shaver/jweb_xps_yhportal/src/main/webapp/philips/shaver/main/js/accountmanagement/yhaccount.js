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
        $.renderDateRange("starttime", "endtime");
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
        var $queryBtn = $("#yhquery-btn");
        // 返利实收账户余额
        var $rebateGatherInput = $("#rebategather-input");
        // 代垫实收账户
        var $prepaidGatherInput = $("#prepaidgather-input");
        // 返利过账账户余额转入按钮
        var $rebateBtn = $("#rebate-btn");
        // 代垫过账账户余额转入按钮
        var $prepaidBtn = $("#prepaid-btn");
        // 返利过账账户余额
        var $rebateInput = $("#rebate-input");
        // 代垫过账账户余额
        var $prepaidInput = $("#prepaid-input");
        // 导出按钮
        var $exportBtn = $("#export-btn");
        // 统计金额按钮
        var $addupAmountBtn = $("#addupamount-btn");
        // 统计信息
        var $addupInfo = $("#addup-info");
        // // 统计数目
        // var $addupAmountNumber = $("#addupamount-number");
        // // 统计金额
        // var $addupAmountAmount = $("#addupamount-amount");
        // 表格
        var $yhTable = $("#yhtable");
        return {
            $projectList: $projectList,
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
            $yhTable: $yhTable,
            $rebateGatherInput: $rebateGatherInput,
            $prepaidGatherInput: $prepaidGatherInput,
            $addupAmountBtn: $addupAmountBtn,
            $addupInfo: $addupInfo
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
        // 待添加客户
        var $toBeAddedTable = $("#tobeaddedtable");
        // 已选客户
        var $addedTable = $("#addedtable");
        return {
            $yhmask: $yhmask,
            $cnyType: $cnyType,
            $intoAmount: $intoAmount,
            $maskRemark: $maskRemark,
            $yhmaskSubmit: $yhmaskSubmit,
            $toBeAddedTable: $toBeAddedTable,
            $addedTable: $addedTable
        }
    }


    function init() {
        var URL = $.getfenxiaoURL();
        var $pageElement = getPageElement();
        var $maskElement = getMaskElement();
        var tableStatus = {};
        // 已添加表格的数据
        var alreadyDataList = [];

        function dataInit(){
            alreadyDataList.splice(0,alreadyDataList.length);
            for(var key in tableStatus){
                delete tableStatus[key];
            };
            $("#right-addnum").text("(" + 0 + ")");
            $("#tobeaddedtable").bootstrapTable("destroy");
            $("#addedtable").bootstrapTable("destroy");
        }

        renderElement();

        function projectAccountAmount() {
            $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
                .then(function () {
                    getGatherAmount("/payment/yhglobal/receivedAccount", $pageElement.$rebateGatherInput, "couponAmountDoubleStr");
                    getGatherAmount("/payment/yhglobal/receivedAccount", $pageElement.$prepaidGatherInput, "prepaidAmountDoubleStr");
                    getGatherAmount("/payment/yhglobal/bufferAccount", $pageElement.$rebateInput, "couponAmountDoubleStr");
                    getGatherAmount("/payment/yhglobal/bufferAccount", $pageElement.$prepaidInput, "prepaidAmountDoubleStr");
                    renderTable(URL)
                });
        }

        projectAccountAmount();

        // 根据账户类型加载账户详情
        $pageElement.$accountType.change(function () {
            $pageElement.$yhTable.bootstrapTable("refreshOptions", {pageNumber: 1});
        });
        // 条件查询
        $pageElement.$queryBtn.click(function () {
            $pageElement.$yhTable.bootstrapTable("refreshOptions", {pageNumber: 1});
        });
        // 收入支出统计信息
        $pageElement.$addupAmountBtn.click(function () {
            var totalParams = getTableDataParams();
            $.get(URL + "/payment/yhglobal/flowSubtotal", totalParams, function (res) {
                if (res.returnCode === 0) {
                    var resData = res.data;
                    var payInfo = "已支出" + resData.expenditureQuantity + "笔" + "，共" + resData.expenditureAmount + "元";
                    var incomeInfo = "已收入" + resData.incomeQuantity + "笔" + "，共" + resData.incomeAmount + "元";
                    $pageElement.$addupInfo.removeClass("addup-infohide").find("li").html(incomeInfo).end().find("li:first-child").html(payInfo);
                }
            }, "json");

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
                    endDateString: endDateString
                };
            }

        }


        // 得到实收账户余额
        function getGatherAmount(url, el, amountValue) {
            var projectId = $("#projectlist").val();
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


        // 点击转出按钮触发事件
        function accountBalance(el, maskType) {
            $maskElement.$yhmask.removeClass("yhmask-hide");
            // var prepaidValue = el.val();
            // $maskElement.$intoAmount.val(prepaidValue);
            $maskElement.$yhmask.attr("transfer-type", maskType);
        }

        // 返利过账账户余额
        $pageElement.$rebateBtn.click(function () {
            accountBalance($pageElement.$rebateInput, "rebate");
            toBeAddedTableRender(URL, "/payment/yhglobal/couponAccountList");
        });
        // 代垫过账账户余额
        $pageElement.$prepaidBtn.click(function () {
            accountBalance($pageElement.$prepaidInput, "prepaid");
            toBeAddedTableRender(URL, "/payment/yhglobal/prepaidAccountList");
        });


        var isAjaxFlag = false;
        var isAjaxFlagPrepaid = false;
        // 确认转出金额
        $maskElement.$yhmaskSubmit.click(function () {
            var projectId = $("#projectlist").val();
            var transferAmountData = $maskElement.$addedTable.bootstrapTable("getData");

            $.each(transferAmountData, function (index, transferAmountDataItem) {
                if (transferAmountDataItem['transferAmountDouble'] < 0 || typeof transferAmountDataItem['transferAmountDouble'] !== 'number') {
                    $.renderLayerMessage("请输入正确的金额！");
                    return;
                }
            });

            if (transferAmountData.length !== 0) {
                transferAmountData = JSON.stringify(transferAmountData);
                var transferOutParams = {
                    projectId: projectId,
                    accounts: transferAmountData
                };
                var maskTransfreType = $maskElement.$yhmask.attr("transfer-type");
                if (maskTransfreType === "rebate") {
                    if (isAjaxFlag) {
                        return;
                    }
                    isAjaxFlag = true;

                    $.ajax({
                        type: "POST",
                        url: URL + "/payment/yhglobal/transCoupon",
                        cache: false,
                        dataType: 'json',
                        data: transferOutParams,
                        async: false,
                        beforeSend: function () {
                            $.renderLoading(true);
                            $maskElement.$yhmaskSubmit.attr("disabled", true);
                        },
                        complete: function () {
                            $.renderLoading(false);
                            $maskElement.$yhmaskSubmit.attr("disabled", false);
                        },
                        success: function (res) {
                            if (res.returnCode === 0) {
                                renderTable(URL);
                                dataInit();
                                setTimeout(function () {
                                    isAjaxFlag = false;
                                }, 2000);
                            } else {
                                setTimeout(function () {
                                    $.renderLoading(false);
                                }, 2000);
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
                    if (isAjaxFlagPrepaid) {
                        return;
                    }
                    isAjaxFlagPrepaid = true;
                    $.ajax({
                        type: "POST",
                        url: URL + "/payment/yhglobal/transPrepaid",
                        cache: false,
                        dataType: 'json',
                        data: transferOutParams,
                        async: false,
                        beforeSend: function () {
                            $.renderLoading(true);
                            $maskElement.$yhmaskSubmit.attr("disabled", true);
                        },
                        complete: function () {
                            $.renderLoading(false);
                            $maskElement.$yhmaskSubmit.attr("disabled", false);
                        },
                        success: function (res) {
                            if (res.returnCode === 0) {
                                renderTable(URL);
                                dataInit();
                                setTimeout(function () {
                                    isAjaxFlagPrepaid = false;
                                }, 2000);
                            } else {
                                setTimeout(function () {
                                    $.renderLoading(false);
                                }, 2000);
                                isAjaxFlagPrepaid = false;
                                $.renderLayerMessage(res.message);
                            }
                        },
                        error: function (err) {
                            isAjaxFlagPrepaid = false;
                            $.renderLoading(false);
                            $.renderLayerMessage(err);
                        }
                    });
                } else {
                    return;
                }

                $maskElement.$yhmask.addClass("yhmask-hide");
                projectAccountAmount();
            }
        });

        $("#yhmask-switch").click(function () {
            dataInit();
        });
        $("#yhmask-cancel").click(function () {
            dataInit();
        });

        $("#yhmask-cancel2").click(function () {
            $("#mask-wrap2").addClass("mask-hide");
        });


        // 返利实收账户余额
        $("#rebategather-btn").click(function () {
            gatherAmount("gather-rebate");
        });

        // 代垫实收账户余额
        $("#prepaidgather-btn").click(function () {
            gatherAmount("gather-prepaid");
        });


        // 返利代垫实收账户余额
        var $maskWrap = $("#mask-wrap2");
        var $maskMenu = $("#mask-menu2");
        var $maskSwitch = $("#mask-switch2");
        var $maskSubmit = $("#mask-submit2");

        function gatherAmount(gatherType) {
            $maskWrap.removeClass("mask-hide").attr("gather-type", gatherType);
            var gatherTitle = {
                rebateTitle: "返利实收账户余额",
                prepaidTitle: "代垫实收账户余额"
            };
            if (gatherType === "gather-rebate") {
                $maskMenu.children("h4").text(gatherTitle.rebateTitle);
            } else if (gatherType === "gather-prepaid") {
                $maskMenu.children("h4").text(gatherTitle.prepaidTitle);
            }
        }

        var maskRebateAjaxFlag = false;
        var maskPrepaidAjaxFlag = false;
        // 返利代垫实收金额转出
        $maskSubmit.click(function () {
            var maskType = $maskWrap.attr("gather-type");
            var projectId = $("#projectlist").val();
            var $maskInput = $("#mask-input2");
            var maskInputVal = $maskInput.val();
            var gatherInputValue = ($("#mask-input2").val()).replace(/\,/g, "");
            var remark = $("#mask-remark").val();
            var gatherParams = {
                projectId: projectId,
                amount: maskInputVal,
                remark: remark
            };
            verification.emptyShow = false;
            verification.noEmpty("mask-input2", "verification-strong", "请填写转出金额!");
            if (verification.emptyShow || maskInputVal > gatherInputValue) {
                return false;
            }
            if (maskType === "gather-rebate") {
                if (maskRebateAjaxFlag) {
                    return;
                }
                maskRebateAjaxFlag = true;
                $.renderLoading(true);
                $.ajax({
                    type: "POST",
                    url: URL + "/payment/yhglobal/transReceivedCoupon",
                    cache: false,
                    dataType: 'json',
                    data: gatherParams,
                    async: false,
                    beforeSend: function () {
                        $.renderLoading(true);
                        $maskSubmit.attr("disabled", true);
                    },
                    complete: function () {
                        $.renderLoading(false);
                        $maskSubmit.attr("disabled", false);
                    },
                    success: function (res) {
                        if (res.returnCode === 0) {
                            $maskWrap.addClass("mask-hide");
                            getGatherAmount("/payment/yhglobal/receivedAccount", $pageElement.$rebateGatherInput, "couponAmountDoubleStr");
                            getGatherAmount("/payment/yhglobal/receivedAccount", $pageElement.$prepaidGatherInput, "prepaidAmountDoubleStr");
                            renderTable(URL);
                            setTimeout(function () {
                                maskRebateAjaxFlag = false;
                            }, 2000);
                            $maskInput.val(0)
                        } else {
                            setTimeout(function () {
                                $maskInput.val(0);
                                $.renderLoading(false);
                            }, 2000);
                            maskRebateAjaxFlag = false;
                            $.renderLayerMessage(res.message);
                        }
                    },
                    error: function (err) {
                        maskRebateAjaxFlag = false;
                        setTimeout(function () {
                            $maskInput.val(0);
                            $.renderLoading(false);
                        }, 2000);
                        $.renderLayerMessage(err);
                    }
                });
            } else if (maskType === "gather-prepaid") {
                if (maskPrepaidAjaxFlag) {
                    return;
                }
                maskPrepaidAjaxFlag = true;
                $.renderLoading(true);
                $.ajax({
                    type: "POST",
                    url: URL + "/payment/yhglobal/transReceivedPrepaid",
                    cache: false,
                    dataType: 'json',
                    data: gatherParams,
                    async: false,
                    beforeSend: function () {
                        $.renderLoading(true);
                        $maskSubmit.attr("disabled", true);
                    },
                    complete: function () {
                        $.renderLoading(false);
                        $maskSubmit.attr("disabled", false);
                    },
                    success: function (res) {
                        if (res.returnCode === 0) {
                            $maskWrap.addClass("mask-hide");
                            getGatherAmount("/payment/yhglobal/receivedAccount", $pageElement.$rebateGatherInput, "couponAmountDoubleStr");
                            getGatherAmount("/payment/yhglobal/receivedAccount", $pageElement.$prepaidGatherInput, "prepaidAmountDoubleStr");
                            renderTable(URL);
                            setTimeout(function () {
                                maskPrepaidAjaxFlag = false;
                                $maskInput.val(0);
                            }, 2000);
                        } else {
                            setTimeout(function () {
                                $maskInput.val(0);
                                $.renderLoading(false);
                            }, 2000);
                            maskPrepaidAjaxFlag = false;
                            $.renderLayerMessage(res.message);
                        }
                    },
                    error: function (err) {
                        maskPrepaidAjaxFlag = false;
                        $.renderLoading(false);
                        $.renderLayerMessage(err);
                    }
                });
            } else {
                return false;
            }
        });

        $maskSwitch.click(function () {
            $maskWrap.addClass("mask-hide");
            $("#mask-input2").val("");
        });

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

        // 基本信息表格
        function renderTable(URL) {
            $pageElement.$yhTable.bootstrapTable({
                contentType: "application/x-www-form-urlencoded",
                url: URL + "/payment/yhglobal/flowList",
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


        function getQueryParams(params) {
            var projectId = $("#projectlist").val();
            return {
                projectId: projectId,
                // 首页页码
                pageNumber: params.pageNumber,
                // 数据条数
                pageSize: params.pageSize
            }
        }

        // 待添加客户表格
        function toBeAddedTableRender(URL, tableUrl) {
            $maskElement.$toBeAddedTable.bootstrapTable({
                method: 'get',
                contentType: "application/x-www-form-urlencoded",
                url: URL + tableUrl,
                queryParamsType: '',
                silent: true,
                undefinedText: '',
                striped: true,
                dataField: 'data',
                pagination: true,
                pageSize: 6, // 一页显示数据条数
                pageList: [4, 6, 8],
                pageNumber: 1, // 首页页码
                sidePagination: "server",
                queryParams: getQueryParams,
                responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法
                columns: [{
                    field: 'distributorName',
                    title: '客户名称'
                }, {
                    field: 'maskOpera',
                    title: '操作',
                    clickToSelect: false,
                    formatter: function (value, row) {
                        if (tableStatus[row.distributorName]) {
                            return '已添加'
                        } else {
                            return '<a href="javascript:void(0);" class="pur-addbtn account-add">添加</a>'
                        }
                    },
                    events: {
                        'click .account-add': function (event, value, row, index) {
                            alreadyDataList.push(row);
                            tableStatus[row.distributorName] = true;
                            if (tableStatus[row.distributorName]) {
                                $(this).removeClass("pur-addbtn").addClass("puralreadyadd");
                                var alreadyaddNum = alreadyDataList.length;
                                $("#right-addnum").text("(" + alreadyaddNum + ")");
                                addedTableRender();
                                $maskElement.$addedTable.bootstrapTable('load', alreadyDataList);
                                $maskElement.$toBeAddedTable.bootstrapTable('updateCell', {
                                    index: index,
                                    field: 'maskOpera',
                                    value: tableStatus[row.distributorName]
                                });
                            }
                        }
                    }
                }]
            })
        }

        // 已选择客户表格
        function addedTableRender() {
            var addedData = [{}];
            $maskElement.$addedTable.bootstrapTable({
                undefinedText: "",
                data: addedData,
                striped: true,
                columns: [{
                    field: 'distributorName',
                    title: '客户帐号'
                }, {
                    field: 'transferAmountDouble',
                    title: '转出金额',
                    formatter: function (value, row) {
                        if (!value) {
                            row.transferAmountDouble = 0;
                            return '<input type="text" class="amount-input" value="' + value + '">'
                        } else {
                            row.transferAmountDouble = value;
                            return '<input type="text" class="amount-input" value="' + value + '">'
                        }
                    },
                    events: {
                        'blur .amount-input': function (event, value, row, index) {
                            var _index = index;
                            var inputValue = Number($(this).val());
                            $(this).removeClass("table-warning-input");
                            if(inputValue > 0 || inputValue === 0){
                                $(this).removeClass("table-warning-input");
                                $maskElement.$addedTable.bootstrapTable('updateCell', {
                                    index: _index,
                                    field: 'transferAmountDouble',
                                    value: inputValue
                                });
                            }else{
                                $(this).addClass("table-warning-input");
                            }

                        }
                    }
                }, {
                    title: '操作',
                    clickToSelect: false,
                    formatter: function (value, row) {
                        if (row.distributorName) {
                            return '<a href="javascript:void(0);" class="account-delete">删除</a>'
                        } else {
                            return ""
                        }
                    },
                    events: {
                        'click .account-delete': function (event, value, row, index) {
                            tableStatus[row.distributorName] = false;
                            // 删除行
                            $maskElement.$addedTable.bootstrapTable('remove', {
                                field: 'distributorName',
                                values: [row.distributorName]
                            });
                            // 更新遮罩层坐标的表格
                            $maskElement.$toBeAddedTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'maskOpera',
                                value: tableStatus[row.distributorName]
                            });
                            var alreadyaddNum = $maskElement.$addedTable.bootstrapTable('getData').length;
                            $("#right-addnum").text("(" + alreadyaddNum + ")");
                        }
                    }
                }]
            })
        }
    }
});

