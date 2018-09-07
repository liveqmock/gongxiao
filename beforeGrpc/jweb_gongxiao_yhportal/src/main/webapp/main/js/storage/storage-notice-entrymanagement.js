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

        layui.config({
            version: '1515376178709' //为了更新 js 缓存
        });
        layui.use(['laydate'],function () {
            var laydate = layui.laydate;
            laydate.render({
                elem: "#createtime",
                format: 'yyyy-MM-dd'
            });
        });

        var purchaseOrderNo = $.getUrlParam("purchaseOrderNumber");
        $("#purchaseno").val(purchaseOrderNo);

        // 获取页面元素
        getPageElement();

        // 动态渲染下拉表单
        renderSelectList(URL);
    }

    // 获取页面DOM元素
    function getPageElement() {
        var $queryBtn = $('#query-btn');
        var $stockquerytable = $('#stockquerytable');
        // 查询
        $queryBtn.click(function () {
            $stockquerytable.bootstrapTable("refreshOptions",{pageNumber:1});
        });
        return {
            $queryBtn: $queryBtn,
            $stockquerytable: $stockquerytable
        }
    }

    // 动态渲染下拉表单
    function renderSelectList(URL) {
        // 获取project列表
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName")
            .then(function () {
                // 获取收货仓库列表
                $.getSelectOptions("warehouselist", URL + "/purchase/foundation/warehouseList", "warehouseId", "warehouseName");
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
            //project
            projectId : $("#projectlist").val(),
            //入库单号
            gonxiaoInboundNo : $("#gongxiaono").val(),
            //采购单号
            purchaseNo : $("#purchaseno").val(),
            //货品编码
            productCode : $("#productcode").val(),
            //创建时间
            dateTime : $("#createtime").val(),
            //仓库名称
            warehouseId : $("#warehouselist").val(),
            // warehouseName : $("#warehouselist").children("option:selected").text(),
            //产品编码
            goodCode : $("#procode").val(),
            //供应商
            supplier : $("#supplier").val(),
            // 首页页码
            pageNumber : params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        }
    }

    // 表格渲染
    function tableRender(URL) {
        var $projectList = $("#projectlist");
        var projectId = $projectList.val();
        var projectName = $projectList.children("option:selected").text();
        var $stockquerytable = $('#stockquerytable');
        $stockquerytable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/warehouseManage/inbound/inboundOrder/select",
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
                field: 'gongxiaoInboundOrderNo',
                title: '入库单号',
                formatter: function (value, row) {
                    if(value){
                        return '<a class="table-yh-link skipreview" href="./storage-notice-entrydetail.html?gongxiaoInboundOrderNo=' + row.gongxiaoInboundOrderNo + '&projectId=' + projectId + '&projectText=' +projectName+'">' + row.gongxiaoInboundOrderNo + '</a>';
                    }else{
                        return ""
                    }
                }
            }, {
                field: 'purchaseOrderNo',
                title: '采购单号'
            }, {
                field: 'inboundType',
                title: '单据类型'
            }, {
                field: 'orderStatus',
                title: '单据状态',
                formatter: function (value, row) {
                    if (row.orderStatus === 1){
                        return "等待收货";
                    }else if(row.orderStatus === 2){
                        return "部分收货";
                    }else if(row.orderStatus === 3){
                        return "收货完成";
                    }else if(row.orderStatus === 4){
                        return "订单已取消";
                    }else if(row.orderStatus === 5){
                        return "订单已关闭";
                    }else {
                        return "";
                    }
                }
            }, {
                field: 'warehouseId',
                title: '收货仓库id',
                visible: false
            }, {
                field: 'warehouseName',
                title: '收货仓库'
            }, {
                field: 'planInStockQuantity',
                title: '计划入库数量'
            }, {
                field: 'actualInStockQuantity',
                title: '实际入库数量'
            }, {
                field: 'differentQuantity',
                title: '差异数量'
            }, {
                field: 'estimateArriveTime',
                title: '预计到仓时间',
                formatter: function (value) {
                    return $.transformTime(value,"seconds");
                }
            }, {
                field: 'createTime',
                title: '创建时间',
                formatter: function (value) {
                    return $.transformTime(value,"seconds");
                }
            }, {
                title: '操作',
                width: 120,
                formatter: function (value, row) {
                    if(row['gongxiaoInboundOrderNo']){
                        if (row.orderStatus === 0){
                            return '<a class="skipreview" href="./storage-notice-entrydetail.html?gongxiaoInboundOrderNo=' + row.gongxiaoInboundOrderNo + '&projectId=' + projectId + '&projectText=' +projectName+'">' + '入库详情' + '</a>  '+'<a class="cancel-submit" href="javascript:void(0)">取消</a>';
                        }else if (row.orderStatus === 1){
                            return '<a class="skipreview" href="./storage-notice-entrydetail.html?gongxiaoInboundOrderNo=' + row.gongxiaoInboundOrderNo + '&projectId=' + projectId + '&projectText=' +projectName+'">' + '入库详情' + '</a>  '+'<a class="close-submit" href="javascript:void(0)">关闭</a>';
                        }else {
                            return '<a class="skipreview" href="./storage-notice-entrydetail.html?gongxiaoInboundOrderNo=' + row.gongxiaoInboundOrderNo + '&projectId=' + projectId + '&projectText=' +projectName+'">' + '入库详情' + '</a>';
                        }
                    }else{
                        return ""
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
                                    gongxiaoInboundOrderNo : row.gongxiaoInboundOrderNo
                                };
                                $.ajax({
                                    url: URL + "/stock/notifyWmsCancelInStock",
                                    type: "GET",
                                    data: confirmParams,
                                    dataType: "json",
                                    success: function (res) {
                                        if (res.returnCode === 0) {
                                            $stockquerytable.bootstrapTable("refreshOptions", {pageNumber: 1});
                                            layer.closeAll('dialog');
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
                                    gongxiaoInboundOrderNo : row.gongxiaoInboundOrderNo
                                };
                                $.ajax({
                                    url: URL + "/close/inStock",
                                    type: "GET",
                                    data: confirmParams,
                                    dataType: "json",
                                    success: function (res) {
                                        if (res.returnCode === 0) {
                                            $stockquerytable.bootstrapTable("refreshOptions", {pageNumber: 1});
                                            layer.closeAll('dialog');
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
