/**
 * 进销存台账js
 * @authors {Pinocchio} (http://csshack.org)
 * @date    2018-03-06 10:40:05
 * @version $Id$
 */

$(function () {

    managementInit();

    // 初始化
    function managementInit() {
        // 全局接口
        var URL = $.getfenxiaoURL();

        // 查询
        $("#yhquery-btn").click(function () {
            $("#yhtable").bootstrapTable("refreshOptions", {pageNumber: 1});
        });

        // 动态渲染下拉表单
        renderSelectList(URL);
    }


    // 动态渲染下拉表单
    function renderSelectList(URL) {
        $.renderDateRange("createtime", "createtime2");
        $("#batch-status").select2({
            minimumResultsForSearch: 6
        });
        // 获取project列表
        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
            .then(function () {
                tableRender(URL);
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

    // 获取表格需要查询的参数
    function getQueryParams(params) {
        return {
            batchNo: $("#batch-no").val(),
            inboundOrderNo: $("#stock-in").val(),
            outboundOrderNo: $("#stock-out").val(),
            startDate: $("#createtime").val(),
            endDate: $("#createtime2").val(),
            status: $("#batch-status").val(),
            projectId: $("#projectlist").val(),
            pageNumber: params.pageNumber,
            pageSize: params.pageSize
        }
    }

    // 表格渲染
    function tableRender(URL) {
        var $yhTable = $('#yhtable');
        $yhTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/inventoryage/getInventoryAge",
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
            columns: [{
                field: 'status',
                title: '批次状态'
            }, {
                field: 'batchNo',
                title: '批次号'
            }, {
                field: 'productCode',
                title: '型号'
            }, {
                field: 'inboundQuantity',
                title: '入库数量'
            }, {
                field: 'purchaseOrderNo',
                title: '采购订单号'
            }, {
                field: 'inboundOrderNo',
                title: '入库单号'
            }, {
                field: 'createTime',
                title: '入库日期',
                formatter:function (value) {
                    return $.transformTime(value,"seconds");
                }
            }, {
                field: 'receiveFinishTime',
                title: '收货完成日期',
                formatter:function (value) {
                    return $.transformTime(value,"seconds");
                }
            }, {
                field: 'purchaseGuidPrice',
                title: '采购指导价'
            }, {
                field: 'purchasePrice',
                title: '采购价'
            }, {
                field: 'costPrice',
                title: '成本价'
            }, {
                field: 'batchInboundAmount',
                title: '批次进货金额'
            }, {
                field: 'inventoryAge',
                title: '库存库龄'
            }, {
                field: 'inventoryTurnover',
                title: '库存周转率'
            }, {
                field: 'restAmount',
                title: '剩余数量'
            }]
            // }, {
            //     field: 'salesOrderNo',
            //     title: '销售订单号'
            // }, {
            //     field: 'outboundOrderNo',
            //     title: '出库单号'
            // }, {
            //     field: 'salesGuidPrice',
            //     title: '销售指导价'
            // }, {
            //     field: 'outboundPrice',
            //     title: '出货价'
            // }, {
            //     field: 'outboundQuantity',
            //     title: '出货数量'
            // }, {
            //     field: 'batchOutboundAmount',
            //     title: '出货总金额'
            // }, {
            //     field: 'customerUseCoupon',
            //     title: '客户使用返利'
            // }, {
            //     field: 'customerUsePrepaid',
            //     title: '客户使用代垫'
            // }, {
            //     field: 'nonDefectiveLossAmount',
            //     title: '出库类型'
            // }, {
            //     field: 'outboundDate',
            //     title: '出货日期'
            // }, {
            //     field: 'inventoryAge',
            //     title: '库龄'
            // }, {
            //     field: 'sightTime',
            //     title: '签收时间'
            // }]
        })
    }
});
