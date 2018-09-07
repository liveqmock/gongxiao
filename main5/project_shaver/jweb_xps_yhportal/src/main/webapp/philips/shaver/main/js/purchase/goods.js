/**
 * @authors {Pinocchio} (http://csshack.org)
 * @date    2018-03-05 10:03:06
 * @version $Id$
 */

$(function () {

    init();

    function init() {

        // 全局接口
        var URL = $.getfenxiaoURL();
        var infoFromManagement = getInfoFromPurchaseManagement();
        goodsQuery();
        // 查询按钮
        var $goodsQueryBtn = $("#purgood-btn");
        // 收货通知表格
        var $goodsTable = $("#purgood");
        // 提交按钮
        var $goodsSubmitBtn = $("#yhsubmit-btn");

        // 收获通知查询
        function goodsQuery() {
            var queryParams = {
                purchaseNumber: infoFromManagement.goodsNo,
                productCode: $("#productcode").val()
            };
            $.get(URL + "/purchase/order/getInboundList", queryParams, function (res) {
                if (res.returnCode === 0) {
                    renderTable();
                    var goodLoadData = res.data;
                    $.each(goodLoadData, function (index, goodsItem) {
                        if (goodsItem.requireInboundDate) {
                            $("#yhdate").val($.transformTime(goodsItem.requireInboundDate));
                        }
                    });
                    $goodsTable.bootstrapTable('load', goodLoadData);
                }
            }, "json");
        }

        $goodsQueryBtn.click(function () {
            goodsQuery();
        });

        var isAjaxFlag = false;
        //提交
        $goodsSubmitBtn.click(function () {
            $goodsSubmitBtn.attr("disabled", true);
            var productInfo = $goodsTable.bootstrapTable('getData');
            var totalQuantity = 0;
            var planInstockNumberTotal = 0;
            for (var i = 0; i < productInfo.length; i++) {
                // 采购总量
                totalQuantity += productInfo[i].purchaseNumber;
                // 计划入库总量
                planInstockNumberTotal += productInfo[i].planInstockNumber;
            }
            productInfo = JSON.stringify(productInfo);
            var submitParams = {
                projectId: infoFromManagement.projectId,
                purchaseOrderNo: infoFromManagement.goodsNo,
                warehouseId: infoFromManagement.warehouseId,
                warehouseName: infoFromManagement.warehouseName,
                requireInboundDate: $("#yhdate").val(),
                totalQuantity: totalQuantity,
                planInstockNumberTotal: planInstockNumberTotal,
                planInboundItemListJson: productInfo
            };
            var $planNumber = $(".plannumber");
            if ($planNumber.hasClass(".table-warning-input")) {
                $.renderLayerMessage("请填写正确的预约数量");
                return false;
            }
            if(isAjaxFlag){
                return;
            }
            isAjaxFlag = true;
            $.ajax({
                type: "POST",
                url: URL + "/purchase/order/planInbound",
                cache: false,
                dataType: 'json',
                data: submitParams,
                async: false,
                beforeSend: function () {
                    $.renderLoading(true);
                    $goodsSubmitBtn.attr("disabled", true);
                },
                success: function (res) {
                    if (res.returnCode === 0) {
                        $goodsSubmitBtn.attr("disabled", false);
                        window.location.href = "./purchase-management.html";
                    }else {
                        setTimeout(function(){
                            $.renderLoading(false);
                        },2000);
                        isAjaxFlag = false;
                        $.renderLayerMessage(res.message);
                    }
                },
                error: function (res) {
                    isAjaxFlag = false;
                    $.renderLoading(false);
                    $.renderLayerMessage(res);
                },
                complete: function () {
                    $.renderLoading(false);
                    $goodsSubmitBtn.attr("disabled",false);
                }
            })

        });
    }


    // 渲染收获通知表格
    function renderTable() {
        var $goodsTable = $("#purgood");
        var goodsData = [{}];
        var tableFlag = false;
        $goodsTable.bootstrapTable({
            undefinedText: '',
            locale: 'zh-CN',
            striped: true,
            data: goodsData,
            columns: [{
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'productCode',
                title: '货品编码'
            }, {
                field: 'brandName',
                title: '品牌'
            }, {
                field: 'currencyCode',
                title: '币种'
            }, {
                field: 'guidePrice',
                title: '指导价'
            }, {
                field: 'costValue',
                title: '成本价'
            }, {
                field: 'purchaseNumber',
                title: '采购数量'
            }, {
                field: 'needInstockNumber',
                title: '剩余需入库数量'
            }, {
                field: 'planInstockNumber',
                title: '本次预约',
                formatter: function (value, row) {
                    var planDefaultValue = row.needInstockNumber;
                    if(tableFlag){
                        return '<input type="text" class="plannumber" value="' + value + '">';
                    }else{
                        // if(!value){
                        //     row.planInstockNumber = planDefaultValue;
                        //     return '<input type="text" class="plannumber" value="' + planDefaultValue + '">';
                        // }else{
                        //     return '<input type="text" class="plannumber" value="' + value + '">';
                        // }
                        row.planInstockNumber = planDefaultValue;
                        return '<input type="text" class="plannumber" value="' + planDefaultValue + '">';
                    }
                },
                events: {
                    'blur .plannumber': function (event, value, row, index) {
                        var inputValue = parseInt($(this).val());
                        tableFlag = true;
                        if (inputValue > row.purchaseNumber) {
                            $(this).addClass("table-warning-input");
                            $.renderLayerMessage("请填写正确的预约数量");
                            return;
                        } else {
                            $(this).removeClass("table-warning-input");
                            $goodsTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'planInstockNumber',
                                value: inputValue
                            });
                        }
                    }
                }
            }]
        })
    }

    // 从采购单管理处获得的信息
    function getInfoFromPurchaseManagement() {
        var $projectId = $("#projectid");
        var $warehouseList = $("#warehouselist");
        var projectId = $.getUrlParam("projectId");
        var projectName = $.getUrlParam("projectName");
        var goodsNo = $.getUrlParam("purchaseOrderNumber");
        var warehouseName = $.getUrlParam("warehouseName");
        var warehouseId = $.getUrlParam("warehouseId");
        $projectId.val(projectName);
        if (warehouseName) {
            $warehouseList.val(warehouseName)
        }
        return {
            projectId: projectId,
            projectName: projectName,
            goodsNo: goodsNo,
            warehouseName: warehouseName,
            warehouseId: warehouseId
        };
    }
});