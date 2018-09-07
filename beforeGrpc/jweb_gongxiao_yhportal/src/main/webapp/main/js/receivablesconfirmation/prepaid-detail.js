/**
 * @authors {len} (http://csshack.org)
 * @date    2018/5/9
 */
 $(function () {
     prepaidDetailInit();

     function prepaidDetailInit() {
         var URL = $.getfenxiaoURL();
         var urlParams = $.getUrlParam("prepaidId");
         var $pageElement = getPageElement();

         var detailParams = {
             id: urlParams
         };
         $.get(URL+"/prepaid/info/get",detailParams,function (res) {
             if(res.returnCode === 0){
                 var resData = res.data;
                 var resTableData = resData.itemList;
                 var projectName = resData.projectName;
                 $pageElement.$projectText.val(projectName);
                 // 供应商，结算方式，付款方，结算单号，收款方，业务日期，税号，联系方式，地址，CNY开户行，CNY帐号
                 var address = resData.provinceName + resData.cityName+ resData.districtName;
                 var arrInfo = [resData.supplierName,"",resData.payer,resData.settlementNo,resData.receivables,resData.dateBusiness,resData.taxNo,resData.contactInfo,address,resData.bankNameCNY,resData.accountCNY];
                 // 基本信息
                 $pageElement.$prepaidinfo.each(function (index) {
                     $(this).val(arrInfo[index]);
                 });
                 tableRender();
                 $pageElement.$chargesTable.bootstrapTable("load",resTableData);
                 $pageElement.$remark.val(resData.remark);
             }
         },"json")
     }

    function getPageElement() {
        // 基本信息
        var $prepaidinfo = $("#prepaidinfo").find("input");
        // 费用明细
        var $chargesTable = $("#charges-table");
        var $projectText = $("#projecttext");
        var $remark = $("#remark");
        return {
            $prepaidinfo:$prepaidinfo,
            $chargesTable:$chargesTable,
            $projectText:$projectText,
            $remark:$remark
        }
    }

     // 表格渲染
     function tableRender() {
         var $chargesTable = $("#charges-table");
         var prepaidAddData = [{}];
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
                 formatter: function (value) {
                     if (value === 1) {
                         return "广告费"
                     } else if (value === 2) {
                         return "赠品费用"
                     }
                 }
             }, {
                 field: 'currencyCode',
                 title: '币别'
             }, {
                 field: 'exchangeRate',
                 title: '汇率'
             }, {
                 field: 'applicationAmount',
                 title: '申请金额'
             }, {
                 field: 'invoiceType',
                 title: '发票类型',
                 formatter: function (value) {
                     if (value === "1") {
                         return "增值税普通发票"
                     } else if (value === "2") {
                         return "赠品费用"
                     }
                 }
             }, {
                 field: 'taxPoint',
                 title: '税点'
             }, {
                 field: 'standardCurrencyAmount',
                 title: '本位币金额'
             }, {
                 field: 'taxSubsidy',
                 title: '税差补贴'
             }, {
                 field: 'isTaxSubsidy',
                 title: '税差是否补贴',
                 align: 'center',
                 valign: 'middle'
             }, {
                 field: 'reason',
                 title: '申请原因'
             }]
         })
     }
 });