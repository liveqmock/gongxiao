/**
 * @authors {len} (http://csshack.org)
 * @date    2018/4/24
 */
$(function () {

    // 全局接口
    var URL = $.getfenxiaoURL();

    confirmInit();

    // layui时间控件渲染
    function renderPageEle() {
        $("#bank-name").select2({
            minimumResultsForSearch: 6
        });

        var distributorName = $.getUrlParam("distributorName");
        $("#client-name").val(distributorName);

        function transformDoubble(num, length) {
            return ('' + num).length < length ? ((new Array(length + 1)).join('0') + num).slice(-length) : '' + num;
        }
        // layUi初始化日期控件
        layui.config({
            version: '1515376178709' //为了更新 js 缓存
        });
        layui.use(['laydate'],function () {
            var laydate = layui.laydate;
            var tempTime = new Date();
            var currentTime = tempTime.getFullYear() + "-" + transformDoubble((tempTime.getMonth() + 1),2) + "-" + transformDoubble(tempTime.getDate(),2);
            laydate.render({
                elem: '#transfortime',
                format: 'yyyy-MM-dd',
                value: currentTime
            });
        });

    }


    function getPageElem() {
        //从预销管理传过来的信息
        var paramsInfo = $.getUrlParam("salesOrderNoList");
        var pageEle = {
            paramsInfo: paramsInfo,
            $receConfirmTable: $("#prepaid-confirmtable"),
            $confirmBtn: $("#confirm-btn"),
            //付款银行
            $bankName: $("#bank-name"),
            //收款日期
            $recevieTime: $("#transfortime"),
            // 银行流水号
            $bankFlow: $("#bank-flow")
        };
        return pageEle;
    }


    function getMaskElem() {
        var maskElem = {
            $yhMask: $("#yhmask"),
            $confirMamount: $("#confirmamount"),
            $amountin: $("#amountin"),
            $yhmaskSubmit: $("#yhmask-submit")
        };
        maskElem.$confirMamount.on("input propertychange", function () {
            var confirMount = $(this).val();
            maskElem.$amountin.val(confirMount);
        });
        return maskElem
    }


    function confirmInit() {
        renderPageEle();
        var $pageElem = getPageElem();
        var $maskElem = getMaskElem();

        var prepaidParam = $pageElem.paramsInfo;

        $.get(URL + "/payment/cashConfirm/confirmList ", {
            salesOrderNoList: prepaidParam
        }, function (res) {
            var confirmListLoadData = res.data;
            receconfirmTableRender();
            $pageElem.$receConfirmTable.bootstrapTable('load', confirmListLoadData);
        }, "json");


        $maskElem.$yhmaskSubmit.click(function () {
            var _index = $maskElem.$yhMask.attr("yhtable-index");
            $maskElem.$yhMask.addClass('yhmask-hide');
            var confirMamount = $maskElem.$confirMamount.val();
            var amountin = $maskElem.$amountin.val();
            // 更新单元格数据
            $pageElem.$receConfirmTable.bootstrapTable('updateCell', {
                index: _index,
                field: 'confirmAmountDouble',
                value: confirMamount
            });
            // 更新单元格数据
            $pageElem.$receConfirmTable.bootstrapTable('updateCell', {
                index: _index,
                field: 'recipientAmountDouble',
                value: amountin
            });
            $pageElem.$receConfirmTable.bootstrapTable("refresh");

        });

        $pageElem.$confirmBtn.click(function () {
            if(!$pageElem.$bankName.val()){
                 $.renderLayerMessage("请先选择收款银行！")
            }else if(!$("#client-name").val()){
                 $.renderLayerMessage("请先选填写付款方！")
            }else{
                var confirData = $pageElem.$receConfirmTable.bootstrapTable("getData");
                confirData = JSON.stringify(confirData);
                var submitParams = {
                    //表单信息
                    itemsJson: confirData,
                    //收款日期
                    recipientDateString: $pageElem.$recevieTime.val(),
                    //收款银行
                    bankName: $pageElem.$bankName.val(),
                    bankFlowNo: $pageElem.$bankFlow.val(),
                    // 付款方
                    clientName: $("#client-name").val(),
                    remark: $("#remark").val()
                };
                if (confirData.length !== 0) {
                    $.post(URL + "/payment/cashConfirm/confirm", submitParams, function (res) {
                        if (res.returnCode === 0) {
                            window.location.href = "./cash-confirm.html";
                        } else {
                            $.renderLayerMessage(res.message)
                        }
                    }, "json")
                }
            }
        })
    }


    function receconfirmTableRender() {
        var $receConfirmTable = $("#prepaid-confirmtable");
        var confirmData = [{}];
        var $maskElem = getMaskElem();
        $receConfirmTable.bootstrapTable({
            undefinedText: "",
            data: confirmData,
            striped: true,
            columns: [{
                filed: 'confirmId',
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
                field: 'salesOrderNo',
                title: '销售单号'

            }, {
                field: 'shouldReceiveCurrency',
                title: '应收货币'
            }, {
                field: 'shouldReceiveAmountDouble',
                title: '应收金额'
            }, {
                field: 'unreceiveCurrency',
                title: '未收货币'
            }, {
                field: 'unreceiveAmountDouble',
                title: '未收金额'
            }, {
                field: 'confirmCurrency',
                title: '确认收款币种'
            }, {
                field: 'confirmAmountDouble',
                title: '确认收款金额'
            }, {
                field: 'recipientCurrency',
                title: '实收币种'
            }, {
                field: 'recipientAmountDouble',
                title: '实收金额'
            }, {
                title: '操作',
                striped: false,
                formatter: function () {
                    return '<a href="javascript:;" class="yhtable-revise btn btn-primary">' + '修改金额' + '</a>';
                },
                events: {
                    'click .yhtable-revise': function (event, value, row, index) {
                        $maskElem.$yhMask.attr("yhtable-index", index);
                        $maskElem.$yhMask.removeClass("yhmask-hide");
                        $maskElem.$confirMamount.val(row.confirmAmountDouble);
                        $maskElem.$amountin.val(row.recipientAmountDouble);
                    }
                }
            }]
        })
    }
});