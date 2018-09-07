$(function () {
    //全局变量
    var projectSalesInfo = getInfoFromPlanManagement();
    var customerStatus = {};
    var addedCustomerList = [];
    var addedCustomerData = [];

    function dataInit() {
        addedCustomerList.splice(0, addedCustomerList.length);
        addedCustomerData.splice(0, addedCustomerData.length);
        $("#right-addnum").text('(' + 0 + ')');
        for (var key in customerStatus) {
            delete customerStatus[key];
        }
        $("#newprotable").bootstrapTable("destroy");
        $("#oldprotable").bootstrapTable("destroy");
    }

    managementInit();

    function managementInit() {
        // 全局接口
        var URL = $.getfenxiaoURL();
        var infoFromPlanManagement = getInfoFromPlanManagement();
        //从预销管理中页面获取信息
        bindFrontEvent(URL);
        //后端绑定事件
        bindBackEvent(URL);
        //初始化查询表格
        repairTable(URL);
        //初始化客户列表
        toBeAddTable(URL);
        infoFromPlanManagement.assignmentProjectVal();
    }

    //从预销管理中页面获取信息
    function getInfoFromPlanManagement() {
        var $projectId = $("#projectid");
        var repairProjectId = $.getUrlParam("projectId");
        var repairProjectName = $.getUrlParam("projectName");
        var salesPlanNo = $.getUrlParam("salesPlanNo");
        var saleGuidePrice = $.getUrlParam("saleGuidePrice");
        var productId = $.getUrlParam("productId");
        var productCode = $.getUrlParam("productCode");
        var productName = $.getUrlParam("productName");

        var projectSalesInfo = {
            projectId: repairProjectId,
            projectName: repairProjectName,
            salesPlanNo: salesPlanNo,
            saleGuidePrice: saleGuidePrice,
            productId: productId,
            productCode: productCode,
            productName: productName,
            assignmentProjectVal: function () {
                $projectId.val(repairProjectName);
            }
        };
        return projectSalesInfo;
    }

    function bindFrontEvent(URL) {
        // 1)查询待添加客户
        $("#mask-query").click(function () {
            $("#oldprotable").bootstrapTable("refreshOptions", {pageNumber: 1});
        });

        //1)查询按钮,查询预售单
        $("#plansalesquerybtn").click(function () {
            $("#yhtable").bootstrapTable("refreshOptions", {pageNumber: 1});
        });

        //2)新增按钮,打开遮罩层
        $("#yhadd-btn").click(function () {
            //初始化客户列表
            toBeAddTable(URL);
            $("#yhmask").removeClass("yhmask-hide").attr("yhmaskcontent", "addmask")
                .find("#editmask-content").addClass("yhmask-hide")
                .end()
                .find("#yhmask-menu").children("span").text("添加客户")
                .end().end()
                .find("#yhmask-addcontent").removeClass("yhmask-hide");
        });


    }

    function bindBackEvent(URL) {
        $("#yhmask-switch").click(function () {
            dataInit();
        });
        $("#yhmask-cancel").click(function () {
            dataInit();
        });

        //2)新增遮罩层提交按钮
        $("#yhmask-submit").click(function () {
            var yhmaskcontent = $("#yhmask").attr("yhmaskcontent");
            if (yhmaskcontent === "addmask") {
                var putCustomerData = $("#newprotable").bootstrapTable("getData");
                //判断是否存在数据
                if (putCustomerData !== 0) {
                    var getTempParams = {
                        projectId: projectSalesInfo.projectId,
                        projectName: projectSalesInfo.projectName,
                        productId: projectSalesInfo.productId,
                        productCode: projectSalesInfo.productCode,
                        productName: projectSalesInfo.productName,
                        salesPlanNo: projectSalesInfo.salesPlanNo
                    };
                    $.each(putCustomerData, function (index, item) {
                        if (item.customerAccount) {
                            delete item.customerAccount;
                        }
                        $.extend(item, getTempParams);
                    });
                    var strPutCustomerData = JSON.stringify(putCustomerData);
                    var confirmParams = {
                        salePlanItemJson: strPutCustomerData
                    };
                    $.post(URL + "/planSaleItem/putCustomerSalePlan", confirmParams, function (res, status, xhr) {
                        if (res.returnCode === 0) {
                            $("#yhtable").bootstrapTable("refreshOptions", {pageNumber: 1});
                            $("#yhmask").addClass("yhmask-hide");
                            dataInit();
                        } else {
                            var resMessage = res.message;
                            $.renderLayerMessage(resMessage)
                        }
                    }, "json")
                } else {
                    $.renderLayerMessage("请选择客户再提交！");
                }
            } else if (yhmaskcontent === "editmask") {
                var $editTable = $("#edittable");
                var editTableData = $editTable.bootstrapTable("getData");
                if (editTableData.length !== 0) {
                    var editDoneData = $editTable.bootstrapTable("getData")[0];
                    var editSubmitParams = {
                        itemId: editDoneData.itemId,
                        adjustNumber: editDoneData.adjustNumber,
                        brandPrepaidUnit: editDoneData.brandPrepaidUnit,
                        brandPrepaidDiscount: editDoneData.brandPrepaidDiscount,
                        yhPrepaidUnit: editDoneData.yhPrepaidUnit,
                        yhPrepaidDiscount: editDoneData.yhPrepaidDiscount,
                        startDate: editDoneData.startTime,
                        endDate: editDoneData.endTime
                    };
                    $.get(URL + "/planSaleItem/editItemInfo", editSubmitParams, function (res) {
                        if (res.returnCode === 0) {
                            $("#yhtable").bootstrapTable("refreshOptions", {pageNumber: 1});
                            $("#yhmask").addClass("yhmask-hide");
                            dataInit();
                        }
                    }, "json")
                }
            }
        });


        // 4)作废
        $("#yhcancel-btn").click(function () {
            var blankOutData = $("#yhtable").bootstrapTable('getSelections');
            var itemIdStr = "";
            $.each(blankOutData, function (index, receiveItem) {
                if (itemIdStr.length !== 0) {
                    itemIdStr += ",";
                }
                itemIdStr += receiveItem["itemId"];
            });
            if (blankOutData.length !== 0) {
                var blankOutParams = {
                    itemIdJson: itemIdStr
                };
                $.get(URL + "/planSaleItem/cancelPlanSaleItemOrder", blankOutParams, function (res, status, xhr) {
                    if (res.returnCode === 0) {
                        $("#yhtable").bootstrapTable("refreshOptions", {pageNumber: 1});
                    }
                }, "json")
            } else {
                $.renderLayerMessage("请先选择客户！", 2);
            }
        })

    }

    function format(amount, digitNumber) {
        if (typeof digitNumber === 'undefined') {
            digitNumber = 6;
        }
        return accounting.formatMoney(amount, "", digitNumber, "", ".")
    }

    //外部表格的查询参数
    function getQueryParamsOut(params) {
        var urlParams = getInfoFromPlanManagement();
        var queryParams = {
            salesPlanNo: urlParams.salesPlanNo,
            customerId: $("#customerid").val(),
            customerName: $("#customername").val(),
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

    function getCustomerParams(params) {
        return {
            projectId: projectSalesInfo.projectId,
            customerId: $("#newproductcode").val(),
            customerName: $("#newproductname").val(),
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        }
    }

    // 添加客户渲染表格
    function responseCustomer(res) {
        if (res['returnCode'] === 0 && res['data']['list']['length'] !== 0 && res['data']['total'] !== 0 && typeof res['data']['total'] !== 'undefined') {
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

    // 待添加客户的表格
    function toBeAddTable(URL) {
        var $oldProductTable = $("#oldprotable");
        $oldProductTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/planSaleItem/getCustomerList",
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
            queryParams: getCustomerParams,
            responseHandler: responseCustomer,// 请求数据成功后，渲染表格前的方法
            columns: [
                // {
                //     field: 'customerId',
                //     title: '客户帐号'
                // },
                {
                    field: 'customerName',
                    title: '客户名称'
                }, {

                    title: '操作',
                    field: 'maskOpera',
                    width: 46,
                    clickToSelect: false,
                    formatter: function (value, row) {
                        if (customerStatus[row.customerId]) {
                            return '已添加'
                        } else {
                            return '<a class="pur-addbtn purchaseaddbtn salesadd" href="javascript:void(0)">' + '添加' + '</a>'
                        }
                    },
                    events: {
                        'click .salesadd': function (e, value, row, index) {
                            addedCustomerList.push(row);
                            customerStatus[row.customerId] = true;
                            if (customerStatus[row.customerId]) {
                                $(this).removeClass("pur-addbtn").addClass("puralreadyadd");
                                addedCustomerData = addedCustomerList;
                                var alreadyaddNum = addedCustomerData.length;
                                $("#right-addnum").text("(" + alreadyaddNum + ")");
                                addEdTable();
                                // 通过预销管理拿到的数据
                                var temPrice = {
                                    saleGuidePrice: projectSalesInfo.saleGuidePrice
                                };
                                $.each(addedCustomerData, function (index, item) {
                                    $.extend(item, temPrice);
                                });
                                $("#newprotable").bootstrapTable('load', addedCustomerData);
                                $oldProductTable.bootstrapTable('updateCell', {
                                    index: index,
                                    field: 'maskOpera',
                                    value: customerStatus[row.productCode]
                                });
                            }
                        }
                    }
                }]
        })
    }

    // 已选客户的表格
    function addEdTable() {
        var $newProTable = $("#newprotable");
        var $oldProductTable = $("#oldprotable");
        var addedData = [{}];
        $newProTable.bootstrapTable({
            undefinedText: "",
            locale: "zh-CN",
            data: addedData,
            pagination: true,
            pageSize: 5,
            search: true,
            pageList: [3, 6],
            striped: true,
            clickToSelect: true,
            columns: [{
                field: 'customerName',
                title: '客户名称'
            }, {
                field: 'saleGuidePrice',
                title: '销售指导价'
            }, {
                field: 'onSaleQuantity',
                title: '分配数量',
                formatter: function (value, row) {
                    if (!value) {
                        var defaultOnSaleQuantity = 0;
                        row.onSaleQuantity = defaultOnSaleQuantity;
                        return '<input type="text" class="sale-quantity-input"  value="' + defaultOnSaleQuantity + '">';
                    } else {
                        return '<input type="text" class="sale-quantity-input"  value="' + value + '">';
                    }
                },
                events: {
                    'blur .sale-quantity-input': function (e, value, row, index) {
                        var inputValue = $(this).val();
                        $newProTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'onSaleQuantity',
                            value: inputValue
                        });
                    }
                }
            }, {
                field: 'brandPrepaidDiscount',
                title: '品牌商支持折扣',
                formatter: function (value, row) {
                    if (!value) {
                        var defaultBrandPrepaidDiscount = 0;
                        row.brandPrepaidDiscount = defaultBrandPrepaidDiscount.toFixed(6);
                        return '<input type="text" class="pre-input brand-discount" value="' + defaultBrandPrepaidDiscount + '">' + '%';
                    } else {
                        var showValue = (value * 100).toFixed(6);
                        if (showValue > 100) {
                            return '<input type="text" class="pre-input table-warning-input brand-discount" value="' + showValue + '">' + '%';
                        }
                        return '<input type="text" class="pre-input brand-discount" value="' + showValue + '">' + '%';
                    }
                },
                events: {
                    'blur .brand-discount': function (e, value, row, index) {
                        var inputValue = Number($(this).val());
                        inputValue = ((inputValue * 1000000) / 100 / 1000000).toString();
                        if (inputValue < 100 || inputValue == 100) {
                            $(this).removeClass("table-warning-input");
                            $newProTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'brandPrepaidDiscount',
                                value: inputValue
                            });
                            var _brandPrepaidUnit = Number(row.saleGuidePrice * inputValue);
                            $newProTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'brandPrepaidUnit',
                                value: _brandPrepaidUnit
                            });
                            var _wholesalePrice = Number(row.saleGuidePrice * (1 - inputValue - row.yhPrepaidDiscount));
                            console.log(_wholesalePrice);
                            $newProTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'wholesalePrice',
                                value: _wholesalePrice
                            });
                        } else {
                            $(this).addClass("table-warning-input");
                        }
                    }
                }
            }, {
                field: 'brandPrepaidUnit',
                title: '品牌商单价支持',
                formatter: function (value, row) {
                    var defaultBrandPrepaidUnit = 0;
                    if (!value && row.brandPrepaidDiscount == 0) {
                        row.brandPrepaidUnit = defaultBrandPrepaidUnit.toFixed(6);
                        return defaultBrandPrepaidUnit;
                    } else {
                        var showValue = Number(value).toFixed(6);
                        row.brandPrepaidUnit = showValue;
                        return showValue;
                    }
                }
            }, {
                field: 'yhPrepaidDiscount',
                title: 'YH销售折扣支持',
                formatter: function (value, row) {
                    if (!value) {
                        var dafaultPrepaidDiscount = 0;
                        row.yhPrepaidDiscount = dafaultPrepaidDiscount.toFixed(6);
                        return '<input type="text" class="pre-input yh-prepaid-discount" value="' + dafaultPrepaidDiscount + '">' + '%';
                    } else {
                        var showValue = (value * 100).toFixed(6);
                        if (showValue > 100) {
                            return '<input type="text" class="pre-input table-warning-input yh-prepaid-discount" value="' + showValue + '">' + '%';
                        }
                        return '<input type="text" class="pre-input yh-prepaid-discount" value="' + showValue + '">' + '%';
                    }
                },
                events: {
                    'blur .yh-prepaid-discount': function (e, value, row, index) {
                        var inputValue = Number($(this).val());
                        inputValue = ((inputValue * 1000000) / 100 / 1000000).toString();
                        if (inputValue < 100 || inputValue == 100) {
                            $(this).removeClass("table-warning-input");
                            $newProTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'yhPrepaidDiscount',
                                value: inputValue
                            });
                            var _yhPrepaidUnit = Number(row.saleGuidePrice * inputValue);
                            $newProTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'yhPrepaidUnit',
                                value: _yhPrepaidUnit
                            });
                            var _wholesalePrice = Number(row.saleGuidePrice * (1 - inputValue - row.brandPrepaidDiscount));
                            console.log(_wholesalePrice);
                            $newProTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'wholesalePrice',
                                value: _wholesalePrice
                            });
                        } else {
                            $(this).addClass("table-warning-input");
                        }
                    }
                }
            }, {
                field: 'yhPrepaidUnit',
                title: 'YH支持金',
                formatter: function (value, row) {
                    var defaultYhPrepaidUnit = 0;
                    if (!value && row.yhPrepaidDiscount == 0) {
                        row.yhPrepaidUnit = defaultYhPrepaidUnit.toFixed(6);
                        return defaultYhPrepaidUnit;
                    } else {
                        var showValue = Number(value).toFixed(6);
                        return showValue;
                    }
                }
            }, {
                field: 'wholesalePrice',
                title: '出货价',
                formatter: function (value, row) {
                    if (!value) {
                        row.wholesalePriceDouble = 0;
                        return 0
                    }
                    return Number(value).toFixed(6);
                }
            }, {
                field: 'startTime',
                title: '有效时间',
                width: 100,
                formatter: function (value, row) {
                    var _startTime = $.transformTime(value);
                    row.startTime = _startTime;
                    return _startTime;
                },
                editable: {
                    type: 'date',
                    title: '有效时间',
                    clear: false,
                    pk: 1,
                    placement: 'left',
                    detepicker: {
                        language: 'zh-CN',
                        startView: 000000
                    },
                    format: 'yyyy-mm-dd'
                }
            }, {
                field: 'endTime',
                title: '失效时间',
                width: 100,
                formatter: function (value, row) {
                    var _endTime = $.transformTime(value);
                    row.endTime = _endTime;
                    return _endTime;
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
            }, {
                title: '操作',
                clickToSelect: false,
                formatter: function (value, row, index) {
                    return '<a class="alreadyadd-delete purchaseaddbtn salesadded" href="javascript:void(0)">删除</a>'
                },
                events: {
                    'click .salesadded': function (e, value, row, index) {
                        customerStatus[row.customerId] = false;
                        // 删除行
                        $newProTable.bootstrapTable('remove', {
                            field: 'customerId',
                            values: [row.customerId]
                        });
                        // 更新遮罩层坐标的表格
                        $oldProductTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'maskOpera',
                            value: customerStatus[row.customerId]
                        });
                        var alreadyaddNum = $newProTable.bootstrapTable('getData').length;
                        $("#right-addnum").text("(" + alreadyaddNum + ")");

                    }
                }
            }]
        })
    }

    // 预售维护表格
    function repairTable(URL) {
        var $yhTable = $("#yhtable");
        $yhTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/planSaleItem/getPlanSaleItemList",
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
            queryParams: getQueryParamsOut,
            responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法
            columns: [{
                checkbox: true
            }, {
                field: 'customerId',
                title: '客户帐号'
            }, {
                field: 'customerName',
                title: '客户名称'
            }, {
                field: 'onSaleQuantity',
                title: '分配数量'
            }, {
                field: 'saleOccupationQuantity',
                title: '销售占用'
            }, {
                field: 'soldQuantity',
                title: '已销数量'
            }, {
                field: 'unallocatedQuantity',
                title: '客户可下单数量'
            }, {
                field: 'currencyCode',
                title: '币种'
            }, {
                field: 'brandPrepaidDiscount',
                title: '品牌商支持折扣',
                formatter: function (value) {
                    if (value) {
                        return (Number(value * 100).toFixed(6)) + ' %';
                    }
                }
            }, {
                field: 'yhPrepaidDiscount',
                title: 'YH销售支持折扣',
                formatter: function (value) {
                    if (value) {
                        return (Number(value * 100).toFixed(6)) + ' %';
                    }
                }
            }, {
                field: 'wholesalePrice',
                title: '出货价'
            }, {
                field: 'startTime',
                title: '有效日期起',
                formatter: function (value) {
                    return $.transformTime(value);
                }
            }, {
                field: 'endTime',
                title: '有效日期止',
                formatter: function (value) {
                    return $.transformTime(value);
                }
            }, {
                field: 'lastUpdateTime',
                title: '最终修改时间',
                formatter: function (value) {
                    return $.transformTime(value);
                }
            }, {
                title: '操作',
                width: 90,
                clickToSelect: false,
                formatter: function (value, row) {
                    if (!row.customerId) {
                        return ""
                    } else {
                        return [
                            '<a class="editbtn btn btn-primary">',
                            '编辑',
                            '</a>'
                        ].join('');
                    }
                },
                events: {
                    'click .editbtn': function (event, value, row) {
                        $("#yhmask").removeClass("yhmask-hide").attr("yhmaskcontent", "editmask")
                            .find("#yhmask-addcontent").addClass("yhmask-hide")
                            .end()
                            .find("#yhmask-menu").children("span").text("编辑预售计划")
                            .end().end()
                            .find("#editmask-content").removeClass("yhmask-hide");
                        editTable();
                        var editParams = {
                            itemId: row.itemId
                        };
                        $.get(URL + "/planSaleItem/getEditItemInfo", editParams, function (res, stauts, xhr) {
                            if (res.returnCode === 0) {
                                var editTableLoadData = res.data;
                                var arrEditTableLoadData = [];
                                arrEditTableLoadData.push(editTableLoadData);
                                $("#edittable").bootstrapTable('load', arrEditTableLoadData);
                            }
                        }, "json")
                    }
                }
            }]
        })
    }

    // 编辑表格
    function editTable() {
        var editData = [{}];
        var $editTable = $("#edittable");
        $editTable.bootstrapTable({
            undefinedText: "",
            pagination: true,
            pageSize: 5,
            pageList: [3, 6],
            locale: "zh-CN",
            data: editData,
            striped: true,
            clickToSelect: true,
            columns: [{
                field: 'customerName',
                title: '客户名称'
            }, {
                field: 'remainSaleAmount',
                title: '客户可下单数'
            }, {
                field: 'unallocatedQuantity',
                title: '剩余销售总数'
            }, {
                field: 'adjustNumber',
                title: '调节变更数',
                formatter: function (value, row) {
                    var defaultAdjustNumber = 0;
                    if (!value) {
                        row.adjustNumber = defaultAdjustNumber;
                        return '<input type="text" class="adjust-number-input" value="' + defaultAdjustNumber + '">';
                    } else {
                        return '<input type="text" class="adjust-number-input" value="' + value + '">';
                    }
                },
                events: {
                    'blur .adjust-number-input': function (e, value, row, index) {
                        var inputValue = Number($(this).val());
                        $editTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'adjustNumber',
                            value: inputValue
                        });
                    }
                }
            }, {
                field: 'saleGuidePrice',
                title: '指导价'
            }, {
                field: 'brandPrepaidDiscount',
                title: '品牌商支持折扣',
                width: 120,
                formatter: function (value, row) {
                    var defaultBrandPrepaidDiscount = 0;
                    if (!value) {
                        row.defaultBrandPrepaidDiscount = defaultBrandPrepaidDiscount;
                        return '<input type="text" class="pre-input discount-input" value="' + defaultBrandPrepaidDiscount + '"' + '/>' + '%';
                    } else {
                        var showValue;
                        if (value < 1) {
                            showValue = format(value * 100);
                        } else {
                            showValue = format(Number(value));
                        }
                        return '<input type="text" class="pre-input discount-input" value="' + showValue + '"' + '/>' + '%';
                    }
                },
                events: {
                    'blur .discount-input': function (event, value, row, index) {
                        var inputValue = $(this).val();
                        inputValue = ((inputValue * 1000000) / 100 / 1000000).toString();
                        if (inputValue < 100 || inputValue == 100) {
                            $(this).removeClass("table-warning-input");
                            $editTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'brandPrepaidDiscount',
                                value: inputValue
                            });
                            var _brandPrepaidUnit = format(Number(row.saleGuidePrice * inputValue));
                            $editTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'brandPrepaidUnit',
                                value: _brandPrepaidUnit
                            });
                            var _wholesalePrice = format((row.saleGuidePrice * (1 - inputValue - row.yhPrepaidDiscount)));
                            $editTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'wholesalePrice',
                                value: _wholesalePrice
                            });
                        } else {
                            $(this).addClass("table-warning-input");
                        }
                    }
                }
            }, {
                field: 'brandPrepaidUnit',
                title: '品牌商单价支持',
                formatter: function (value, row) {
                    if (!value && row.brandPrepaidDiscount == 0) {
                        var defaultBrandPrepaidUnit = 0;
                        row.brandPrepaidUnit = 0;
                        return defaultBrandPrepaidUnit;
                    } else {
                        return value;
                    }
                }
            }, {
                field: 'yhPrepaidDiscount',
                title: 'YH销售支持折扣',
                formatter: function (value, row) {
                    if (!value) {
                        var defaultYhPrepaidDiscount = 0;
                        row.yhPrepaidDiscount = defaultYhPrepaidDiscount;
                        return '<input type="text" class="pre-input discount-input" value="' + defaultYhPrepaidDiscount + '"' + '/>' + '%';
                    } else {
                        var showValue;
                        if (value < 1) {
                            showValue = format(value * 100);
                        } else {
                            showValue = format(Number(value));
                        }
                        return '<input type="text" class="pre-input discount-input" value="' + showValue + '"' + '/>' + '%';
                    }
                },
                events: {
                    'blur .discount-input': function (event, value, row, index) {
                        var inputValue = $(this).val();
                        inputValue = ((inputValue * 1000000) / 100 / 1000000).toString();
                        if (inputValue < 100 || inputValue == 100) {
                            $(this).removeClass("table-warning-input");
                            $editTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'yhPrepaidDiscount',
                                value: inputValue
                            });
                            var _yhPrepaidUnit = format(Number(row.saleGuidePrice * inputValue));
                            $editTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'yhPrepaidUnit',
                                value: _yhPrepaidUnit
                            });
                            var _wholesalePrice = format((row.saleGuidePrice * (1 - inputValue - row.brandPrepaidDiscount)));
                            $editTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'wholesalePrice',
                                value: _wholesalePrice
                            });
                        } else {
                            $(this).addClass("table-warning-input");
                        }
                    }
                }
            }, {
                field: 'yhPrepaidUnit',
                title: 'YH支持金',
                formatter: function (value, row) {
                    if (!value && row.yhPrepaidDiscount == 0) {
                        var defaultPrepaidUnit = 0;
                        row.yhPrepaidUnit = 0;
                        return defaultPrepaidUnit;
                    } else {
                        return value;
                    }
                }
            }, {
                field: 'wholesalePrice',
                title: '出货价'
            }, {
                field: 'startTime',
                title: '有效时间',
                width: 100,
                formatter: function (value, row) {
                    var _startTime = $.transformTime(value);
                    row.startTime = _startTime;
                    return _startTime;
                },
                editable: {
                    type: 'date',
                    title: '有效时间',
                    clear: false,
                    placement: 'left',
                    detepicker: {
                        language: 'zh-CN'
                    },
                    format: 'yyyy-mm-dd'
                }
            }, {
                field: 'endTime',
                title: '失效时间',
                width: 100,
                formatter: function (value, row) {
                    var _endTime = $.transformTime(value);
                    row.endTime = _endTime;
                    return _endTime;
                },
                editable: {
                    type: 'date',
                    title: '失效时间',
                    clear: false,
                    placement: 'left',
                    detepicker: {
                        language: 'zh-CN'
                    },
                    format: 'yyyy-mm-dd'
                }
            }]
        })
    }
});