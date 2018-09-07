$(function () {

    //全局变量
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
        $("#right-addnum").text('('+0+')');
        $("#leftbox-table").bootstrapTable("destroy");
        $("#rightbox-table").bootstrapTable("destroy");
    }

    $("#yhmask-switch").click(function () {
        dataInit();
    });

    $("#yhmask-cancel").click(function () {
        dataInit();
    });


    // 控制逻辑
    function managementInit() {
        // 全局接口
        var URL = $.getfenxiaoURL();
        // 初始化货品输入框禁止输入
        $(".yhpurinput").attr("disabled", true);
        //下拉框初始化(项目信息)
        renderSelectList(URL);
        //前端事件绑定
        bindFrontEvent();
        //后端事件绑定
        bindBackEvent(URL);
        //渲染表格
        renderTable(URL);
    }

    // 下拉框初始化
    function renderSelectList(URL) {
        layui.use(['laydate'],function () {
            var laydate = layui.laydate;
            laydate.render({
                elem: '#yhbusiness-date',
                format: 'yyyy-MM-dd'
            });
        });
        $("#inboundtype").select2({
            minimumResultsForSearch: 6
        });
        // 项目列表
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName");
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

    //前端事件绑定
    function bindFrontEvent() {
        // 仓库下拉框变化
        $("#warehouselist").change(function () {
            var itemValue = $(this).children("option:selected").text();
            var $shopAddress = $("#shopaddress");
            $shopAddress.val(itemValue);
        });

        // 弹出框添加商品后的确认按钮
        var $yhmaskSubmit = $("#yhmask-submit");
        var $purchaseVariety = $("#purchase-variety");
        $yhmaskSubmit.click(function () {
            // 已经添加好的商品
            var alreadyAddTableData = $("#rightbox-table").bootstrapTable('getData');
            var alreadyProLen = alreadyAddTableData.length;
            if (alreadyProLen !== 0) {
                $("#yhmask").addClass("yhmask-hide");
                $("#couponamount").attr("disabled", false);
                $(".yhpurinput").attr("disabled", false);
                $("#purchase-prepaid").attr("disabled", false);
                $("#otheraddtable").bootstrapTable('load', alreadyAddTableData);
                // 添加商品的种类数(个数)
                $purchaseVariety.text(alreadyProLen);
                dataInit();
            } else {
                $.renderLayerMessage("请选择商品！");
            }
        });
    }

    //后端事件绑定
    function bindBackEvent(URL) {
        // 1. 根据对应的项目添加商品
        $("#entry-addbtn").click(function () {
            var projectId = $("#projectlist").val();
            if (!projectId) {
                return;
            } else {
                $("#yhmask").removeClass("yhmask-hide");
                //添加产品列表
                maskTableRender(URL);
            }
        });

        // 3. 货品查询
        $("#searchProduct").click(function () {
            $("#leftbox-table").bootstrapTable("refreshOptions", {pageNumber: 1});
        });

        //4. 新增采购单接口
        var $entrySubmit = $("#entry-submit");
        $entrySubmit.click(function () {
            var itemJson = $("#otheraddtable").bootstrapTable('getData');
            var targetJson = JSON.stringify(itemJson);
            var orderParams = {
                //项目ID
                projectId: $("#projectlist").val(),
                //仓库ID
                warehouseId: $("#warehouselist").val(),
                //仓库名称
                warehouseName: $("#warehouselist").children("option:selected").text(),
                //收货地址
                recieveAddress: $("#shopaddress").val(),
                //供应商ID
                supplierName: $("#supplierlist").children("option:selected").text(),
                //业务发生日期
                businessDate: $("#yhbusiness-date").val(),
                //入库类型
                inboundType : $("#inboundtype").val(),
                //备注
                remark: $("#remask").val(),
                //采购货品列表jsonArray
                purchaseItemInfoJson: targetJson
            };
            $.post(URL + "/manualInbound/insertInbound", orderParams, function (res) {
                if (res.returnCode === 0) {
                    window.location.href = './storage-entrymanagement.html';
                }
            }, "json")
        })
    }


    // 添加商品完毕后渲染商品表格
    function renderTable(URL) {
        var tableData = [{}];
        var $otheraddtable = $("#otheraddtable");
        $otheraddtable.bootstrapTable({
            undefinedText: '',
            striped: true,
            data: tableData,
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
                field: 'quantity',
                title: '入库数量/个',
                formatter: function (value, row) {
                    if (!value) {
                        value = 1;
                        row.quantity = 1;
                        return '<input type="text" class="yhpurinput numberinput" value="' + value + '"' + '/>';
                    } else {
                        row.quantity = value;
                        return '<input type="text" class="yhpurinput numberinput" value="' + value + '"' + '/>';
                    }
                },
                events: purNumberEvents = {
                    'blur .numberinput': function (event, value, row, index) {
                        var $val = $(this).val();
                        $otheraddtable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'quantity',
                            value: $val
                        });

                    }
                }
            }, {
                field: 'purchasePrice',
                title: '采购单价/元',
                formatter: function (value, row) {
                    if (!value) {
                        value = 1;
                        row.purchasePrice = 1;
                        return '<input type="text" class="yhpurinput numberinput" value="' + value + '"' + '/>';
                    } else {
                        row.purchasePrice = value;
                        return '<input type="text" class="yhpurinput numberinput" value="' + value + '"' + '/>';
                    }
                },
                events: purNumberEvents = {
                    'blur .numberinput': function (event, value, row, index) {
                        var $val = $(this).val();
                        $otheraddtable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'purchasePrice',
                            value: $val
                        });

                    }
                }
            }, {
                field: 'costPrice',
                title: '成本价/元',
                formatter: function (value, row) {
                    if (!value) {
                        value = 1;
                        row.costPrice = 1;
                        return '<input type="text" class="yhpurinput numberinput" value="' + value + '"' + '/>';
                    } else {
                        row.costPrice = value;
                        return '<input type="text" class="yhpurinput numberinput" value="' + value + '"' + '/>';
                    }
                },
                events: purNumberEvents = {
                    'blur .numberinput': function (event, value, row, index) {
                        var $val = $(this).val();
                        $otheraddtable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'costPrice',
                            value: $val
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
                        $otheraddtable.bootstrapTable('remove', {
                            field: 'productCode',
                            values: [row.productCode]
                        })
                    }
                }
            }]
        })
    }

    ///////////////////////////////////////////////////
    //遮罩层

    // 遮罩层左边的表格
    function maskTableRender(URL) {
        var $leftboxTable = $("#leftbox-table");
        var $rightboxTable = $("#rightbox-table");
        var $rightTableNumber = $("#right-addnum");
        $leftboxTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/purchase/foundation/getProductInfoList",
            queryParamsType: '',
            silent: true,
            undefinedText: '',
            striped: true,
            dataField: 'data',
            pagination: true,
            pageSize: 6, // 一页显示数据条数
            pageList: [4, 6, 8],
            pageNumber: 1, // 首页页码
            sidePagination: "server",
            queryParams: getQueryParams,
            responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法
            columns: [{
                field: 'productCode',
                title: '货品编码',
                width: '30%'
            }, {
                field: 'productName',
                title: '货品名称',
                width: '50%'
            }, {
                field: 'maskOpera',
                title: '操作',
                width: '20%',
                clickToSelect: false,
                rowAttributes: function (row, index) {
                    return {
                        'rowIdIndex': index
                    }
                },
                formatter: function (value, row) {
                    if (tableObj[row.productCode]) {
                        return '已添加'
                    } else {
                        return '<a class="pur-addbtn purchaseaddbtn" href="javascript:void(0)">' + '添加' + '</a>'
                    }
                },
                events: {
                    'click .pur-addbtn': function (e, value, row, index) {
                        alreadyDataList.push(row);
                        tableObj[row.productCode] = true;
                        if (tableObj[row.productCode]) {
                            $(this).removeClass("pur-addbtn").addClass("puralreadyadd");
                            var alreadyaddNum = alreadyDataList.length;
                            $rightTableNumber.text("(" + alreadyaddNum + ")");
                            alreadyTableRender();
                            $rightboxTable.bootstrapTable('load', alreadyDataList);
                            $leftboxTable.bootstrapTable('updateCell', {
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

    // 返回成功渲染表格
    function responseHandler(res) {
        var $leftboxTable = $("#leftbox-table");
        $leftboxTable.bootstrapTable('hideLoading');
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

    // 遮罩层查询参数
    function getQueryParams(params) {
        var projectId = $("#projectlist").val();
        var productCode = $("#productCode").val();
        var productName = $("#productName").val();
        return {
            projectId: projectId,
            productCode: productCode,
            productName: productName,
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        }
    }

    // 右边已选择的表格
    function alreadyTableRender() {
        var $leftboxTable = $("#leftbox-table");
        var $rightboxTable = $("#rightbox-table");
        var $rightTableNumber = $("#right-addnum");
        var rightTableData = [{}];
        $rightboxTable.bootstrapTable({
            undefinedText: "",
            clickToSelect: true,
            locale: "zh-CN",
            data: rightTableData,
            striped: true,
            pagination: true,
            pageNumber: 1,
            pageSize: 6,
            pageList: [4, 6, 8],
            paginationPreText: "&lt;",
            paginationNextText: "&lt;",
            columns: [{
                field: 'productId',
                title: '货品编码',
                width: "120px"
            }, {
                field: 'productName',
                title: '货品名称',
                width: "250px"
            }, {
                title: '操作',
                width: "80px",
                clickToSelect: false,
                formatter: function () {
                    return '<a class="alreadyadd-delete purchaseaddbtn" href="javascript:void(0)">' + '删除' + '</a>'
                },
                events: {
                    'click .alreadyadd-delete': function (event, value, row, index) {
                        tableObj[row.productCode] = false;
                        var alreadyaddNum = alreadyDataList.length;
                        $rightTableNumber.text("(" + alreadyaddNum + ")");
                        // 删除行
                        $rightboxTable.bootstrapTable('remove', {
                            field: 'productId',
                            values: [row.productId]
                        });
                        // 更新遮罩层坐标的表格
                        $leftboxTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'maskOpera',
                            value: tableObj[row.productCode]
                        });
                    }
                }
            }]
        })
    }

});

