 $(function () {

    //全局变量

    // 返利比例
    var projectRate = [{}];
    // 遮罩层表格内容变化的状态
    var tableObj = {};
    // 已添加表格的数据
    var alreadyDataList = [];
    managementInit();

    // 控制逻辑
    function managementInit() {
        // 全局接口
        var URL = $.getfenxiaoURL();
        renderDate();
        //下拉框初始化(项目信息)
        renderSelectList(URL);
        //前端事件绑定
        bindFrontEvent();
        //后端事件绑定
        bindBackEvent(URL);
        //渲染表格
        renderTable(URL);
    }

    // 渲染日期组组件
    function renderDate() {
        $("#logistic").select2({
            minimumResultsForSearch: 6
        });
        $("#paidstyle").select2({
            minimumResultsForSearch: 6
        });

        layui.config({
            version: '1515376178709' //为了更新 js 缓存
        });
        layui.use(['laydate'], function () {
            var laydate = layui.laydate;
            laydate.render({
                elem: '#yhbusiness-date',
                format: 'yyyy-MM-dd'
            });
            laydate.render({
                elem: '#yharrival-date',
                format: 'yyyy-MM-dd'
            });
            laydate.render({
                elem: '#yhexpiry-date',
                format: 'yyyy-MM-dd'
            });
        });
    }

    /////////////////////////////////////计算
    // 计算后的金额数量
    function purcahseCalcAmount(resData) {
        var clacAmountEle = {
            //采购指导金额
            purchaseGuideAmount: $("#purchase-guideamount"),
            //采购应付金额
            purchaseShouldPayAmount: $("#purchase-shouldpay"),
            //返利账户余额
            couponRemainMoney: $("#couponmoney"),
            //返利使用金额
            couponAmount: $("#couponamount"),
            //AD使用返利金额
            adCouponAmount: $("#couponpostamount"),
            //AD使用代垫金额
            adPrepaidAmount: $("#prepaidpostamount"),
            //采购优惠金额
            purchasePrivilegeAmount: $("#purchase-privilege"),
            //采购实付金额
            purchaseActualPayAmount: $("#purchase-pay"),
            //代垫账户余额
            prepaidRemainMoney: $("#prepaidamount"),
            //代垫使用金额
            prepaidAmount: $("#purchase-prepaid"),
            //现金返点金额
            returnCash: $("#purchase-returncash")
        };
        clacAmountEle.couponAmount.val(resData.couponAmount);
        clacAmountEle.couponRemainMoney.text(resData.couponRemainMoney);
        clacAmountEle.prepaidAmount.val(resData.prepaidAmount);
        clacAmountEle.prepaidRemainMoney.text(resData.prepaidRemainMoney);
        clacAmountEle.adCouponAmount.val(resData.adCouponAmount);    //AD返利
        clacAmountEle.adPrepaidAmount.val(resData.adPrepaidAmount);//AD代垫
        clacAmountEle.purchaseActualPayAmount.text(resData.purchaseActualPayAmount);
        clacAmountEle.purchaseGuideAmount.text(resData.purchaseGuideAmount);
        clacAmountEle.purchasePrivilegeAmount.text(resData.purchasePrivilegeAmount);
        clacAmountEle.purchaseShouldPayAmount.text(resData.purchaseShouldPayAmount);
        clacAmountEle.returnCash.text(resData.returnCash);
    }

    //计算参数
    function calcProInfo() {
        var productInfo = $("#purchaseaddtable").bootstrapTable('getData');
        productInfo = JSON.stringify(productInfo);
        var couponAmountUse = $("#couponamount").val() || 0;
        var prepaidAmountUse = $("#purchase-prepaid").val() || 0;
        var adCouponAmountUse = $("#couponpostamount").val() || 0;
        var adPrepaidAmountUse = $("#prepaidpostamount").val() || 0;
        var $supplierList = $("#supplierlist");
        var $projectList = $("#projectlist");
        var projectIndex = $projectList.children("option:selected").index();
        return {
            projectId: $projectList.val(),
            supplierCode: $supplierList.val(),
            supplierName: $supplierList.children("option:selected").text(),
            //现金返利折扣，暂填0.01
            cashRate: 0.01,
            //当前项目的返利使用比例
            couponUseReferRate: projectRate[projectIndex].couponUseReferRate,
            //当前项目的代垫使用比例
            prepaidUseReferRate: projectRate[projectIndex].prepaidUseReferRate,
            //月度返利
            monthlyCouponGenerateRate: projectRate[projectIndex].monthlyCouponGenerateRate,
            //季度返利
            quarterlyCouponGenerateRate: projectRate[projectIndex].quarterlyCouponGenerateRate,
            //年度返利
            annualCouponGenerateRate: projectRate[projectIndex].annualCouponGenerateRate,
            //订单yh返利金额
            couponAmountUse: couponAmountUse,
            //订单yh代垫使用额
            prepaidAmountUse: prepaidAmountUse,
            //订单AD返利使用额
            adCouponAmountUse: adCouponAmountUse,
            //订单AD代垫使用额
            adPrepaidAmountUse: adPrepaidAmountUse,
            //货品信息
            productJson: productInfo
        };
    }

    //计算接口
    function couponamountPrepaid(URL, calcStatus) {
        var calcPriceParams = calcProInfo();
        $.extend(calcPriceParams, {
            status: calcStatus
        });
        $.post(URL + "/purchase/order/calculateProductInfo", calcPriceParams, function (res) {
            if (res.returnCode === 0) {
                var resData = res.data;
                var calculateProductInfoList = resData.calculateProductInfoList;
                // 使用返利 couponAmount 使用代垫 prepaidAmount 采购单价 purchasePrice 成本价  couponBasePrice 返利返利单价基数 purchaseDiscount
                $("#purchaseaddtable").bootstrapTable("load", calculateProductInfoList);
                // 金额信息
                purcahseCalcAmount(resData);
            }
        }, "json")
    }

    // 下拉框初始化
    function renderSelectList(URL) {
        // 项目列表
        $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
            .then(function (res) {
                var projectInfo = res.data;
                $.each(projectInfo, function (index, projectItem) {
                    // 项目列表中附带的返利比例
                    projectRate[index] = {
                        //返利使用比列
                        couponUseReferRate: projectItem.couponUseReferRate,
                        couponTotalRate: projectItem.monthlyCouponGenerateRate + projectItem.quarterlyCouponGenerateRate + projectItem.annualCouponGenerateRate,
                        // 代垫使用比列
                        prepaidUseReferRate: projectItem.prepaidUseReferRate,
                        // 月度返利
                        monthlyCouponGenerateRate: projectItem.monthlyCouponGenerateRate,
                        // 季度返利
                        quarterlyCouponGenerateRate: projectItem.quarterlyCouponGenerateRate,
                        // 年度返利
                        annualCouponGenerateRate: projectItem.annualCouponGenerateRate
                    };
                });
            });
        // 获取仓库列表
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
        // 供应商列表
        $.getSelectOptions("supplierlist", URL + "/supplier/selectAllSupplier", "supplierCode", "supplierName");
    }

    //前端事件绑定
    function bindFrontEvent() {
        // 仓库下拉框变化
        $("#warehouselist").change(function () {
            var itemValue = $(this).children("option:selected").text();
            var $shopAddress = $("#shopaddress");
            $shopAddress.val(itemValue);
        });

        // 弹出框添加商品后的确认按钮
        var $yhmaskSubmit = $("#yhmask-submit");
        var $purchaseVariety = $("#purchase-variety");
        $yhmaskSubmit.click(function () {
            // 已经添加好的商品
            var alreadyAddTableData = $("#rightbox-table").bootstrapTable('getData');
            var alreadyProLen = alreadyAddTableData.length;
            if (alreadyProLen !== 0) {
                $("#yhmask").addClass("yhmask-hide");
                $("#couponamount").attr("disabled", false);
                $(".yhpurinput").attr("disabled", false);
                $("#purchase-prepaid").attr("disabled", false);
                $("#purchaseaddtable").bootstrapTable('load', alreadyAddTableData);
                // 添加商品的种类数(个数)
                $purchaseVariety.text(alreadyProLen);
            } else {
                $.renderLayerMessage("请选择商品！");
            }
        });
    }

    //后端事件绑定
    function bindBackEvent(URL) {
        var $couponAmont = $("#couponamount");
        var $purchasePrepaid = $("#purchase-prepaid");
        var $couponPostAmount = $("#couponpostamount");
        var $prepaidPostAmount = $("#prepaidpostamount");
        // 1. 根据对应的项目添加商品
        $("#purchase-addbtn").click(function () {
            var projectId = $("#projectlist").val();
            if (!projectId) {
                return;
            } else {
                $("#yhmask").removeClass("yhmask-hide");
                //添加产品列表
                maskTableRender(URL);
            }
        });
        // 2. blur触发计算事件
        $couponAmont.blur(function () {
            // 计算货品价格
            couponamountPrepaid(URL, 2);
        });

        $purchasePrepaid.blur(function () {
            // 计算货品价格
            couponamountPrepaid(URL, 2);
        });

        $couponPostAmount.blur(function () {
            // 计算货品价格
            couponamountPrepaid(URL, 3);
        });

        $prepaidPostAmount.blur(function () {
            // 计算货品价格
            couponamountPrepaid(URL, 3);
        });


        // 3. 货品查询
        $("#searchProduct").click(function () {
            $("#leftbox-table").bootstrapTable("refreshOptions", {pageNumber: 1});
        });

        //4. 新增采购单接口
        var $purchaseSubmit = $("#purchase-submit");
        $purchaseSubmit.click(function () {
            var itemJson = $("#purchaseaddtable").bootstrapTable('getData');
            var purchaseTableData = itemJson[0]['productCode'];
            var targetJson = JSON.stringify(itemJson);
            var addParams = {

            };

            //验证表单
            verification.emptyShow = false;
            verification.noEmptySelect("supplierlist", "VerificationEmpty", "输入的字符不能为空");
            verification.noEmptySelect("warehouselist", "VerificationEmpty1", "输入的字符不能为空");
            verification.noEmptySelect("logistic", "VerificationEmpty2", "输入的字符不能为空");
            verification.noEmpty("shopaddress", "VerificationEmpty3", "输入的字符不能为空");
            verification.noEmpty("yhbusiness-date", "VerificationEmpty4", "输入的字符不能为空");
            verification.noEmpty("paidstyle", "VerificationEmpty5", "输入的字符不能为空");
            /* verification.noEmpty("yharrival-date","VerificationEmpty6","输入的字符不能为空");
             verification.noEmpty("yhexpiry-date","VerificationEmpty7","输入的字符不能为空");*/
            verification.noEmpty("brandno", "VerificationEmpty8", "输入的字符不能为空");
            verification.noEmpty("orderno", "VerificationEmpty9", "输入的字符不能为空");
            //verification.characterLength("orderno","VerificationEmpty9","输入的字符不能超过5个字符",5);
            if (verification.emptyShow) {
                return false;
            }

            $.renderLoading();

            $.ajax({
                url: URL + "/purchase/order/generatePurchaseOrders",
                type: "post",
                data: addParams,
                dataType: "json",
                success: function (res) {
                    if (res.returnCode === 0) {
                        layui.config({
                            version: '1515376178709' //为了更新 js 缓存
                        });
                        layui.use(['layer'], function () {
                            var layer = layui.layer;
                            layer.load(1, {
                                shade: [0.1, '#fff'] //0.1透明度的白色背景
                            });
                        });
                        $.renderLoading(false);
                        window.location.href = "./purchase-management.html";
                    } else {
                        setTimeout(function () {
                            $.renderLoading(false);
                        }, 2000)
                    }
                },
                error: function (err) {
                    setTimeout(function () {
                        $.renderLoading(false);
                    }, 2000)
                }
            })
        })
    }


    // 添加商品完毕后渲染商品表格
    function renderTable(URL) {
        var tableData = [{}];
        var $purchaseTotalNum = $("#pruchase-totalnum");
        var $purchaseAddTable = $("#purchaseaddtable");
        $purchaseAddTable.bootstrapTable({
            undefinedText: '',
            striped: true,
            data: tableData,
            columns: [{
                field: 'productCode',
                title: '型号'
            }, {
                field: 'productName',
                title: '货品名称'
            }, {
                field: 'guidePrice',
                title: '采购指导价'
            }, {
                field: 'purchaseNumber',
                title: '采购数量',
                formatter: function (value, row) {
                    if (row['productCode']) {
                        if (!value) {
                            var _value = 1;
                            row.purchaseNumber = _value;
                            return '<input type="text" class="yhpurinput purchase-number" value="' + _value + '"' + '/>';
                        } else {
                            return '<input type="text" class="yhpurinput purchase-number" value="' + value + '"' + '/>';
                        }
                    } else {
                        return ""
                    }

                },
                events: {
                    'blur .purchase-number': function (event, value, row, index) {
                        var $val = $(this).val();
                        $purchaseAddTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'purchaseNumber',
                            value: $val
                        });
                        var calcPriceParams = calcProInfo();
                        $.extend(calcPriceParams, {
                            status: 1
                        });
                        // 计算货品价格
                        $.post(URL + "/purchase/order/calculateProductInfo", calcPriceParams, function (res) {
                            if (res.returnCode === 0) {
                                var resData = res.data;
                                var calculateProductInfoList = resData.calculateProductInfoList;
                                // 使用返利 couponAmount 使用代垫 prepaidAmount 采购单价 purchasePrice 成本价  couponBasePrice 返利返利单价基数 purchaseDiscount
                                $purchaseAddTable.bootstrapTable('updateRow', {
                                    index: index,
                                    row: calculateProductInfoList[index]
                                });
                                //金额信息
                                purcahseCalcAmount(resData);
                                //总采购数量
                                var $numberinputs = $(".numberinput");
                                var purchaseSum = 0;
                                $.each($numberinputs, function (i, item) {
                                    purchaseSum += parseInt($(item).val());
                                });
                                $purchaseTotalNum.text(purchaseSum);
                            }
                        }, "json")
                    }
                }
            }, {
                field: 'couponAmount',
                title: '使用返利',
                formatter: function (value, row) {
                    if (!value) {
                        row.couponAmount = 0;
                        return 0
                    } else {
                        row.couponAmount = value;
                        return value;
                    }
                }
            }, {
                field: 'prepaidAmount',
                title: '使用代垫',
                formatter: function (value, row, index) {
                    if (!value) {
                        row.prepaidAmount = 0;
                        return 0
                    } else {
                        row.prepaidAmount = value;
                        return value;
                    }
                }
            }, {
                field: 'couponBasePrice',
                title: '采购价'
            }, {
                field: 'costPrice',
                title: '成本价',
                formatter: function (value, row, index) {
                    if (!value) {
                        row.costPrice = 0;
                        return 0;
                    } else {
                        row.costPrice = value;
                        return value;
                    }
                }
            }, {
                field: 'couponBasePrice',
                title: '进货价'
            }, {
                title: '操作',
                formatter: function () {
                    return '<a class="purchase-delete" href="javascript:void(0)">' + '删除' + '</a>'
                },
                events: {
                    'click .purchase-delete': function (event, value, row) {
                        $purchaseAddTable.bootstrapTable('remove', {
                            field: 'productCode',
                            values: [row.productCode]
                        })
                    }
                }
            }]
        })
    }

    ///////////////////////////////////////////////////
    //遮罩层

    // 遮罩层左边的表格
    function maskTableRender(URL) {
        var $leftboxTable = $("#leftbox-table");
        var $rightboxTable = $("#rightbox-table");
        var $rightTableNumber = $("#right-addnum");
        $leftboxTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/product/getProductInfoList",
            queryParamsType: '',
            silent: true,
            undefinedText: '',
            striped: true,
            dataField: 'data',
            pagination: true,
            pageSize: 6, // 一页显示数据条数
            pageList: [4, 6, 8],
            pageNumber: 1, // 首页页码
            sidePagination: "server",
            queryParams: getQueryParams,
            responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法
            columns: [{
                field: 'productCode',
                title: '货品编码',
                width: '30%'
            }, {
                field: 'productName',
                title: '货品名称',
                width: '50%'
            }, {
                field: 'maskOpera',
                title: '操作',
                width: '20%',
                clickToSelect: false,
                rowAttributes: function (row, index) {
                    return {
                        'rowIdIndex': index
                    }
                },
                formatter: function (value, row) {
                    if (tableObj[row.productCode]) {
                        return '已添加'
                    } else {
                        return '<a class="pur-addbtn purchaseaddbtn" href="javascript:void(0)">' + '添加' + '</a>'
                    }
                },
                events: {
                    'click .pur-addbtn': function (e, value, row, index) {
                        alreadyDataList.push(row);
                        tableObj[row.productCode] = true;
                        if (tableObj[row.productCode]) {
                            $(this).removeClass("pur-addbtn").addClass("puralreadyadd");
                            var alreadyaddNum = alreadyDataList.length;
                            $rightTableNumber.text("(" + alreadyaddNum + ")");
                            alreadyTableRender();
                            $rightboxTable.bootstrapTable('load', alreadyDataList);
                            $leftboxTable.bootstrapTable('updateCell', {
                                index: index,
                                field: 'maskOpera',
                                value: tableObj[row.productCode]
                            });
                        }
                    }
                }
            }]
        })
    }

    // 返回成功渲染表格
    function responseHandler(res) {
        var $leftboxTable = $("#leftbox-table");
        $leftboxTable.bootstrapTable('hideLoading');
        if (res['returnCode'] === 0 && res['data']['list']['length'] !== 0 && res['data']['total'] !== 0 && typeof res['data']['total'] !== 'undefined') {
            return {
                total: res['data']['total'], // 总页数,前面的key必须为"total"
                data: res['data']['list'] // 行数据，前面的key要与之前设置的dataField的值一致.
            }
        } else {
            return {
                total: 0, // 总页数,前面的key必须为"total"
                data: [{}] // 行数据，前面的key要与之前设置的dataField的值一致.
            };
        }
    }

    // 遮罩层查询参数
    function getQueryParams(params) {
        var projectId = $("#projectlist").val();
        var productCode = $("#productCode").val();
        var productName = $("#productName").val();
        return {
            projectId: projectId,
            productCode: productCode,
            productName: productName,
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        }
    }

    // 右边已选择的表格
    function alreadyTableRender() {
        var $leftboxTable = $("#leftbox-table");
        var $rightboxTable = $("#rightbox-table");
        var $rightTableNumber = $("#right-addnum");
        var rightTableData = [{}];
        $rightboxTable.bootstrapTable({
            undefinedText: "",
            clickToSelect: true,
            locale: "zh-CN",
            data: rightTableData,
            striped: true,
            pagination: true,
            pageNumber: 1,
            pageSize: 6,
            pageList: [4, 6, 8],
            paginationPreText: "&lt;",
            paginationNextText: "&lt;",
            columns: [{
                field: 'productCode',
                title: '货品编码',
                width: "120px"
            }, {
                field: 'productName',
                title: '货品名称',
                width: "250px"
            }, {
                title: '操作',
                width: "80px",
                clickToSelect: false,
                formatter: function () {
                    return '<a class="alreadyadd-delete purchaseaddbtn" href="javascript:void(0)">' + '删除' + '</a>'
                },
                events: {
                    'click .alreadyadd-delete': function (event, value, row, index) {
                        tableObj[row.productCode] = false;
                        // 删除行
                        $rightboxTable.bootstrapTable('remove', {
                            field: 'productCode',
                            values: [row.productCode]
                        });
                        // 更新遮罩层坐标的表格
                        $leftboxTable.bootstrapTable('updateCell', {
                            index: index,
                            field: 'maskOpera',
                            value: tableObj[row.productCode]
                        });
                        var alreadyaddNum = $rightboxTable.bootstrapTable('getData').length;
                        $rightTableNumber.text("(" + alreadyaddNum + ")");
                    }
                }
            }]
        })
    }

});

