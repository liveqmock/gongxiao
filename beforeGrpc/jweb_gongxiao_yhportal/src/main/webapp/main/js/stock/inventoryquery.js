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
        var $storageEntryTable = $("#storageentrytable");
        var $queryBtn = $("#query-btn");


        // 渲染projectlist
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName")
            .then(function () {
                renderTable(URL)
            });

        $queryBtn.click(function () {
            $storageEntryTable.bootstrapTable("refreshOptions", {pageNumber: 1});
        });

    }
    init();


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
            // 项目ID
            projectId: $("#projectlist").val(),
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        }
    }





    //渲染表格数据
    function renderTable(URL) {
        var $stockQueryTable = $("#storageentrytable");
        $stockQueryTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/duizhan/checkinventory",
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
                title: '日期'
            }, {
                field: 'productCode',
                title: '货品名称'
            }, {
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'warehouseName',
                title: '仓库名称'
            }, {
                field: 'status',
                title: '状态'
            }, {
                field: 'fenxiaoPerfectQuantity',
                title: '分销良品'
            }, {
                field: 'wmsPerfectQuantity',
                title: '仓库良品'
            }, {
                field: 'perfectDifferent',
                title: '良品差异'
            }, {
                field: 'fenxiaoImperfectQuantity',
                title: '分销残品'
            }, {
                field: 'wmsImperfectQuantity',
                title: '仓库残品'
            },{
                field: 'imPerfectDifferent',
                title: '残次品差异'
            },  {
                field: 'fenxiaoEngineDamage',
                title: '分销机损'
            }, {
                field: 'wmsEngineDamage',
                title: '仓库机损'
            }, {
                field: 'engineDamageDifferent',
                title: '机损差异'
            }, {
                field: 'fenxiaoBoxDamage',
                title: '分销箱损'
            }, {
                field: 'wmsBoxDamage',
                title: '仓库箱损'
            }, {
                field: 'boxDamageDifferent',
                title: '箱损差异'
            }, {
                field: 'fenxiaoFrozenStock',
                title: '分销冻结'
            }, {
                field: 'wmsFrozenStock',
                title: '仓库冻结'
            },{
                field: 'frozenStockDifferent',
                title: '冻结差异'
            }, ]
        })
    }
});