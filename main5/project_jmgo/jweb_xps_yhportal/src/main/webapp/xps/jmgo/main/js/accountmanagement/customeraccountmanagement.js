$(function () {

    managementInit();

    // 初始化
    function managementInit() {
        // 全局接口
        var URL = $.getfenxiaoURL();
        var $pageElement = getPageElement();
        // 动态渲染下拉表单
        renderSelectList(URL);

        // 条件查询客户
        $pageElement.$queryBtn.click(function () {
            $pageElement.refreshTable();
        });

        // 经销商列表导出
        $pageElement.$exportBtn.click(function () {
            var accountType = $("#accounttype").val();
                // $pageElement.refreshTable(couponoUrl);
                // 项目ID
                var projectId = $("#projectlist").val();
            // 客户ID
            var distributorId= $("#customerid").val();
                // 客户名称
                var distributorName= $("#customername").val();
                // 返利流水
                var couponoUrl = URL+ "/distributor/coupon/exportSupplierList?projectId="+projectId+"&distributorId="+distributorId+"&distributorName="
                    +distributorName;
                window.open(couponoUrl);
        })

    }

    // 获取页面DOM元素
    function getPageElement() {
        var $queryBtn = $('#yhquery-btn');
        var $yhTable = $('#yhtable');
        var $exportBtn = $('#yhhandle-btn');
        return {
            $queryBtn: $queryBtn,
            $yhTable: $yhTable,
            $exportBtn:$exportBtn,
            refreshTable: function () {
                $yhTable.bootstrapTable("refreshOptions", {pageNumber: 1});
            }
        }
    }

    // 获取表格需要查询的参数
    function getQueryParams(params) {
        return {
            // 项目ID
            projectId: $("#projectlist").val(),
            // 客户ID
            distributorId: $("#customerid").val(),
            // 客户名称
            distributorName: $("#customername").val(),
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        }
    }



    // 动态渲染下拉表单
    function renderSelectList(URL) {
        // 获取project列表
        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
            .then(function () {
                // 初始化表格
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

    // 表格渲染
    function tableRender(URL) {
        var $projectList = $("#projectlist");
        var projectId = $projectList.val();
        var projectName = $projectList.children("option:selected").text();
        var $yhTable = $('#yhtable');
        $yhTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/distributor/coupon/distributorDatailList",
            queryParamsType: '',
            striped: true,
            silent: true,
            undefinedText: '',
            dataField: 'data',
            pagination: true,
            pageSize: 60, // 一页显示数据条数
            pageList: [60, 100, 200],
            pageNumber: 1, // 首页页码
            sidePagination: "server",
            queryParams: getQueryParams,
            responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法
            columns: [{
                field: 'distributorId',
                title: '客户账号'
            }, {
                field: 'distributorName',
                title: '客户名称'
            }, {
                field: 'cashAmountDoubleString',
                title: '现金账户'
            },
            //     {
            //     field: 'couponAmountDoubleString',
            //     title: '返利账户余额'
            // }, {
            //     field: 'prepaidAmountDoubleString',
            //     title: '代垫账户余额'
            // },
                {
                title: '操作',
                formatter: function (value,row) {
                    return '<a class="btn btn-info" href="./customeraccountdetail.html?distributorId=' + row.distributorId + '&projectId='+projectId+'&projectName='+projectName+'">' + '明细' + '</a>';
                }
            }]
        })
    }
});

