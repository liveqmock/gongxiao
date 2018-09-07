$(function() {

    // 全局接口
    var URL = $.getfenxiaoURL();
    // 订单进行中
    function orderStatus(el) {
        $(el).addClass("line-cur").prev().addClass("line-prev").end().prevAll().addClass("line-done");
        $(el).nextAll().children("p").hide();
    }
    // 转换成两位数
    function checkTime(i) {
        return i < 10 ? "0" + i : i;
    }
    // 时间轴里的时间转换
    function checkDate(date) {
        if (date) {
            var tempDate = new Date(date);
            var lineDate1 = tempDate.getFullYear() + "-" + checkTime((tempDate.getMonth() + 1)) + "-" + checkTime(tempDate.getDate());
            var lineDate2 = checkTime(tempDate.getHours()) + ":" + checkTime(tempDate.getMinutes()) + ":" + checkTime(tempDate.getSeconds());
            var lineDate = lineDate1 + "<br/>" + lineDate2;
            return lineDate;
        } else {
            return "";
        }
    }
    // 获得详情数据
    function getDetailInfo() {
        // 从退货管理处拿到采购退货单号
        var getInfoFromManagement = $.getUrlParam("purchaseReturnedOrderNo");
        // 退货单详情表格
        var $reviewTable = $("#reviewtable");
        // 操作日志表格
        var $reviewLog = $("#reviewlog");
        // 品牌退货及物流信息
        var $logisticsInfos = $("#returnedtable-info").find("b");
        // 相关单据信息
        var $billinfos = $("#billinfo").find("span");
        $.get(URL+"/purchaseReturn/getPurchaseReturnDetail", {
            purchaseReturnedOrderNo: getInfoFromManagement
        }, function(res, status, xhr) {
            if (res.returnCode == 0) {
                var detailData = res.data;
                // 退货单号
                var detailOrderNo = detailData.purchaseReturnedOrderNo;
                // 退货状态
                var returnedStatusDetail = detailData.returnedOrderStatus;
                // 退单创建时间
                var returnedCreateTime = $.transformTime(detailData.createTime, "seconds");
                // 退货单详情(表格)
                var returnedDetailTable = detailData.productReturnInfoList;
                // 操作日志信息表格
                var operateInfo = detailData.operateHistoryRecordList;
                // 运费承担方(特殊处理后插入数组中)
                var bearMan;
                if (detailData.freightPaidBy == 1) {
                    bearMan = "越海"
                } else if (detailData.freightPaidBy == 2) {
                    bearMan = "品牌商"
                } else if (detailData.freightPaidBy == 2) {
                    bearMan = "经销商"
                }
                var freightPaid = bearMan + " " + detailData.freight + "元";
                // 品牌退货及物流信息
                // 收货人，收货人地址，详细地址，手机，收货公司名称，运输方式，退回物流公司，物流运单号，发货仓库，运费承担方
                var arrLogisticsInfo = [detailData.recipientName, detailData.recipientAddress, detailData.recipientDetailAddress, detailData.recipientMobile, detailData.recipientCompanyName, detailData.shippingMode, detailData.logisticsCompany, detailData.logisticsOrderNo, detailData.warehouseName, freightPaid];
                // 退货单号
                $("#ordernumber").html(detailOrderNo);
                // var returnedStatusDetail = 3;
                var returnedStatusTextDetail;
                if (returnedStatusDetail == 0) {
                    returnedStatusTextDetail = "已提交";
                } else if (returnedStatusDetail == 1) {
                    returnedStatusTextDetail = "出库中";
                } else if (returnedStatusDetail == 2) {
                    returnedStatusTextDetail = "出库完成";
                } else if (returnedStatusDetail == 3) {
                    returnedStatusTextDetail = "已签收";
                }
                // 相关单据信息
                // 退货单类型
                var returnedGoodsType;
                if (detailData.returnType == 1) {
                    returnedGoodsType = "采购退货"
                } else if (detailData.returnType == 2) {
                    returnedGoodsType = "采购换货"
                }
                // 退货编号，发货单编号，退货单状态，项目名称，退货单类型，客户名称，退货单创建人，状态更新时间
                var arrBillInfos = [detailData.purchaseReturnedOrderNo, detailData.outboundOrderNumber, returnedStatusTextDetail, detailData.projectName, returnedGoodsType, "", detailData.createdByName, detailData.lastUpdateTime]
                // 退货状态
                $("#orderstatus").html(returnedStatusTextDetail);
                // 根据状态值来确定时间线
                // 将操作表格日志中的时间取出来
                var arrDetailStatusTime = [];
                $.each(operateInfo, function(index, operateInfoObj) {
                    arrDetailStatusTime[operateInfoObj.operateStatus] = checkDate(operateInfoObj.operateTime);
                })
                if (returnedStatusDetail == 0) {
                    // 退货单已提交
                    var el = ".line-pro" + returnedStatusDetail;
                    $(el).addClass("line-startend").nextAll().children("p").hide();
                    var infoTime = arrDetailStatusTime[0]
                    $(el).children("p").html(infoTime);
                } else {
                    var el = ".line-pro" + returnedStatusDetail;
                    orderStatus(el)
                    for (var i = 0; i <= returnedStatusDetail; i++) {
                        (function(j) {
                            var el2 = ".line-pro" + j;
                            $(el2).children("p").html(arrDetailStatusTime[j])
                        }(i));
                    }
                }
                // 退单创建时间
                $("#createtime").html(returnedCreateTime);
                // 退货单详情(表格)
                renderDetailTable();
                $reviewTable.bootstrapTable('load', returnedDetailTable);
                // 品牌退货及物流信息
                $logisticsInfos.each(function(i) {
                    $(this).html(arrLogisticsInfo[i])
                })
                // 相关单据信息
                $billinfos.each(function(i) {
                    $(this).html(arrBillInfos[i])
                })
                // 操作日志信息表格
                renderLogTable();
                $reviewLog.bootstrapTable('load', operateInfo);
            }
        }, "json")
    };
    // 初始化
    function detailInit() {
        // 初始化退货状态
        $(".line-pro").removeClass("line-done line-prev line-cur");
        getDetailInfo();
    }
    detailInit();
    //退货单详情表格
    function renderDetailTable() {
        var $reviewTable = $("#reviewtable");
        var returnViewData = [{}];
        $reviewTable.bootstrapTable({
            undefinedText: '',
            locale: 'zh-CN',
            striped: true,
            data: returnViewData,
            columns: [{
                field: 'productName',
                title: '货品名称',
            }, {
                field: 'productCode',
                title: '型号',
                formatter: function(value, row, index) {
                    if (!value) {
                        return '未知'
                    }
                    return value
                }
            }, {
                field: 'currencyName',
                title: '币种'
            }, {
                field: 'guidePrice',
                title: '指导价',
            }, {
                field: 'purchasePrice',
                title: '进货价',
            }, {
                field: 'returnAmount',
                title: '商品退款金额',
                formatter: function(value, row, index) {
                    if (!value) {
                        return 0;
                    } else {
                        var val = value;
                        val = val.toFixed(2)
                        return val + "%";
                    }
                }
            }, {
                field: 'returnReferNumber',
                title: '退货数量',
            }, {
                field: 'returnNumber',
                title: '原始数量',
                formatter: function(value, row, index) {
                    if (!value) {
                        return 0;
                    } else {
                        var val = value;
                        val = val.toFixed(2)
                        return val + "%";
                    }
                }
            }, {
                field: 'warehouseActualReceiptQuantity',
                title: '仓库实收',
                formatter: function(value, row, index) {
                    if (!value) {
                        return ''
                    } else {
                        var nondefective = value;
                        nondefective = nondefective.split(',')
                        return '良品: ' + nondefective[0] + '<br/>' + '残次品: ' + nondefective[1];
                    }
                }
            }, {
                field: 'returnReason',
                title: '退货原因',
            }]
        })
    };
    //操作日志表格
    function renderLogTable() {
        var $reviewLog = $("#reviewlog");
        var logData = [{}];
        $reviewLog.bootstrapTable({
            striped: true,
            data: logData,
            columns: [{
                field: 'operateStatus',
                title: '状态',
                align: 'center'
            }, {
                field: 'operateFunction',
                title: '操作功能',
                align: 'center'
            }, {
                field: 'operateTime',
                title: '操作时间',
                align: 'center',
                formatter: function(value, row, index) {
                    if (!value) {
                        return ""
                    } else {
                        return $.transformTime(value, "seconds")
                    }
                }
            }, {
                field: 'operateName',
                title: '操作人',
                align: 'center'
            }, ]
        })
    };
})