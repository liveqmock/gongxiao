/**
 * @authors {Pinocchio} (http://csshack.org)
 * @date    2018-04-08 09:33:57
 * @version $Id$
 */
$(function () {

    var URL = $.getcustomerURL();

    init();

    // 页面DOM
    function getPageEle() {
        // 收货地址
        var $submitOrderAddress = $("#submitorder-address");
        // 可用金额
        var $availAmount = $("#availamount");
        // 使用额度
        var $cashAmount = $("#cashamount");
        // 折扣信息
        var $discountInfo = $("#discount-info");
        // 提交订单按钮
        var $submitOrderBtn = $("#submitorderbtn");
        // 预存账户扣减
        var $accountDeduction = $("#account-deduction");
        // 代垫优惠扣减
        var $couponAmount = $("#couponamount");
        // 返利优惠扣减
        var $prepaidAmount = $("#prepaidamount");
        // 提交确认表格
        var $submitOrderTable = $("#submitorder-table");

        var pageEle = {
            $submitOrderTable: $submitOrderTable,
            $submitOrderAddress: $submitOrderAddress,
            $cashAmount: $cashAmount,
            $discountInfo: $discountInfo,
            $submitOrderBtn: $submitOrderBtn,
            $availAmount: $availAmount,
            $couponAmount: $couponAmount,
            $prepaidAmount: $prepaidAmount,
            $accountDeduction: $accountDeduction
        };
        return pageEle;
    }

    // 地址信息
    function getAddressInfo() {
        var $addressSelected = $(".address-selected");
        var addressInfo = {
            addressSelected: $addressSelected,
            // 收件人
            recipientName: $addressSelected.children("h4").html(),
            // 收货地址
            shippingAddress: $addressSelected.children("h5").children().val(),
            // 收件人手机号
            recipientMobile: $addressSelected.children("p").children().val()
        };
        return addressInfo
    }

    function getDataFromPrev() {
        var $pageEleInfo = getPageEle();
        var cashAmount = $.getInfoFromUrl("cashAmount");
        var couponAmount = $.getInfoFromUrl("couponAmount");
        var prepaidAmount = $.getInfoFromUrl("prepaidAmount");
        var submitLoadData = $.getInfoFromUrl("orderData");
        submitLoadData = JSON.parse(decodeURIComponent(submitLoadData));
        var amountInfo = {
            couponAmount: couponAmount,
            prepaidAmount: prepaidAmount,
            submitLoadData: submitLoadData
        };
        return amountInfo;
    }

    function init() {

        var amountInfo = getDataFromPrev();
        var $pageEleInfo = getPageEle();
        var $shouldPayTotalAmount = $("#shouldpaytotalamount");
        var $payAmount = $("#payamount");

        $.getSelectPreferAddress(URL + "/address/selectValidAddress", "submitorder-address")
            .then(function () {
                $pageEleInfo.$submitOrderAddress.children("div:first-child").addClass("address-selected");
                $pageEleInfo.$submitOrderAddress.find(".shipping-address").click(function () {
                    $(this).addClass("address-selected").siblings().removeClass("address-selected");
                });
                //查询现金余额
                $.get(URL + "/order/getAvailableBalance",function(res){
                    $("#availamount").html(res.data.cashAmount);
                },"json");
            });


        renderTable();
        var submitLoadData = amountInfo.submitLoadData;
        $pageEleInfo.$submitOrderTable.bootstrapTable('load', submitLoadData);
        // 付款金额
        var payAmount = ($shouldPayTotalAmount.val() - amountInfo.couponAmount - amountInfo.prepaidAmount).toFixed(2);
        $payAmount.html(payAmount);
        // 预存账户扣减
        $pageEleInfo.$cashAmount.on("input propertychange", function () {
            var cashAmountChangedValue = $(this).val();
                $pageEleInfo.$accountDeduction.html(cashAmountChangedValue);
                var outOfPacketAmount = ($shouldPayTotalAmount.val() - cashAmountChangedValue).toFixed(2);
                $payAmount.html(outOfPacketAmount);
        });


        $.getSubmitOrderAddress(URL + "/address/selectValidAddress")
            .then(function (res) {
                var addressParams = {};
                var userAddressData = res.data;
                // 提交订单
                var isAjaxFlag = false;
                $pageEleInfo.$submitOrderBtn.click(function () {
                    var $arrAddressDivs = $("#submitorder-address").children(".shipping-address");
                    $arrAddressDivs.each(function (index) {
                       if($(this).hasClass("address-selected")){
                           addressParams = {
                               shippingAddress: userAddressData[index].streetAddress,
                               provinceId: userAddressData[index].provinceId,
                               provinceName: userAddressData[index].provinceName,
                               cityId: userAddressData[index].cityId,
                               cityName: userAddressData[index].cityName,
                               districtId: userAddressData[index].districtId,
                               districtName: userAddressData[index].districtName,
                               recipientName: userAddressData[index].receiver,
                               recipientMobile: userAddressData[index].phoneNumber
                           }
                       }
                    });
                    var addressInfo = getAddressInfo();
                    var orderListData = $pageEleInfo.$submitOrderTable.bootstrapTable('getData');
                    var submitOrderListData = [{}];
                    $.each(orderListData, function (index, orderItem) {
                        if(Number(orderItem["purchaseNumber"]) == 0){
                            return false;
                        }
                        submitOrderListData[index] = {
                            productId: orderItem.productId,
                            productCode: orderItem.productCode,
                            totalQuantity: orderItem.purchaseNumber,
                            totalOrderAmountDouble: orderItem.discountAmount
                        }
                    });
                    submitOrderListData = JSON.stringify(submitOrderListData);
                    var submitOrderParams = {
                        currencyId: "CNY",
                        currencyCode: "CNY",
                        cashAmount: $pageEleInfo.$cashAmount.val(),// 预存账户使用金额
                        couponAmount: amountInfo.couponAmount, // 可用返利金额
                        prepaidAmount: amountInfo.prepaidAmount,
                        totalOrderAmount: $shouldPayTotalAmount.val(), // 订单总金额
                        totalPaidAmount: $payAmount.html(), // 实付金额
                        salesOrderItem: submitOrderListData
                    };
                    $.extend(submitOrderParams,addressParams);

                    if(isAjaxFlag){
                        return;
                    }
                    isAjaxFlag = true;
                    $.ajax({
                        type: "POST",
                        url: URL + "/order/saveOrder",
                        cache: false,
                        dataType: 'json',
                        data: submitOrderParams,
                        async: false,
                        beforeSend: function () {
                            $pageEleInfo.$submitOrderBtn.attr("disabled", true);
                        },
                        complete: function () {
                            $pageEleInfo.$submitOrderBtn.attr("disabled",false);
                        },
                        success: function (res) {
                            if (res.returnCode === 0) {
                                window.location.href = "./orderlistmanagement.html"
                            } else {
                                isAjaxFlag = false;
                                $.renderLayer(res.message);
                            }
                        },
                        error: function (err) {
                            isAjaxFlag = false;
                            $.renderLayer(err);
                        }
                    })
                })
            });

    }

    function renderTable() {
        var $submitOrderTable = $("#submitorder-table");
        var $shouldPayTotalAmount = $("#shouldpaytotalamount");
        var submitTableData = [{}];
        $submitOrderTable.bootstrapTable({
            undefinedText: '',
            clickToSelect: true,
            striped: true,
            showFooter: true,
            data: submitTableData,
            columns: [{
                field: 'productName',
                title: '商品名称',
                align: 'center',
                halign: 'center'
            }, {
                field: 'productCode',
                title: '型号',
                align: 'center',
                halign: 'center'
            }, {
                field: 'buyingPrice',
                title: '进货价',
                align: 'center',
                halign: 'center'
            }, {
                field: 'purchaseNumber',
                title: '数量',
                align: 'center',
                halign: 'center',
                formatter: function (value, row, index, field) {
                    if (!value) {
                        value = 0;
                        return '<input type="text" value="' + value + '">';
                    } else {
                        return '<input type="text" value="' + value + '">';
                    }
                }
            }, {
                field: 'discountAmount',
                title: '小计',
                align: 'center',
                halign: 'center',
                footerFormatter: function (row) {
                    if (row.length == 0) {
                        return "合计： CNY " + 0;
                    } else {
                        var count = 0;
                        for (var i in row) {
                            count += Number(row[i].discountAmount);
                        }
                        $shouldPayTotalAmount.val(count);
                        return "合计： CNY " + count;
                    }
                }
            }]
        })
    }
});