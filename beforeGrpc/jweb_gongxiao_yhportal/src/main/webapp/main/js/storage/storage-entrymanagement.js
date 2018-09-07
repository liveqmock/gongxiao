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
        var $yhTable = $('#yhtable');
        $.renderDateRange("create-time1","create-time2");
        $("#document-type").select2({
            minimumResultsForSearch: 6
        });
        // 查询
        $queryBtn.click(function () {
            $yhTable.bootstrapTable("refreshOptions",{pageNumber:1});
        });
    }

    // 动态渲染下拉表单
    function renderSelectList(URL) {
        // 获取project列表
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName")
            .then(function () {
                // 获取收货仓库列表
                $.getSelectOptions("warehouse-list", URL + "/purchase/foundation/warehouseList", "warehouseId", "warehouseName");
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
            projectId:$("#projectlist").val(),
            gonxiaoInboundNo:$("#entry-no").val(),
            batchNo:$("#batch-number").val(),
            orderType:$("#document-type").val(),
            warehouseId:$("#warehouse-list").val(),
            supplier:$("#supplier-list").val(),
            wmsInboundOrderNo:$("#entry-notice-no").val(),
            createTimeBegin:$("#create-time1").val(),
            createTimeTo:$("#create-time2").val(),
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
        var $yhTable = $('#yhtable');
        $yhTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/warehouseManage/inboundNotify/inboundOrder/select",
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
                field: 'wmsInboundOrderNo',
                title: '仓库入库单号',
                visible: false
            },{
                field: 'gongxiaoInboundOrderNo',
                title: '入库单号',
                formatter: function (value, row) {
                    if(value){
                        return '<a class="table-yh-link skipreview" href="./storage-entrydetail.html?gongxiaoInboundOrderNo=' + row.gongxiaoInboundOrderNo + '&wmsInboundOrderNo=' + row.wmsInboundOrderNo + '&projectId=' + projectId + '&projectText=' +projectName+'">' + row.gongxiaoInboundOrderNo + '</a>';
                    }else{
                        return ""
                    }
                }
            }, {
                field: 'purchaseOrderNo',
                title: '采购单号'
            }, {
                field: 'batchNo',
                title: '批次号'
            }, {
                field: 'inboundType',
                title: '单据类型',
                formatter: function (value,row) {
                    if (row.inboundType === 101){
                        return "采购入库单";
                    }else if (row.inboundType === 102){
                        return "调拨入库单";
                    }else if (row.inboundType === 103){
                        return "退货入库单";
                    }else if (row.inboundType === 90006){
                        return "其它入库";
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
                field: 'inStockQuantity',
                title: '实际入库数量'
            }, {
                field: 'createTime',
                title: '创建时间',
                formatter: function (value) {
                    return $.transformTime(value,"seconds");
                }
            }]
        })
    }
});
