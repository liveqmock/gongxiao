/**
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

        $.renderDateRange("create-time1","create-time2");
        $.renderDateRange("finish-time1","finish-time2");

        var salesOrderNo = $.getUrlParam("salesOrderNo");
        $("#xiaoshouno").val(salesOrderNo);

        // 获取页面元素
        getPageElement();

        // 动态渲染下拉表单
        renderSelectList(URL);
    }

    // 获取页面DOM元素
    function getPageElement() {
        var $queryBtn = $('#query-btn');
        var $storageEntryTable = $('#storageentrytable');
        // 查询
        $queryBtn.click(function () {
            $storageEntryTable.bootstrapTable("refreshOptions",{pageNumber:1});
        });
        return {
            $queryBtn: $queryBtn,
            $storageEntryTable: $storageEntryTable
        }
    }

    // 动态渲染下拉表单
    function renderSelectList(URL) {
        // 获取project列表
        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
            .then(function () {
                // 获取收货仓库列表
                $.getSelectOptions("warehouselist", URL + "/warehouse/selectWarehouseList", "warehouseId", "warehouseName");
                // 初始化查询
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
            // project
            projectId : $("#projectlist").val(),
            //入库单号
            gongxiaoOutNo : $("#gongxiaono").val(),
            //销售单号
            salseNo : $("#xiaoshouno").val(),
            //货品编码
            productCode : $("#productcode").val(),
            //创建时间
            createTimeBeging : $("#create-time1").val(),
            //创建时间
            createTimeLast : $("#create-time2").val(),
            //仓库名称
            warehouseId : $("#warehouselist").val(),
            //出库完成时间
            finishTimeBegin : $("#finish-time1").val(),
            //出库完成时间
            finishTimeLast : $("#finish-time2").val(),
            //供应商
            supplier : $("#supplier").val(),
            //客户
            customer : $("#customer").val(),
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        }
    }

    // 表格渲染
    function tableRender(URL) {
        var $projectList = $("#projectlist");
        var projectId = $projectList.val();
        var projectName = $projectList.children("option:selected").text();
        var $storageEntryTable = $('#storageentrytable');
        $storageEntryTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/warehouseManage/outbound/selectOutboundOrder",
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
                field: 'gongxiaoOutboundOrderNo',
                title: '出库通知单号',
                formatter: function(value, row) {
                    if(value){
                        return '<a class="table-yh-link" href="./storage-notice-outbounddetail.html?gongxiaoOutboundOrderNo=' + row.gongxiaoOutboundOrderNo +'&productCode='+ row.productCode + '&projectId='+projectId+'&projectName='+projectName+'&inventoryType='+row.inventoryType+'">' + row.gongxiaoOutboundOrderNo + '</a>';
                    }else{
                        return ""
                    }
                }
            }, {
                field: 'outboundOrderItemNo',
                title: '明细单号',
                visible: false
            }, {
                field: 'productCode',
                title: '商品Code',
                visible: false
            }, {
                field: 'projectId',
                title: '项目id',
                visible: false
            }, {
                field: 'salesOrderNo',
                title: '销售单号'
            }, {
                field: 'inventoryType',
                title: '库存类型',
                visible: false
            }, {
                field: 'outStorageType',
                title: '单据类型'
            }, {
                field: 'orderStatus',
                title: '单据状态',
                formatter: function (value, row) {
                    if (row.orderStatus === 1){
                        return "等待发货";
                    }else if(row.orderStatus === 2){
                        return "部分发货";
                    }else if(row.orderStatus === 3){
                        return "发货完成";
                    }else if(row.orderStatus === 4){
                        return "订单已取消";
                    }else if(row.orderStatus === 5){
                        return "订单已关闭";
                    }else if(row.orderStatus === 6){
                        return "订单已签收";
                    }else if(row.orderStatus === 7){
                        return "订单同步到wms中.."
                    }else {
                        return ""
                    }
                }
            }, {
                field: 'warehouseId',
                title: '收货仓库id',
                visible: false
            }, {
                field: 'deliverWarehouse',
                title: '发货仓库'
            }, {
                field: 'plantQuantity',
                title: '计划出库数量'
            }, {
                field: 'actualQuantity',
                title: '实际出库数量'
            }, {
                field: 'differentQuantity',
                title: '差异数量'
            }, {
                field: 'supplier',
                title: '供应商'
            }, {
                field: 'customer',
                title: '客户'
            }, {
                field: 'createTime',
                title: '创建时间',
                formatter: function(value){
                    return $.transformTime(value,"seconds");
                }
            }, {
                title: '操作',
                formatter: function (value, row) {
                    if (row.orderStatus === 1){
                        return '<a class="cancel-submit btn btn-warning" href="javascript:void(0)">取消</a>';
                    }else if (row.orderStatus === 2){
                        return '<a class="close-submit btn btn-danger" href="javascript:void(0)">关闭</a>';
                    }else {
                        return "";
                    }

                },
                events: {
                    'click .cancel-submit': function (e, value, row) {
                        layui.use('layer',function () {
                            var layer = layui.layer;
                            layer.alert("您确认取消此订单吗？",{
                                icon: 0
                            },function () {
                                var confirmParams = {
                                    projectId: row.projectId,
                                    warehouseId: row.warehouseId,
                                    gongxiaoOutboundOrderNo: row.gongxiaoOutboundOrderNo
                                };
                                $.ajax({
                                    url: URL + "/stock/notifyWmsCancelOutStock",
                                    type: "GET",
                                    data: confirmParams,
                                    dataType: "json",
                                    success: function (res) {
                                        if (res.returnCode === 0) {
                                            $storageEntryTable.bootstrapTable("refreshOptions", {pageNumber: 1});
                                            layer.closeAll('dialog');
                                        }else{
                                            $.renderLayerMessage("取消订单失败！");
                                        }
                                    },
                                    error: function (err) {
                                        $.renderLayerMessage(err);
                                    }
                                });
                            })
                        });
                    },
                    'click .close-submit': function (e, value, row) {
                        layui.use('layer',function () {
                            var layer = layui.layer;
                            layer.alert("您确认关闭此订单吗？",{
                                icon: 0
                            },function () {
                                var confirmParams = {
                                    projectId: row.projectId,
                                    warehouseId: row.warehouseId,
                                    gongxiaoOutboundOrderNo: row.gongxiaoOutboundOrderNo
                                };
                                $.ajax({
                                    url: URL + "/close/outStock",
                                    type: "GET",
                                    data: confirmParams,
                                    dataType: "json",
                                    success: function (res) {
                                        if (res.returnCode === 0) {
                                            $storageEntryTable.bootstrapTable("refreshOptions", {pageNumber: 1});
                                            layer.closeAll('dialog');
                                        }else{
                                            $.renderLayerMessage("关闭订单失败！");
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
