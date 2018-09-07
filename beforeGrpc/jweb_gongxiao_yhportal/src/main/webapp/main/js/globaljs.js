;(function ($, window, document) {
    $.extend({
        getfenxiaoURL: function () {
            var fenxiaoURL = "";
            return fenxiaoURL;
        },
        // 范围时间控件
        renderDateRange: function (el1, el2) {
            // layui时间控件渲染
            layui.use(['laydate'], function () {
                var $ = layui.$;
                var laydate = layui.laydate;
                var nowTime = new Date().valueOf();
                var max = null;
                var start = laydate.render({
                    elem: '#' + el1,
                    max: nowTime,
                    btns: ['clear', 'confirm'],
                    done: function (value, date) {
                        endMax = end.config.max;
                        end.config.min = date;
                        end.config.min.month = date.month - 1;
                    }
                });
                var end = laydate.render({
                    elem: '#' + el2,
                    // max: nowTime,
                    done: function (value, date) {
                        if ($.trim(value) == '') {
                            var curDate = new Date();
                            date = {
                                'date': curDate.getDate(),
                                'month': curDate.getMonth() + 1,
                                'year': curDate.getFullYear()
                            };
                        }
                        start.config.max = date;
                        start.config.max.month = date.month - 1;
                    }
                });
            });
        }
    })
}(jQuery, window, document));