$(function() {

    managementInit();

    function managementInit() {
        //全局URL
        var URL = $.getfenxiaoURL();
        renderPageElement();
        //绑定查询事件
        $("#yhquery-btn").click(function () {
            $("#yhtable").bootstrapTable("refreshOptions",{pageNumber:1});
        });
        //初始化查询
        customerSalePlanItem(URL);
    }

    function renderPageElement() {
        layui.config({
            version: '1515376178709' //为了更新 js 缓存
        });
        layui.use(['laydate'],function () {
            var laydate = layui.laydate;
            laydate.render({
                elem: "#yhdate",
                format: 'yyyy-MM-dd'
            });
        });
        $("#sales-status").select2({
            minimumResultsForSearch: 6
        });
    }
    
    
    //获取URL有效参数
    function getInfoFromPrev() {
        var projectId = $.getUrlParam("projectId");
        var projectName = $.getUrlParam("projectName");
        var distributorId = $.getUrlParam("distributorId");
        var urlParam = {
            projectId:projectId,
            distributorId:distributorId
        };
        $("#projecttext").val(projectName);
        return urlParam;
    }

    // 全局接口
    function customerSalePlanItem(URL) {
        var $yhTable = $("#yhtable");
        $yhTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/customerPlanSale/selectCustomerSalePlanItemList",
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
                field: 'productCode',
                title: '型号'
            }, {
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'allocatedQuantity',
                title: '分配数量'
            }, {
                field: 'saleOccupationQuantity',
                title: '销售占用'
            }, {
                field: 'soldQuantity',
                title: '已售数量'
            }, {
                field: 'couldBuyNumber',
                title: '客户可下单数量'
            }, {
                field: 'currencyCode',
                title: '币种'
            }, {
                field: 'guidePrice',
                title: '指导价'
            }, {
                field: 'brandPrepaidDiscount',
                title: '品牌商支持折扣'
            }, {
                field: 'yhPrepaidDiscount',
                title: 'YH销售支持折扣'
            }, {
                field: 'wholesalePrice',
                title: '出货价'
            }, {
                field: 'recordStatus',
                title: '状态'
            }, {
                field: 'salePlaneNo',
                title: '可售单号'
            }, {
                field: 'startDate',
                title: '有效日期起'
            }, {
                field: 'endDate',
                title: '有效日期止'
            }]
        })
    }

    //查询参数
    function getQueryParams(params) {
        var urlParam = getInfoFromPrev();
        var queryParams = {
            productCode:$("#productno").val(),
            productName:$("#productname").val(),
            orderStatus:$("#sales-status").val(),
            salePlanNo:$("#saleOrderNo").val(),
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        };
        $.extend(urlParam,queryParams);
        return urlParam;
    }

    // 返回成功渲染表格
    function responseHandler(res) {
        if(res['returnCode'] === 0 && res['data']['list']['length'] !== 0 && res['data']['total'] !==0 && typeof res['data']['total'] !== 'undefined'){
            return {
                total: res['data']['total'], // 总页数,前面的key必须为"total"
                data: res['data']['list'] // 行数据，前面的key要与之前设置的dataField的值一致.
            }
        }else{
            return {
                total: 0, // 总页数,前面的key必须为"total"
                data: [{}] // 行数据，前面的key要与之前设置的dataField的值一致.
            };
        }
    }

});