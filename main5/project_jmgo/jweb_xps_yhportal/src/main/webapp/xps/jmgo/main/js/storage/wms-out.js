/**
 * @authors {Pinocchio} (http://csshack.org)
 * @date    2018-03-06 10:40:05
 * @version $Id$
 */

$(function() {

    var URL = $.getfenxiaoURL();

    //初始化
    function init() {
        //初始化表格数据
        var $yhtable = $("#yhtable");
        var $queryBtn = $("#yhquery-btn");

        function queryTableData() {
            // var queryParams = getParams();
            $.get(URL + "/wmsconfirm/getOutstocks", function (res) {
                if (res.returnCode === 0) {
                    renderTable();
                    var entryTableLoadData = res.data;
                    $yhtable.bootstrapTable('load', entryTableLoadData);
                }
            }, "json")
        }


        $queryBtn.click(function () {
            queryTableData();
        });

    }
    init();

    //渲染表格数据
    function renderTable() {
        var $yhTable = $("#yhtable");
        var queryData = [{}];
        $yhTable.bootstrapTable({
            undefinedText: '',
            striped: true,
            data: queryData,
            columns: [{
                field: 'id',
                title: '序号'
            }, {
                field: 'record',
                title: '入库记录'
            }, {
                field: 'status',
                title: '订单状态'
            }, {
                title: '操作',
                width: 90,
                formatter: function () {
                    return '<a class="out-submit" href="javascript:;">确认出库</a>'
                },
                events: {
                    'click .out-submit': function (e, value, row) {
                        var confirmParams = {
                            outstockInfo: row.record
                        }
                        $.post(URL + "/wmsconfirm/confirmOutstock", confirmParams, function (res) {
                            if (res.returnCode === 0) {
                                alert("出库确认成功！");
                            } else {
                                alert("出库确认失败！");
                            }
                        }, "json")
                    }
                }
            }]
        })
    }
})