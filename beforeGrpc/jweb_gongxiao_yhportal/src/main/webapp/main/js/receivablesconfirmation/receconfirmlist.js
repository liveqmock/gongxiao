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
                elem: '#recevietime',
                format: 'yyyyMMdd'
            })
        })
    }


    function getPageElem() {
        //从预销管理传过来的信息
        var paramsInfo = $.getUrlParam("confirmList");
        var infoFromPrev = JSON.parse(decodeURIComponent(paramsInfo));
        var pageEle = {
            $receConfirmTable: $("#receconfirmtable"),
            $confirmBtn: $("#confirm-btn"),
            // 付款方
            $payment: $("#payment"),
            $recevieTime: $("#recevietime"),
            tableInfo: infoFromPrev
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
        return maskElem
    }


    function confirmInit() {
        renderPageEle();
        var $pageElem = getPageElem();
        var $maskElem = getMaskElem();

        var confirmListLoadData = $pageElem.tableInfo;
        receconfirmTableRender();
        $pageElem.$receConfirmTable.bootstrapTable('load',confirmListLoadData);

        $maskElem.$yhmaskSubmit.click(function () {
            var _index = $maskElem.$yhMask.attr("yhtable-index");
            $maskElem.$yhMask.addClass('yhmask-hide');
            var confirMamount = $maskElem.$confirMamount.val();
            var amountin = $maskElem.$amountin.val();
            // 更新单元格数据
             $pageElem.$receConfirmTable.bootstrapTable('updateCell', { index: _index, field: 'confirmAmountDouble', value: confirMamount });
            // 更新单元格数据
             $pageElem.$receConfirmTable.bootstrapTable('updateCell', { index: _index, field: 'recipientAmountDouble', value: amountin });

        });

        var isAjaxFlag = false;
        $pageElem.$confirmBtn.click(function () {
           var confirData = $pageElem.$receConfirmTable.bootstrapTable("getData");
           $.each(confirData,function (index,confirDataItem) {
              if(confirDataItem[index]){
                  delete confirDataItem[index];
              }
           });
           confirData = JSON.stringify(confirData);
           var submitParams = {
               payer:$pageElem.$payment.val(),
               recipientDateString:$pageElem.$recevieTime.val(),
               itemsJson:confirData
           };
           if(confirData.length !== 0){
               if(isAjaxFlag){
                   return;
               }
               isAjaxFlag = true;
               $.ajax({
                   type: "POST",
                   url: URL + "/sales/confirm/confirm",
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
                           window.location.href = "./receconfirm.html";
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
           }
        })
    }



    function receconfirmTableRender() {
        var $receConfirmTable = $("#receconfirmtable");
        var confirmData = [{}];
        var $maskElem = getMaskElem();
        $receConfirmTable.bootstrapTable({
            undefinedText: "",
            data: confirmData,
            striped: true,
            columns: [{
                field: 'recipientStatus',
                title: '收款状态'
            }, {
                field: 'salesOrderNo',
                title: '订单号'
            }, {
                field: 'recipient',
                title: '收款方'
            }, {
                field: 'shouldReceiveCurrency',
                title: '应收币种'
            }, {
                field: 'shouldReceiveAmountDouble',
                title: '应收金额'
            }, {
                field: 'unreceiveCurrency',
                title: '待确认币种'
            },{
                field: 'unreceiveAmountDouble',
                title: '待确认金额',
                formatter: function (value,row) {
                    if(!row.recipientAmountDouble || !value){
                        return value
                    }else{
                        var loadUnreceiveAmount = row.unreceiveAmountDouble - row.confirmAmountDouble;
                        row.unreceiveAmountDouble = loadUnreceiveAmount;
                        return loadUnreceiveAmount;
                    }
                }
            },{
                field: 'confirmCurrency',
                title: '确认币种'
            },{
                field: 'confirmAmountDouble',
                title: '确认金额'
            }, {
                field: 'recipientCurrency',
                title: '实收币种'
            },  {
                field: 'recipientAmountDouble',
                title: '实收金额'
            }, {
                title: '操作',
                striped: false,
                formatter: function () {
                    return '<a href="javascript:;" class="yhtable-revise">'+'修改金额'+'</a>';
                },
                events: reviseEvents = {
                    'click .yhtable-revise': function (event,value,row,index) {
                        $maskElem.$yhMask.attr("yhtable-index",index);
                        $maskElem.$yhMask.removeClass("yhmask-hide");
                        $maskElem.$confirMamount.val(row.unreceiveAmountDouble);
                        $maskElem.$amountin.val(row.unreceiveAmountDouble);
                    }
                }
            }]
        })
    }
});