 $(function () {

    //全局变量

    // 返利比例
    var projectRate = [{}];
    // 遮罩层表格内容变化的状态
    var tableObj = {};
    // 已添加表格的数据
    var alreadyDataList = [];

    managementInit();
    // 控制逻辑
    function managementInit() {
        // 全局接口
        var URL = $.getfenxiaoURL();
        renderDate();
        // 下拉框初始化(项目信息)
        renderSelectList(URL);
        //前端事件绑定
        bindFrontEvent(URL);
        //后端事件绑定
        bindBackEvent(URL);
        //渲染表格
        renderTable();
    }

    // 渲染日期组组件
    function renderDate() {
        $("#logistic").select2({
            minimumResultsForSearch: 6
        });

        $("#purchase-type").select2({
            minimumResultsForSearch: 6
        });

        $("#invoice").select2({
            minimumResultsForSearch: 6
        });

        layui.config({
            version: '1515376178709' //为了更新 js 缓存
        });

        layui.use(['laydate'], function () {
            var laydate = layui.laydate;
            laydate.render({
                elem: '#yhbusiness-date',
                format: 'yyyy-MM-dd'
            });
            laydate.render({
                elem: '#yharrival-date',
                format: 'yyyy-MM-dd'
            });
            laydate.render({
                elem: '#yhexpiry-date',
                format: 'yyyy-MM-dd'
            });
        });
    }

     // 下拉框初始化
     function renderSelectList(URL) {
         // 项目列表
         $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
             .then(function (res) {
                 var projectInfo = res.data;
                 $.each(projectInfo, function (index, projectItem) {
                     // 项目列表中附带的返利比例
                     projectRate[index] = {
                         //返利使用比列
                         couponUseReferRate: projectItem.couponUseReferRate,
                         couponTotalRate: projectItem.monthlyCouponGenerateRate + projectItem.quarterlyCouponGenerateRate + projectItem.annualCouponGenerateRate,
                         // 代垫使用比列
                         prepaidUseReferRate: projectItem.prepaidUseReferRate,
                         // 月度返利
                         monthlyCouponGenerateRate: projectItem.monthlyCouponGenerateRate,
                         // 季度返利
                         quarterlyCouponGenerateRate: projectItem.quarterlyCouponGenerateRate,
                         // 年度返利
                         annualCouponGenerateRate: projectItem.annualCouponGenerateRate
                     };
                 });
             });
         // 获取仓库列表
         $.getSelectOptions("warehouselist", URL + "/warehouse/selectWarehouseList", "warehouseId", "warehouseName")
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
         $.getSelectOptions("supplierlist", URL + "/supplier/selectAllSupplier", "supplierCode", "supplierName");
     }

    // 计算接口表格参数
    function calcJsonParams(tableData) {
        if(tableData.length !== 0){
            var productJson = [];
            $.each(tableData,function (index,item) {
                productJson[index] = {
                    productId: item['productId'],
                    productCode: item['productCode'],
                    productName: item['productName'],
                    guidePrice: item['guidePrice'],
                    purchasePrice: item['purchasePrice'],
                    costPrice: item['costPrice'],
                    factoryPrice: item['factoryPrice'],
                    purchaseNumber: item['purchaseNumber'],
                    discountPercent: item['discountPercent'],
                    couponAmount: item['couponAmount'],
                    prepaidAmount: item['prepaidAmount']
                };
            });
            var JSonParams = JSON.stringify(productJson);
            return JSonParams
        }else{
            return false;
        }

    }

    // 使用返利使用代垫的计算调用
    function couponamountPrepaid(URL) {
        var $yhTable = $("#yhtable");
        var productInfo = $yhTable.bootstrapTable('getData');
        var productJson  = calcJsonParams(productInfo);
        var calcPriceParams = {
            // 返利使用金额
            couponAmountUse:$("#couponamount").val() || 0,
            // 代垫使用金额
            prepaidAmountUse:$("#purchase-prepaid").val() || 0,
            productJson:productJson
        };
        $.post(URL + "/purchase/order/calculateProductInfo", calcPriceParams, function (res) {
            if (res.returnCode === 0) {
                // 计算后返回的数据
                var resData = res.data;
                var calculateProductInfoList = resData['calculateProductInfoList'];
                $yhTable.bootstrapTable("load", calculateProductInfoList);
                $("#purchase-should-pay").text(res['data']['purchaseGuideAmount']);
                $("#purchase-order-amount").text(res['data']['purchaseActualPayAmount']);
            }
        }, "json")
    }

    // 添加商品
    function bindFrontEvent(URL) {
        var $purchaseAddBtn = $("#purchase-addbtn");
        var $leftBoxTable = $("#leftbox-table");
        var $searchProduct = $("#searchProduct");
        var $yhMask = $("#yhmask");
        var $yhmaskSubmit = $("#yhmask-submit");
        var purchaseType = $("#purchase-type").val();
        // var $yhTable = $("#yhtable");
        // 采购品种个数
        var $purchaseVariety = $("#purchase-variety");

        // 根据对应的项目添加商品
        $purchaseAddBtn.click(function () {
            var projectId = $("#projectlist").val();
            if (!projectId) {
                return;
            } else {
                $("#yhmask").removeClass("yhmask-hide");
                maskTableRender(URL);
            }
        });

        // 遮罩层货品查询
        $searchProduct.click(function () {
            $leftBoxTable.bootstrapTable("refreshOptions", {pageNumber: 1});
        });

        // 弹出框确认添加商品后
        $yhmaskSubmit.click(function () {
            var alreadyAddTableData = $("#rightbox-table").bootstrapTable('getData');
            var alreadyProLen = alreadyAddTableData.length;
            if (alreadyProLen !== 0) {
                $yhMask.addClass("yhmask-hide");
                $("#couponamount").attr("disabled", false);
                $("#purchase-prepaid").attr("disabled", false);
                renderTable();
                calculatePurchaseType(URL,purchaseType,alreadyAddTableData);
                $purchaseVariety.text(alreadyProLen);
            } else {
                $.renderLayerMessage("请选择商品！");
            }
        });
    }

    // 添加商品和下拉选择框的计算
     function calculatePurchaseType(URL,purchaseType,tableData){
         var productJson  = calcJsonParams(tableData);
         var purchaseParams = {
             purchaseType: purchaseType,
             productJson:productJson
         };
         $.ajax({
             url: URL + "/purchase/order/calculatePurchaseType",
             type: "post",
             data: purchaseParams,
             dataType: "json",
             success: function (res) {
                 if (res.returnCode === 0) {
                     $.renderLoading(false);
                     $("#yhtable").bootstrapTable('load', res['data']['calculateProductInfoList']);
                     $("#purchase-should-pay").text(res['data']['purchaseGuideAmount']);
                     $("#purchase-order-amount").text(res['data']['purchaseActualPayAmount']);
                 } else {
                     setTimeout(function () {
                         $.renderLoading(false);
                     }, 2000)
                 }
             },
             error: function (err) {
                 setTimeout(function () {
                     $.renderLoading(err);
                 }, 2000)
             }
         })
     }

    // 计算和提交新增商品
    function bindBackEvent(URL) {
        var $couponAmont = $("#couponamount");
        var $purchasePrepaid = $("#purchase-prepaid");
        var $purchaseType = $("#purchase-type");
        var $yhTable = $("#yhtable");

        // blur触发计算事件
        $couponAmont.blur(function () {
            // 计算货品价格
            couponamountPrepaid(URL);
        });

        $purchasePrepaid.blur(function () {
            // 计算货品价格
            couponamountPrepaid(URL);
        });

        // 下拉选择框触发计算接口
        $purchaseType.change(function () {
            var purchaseType = $(this).val();
            var productInfo = $yhTable.bootstrapTable('getData');
            renderTable();
            calculatePurchaseType(URL,purchaseType,productInfo);
        });

        // 新增采购单接口
        var $purchaseSubmit = $("#purchase-submit");
        var isAjaxFlag = false;
        $purchaseSubmit.click(function () {
            // 验证表单
            verification.emptyShow = false;
            verification.noEmptySelect("supplierlist", "VerificationEmpty", "输入的字符不能为空");
            verification.noEmptySelect("warehouselist", "VerificationEmpty1", "输入的字符不能为空");
            verification.noEmptySelect("logistic", "VerificationEmpty2", "输入的字符不能为空");
            verification.noEmpty("shopaddress", "VerificationEmpty3", "输入的字符不能为空");
            verification.noEmpty("yhbusiness-date", "VerificationEmpty4", "输入的字符不能为空");
            verification.noEmpty("paidstyle", "VerificationEmpty5", "输入的字符不能为空");
            verification.noEmpty("brandno", "VerificationEmpty8", "输入的字符不能为空");
            verification.noEmpty("orderno", "VerificationEmpty9", "输入的字符不能为空");
            var newAddTableData = $yhTable.bootstrapTable('getData');
            var purchaseNumberFlag = 0;
            $.each(newAddTableData,function (index,item) {
                if(!item['purchaseNumber']){
                    purchaseNumberFlag++
                }
            });

            if (verification.emptyShow || !newAddTableData.length || purchaseNumberFlag > 0) {
                return false;
            }

            // if(isAjaxFlag){
            //     return;
            // }
            //
            // isAjaxFlag = true;

            var purchaseItemInfoJson = [{}];

            $.each(newAddTableData,function (index,item) {
                purchaseItemInfoJson[index] = {
                    productId:item['productId'],
                    productCode:item['productCode'],
                    productName:item['productName'],
                    guidePrice:item['guidePrice'],
                    purchaseNumber:item['purchaseNumber'],
                    purchaseDiscount:item['purchaseDiscount'],
                    couponAmount:item['couponAmount'],
                    prepaidAmount:item['prepaidAmount'],
                    purchasePrice:item['purchasePrice'],
                    costPrice:item['costPrice'],
                    factoryPrice:item['factoryPrice'],
                    couponBasePrice:item['couponBasePrice'],
                    priceDiscount:item['priceDiscount']
                }
            });

            purchaseItemInfoJson = JSON.stringify(purchaseItemInfoJson);

            var addParams = {
                warehouseId: $("#warehouselist").val(),
                supplierCode:$("#supplierlist").val(),
                logisticType:$("#logistic").val(),
                remark:$("#remask").val(),
                couponAmountUse:$("#couponamount").val(),
                prepaidAmountUse:$("#purchase-prepaid").val(),
                supplierReceipt:$("#invoice").val(),
                arrivalDeadline:$("#yhexpiry-date").val(),
                requireArrivalDate:$("#yharrival-date").val(),
                purchaseType:$("#purchase-type").val(),
                businessDate:$("#yhbusiness-date").val(),
                purchaseNumber:$("#purchase-total-number").text(),
                // 供应商订单号
                // brandOrderNo:$("#brandno").val(),
                contractReferenceOrderNo:$("#orderno").val(),
                // 品种
                purchaseCategory:$("#purchase-variety").text(),
                // 订单应付金额
                purchaseGuideAmount:$("#purchase-should-pay").text(),
                purchaseItemInfoJson:purchaseItemInfoJson
            };
            $.renderLoading();
            $.ajax({
                url: URL + "/purchase/order/createPurchaseOrder",
                type: "post",
                data: addParams,
                dataType: "json",
                success: function (res) {
                    if (res.returnCode === 0) {
                        $.renderLoading(false);
                        window.location.href = "./purchase-management.html";
                    } else {
                        setTimeout(function () {
                            $.renderLoading(false);
                        }, 2000)
                    }
                },
                error: function (err) {
                    setTimeout(function () {
                        $.renderLoading(false);
                    }, 2000)
                }
            })
        })
    }

    // 添加商品完毕后渲染商品表格
    function renderTable() {
        var tableData = [{}];
        var URL = $.getfenxiaoURL();
        var purchaseType = $("#purchase-type").val();
        var $yhTable = $("#yhtable");
        $yhTable.bootstrapTable({
            undefinedText: '',
            striped: true,
            data: tableData,
            columns: [{
                filed:'productId',
                title: 'productId',
                visible: false
            },{
                field: 'productCode',
                title: '型号'
            }, {
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'guidePrice',
                title: '采购指导价'
            }, {
                field: 'purchaseNumber',
                title: '采购数量',
                formatter: function (value, row) {
                    if (row['productCode']) {
                        if (!value) {
                            var _value = 1;
                            row.purchaseNumber = _value;
                            return '<input type="text" class="yhpurinput purchase-number" value="' + _value + '"' + '/>';
                        } else {
                            return '<input type="text" class="yhpurinput purchase-number" value="' + value + '"' + '/>';
                        }
                    } else {
                        return ""
                    }
                },
                events: {
                    'blur .purchase-number': function (event, value, row, index) {
                        var _purchaseNumber = $(this).val();
                        $yhTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'purchaseNumber',
                            value: _purchaseNumber
                        });
                        var orderAmount = 0;
                        var purchaseNumberCount= 0;

                        var _tableData = $yhTable.bootstrapTable('getData');
                        calculatePurchaseType(URL,purchaseType,_tableData);
                        $.each(_tableData,function (index,item) {
                            purchaseNumberCount += parseInt(item['purchaseNumber']);
                            orderAmount += parseInt(item['purchaseNumber']) * parseFloat(item['guidePrice']);
                        });
                        console.log(orderAmount);
                        console.log(purchaseNumberCount);
                        $("#purchase-total-number").text(purchaseNumberCount);
                    }
                }
            }, {
                field: 'couponAmount',
                title: '使用返利'
            }, {
                field: 'prepaidAmount',
                title: '使用代垫'
            }, {
                field: 'purchasePrice',
                title: '采购价'
            }, {
                field: 'costPrice',
                title: '成本价'
            }, {
                field: 'factoryPrice',
                title: '进货价'
            }, {
                title: '操作',
                formatter: function (value,row) {
                    if(row['productCode']){
                        return '<a class="purchase-delete">删除</a>'
                    }else{
                        return ""
                    }

                },
                events: {
                    'click .purchase-delete': function (event, value, row) {
                        console.log(row);
                        $yhTable.bootstrapTable('remove', {
                            field: 'productCode',
                            values: [row.productId]
                        })
                    }
                }
            }]
        })
    }
