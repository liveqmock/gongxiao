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
            $pageElement.refreshTable();
        });
        // 取消
        $pageElement.$yhcancelBtn.click(function () {
            var tableData = $pageElement.$yhTable.bootstrapTable("getSelections");
            // var flowCodeStr = "";
            // $.each(tableData,function (index,tableDataItem) {
            //     if(flowCodeStr.length !== 0){
            //         flowCodeStr += "," ;
            //     }
            //     flowCodeStr +=  tableDataItem["flowNo"];
            // });
            var arrParams = [{}];
            $.each(tableData,function (index,confirmDataItem) {
                arrParams[index] = {
                    confirmAmount:confirmDataItem.confirmAmount,
                    receiptAmount:confirmDataItem.receiptAmount,
                    transferIntoPatternStr: confirmDataItem.transferIntoPatternStr,
                    flowNo:confirmDataItem.flowNo
                }
            });
            arrParams = JSON.stringify(arrParams);
            var cancelParams = {
                //flowCodes:flowCodeStr,
                projectId: $("#projectlist").val(),
                cancelConfirmJson:arrParams
            };
            if(tableData.length !== 0){
                $.post(URL+"/yhglobal/grossProfit/cancelConfirm",cancelParams,function (res) {
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

    // 获取页面DOM元素
    function getPageElement() {
        var $queryBtn = $('#yhquery-btn');
        var $yhTable = $('#yhtable');
        var $yhcancelBtn = $("#yhcancel-btn");
        return{
            $queryBtn:$queryBtn,
            $yhcancelBtn:$yhcancelBtn,
            $yhTable:$yhTable,
            refreshTable:function () {
                this.$yhTable.bootstrapTable("refreshOptions",{pageNumber:1});
            }
        }
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
            transferIntoPattern: $("#accounttype").val(),
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

    function renderDateSelect() {
        $.renderDateRange('transfertime','transferendtime');
        $.renderDateRange('arrivaltime','arrivalendtime');
        $("#accounttype").select2({
            minimumResultsForSearch: 10
        })
    }


    // 动态渲染下拉表单
    function renderSelectList(URL) {
        // 获取project列表
        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
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

        var $yhcancelBtn = $("#yhcancel-btn");
        function BtnStatus() {
            var tableSelectedData = $yhTable.bootstrapTable("getSelections");
            if(tableSelectedData.length !== 0){
                $yhcancelBtn.removeClass("yh-handle-btn").removeAttr("disabled");
            }else{
                $yhcancelBtn.addClass("yh-handle-btn").attr("disabled", true);
            }
        }

        $yhTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: url+"/yhglobal/grossProfit/confirm/gets",
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
            onCheck: function (rows) {
                BtnStatus();
            },
            onUncheck: function (rows) {
                BtnStatus();
            },
            onCheckAll: function () {
                BtnStatus();
            },
            onUncheckAll: function () {
                BtnStatus();
            },
            columns: [{
                checkbox: true
            },{
                field: 'flowNo',
                title: '确认流水号'
            }, {
                field: 'confirmCurrencyCode',
                title: '确认币种'
            }, {
                field: 'confirmAmount',
                title: '确认金额'
            }, {
                field: 'receivedCurrencyCode',
                title: '使用币种'
            }, {
                field: 'receiptAmount',
                title: '使用金额'
            }, {
                field: 'differenceAmount',
                title: '差额'
            }, {
                field: 'useDateStr',
                title: '使用日期'
            },{
                field: 'transferIntoPatternStr',
                title: '使用账户'
            }, {
                field: 'createTimeStr',
                title: '确认时间'
            },{
                field: 'createdByName',
                title: '转入人'
            }]
        })
    }
});

