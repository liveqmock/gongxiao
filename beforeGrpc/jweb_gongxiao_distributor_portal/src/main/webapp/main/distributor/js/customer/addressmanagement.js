/**
 * @authors {len} (http://csshack.org)
 * @date    2018/4/10
 */
$(function () {
    var URL = $.getcustomerURL();
    addressManagementInit();
    // 页面元素
    // 页面元素
    function getPageEle() {
        // 地区选择
        var $areaList = $("#arealist");
        // 省市区
        var $yhareaInfo = $("#yharea-info");
        // 详细地址
        var $addressDetail = $("#address-detail");
        // 收货人姓名
        var $receiver = $("#receiver");
        // 手机号码
        var $phoneNumber = $("#phone-number");
        // 电话号码
        var $telNumber = $("#tel-number");
        // 邮政编码
        var $postalCode = $("#postalcode");
        // 提交按钮
        var $submitBtn = $("#submit-btn");
        var $defaultAddress = $("#default-address");
        var pageElem = {
            $areaList: $areaList,
            $yhareaInfo: $yhareaInfo,
            $addressDetail: $addressDetail,
            $receiver: $receiver,
            $phoneNumber: $phoneNumber,
            $telNumber: $telNumber,
            $postalCode: $postalCode,
            $defaultAddress: $defaultAddress,
            $submitBtn: $submitBtn
        };

        return pageElem
    }


    function addressManagementInit() {
        var URL = $.getcustomerURL();
        var $addressDetailElem = getPageEle();
        var $addressTable = $("#address-table");
        renderTable(URL);
        // $addressDetailElem.$yhareaInfo.citypicker();
        $.fn.regionpicker.setDefaults({
            store: window['ChineseRegions']
            // getStreetsFun(parentRegion, callback) {
            //     $.getJSON(`data/${parentRegion.code}.json`, function (data, status) {
            //         callback(data);
            //     });
            // }
        });


        $('#yharea-info').regionpicker({
            // level: 'street',
            placeholder: '请选择 省/市/县区/街道'
        });

        $("#address-table tbody").on("mouseover","tr",function () {
            var _index = $(this).index();
            $(".note").eq(_index).removeClass("note-hide");
        }).on("mouseout","tr",function () {
            $(".note").addClass("note-hide");
        })



        var isAjaxFlag = false;
        // 地址提交
        $addressDetailElem.$submitBtn.click(function () {
            var provinceCode = $addressDetailElem.$yhareaInfo.getRegionPicker().getCode("province");
            var provinceText = $addressDetailElem.$yhareaInfo.getRegionPicker().getVal("province");
            var cityCode = $addressDetailElem.$yhareaInfo.getRegionPicker().getCode("city");
            var cityText = $addressDetailElem.$yhareaInfo.getRegionPicker().getVal("city");
            var districtCode = $addressDetailElem.$yhareaInfo.getRegionPicker().getCode("district");
            var districtText = $addressDetailElem.$yhareaInfo.getRegionPicker().getVal("district");
            var isCheckboxStatus = false;
            if(isAjaxFlag){
                return false;
            }
            isAjaxFlag = true;
            if ($("#default-address").prop("ckecked")) {
                isCheckboxStatus = true;
            }
            var submitParams = {
                // distributorId:,
                // distributorName:,
                receiver: $addressDetailElem.$receiver.val(),
                provinceId: provinceCode,
                provinceName: provinceText,
                cityId: cityCode,
                cityName: cityText,
                districtId: districtCode,
                districtName: districtText,
                streetAddress: $addressDetailElem.$addressDetail.val(),
                phoneNumber: $addressDetailElem.$phoneNumber.val(),
                telephone: $addressDetailElem.$telNumber.val(),
                postCode: $addressDetailElem.$postalCode.val(),
                boolean: isCheckboxStatus
            };
            layer.load(2);
            $.ajax({
                type: "GET",
                url: URL + "/address/insertAddress",
                cache: false,
                dataType: 'json',
                data: submitParams,
                async: false,
                beforeSend: function () {
                    $addressDetailElem.$submitBtn.attr("disabled", true);
                },
                complete: function () {
                    $addressDetailElem.$submitBtn.attr("disabled",false);
                },
                success: function (res) {
                    if (res.returnCode === 0) {
                        $addressTable.bootstrapTable('refresh', {url: URL + "/address/selectAllAddress"});
                        setTimeout(function(){
                            layer.closeAll('loading');
                        },1500);
                    }else{
                        layer.msg(res.message);
                        setTimeout(function(){
                            layer.closeAll('loading');
                            isAjaxFlag = false;
                        },2000);
                    }
                },
                error: function (err) {
                    isAjaxFlag = false;
                }
            })
        });
    }


    function getQueryParams(params) {
        return {
            // "distributorId": 2
        }
    }

    // 返回成功渲染表格
    function responseHandler(res) {
        if (res.returnCode === 0 && res['data']['length'] !== 0) {
            var totalNumber = res['data']['length'];
            return {
                total: totalNumber, // 总页数,前面的key必须为"total"
                data: res['data'] // 行数据，前面的key要与之前设置的dataField的值一致.
            }
        }else{
            return {
                total: 0, // 总页数,前面的key必须为"total"
                data: [{}] // 行数据，前面的key要与之前设置的dataField的值一致.
            }
        }
    }

    function renderTable(URL) {
        var $addressTable = $("#address-table");
        $addressTable.bootstrapTable({
            method: 'get',
            contentType: "application/x-www-form-urlencoded",
            url: URL + "/address/selectAllAddress",
            queryParamsType: '',
            silent: true,
            undefinedText: '',
            striped: true,
            dataField: 'data',
            sidePagination: "server",
            queryParams: getQueryParams,
            responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法
            columns: [{
                checkbox: true
            }, {
                field: 'receiver',
                title: '收货人',
                align: 'center',
                halign: 'center'
            }, {
                field: 'provinceName',
                title: '所在地区',
                formatter: function (value, row, index) {
                    if (!value) {
                        return ''
                    } else {
                        return row.provinceName + row.cityName + row.districtName;
                    }
                }
            }, {
                field: 'streetAddress',
                title: '详细地址',
                align: 'center',
                halign: 'center'
            }, {
                field: 'phoneNumber',
                title: '电话/手机',
                align: 'center',
                halign: 'center'
            }, {
                field: 'postCode',
                title: '邮编',
                align: 'center',
                halign: 'center'
            }, {
                field: 'auditStatus',
                title: '状态',
                formatter: function (value, row, index) {
                    if (!value) {
                        return ''
                    } else {
                        if (row.auditStatus == 1) {
                            return "未审核";
                        } else if (row.auditStatus == 2) {
                            return "已审核";
                        }
                    }
                }
            }, {
                title: '操作',
                align: 'center',
                halign: 'center',
                clickToSelect: false,
                formatter: function (value, row, index) {
                    // return [
                    //     '<a href="javascript:void(0);" class="address-revise">' + '修改' + '</a>',
                    //     " | ",
                    //     '<a href="javascript:void(0);" class="address-delete">' + '删除' + '</a>'
                    // ].join("");
                    if(row['receiver']){
                        return '<a href="javascript:void(0);" class="address-delete">' + '删除' + '</a>'
                    }else{
                        return ""
                    }
                },
                events: addressEvent = {
                    // "click .address-revise": function (event, value, row, index) {
                    //     console.log(1)
                    // },
                    "click .address-delete": function (event, value, row, index) {
                        $addressTable.bootstrapTable("remove", {
                            field: "productCode",
                            values: row.productCode
                        });
                        $.get(URL + "/address/deleteAddress", {
                            "distributorId": row.distributorId,
                            "addressId": row.addressId
                        }, function (res, status, xhr) {
                            if (res.returnCode == 0) {
                                layer.msg("删除记录成功");
                                $addressTable.bootstrapTable('refresh', {url: URL + "/address/selectAllAddress"});
                            }else{
                                layer.msg(res.message);
                            }
                        }, "json");
                    }
                }
            }, {
                align: 'center',
                halign: 'center',
                width: '80px',
                clickToSelect: false,
                class: 'address-default',
                formatter: function () {
                    return '<a class="note note-hide" href="javascript:void(0);">' + "默认地址" + '</a>'
                },
                events:{
                    'click .note': function (e,value,row,index) {
                        var defaultAddress = row.addressId;
                        console.log(defaultAddress);
                        $.post(URL+'/address/setDefaultAddress',{
                            addressId: defaultAddress
                        },function (res) {
                            if(res.returnCode === 0){
                                layer.msg("设置默认地址成功");
                            }else{
                                layer.msg(res.message);
                            }
                        },"json")
                    }
                }
            }]
        })
    }
});