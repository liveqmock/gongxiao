$(function () {

    managementInit();

    function managementInit() {
        // 全局接口
        var URL = $.getfenxiaoURL();
        //初始化下拉列表
        renderSelectList(URL);
        //绑定查询事件
        $("#yhquery-btn").click(function () {
           $("#customerplantable").bootstrapTable("refreshOptions",{pageNumber:1});
        })


    }

    function renderSelectList(URL) {
        // 获取project列表
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName")
            .then(function () {
                // $("#customerplantable").bootstrapTable("refreshOptions",{pageNumber:1});
                customerSalePlan(URL);

            });
    }

    function customerSalePlan(URL) {
        var projectInfo = getProjectInfo("#projectlist");
        $("#customerplantable").bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/customerPlanSale/selectCustomerSalePlanList",
            queryParamsType: '',
            silent: true,
            undefinedText: '',
            striped: true,
            dataField: 'data',
            pagination: true,
            pageSize: 6, // 一页显示数据条数
            pageList: [6, 12, 20],
            pageNumber: 1, // 首页页码
            sidePagination: "server",
            queryParams: getQueryParams,
            responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法
            columns: [{
                field: 'distributorName',
                title: '客户名称'
            }, {
                field: 'onSaleCategory',
                title: '可售品种'
            }, {
                field: 'onSaleQuantity',
                title: '可售总数'
            }, {
                field: 'saleOccupationQuantity',
                title: '销售占用'
            }, {
                field: 'soldQuantity',
                title: '已售数量'
            }, {
                field: 'remainQuantity',
                title: '剩余可售数量'
            }, {
                title: '操作',
                clickToSelect: false,
                formatter: function (value, row, index) {
                    if(row['distributorName']){
                        return '<a class="detail" href="javascript:void(0)">' + '明细' + '</a>'
                    }else{
                        return ""
                    }
                },
                events: reviseEvents = {
                    'click .detail': function (event, value, row) {
                        window.open('./sales-customerdetail.html?projectId=' + projectInfo.Id + '&projectName=' + projectInfo.Name + '&distributorId=' + row.distributorId )
                    }
                }
            }]
        })
    }
    //获取已选择项目的信息
    function getProjectInfo(el) {
        var $el = $(el);
        var elId = $el.val();
        var elText = $el.children('option:selected').text();
        var info = {
            Id: elId,
            Name: elText
        };
        return info;
    }
    //查询参数
    function getQueryParams(params) {
        var queryParams = {
            projectId: $("#projectlist").val(),
            distributorId: $("#customercode").val(),
            distributorName: $("#customername").val(),
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        };
        return queryParams;
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

});