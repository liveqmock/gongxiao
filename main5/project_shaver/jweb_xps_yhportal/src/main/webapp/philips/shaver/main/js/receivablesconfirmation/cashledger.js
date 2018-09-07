/**
 * @authors {len} (http://csshack.org)
 * @date    2018/4/24
 */


$(function () {


    confirmInit();


    function confirmInit() {

        var URL = $.getfenxiaoURL();

        renderPageEle();

        var $pageElem = getPageElement();

        projectAccountAmount(URL);


        //点击查询按钮，执行查询
        $pageElem.$yhQueryBtn.click(function () {
            $pageElem.tableQuery();
        });

    }


    // layui时间控件渲染
    function renderPageEle() {
        $("#bankName").select2({
            minimumResultsForSearch: 6
        });
        $("#approveStatus").select2({
            minimumResultsForSearch: 6
        });
        $.renderDateRange('orderstarttime', 'confirmstarttime');
        $.renderDateRange('orderendtime', 'confirmendtime');
    }

    function getPageElement() {
        var $projectList = $("#projectlist");
        //确认起始时间
        var $confirmStartTime = $("#confirmstarttime");
        //确认截止时间
        var $confirmEndTime = $("#confirmendtime");
        //收款流水号
        var $flowNo = $("#salesno");
        //收款银行
        var $bankName = $("#bankName");
        //客户名称
        var $switchNumber = $("#switchnumber");
        //订单起始时间
        var $orderStartTime = $("#orderstarttime");
        //订单截止时间
        var $orderEndTime = $("#orderendtime");
        //审批状态
        var $approveStatus = $("#approveStatus");
        //查询按钮
        var $yhQueryBtn = $("#yhquery-btn");

        return {
            $yhTable: $("#yhtable"),
            $approveBtn: $("#approve-btn"),
            $projectList: $projectList,
            $orderStartTime: $orderStartTime,
            $orderEndTime: $orderEndTime,
            $flowNo: $flowNo,
            $bankName: $bankName,
            $switchNumber: $switchNumber,
            $approveStatus: $approveStatus,
            $confirmStartTime: $confirmStartTime,
            $confirmEndTime: $confirmEndTime,
            $yhQueryBtn: $yhQueryBtn,
            $cancelBtn: $("#cancel-btn"),
            $cancelConfirmBtn: $("#cancelConfirm-btn"),
            tableQuery: function () {
                this.$yhTable.bootstrapTable("refreshOptions", {pageNumber: 1});
            }
        };
    }


    function projectAccountAmount(URL) {
        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
            .then(function () {
                prepaidTableRender(URL)
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


    function getQueryParams(params) {
        var $pageElem = getPageElement();
        return {
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize,
            projectId: $pageElem.$projectList.val(),
            bankName: $pageElem.$bankName.val(),
            flowNo: $pageElem.$flowNo.val(),
            confirmBegin: $pageElem.$confirmStartTime.val(),
            confirmEnd: $pageElem.$orderEndTime.val(),
            receiveBegin: $pageElem.$orderStartTime.val(),
            receiveEnd: $pageElem.$orderEndTime.val(),
            approveStatus: $pageElem.$approveStatus.val()
        }
    }


    function prepaidTableRender(URL) {
        var $yhTable = $("#yhtable");
        $yhTable.bootstrapTable({
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/payment/cash/ledger/list",
            queryParamsType: '',
            silent: true,
            undefinedText: '',
            striped: true,
            dataField: 'data',
            pagination: true,
            pageSize: 10, // 一页显示数据条数
            pageList: [10, 20, 30],
            pageNumber: 1, // 首页页码
            sidePagination: "server",
            queryParams: getQueryParams,
            responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法
            columns: [{
                field: 'approvalStatus',
                title: '审批状态',
                formatter: function (value, row) {
                    if (row['flowNo']) {
                        if (value === true) {
                            return "已审批"
                        } else {
                            return "未审批"
                        }
                    } else {
                        return ''
                    }
                }
            },
                {
                field: 'flowNo',
                title: '确认流水号'
                },
                {
                    field: 'bankName',
                    title: '收款银行'
                }, {
                    field: 'projectName',
                    title: '项目名称'
                },
                // {
                //     title: '收款银行流水号',
                //     field: 'bankFlowNo'
                // },
                {
                    field: 'distributorName',
                    title: '客户名称'
                },
                    {
                    field: 'clientName',
                    title: '付款方'
                },
                {
                    field: 'confirmAmountDouble',
                    title: '确认金额',
                    formatter: function (value) {
                        return accounting.formatMoney(value, "", 2, ",", ".")
                    }
                },
                //     {
                //     field: 'recipientCurrency',
                //     title: '实收币种'
                // },
                {
                    field: 'recipientAmountDouble',
                    title: '实收金额',
                    formatter: function (value) {
                        return accounting.formatMoney(value, "", 2, ",", ".")
                    }
                }, {
                    field: 'receiveTime',
                    title: '收款日期',
                    formatter: function (value) {
                        return $.transformTime(value);
                    }
                }, {
                    field: 'confirmTime',
                    title: '确认时间',
                    formatter: function (value) {
                        return $.transformTime(value, "seconds");
                    }
                },{
                    field:"createdByName",
                    title:"创建人"
                }, {
                    field: 'approvalUserName',
                    title: '审批人'
                }, {
                    field: 'approveTime',
                    title: '审批时间',
                    formatter: function (value) {
                        return $.transformTime(value, "seconds");
                    }
                }, {
                    title: '操作',
                    width: 140,
                    formatter: function (value, row) {
                        var approveStatus = row.approvalStatus;
                        if (row['flowNo']) {
                            if (approveStatus === true) {
                                return '<a class="cancelApprove btn btn-warning">' + "取消审批" + '</a>';
                            } else {
                                return '<a class="approve btn btn-success">' + "审批" + '</a> ' + '<a class="cancelConfirm btn btn-danger">' + "取消确认" + '</a>';
                            }
                        } else {
                            return ""
                        }
                    },
                    events: {
                        'click .cancelApprove': function (e, value, row, index) {
                            var cancelParams = {
                                flowNo: row.flowNo
                            };
                            layui.use('layer', function () {
                                var layer = layui.layer;
                                layer.alert("您确认取消此审批吗？", {
                                    icon: 0
                                }, function () {
                                    $.ajax({
                                        url: URL + "/payment/cash/ledger/cancelApprove",
                                        type: "POST",
                                        data: cancelParams,
                                        dataType: "json",
                                        success: function (res) {
                                            if (res.returnCode === 0) {
                                                $yhTable.bootstrapTable("refreshOptions", {pageNumber: 1});
                                                layer.closeAll('dialog');
                                            } else {
                                                $.renderLayerMessage(res.message);
                                            }
                                        },
                                        error: function (err) {
                                            $.renderLayerMessage(err);
                                        }
                                    });
                                })
                            });
                        },
                        'click .cancelConfirm': function (e, value, row, index) {
                            var cancelParams = {
                                flowNo: row.flowNo
                            };
                            layui.use('layer', function () {
                                var layer = layui.layer;
                                layer.alert("您确认取消吗？", {
                                    icon: 0
                                }, function () {
                                    $.ajax({
                                        url: URL + "/payment/cash/ledger/cancelConfirm",
                                        type: "POST",
                                        data: cancelParams,
                                        dataType: "json",
                                        success: function (res) {
                                            if (res.returnCode === 0) {
                                                $yhTable.bootstrapTable("refreshOptions", {pageNumber: 1});
                                                layer.closeAll('dialog');
                                            } else {
                                                $.renderLayerMessage(res.message);
                                            }
                                        },
                                        error: function (err) {
                                            $.renderLayerMessage(err);
                                        }
                                    });
                                })
                            });
                        },
                        'click .approve': function (e, value, row, index) {
                            var cancelParams = {
                                flowNo: row.flowNo
                            };
                            layui.use('layer', function () {
                                var layer = layui.layer;
                                layer.alert("您确认审批吗？", {
                                    icon: 0
                                }, function () {
                                    $.ajax({
                                        url: URL + "/payment/cash/ledger/approve",
                                        type: "POST",
                                        data: cancelParams,
                                        dataType: "json",
                                        success: function (res) {
                                            if (res.returnCode === 0) {
                                                $yhTable.bootstrapTable("refreshOptions", {pageNumber: 1});
                                                layer.closeAll('dialog');
                                            } else {
                                                $.renderLayerMessage(res.message);
                                            }
                                        },
                                        error: function (err) {
                                            $.renderLayerMessage(err);
                                        }
                                    });
                                })
                            });
                        }
                    }
                }]
        })
    }
});