// ===============================================================
    // 遮罩层

    // 遮罩层左边的表格
    function maskTableRender(URL) {
        var $leftboxTable = $("#leftbox-table");
        var $rightboxTable = $("#rightbox-table");
        var $rightTableNumber = $("#right-addnum");
        $leftboxTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/product/getProductInfoList",
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
                    if (tableObj[row.productId]) {
                        return '已添加'
                    } else {
                        return '<a class="pur-addbtn purchaseaddbtn" href="javascript:void(0)">' + '添加' + '</a>'
                    }
                },
                events: {
                    'click .pur-addbtn': function (e, value, row, index) {
                        alreadyDataList.push(row);
                        tableObj[row.productId] = true;
                        if (tableObj[row.productId]) {
                            $(this).removeClass("pur-addbtn").addClass("puralreadyadd");
                            var alreadyaddNum = alreadyDataList.length;
                            $rightTableNumber.text("(" + alreadyaddNum + ")");
                            alreadyTableRender();
                            $rightboxTable.bootstrapTable('load', alreadyDataList);
                            $leftboxTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'maskOpera',
                                value: tableObj[row.productId]
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
                field: 'productCode',
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
                        tableObj[row.productId] = false;
                        // 删除行
                        $rightboxTable.bootstrapTable('remove', {
                            field: 'productCode',
                            values: [row.productId]
                        });
                        // 更新遮罩层坐标的表格
                        $leftboxTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'maskOpera',
                            value: tableObj[row.productId]
                        });
                        var alreadyaddNum = $rightboxTable.bootstrapTable('getData').length;
                        $rightTableNumber.text("(" + alreadyaddNum + ")");
                    }
                }
            }]
        })
    }
});

