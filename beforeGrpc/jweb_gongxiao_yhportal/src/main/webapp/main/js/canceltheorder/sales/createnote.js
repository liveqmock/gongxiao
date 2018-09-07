/**
 * @authors {Pinocchio} (http://csshack.org)
 * @date    2018-03-19 09:55:00
 * @version $Id$
 */
$(function () {
    // 全局接口
    var URL = $.getfenxiaoURL();

    //退换货管理表格
    var $yhTable = $("#yhtable");
    // 遮罩层表格
    var $maskTable = $("#yhmask-table");

    // 初始化
    function init() {


        //选择采购单商品按钮
        var $chooseBtn = $("#choosebtn");
        // 遮罩层
        var $yhMask = $("#yhmask");
        // 遮罩层确认按钮
        var $submitMaskBtn = $("#ymask-submit");

        $("#shippingmode").select2({
            minimumResultsForSearch: 6
        });
        $("#fareselect").select2({
            minimumResultsForSearch: 6
        });

        var $recipientAddress = $('#recipientaddress');
        $.fn.regionpicker.setDefaults({
            store: window['ChineseRegions']
        });
        $recipientAddress.regionpicker({
            codes: '420000/420100/420104',
            addresses: '湖北/武汉/硚口'
        });

        // 项目列表
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName")
            .then(function () {
                var salesOrderNo = $.getUrlParam("salesOrderNo");
                if (salesOrderNo) {
                    $yhMask.removeClass("yhmask-hide");
                    $("#orderno").val(salesOrderNo);
                    var maskParams = {
                        projectId: $("#projectlist").val(),
                        salesNo: salesOrderNo
                    };
                    proQuery(maskParams);
                }
            });
        // 仓库列表
        $.getSelectOptions("warehouselist", URL + "/purchase/foundation/warehouseList", "warehouseId", "warehouseName");

        $("#projectlist").change(function () {
            var projectChangeText = $(this).children("option:selected").text();
            $("#projecttext").html(projectChangeText);
        });


        // 选择采购单商品
        $chooseBtn.click(function () {
            $yhMask.removeClass("yhmask-hide");
        });


        // 选择采购单的商品查询
        var $queryInput = $("#yhmask-query");
        // 出库单号作为新增采购单的参数传入
        var outOrderNo = "";

        function proQuery(maskParams) {

            var $queryRadio = $("#queryradio");
            $queryRadio.html("");
            $.get(URL + "/sales/return/getRecordList", maskParams, function (res) {
                if (res.returnCode === 0) {
                    var maskLoadRadioList = res.data;
                    var tempRadio = '';
                    $.each(maskLoadRadioList, function (index, item) {
                        // 出库状态说明
                        tempRadio += '<p><input type="radio" id="' + item.gongxiaoOutboundOrderNo + '" name="product">' +
                            '<label for="' + item.gongxiaoOutboundOrderNo + '"></label>' + '<span>出库单号：</span>' +
                            '<strong>' + item.gongxiaoOutboundOrderNo +
                            '</strong>' + '<span>状态：</span>' + '<strong>' + item.orderStatus +
                            '</strong>' + '<span>收件人信息：</span>' + '<strong>' + item.contactsPeople + " " + item.phoneNum +
                            '</strong>' + '<span>更新时间：</span>' + '<strong>' + $.transformTime(item.lastUpdateTime, "seconds") +
                            '</strong>' + '</p>';
                    });
                    $("#recipientname").val(maskLoadRadioList[0].contactsPeople);
                    $("#iphoneno").val(maskLoadRadioList[0].phoneNum);
                    $recipientAddress.regionpicker({
                        province: maskLoadRadioList[0].provinceName,
                        city: maskLoadRadioList[0].cityName,
                        district: maskLoadRadioList[0].districtName
                    });


                    $("#recipientaddressdetail").val(maskLoadRadioList[0].shippingAddress);
                    $("#recipientcompany").val(maskLoadRadioList[0].recipientCompany);
                    $queryRadio.append(tempRadio);
                    var $arrRadios = $queryRadio.find('input[type="radio"]');
                    $arrRadios.change(function () {
                        var orderNumber = $(this).attr("id");
                        outOrderNo = orderNumber;
                        $.get(URL + "/sales/return/getRecordItemList", {
                            gongxiaoOutboundOrderNo: orderNumber,
                            projectId: $("#projectlist").val(),
                            salesOrderNo: $("#orderno").val()
                        }, function (res) {
                            if (res.returnCode === 0) {
                                renderMaskTable();
                                var maskLoadTable = res.data;
                                $maskTable.bootstrapTable('load', maskLoadTable)
                            }
                        }, "json")
                    })
                }
            }, "json");
        }

        // 单号输入框
        $queryInput.click(function () {
            var maskParams = {
                projectId: $("#projectlist").val(),
                salesNo: $("#orderno").val()
            };
            proQuery(maskParams);
        });

        //确认采购退货单
        $submitMaskBtn.click(function () {
            returnedRenderTable();
            var returnedLoadTable = $maskTable.bootstrapTable("getSelections");
            if (returnedLoadTable.length === 0) {
                $.renderLayerMessage("请选择退货单商品")
            } else {
                $yhMask.addClass("yhmask-hide");
                $yhTable.bootstrapTable("load", returnedLoadTable);
            }
        });

        // 提交销售退货单
        var $createnoteSubmit = $("#createnote-submit");
        var isAjaxFlag = false;
        $createnoteSubmit.click(function () {
            var $queryRadio = $("#queryradio");
            var $warehouseId = $("#warehouseid");
            var $projectList = $("#projectlist");
            var $warehouseList = $("#warehouselist");
            var originalGongxiaoInboundOrderNo = $queryRadio.find('input[type="radio"]:checked').attr("id");
            var yhTableData = $yhTable.bootstrapTable("getData");
            var submitJson = [{}];
            $.each(yhTableData, function (index, item) {
                submitJson[index] = {
                    productId: item.productId,
                    productCode: item.productCode,
                    productName: item.productName,
                    productUnit: 1,
                    returnCause:item.returnReason,
                    totalReturnedQuantity: item["returnNumber"],
                    warehouseId: $warehouseId.val(),
                    warehouseName: $warehouseId.children("option:selected").text()
                };
            });

            var submitParamsJson = JSON.stringify(submitJson);
            var submitParams = {
                // 项目ID
                projectId: $projectList.val(),
                // 项目名称
                projectName: $projectList.children("option:selected").text(),
                // 原销售单号
                originalSalesOrderNo: $("#orderno").val(),
                // 原销售单的出库单号
                originalGongxiaoOutboundOrderNo: outOrderNo,
                // 退货目标仓库ID(
                targetWarehouseId: $warehouseList.val(),
                // 退货目标仓库名称
                targetWarehouseName: $warehouseList.children("option:selected").text(),
                // 发货仓库ID(
                originalOutboundWarehouseId: $warehouseList.val(),
                // 发货仓库名称
                originalOutboundWarehouseName: $warehouseList.children("option:selected").text(),
                // 发件人名字
                senderName: $("#recipientname").val(),
                // 收件人手机号
                senderMobile: $("#iphoneno").val(),
                // 运输方式
                logisticsType: $("#shippingmode").val(),
                // 运费
                freightDouble: $("#freightpai").val(),
                // 运费承担方
                freightPaidBy: $("#fareselect").val(),
                // 物流单号
                logisticsOrderNo: $("#logisticno").val(),
                // 物流公司名称
                logisticsCompany: $("#logistic").val(),
                provinceId: $recipientAddress.getRegionPicker().getCode("province"),
                provinceName: $recipientAddress.getRegionPicker().getVal("province"),
                cityId: $recipientAddress.getRegionPicker().getCode("city"),
                cityName: $recipientAddress.getRegionPicker().getVal("city"),
                districtId: $recipientAddress.getRegionPicker().getCode("district"),
                districtName: $recipientAddress.getRegionPicker().getVal("district"),
                senderAddressItem: $("#recipientaddressdetail").val(),
                salesReturnOrderItemListJson: submitParamsJson
            };
            $.each(yhTableData,function (index,returnProItem) {
                if(Number(returnProItem["returnNumber"]) > Number(returnProItem["canReturnQuantity"])){
                    return false;
                }
            });

            if(isAjaxFlag){
                return;
            }
            isAjaxFlag = true;
            $.ajax({
                type: "POST",
                url: URL + "/sales/return/addSalesReturn",
                cache: false,
                dataType: 'json',
                data: submitParams,
                async: false,
                beforeSend: function () {
                    $.renderLoading(true);
                    $createnoteSubmit.attr("disabled", true);
                },
                complete: function () {
                    $.renderLoading(false);
                    $createnoteSubmit.attr("disabled",false);
                },
                success: function (res) {
                    if (res.returnCode === 0) {
                        window.location.href = "./sales-cancel-management.html"
                    } else {
                        setTimeout(function(){
                            $.renderLoading(false);
                        },2000);
                        isAjaxFlag = false;
                        $.renderLayerMessage(res.message);
                    }
                },
                error: function (err) {
                    isAjaxFlag = false;
                    $.renderLoading(false);
                    $.renderLayerMessage(err);
                }
            });
        })
    }

    init();


    // 退换货表格渲染
    function returnedRenderTable() {
        var returnedTable = [{}];
        $yhTable.bootstrapTable({
            undefinedText: "",
            data: returnedTable,
            language: "zh-CN",
            //隔行变色
            striped: true,
            columns: [{
                field: 'productName',
                title: '货品名称',
                clickToSelect: true
            }, {
                field: 'productCode',
                title: '型号'
            }, {
                field: 'currencyName',
                title: '币种'
            }, {
                field: 'wholesalePriceStr',
                title: '出货价'
            }, {
                field: 'returnAmount',
                title: '商品退款金额',
                formatter: function (value, row, index) {
                    if (!value) {
                        row.returnAmount = 0;
                        return 0
                    } else {
                        return row.returnAmount
                    }
                }
            }, {
                field: 'returnNumber',
                title: '退货数量',
                class: 'numberinput',
                formatter: function (value,row) {
                    if(!value){
                        var defaultReturnNumber = 0;
                        row.returnNumber = defaultReturnNumber;
                        return '<input type="text" class="return-number" value="' + defaultReturnNumber + '">';
                    }else{
                        return '<input type="text" class="return-number" value="' + value + '">';
                    }
                },
                events: {
                    'blur .return-number': function (e, value, row, index) {
                        var inputValue = Number($(this).val());
                        var _canReturnQuantity = Number(row.canReturnQuantity);
                        if(inputValue < _canReturnQuantity || inputValue === _canReturnQuantity){
                            $(this).removeClass("table-warning-input");
                            $yhTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'returnNumber',
                                value: inputValue
                            });
                        }else{
                            $(this).addClass("table-warning-input");
                        }
                    }
                }
                // formatter: function(value, row) {
                //     if (!value) {
                //         row.returnNumber = 0;
                //         value = 0;
                //         return '<span class="notereducebtn">' + '-' + '</span>' + '<input type="text" value=' + value + ' readonly>' + '<span class="noteaddbtn">' + '+' + '</span>';
                //     } else {
                //         row.returnNumber = value;
                //         return '<span class="notereducebtn">' + '-' + '</span>' + '<input type="text" value=' + value + ' readonly>' + '<span class="noteaddbtn">' + '+' + '</span>';
                //     }
                // },
                // events: {
                //     'click .noteaddbtn': function(event, value, row, index) {
                //         value++;
                //         var calcReturnAmount = value * row.wholesalePrice;
                //         $yhTable.bootstrapTable('updateCell', { index: index, field: 'returnNumber', value: value });
                //         $yhTable.bootstrapTable('updateCell', { index: index, field: 'returnAmount', value: calcReturnAmount.toFixed(2) })
                //
                //     },
                //     'click .notereducebtn': function(event, value, row, index) {
                //         value--;
                //         if (value <= 0) {
                //             value = 0;
                //         }
                //         var calcReturnAmount = value * row.wholesalePrice;
                //         $yhTable.bootstrapTable('updateCell', { index: index, field: 'returnNumber', value: value });
                //         $yhTable.bootstrapTable('updateCell', { index: index, field: 'returnAmount', value: calcReturnAmount })
                //     }
                // }
            }, {
                field: 'canReturnQuantity',
                title: '可退数量'
            }, {
                field: 'returnReason',
                title: '退货原因',
                formatter: function (value, row, index) {
                    if (!value) {
                        row.returnReason = "";
                        return '<input type="text" class="reason-input">';
                    } else {
                        row.returnReason = value;
                        return '<input type="text" class="reason-input" value="' + value + '">';
                    }
                },
                events: {
                    'blur .reason-input': function (event, value, row, index) {
                        var reasonUpdateValue = $(this).val();
                        $yhTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'returnReason',
                            value: reasonUpdateValue
                        });
                    }
                }
            }]
        })
    }

    // 遮罩层表格渲染
    function renderMaskTable() {
        var maskTableData = [{}];
        $maskTable.bootstrapTable({
            undefinedText: "",
            language: "zh-CN",
            data: maskTableData,
            //隔行变色
            striped: true,
            columns: [{
                checkbox: true
            }, {
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'productCode',
                title: '货品编码'
            }, {
                field: 'currencyName',
                title: '币种'
            }, {
                field: 'guidePriceStr',
                title: '指导价'
            }, {
                field: 'wholesalePriceStr',
                title: '出货价'
            }, {
                field: 'outStockQuantity',
                title: '已发数量'
            }, {
                field: 'canReturnQuantity',
                title: '可退数量'
            }]
        })
    }
});