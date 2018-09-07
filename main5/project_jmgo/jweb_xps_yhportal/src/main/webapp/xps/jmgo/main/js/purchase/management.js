$(function () {

    managementInit();

    // 初始化
    function managementInit() {
        // 全局接口
        var URL = $.getfenxiaoURL();

        renderDate();

        // 动态渲染下拉表单
        renderSelectList(URL);

        // 获取页面元素
        var $pageElement = getPageElement();

        // 查询
        $pageElement.$queryBtn.click(function () {
            $pageElement.refreshPurchaseTable();
        });

        $pageElement.$yhleadinginBtn.click(function () {
            $("#mask-wrap").removeClass("mask-hide");
        });
    }

    // 渲染时间控件
    function renderDate() {
        layui.config({
            version: '1515376178709' //为了更新 js 缓存
        });
        layui.use(['laydate'], function () {
            var laydate = layui.laydate;
            laydate.render({
                elem: '#yharrival-date',
                format: 'yyyy-MM-dd'
            });
            laydate.render({
                elem: '#yhcreate-date',
                format: 'yyyy-MM-dd'
            });

            laydate.render({
                elem: '#yhexpiry-date',
                format: 'yyyy-MM-dd'
            });
        });
        $("#mask-switch").click(function () {
            $("#mask-wrap").addClass("mask-hide");
            var $fileName = $("#file-name");
            $fileName.val("");
        })
    }


    // 获取页面DOM元素
    function getPageElement() {
        var $queryBtn = $('#yhquery-btn');
        var $purchaseManagementTable = $('#purmanagetable');
        var $yhleadinginBtn = $("#yhleadingin-btn");
        var $yhMaskUpload = $("#mask-wrap-upload");
        return {
            $queryBtn: $queryBtn,
            $yhleadinginBtn: $yhleadinginBtn,
            $purchaseManagementTable: $purchaseManagementTable,
            $yhMaskUpload: $yhMaskUpload,
            refreshPurchaseTable: function () {
                this.$purchaseManagementTable.bootstrapTable("refreshOptions", {pageNumber: 1});
            }
        }
    }

    // 动态渲染下拉表单
    function renderSelectList(URL) {
        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
            .then(function () {
                // 初始化查询
                tableRender(URL);
                // 获取供应商列表
                $.getSelectOptions("supplierlist", URL + "/supplier/selectAllSupplier", "supplierCode", "supplierName")
                    .then(function () {
                        // 获取收货仓库列表
                        $.getSelectOptions("warehouselist", URL + "/warehouse/selectWarehouseList", "warehouseId", "warehouseName")
                    });
            })
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
            // 项目ID
            projectId: $("#projectlist").val(),
            // 采购单号
            purchaseOrderNo: $("#productno").val(),
            // 供应商ID
            supplierCode: $("#supplierlist").children("option:selected").val(),
            // 收货仓库
            warehouseId: $("#warehouselist").children("option:selected").val(),
            // 货品信息
            productCode: $("#productinfo").val(),
            // 要求到货时间
            requireArrivalDate: $("#yharrival-date").val(),
            // 到货截止时间
            arrivalDeadline: $("#yhexpiry-date").val(),
            // 创建时间
            businessDate: $("#yhcreate-date").val(),
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        }
    }

    // 表格渲染
    function tableRender(URL) {
        var $projectList = $("#projectlist");
        var $purchaseManagementTable = $('#purmanagetable');
        var projectId = $projectList.val();
        var projectName = $projectList.children("option:selected").text();
        $purchaseManagementTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/purchase/order/getPurchaseList",
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
                field: 'purchaseOrderNumber',
                title: '采购单号',
                formatter: function (value) {
                    if(!value){
                        return ""
                    }else{
                        return '<a class="table-yh-link" href="./purchase-detail.html?purchaseOrderNumber=' + value + '">' + value + '</a>';
                    }
                }
            }, {
                field: 'supplierOrderNo',
                title: '供应商订单号'
            }, {
                field: 'contractReferenceOrderNo',
                title: '合同参考号'
            }, {
                field: 'supplierName',
                title: '供应商'
            }, {
                field: 'orderAmount',
                title: '订单金额'
            }, {
                field: 'warehouseName',
                title: '收货仓库'
            }, {
                field: 'purchaseStatus',
                title: '订单状态'
            }, {
                field: 'businessDate',
                title: '创建时间'
            }, {
                field: 'createPerson',
                title: '创建人'
            }, {
                title: '操作',
                width: 140,
                formatter: function (value, row) {
                    var str1 = '<a class="btn btn-success" href="./purchase-goods.html?purchaseOrderNumber=' + row.purchaseOrderNumber + '&projectName=' + projectName + '&projectId=' + projectId + '&warehouseName=' + row.warehouseName + '&warehouseId=' + row.warehouseId + '&requireInboundDate=' + row.requireInboundDate + '">' + '预约入库  ' + '</a>  ';
                    var str2 = '<a class="btn btn-primary" href="./purchase-edit.html?purchaseOrderNumber=' + row.purchaseOrderNumber + '&projectName=' + projectName + '&projectId=' + projectId + '">' + '编辑 ' + '</a>  ';
                    var str3 = '<a class="btn btn-info" href="../storage/storage-notice-entrymanagement.html?purchaseOrderNumber=' + row.purchaseOrderNumber + '&projectName=' + projectName + '&projectId=' + projectId + '">' + '  入库单详情' + '</a>  ';
                    var cancelStr = '<a class="cancel-opera btn btn-danger">' + '作废 ' + '</a>  ';
                    var statusResult = null;

                    if (row['enableEdit']) {
                        statusResult = str2;
                    }
                    if (row['enablePlanInStock']) {
                        statusResult = [statusResult, str1].join("");
                    }
                    if (row['enableDetail']) {
                        statusResult = [statusResult, str3].join("");
                    }
                    if (row['enableCancel']) {
                        statusResult = [statusResult, cancelStr].join("");
                    }
                    return statusResult;
                },
                events: {
                    'click .cancel-opera': function (event, value, row, index) {
                        layui.use('layer',function () {
                            var layer = layui.layer;
                            layer.alert("您确认作废此订单吗？",{
                                icon: 0
                            },function () {
                                var cancelParams = {
                                    purchaseOrderNumber:row.purchaseOrderNumber
                                };
                                $.ajax({
                                    url: URL + "/purchase/order/cancelPurchaseOrder",
                                    type: "GET",
                                    data: cancelParams,
                                    dataType: "json",
                                    success: function (res) {
                                        if (res.returnCode === 0) {
                                            var $pageElement = getPageElement();
                                            $pageElement.refreshPurchaseTable();
                                            layer.closeAll('dialog');
                                        }
                                    },
                                    error: function (err) {
                                        $.renderLayerMessage(err);
                                    }
                                });
                            })
                        });
                    }
                }
            }]
        })
    }
});

