/**
 * @authors {len} (http://csshack.org)
 * @date    2018/5/4
 */
$(function () {

    prepaidAddInit();

    function prepaidAddInit() {
        // 全局接口
        var URL = $.getfenxiaoURL();
        // 页面元素
        var $pageElement = getPageElement();
        renderSelectList(URL);
        layUiDateRender();

        prepaidAddTable();
        $.fn.regionpicker.setDefaults({
            store: window['ChineseRegions']
        });

        $('#yhaddress').regionpicker({
            placeholder: '请选择省/市/县区/街道'
        });


        var isAjaxFlag = false;
        $pageElement.$prepaidSubmit.click(function () {
            verification.noEmpty("supplierlist", "supplier-list-verification", "供应商不能为空!");
            verification.noEmpty("pay-type", "pay-type-verification", "结算不能为空!");
            verification.noEmpty("payer", "payer-verification", "付款方不能为空!");
            verification.noEmpty("settlementno", "settlementno-verification", "结算方式不能为空!");
            verification.noEmpty("receivables", "receivables-verification", "收款方不能为空!");
            verification.noEmpty("businessdate", "businessdate-verification", "请选择业务日期");
            verification.noEmpty("taxno", "taxno-verification", "税号不能为空!");
            verification.noEmpty("contactinfo", "contactinfo-verification", "联系方式不能为空!");
            verification.noEmpty("streetaddress", "streetaddress-verification", "详细地址不能为空!");
            verification.noEmpty("accountcny", "accountcny-verification", "开户行不能为空!");
            verification.noEmpty("bank-name", "bank-name-verification", "账号不能为空!");

            if (verification.emptyShow) {
                return false;
            }
            var addParams = getAddParams();
            if(isAjaxFlag){
                return;
            }
            isAjaxFlag = true;
            console.log(addParams);
            $.renderLoading(true);
            $.ajax({
                type: "POST",
                url: URL + "/prepaid/info/add",
                cache: false,
                dataType: 'json',
                data: addParams,
                async: false,
                beforeSend: function () {
                    $pageElement.$prepaidSubmit.attr("disabled", true);
                },
                complete: function () {
                    $.renderLoading(false);
                    $pageElement.$prepaidSubmit.attr("disabled",false);
                },
                success: function (res) {
                    if (res.returnCode === 0) {
                        window.location.href = './prepaidmanagement.html';
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
                    setTimeout(function(){
                        $.renderLoading(false);
                    },2000);
                    $.renderLayerMessage(err);
                }
            })
        });
    }

    // 动态渲染下拉表单
    function renderSelectList(URL) {
        // 获取project列表
        $.getSelectOptions("projectlist", URL + "/purchase/foundation/getProjectList", "projectId", "projectName")
            .then(function () {
                $.getSelectOptions("supplierlist", URL + "/purchase/foundation/getSupplierList", "supplierCode", "supplierName");
            })
    }

    // 得到页面信息
    function getPageElement() {
        // 基本信息
        var $prepaidSubmit = $("#prepaid-submit");
        return {
            $prepaidSubmit: $prepaidSubmit
        }
    }

    // 日期组件渲染
    function layUiDateRender() {
        layui.config({
            version: '1515376178709' //为了更新 js 缓存
        });
        layui.use(['laydate'], function () {
            var laydate = layui.laydate;
            // 要求到货时间
            laydate.render({
                elem: "#businessdate",
                format: 'yyyy-MM-dd'
            });
        })
        $("#pay-type").select2({
            minimumResultsForSearch: 6
        })
    }

    // 新增参数
    function getAddParams() {
        var $yhaddress = $('#yhaddress');
        var $projectList = $("#projectlist");
        var $supplierId = $("#supplierlist");
        var $chargesTable = $("#charges-table");
        var provinceCode = $yhaddress.getRegionPicker().getCode("province");
        var provinceText = $yhaddress.getRegionPicker().getVal("province");
        var cityCode = $yhaddress.getRegionPicker().getCode("city");
        var cityText = $yhaddress.getRegionPicker().getVal("city");
        var districtCode = $yhaddress.getRegionPicker().getCode("district");
        var districtText = $yhaddress.getRegionPicker().getVal("district");
        var itemJson = $chargesTable.bootstrapTable("getData");
        itemJson = JSON.stringify(itemJson);
        return {
            projectId: $projectList.val(),
            payer: $("#payer").val(),
            supplierId:$supplierId.val(),
            supplierName: $supplierId.children("option:selected").text(),
            settlementType: $("#pay-type").val(),
            receivables: $("#receivables").val(),
            settlementNo: $("#settlementno").val(),
            dateBusiness: $("#businessdate").val(),
            taxNo: $("#taxno").val(),
            contactInfo: $("#contactinfo").val(),
            provinceId: provinceCode,
            provinceName: provinceText,
            cityId: cityCode,
            cityName: cityText,
            districtId: districtCode,
            districtName: districtText,
            streetAddress: $("#streetaddress").val(),
            accountCNY: $("#bank-name").val(),
            bankNameCNY: $("#accountcny").val(),
            itemJson: itemJson,
            remark: $("#remark").val()
        }
    }


    // 已选客户的表格
    function prepaidAddTable() {
        var prepaidAddData = [{}];
        var $chargesTable = $("#charges-table");
        $chargesTable.bootstrapTable({
            undefinedText: "",
            locale: "zh-CN",
            data: prepaidAddData,
            pagination: true,
            pageSize: 60, // 一页显示数据条数
            pageList: [60, 100, 200],
            pageNumber: 1, // 首页页码
            striped: true,
            clickToSelect: true,
            columns: [{
                field: 'costType',
                title: '费用类型',
                formatter: function (value,row) {
                    if(!value || value == 1){
                        row.costType = 1;
                        return "<select class='costtype-select'>" +
                            "<option value='1' selected='true'>广告费</option>" +
                            "<option value='2'>赠品费用</option></select>";
                    }else if(value == 2){
                        return "<select class='costtype-select'>" +
                            "<option value='1'>广告费</option>" +
                            "<option value='2' selected='true'>赠品费用</option></select>";
                    }
                },
                events: {
                    'change .costtype-select': function (e, value, row, index) {
                        var valueSelected = $(this).val();
                        $chargesTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'costType',
                            value: valueSelected
                        })
                    }
                }
            }, {
                field: 'currencyCode',
                title: '币别',
                formatter: function (value,row) {
                    if(!value || value === 'CNY'){
                        row['currencyCode'] = 'CNY';
                        return "<select class='currency-select'>" +
                            "<option value='CNY' selected='selected'>CNY</option>" +
                            "<option value='USD'>USD</option>" +
                            "<option value='HKD'>HKD</option></select>";
                    }else if(value === 'USD'){
                        return "<select class='currency-select'>" +
                            "<option value='CNY'>CNY</option>" +
                            "<option value='USD' selected='selected'>USD</option>" +
                            "<option value='HKD'>HKD</option></select>";
                    }else if(value === 'HKD'){
                        return "<select class='currency-select'>" +
                            "<option value='CNY'>CNY</option>" +
                            "<option value='USD'>USD</option>" +
                            "<option value='HKD' selected='selected'>HKD</option></select>";
                    }
                },
                events: {
                    'change .currency-select': function (e, value, row, index) {
                        var valueSelected = $(this).val();
                        $chargesTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'currencyCode',
                            value: valueSelected
                        })
                    }
                }
            }, {
                field: 'exchangeRateDouble',
                title: '汇率',
                formatter: function (value,row) {
                    if(!value){
                        var defaultValue = 0;
                        row['exchangeRateDouble'] = defaultValue;
                        return '<input type="text" class="rate-input" value="' + defaultValue + '"' + '>';
                    }
                    return '<input type="text" class="rate-input" value="' + value + '"' + '>';
                },
                events:{
                    'blur .rate-input':function (e,value,row,index) {
                        var inputValue = $(this).val();
                        $chargesTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'exchangeRateDouble',
                            value: inputValue
                        })
                    }
                }
            }, {
                field: 'applicationAmountDouble',
                title: '申请金额',
                formatter: function (value,row) {
                    if(!value){
                        var defaultValue = 0;
                        row['applicationAmountDouble'] = defaultValue;
                        return '<input type="text" class="amount-input" value="' + defaultValue + '"' + '>';
                    }
                    return '<input type="text" class="amount-input" value="' + value + '"' + '>';
                },
                events:{
                    'blur .amount-input':function (e,value,row,index) {
                        var inputValue = $(this).val();
                        $chargesTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'applicationAmountDouble',
                            value: inputValue
                        })
                    }
                }
            }, {
                field: 'invoiceType',
                title: '发票类型',
                formatter: function (value,row) {
                    if(!value || value == 1){
                        row['invoiceType'] = 1;
                        return "<select class='invoice-select'>" +
                            "<option value='1' selected='selected'>增值税普通发票</option>" +
                            "<option value='2'>赠品费用</option>"
                    }else if(value == 2){
                        return "<select class='invoice-select'>" +
                            "<option value='1'>增值税普通发票</option>" +
                            "<option value='2' selected='selected'>赠品费用</option>"
                    }
                },
                events: {
                    'change .invoice-select': function (e, value, row, index) {
                        var valueSelected = $(this).val();
                        $chargesTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'invoiceType',
                            value: valueSelected
                        })
                    }
                }
            }, {
                field: 'taxPointDouble',
                title: '税点',
                formatter: function (value,row) {
                    if(!value){
                        var defaultValue = 0;
                        row['taxPointDouble'] = defaultValue;
                        return '<input type="text" class="point-input" value="' + defaultValue + '"' + '>';
                    }
                    return '<input type="text" class="point-input" value="' + value + '"' + '>';
                },
                events:{
                    'blur .point-input':function (e,value,row,index) {
                        var inputValue = $(this).val();
                        console.log(111);
                        $chargesTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'taxPointDouble',
                            value: inputValue
                        })
                    }
                }
            }, {
                field: 'standardCurrencyAmountDouble',
                title: '本位币金额',
                formatter: function (value,row) {
                    if(!value){
                        var defaultValue = 0;
                        row['standardCurrencyAmountDouble'] = defaultValue;
                        return '<input type="text" class="currency-input" value="' + defaultValue + '"' + '>';
                    }
                    return '<input type="text" class="currency-input" value="' + value + '"' + '>';
                },
                events:{
                    'blur .currency-input':function (e,value,row,index) {
                        var inputValue = $(this).val();
                        $chargesTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'standardCurrencyAmountDouble',
                            value: inputValue
                        })
                    }
                }
            }, {
                field: 'taxSubsidyDouble',
                title: '税差补贴',
                formatter: function (value,row) {
                    if(!value){
                        var defaultValue = 0;
                        row['taxSubsidyDouble'] = defaultValue;
                        return '<input type="text" class="tax-input" value="' + defaultValue + '"' + '>';
                    }
                    return '<input type="text" class="tax-input" value="' + value + '"' + '>';
                },
                events:{
                    'blur .tax-input':function (e,value,row,index) {
                        var inputValue = $(this).val();
                        $chargesTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'taxSubsidyDouble',
                            value: inputValue
                        })
                    }
                }
            }, {
                field: 'isTaxSubsidy',
                title: '税差是否补贴',
                align: 'center',
                formatter: function (value,row) {
                    if(!value){
                        return '<input type="checkbox" class="subsidy-input">'
                    }else{
                        return '<input type="checkbox" class="subsidy-input" checked>'
                    }
                },
                events:{
                    'change .subsidy-input': function (e,value,row,index) {
                        if($(this).is(":checked")){
                            $chargesTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'isTaxSubsidy',
                                value: 1
                            })
                        }else{
                            $chargesTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'isTaxSubsidy',
                                value: 0
                            })
                        }
                    }
                }
            }, {
                field: 'reason',
                title: '申请原因',
                formatter: function (value,row) {
                    if(!value){
                        var defaultValue = "";
                        row['reason'] = defaultValue;
                        return '<input type="text" class="reason-input" value="' + defaultValue + '"' + '>';
                    }else{
                        return '<input type="text" class="reason-input" value="' + value + '"' + '>';
                    }
                },
                events:{
                    'blur .reason-input':function (e,value,row,index) {
                        var inputValue = $(this).val();
                        $chargesTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'reason',
                            value: inputValue
                        })
                    }
                }
            }, {
                title: "操作",
                width:100,
                formatter: function () {
                    return [
                        '<a href="javascript:void(0)" class="prepaid-add" style="width:30px;display: inline-block">新增</a> ',
                        '<a href="javascript:void(0)" class="prepaid-remove" style="width:30px;display: inline-block">删除</a>'
                    ].join("")
                },
                events: {
                    'click .prepaid-add': function (event, value, row, index) {
                        var _row = {
                            "costType": 0,
                            "currencyCode": "CNY",
                            "exchangeRateDouble": 0,
                            "applicationAmountDouble": 0,
                            "invoiceType": 1,
                            "taxPointDouble": 0,
                            "standardCurrencyAmountDouble": 0,
                            "taxSubsidyDouble": 0,
                            "reason": null
                        };
                        var _index = index +1;
                        // var changeRowData ={
                        //
                        // };
                        // $.extend(_row, row, changeRowData);
                        $chargesTable.bootstrapTable("insertRow", {
                            index: _index,
                            row: _row
                        });
                    },
                    'click .prepaid-remove': function (event, value, row, index) {
                        $chargesTable.bootstrapTable('remove', {
                            field: 'costType',
                            values: [row['costType']]
                        });
                    }
                }
            }]
        })
    }
});