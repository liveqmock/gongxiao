$(function() {


    var URL = $.getfenxiaoURL();

    function init() {
        // 货品信息表格
        var $yhTable = $("#yhtable");
        // 操作日志表格
        var $logTable = $("#yhlogtable");
        // 获取url中的参数        
        var purchaseOrderNumber = $.getUrlParam("purchaseOrderNumber");
        // 表格右侧金额列表
        var $moneyList = $("#detailmoney").find("b");

        $.get(URL+"/purchase/order/getPurchaseDetail",{
            purchaseOrderNo: purchaseOrderNumber
        },function(res){
            if(res.returnCode === 0){
                var resData = res.data;
                var itemList = resData.itemList;
                var brandInfo =  {
                    brandName: resData.brandName
                };
                $.each(itemList, function(index, order) {
                    $.extend(order, brandInfo);
                });
                renderTable();
                var detailLoadData = itemList;
                $yhTable.bootstrapTable('load',detailLoadData);
                //项目名称
                $("#projectid").val(resData.projectName);
                // 供应商名称
                $("#supplier").val(resData.supplierName);
                //仓库名称
                $("#warehouse").val(resData.warehouseName);
                //物流方式
                if(resData.shippingMode == 0){
                    var logistic = "不详";
                }else if(resData.shippingMode == 1){
                    var logistic = "自提";
                }else if(resData.shippingMode == 2){
                    var logistic = "第三方物流"
                }
                $("#logistic").val(logistic);
                //收货地址
                $("#shopaddress").val(resData.address);
                //业务日期(缺少字段)
                var businessDate = $.transformTime(resData.businessDate);
                $("#business-date").val(businessDate);
                //付款方式
                // if(resData.paymentMode == 0){
                //     var paidstyle = "预付费";
                // }else if(resData.paymentMode == 1){
                //     var paidstyle = "后付费";
                // }
                $("#purchase-style").val(resData.purchaseType);
                //要求到货日期
                var expectedInboundDate = $.transformTime(resData.expectedInboundDate);
                $("#received-date").val(expectedInboundDate);
                //到货截至日期
                var orderDeadlineDate = $.transformTime(resData.orderDeadlineDate);
                $("#cutoff-date").val(orderDeadlineDate);
                //品牌商订单号
                $("#brandno").val(resData.brandOrderNo);
                //合同参考号
                $("#orderno").val(resData.contractReferenceOrderNo);
                //备注
                $("#remark").val(resData.remark);
                //采购品种
                $("#pro-type").text(resData.purchaseCategory);
                //采购总数量
                $("#pro-number").text(resData.totalQuantity);
                //采购单指导金额,采购优惠金额,代垫使用金额,返利使用金额,现金折扣金额
                // var arrAmount = [resData.purchaseGuideAmount,resData.purchasePrivilegeAmount,resData.prepaidAmountUse,resData.couponAmountUse,resData.returnCash];
                var arrAmount = [resData.purchaseGuideAmount,resData.prepaidAmountUse,resData.couponAmountUse,resData.returnCash];
                $moneyList.each(function(i){
                    $(this).html(accounting.formatMoney(arrAmount[i], "", 2, ",", "."));
                });
                // 采购实付金额
                $("#payamount").html(accounting.formatMoney(resData.purchaseActualPayAmount, "", 2, ",", "."));
                //操作日志表格
                logTableRender();
                var logLoadData = res.data.traceLogList;
                $logTable.bootstrapTable('load',logLoadData);
            }
        },"json");


        //渲染表格
        function renderTable() {
            var detailData = [{}];
            $yhTable.bootstrapTable({
                undefinedText: '',
                clickToSelect: true,
                striped: true,
                data: detailData,
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
                    field: 'purchaseNumber',
                    title: '采购数量/个'
                }, {
                    field: 'discountPercent',
                    title: '采购折扣',
                    visible: false,
                    formatter: function (value) {
                        return (Number(value * 100)).toFixed(6);
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
                },{
                    field: 'costPrice',
                    title: '成本价'
                }, {
                    field: 'purchasePrice',
                    title: '进货价'
                }]
            })
        }

        // 操作日志表格
        function logTableRender() {
            var logData = [{}];
            $logTable.bootstrapTable({
                undefinedText: '',
                clickToSelect: true,
                striped: true,
                data: logData,
                columns: [{
                    field: 'operateFunction',
                    title: '描述'
                }, {
                    field: 'operateTimeString',
                    title: '操作时间'
                }, {
                    field: 'operateName',
                    title: '操作人'
                }]
            })
        }

    }
    init();
});