$(function () {
    salesDetailInit();

    function salesDetailInit() {

        // 订单信息表格
        var $reviewTable = $("#reviewtable");
        // 操作表格
        var $reviewLog = $("#reviewlog");
        // 订单号
        var orderNumber = $.getUrlParam("salesOrderNo");

        var URL = $.getfenxiaoURL();
        // 客户收货信息
        var $customerInfo = $("#customer-info");


        //销售单详细信息
        $.get(URL + "/sales/orderInfo", {
            salesOrderNo: orderNumber
        }, function (res) {
            if (res.returnCode === 0) {
                var orderData = res.data;
                var orderInfoma = orderInfo();
                renderTable();
                renderLogTable();
                // 销售单商品列表
                var salesTableLoadData = res.data.salesOrderItemInfos;
                // 操作列表数据
                var logLoadData = res.data.traceLog;
                $reviewTable.bootstrapTable('load', salesTableLoadData);
                $reviewLog.bootstrapTable('load', logLoadData);

                // 订单信息
                //订单号 销售合同号  当前状态 下单时间 支付时间
                $("#ordernumber").html(orderData.salesOrderNo);
                $("#salescontactno").html(orderData.salesContactNo);
                $("#orderstatus").html(orderData['salesOrderStatusStr']);
                $("#createtime").html($.transformTime(orderData.createTime));
                $("#paidtime").html($.transformTime(orderData.paidTime));

                // 客户收货信息
                // 省市区
                var customerAddress = orderData.provinceName + orderData.cityName + orderData.districtName;
                // 收货人，手机，收货公司，地址，详细地址
                var costomerInfoList = [orderData.recipientName, orderData.recipientMobile, orderData.recipientCompany, customerAddress, orderData.shippingAddress];
                var $costomerInfoList = $customerInfo.find("span");
                $costomerInfoList.each(function (index) {
                    $(this).html(costomerInfoList[index]);
                });


                // 供应商代垫金额 YH代垫金额  出货预收金额 返利账户使用金额 返利代垫使用金额 应收金额
                // 出货总金额 返利账户使用金额 代垫账户使用金额 预存账户使用金额 应收金额
                var $shipmentAmount = $("#shipmentamount");
                var $couponAmount = $("#couponamount");
                var $prepaidAmount = $("#prepaidamount");
                // 出货总金额
                var shipmentAmount = Number(orderData.totalOrderAmountDouble);
                var cashAmount = Number(orderData['prestoredAmountDouble']);

                $shipmentAmount.html(accounting.formatMoney(shipmentAmount, "", 2, ",", "."));
                $couponAmount.val(orderData.couponAmountDouble);
                $prepaidAmount.val(orderData.prepaidAmountDouble);
                $("#cashamount").val(cashAmount);
                $("#amountmoney").html(orderData.cashAmountDouble);

                $("#supplieramount").html(accounting.formatMoney(orderData['supplierDiscountAmountDouble'], "", 2, ",", "."));
                $("#yhamount").html(accounting.formatMoney(orderData['yhDiscountAmountDouble'], "", 2, ",", "."));


                // 时间轴时间
                var arrTime = [orderData.createTime, orderData.orderCheckTime, orderData.paidTime, orderData.informWarehouseTime,orderData.outBoundTime, orderData.sighTime];
                for (var j = 0; j < arrTime.length; j++) {
                    arrTime[j] = checkDate(arrTime[j]);
                }
                // 根据状态值来确定时间线
                var salesOrderStatus = orderData.salesOrderStatus;
                if (salesOrderStatus === 1) {
                    // 待审核(订单新建)
                    var el = ".line-pro" + (salesOrderStatus - 1);
                    $(el).addClass("line-startend").nextAll().children("p").hide();
                    var infoTime = arrTime[0];
                    $(el).children("p").html(infoTime);
                } else if(salesOrderStatus < 7){
                    var el = ".line-pro" + (salesOrderStatus - 1);
                    orderStatus(el);
                    for (var i = 0; i <= salesOrderStatus; i++) {
                        (function (j) {
                            var el2 = ".line-pro" + j;
                            $(el2).children("p").html(arrTime[j])
                        }(i));
                    }
                }else if (salesOrderStatus === 9) {
                    var html = "";
                    $(".line-pro").children("p").html(html);
                }
            }
        }, "json");


        //审核驳回跳转
        function reviewSkip() {
            //审核
            var $reviewBtn = $("#review-btn");
            //驳回
            var $reviewRebut = $("#review-rebut");

            function skipAjax(url, opt, skipurl) {
                $.get(url, opt, function (res) {
                    if (res.returnCode === 0) {
                        window.location.href = skipurl;
                    }
                }, "json");
            }

            //审核
            $reviewBtn.click(function () {
                var obj = {
                    couponAmountDouble: $("#coupontamount").val(),
                    prepaidAmountDouble: $("#prepaidamount").val(),
                    cashAmountDouble: $("#cashamount").text(),
                    salesOrderNo: orderNumber
                };
                var skipurl = "./sales-management.html";
                skipAjax(URL + "/sales/check", obj, skipurl);
            });

            //驳回
            $reviewRebut.click(function () {
                var obj = {
                    salesOrderNo: orderNumber
                };
                skipAjax(URL + "/sales/reject", obj, "./sales-management.html");
            })
        }

        reviewSkip();
    }


    //转换成两位数
    function transformDoubble(num, length) {
        return ('' + num).length < length ? ((new Array(length + 1)).join('0') + num).slice(-length) : '' + num;
    }

    //时间轴里的时间转换
    function checkDate(date) {
        if (date) {
            var tempDate = new Date(date);
            var lineDate1 = tempDate.getFullYear() + "-" + transformDoubble((tempDate.getMonth() + 1), 2) + "-" + transformDoubble(tempDate.getDate(), 2);
            var lineDate2 = transformDoubble(tempDate.getHours(), 2) + ":" + transformDoubble(tempDate.getMinutes(), 2) + ":" + transformDoubble(tempDate.getSeconds(), 2);
            return lineDate1 + "<br/>" + lineDate2;
        } else {
            return "";
        }
    }

    $(".line-pro").removeClass("line-done line-prev line-cur");


    function reviewCalcAmount(el) {
        // 出货预售金额
        var shipmentamount = Number($("#shipmentamount").html());
        // 返利账户使用金额
        var useamount = Number($("#useamount").val()) || 0;
        // 代垫账户可用金额
        var availablemount = Number($("#availablemount").val()) || 0;
        // 应收金额
        var amountmoney = shipmentamount - useamount - availablemount;
        $("#amountmoney").html(amountmoney);
        $("#" + el).on("input propertychange", function () {
            // 出货预售金额
            var shipmentamount = Number($("#shipmentamount").html());
            // 返利账户使用金额
            var useamount = Number($("#useamount").val()) || 0;
            // 代垫账户可用金额
            var availablemount = Number($("#availablemount").val()) || 0;
            var amountmoney = shipmentamount - useamount - availablemount;
            $("#amountmoney").html(amountmoney);
        });
    }

    reviewCalcAmount("useamount");
    reviewCalcAmount("availablemount");

    //订单进行中
    function orderStatus(el) {
        $(el).addClass("line-cur").prev().addClass("line-prev").end().prevAll().addClass("line-done");
        $(el).nextAll().children("p").hide();
    }


    // 销售单详细信息
    function orderInfo() {
        // 订单号
        var $orderNumber = $("#ordernumber");
        // 销售合同号
        var $salesContactNo = $("#salescontactno");
        // 当前状态
        var $orderStatus = $("#orderstatus");
        // 下单时间
        var $createTime = $("#createtime");
        // 支付时间
        var $paidTime = $("#paidtime");
        return {
            orderNumber: $orderNumber,
            salesContactNo: $salesContactNo,
            orderStatus: $orderStatus,
            createTime: $createTime,
            paidTime: $paidTime
        };


    }


    //渲染表格数据
    function renderTable() {
        var $reviewTable = $("#reviewtable");
        var tableData = [{}];
        $reviewTable.bootstrapTable({
            undefinedText: '',
            clickToSelect: true,
            striped: true,
            data: tableData,
            columns: [{
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'productCode',
                title: '型号'
            }, {
                field: 'totalQuantity',
                title: '数量'
            }, {
                field: 'currencyName',
                title: '币种'
            }, {
                field: 'guidePriceDouble',
                title: '指导价',
                formatter: function (value) {
                    return accounting.formatMoney(value, "", 2, ",", ".")
                }
            }, {
                field: 'supplierDiscountPercentDouble',
                title: '品牌商支持折扣点'
            }, {
                field: 'supplierDiscountAmountDouble',
                title: '品牌商单价支持',
                formatter: function (value) {
                    return accounting.formatMoney(value, "", 2, ",", ".")
                }
            }, {
                field: 'yhDiscountPercentDouble',
                title: 'YH代垫',
                formatter: function (value) {
                    return accounting.formatMoney(value, "", 2, ",", ".")
                }
            }, {
                field: 'yhDiscountAmountDouble',
                title: 'Yh单价代垫',
                formatter: function (value) {
                    return accounting.formatMoney(value, "", 2, ",", ".")
                }
            }, {
                field: 'wholesalePriceDouble',
                title: '出货价',
                formatter: function (value) {
                    return accounting.formatMoney(value, "", 2, ",", ".")
                }
            }, {
                field: 'totalOrderAmountDouble',
                title: '出货小计',
                formatter: function (value) {
                    return accounting.formatMoney(value, "", 2, ",", ".")
                }
            }]
        })
    }

    //操作日志
    function renderLogTable() {
        var $reviewLog = $("#reviewlog");
        var logData = [{}];
        $reviewLog.bootstrapTable({
            pageSize: 25,
            pageList: [60, 100, 200],
            undefinedText: '',
            locale: 'zh-CN',
            striped: true,
            data: logData,
            columns: [{
                field: 'opName',
                title: '操作人'
            }, {
                field: 'content',
                title: '操作功能'
            }, {
                field: 'opTime',
                title: '操作时间',
                formatter: function (value) {
                    return $.transformTime(value,"seconds");
                }
            }]
        })
    }
});