/**
 * @authors {len} (http://csshack.org)
 * @date    2018/4/24
 */


$(function () {

var URL = $.getfenxiaoURL();

    confirmInit();

    // layui时间控件渲染
    function renderPageEle(){
        $.renderDateRange('orderstarttime','orderendtime');
    }


    function getPageElem() {
        var pageEle = {
            $receConfirmTable: $("#receconfirmtable"),
            $confirmBtn: $("#confirm-btn")
        };
        return pageEle;
    }

    function confirmInit() {
        renderPageEle();
        var $pageElem = getPageElem();
        // $.get(URL+"/sales/confirm/list",function (res) {
        //     if(res.returnCode == 0){
                receconfirmTableRender();
                // var confirmLoadData = res.data.list;
                var confirmLoadData = [{"confirmStatus":1,"unreceiveAmountDouble":111},{"confirmStatus":2,"unreceiveAmountDouble":222},{"confirmStatus":3,"unreceiveAmountDouble":333},{"confirmStatus":1,"unreceiveAmountDouble":222},{"confirmStatus":1,"unreceiveAmountDouble":333}];
                $pageElem.$receConfirmTable.bootstrapTable('load',confirmLoadData);
                $.each(confirmLoadData,function (index,dataItem) {
                    var fieldList=["selfcheckbox","confirmStatus","salesOrderNo","returnOrderType","distributorName","shouldReceiveCurrency","shouldReceiveAmountDouble"];
                    $.mergeCells(confirmLoadData, "confirmStatus", 1, $pageElem.$receConfirmTable,fieldList);
                });

            // }
        // },"json");

        $pageElem.$confirmBtn.click(function () {
           var selectedData = $pageElem.$receConfirmTable.bootstrapTable('getSelections');
           var strSelectedData = JSON.stringify(selectedData);
           if(selectedData.length !== 0){
                window.location.href = './receconfirmlist.html?confirmList='+ encodeURIComponent(strSelectedData);
           }else{
                $.renderLayerMessage("请勾选需要确认的项目")
           }
        })
    }


    function receconfirmTableRender() {
        var $receConfirmTable = $("#receconfirmtable");
        var confirmData = [{}];
        $receConfirmTable.bootstrapTable({
            undefinedText: "",
            data: confirmData,
            striped: true,
            columns: [{
                checkbox: true,
                field: 'selfcheckbox'
            },{
                field: 'confirmStatus',
                title: '确认状态',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'salesOrderNo',
                title: '订单号',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'returnOrderType',
                title: '项目',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'distributorName',
                title: '客户名称',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'shouldReceiveCurrency',
                title: '应收币种',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'shouldReceiveAmountDouble',
                title: '应收金额',
                align: 'center',
                valign: 'middle'
            }, {
                field: 'unreceiveAmountDouble',
                title: '未收金额',
                align: 'center'
            }, {
                field: 'confirmCurrency',
                title: '确认币种',
                align: 'center'
            },{
                field: 'confirmAmountDouble',
                title: '确认金额',
                align: 'center'
            }, {
                field: 'recipientCurrency',
                title: '实收币种',
                align: 'center'
            },  {
                field: 'recipientAmountDouble',
                title: '实收金额',
                align: 'center'
            }, {
                field: 'confirmTime',
                title: '确认时间',
                formatter: function (value) {
                    return $.transformTime(value);
                }
            }, {
                field: 'flowNo',
                title: '确认流水号',
                align: 'center'
            },{
                field: 'recipientStatus',
                title: '收款状态',
                align: 'center'
            }]
        })
    }
});