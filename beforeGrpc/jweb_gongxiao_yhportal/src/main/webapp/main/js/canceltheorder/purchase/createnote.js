/**
 * @authors {Pinocchio} (http://csshack.org)
 * @date    2018-03-19 09:55:00
 */
$(function () {

    // 全局接口
    var URL = $.getfenxiaoURL();
    init();

    function init() {
        // 获取项目列表
        $.getSelectOptions("projectlist",URL + "/purchase/foundation/getProjectList","projectId","projectName");

        $("#projectlist").change(function () {
            var projectChangeText = $(this).children("option:selected").text();
            $("#projecttext").html(projectChangeText);
        });
        // 选择采购单商品
        var $chooseBtn = $("#choosebtn");
        $chooseBtn.click(function () {
            $createNoteMask.removeClass("yhmask-hide");
        });
        // 遮罩层
        var $createNoteMask = $("#yhmask-createnote");
        // 遮罩层确认按钮
        var $submitMaskBtn = $("#createnotemask-submit");


        // 选择采购单的商品查询
        var $queryInput = $("#queryinput");
        var $queryRadio = $("#queryradio");
        // 单号输入框
        $queryInput.click(function () {
            var maskParams = {
                projectId: $("#projectlist").val(),
                purchaseOrderNumber: $("#orderno").val()
            };
            $.get(URL + "/purchaseReturn/getInboundOrderList", maskParams, function (res, status, xhr) {
                if (res.returnCode === 0) {
                    var maskLoadRadioList = res.data;
                    var tempRadio = '';
                    $.each(maskLoadRadioList, function (index, item) {
                        if (item.inboundOrderStatus == 0) {
                            item.inboundOrderStatus = "未入库"
                        } else if (item.inboundOrderStatus == 1) {
                            item.inboundOrderStatus = "部分入库"
                        } else if (item.inboundOrderStatus == 2) {
                            item.inboundOrderStatus = "已入库"
                        }
                        tempRadio += '<p><input type="radio" id="' + item.inboundOrderNumber + '" name="product">' +
                            '<label for="' + item.inboundOrderNumber + '"></label>' + '<span>入库单号：</span>' +
                            '<strong>' + item.inboundOrderNumber +
                            '</strong>' + '<span>状态：</span>' + '<strong>' + item.inboundOrderStatus +
                            '</strong>' + '<span>批次号：</span>' + '<strong>' + item.batchNo +
                            '</strong>' + '<span>入库时间：</span>' + '<strong>' + $.transformTime(item.inboundDate, "seconds") +
                            '</strong>' + '<span>入库仓库：</span>' + '<strong>' + item.warehouseName +
                            '</strong>' + '</p>';
                    });
                    $queryRadio.append(tempRadio);

                    // 根据单号变化得到明细
                    var $arrRadios = $queryRadio.find('input[type="radio"]');
                    // 遮罩层表格
                    var $maskTable = $("#createnotetable");
                    $arrRadios.change(function () {
                        var orderNumber = $(this).attr("id");
                        $.get(URL + "/purchaseReturn/getInboundItemList", {
                            gongxiaoInboundOrderNo: orderNumber,
                            projectId: $("#projectlist").val()
                        },function(res, status, xhr) {
                            if (res.returnCode == 0) {
                                renderMaskTable();
                                var maskLoadTable = res.data;
                                $maskTable.bootstrapTable('load', maskLoadTable)
                            }
                        }, "json");
                    })
                }
            }, "json");
        });

        // 确认采购退货单
        $submitMaskBtn.click(function () {
            // 遮罩层表格
            var $maskTable = $("#createnotetable");
            // 退换货管理表格
            var $returnedPurchaseManagementTable = $("#returned-purmanagement");
            returnedRenderTable();
            var returnedLoadTable = $maskTable.bootstrapTable("getSelections");
            if(returnedLoadTable.length !== 0){
                $createNoteMask.addClass("yhmask-hide");
                $returnedPurchaseManagementTable.bootstrapTable("load", returnedLoadTable);
            }else{
                layer.msg("请选择退货单商品!");
            }
        });

        // 提交退货单
        var $createnoteSubmit = $("#createnote-submit");
        var isAjaxFlag = false;
        $createnoteSubmit.click(function () {
            // 退换货管理表格
            var $returnedPurchaseManagementTable = $("#returned-purmanagement");
            var originalGongxiaoInboundOrderNo = $queryRadio.find('input[type="radio"]:checked').attr("id");
            var submitJson = $returnedPurchaseManagementTable.bootstrapTable("getData");
            submitJson = JSON.stringify(submitJson);
            var submitParams = {
                // 原始采购单号
                originalPurchaseOrderNo: $("#orderno").val(),
                // 原始入库单号
                originalGongxiaoInboundOrderNo: originalGongxiaoInboundOrderNo,
                // 收件人姓名
                recipientName: $("#recipientname").val(),
                // 收件人公司名称
                recipientCompanyName: $("#recipientcompany").val(),
                // 收件人地址
                recipientAddress: $("#recipientaddress").val(),
                // 收件人详细地址
                recipientDetailAddress: $("#recipientaddressdetail").val(),
                // 收件人电话
                recipientMobile: $("#iphoneno").val(),
                // 物流方式
                shippingMode: $("#shippingmode").val(),
                // 物流单号
                logisticsOrderNo: $("#logisticno").val(),
                // 物流公司
                logisticsCompany: $("#logistic").val(),
                // 仓库ID
                warehouseId: $("#warehouseid").val(),
                // 仓库名称
                warehouseName: $("#warehouseid").children("option:selected").text(),
                // 运费承担方
                freightPaidBy: $("#fareselect").val(),
                // 运费金额
                freight: $("#freightpai").val(),
                // 项目ID
                projectId: $("#projectlist").val(),
                // 项目名称
                projectName: $("#projectlist").children("option:selected").text(),
                // 退货类型
                returnType: 0,
                // 采购退货单货品信息
                purchaseReturnOrderItemJson: submitJson
            };
            if(isAjaxFlag){
                return;
            }
            isAjaxFlag = true;
            $.ajax({
                type: "POST",
                url: URL + "/purchaseReturn/putPurchaseDetail",
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
                        $.renderLayerMessage("提交成功！");
                        window.location.href = './purchase-management.html';
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

    // 退换货表格渲染
    function returnedRenderTable() {
        // 退换货管理表格
        var $returnedPurchaseManagementTable = $("#returned-purmanagement");
        var returnedTable = [{}];
        $returnedPurchaseManagementTable.bootstrapTable({
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
                field: 'currencyCode',
                title: '币种'
            }, {
                field: 'purchasePrice',
                title: '进货价'
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
                formatter: function (value, row, index) {
                    // if (!value) {
                    //     row.returnNumber = 0;
                    //     return '<span class="notereducebtn">' + '-' + '</span>' + '<input type="text" value=' + value + ' readonly>' + '<span class="noteaddbtn">' + '+' + '</span>';
                    // } else {
                    //     row.returnNumber = value;
                    //     return '<span class="notereducebtn">' + '-' + '</span>' + '<input type="text" value=' + value + ' readonly>' + '<span class="noteaddbtn">' + '+' + '</span>';
                    // }
                    if(!value){
                        return ""
                    }else{
                        return '<input type="text" class="cancel-input" value=' + value + ' readonly>';
                    }
                },
                events: {
                    'blur .cancel-input': function (event,value,row,index) {
                        var inputValue = $(this).val();
                        var calcReturnAmount = value * row.purchasePrice;
                        $returnedPurchaseManagementTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'returnNumber',
                            value: inputValue
                        });
                        $returnedPurchaseManagementTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'returnAmount',
                            value: calcReturnAmount
                        })
                    }
                    // 'click .noteaddbtn': function (event, value, row, index) {
                    //     value++;
                    //     var calcReturnAmount = value * row.purchasePrice;
                    //     $returnedPurchaseManagementTable.bootstrapTable('updateCell', {
                    //         index: index,
                    //         field: 'returnNumber',
                    //         value: value
                    //     });
                    //     $returnedPurchaseManagementTable.bootstrapTable('updateCell', {
                    //         index: index,
                    //         field: 'returnAmount',
                    //         value: calcReturnAmount
                    //     })
                    //
                    // },
                    // 'click .notereducebtn': function (event, value, row, index) {
                    //     value--;
                    //     if (value <= 0) {
                    //         value = 0;
                    //     }
                    //     var calcReturnAmount = value * row.purchasePrice;
                    //     $returnedPurchaseManagementTable.bootstrapTable('updateCell', {
                    //         index: index,
                    //         field: 'returnNumber',
                    //         value: value
                    //     });
                    //     $returnedPurchaseManagementTable.bootstrapTable('updateCell', {
                    //         index: index,
                    //         field: 'returnAmount',
                    //         value: calcReturnAmount
                    //     })
                    // }
                }
            }, {
                field: 'returnReferNumber',
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
                        $returnedPurchaseManagementTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'returnReason',
                            value: reasonUpdateValue
                        });
                    }
                }
            }, {
                title: '操作',
                formatter: function (value, row, index) {
                    if (!row.customerId) {
                        return ""
                    }
                    return '<a class="pur-addbtn purchaseaddbtn salesadd" href="javascript:void(0)">' + '添加' + '</a>'
                }
            }]
        })
    }

    // 遮罩层表格渲染
    function renderMaskTable() {
        // 遮罩层表格
        var $maskTable = $("#createnotetable");
        var maskTableData = [{}];
        $maskTable.bootstrapTable({
            undefinedText: "",
            language: "zh-CN",
            data: maskTableData,
            // 隔行变色
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
                field: 'guidePrice',
                title: '指导价'
            }, {
                field: 'purchasePrice',
                title: '采购价'
            }, {
                field: 'customerName',
                title: '已发数量'
            }, {
                field: 'returnReferNumber',
                title: '可退数量'
            }]
        })
    }
});