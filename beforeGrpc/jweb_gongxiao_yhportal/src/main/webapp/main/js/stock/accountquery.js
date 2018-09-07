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

        // 获取页面元素
        var $pageElement = getPageElement();

        // 查询
        $pageElement.$queryBtn.click(function () {
            $pageElement.$yhTable.bootstrapTable("refreshOptions",{pageNumber:1});
        });

        // 动态渲染下拉表单
        renderSelectList(URL);
    }

    // 获取页面DOM元素
    function getPageElement() {
        var $queryBtn = $('#yhquery-btn');
        var $yhTable = $('#yhtable');
        return {
            $queryBtn: $queryBtn,
            $yhTable: $yhTable
        }
    }

    // 动态渲染下拉表单
    function renderSelectList(URL) {
        layui.config({
            version: '1515376178709' //为了更新 js 缓存
        });
        layui.use(['laydate'],function () {
            var laydate = layui.laydate;
            // 开始日期，结束日期，要求到货日期，到货截至日期，创建时间，普通日期，业务日期
            var dateElements = ['yhstart-date','yhend-date'];
            $.each(dateElements,function (index,dateItem) {
                $("#"+dateItem).each(function () {
                    laydate.render({
                        elem: this,
                        format: 'yyyy-MM-dd'
                    });
                })
            })
        });

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
            // 商品型号
            productCode : $("#productcode").val(),
            // 商品型号
            productName : $("#productname").val(),
            // 仓库id
            warehouseid : $("#warehouselist").val(),
            // 起始时间
            startdate : $("#yhstart-date").val(),
            // 结束时间
            enddate : $("#yhend-date").val(),
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
        var $yhTable = $('#yhtable');
        $yhTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/inventory/standFlow",
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
                field: 'dateTime',
                title: '要求到货时间',
                formatter: function (value) {
                    return $.transformTime(value);
                }
            }, {
                field: 'productId',
                title: '货品ID'
            }, {
                field: 'productModel',
                title: '型号'
            }, {
                field: 'availableQuantity',
                title: '货品名称'
            }, {
                field: 'warehouseName',
                title: '仓库名称'
            }, {
                field: 'beginTotalAmount',
                title: '期初总量'
            }, {
                field: 'beginNonDefective',
                title: '期初良品'
            }, {
                field: 'beginDefective',
                title: '期初次品'
            }, {
                field: 'inStockTotalAmount',
                title: '入库总量'
            }, {
                field: 'inStockNonDefectiveAmount',
                title: '入库良品'
            }, {
                field: 'inStockDefectiveAmount',
                title: '入库次品'
            }, {
                field: 'outStockTotalAmount',
                title: '出库总量'
            }, {
                field: 'nonDefectiveSaleAmount',
                title: '良品销售发货'
            }, {
                field: 'nonDefectiveOtherAmount',
                title: '良品其他出库'
            }, {
                field: 'endTotalAmount',
                title: '期末总量'
            }, {
                field: 'endNonDefectiveAmount',
                title: '期末良品'
            }, {
                field: 'endDefectiveAmount',
                title: '期末次品'
            }, {
                field: 'onPurchaseAmount',
                title: '采购在途'
            }, {
                field: 'onTransferInAmount',
                title: '调拨在途'
            }, {
                field: 'onTransferOutAmount',
                title: '退仓调拨在途'
            }, {
                field: 'nonDefectiveProfitkAmount',
                title: '良品盘盈'
            }, {
                field: 'defectiveProfitAmount',
                title: '次品盘盈'
            }, {
                field: 'defectiveOutAmount',
                title: '次品出库'
            }, {
                field: 'nonDefectiveLossAmount',
                title: '良品盘亏'
            }, {
                field: 'defectiveLossAmount',
                title: '次品盘亏'
            }, {
                field: 'adjustmentAccountTotalOut',
                title: '调账出库总量'
            }, {
                field: 'adjustmentAccountNonDefectiveOut',
                title: '调账出库良品'
            }, {
                field: 'adjustmentAccountDefectiveOut',
                title: '调账出库非良品'
            }, {
                field: 'adjustmentAccountTotalIn',
                title: '调账入库总量'
            }, {
                field: 'adjustmentAccountNonDefectiveIn',
                title: '调账入库良品'
            }, {
                field: 'modifyDefectiveAmountIn',
                title: '调账入库非良品'
            }, {
                field: 'modifyTotalAmountIn',
                title: '调整入库总量'
            }, {
                field: 'modifyNonDefectiveAmountIn',
                title: '调整入库良品'
            }, {
                field: 'adjustmentAccountDefectiveIn',
                title: '调整入库非良品'
            }]
        })
    }
});
