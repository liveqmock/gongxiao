$(function () {

    managementInit();

    // 初始化
    function managementInit() {
        // 全局接口
        var URL = $.getfenxiaoURL();

        renderDateSelect();

        // 动态渲染下拉表单
        renderSelectList(URL);

        // 获取页面元素
        var $pageElement = getPageElement();

        // 查询
        $pageElement.$queryBtn.click(function () {
            $pageElement.queryTableData();
        });

        // 取消
        $pageElement.$yhCancelBtn.click(function () {
            var tableData = $pageElement.$yhTable.bootstrapTable("getSelections");
            var flowCodeStr = "";
            $.each(tableData,function (index,tableDataItem) {
                if(flowCodeStr.length !== 0){
                    flowCodeStr += "," ;
                }
                flowCodeStr +=  tableDataItem["flowCode"];
            });
            var cancelParams = {
                flowCodes:flowCodeStr
            };
            if(tableData.length !== 0){
                $.post(URL+"/prepaid/receive/confirm/cancel",cancelParams,function (res) {
                    if(res.returnCode === 0){
                        $.renderLayerMessage("取消成功！");
                        $pageElement.queryTableData();
                    }
                },"json")
            }else{
                $.renderLayerMessage("请勾选流水号");
            }
        });
    }

    function renderDateSelect() {
        $.renderDateRange('transfertime','transferendtime');
        $.renderDateRange('arrivaltime','arrivalendtime');
        $("#accounttype").select2({
            minimumResultsForSearch: 10
        })
    }


    // 获取页面DOM元素
    function getPageElement() {
        var $queryBtn = $('#yhquery-btn');
        var $yhTable = $('#yhtable');
        var $yhCancelBtn = $("#yhcancel-btn");
        return {
            $queryBtn:$queryBtn,
            $yhTable:$yhTable,
            $yhCancelBtn:$yhCancelBtn,
            queryTableData: function () {
                this.$yhTable.bootstrapTable("refreshOptions",{pageNumber:1});
            }
        };
    }

    // 获取表格需要查询的参数
    function getQueryParams(params) {
        return {
            projectId: $("#projectlist").val(),
            // 转入时间始
            useDateStart: $("#transfertime").val(),
            // 转入时间至
            useDateEnd: $("#transferendtime").val(),
            // 转入号
            flowCode: $("#transno").val(),
            // 账户类型
            accountType: $("#accounttype").val(),
            // 上账类型
            productCode: $("#entryaccounttype").val(),
            // 到账日期
            dateStart: $("#arrivaltime").val(),
            // 到货截止时间
            dateEnd: $("#arrivalendtime").val(),
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        }
    }

    // 动态渲染下拉表单
    function renderSelectList(URL) {
        // 获取project列表
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName")
            .then(function () {
                tableRender(URL)
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
    function tableRender(url) {
        var $yhTable = $('#yhtable');
        $yhTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: url+"/prepaid/confirm/gets",
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
                checkbox: true
            },{
                field: 'flowCode',
                title: '确认流水号'
            }, {
                field: 'confirmAmountDouble',
                title: '确认金额'
            }, {
                field: 'receiptAmountDouble',
                title: '使用金额'
            }, {
                field: 'useDate',
                title: '使用日期',
                formatter: function (value) {
                    return $.transformTime(value);
                }
            },{
                field: 'accountTypeStr',
                title: '使用账户'
            },{
                field: 'philipNo',
                title: 'philip单据号'
            }, {
                field: 'createTime',
                title: '确认时间',
                formatter: function (value) {
                    return $.transformTime(value);
                }
            },{
                field: 'createdByName',
                title: '转入人'
            }]
        })
    }
});

