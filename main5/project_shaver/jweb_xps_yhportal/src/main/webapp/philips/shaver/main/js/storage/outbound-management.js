/**
 * @authors {Pinocchio} (http://csshack.org)
 * @date    2018-03-06 10:40:05
 * @version $Id$
 */

/**
 * 出库单js
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


        $("#document-type").select2({
            minimumResultsForSearch: 6
        });
        $.renderDateRange("create-time1","create-time2");

        // 获取页面元素
        getPageElement();

        // 动态渲染下拉表单
        renderSelectList(URL);
    }

    // 获取页面DOM元素
    function getPageElement() {
        var $queryBtn = $('#query-btn');
        var $yhTable = $('#yhtable');
        // 查询
        $queryBtn.click(function () {
            $yhTable.bootstrapTable("refreshOptions",{pageNumber:1});
        });
        return {
            $queryBtn: $queryBtn,
            $yhTable: $yhTable
        }
    }

    // 动态渲染下拉表单
    function renderSelectList(URL) {
        // 获取project列表
        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
            .then(function () {
                // 获取收货仓库列表
                $.getSelectOptions("warehouse-list", URL + "/warehouse/selectWarehouseList", "warehouseId", "warehouseName");
                // 初始化查询
                tableRender(URL);
            });
    }

    // 获取表格需要查询的参数
    function getQueryParams(params) {
        return {
            // project
            projectId : $("#projectlist").val(),
            //入库单号
            gongxiaoOutNo : $("#outbound-no").val(),
            //销售单号
            salesNo : $("#sales-number").val(),
            //货品编码
            orderType : $("#document-type").val(),
            //创建时间
            createTimeBeging : $("#create-time1").val(),
            //创建时间
            createTimeLast : $("#create-time2").val(),
            //客户
            customer : $("#customer").val(),
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        }
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


    // 表格渲染
    function tableRender(URL) {
        var $projectList = $("#projectlist");
        var projectId = $projectList.val();
        var projectName = $projectList.children("option:selected").text();
        var $yhTable = $('#yhtable');
        $yhTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/warehouseManage/outboundNotify/selectOutboundOrder",
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
                field: 'wmsOutboundOrderNo',
                title: 'wms出库单号',
                visible: false
            },{
                field: 'gongxiaoOutboundOrderNo',
                title: '出库单号',
                formatter: function(value, row) {
                    if(value){
                        return '<a class="table-yh-link" href="./storage-outbounddetail.html?gongxiaoOutboundOrderNo=' + row.gongxiaoOutboundOrderNo +'&wmsOutboundOrderNo='+ row.wmsOutboundOrderNo + '&projectId='+projectId+'&projectName='+projectName+'">' + row.gongxiaoOutboundOrderNo + '</a>';
                    }else{
                        return ""
                    }
                }
            }, {
                field: 'projectId',
                title: '项目id',
                visible: false
            }, {
                field: 'salesOrderNo',
                title: '销售单号'
            }, {
                field: 'outboundType',
                title: '单据类型',
                formatter: function (value,row) {
                    if (row.outboundType === 901){
                        return "普通出库单";
                    }else if (row.outboundType === 301){
                        return "调拨出库单";
                    }else if (row.outboundType === 5252){
                        return "采购退货出库";
                    }else if (row.outboundType === 90005){
                        return "其它出库";
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
                title: '发货仓库'
            }, {
                field: 'outStockQuantity',
                title: '出库数量'
            }, {
                field: 'customer',
                title: '客户'
            }, {
                field: 'createTime',
                title: '创建时间',
                formatter: function(value){
                    return $.transformTime(value,"seconds");
                }

            }]
        })
    }
});
