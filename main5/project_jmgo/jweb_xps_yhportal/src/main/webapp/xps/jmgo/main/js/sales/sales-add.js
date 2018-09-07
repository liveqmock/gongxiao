/**
 * @authors {len} (http://csshack.org)
 * @date    2018-04-20 11:14:46
 */

$(function () {

    // 全局接口
    var URL = $.getfenxiaoURL();
    salesAddInit();

    // 渲染页面元素
    function renderDateAndSelect() {
        // 项目列表
        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
            .then(function(){
                // 订货客户列表
                var projectId = $("#projectlist").val();
                $.get(URL + "/planSaleItem/getCustomerList", {
                    projectId: projectId
                }, function (res) {
                    var selectData = [{}];
                    if (res.returnCode === 0) {
                        selectData = res.data;
                        $.each(selectData, function (index, item) {
                            selectData[index] = {
                                id: item["customerId"],
                                text: item["customerName"]
                            };
                        });
                        $("#supplierlist").select2({
                            width: 200,
                            data: selectData,
                            minimumResultsForSearch: 6,
                            placeholder: '请选择',
                            language: "zh-CN"
                        })
                    }
                }, "json");
            });
    }

    // 页面元素
    function getPageElem() {
        var $supplierList = $("#supplierlist");
        var $businessDate = $("#yhbusiness-date");
        var $logistic = $("#logistic");
        var $paidStyle = $("#paidstyle");
        var $brandno = $("#brandno");
        var $orderno = $("#orderno");

        var pageElem = {
            $projectList: $("#projectlist"),
            // 添加商品按钮
            $salesAddBtn: $("#sales-addbtn"),
            $supplierList: $supplierList,
            $businessDate: $businessDate,
            $logistic: $logistic,
            $paidStyle: $paidStyle,
            $brandno: $brandno,
            $orderno: $orderno,
            // 备注
            $remask: $("#remark"),
            $salesTable: $("#salestable"),
            // 提交按钮
            $salesSubmitBtn: $("#sales-submit")
        };

        return pageElem;
    }

    // 遮罩层
    function getMaskElem() {
        var maskEle = {
            $salesMask: $("#salesmask"),
            // 查询input
            $proInfo: $("#proinfo"),
            // 关闭按钮
            $maskCloseBtn: $("#mask-close"),
            // 确认按钮
            $salesConfirmBtn: $("#yhmask-submit"),
            // 取消按钮
            $salesCancelBtn: $("#yhmask-cancel"),
            // 待添加表格
            $tobeaddTable: $("#tobeaddtable"),
            // 已选货品数量
            $addedNumber: $("#addednumber"),
            // 已添加表格
            $addedTable: $("#addedtable"),
            closeMask: function () {
                this.$salesMask.addClass("yhmask-hide");
            },
            showMask: function () {
                this.$salesMask.removeClass("yhmask-hide");
            }
        };
        maskEle.$maskCloseBtn.click(function () {
            maskEle.closeMask();
        });
        maskEle.$salesCancelBtn.click(function () {
            maskEle.closeMask();
        });
        return maskEle;
    }

    // 销售初始化
    function salesAddInit() {
        renderDateAndSelect();
        var $pageElem = getPageElem();
        var $maskElem = getMaskElem();
        var amountParams = {};

        function getAmount() {
            // 返利账户余额
            var $couponmoney = $("#couponmoney");
            // 返利使用金额
            var $prepaidamount = $("#prepaidamount");

            var getAmoutParams = {
                projectId: $("#projectlist option:selected").val(),
                distributorId: $("#supplierlist option:selected").val()
            };
            $.get(URL + "/sales/amount", getAmoutParams, function (res) {
                if (res.returnCode == 0) {
                    var resData = res.data;
                    $couponmoney.html(resData.couponAmountDouble);
                    $prepaidamount.html(resData.prepaidAmountDouble);
                    amountParams = {
                        // 返利金额
                        couponAmountDouble: resData.couponAmountDouble,
                        // 代垫金额
                        prepaidAmountDouble: resData.prepaidAmountDouble,
                        // 返利可用比例
                        couponRateDouble: resData.couponRateDouble,
                        // 代垫使用比例
                        prepaidRateDouble: resData.prepaidRateDouble
                    }
                }
            }, "json");
            return amountParams;
        }

        $pageElem.$projectList.change(function () {
            getAmount();
        });

        $("#supplierlist").change(function () {
            getAmount();
        });


        // 添加商品
        $pageElem.$salesAddBtn.click(function () {
            $maskElem.showMask();
            getTobeaddTableData();
        });
        // 提交商品
        $pageElem.$salesSubmitBtn.click(function () {
            submitSalesOrder();
        });

        var statusObj = {};
        var alreadyDataList = [];

        // 待添加表格数据
        function getTobeaddTableData() {
            var tabeaddparams = {
                projectId: $pageElem.$projectList.children("option:selected").val(),
                distributorId: $pageElem.$supplierList.val(),
                productCode: $maskElem.$proInfo.val(),
                businessDateString: $pageElem.$businessDate.val(),
                pageSize: 1,
                pageNum: 1
            };
            $.get(URL + "/sales/planItems", tabeaddparams, function (res) {
                if (res.returnCode == 0) {
                    tobeaddTableRender();
                    var tobeaddLoadData = res.data.list;
                    $maskElem.$tobeaddTable.bootstrapTable('load', tobeaddLoadData);
                }
            }, "json")
        }

        // 确认
        $maskElem.$salesConfirmBtn.click(function () {
            var addedProductData = $maskElem.$addedTable.bootstrapTable("getData");
            // 销售品种
            var $salesVariety = $("#sales-variety");
            if (addedProductData.length !== 0) {
                var salesVariety = addedProductData.length;
                $maskElem.closeMask();
                salesTableRender();
                $salesVariety.html(salesVariety);
                $pageElem.$salesTable.bootstrapTable('load', addedProductData);
                getAmount();
            }
        });

        function salesTableRender() {
            var $salesTable = $("#salestable");
            // 销售总数量
            var $salesTotalnum = $("#sales-totalnum");
            // 销售指导金额
            var $salesGuideamount = $("#sales-guideamount");
            // 销售应付金额
            var $salesShouldpay = $("#sales-shouldpay");
            // 销售金额
            var $salesPrivilege = $("#sales-privilege");
            // 出货金额
            var $salesPay = $("#sales-pay");
            // 代垫使用金额
            var $salesPrepaid = $("#sales-prepaid");
            // 返利使用金额
            var $couponAmount = $("#couponamount");
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
                },

                    {
                        field: 'guidePriceDouble',
                        title: '指导价'
                    }, {
                        field: 'onSaleQuantity',
                        title: '销售数量',
                        formatter: function (value, row, index) {
                            if(!value){
                                value = 0;
                                row.onSaleQuantity = 0;
                                return '<input type="text" class="yhpurinput quantityInput" value="' + value + '"' + '/>';
                            }else{
                                return '<input type="text" class="yhpurinput quantityInput" value="' + value + '"' + '/>';
                            }
                        },
                        events: onSalesQuantityEvents = {
                            'blur .quantityInput': function (event, value, row, index) {
                                var salesQuantity = $(this).val();
                                row.onSaleQuantity = salesQuantity;
                                var addedTableData = $salesTable.bootstrapTable("getData");
                                var calcParams = {
                                    // 返利金额
                                    couponAmountDouble: amountParams.couponAmountDouble,
                                    // 代垫金额
                                    prepaidAmountDouble: amountParams.prepaidAmountDouble,
                                    // 返利可用比例
                                    couponRateDouble: amountParams.couponRateDouble,
                                    // 代垫使用比例
                                    prepaidRateDouble: amountParams.prepaidRateDouble,
                                    itemsJson: JSON.stringify(addedTableData)
                                };
                                $.post(URL+"/sales/addCalculate",calcParams,function (res) {
                                    if(res.returnCode === 0){
                                        var resData = res.data;
                                        $salesTable.bootstrapTable('load',resData.salesPlanItems);
                                        $salesGuideamount.html(resData.salesGuideSubtotal);
                                        $salesPrivilege.html(resData.salesSubtotal);
                                        $salesShouldpay.html(resData.salesShouldReceiveSubtotal);
                                        $salesPay.html(resData.salesExportSubtotal);
                                        $salesPrepaid.val(resData.usedPrepaid);
                                        $couponAmount.val(resData.usedCoupon);
                                    }
                                },"json");
                                //总数量
                                var $numberinputs = $(".quantityInput");
                                var purchaseSum = 0;
                                $.each($numberinputs, function (i, item) {
                                    purchaseSum += parseInt($(item).val());
                                });
                                $salesTotalnum.html(purchaseSum);
                            }
                        }
                    }, {
                        field: 'customerDiscountAmountDouble',
                        title: '客户优惠金额',
                        formatter: function (value, row, index) {
                            if (!value) {
                                value = 0;
                                row.customerDiscountAmountDouble = value;
                                return '<input type="text" class="yhpurinput customerPriceInput" value="' + value + '"' + '/>';
                            } else {
                                return '<input type="text" class="yhpurinput customerPriceInput" value="' + value + '"' + '/>';
                            }
                        },
                        events: customerInputEvents = {
                            'blur .customerPriceInput': function (event, value, row, index) {
                                var customerDisAmountVal = $(this).val();
                                row.customerDiscountAmountDouble = customerDisAmountVal;
                                var addedTableData = $salesTable.bootstrapTable("getData");
                                var calcParams = {
                                    // 返利金额
                                    couponAmountDouble: amountParams.couponAmountDouble,
                                    // 代垫金额
                                    prepaidAmountDouble: amountParams.prepaidAmountDouble,
                                    // 返利可用比例
                                    couponRateDouble: amountParams.couponRateDouble,
                                    // 代垫使用比例
                                    prepaidRateDouble: amountParams.prepaidRateDouble,
                                    itemsJson: JSON.stringify(addedTableData)
                                };
                                $.post(URL+"/sales/addCalculate",calcParams,function (res) {
                                    if(res.returnCode === 0){
                                        var resData = res.data;
                                        $salesTable.bootstrapTable('load',resData.salesPlanItems);
                                        $salesGuideamount.html(resData.salesGuideSubtotal);
                                        $salesPrivilege.html(resData.salesSubtotal);
                                        $salesShouldpay.html(resData.salesShouldReceiveSubtotal);
                                        $salesPay.html(resData.salesExportSubtotal);
                                        $salesPrepaid.val(resData.usedPrepaid);
                                        $couponAmount.val(resData.usedCoupon);
                                    }
                                },"json");
                            }
                        }
                    }, {
                        field: 'totalDiscountDouble',
                        title: '销售折扣点'
                    }, {
                        field: 'wholesalePriceDouble',
                        title: '出货价'
                    },
                    {
                        title: "出货小计",
                        field: 'subtotalDouble',
                        formatter: function (value, row) {
                            if(!value) {
                                return ""
                            }else{
                                return value;
                            }
                        }
                    }, {
                        title: '操作',
                        clickToSelect: false
                    }]
            })
        }

        // 待选商品
        function tobeaddTableRender() {
            var $tobeaddTable = $("#tobeaddtable");
            var $addedTable = $("#addedtable");
            var tobeaddTableData = [{}];
            $tobeaddTable.bootstrapTable({
                undefinedText: "",
                striped: true,
                data: tobeaddTableData,
                pagination: true,
                pageSize: 5,
                pageList: [3, 6],
                columns: [{
                    field: 'productCode',
                    title: '货品编码'
                }, {
                    field: 'productName',
                    title: '货品名称',
                    width: '35%'
                }, {
                    field: 'guidePriceDouble',
                    title: '指导价'
                }, {
                    title: '操作',
                    clickToSelect: false,
                    formatter: function (value, row) {
                        if (statusObj[row.productCode]) {
                            return '已添加'
                        } else {
                            return '<a class="pur-addbtn purchaseaddbtn" href="javascript:void(0)">' + '添加' + '</a>'
                        }
                    },
                    events: tobeaddEvent = {
                        'click .pur-addbtn': function (e, value, row) {
                            alreadyDataList.push(row);
                            statusObj[row.productCode] = true;
                            if (statusObj[row.productCode]) {
                                $(this).removeClass("pur-addbtn").addClass("puralreadyadd");
                                var addedLoadData = alreadyDataList;
                                var alreadyaddNum = addedLoadData.length;
                                $maskElem.$addedNumber.text("(" + alreadyaddNum + ")");
                                addedTableRender();
                                $addedTable.bootstrapTable('load', addedLoadData);
                                getTobeaddTableData();
                            }
                        }
                    }
                }]
            })
        }

        // 已选择商品
        function addedTableRender() {
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
                    field: 'productCode',
                    title: '货品编码'
                }, {
                    field: 'productName',
                    title: '货品名称',
                    width: '35%'
                }, {
                    field: 'guidePriceDouble',
                    title: '指导价'
                }, {
                    title: '操作',
                    clickToSelect: false,
                    formatter: function () {
                        return '<a class="alreadyadd-delete btn btn-danger" href="javascript:void(0)">' + '删除' + '</a>'
                    },
                    'click .alreadyadd-delete': function (event, value, row) {
                        $addedTable.bootstrapTable('remove', {
                            field: 'productCode',
                            values: [row.productCode]
                        });
                        statusObj[row.productCode] = false;
                        var alreadyaddNum = addedLoadData.length;
                        $maskElem.$addedNumber.text("(" + alreadyaddNum + ")");
                        getTobeaddTableData();
                    }
                }]
            })
        }

        // 提交新增销售单
        function submitSalesOrder() {
            var salesOrderData = $pageElem.$salesTable.bootstrapTable("getData");
            var itemsInfo = [{}];
            $.each(salesOrderData, function (index, salesItem) {
                itemsInfo[index] = {
                    productCode: salesItem.productCode,
                    totalQuantity: salesItem.onSaleQuantity,
                    customerDiscountAmountDouble: salesItem.customerDiscountAmountDouble,
                    totalOrderAmountDouble: salesItem.subtotalDouble
                }
            });
            itemsInfo = JSON.stringify(itemsInfo);
            var submitParams = {
                salesOrderNo: $("#brandno").val(),
                salesContactNo: $("#orderno").val(),
                projectId: $pageElem.$projectList.children("option:selected").val(),
                projectName: $pageElem.$projectList.children("option:selected").text(),
                distributorId: $pageElem.$supplierList.children("option:selected").val(),
                distributorName: $pageElem.$supplierList.children("option:selected").text(),
                shippingAddress: "福华一路88号中心商务大厦2001",
                provinceId: 2,
                provinceName: "广东省",
                cityId: 2,
                cityName: "深圳市",
                districtId: 3,
                districtName: "福田区",
                recipientName: "赖先生",
                recipientMobile: 15011114700,
                couponAmount: $("#couponamount").val() || 0,
                prepaidAmount: $("#prepaidamount").val() || 0,
                // 销售应收金额
                totalOrderAmount: $("#sales-shouldpay").html(),
                // 出货金额
                totalCash: $("#sales-pay").html(),
                createTimeString: $pageElem.$businessDate.val(),
                itemsInfo: itemsInfo
            };
            $.post(URL + "/sales/createOrder", submitParams, function (res) {
                if (res.returnCode === 0) {
                    layer.msg("提交成功！");
                    window.location.href = './sales-management.html';
                }
            },"json")
        }
    }


});