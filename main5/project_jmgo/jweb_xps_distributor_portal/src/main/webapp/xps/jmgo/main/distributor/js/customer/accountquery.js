/**
 * @authors {Pinocchio} (http://csshack.org)
 * @date    2018-04-08 09:33:57
 * @version $Id$
 */

$(function () {

    var URL = $.getcustomerURL();


    accountQueryInit();


    function accountQueryInit() {
        renderDate();
        renderAccountTable();
        getAmountInfo(URL);

        // 统计信息
        $("#addupamount-btn").click(function () {
            var totalParams = {
                accountType: $("#accounttype").val(),
                // currencyCode:,
                moneyFlow: $("#moneyflow").val(),
                beginDateString: $("#starttime").val(),
                endDateString: $("#endtime").val()
            };
            $.get(URL + "/account/subtotal", totalParams, function (res) {
                if (res.returnCode === 0) {
                    var resData = res.data;
                    var payInfo = "已支出" + resData.expenditureQuantity + "笔" + "，共" + resData.expenditureAmount + "元";
                    var incomeInfo = "已收入" + resData.incomeQuantity + "笔" + "，共" + resData.incomeAmount + "元";
                    $("#addup-info").removeClass("addup-infohide").find("li").html(incomeInfo).end().find("li:first-child").html(payInfo);
                }
            }, "json");
        })

        $("#yhquery-btn").click(function () {
            $("#accountquery-table").bootstrapTable("refreshOptions", {pageNumber: 1});
        })

    }

    // 账户金额信息
    function getAmountInfo(URL){
        $.get(URL+"/account/accountAmount",function (res) {
            if(res.returnCode === 0){
                var cashAmountDoubleStr  = res.data.cashAmountDoubleStr;
                var couponAmountDoubleStr = res.data.couponAmountDoubleStr;
                var prepaidAmountDoubleStr = res.data.prepaidAmountDoubleStr;
                $("#rebate-input").val(couponAmountDoubleStr);
                $("#prepaid-input").val(prepaidAmountDoubleStr);
                $("#cash-input").val(cashAmountDoubleStr)

            }else{
                alert(res.message);
            }
        },"json")
    }

    // 时间控件
    function renderDate() {
        var start = laydate.render({
            elem: '#starttime',
            // max: nowTime,
            btns: ['clear', 'confirm'],
            done: function (value, date) {
                endMax = end.config.max;
                end.config.min = date;
                end.config.min.month = date.month - 1;
            }
        });
        var end = laydate.render({
            elem: '#endtime',
            // max: nowTime,
            done: function (value, date) {
                var curDate = new Date();
                date = {'date': curDate.getDate(), 'month': curDate.getMonth() + 1, 'year': curDate.getFullYear()};
                start.config.max = date;
                start.config.max.month = date.month - 1;
            }
        });

        $("#moneyflow").select2({
            minimumResultsForSearch: 6
        });
        $("#accounttype").select2({
            minimumResultsForSearch: 6
        });
    }

    // 返回成功渲染表格
    function responseHandler(res) {
        if (res.returnCode === 0 && res['data']['list']['length'] !== 0 && res['data']['total'] !== 0 && typeof res['data']['total'] !== 'undefined') {
            return {
                total: res.data.total, // 总页数,前面的key必须为"total"
                data: res.data.list // 行数据，前面的key要与之前设置的dataField的值一致.
            }
        }else{
            return{
                total: 0,
                data: [{}]
            }
        }
    }


    // 获取表格需要查询的参数
    function getQueryParams(params) {
        return {
            accountType: $("#accounttype").val(),
            // currencyCode:,
            moneyFlow: $("#moneyflow").val(),
            beginDateString: $("#starttime").val(),
            endDateString: $("#endtime").val(),
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        }
    }

    function renderAccountTable() {
        var $accountQueryTable = $("#accountquery-table");
        $accountQueryTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/account/flowList",
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
            // columns: [{
            //     field: 'flowNo',
            //     title: '订单号/流水号',
            //     align: 'center',
            //     halign: 'center'
            // }, {
            //     field: 'transactionAmountStr',
            //     title: '收/支',
            //     align: 'center',
            //     halign: 'center'
            // }, {
            //     field: 'currencyCode',
            //     title: '币种',
            //     align: 'center',
            //     halign: 'center'
            // }, {
            //     field: 'transactionAmountStr',
            //     title: '金额',
            //     align: 'center',
            //     halign: 'center'
            // }, {
            //     field: 'type',
            //     title: '账户类型',
            //     align: 'center',
            //     halign: 'center'
            // }, {
            //     field: 'transactionAmount',
            //     title: '余额',
            //     align: 'center',
            //     halign: 'center'
            // }, {
            //     field: 'transactionTime',
            //     title: '交易时间',
            //     align: 'center',
            //     halign: 'center',
            //     formatter: function (value) {
            //         return $.transFormTime(value);
            //     }
            // }]
        })
    }
});