/**
 * @authors {len} (http://csshack.org)
 * @date    2018-04-20 11:14:46
 */

$(function () {


    salesAddInit();

    // 销售初始化
    function salesAddInit() {

        // 全局接口
        var statusObj = {};
        var alreadyDataList = [];
        var URL = $.getfenxiaoURL();
        var $pageElement = getPageElement();
        var $maskElement = getMaskElement();

        renderDate();

        renderDateAndSelect(URL);

        function dataInit() {
            alreadyDataList.splice(0, alreadyDataList.length);
            for (var key in statusObj) {
                delete statusObj[key];
            }
            $("#yhmask-pronum").text('('+0+')');
            $("#tobeaddtable").bootstrapTable("destroy");
            $("#addedtable").bootstrapTable("destroy");
        }


        $("#yhmask-switch").click(function () {
            dataInit();
        });

        $("#yhmask-cancel").click(function () {
            dataInit();
        });



        // 添加商品
        $pageElement.$salesAddBtn.click(function () {
            $("#yhmask-batchnotable").addClass("yhmask-hide");
            $("#yhmask-protable").removeClass("yhmask-hide");
            $maskElement.showMask();
            tobeaddTableRender(URL);
        });

        // 查询待添加的商品
        $("#yhmask-query").click(function () {
            $maskElement.refreshTable();
        });

        // 提交商品
        $pageElement.$salesSubmitBtn.click(function () {
            submitSalesOrder();
        });


        // 确认
        $maskElement.$salesConfirmBtn.click(function () {
            var addedProductData = $maskElement.$addedTable.bootstrapTable("getData");
            // 销售品种
            var $salesVariety = $("#sales-variety");
            if (addedProductData.length !== 0) {
                var salesVariety = addedProductData.length;
                $maskElement.hideMask();
                salesTableRender();
                $salesVariety.html(salesVariety);
                $pageElement.$salesTable.bootstrapTable('load', addedProductData);
                dataInit();
            }
        });

        function salesTableRender() {
            var $salesTable = $("#salestable");
            var salesData = [{}];
            $salesTable.bootstrapTable({
                undefinedText: "",
                striped: true,
                data: salesData,
                pageSize: 5,
                pageList: [3, 6],
                columns: [{
                    field: 'productCode',
                    title: '型号'
                }, {
                    field: 'productName',
                    title: '货品名称'
                }, {
                    field: 'batchNo',
                    title: '批次号',
                    formatter: function (value, row, index) {
                        if (!value) {
                            return '<a class="choosebatchno" href="javascript:void(0);">' + '请选择批次号' + '</a>';
                        } else {
                            row.batchNo = value;
                            return value;
                        }
                    },
                    events: {
                        'click .choosebatchno': function (event, value, row,index) {
                            $maskElement.showMask();
                            $maskElement.$yhMask.attr("yhmask-index",index);
                            $("#yhmask-protable").addClass("yhmask-hide");
                            $("#yhmask-batchnotable").removeClass("yhmask-hide");
                            function getBatchTableParams(params){
                                return {
                                    productCode: row.productCode,
                                    projectId: $pageElement.$projectList.val(),
                                    warehouseId: $pageElement.$warehouseList.val(),
                                    pageNumber: params.pageNumber,
                                    pageSize: params.pageSize
                                }
                            }
                            renderMskTable(URL,getBatchTableParams);
                        }
                    }
                }, {
                    field: 'guidePrice',
                    title: '指导价'
                }, {
                    field: 'quantity',
                    title: '出库数量',
                    formatter: function (value, row, index) {
                        if (!value) {
                            value = 0;
                            row.onSaleQuantity = 0;
                            return '<input type="text" class="yhpurinput quantity-input" value="' + value + '"' + '/>';
                        } else {
                            return '<input type="text" class="yhpurinput quantity-input" value="' + value + '"' + '/>';
                        }
                    },
                    events: {
                        'blur .quantity-input': function (event, value, row, index) {
                            var inputValue = $(this).val();
                            $salesTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'quantity',
                                value: inputValue
                            });
                        }
                    }
                }, {
                    field: 'wholesalePriceDouble',
                    title: '出货价',
                    formatter: function (value, row, index) {
                        if (!value) {
                            value = 0;
                            row.onSaleQuantity = 0;
                            return '<input type="text" class="yhpurinput price-input" value="' + value + '"' + '/>';
                        } else {
                            return '<input type="text" class="yhpurinput price-input" value="' + value + '"' + '/>';
                        }
                    },
                    events: {
                        'blur .price-input': function (event, value, row, index) {
                            var inputValue = $(this).val();
                            $salesTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'wholesalePriceDouble',
                                value: inputValue
                            });
                        }
                    }
                }, {
                    title: '操作',
                    formatter: function () {
                        return '<a class="purchase-delete" href="javascript:void(0)">' + '删除' + '</a>'
                    },
                    events: {
                        'click .purchase-delete': function (event, value, row) {
                            $salesTable.bootstrapTable('remove', {
                                field: 'productCode',
                                values: [row.productCode]
                            })
                        }
                    }
                }]
            })
        }

        // 获取表格需要查询的参数
        function getQueryParams(params) {
            return {
                projectId: $pageElement.$projectList.children("option:selected").val(),
                productCode: $("#product-code").val(),
                productName: $("#product-name").val(),
                pageNumber: params.pageNumber,
                pageSize: params.pageSize
            }
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

        // 待选商品
        function tobeaddTableRender(URL) {
            var $tobeaddTable = $("#tobeaddtable");
            var $addedTable = $("#addedtable");
            $tobeaddTable.bootstrapTable({
                method: 'get',
                contentType: "application/x-www-form-urlencoded",
                url: URL + "/purchase/foundation/getProductInfoList",
                queryParamsType: '',
                silent: true,
                undefinedText: '',
                striped: true,
                dataField: 'data',
                pagination: true,
                pageSize: 5, // 一页显示数据条数
                pageList: [3, 6],
                pageNumber: 1, // 首页页码
                sidePagination: "server",
                queryParams: getQueryParams,
                responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法
                columns: [{
                    field: 'productCode',
                    title: '货品编码'
                }, {
                    field: 'productName',
                    title: '货品名称'
                }, {
                    field: 'guidePrice',
                    title: '指导价'
                }, {
                    field: 'maskOpera',
                    title: '操作',
                    clickToSelect: false,
                    formatter: function (value, row) {
                        if (statusObj[row.productCode]) {
                            return '已添加'
                        } else {
                            return '<a class="pur-addbtn purchaseaddbtn" href="javascript:void(0)">' + '添加' + '</a>'
                        }
                    },
                    events: {
                        'click .pur-addbtn': function (e, value, row, index) {
                            alreadyDataList.push(row);
                            statusObj[row.productCode] = true;
                            if (statusObj[row.productCode]) {
                                $(this).removeClass("pur-addbtn").addClass("puralreadyadd");
                                var alreadyaddNum = alreadyDataList.length;
                                $maskElement.$yhmaskPronum.text("(" + alreadyaddNum + ")");
                                addedTableRender();
                                $addedTable.bootstrapTable('load', alreadyDataList);
                                $tobeaddTable.bootstrapTable('updateCell', {
                                    index: index,
                                    field: 'maskOpera',
                                    value: statusObj[row.productCode]
                                });
                            }
                        }
                    }
                }]
            })
        }

        // 已选择商品
        function addedTableRender() {
            var $tobeaddTable = $("#tobeaddtable");
            var $addedTable = $("#addedtable");
            var addedTableData = [{}];
            $addedTable.bootstrapTable({
                undefinedText: "",
                locale: "zh-CN",
                data: addedTableData,
                striped: true,
                pagination: true,
                pageNumber: 1,
                pageSize: 6,
                pageList: [3, 6, 9],
                paginationPreText: "&lt;",
                paginationNextText: "&lt;",
                columns: [{
                    field: 'productId',
                    title: '货品id',
                    visible:false
                },{
                    field: 'productCode',
                    title: '货品编码'
                }, {
                    field: 'productName',
                    title: '货品名称',
                    width: '35%'
                }, {
                    field: 'guidePrice',
                    title: '指导价'
                }, {
                    title: '操作',
                    width: "80px",
                    clickToSelect: false,
                    formatter: function () {
                        return '<a class="alreadyadd-delete purchaseaddbtn" href="javascript:void(0)">' + '删除' + '</a>'
                    },
                    events: {
                        'click .alreadyadd-delete': function (event, value, row, index) {
                            statusObj[row.productCode] = false;
                            // 删除行
                            $addedTable.bootstrapTable('remove', {
                                field: 'productCode',
                                values: [row.productCode]
                            });
                            // 更新遮罩层左边的表格
                            $tobeaddTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'maskOpera',
                                value: statusObj[row.productCode]
                            });
                            var alreadyaddNum = $addedTable.bootstrapTable('getData').length;
                            $maskElement.$yhmaskPronum.text("(" + alreadyaddNum + ")");
                        }
                    }
                }]
            })
        }

        // 待选批次表格
        function renderMskTable(URL,getBatchTableParams) {
            var $salesTable = $("#salestable");
            var $yhmaskTable = $("#yhmask-table");
            $yhmaskTable.bootstrapTable({
                method: 'get',
                contentType: "application/x-www-form-urlencoded",
                url: URL + "/manualOutbound/batch",
                queryParamsType: '',
                silent: true,
                undefinedText: '',
                striped: true,
                dataField: 'data',
                pagination: true,
                pageSize: 5, // 一页显示数据条数
                pageList: [3, 6],
                pageNumber: 1, // 首页页码
                sidePagination: "server",
                queryParams: getBatchTableParams,
                responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法
                columns: [
                    {
                        field: 'warehouseId',
                        title: '仓库名',
                        visible: false
                    }, {
                        field: 'productId',
                        title: '货品名',
                        visible: false
                    },
                    {
                        field: 'warehouseName',
                        title: '仓库'
                    }, {
                        field: 'batchNo',
                        title: '批次号'
                    }, {
                        field: 'inventoryBatchAmount',
                        title: '批次库存'
                    }, {
                        field: 'createTime',
                        title: '入库时间',
                        visible: false
                    }, {
                        field: 'purchasePrice',
                        title: '采购价',
                        visible: false
                    }, {
                        title: '操作',
                        width: 80,
                        formatter: function () {
                            return '<a class="batch-select" href="javascript:void(0);">选择</a>'
                        },
                        events: {
                            'click .batch-select': function (event, value, row, index) {
                                var btahcNoInput = row.batchNo;
                                $maskElement.hideMask();
                                var _index = $maskElement.$yhMask.attr("yhmask-index");
                                $salesTable.bootstrapTable('updateCell', {
                                    index: _index,
                                    field: 'batchNo',
                                    value: btahcNoInput
                                });
                            }
                        }
                    }]
            })
        }

        // 提交新增销售单
        function submitSalesOrder() {
            var salesOrderData = $pageElement.$salesTable.bootstrapTable("getData");
            var itemsInfo = [{}];
            $.each(salesOrderData, function (index, salesItem) {
                itemsInfo[index] = {
                    productId: salesItem.productId,
                    productCode: salesItem.productCode,
                    productName: salesItem.productName,
                    batchNo: salesItem.batchNo,
                    guidePrice: salesItem.guidePrice,
                    quantity: salesItem.quantity,
                    purchasePrice: salesItem.purchasePrice,
                    wholesalePriceDouble: salesItem.wholesalePriceDouble
                }
            });
            itemsInfo = JSON.stringify(itemsInfo);
            var submitParams = {
                projectId: $pageElement.$projectList.val(),
                supplierName: $pageElement.$supplierList.children("option:selected").text(),
                warehouseId: $pageElement.$warehouseList.val(),
                warehouseName: $pageElement.$warehouseList.children("option:selected").text(),
                businessDate: $pageElement.$businessDate.val(),
                delieverAddress: $pageElement.$shopAddress.val(),
                outboundType: $pageElement.$outboundType.val(),
                remask: $pageElement.$remask.val(),
                itemsInfo: itemsInfo
            };
            $.post(URL + "/manualOutbound/insertOutbound", submitParams, function (res) {
                if (res.returnCode === 0) {
                    window.location.href = './storage-outboundmanagement.html';
                }
            }, "json")
        }
    }

    function renderDate(){
        layui.config({
            version: '1515376178709' //为了更新 js 缓存
        });
        layui.use(['laydate'],function () {
            var laydate = layui.laydate;
            laydate.render({
                elem: '#yhbusiness-date',
                format: 'yyyy-MM-dd'
            });
        });
        $("#outboundtype").select2({
            minimumResultsForSearch: 6
        });
    }



    // 渲染页面元素
    function renderDateAndSelect(URL) {
        // 项目列表
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName")
        // 获取仓库列表
        $.getSelectOptions("warehouselist", URL + "/purchase/foundation/warehouseList", "warehouseId", "warehouseName")
            .then(function (res) {
                var $warehouseOutList = $("#warehouselist");
                var $shopAddress = $("#shopaddress");
                $warehouseOutList.change(function () {
                    var warehouseValue = $(this).val();
                    var resData = res.data;
                    $.each(resData, function (index, resDataItem) {
                        if (resDataItem.warehouseId == warehouseValue) {
                            var address = resDataItem.streetAddress;
                            $shopAddress.val(address);
                        }
                    });
                });
            });
        // 供应商列表
        $.getSelectOptions("supplierlist", URL + "/purchase/foundation/getSupplierList", "supplierCode", "supplierName");
    }

    // 页面元素
    function getPageElement() {
        var $supplierList = $("#supplierlist");
        var $warehouseList = $("#warehouselist");
        var $businessDate = $("#yhbusiness-date");
        var $shopAddress = $("#shopaddress");
        var $outboundType = $("#outboundtype");
        var $remask = $("#remask");

        var pageElem = {
            $projectList: $("#projectlist"),
            // 添加商品按钮
            $salesAddBtn: $("#sales-addbtn"),
            $supplierList: $supplierList,
            $warehouseList: $warehouseList,
            $businessDate: $businessDate,
            $shopAddress: $shopAddress,
            $outboundType: $outboundType,
            // 备注
            $remask: $remask,
            $salesTable: $("#salestable"),
            // 提交按钮
            $salesSubmitBtn: $("#sales-submit")
        };

        return pageElem;
    }

    // 遮罩层
    function getMaskElement() {
        var maskElement = {
            $yhMask: $("#yhmask"),
            // 查询input
            $proInfo: $("#proinfo"),
            // 关闭按钮
            $yhmaskSwitch: $("#yhmask-switch"),
            // 确认按钮
            $salesConfirmBtn: $("#yhmask-submit"),
            // 取消按钮
            $salesCancelBtn: $("#yhmask-cancel"),
            // 待添加表格
            $tobeaddTable: $("#tobeaddtable"),
            // 已选货品数量
            $yhmaskPronum: $("#yhmask-pronum"),
            // 已添加表格
            $addedTable: $("#addedtable"),
            showMask: function () {
                this.$yhMask.removeClass("yhmask-hide");
            },
            hideMask: function () {
                this.$yhMask.addClass("yhmask-hide");
            },
            refreshTable: function () {
                this.$tobeaddTable.bootstrapTable("refreshOptions", {pageNumber: 1});
            }
        };
        return maskElement;
    }

});