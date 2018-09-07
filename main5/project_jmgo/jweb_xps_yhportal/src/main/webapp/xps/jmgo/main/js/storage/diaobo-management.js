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
        layui.config({
            version: '1515376178709' //为了更新 js 缓存
        });
        layui.use(['laydate'],function () {
            var laydate = layui.laydate;
            laydate.render({
                elem: '#create-begin-time',
                format: 'yyyy-MM-dd'
            });
            laydate.render({
                elem: '#create-end-time',
                format: 'yyyy-MM-dd'
            });
        });
        // 获取project列表
        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
            .then(function () {
                // 获取收货仓库列表
                $.getSelectOptions("warehouseentrylist", URL + "/warehouse/selectWarehouseList", "warehouseId", "warehouseName");
                $.getSelectOptions("warehouseoutlist", URL + "/warehouse/selectWarehouseList", "warehouseId", "warehouseName");
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
            //调拨单号
            allocateNo: $("#diaobono").val(),
            //调拨入库单号
            gongxiaoInboundOrderNo : $("#gongxiaoentryno").val(),
            //调拨出库单号
            gongxiaoOutboundOrderNo : $("#gongxiaooutno").val(),
            //调出仓库名称
            warehouseOut : $("#warehouseoutlist").val(),
            //调入仓库名称
            warehouseEnter : $("#warehouseentrylist").val(),
            //创建时间
            createBeginTime : $("#create-begin-time").val(),
            //创建结束时间
            createEndTime : $("#create-end-time").val(),
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
        var $stockquerytable = $('#stockquerytable');
        $stockquerytable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/allocte/selectAllocateOrder",
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
                field: 'allocateNo',
                title: '调拨单号',
                formatter: function (value, row) {
                    if(value){
                        return '<a class="table-yh-link" href="./diaobo-detail.html?allocateNo=' + row.allocateNo + '&projectId=' + projectId +'">' + row.allocateNo + '</a>';
                    }else{
                        return ""
                    }
                }
            }, {
                field: 'status',
                title: '状态',
                formatter: function (value, row) {
                    if(value){
                        if (row.status === 1){
                            return '调拨未完成';
                        }else{
                            return '调拨完成';
                        }
                    }else{
                        return ""
                    }
                }
            }, {
                field: 'gongxiaoOutboundOrderNo',
                title: '出库单号'
            }, {
                field: 'gongxiaoInboundOrderNo',
                title: '入库单号'
            },  {
                field: 'warehouseOut',
                title: '调出仓库'
            },  {
                field: 'warehouseEnter',
                title: '调入仓库'
            }, {
                field: 'alloteWay',
                title: '调拨方式'
            }, {
                field: 'createTime',
                title: '创建时间',
                formatter: function (value) {
                    return $.transformTime(value,"seconds");
                }
            }, {
                title: '操作',
                width: 80,
                formatter: function (value, row) {
                    if(row['allocateNo']){
                        if (row.status === 0){
                            return '<a class="sight-submit btn btn-primary" href="javascript:;">确认调拨</a>'
                        }else{
                            return '<a href="javascript:void(0);">调拨完成</a>'
                        }
                    }else{
                        return ""
                    }

                },
                events: {
                    'click .sight-submit': function (e, value, row) {
                        var confirmParams = {
                            projectId: row.projectId,
                            orderNo : row.gongxiaoInboundOrderNo
                        };
                        $.post(URL + "/allocte/updateByAllocateNo", confirmParams, function (res) {
                            if (res.returnCode === 0) {
                                $stockquerytable.bootstrapTable("refreshOptions",{pageNumber:1});
                            } else{
                                $.renderLayerMessage("确认调拨失败！");
                            }
                        }, "json")
                    }
                }
            }]
        })
    }
});
