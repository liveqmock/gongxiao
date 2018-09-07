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
        layui.use(['laydate'], function () {
            var $ = layui.$;
            var laydate = layui.laydate;
            laydate.render({
                elem: '#yhdate',
                format: 'yyyy-MM-dd'
            })
        })
    }


    function getPageElem() {
        var paramsInfo = $.getUrlParam("receiveId");
        var projectId = $.getUrlParam("projectId");
        var projectName = $.getUrlParam("projectName");
        return {
            projectId:projectId,
            projectName:projectName,
            paramsInfo: decodeURIComponent(paramsInfo),
            $receConfirmTable: $("#prepaid-confirmtable"),
            $confirmBtn: $("#confirm-btn"),
            $payment: $("#payment"),
            $recevieTime: $("#recevietime")
        };
    }


    function getMaskElement() {
        var maskElem = {
            $yhMask: $("#yhmask"),
            $confirMamount: $("#confirmamount"),
            $amountin: $("#amountin"),
            $yhmaskSubmit: $("#yhmask-submit")
        };
        maskElem.$confirMamount.on("input propertychange",function () {
            var confirMount = $(this).val();
            maskElem.$amountin.val(confirMount);
        });
        return maskElem
    }


    function confirmInit() {
        renderPageEle();
        var $pageElem = getPageElem();
        var $maskElem = getMaskElement();

        var prepaidParam = $pageElem.paramsInfo;

        $.post(URL+"/prepaid/receive/getsByIds",{
            ids:prepaidParam
        },function (res) {
            var confirmListLoadData = res.data;
            receconfirmTableRender();
            $pageElem.$receConfirmTable.bootstrapTable('load',confirmListLoadData);
        },"json");

        $maskElem.$yhmaskSubmit.click(function () {
            var _index = $maskElem.$yhMask.attr("yhtable-index");
            $maskElem.$yhMask.addClass('yhmask-hide');
            var confirMamount = $maskElem.$confirMamount.val();
            var amountin = $maskElem.$amountin.val();
            // 更新单元格数据
             $pageElem.$receConfirmTable.bootstrapTable('updateCell', { index: _index, field: 'confirmAmountDouble', value: confirMamount });
            // 更新单元格数据
            $pageElem.$receConfirmTable.bootstrapTable('updateCell', { index: _index, field: 'receiptAmountDouble', value: amountin });
            $pageElem.$receConfirmTable.bootstrapTable("refresh");
        });

        var isAjaxFlag = false;
        // 提交代垫确认
        $pageElem.$confirmBtn.click(function () {
           var projectId = $pageElem.projectId;
           var projectName = $pageElem.projectName;
           var confirData = $pageElem.$receConfirmTable.bootstrapTable("getData");
           var arrParams = [{}];
           $.each(confirData,function (index,confirDataItem) {
               arrParams[index] = {
                   confirmAmountDouble:confirDataItem._confirmAmountDouble,
                   receiptAmountDouble:confirDataItem._receiptAmountDouble,
                   receiveId: confirDataItem.receiveId,
                   dataVersion:confirDataItem.dataVersion
               }

           });
            arrParams = JSON.stringify(arrParams);
           var submitParams = {
               projectId: projectId,
               projectName: projectName,
               useDate: $("#yhdate").val(),
               accountType: $("#accounttype").val(),
               philipNo:$("#philipNo").val(),
               confirmInfoJson:arrParams
           };
            if(isAjaxFlag){
                return;
            }
            isAjaxFlag = true;
            $.ajax({
                type: "POST",
                url: URL + "/prepaid/receive/confirm",
                cache: false,
                dataType: 'json',
                data: submitParams,
                async: false,
                beforeSend: function () {
                    $.renderLoading(true);
                    $pageElem.$confirmBtn.attr("disabled", true);
                },
                complete: function () {
                    $.renderLoading(false);
                    $pageElem.$confirmBtn.attr("disabled",false);
                },
                success: function (res) {
                    if (res.returnCode === 0) {
                        window.location.href = "./prepaid-confirm.html";
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
        })
    }



    function receconfirmTableRender() {
        var $receConfirmTable = $("#prepaid-confirmtable");
        var confirmData = [{}];
        $receConfirmTable.bootstrapTable({
            undefinedText: "",
            data: confirmData,
            striped: true,
            columns: [{
                field: 'receiveStatusStr',
                title: '确认状态',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'orderId',
                title: '出库单号',
                align: 'center',
                valign: 'middle'
            },{
                field: 'currencyCode',
                title: '应收币种',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'receiveAmountStr',
                title: '应收金额',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'toBeConfirmAmountStr',
                title: '未收金额',
                align: 'center'
            },{
                field: '_confirmAmountDouble',
                title: '确认金额',
                formatter: function (value,row) {
                    if(!value){
                        row._confirmAmountDouble = row.toBeConfirmAmountDouble;
                        return "<input type='text' class='confirm-input'  value="+row.toBeConfirmAmountDouble+">";
                    }
                    return "<input type='text' class='confirm-input' value="+value+">";
                },
                events: {
                    'blur .confirm-input':function (e,value,row,index) {
                        var inputValue = $(this).val();
                        $receConfirmTable.bootstrapTable("updateCell",{
                            index: index,
                            field: '_confirmAmountDouble',
                            value: inputValue
                        })
                    }

                }
            },  {
                field: '_receiptAmountDouble',
                title: '实收金额',
                formatter: function (value,row) {
                    if(!value){
                        row._receiptAmountDouble = row.toBeConfirmAmountDouble;
                        return "<input type='text' class='confirm-input' value="+row.toBeConfirmAmountDouble+" number>";
                    }
                    return "<input type='text' class='confirm-input' value="+value+">";
                },
                events: {
                    'blur .confirm-input':function (e,value,row,index) {
                        var inputValue = $(this).val();
                        $receConfirmTable.bootstrapTable("updateCell",{
                            index: index,
                            field: '_receiptAmountDouble',
                            value: inputValue
                        })
                    }

                }
            }]
        })
    }
});