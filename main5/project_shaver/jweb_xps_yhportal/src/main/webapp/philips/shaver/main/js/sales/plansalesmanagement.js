$(function () {

    // 遮罩层表格内容变化的状态
    var tableObj = {};
    // 已添加表格的数据
    var alreadyDataList = [];
    managementInit();


    function dataInit() {
        alreadyDataList.splice(0, alreadyDataList.length);
        for (var key in tableObj) {
            delete tableObj[key];
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


    function managementInit() {
        // 全局接口
        var URL = $.getfenxiaoURL();
        var $pageElement = getPageElement();
        var $maskElement = getMaskElement();
        // 获取project列表
        getProjectList(URL);


        // 查询
        $pageElement.$yhQueryBtn.click(function () {
            $pageElement.refreshPurchaseTable();
        });

        // 遮罩层待添加表格查询
        $("#yhmask-query").click(function () {
            $("#tobeaddtable").bootstrapTable("refreshOptions", {
                projectId: $("#projectlist").val(),
                productCode: $("#newproductcode").val(),
                productName: $("#newproductname").val()
            })
        });


        // 新增预售商品
        $pageElement.$yhAddBtn.click(function () {
            $maskElement.operaMask("yhadd", "revise-mask", "add-mask", "添加");
            $maskElement.addTableRender(URL);
        });

        // 作废
        $pageElement.$yhCancelBtn.on('click',function () {
            var cancelData = $pageElement.$yhTable.bootstrapTable("getSelections");
            var itemIdStr = "";
            $.each(cancelData, function (index, receiveItem) {
                if (itemIdStr.length !== 0) {
                    itemIdStr += ",";
                }
                itemIdStr += receiveItem["salesPlanNo"];
            });

            if (cancelData.length !== 0) {
                var blankOutParams = {
                    planSaleOrderListString: itemIdStr
                };
                $.post(URL + "/planSale/cancelPlanOrder", blankOutParams, function (res) {
                    if (res.returnCode === 0) {
                        $pageElement.refreshPurchaseTable();
                    }
                }, "json")
            }
        });


        // 确认插入(修改)
        // 新增商品插入(确认)
        $maskElement.$yhMaskSubmit.click(function () {
            var maskStatus = $maskElement.$yhmask.attr("yhmask-status");
            if (maskStatus === "yhadd") {
                var insetTableData = $maskElement.$addedTable.bootstrapTable("getData");
                var arrInsertObj = [];

                if (insetTableData !== 0) {
                    $.each(insetTableData, function (index, item) {
                        arrInsertObj[index] = {
                            productId: item.productId,
                            productCode: item.productCode,
                            onSaleQuantity: item.onSaleQuantity,
                            startTime: item.startTime,
                            endTime: item.endTime,
                            projectId: $("#projectlist").val()
                        }
                    });
                    var insetParams = {
                        salePlanJson: JSON.stringify(arrInsertObj)
                    };
                    $.post(URL + "/planSale/insertSalePlanInfo", insetParams, function (res) {
                        if (res.returnCode === 0) {
                            $maskElement.$yhmask.addClass('yhmask-hide');
                            $pageElement.refreshPurchaseTable();
                            dataInit();
                        } else {
                            $.renderLayerMessage(res.message);
                        }
                    }, "json")
                } else {
                    $.renderLayerMessage("请添加商品");
                }
            } else if (maskStatus === "yhrevise") {
                var reviseTableData = $maskElement.$reviseTable.bootstrapTable("getData");
                var editPlanParams = {
                    salesPlanNo: reviseTableData[0].salePlanNo,
                    onSaleQuantity: reviseTableData[0].onSaleQuantity,
                    startDate: reviseTableData[0].startTime,
                    endDate: reviseTableData[0].endTime
                };
                $.get(URL + "/planSale/editPlanQuantity", editPlanParams, function (res) {
                    if (res.returnCode === 0) {
                        $maskElement.$yhmask.addClass('yhmask-hide');
                        $pageElement.refreshPurchaseTable();
                    }
                }, "json")
            }
        });
    }

    // 获取project列表
    function getProjectList(URL) {
        layui.config({
            version: '1515376178709'
        });
        layui.use(['laydate'], function () {
            var laydate = layui.laydate;
            // 渲染时间选择器
            laydate.render({
                elem: "#yhcreate-date",
                format: 'yyyy-MM-dd'
            })
        });

        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
            .then(function () {
                renderTable(URL);
            });
    }

    // 待添加货品的表格
    function toBeAddTableRender(URL) {
        var $maskElement = getMaskElement();
        $maskElement.$toBeAddTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/planSale/getProductList",
            queryParamsType: '',
            silent: true,
            undefinedText: '',
            striped: true,
            dataField: 'data',
            pagination: true,
            pageSize: 6, // 一页显示数据条数
            pageList: [3, 6, 9],
            pageNumber: 1, // 首页页码
            sidePagination: "server",
            queryParams: getMaskTableParams,
            responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法
            columns: [{
                field: 'productCode',
                title: '货品编码'
            }, {
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'saleGuidePrice',
                title: '指导价'
            }, {
                field: 'inStockQuantity',
                title: '实物库存'
            }, {
                field: 'maskOpera',
                title: '操作',
                clickToSelect: false,
                formatter: function (value, row) {
                    var rowData = JSON.stringify(row);
                    if(rowData !== "{}"){
                        if (tableObj[row.productCode]) {
                            return '已添加'
                        } else if(!tableObj[row.productCode]){
                            return '<a class="pur-addbtn purchaseaddbtn salesadd" href="javascript:void(0)">' + '添加' + '</a>'
                        }else{
                            return ""
                        }
                    }
                },
                events: {
                    'click .pur-addbtn': function (e, value, row, index) {
                        alreadyDataList.push(row);
                        tableObj[row.productCode] = true;
                        if (tableObj[row.productCode]) {
                            $(this).removeClass("pur-addbtn").addClass("puralreadyadd");
                            var alreadyaddNum = alreadyDataList.length;
                            $maskElement.$yhmaskProNum.text("(" + alreadyaddNum + ")");
                            addedTableRender();
                            $maskElement.$addedTable.bootstrapTable('load', alreadyDataList);
                            $maskElement.$toBeAddTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'maskOpera',
                                value: tableObj[row.productCode]
                            });
                        }
                    }
                }
            }]
        })
    }

    // 已选货品的表格
    function addedTableRender() {
        var addedTableData = [{}];
        var $maskElement = getMaskElement();
        $maskElement.$addedTable.bootstrapTable({
            undefinedText: "",
            locale: "zh-CN",
            data: addedTableData,
            pagination: true,
            pageSize: 5,
            pageList: [3, 6],
            striped: true,
            clickToSelect: true,
            columns: [{
                field: 'productCode',
                title: '货品编码'
            }, {
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'onSaleQuantity',
                title: '可售总量',
                formatter: function (value, row) {
                    var defaultQuantity = 0;
                    if (!value) {
                        row.onSaleQuantity = defaultQuantity;
                        return '<input type="text" class="quantity-input" value="' + defaultQuantity + '"' + '/>';
                    } else {
                        return '<input type="text" class="quantity-input" value="' + value + '"' + '/>';
                    }
                },
                events: {
                    'blur .quantity-input': function (e, value, row, index) {
                        var inputValue = $(this).val();
                        $maskElement.$addedTable.bootstrapTable("updateCell", {
                            index: index,
                            field: 'onSaleQuantity',
                            value: inputValue
                        })
                    }
                }
            }, {
                field: 'inStockQuantity',
                title: '实物库存'
            }, {
                field: 'startTime',
                title: '有效时间',
                width: 100,
                editable: {
                    type: 'date',
                    title: '有效时间',
                    clear: false,
                    format: 'yyyy-mm-dd',
                    placement: 'left',
                    detepicker: {
                        language: 'zh-CN',
                        weekStart: 1
                    }
                }
            }, {
                field: 'endTime',
                title: '失效时间',
                width: 100,
                editable: {
                    type: 'date',
                    title: '失效时间',
                    clear: false,
                    format: 'yyyy-mm-dd',
                    placement: 'left',
                    detepicker: {
                        language: 'zh-CN',
                        weekStart: 1
                    }
                }
            }, {
                title: '操作',
                clickToSelect: false,
                formatter: function (value, row, index) {
                    return '<a class="alreadyadd-delete purchaseaddbtn salesadded" href="javascript:void(0)">' + '删除' + '</a>'
                },
                events: {
                    'click .alreadyadd-delete': function (event, value, row, index) {
                        tableObj[row.productCode] = false;
                        // 删除行
                        $maskElement.$addedTable.bootstrapTable('remove', {
                            field: 'productId',
                            values: [row.productId]
                        });
                        // 更新遮罩层左边的表格
                        $maskElement.$toBeAddTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'maskOpera',
                            value: tableObj[row.productCode]
                        });
                        var alreadyaddNum = $maskElement.$addedTable.bootstrapTable('getData').length;
                        $maskElement.$yhmaskProNum.text("(" + alreadyaddNum + ")");
                    }
                }
            }]
        })
    }

    // 修改预售计划表格
    function repairTableRender() {
        var $salesPlanMask = getMaskElement();
        var repairTableData = [{}];
        $salesPlanMask.$reviseTable.bootstrapTable({
            undefinedText: "",
            locale: "zh-CN",
            data: repairTableData,
            striped: true,
            clickToSelect: true,
            columns: [{
                field: 'productCode',
                title: '货品编码'
            }, {
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'onSaleQuantity',
                title: '销售总数',
                formatter: function (value, row) {
                    var defaultQuantity = 0;
                    if (!value) {
                        row.onSaleQuantity = defaultQuantity;
                        return '<input type="text" class="sale-quantity-input" value="' + defaultQuantity + '"' + '/>';
                    } else {
                        return '<input type="text" class="sale-quantity-input" value="' + value + '"' + '/>';
                    }
                },
                events: {
                    'blur .sale-quantity-input': function (e, value, row, index) {
                        var inputValue = Number($(this).val());
                        $salesPlanMask.$reviseTable.bootstrapTable("updateCell", {
                            index: index,
                            field: 'onSaleQuantity',
                            value: inputValue
                        })
                    }
                }
            }, {
                field: 'inStockQuantity',
                title: '实物库存'
            }, {
                field: 'allocatedQuantity',
                title: '已分配数量'
            }, {
                field: 'startTime',
                title: '有效时间',
                width: 100,
                formatter: function (value, row, index) {
                    return $.transformTime(value);
                },
                editable: {
                    type: 'date',
                    title: '有效时间',
                    clear: false,
                    format: 'yyyy-mm-dd',
                    placement: 'left',
                    detepicker: {
                        language: 'zh-CN',
                        weekStart: 1
                    }
                }
            }, {
                field: 'endTime',
                title: '失效时间',
                width: 100,
                formatter: function (value, row, index) {
                    return $.transformTime(value);
                },
                editable: {
                    type: 'date',
                    title: '失效时间',
                    clear: false,
                    pk: 1,
                    placement: 'left',
                    detepicker: {
                        language: 'zh-CN',
                        startView: 000000
                    },
                    format: 'yyyy-mm-dd'
                }
            }]
        })
    }

    // 获取遮罩层表格需要查询的参数
    function getMaskTableParams(params) {
        var productParams = {
            projectId: $("#projectlist").val(),
            productCode: $("#newproductcode").val(),
            productName: $("#newproductname").val(),
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        };
        return productParams;
    }

    // 预售管理表格
    function renderTable(URL) {
        var $maskElement = getMaskElement();
        var $projectList = $("#projectlist");
        var $yhCancelBtn = $("#yhcancel-btn");
        var projectId = $projectList.val();
        var projectName = $projectList.children("option:selected").text();
        // 预售计划列表
        var $yhTable = $("#yhtable");
        $yhTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/planSale/getSalePlanList",
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
            uniqueId: "salesPlanNo",
            columns: [{
                checkbox: true
            }, {
                field: 'salesPlanNo',
                title: '可销单号'
            }, {
                field: 'productCode',
                title: '货品编码'
            }, {
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'saleGuidePrice',
                title: '指导价'
            }, {
                field: 'onSaleQuantity',
                title: '可销总数'
            }, {
                field: 'allocatedQuantity',
                title: '已分配总数'
            }, {
                field: 'unallocatedQuantity',
                title: '未分配数量'
            }, {
                field: 'productActualQuantity',
                title: '货品实物库存'
            }, {
                field: 'startTime',
                title: '有效日期起'
            }, {
                field: 'endTime',
                title: '有效日期止'
            }, {
                field: 'createTime',
                title: '创建时间'
            }, {

                title: '操作',
                width: "140px",
                clickToSelect: false,
                formatter: function (value, row) {
                    if(row['salesPlanNo']){
                        return [
                            '<a target="_blank" class="btn btn-info" href="./sales-repair.html?projectId=' + projectId + '&projectName=' + projectName + '&salesPlanNo=' + row.salesPlanNo + '&saleGuidePrice=' + row.saleGuidePrice + '&productCode=' + row.productCode + '&productName=' + row.productName + '&productId=' + row.productId + '">' + '维护' + '</a> ',
                            '<a class="reviselink btn btn-primary" href="javascript:void(0)">',
                            '修改',
                            '</a>'
                        ].join('');
                    }else{
                        return ""
                    }
                },
                events: {
                    'click .reviselink': function (event, value, row) {
                        $maskElement.operaMask("yhrevise", "add-mask", "revise-mask", "修改");
                        var salesPlanNo = row.salesPlanNo;
                        var reviseParams = {
                            salesPlanNo: salesPlanNo
                        };
                        $.get(URL + "/planSale/getPlanSaleInfo", reviseParams, function (res) {
                            if (res.returnCode === 0) {
                                repairTableRender();
                                var repairDataList = res.data;
                                var arrReviseData = [];
                                arrReviseData.push(repairDataList);
                                $maskElement.$reviseTable.bootstrapTable('load', arrReviseData);
                            }
                        }, "json");
                    }
                }
            }],
            onCheck:function() {
                $yhCancelBtn.removeClass("yh-handle-btn").removeAttr("disabled");
            },
            onUncheck:function(){
                var tableSelectedData = $yhTable.bootstrapTable("getSelections");
                if(tableSelectedData.length === 0){
                    $yhCancelBtn.addClass("yh-handle-btn").attr("disabled",true);
                }
            },
            onUncheckAll:function() {
                $yhCancelBtn.addClass("yh-handle-btn").attr("disabled",true);
            },
            onCheckAll:function () {
                $yhCancelBtn.removeClass("yh-handle-btn").removeAttr("disabled");
            }
        })
    }

    // 预售管理表格的查询参数
    function getQueryParams(params) {
        var queryParams = {
            projectId: $("#projectlist").val(),
            productCode: $("#productcode").val(),
            productName: $("#productname").val(),
            createTime: $("#createtime").val(),
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

    function getPageElement() {
        var $yhTable = $("#yhtable");
        // 预销售新增按钮
        var $yhAddBtn = $("#yhadd-btn");
        // 预销售计划列表查询
        var $yhQueryBtn = $("#yhquery-btn");
        // 作废按钮
        var $yhExportBtn = $("#yhexport-btn");
        var $yhCancelBtn = $("#yhcancel-btn");
        // 后续可添加作废取消作废等功能按钮
        var pageElement = {
            $yhTable: $yhTable,
            $yhAddBtn: $yhAddBtn,
            $yhQueryBtn: $yhQueryBtn,
            $yhCancelBtn: $yhCancelBtn,
            $yhExportBtn: $yhExportBtn,
            refreshPurchaseTable: function () {
                this.$yhTable.bootstrapTable("refreshOptions", {pageNumber: 1});
            }
        };


        return pageElement;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // 遮罩层
    function getMaskElement() {
        // 遮罩层-新增遮罩层
        var $yhmask = $("#yhmask");
        // 待添加表格
        var $toBeAddTable = $("#tobeaddtable");
        // 已选产品表格
        var $addedTble = $("#addedtable");
        // 确认按钮
        var $yhMaskSubmit = $("#yhmask-submit");
        // 已选商品数量
        var $yhmaskProNum = $("#yhmask-pronum");
        // 修改表格
        var $reviseTable = $("#revise-table");
        var maskInfo = {
            $yhmask: $yhmask,
            $toBeAddTable: $toBeAddTable,
            $yhMaskSubmit: $yhMaskSubmit,
            $addedTable: $addedTble,
            $yhmaskProNum: $yhmaskProNum,
            $reviseTable: $reviseTable,
            // 新增按钮事件
            addTableRender: function (URL) {
                toBeAddTableRender(URL);
            },
            operaMask: function (maskStatus, hideEl, showEl, maskMenuText) {
                $yhmask.removeClass("yhmask-hide").attr("yhmask-status", maskStatus)
                    .find("#" + hideEl).addClass("yhmask-hide")
                    .end()
                    .find("#yhmask-menu").children("span").text(maskMenuText + "预售计划")
                    .end().end()
                    .find("#" + showEl).removeClass("yhmask-hide");
            }
        };
        return maskInfo;
    }

});

