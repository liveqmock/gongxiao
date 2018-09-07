/**
 * 出入库流水台账
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
            // 商品型号
            productCode : $("#productcode").val(),
            // 商品型号
            productName : $("#productname").val(),
            // 仓库id
            warehouseId : $("#warehouselist").val(),
            //订单号
            orderNo : $("#orderno").val(),
            //起始时间
            startDate : $("#startdate").val(),
            //结束时间
            endDate : $("#enddate").val(),
            // 项目id
            projectId : $("#projectlist").val(),
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        }
    }

    // 表格渲染
    function tableRender(URL) {
        var $stockquerytable = $('#stockquerytable');
        $stockquerytable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/inventory/inventoryFlow",
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
                field: 'orderNo',
                title: '出/入库单号'
            }, {
                field: 'batchNo',
                title: '批次号'
            }, {
                field: 'productModel',
                title: '型号'
            }, {
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'warehouseName',
                title: '仓库名称'
            }, {
                field: 'createTime',
                title: '出入库时间',
                formatter: function (value) {
                    return $.transformTime(value,"seconds");
                }
            }, {
                field: 'orderType',
                title: '单据类型'
            }, {
                field: 'inventoryFlowType',
                title: '库存类型'
            }, {
                field: 'amountBeforeTransaction',
                title: '流水发生前的数量'
            }, {
                field: 'transactionAmount',
                title: '出入数量'
            }, {
                field: 'perfectQuantity',
                title: '良品'
            }, {
                field: 'imperfectQuantity',
                title: '残次品'
            }, {
                field: 'amountAfterTransaction',
                title: '结存数量'
            }, {
                field: 'relatedOrderNo',
                title: '采购/销售单号'
            }, {
                field: 'inventoryFlowId',
                title: '外部流水号'
            }]
        })
    }
});
