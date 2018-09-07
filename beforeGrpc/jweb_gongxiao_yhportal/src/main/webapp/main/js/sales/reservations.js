/**
 * @authors {Pinocchio} (http://csshack.org)
 * @date    2018-03-05 10:03:06
 */

$(function () {

    // 全局接口
    var URL = $.getfenxiaoURL();
    // 表格数据
    var tableLoadData = {};

    function selectDateRender() {
        var salesHeight = $(window).height();
        $("#sales-box").height(salesHeight);
        layui.config({
            version: '1515376178709'
        });
        layui.use(['laydate'], function () {
            var laydate = layui.laydate;
            laydate.render({
                elem: "#arrival-date",
                format: 'yyyy-MM-dd'
            })
        });
    }

    // 查询销售预约商品信息
    function reservationQuery() {
        var $yhTable = $("#yhtable");
        var salesOrderNo = $.getUrlParam("salesOrderNo");
        var queryParams = {
            salesOrderNo: salesOrderNo
        };
        $.get(URL + "/sales/schedule/info", queryParams, function (res) {
            if (res.returnCode === 0) {
                renderTable();
                tableLoadData["dataList"] = res.data;
                $yhTable.bootstrapTable('load', tableLoadData["dataList"]);
            }
        }, "json")
    }

    reservationInit();

    function reservationInit() {
        selectDateRender();
        reservationQuery();
        //初始化

        var isAjaxFlag = false;

        function submitReservation(URL) {
            var projectId = $.getUrlParam("projectId");
            var orderNumber = $.getUrlParam("salesOrderNo");
            var $yhTable = $("#yhtable");
            var $yhSubmitBtn = $("#yhsubmit-btn");
            var $arrivalDate = $("#arrival-date");
            var reservationTableData = $yhTable.bootstrapTable('getData');
            var reservationTableDataString = JSON.stringify(reservationTableData);

            var submitParams = {
                projectId: projectId,
                // 销售单号
                salesOrderNo: orderNumber,
                arrivalDateStr: $arrivalDate.val(),
                itemsInfo: reservationTableDataString
            };

            verification.emptyShow = false;
            verification.noEmpty("arrival-date", "arrival-date-verification", "时间不能为空!");

            if (verification.emptyShow) {
                return false;
            }
            $.each(reservationTableData, function (index, reservationItem) {
                if (reservationItem["scheduleQuantity"] === 0 || reservationItem["scheduleQuantity"] > reservationItem['inventoryBatchAmount'] || reservationItem["scheduleQuantity"] > reservationItem['orderUnhandledQuantity']) {
                    $yhTable.find("input[type='text']").eq(index).addClass("table-warning-input");
                    return false;
                } else if (!reservationItem['batchNo']) {
                    $yhTable.find("a.choose-batch").eq(index).removeClass("table-yh-info").addClass("table-yh-danger");
                    return false;
                }else{
                    $yhTable.find("input[type='text']").eq(index).removeClass("table-warning-input");
                    $yhTable.find("a.choose-batch").eq(index).removeClass("table-yh-danger").addClass("table-yh-info");
                }
            });
            if(isAjaxFlag){
                return false;
            }
            isAjaxFlag = true;
            $.renderLoading(true);
            $.ajax({
                type: "POST",
                url: URL + "/sales/schedule/create",
                cache: false,
                dataType: 'json',
                data: submitParams,
                async: false,
                beforeSend: function () {
                    $yhSubmitBtn.attr("disabled", true);
                },
                complete: function () {
                    $yhSubmitBtn.attr("disabled",false);
                },
                success: function (res) {
                    if (res.returnCode === 0) {
                        $yhSubmitBtn.attr("disabled",false);
                        window.location.href = './sales-management.html';
                    } else {
                        setTimeout(function(){
                            $.renderLoading(false);
                            $yhSubmitBtn.attr("disabled",false);
                        },2000);
                        isAjaxFlag = false;
                        $.renderLayerMessage(res.message);
                    }
                },
                error: function (err) {
                    setTimeout(function(){
                        isAjaxFlag = false;
                    },2000);
                    $.renderLoading(false);
                    $yhSubmitBtn.attr("disabled",false);
                    $.renderLayerMessage(err);
                }
            })
        }

        $("#yhsubmit-btn").click(function () {
            submitReservation(URL);
        })
    }

    // 渲染表格数据
    function renderTable() {
        var $yhTable = $("#yhtable");
        var $yhMaskTable = $("#yhmask-table");
        var $yhMask = $("#yhmask");
        var projectId = $.getUrlParam("projectId");
        var tableData = [{}];
        $yhTable.bootstrapTable({
            undefinedText: '',
            locale: 'zh-CN',
            striped: true,
            data: tableData,
            columns: [{
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'productCode',
                title: '货品编码'
            }, {
                field: 'warehouseName',
                title: '仓库名'
            }, {
                field: 'productId',
                title: '货品名',
                visible: false
            }, {
                field: 'warehouseId',
                title: '仓库名',
                visible: false
            }, {
                field: 'batchNo',
                title: '批次号',
                cellStyle: function () {
                    return {
                        css: {
                            "color": "#d9534f"
                        }
                    }
                },
                formatter: function (value, row, index) {
                    if (!value) {
                        var defaultBatchNo = "请选择批次号";
                        row.batchNo = null;
                        return '<a class="table-yh-info choose-batch" href="javascript:void(0);">' + defaultBatchNo + '</a>';
                    } else {
                        var showBatchNo = value;
                        row.batchNo = showBatchNo;
                        return '<a class="table-yh-info choose-batch" href="javascript:void(0);">' + showBatchNo + '</a>';
                    }
                },
                events: {
                    'click .choose-batch': function (event, value, row, index) {
                        $yhMask.removeClass("yhmask-hide");
                        var _productCode = row.productCode;

                        function getBatchTableParams(params) {
                            return {
                                productCode: _productCode,
                                projectId: projectId,
                                pageNumber: params.pageNumber,
                                pageSize: params.pageSize
                            }
                        }

                        tableLoadData["tableIndex"] = index;
                        $yhMaskTable.bootstrapTable("destroy");
                        renderMskTable(URL, getBatchTableParams);
                    }
                }
            }, {
                field: 'orderTotalQuantity',
                title: '订单出货数量'
            }, {
                field: 'orderUnhandledQuantity',
                title: '剩余需发货数量'
            }, {
                field: 'inventoryStatus',
                title: '库存品质',
                formatter: function (value) {
                    if (value === 1) {
                        return "普通好机";
                    } else if (value === 101) {
                        return "残次";
                    } else if (value === 102) {
                        return "机损";
                    } else if (value === 103) {
                        return "箱损";
                    } else if (value === 201) {
                        return "冻结库存";
                    } else {
                        return "";
                    }
                }
            },{
                field:'purchaseType',
                title:'采购类型',
                visiable:false
            }, {
                field: 'inventoryTotalAmount',
                title: '货品库存'
            }, {
                field: 'inventoryBatchAmount',
                title: '批次可出库存'
            }, {
                field: 'scheduleQuantity',
                title: '本次预约',
                formatter: function (value, row) {
                    var planDefaultValue = 0;
                    if (!value || value == null) {
                        row.scheduleQuantity = planDefaultValue;
                        return '<input type="text" class="reservations-input" value="' + planDefaultValue + '">';
                    } else {
                        row.scheduleQuantity = value;
                        return '<input type="text" class="reservations-input" value="' + value + '">';
                    }
                },
                events: {
                    'blur .reservations-input': function (event, value, row, index) {
                        var quantityInput = Number($(this).val());
                        // 预约发货数量应该小于剩余发货数量和仓库可出库存的任意值
                        var arrQuantity = [Number(row.orderUnhandledQuantity), Number(row.inventoryBatchAmount)];
                        var iScheduleQuantity = Math.min.apply(null, arrQuantity);
                        if (quantityInput < iScheduleQuantity || quantityInput == iScheduleQuantity) {
                            $(this).removeClass("table-warning-input");
                            $yhTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'scheduleQuantity',
                                value: quantityInput
                            });
                        } else {
                            $(this).addClass("table-warning-input");
                        }
                    }
                }
            },
                {
                    title: '操作',
                    width: 80,
                    formatter: function () {
                        return [
                            '<a class="table-yh-danger adelete" href="javascript:void(0);">删除</a>',
                            ' <a class="table-yh-primary reservation-input" href="javascript:void(0);">添加</a>'
                        ].join("");
                    },
                    events: {
                        'click .adelete': function (event, value, row) {
                            $yhTable.bootstrapTable('remove', {
                                field: 'productCode',
                                values: [row.productCode]
                            });
                        },
                        'click .reservation-input': function (event, value, row, index) {
                            var _index = index + 1;
                            var changeRowData = {
                                "warehouseName": '',
                                "warehouseId": '',
                                "inventoryBatchAmount": 0,
                                "inventoryTotalAmount": 0,
                                "scheduleQuantity": 0,
                                "inventoryStatus": '',
                                "batchNo": null
                            };
                            var _row = {};
                            $.extend(_row, row, changeRowData);
                            $yhTable.bootstrapTable("insertRow", {
                                index: _index,
                                row: _row
                            });
                        }
                    }
                }]
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

    // 遮罩层表格
    function renderMskTable(URL, getBatchTableParams) {
        var $yhTable = $("#yhtable");
        var $yhMaskTable = $("#yhmask-table");
        var $yhMask = $("#yhmask");
        $yhMaskTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/sales/schedule/batch",
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
                    field: 'inventoryTotalAmount',
                    title: '货品库存'
                }, {
                    field: 'inventoryBatchAmount',
                    title: '批次可出库存'
                }, {
                    field: 'inventoryStatus',
                    title: '库存品质',
                    formatter: function (value) {
                        if (value === 1) {
                            return "普通好机";
                        } else if (value === 101) {
                            return "残次";
                        } else if (value === 102) {
                            return "机损";
                        } else if (value === 103) {
                            return "箱损";
                        } else if (value === 201) {
                            return "冻结库存";
                        } else {
                            return "在途库存";
                        }
                    }
                }, {
                    field: 'purchaseType',
                    title: '库区',
                    formatter: function (value) {
                        if (value === 1) {
                            return "普通采购";
                        } else if(value === 2){
                            return "货补采购";
                        }else if(value === 3){
                            return "赠品采购";
                        }
                    }
                }, {
                    field: 'purchasePrice',
                    title: '采购价'
                }, {
                    title: '操作',
                    width: 80,
                    formatter: function () {
                        return '<a class="achooselink" href="javascript:void(0);">选择</a>'
                    },
                    events: {
                        'click .achooselink': function (events, value, row, index) {
                            var rowData = {
                                "warehouseName": row["warehouseName"],
                                "warehouseId": row["warehouseId"],
                                "inventoryBatchAmount": row["inventoryBatchAmount"],
                                "inventoryTotalAmount": row["inventoryTotalAmount"],
                                "batchNo": row["batchNo"],
                                "inventoryStatus": row["inventoryStatus"],
                                "purchaseType" : row["purchaseType"],
                                "productId":row["productId"]
                            };
                            var _index = tableLoadData["tableIndex"];
                            $yhMask.addClass("yhmask-hide");
                            $.extend(tableLoadData["dataList"][_index], rowData);
                            $yhTable.bootstrapTable('load', tableLoadData["dataList"]);
                        }
                    }
                }]
        })
    }

});