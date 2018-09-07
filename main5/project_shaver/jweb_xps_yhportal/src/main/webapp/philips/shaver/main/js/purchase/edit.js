$(function () {

    init();
    function init() {
        var URL = $.getfenxiaoURL();
        renderTable(URL);
        renderSelect(URL);
        getPurchaseEditDetail(URL);

        $("#couponamount").on("blur",function () {
            calc(URL,1);
        });
        $("#purchase-prepaid").on("blur",function () {
            calc(URL,1);
        });
        $("couponpostamount").on("blur",function () {
            calc(URL,2);
        });
        $("#prepaidpostamount").on("blur",function () {
            calc(URL,2);
        });



        //保存提交
        var isAjaxFlag = false;
        var $yhSubmitBtn = $("#yhsubmit-btn");
        $yhSubmitBtn.click(function () {
            verification.emptyShow = false;
            verification.noEmpty("supplierlist", "supplier-list-verification", "供应商不能为空!");
            verification.noEmpty("warehouselist", "warehouse-list-verification", "仓库名不能为空!");
            verification.noEmpty("logistic", "logistic-verification", "请选择物流方式!");
            verification.noEmpty("shopaddress", "shopaddress-verification", "收货地址不能为空!");
            verification.noEmpty("yhbusiness-date", "yhbusiness-date-verification", "业务时间不能为空!");
            verification.noEmpty("yhbusiness-date", "yharrival-date-verification", "要求到货时间不能为空!");
            verification.noEmpty("purchase-type", "purchase-type-verification", "请选择采购方式!");
            verification.noEmpty("brandno", "brandno-verification", "品牌商订单号不能为空!");
            verification.noEmpty("orderno", "orderno-verification", "合同参考号不能为空!");

            if (verification.emptyShow) {
                return false;
            }
            if(isAjaxFlag){
                return;
            }
            isAjaxFlag = true;

            var urlParams = getUrlParams();
            var purchaseItemInfoJson = $("#yhtable").bootstrapTable("getData");
            var purchaseGuideAmount = formatNoComma($("#purchase-guideamount").html());
            purchaseItemInfoJson = JSON.stringify(purchaseItemInfoJson);
            var submitParams = {
                purchaseOrderNo:urlParams.purchaseOrderNumber,
                warehouseId:$("#warehouselist").val(),
                supplierCode:$("#supplierlist").val(),
                // 应该改为采购方式
                purchaseType:$("#purchase-type").val(),
                logisticType:$("#logistic").val(),
                businessDate:$("#yhbusiness-date").val(),
                requireArrivalDate:$("#yharrival-date").val(),
                arrivalDeadline:$("#yhexpiry-date").val(),
                remark:$("#remask").val(),
                //各金额
                couponAmountUse:$("#couponamount").val(),
                prepaidAmountUse:$("#purchase-prepaid").val(),
                adCouponAmountUse:$("#couponpostamount").val(),
                adPrepaidAmountUse:$("#prepaidpostamount").val(),
                purchaseGuideAmount:purchaseGuideAmount,
                // purchasePrivilegeAmount:$("#purchase-shouldpay").html(),
                purchaseItemInfoJson:purchaseItemInfoJson
            };

            $.ajax({
                type: "POST",
                url: URL + "/purchase/order/putPurchaseEditDetail",
                cache: false,
                dataType: 'json',
                data: submitParams,
                async: false,
                beforeSend: function () {
                    $.renderLoading(true);
                    $yhSubmitBtn.attr("disabled", true);
                },
                complete: function () {
                    $.renderLoading(false);
                    $yhSubmitBtn.attr("disabled",false);
                },
                success: function (res) {
                    if (res.returnCode === 0) {
                        window.location.href = "./purchase-management.html";
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

    function formatNoComma(amount){
        return accounting.formatMoney(amount, "", 2, "", ".")
    }

    function format(amount) {
        return accounting.formatMoney(amount, "", 2, ",", ".")
    }

    function getPurchaseEditDetail(detailUrl){
        var urlParams = getUrlParams();
        $("#projectid").val(urlParams.projectName);
        var getDetailParams = {
            projectId:urlParams.projectId,
            projectName:urlParams.projectName,
            purchaseOrderNumber:urlParams.purchaseOrderNumber
        };

        $.get(detailUrl+"/purchase/order/getPurchaseEditDetail",getDetailParams,function (res) {
            if(res.returnCode === 0){
                var resData = res.data;
                renderTable(URL);
                $("#yhtable").bootstrapTable("load",resData.itemList);
                var businessDate = $.transformTime(resData.businessDate);

                layui.config({
                    version: '1515376178709' //为了更新 js 缓存
                });
                layui.use(['laydate'], function () {
                    var laydate = layui.laydate;
                    laydate.render({
                        elem: '#yhbusiness-date',
                        format: 'yyyy-MM-dd',
                        value: businessDate
                    });
                });
                $("#brandno").val(resData.supplierOrderNo);
                $("#orderno").val(resData.contractReferenceOrderNo);

                //种类 采购数量,返利账户余额  代垫账户余额
                $("#purchase-variety").html(resData.purchaseCategory);
                $("#pruchase-totalnum").html(resData.purchaseTotalNumber);
                $("#couponmoney").html(format(resData.accountCouponAmount));
                $("#prepaidamount").html(format(resData.accountPrepaidAmount));


                // 采购指导金额 采购应付金额 采购优惠 采购实付 现金返点金额
                $("#purchase-guideamount").html(resData.purchaseGuideAmount);
                $("#purchase-shouldpay").html(resData.purchaseShouldPayAmount);
                // $("#purchase-privilege").html(format(resData.purchasePrivilegeAmount));
                $("#purchase-pay").html(resData.purchaseActualPayAmount);
                $("#purchase-returncash").html(format(resData.returnCash));

                //返利代垫金额
                $("#couponamount").val(resData.couponAmountUse);
                $("#purchase-prepaid").val(resData.prepaidAmountUse);
                $("#couponpostamount").val(resData.adCouponAmountUse);
                $("#prepaidpostamount").val(resData.adPrepaidAmountUse);

            }
        },"json")
    }


    function getUrlParams(){
        var projectId = $.getUrlParam("projectId");
        var projectName = $.getUrlParam("projectName");
        var purchaseOrderNumber = $.getUrlParam("purchaseOrderNumber");
        return{
            projectId:projectId,
            projectName:projectName,
            purchaseOrderNumber:purchaseOrderNumber
        }
    }


    //计算
    function calc(URL,status){
        var tableData = $("#yhtable").bootstrapTable("getData");
        tableData = JSON.stringify(tableData);
        var calcParams = {
            projectId:"146798161",
            //YH返利使用金额
            couponAmountUse:$("#couponamount").val(),
            //YH代垫使用金额
            prepaidAmountUse:$("#purchase-prepaid").val(),
            //AD返利使用金额
            adCouponAmountUse:$("#couponpostamount").val(),
            //AD代垫使用金额
            adPrepaidAmountUse:$("#prepaidpostamount").val(),
            //采购应付
            purchaseShouldPayAmount:$("#purchase-shouldpay").html(),
            status: status,
            productJson:tableData
        };
        $.post(URL+"/purchase/order/calculateEditProductInfo",calcParams,function (res) {
            if(res.returnCode === 0){
                renderTable(URL);
                $("#yhtable").bootstrapTable("load",res.data.calculateProductInfoList);
                // 采购指导金额 采购应付金额 采购优惠 采购实付 现金返点金额
                $("#purchase-guideamount").html(format(res.data.purchaseGuideAmount));
                // $("#purchase-privilege").html(format(res.data.purchasePrivilegeAmount));
                //返利代垫金额
                $("#couponamount").val(res.data.couponAmount);
                $("#purchase-prepaid").val(res.data.prepaidAmount);
                $("#couponpostamount").val(res.data.adCouponAmount);
                $("#prepaidpostamount").val(res.data.adPrepaidAmount);

            }else{
                $.renderLayerMessage(res.message);
            }
        },"json");
    }

    function renderSelect(URL){
        // 获取仓库列表
        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName");
        // 供应商列表
        $.getSelectOptions("supplierlist", URL + "/supplier/selectAllSupplier", "supplierCode", "supplierName");
        // 获取收货仓库列表
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
        //物流列表
        $("#logistic").select2({
            minimumResultsForSearch: 6
        });
        //支付方式列表
        $("#purchase-type").select2({
            minimumResultsForSearch: 6
        });


        layui.config({
            version: '1515376178709' //为了更新 js 缓存
        });
        layui.use(['laydate'],function () {
            var laydate = layui.laydate;
            laydate.render({
                elem: "#yharrival-date",
                format: 'yyyy-MM-dd'
            });
            laydate.render({
                elem: "#yhexpiry-date",
                format: 'yyyy-MM-dd'
            });
        });
    }

    // 渲染表格
    function renderTable(URL) {
        var $yhTable = $("#yhtable");
        var tableData = [{}];
        $yhTable.bootstrapTable({
            silent: true,
            undefinedText: '',
            data: tableData,
            striped: true,
            pagination: true,
            pageSize: 6, // 一页显示数据条数
            pageList: [3, 6],
            pageNumber: 1, // 首页页码
            columns: [{
                field: 'productCode',
                title: '货品编码'
            }, {
                field: 'productName',
                title: '货品名称'
            },{
                field: 'guidePrice',
                title: '指导价'
            }, {
                field: 'purchaseNumber',
                title: '采购数量'
            }, {
                field: 'couponAmount',
                title: '使用返利'
            }, {
                field: 'prepaidAmount',
                title: '使用代垫'
            }, {
                field: 'cashAmount',
                title: '现金折扣',
                formatter: function (value,row) {
                    if(!value){
                        row['cashAmount'] = 0;
                        return "0.00"
                    }
                }
            },{
                field: 'discountPercent',
                title: '采购折扣',
                visible: false,
                formatter: function (value, row) {
                    if (!value) {
                        row.discountPercent = 0;
                        return '<input type="text" class="discount-input" ' + 'value=' + value + '>';
                    } else {
                        return '<input type="text" class="discount-input" ' + 'value=' + value + '>';
                    }
                },
                events: {
                    'blur .discount-input':function (e,value,row,index) {
                        var inputValue = $(this).val();
                        $yhTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'discountPercent',
                            value: inputValue
                        });
                        calc(URL,1);
                    }
                }
            },{
                field: 'purchasePrice',
                title: '采购价'
            }, {
                field: 'costPrice',
                title: '成本价'
            }, {
                field: 'purchasePrice',
                title: '进货价'
            }]
        })
    }

});