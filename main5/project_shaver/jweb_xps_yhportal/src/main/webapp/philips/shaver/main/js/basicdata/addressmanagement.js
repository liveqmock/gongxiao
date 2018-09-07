$(function () {

    managementInit();

    function managementInit() {
        //获取全局地址
        var URL = $.getfenxiaoURL();
        renderPageElement();
        //绑定事件
        bindEvent(URL);
        //初始化
        renderTable(URL);
    }

    function renderPageElement() {
        $("#auditstatus").select2({
            minimumResultsForSearch: 6
        });
    }


    //前端绑定事件
    function bindEvent(URL) {
        //1) 查询点击事件
        $("#yhquery-btn").click(function () {
            $("#yhtable").bootstrapTable("refreshOptions", {pageNumber: 1});
        });
    }

    function audit(URL, row) {
        var addressId = row.addressId;
        $.get(URL + "/shippingAddress/auditAddress", {"addressId": addressId}, function (res, status, xhr) {
            if (res.returnCode === 0) {
                $("#yhtable").bootstrapTable("refreshOptions", {pageNumber: 1});
            }
        }, "json")
    }


    function reject(URL, row) {
        var addressId = row.addressId;
        $.get(URL + "/shippingAddress/rejectAddress", {"addressId": addressId}, function (res, status, xhr) {
            if (res.returnCode === 0) {
                $("#yhtable").bootstrapTable("refreshOptions", {pageNumber: 1});
            }
        }, "json")
    }


// 渲染采购单管理表格数据
    function renderTable(URL) {
        $("#yhtable").bootstrapTable({
                method: 'get',
                contentType: "application/x-www-form-urlencoded",
                url: URL + "/shippingAddress/selectNeedAuditAddressList",
                queryParamsType: '',
                silent: true,
                undefinedText: '',
                striped: true,
                dataField: 'data',
                pagination: true,
                pageSize: 60, // 一页显示数据条数
                pageList: [60, 100, 200],
                pageNumber: 1, // 首页页码
                sidePagination: "server",
                queryParams: getQueryParams,
                responseHandler: responseHandler,// 请求数据成功后，渲染表格前的方法
                columns: [{
                    checkbox: true
                }, {
                    field: 'distributorName',
                    title: '客户名称'
                }, {
                    field: 'receiver',
                    title: '收货人'
                }, {
                    field: 'receiver',
                    title: '省市区',
                    formatter: function (value, row) {
                        if (value) {
                            return row.provinceName + row.cityName + row.districtName
                        } else {
                            return ""
                        }
                    }
                }, {
                    field: 'streetAddress',
                    title: '街道地址'
                }, {
                    field: 'postCode',
                    title: '邮编'
                }, {
                    field: 'phoneNumber',
                    title: '电话'
                }, {
                    field: 'auditStatus',
                    title: '状态'
                }, {
                    title: '操作',
                    width: 140,
                    clickToSelect: false,
                    formatter: function (value, row, index) {
                        if (!row) {
                            return ''
                        } else {
                            if (row.auditStatus == '已提交') {
                                return [
                                    '<a class="audit btn btn-success" href="javascript:void(0)">', '审核 ', '</a> ',
                                    ' <a class="refuse btn btn-warning" href="javascript:void(0)">', ' 驳回', '</a>'
                                ].join('');
                            }
                        }
                    },
                    events: {
                        'click .audit': function (event, value, row) {
                            audit(URL, row);
                        },
                        'click .refuse': function (event, value, row) {
                            reject(URL, row);
                        }
                    }

                }, {
                    width: '80px',
                    clickToSelect: false,
                    class: 'address-default',
                    formatter: function (value, row) {
                        if (row['isDefaultAddress'] === 1) {
                            return '<span class="note">' + "默认地址" + '</span>'
                        }
                    }
                }
                ]
            }
        )
    }

    //遮罩层查询参数
    function getQueryParams(params) {
        var addressParam = {
            projectId: "",
            distributorAccount: $("#distributorId").val(),
            distributorName: $("#distributorName").val(),
            auditStatus: $("#auditstatus").val(),
            // 首页页码
            pageNumber: params.pageNumber,
            // 数据条数
            pageSize: params.pageSize
        };
        return addressParam;
    }

    // 返回成功渲染表格
    function responseHandler(res) {
        if (res.returnCode === 0 && res.data.list.length !== 0 && res.data.total !== 0 && typeof res.data.total !== 'undefined') {
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

});