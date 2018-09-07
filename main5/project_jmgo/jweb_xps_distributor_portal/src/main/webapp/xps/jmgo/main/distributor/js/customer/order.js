/**
 * @authors {Pinocchio} (http://csshack.org)
 * @date    2018-04-08 09:33:57
 * @version $Id$
 */

$(function () {
    // 全局接口
    var URL = $.getcustomerURL();
    orderInit();
    function orderInit() {
        var $orderEle = orderPageEle();
        var $selectedProNum = $("#selectedpronum");
        getProductList();
        $orderEle.queryBtn.click(function () {
            getProductList();
        });
        var arrProductData = [];
        // 添加商品
        $orderEle.orderProList.on('click', 'input', function () {
            $(this).attr("disabled","disabled");
            var productSelfInfo = $(this).attr("id");
            var productSelfId = productSelfInfo.split("-");
            var addProductId = productSelfId[productSelfId.length - 1];
            var addProductParams = {
                productId: addProductId
            };

            $.get(URL + "/order/getProductInfo", addProductParams, function (res) {
                if (res.returnCode === 0) {
                    renderTable();
                    var addedProductData = res.data;
                    arrProductData.push(addedProductData);
                    $orderEle.orderTable.bootstrapTable('load', arrProductData);
                    var proNumber = $orderEle.orderTable.bootstrapTable('getData').length;
                    $selectedProNum.html(proNumber);
                } else {
                    layer.msg(res.message);
                }
            }, "json");
        });


            var orderFlag = {};
            // 下单
            $orderEle.orderBtn.click(function () {
                var orderListData = $orderEle.orderTable.bootstrapTable("getData");
                if(orderListData.length == 0){
                    layer.msg("请先添加商品!");
                    return false;
                }
                $.each(orderListData,function (index,orderItem) {
                    if(orderItem['purchaseNumber'] == 0){
                        orderFlag[index] = true;
                    }else{
                        orderFlag[index] = false;
                    }
                });
                var submitOrderListData = [{}];
                $.each(orderListData,function (index,orderItem) {
                    submitOrderListData[index] = {
                        productName: orderItem.productName,
                        productCode: orderItem.productCode,
                        buyingPrice: orderItem.buyingPrice,
                        purchaseNumber: orderItem.purchaseNumber,
                        discountAmount: orderItem.discountAmount
                    }
                });
                submitOrderListData = JSON.stringify(submitOrderListData);
                for(var orderFlagKey in orderFlag){
                    if(orderFlag[orderFlagKey]){
                        layer.msg("采购数量不能为0")
                        return;
                    }
                }
                // window.location.href = "./submitorder.html?cashAmount="+cashAmount+"&couponAmount="+couponAmount+"&prepaidAmount="+prepaidAmount+"&orderData="+encodeURIComponent(submitOrderListData);
                window.location.href = "./submitorder.html?orderData="+encodeURIComponent(submitOrderListData);
            })
    }

    // 渲染购买商品信息数据表格
    function renderTable() {
        var $orderEle = orderPageEle();
        var orderTableData = [{}];
        var $orderTable = $("#order-table");
        $orderTable.bootstrapTable({
            undefinedText: '',
            clickToSelect: true,
            striped: true,
            showFooter: true,
            falign: 'right',
            data: orderTableData,
            columns: [{
                title: '序号',
                formatter: function (value,row,index) {
                    return index + 1;
                }
            },{
                field: 'productId',
                visible: false
            }, {
                field: 'productCode',
                title: '型号'
            }, {
                field: 'productName',
                title: '商品名称'
            }, {
                field: 'purchaseNumber',
                title: '采购数量',
                clickToSelect: false,
                class: 'purchasenum-orderinput clearfix',
                formatter: function (value,row) {
                    if (!value) {
                        value = 0;
                        row.purchaseNumber = value;
                        return '<input type="text" class="purchase-number" value=' + value + '>';
                    } else {
                        row.purchaseNumber = value;
                        return '<input type="text" class="purchase-number" value=' + value + '>';
                    }
                },
                events: {
                    'blur .purchase-number':function (e,value,row,index) {
                        var inputValue = Number($(this).val());
                        var purchaseMaxNumber = row.canBePurchasedPieces;
                        if(inputValue - purchaseMaxNumber > 0 || inputValue == 0){
                            $(this).addClass("table-input-warning");
                            $("#orderbtn").attr("disabled","disabled");
                        }else{
                            $(this).removeClass("table-input-warning");
                            $("#orderbtn").removeAttr("disabled");
                            var calcReturnAmount = (inputValue * row.buyingPrice).toFixed(2);
                            $orderTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'purchaseNumber',
                                value: inputValue
                            });
                            $orderTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'discountAmount',
                                value: calcReturnAmount
                            });
                        }
                    }
                }
            },  {
                field: 'canBePurchasedPieces',
                title: '可采库存'
            }, {
                field: 'currencyCode',
                title: '币种',
                formatter: function (value) {
                    if(!value){
                        return "CNY"
                    }else{
                        return value
                    }
                }
            }, {
                field: 'guidePriceStr',
                title: '指导价'
            }, {
                field: 'buyingPriceStr',
                title: '进货价',
                cellStyle: {
                    css: {
                        'font-size': '30px',
                        'color': '#ff7623'
                    }
                }
            }, {
                field: 'discountStr',
                title: '折扣点'
            }, {
                field: 'discountAmount',
                title: '折后小计',
                formatter: function(value,row,index){
                    return value;
                },
                footerFormatter: function(row) {
                    if(row.length === 0 || !row[0].discountAmount){
                        $orderEle.totalAmount.html(0);
                    }else{
                        var count = 0;
                        for(var i in row){
                            count += Number(row[i].discountAmount);
                        }
                        count = count.toFixed(2);
                        console.log(count)
                        console.log(typeof count)
                        $orderEle.totalAmount.html(count);
                    }
                }
            }, {
                field: 'endTime',
                title: '有效期至',
                formatter: function (value, row, index) {
                    return $.transFormTime(value);
                }
            }, {
                title: '操作',
                formatter: function () {
                    return '<a href="javascript:void(0);" class="order-delete">' + '删除' + '</a>';
                },
                events: {
                    "click .order-delete": function (event, value, row) {
                        $orderTable.bootstrapTable('remove',{
                            field: 'productCode',
                            values:  [row.productCode]
                        });
                    }
                }
            }]
        })
    }

    function orderPageEle() {
        var $orderQueryBtn = $("#orderquerybtn");
        var $orderTable = $("#order-table");
        var $orderProList = $("#order-prolist");
        var $totalAmount = $("#totalamount");
        var $orderBtn = $("#orderbtn");
        // var $availRebate = $("#availrebate");
        // var $availAmount = $("#availamount");
        var eleObj = {
            // 查询按钮
            queryBtn: $orderQueryBtn,
            // 商品列表
            orderProList: $orderProList,
            // 订单表格
            orderTable: $orderTable,
            // 总金额
            totalAmount: $totalAmount,
            // 可用返利
            // availRebate: $availRebate,
            // 可用代垫
            // availAmount: $availAmount,
            // 下单
            orderBtn: $orderBtn
        };
        return eleObj
    }
    // 商品信息列表
    function orderProductListAppendEle(data, code, name, id) {
        var $orderProList = $("#order-prolist");
        $orderProList.html("");
        if (data.length !== 0) {
            $.each(data, function (index, item) {
                if (!item[code]) {
                    item[code] = ""
                }
                if (!item[name]) {
                    item[name] = "";
                }
                var arrChildEles = '<li><span>' + item[code] + '</span><span>' + item[name] + '</span>' + '<input type="button" value="添加" id="yhpro-' + item[code] + '-' + item[id] + '"></li>';
                $orderProList.append(arrChildEles);
            });
        } else {
            layer.msg("无数据！");
        }
    }

    // 根据输入框内容搜索
    function getProductList() {
        var $orderQueryInput = $("#orderquery-input");
        var queryParams = {
            queryStr: $orderQueryInput.val()
        };
        $.get(URL + "/order/getsProductList", queryParams, function (res) {
            if (res.returnCode === 0) {
                var productList = res.data;
                orderProductListAppendEle(productList, "productCode", "productName", "productId");
            } else {
                layer.msg(res.message)
            }
        }, "json");
    }
});