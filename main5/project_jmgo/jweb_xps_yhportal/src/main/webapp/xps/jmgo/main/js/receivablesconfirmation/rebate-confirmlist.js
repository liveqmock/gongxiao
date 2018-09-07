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
            var laydate = layui.laydate;
            laydate.render({
                elem: '#yhdate',
                format: 'yyyyMMdd'
            })
        })
    }


    function getPageElem() {
        //从预销管理传过来的信息
        var paramsInfo = $.getUrlParam("ids");
        var projectId = $.getUrlParam("projectId");
        var projectName = $.getUrlParam("projectName");
        var pageEle = {
            paramsInfo: paramsInfo,
            projectName:projectName,
            projectId:projectId,
            $receConfirmTable: $("#prepaid-confirmtable"),
            $confirmBtn: $("#confirm-btn"),
            // 付款方
            $payment: $("#payment"),
            $recevieTime: $("#recevietime")
        };
        $("#projectid").val(projectName);
        return pageEle;
    }


    function getMaskElem() {
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
        var $maskElem = getMaskElem();

        var prepaidParam = $pageElem.paramsInfo;
        $.get(URL+"/yhglobal/coupon/selectByPurchaseCouponLedgerId",{
            ids:prepaidParam,
            projectId: $pageElem.projectId
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
             $pageElem.$receConfirmTable.bootstrapTable('updateCell', { index: _index, field: 'receiptAmountDouble', value: amountin});
            $pageElem.$receConfirmTable.bootstrapTable("refresh");

        });


        var isAjaxFlag =false;

        $pageElem.$confirmBtn.click(function () {
           var $philipDocumentNo = $("#philip-no");
           var confirmData = $pageElem.$receConfirmTable.bootstrapTable("getData");
           var philipDocumentNo = $philipDocumentNo.val();
            var arrParams = [{}];
            $.each(confirmData,function (index,confirmDataItem) {
                arrParams[index] = {
                    confirmAmountDouble:confirmDataItem._confirmAmountDouble,
                    receiptAmountDouble:confirmDataItem._receiptAmountDouble,
                    purchaseCouponLedgerId: confirmDataItem.purchaseCouponLedgerId
                }
           });
            arrParams = JSON.stringify(arrParams);
           var submitParams = {
               projectId: $pageElem.projectId,
               projectName: $pageElem.projectName,
               useDate: $("#yhdate").val(),
               accountType: $("#accounttype").val(),
               philipDocumentNo:$philipDocumentNo.val(),
               confirmInfoJson:arrParams
           };
           if(confirmData.length !== 0) {
               if (philipDocumentNo !== null) {
                   if(isAjaxFlag){
                       return;
                   }
                   isAjaxFlag = true;
                   $.ajax({
                       type: "POST",
                       url: URL + "/yhglobal/coupon/writeroff",
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
                           if (res.returnCode === 0 || res.returnCode === 1407 || res.returnCode === 1408) {
                               $.renderLayerMessage(res.message);
                               setTimeout(function(){
                                   window.location.href = "./rebate-confirm.html"
                               },3000)
                           }else {
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
               }
           }else {
               $.renderLayerMessage("philip单据号不能为空");
           }
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
                field: 'couponStatusString',
                title: '确认状态'
            }, {
                field: 'supplierOrderNo',
                title: '订单号'
            },{
                field: 'couponTypeString',
                title: '返利类型'
            }, {
                field: 'currencyCode',
                title: '应收币种'
            },{
                field: 'estimatedAmountDoubleStr',
                title: '应收金额'
            },{
                field: 'currencyCode',
                title: '待确认币种'
            }, {
                field: 'toBeConfirmAmountDoubleStr',
                title: '待确认金额'
            },{
                field: 'currencyCode',
                title: '确认币种'
            },{
                field: '_confirmAmountDouble',
                title: '确认金额',
                align: 'center',
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
            }, {
                field: 'confirmCurrencyCode',
                title: '实收币种',
                align: 'center',
                formatter: function (value) {
                    if(!value){
                        return "CNY"
                    }
                }
            },  {
                field: '_receiptAmountDouble',
                title: '实收金额',
                align: 'center',
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