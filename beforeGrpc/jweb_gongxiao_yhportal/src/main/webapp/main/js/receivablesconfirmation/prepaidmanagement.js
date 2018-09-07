$(function () {

    managementInit();

    // 初始化
    function managementInit() {
        // 全局接口
        var URL = $.getfenxiaoURL();

        // 获取页面元素
        var $pageElement = getPageElement();

        // 动态渲染下拉表单
        renderSelectList(URL);

        // 日期组件渲染
        layUiDateRender();

        // 初始化查询
        tableRender(URL);

        // 查询商品
        $pageElement.$queryBtn.click(function () {
            $pageElement.$purchaseManagementTable.bootstrapTable("refreshOptions",{pageNumber:1});
        });
    }

    // 获取页面DOM元素
    function getPageElement() {
        var $queryBtn = $('#proquery-btn');
        var $purchaseManagementTable = $('#prepaidtable');
        return {
            $queryBtn: $queryBtn,
            $purchaseManagementTable: $purchaseManagementTable
        }
    }

    // 获取表格需要查询的参数
    function getQueryParams(params) {
        return {
            // 项目ID
            // projectId: $("#projectlist").val(),
            projectId: 146798161,
            // 代垫付款单号
            prepaidNo: $("#productno").val(),
            receivables: $("#receivables").val(),
            // 创建时间
            dateStart: $("#createtime").val(),
            dateEnd: $("#dateend").val(),
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        }
    }

    // 日期组件渲染
    function layUiDateRender() {
        layui.config({
            version: '1515376178709' //为了更新 js 缓存
        });
        layui.use(['laydate'], function () {
            var laydate = layui.laydate;
            // 创建时间
            laydate.render({
                elem: "#createtime",
                format: 'yyyy-MM-dd'
            });
            laydate.render({
                elem: "#dateend",
                format: 'yyyy-MM-dd'
            });
        })
    }

    // 动态渲染下拉表单
    function renderSelectList(URL) {
        // 获取project列表
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName");
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
        var $purchaseManagementTable = $('#prepaidtable');
        var projectId = $projectList.val();
        var projectName = $projectList.children("option:selected").text();
        $purchaseManagementTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/prepaid/info/gets",
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
                field: 'prepaidNo',
                title: '代垫付款单号',
                formatter: function (value,row,index) {
                    if(value){
                        var prepaidId = row.prepaidId;
                        return '<a class="skipdetail" href="./prepaid-detail.html?prepaidId=' + prepaidId + '">' + value + '</a>';
                    }else{
                        return ""
                    }
                }
            }, {
                field: 'standardCurrencyAmountDouble',
                title: '本位币金额'
            }, {
                field: 'receivables',
                title: '收款方'
            }, {
                field: 'supplierName',
                title: '供应商'
            }, {
                field: 'createTime',
                title: '创建时间',
                formatter: function (value) {
                    return $.transformTime(value);
                }
            }, {
                field: 'createdByName',
                title: '创建人'
            }, {
                title: '操作',
                width: 90,
                formatter: function (value, row) {
                    if(row['prepaidNo']){
                        if (row.purchaseStatus === 1) {
                            return '<a class="skipreview" href="./storage-entrymanagement.html?purchaseOrderNumber=' + row.purchaseOrderNumber + '&projectName=' + projectName + '&projectId=' + projectId + '">' + "入库单详情" + '</a>';
                        } else if (row.purchaseStatus === 0) {
                            return '<a class="skipreview" href="./purchase-goods.html?purchaseOrderNumber=' + row.purchaseOrderNumber + '&projectName=' + projectName + '&projectId=' + projectId + '">' + "收货通知" + '</a>';
                        } else {
                            return "";
                        }
                    }else{
                        return ""
                    }
                }
            }]
        })
    }
});

