/**
 * @authors {Pinocchio} (http://csshack.org)
 * @date    2018-03-06 10:40:05
 * @version $Id$
 */

$(function() {

    var URL = $.getfenxiaoURL();

    // function getParams() {
    //     var $productCode = $("#productCode");
    //     var $productName = $("#productName");
    //     var $projectId = $("#projectlist");
    //     var obj = {
    //         productCode: $productCode.val(),
    //         productName: $productName.val(),
    //         projectId: $projectId.val()
    //     }
    //     return obj;
    // }

    //初始化
    function init() {
        //初始化表格数据
        var $yhtable = $("#yhtable");
        var $queryBtn = $("#yhquery-btn");

        function queryTableData() {
            // var queryParams = getParams();
            $.get(URL + "/wmsconfirm/getInstocks", function (res) {
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
                title: '订单状态',
                formatter: function (value, row) {
                    if (row.status == "1") {
                        return "入库完成";
                    } else  {
                        return "待入库";
                    }
                }

            }, {
                title: '操作',
                width: 90,
                formatter: function () {
                    return '<a class="entry-submit" href="javascript:;">确认入库</a>'
                },
                events: {
                    'click .entry-submit': function (e, value, row) {
                        var confirmParams = {
                            instockInfo: row.record
                        }
                        $.post(URL + "/wmsconfirm/confirmInstock", confirmParams, function (res) {
                            if (res.returnCode === 0) {
                                alert("入库确认成功！");
                            } else {
                                alert("入库确认失败！");
                            }
                        }, "json")
                    }
                }
            }]
        })
    }
})