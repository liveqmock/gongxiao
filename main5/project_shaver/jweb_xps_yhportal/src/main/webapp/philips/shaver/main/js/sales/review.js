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
        // 表格底部信息的text信息
        var $availAmount = $("#availamount").find("b");


        var _cashAmount = 0;
        var _shipmentAmount = 0;


        //销售单详细信息
        $.get(URL + "/sales/getOrderInfo", {
            salesOrderNo: orderNumber
        }, function (res) {
            if (res.returnCode === 0) {
                var orderData = res.data;
                renderTable();
                renderLogTable();
                // 销售单商品列表
                var salesTableLoadData = res.data.salesOrderItemInfos;
                // 操作列表数据
                var logLoadData = res.data.traceLog;
                $reviewTable.bootstrapTable('load', salesTableLoadData);
                $reviewLog.bootstrapTable('load', logLoadData);

                // 订单信息
                // 订单号 销售合同号  当前状态 下单时间 支付时间
                $("#ordernumber").html(orderData.salesOrderNo);
                $("#sales-contract").val(orderData.salesContactNo);
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

                // 品牌商支持金额 越海代垫金额 客户返利余额 客户代垫余额
                var amountInfo = [orderData.supplierDiscountAmountDouble,orderData.yhDiscountAmountDouble,orderData.distributorCouponAmountDouble,orderData.distributorPrepaidAmountDouble];

                // 金额信息
                $availAmount.each(function (index) {
                    $(this).html(amountInfo[index]);
                });

                // 出货总金额 返利账户使用金额 代垫账户使用金额 预存账户使用金额 应收金额
                var $shipmentAmount = $("#shipmentamount");
                var $couponAmount = $("#couponamount");
                var $prepaidAmount = $("#prepaidamount");
                var shipmentAmount = Number(orderData.totalOrderAmountDouble);
                var cashAmount = Number(orderData.prestoredAmountDouble);
                // 出货总金额
                _shipmentAmount = shipmentAmount;
                // 预存账户金额
                _cashAmount = cashAmount;

                $shipmentAmount.val(shipmentAmount);
                $couponAmount.val((0).toFixed(2));
                $prepaidAmount.val((0).toFixed(2));
                $("#cashamount").val(cashAmount);
                $("#amountmoney").val(orderData.cashAmountDouble);


                // 时间轴时间
                var arrTime = [orderData.createTime, orderData.orderCheckTime, orderData.paidTime, orderData.informWarehouseTime, orderData.warehouseHandleTime, orderData.sendCarTime, orderData.outBoundTime, orderData.sighTime];
                for (var j = 0; j < arrTime.length; j++) {
                    arrTime[j] = checkDate(arrTime[j]);
                }
                // 根据状态值来确定时间线
                var salesOrderStatus = orderData.salesOrderStatus;
                //var salesOrderStatus = 3;
                if (salesOrderStatus === 0) {
                    // 待审核(订单新建)
                    var el = ".line-pro" + salesOrderStatus;
                    $(el).addClass("line-startend").nextAll().children("p").hide();
                    var infoTime = arrTime[0];
                    $(el).children("p").html(infoTime);
                } else if (salesOrderStatus === 9) {
                    var html = "";
                    $(".line-pro").children("p").html(html);
                } else {
                    var el = ".line-pro" + salesOrderStatus;
                    orderStatus(el);
                    for (var i = 0; i <= salesOrderStatus; i++) {
                        (function (j) {
                            var el2 = ".line-pro" + j;
                            $(el2).children("p").html(arrTime[j])
                        }(i));
                    }
                }


            }
        }, "json");



        $("#couponamount").on("input propertychange",function () {
            var inputValue = Number($(this).val());
            // 代垫账户使用金额
            var prepaidAmount = Number($("#prepaidamount").val());
            // 预存账户可用金额
            var cashAmount = Number($("#cashamount").val());
            var surplusValue = (Number(_shipmentAmount) - prepaidAmount - cashAmount - inputValue).toFixed(2);
            $("#amountmoney").val(surplusValue);
        });

        $("#prepaidamount").on("input propertychange",function () {
            var inputValue = Number($(this).val());
            // 代垫账户使用金额
            var prepaidAmount = Number($("#couponamount").val());
            // 预存账户可用金额
            var cashAmount = Number($("#cashamount").val());
            var surplusValue = (Number(_shipmentAmount) - prepaidAmount - cashAmount - inputValue).toFixed(2);
            $("#amountmoney").val(surplusValue);
        });

        //审核驳回跳转
        function reviewSkip() {
            //审核
            var $reviewBtn = $("#review-btn");
            //驳回
            var $reviewRebut = $("#review-rebut");

            var isAjaxReviewFlag = false;
            var isAjaxRebutFlag = false;

            //审核
            $reviewBtn.click(function () {
                // 返利账户使用金额
                var coupontAmount = Number($("#couponamount").val());
                // 代垫账户使用金额
                var prepaidAmount = Number($("#prepaidamount").val());
                // 预存账户可用金额
                var cashAmount = Number($("#cashamount").text());
                // 出货总金额
                var shipmentAmount = Number($("#shipmentamount").val());
                // 客户返利金额
                var customerCoupontAmount = Number($("#availamount li:nth-child(2)").find("span").html());
                // 客户代垫金额
                var customerPrepaidAmount = Number($("#availamount li:last-child").find("span").html());
                var reviewParams = {
                    couponAmountDouble: coupontAmount,
                    prepaidAmountDouble: prepaidAmount,
                    cashAmountDouble: cashAmount,
                    salesOrderNo: orderNumber,
                    // 销售合同号
                    salesContractNo:$("#sales-contract").val()
                };


                if(coupontAmount - customerCoupontAmount > 0 || prepaidAmount - customerPrepaidAmount > 0 || (coupontAmount + prepaidAmount + cashAmount)-shipmentAmount > 0){
                    $.renderLayerMessage("请重新填写金额")
                }else{
                    if(isAjaxReviewFlag){
                        return;
                    }
                    isAjaxReviewFlag = true;
                    $.ajax({
                        type: "POST",
                        url: URL + "/sales/check",
                        cache: false,
                        dataType: 'json',
                        data: reviewParams,
                        async: false,
                        beforeSend: function () {
                            $.renderLoading(true);
                            $reviewBtn.attr("disabled", true);
                        },
                        success: function (res) {
                            if (res.returnCode === 0) {
                                window.location.href = "./sales-management.html"
                            }else{
                                setTimeout(function(){
                                    $.renderLoading(false);
                                },2000);
                                isAjaxReviewFlag = false;
                                $.renderLayerMessage(res.message);
                            }
                        },
                        error: function (res) {
                            $.renderLoading(false);
                            $.renderLayerMessage(res);
                        },
                        complete: function () {
                            $.renderLoading(false);
                            $reviewBtn.attr("disabled", false);
                        }
                    });
                }
            });

            //驳回
            $reviewRebut.click(function () {
                var reviewParams = {
                    salesOrderNo: orderNumber
                };
                if(isAjaxRebutFlag){
                    return;
                }
                isAjaxRebutFlag = true;
                $.ajax({
                    type: "POST",
                    url: URL + "/sales/reject",
                    cache: false,
                    dataType: 'json',
                    data: reviewParams,
                    async: false,
                    beforeSend: function () {
                        $.renderLoading(true);
                        $reviewRebut.attr("disabled", true);
                    },
                    success: function (res) {
                        if (res.returnCode === 0) {
                            window.location.href = "./sales-management.html"
                        }else{
                            setTimeout(function(){
                                $.renderLoading(false);
                            },2000);
                            isAjaxRebutFlag = false;
                            $.renderLayerMessage(res.message);
                        }
                    },
                    error: function (res) {
                        isAjaxRebutFlag = false;
                        $.renderLoading(false);
                        $.renderLayerMessage(res);
                    },
                    complete: function () {
                        $.renderLoading(false);
                        $reviewRebut.attr("disabled", false);
                    }
                });
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
        var shipmentamount = Number($("#shipmentamount").val());
        // 返利账户使用金额
        var useamount = Number($("#useamount").val()) || 0;
        // 代垫账户可用金额
        var availablemount = Number($("#availablemount").val()) || 0;
        // 应收金额
        var amountmoney = shipmentamount - useamount - availablemount;
        $("#amountmoney").val(amountmoney);
        $("#" + el).on("input propertychange", function () {
            // 出货预售金额
            var shipmentamount = Number($("#shipmentamount").val());
            // 返利账户使用金额
            var useamount = Number($("#useamount").val()) || 0;
            // 代垫账户可用金额
            var availablemount = Number($("#availablemount").val()) || 0;
            var amountmoney = shipmentamount - useamount - availablemount;
            $("#amountmoney").val(amountmoney);
        });
    }

    reviewCalcAmount("useamount");
    reviewCalcAmount("availablemount");

    //订单进行中
    function orderStatus(el) {
        $(el).addClass("line-cur").prev().addClass("line-prev").end().prevAll().addClass("line-done");
        $(el).nextAll().children("p").hide();
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
                title: '指导价'
            }, {
                field: 'supplierDiscountPercentDouble',
                title: '品牌商支持折扣'
            }, {
                field: 'supplierDiscountAmountDouble',
                title: '品牌商单价支持'
            }, {
                field: 'yhDiscountPercentDouble',
                title: 'YH销售折扣支持'
            }, {
                field: 'yhDiscountAmountDouble',
                title: 'YH支持金'
            }, {
                field: 'wholesalePriceDouble',
                title: '出货价'
            }, {
                field: 'totalOrderAmountDouble',
                title: '出货小计'
            }, {
                title: '操作'
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