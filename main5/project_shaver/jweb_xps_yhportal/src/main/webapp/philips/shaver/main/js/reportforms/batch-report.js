$(function () {
    var batchReport1 = echarts.init(document.getElementById('batch-report1'));
    var batchReport2 = echarts.init(document.getElementById('batch-report2'));
    var batchReport3 = echarts.init(document.getElementById('batch-report3'));
    var batchReport4 = echarts.init(document.getElementById('batch-report4'));
    var batchReport5 = echarts.init(document.getElementById('batch-report5'));

    function reportMaxRange(arr) {
        var count = 0;
        var max = arr[0];
        for (var i = 1; i < arr.length; i++) {
            var cur = arr[i];
            if (cur - max > 0) {
                max = cur;
            }
        }
        if(max !== 0){
            var a = (max / 10) > 1 || (max / 10) == 1;
            var b = (max / 10) < 10;
            max = 0;
            while (!(a&&b)) {
                max = max / 10;
                count++;
            }
            max = max / 10;
            count++;
            max = Math.ceil(max);
            for (var i = 0; i < count; i++) {
                max *= 10;
            }
            return {max: max, count: count};
        }else{
            return {
                max:5000000,
                count: 500000
            }
        }

    }

    function isAllEqual(array){
        if(array.length > 0){
            return !array.some(function (value,index){
                return value !== array[0]
            })
        }else{
            return true
        }
    }


    var URL = $.getfenxiaoURL();

    var option = {
        title: {
            text: '月度进销存',
            left: '4%'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        // toolbox: {
        //     feature: {
        //         dataView: {show: true, readOnly: false},
        //         magicType: {show: true, type: ['line', 'bar']},
        //         restore: {show: true},
        //         saveAsImage: {show: true}
        //     }
        // },
        legend: {
            data: ['采购', '销售', '库存', '库存周转率']
        },
        xAxis: [
            {
                type: 'category',
                data: [],
                axisPointer: {
                    type: 'shadow'
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '总值',
                min: 0,
                max: 50000000,
                interval: 5000000,
                axisLabel: {
                    formatter: function (value) {
                        return value / 10000 + '(万)'
                    }
                }
            },
            {
                type: 'value',
                name: '库存周转率',
                min: 0,
                max: 1,
                interval: 0.1
            }
        ],
        series: [
            {
                name: '采购',
                type: 'bar',
                data: [],
                barGap: 0
            },
            {
                name: '销售',
                type: 'bar',
                data: [],
                barGap: 0
            },
            {
                name: '库存',
                type: 'bar',
                data: [],
                barGap: 0
            },
            {
                name: '库存周转率',
                type: 'line',
                yAxisIndex: 1,
                data: [],
                barGap: 0
            }
        ]
    };

    var option2 = {
        title: {
            text: '整体库龄',
            left: '2%'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        legend: {
            data: ['总金额', '金额占比情况']
        },
        xAxis: {
            type: 'category',
            data: []
        },
        yAxis: [
            {
                type: 'value',
                name: '总金额',
                min: 500000,
                max: 200000000,
                interval: 20000000,
                axisLabel: {
                    formatter: function (value) {
                        return value / 10000 + '(万)'
                    }
                }
            },
            {
                type: 'value',
                name: '金额占比情况',
                min: 0,
                max: 1,
                interval: 0.1,
                axisLabel: {
                    formatter: '{value}%'
                }
            }
        ],
        series: [
            {
                name: '总金额',
                data: [],
                type: 'bar'
            },
            {
                name: '金额占比情况',
                type: 'line',
                yAxisIndex: 1,
                data: []
            }
        ]
    };

    var option3 = {
        title: {
            text: '180天以上的库存情况',
            left: '2%'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        legend: {
            data: ['金额', '金额占库存比重']
        },
        xAxis: {
            type: 'category',
            data: []
        },
        yAxis: [
            {
                type: 'value',
                name: '金额',
                min: 0,
                max: 50000000,
                interval: 5000000,
                axisLabel: {
                    formatter: function (value) {
                        return value / 10000 + '(万)'
                    }
                }
            },
            {
                type: 'value',
                name: '金额占库存比重',
                min: 0,
                max: 1,
                interval: 0.1,
                axisLabel: {
                    formatter: function (value) {
                        return value * 100 + '%'
                    }
                }
            }
        ],
        series: [
            {
                name: '总金额',
                data: [],
                type: 'bar'
            },
            {
                name: '金额占库存比重',
                type: 'line',
                yAxisIndex: 1,
                data: []
            }
        ]
    };

    var option4 = {
        title: {
            text: '代垫未收金额',
            subtext: '',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: []
        },
        series: [
            {
                name: '代垫未收金额',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    var option5 = {
        title: {
            text: '代垫金额',
            subtext: '',
            left: '2%'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        legend: {
            data: ['代垫应收金额', '代垫实收金额']
        },
        xAxis: {
            type: 'category',
            data: []
        },
        yAxis: [
            {
                type: 'value',
                name: '总额',
                min: 0,
                max: 500000,
                interval: 50000,
                axisLabel: {
                    formatter: function (value) {
                        return value / 10000 + '(万)'
                    }
                }
            },
            {
                type: 'value',
                name: '回收率',
                min: 0,
                max: 3.5,
                interval: 0.5,
                axisLabel: {
                    formatter: '{value}'
                }
            }
        ],
        series: [
            {
                name: '代垫实收金额',
                data: [],
                type: 'bar',
                barGap: 0
            },
            {
                name: '代垫应收金额',
                data: [],
                type: 'bar',
                barGap: 0
            },
            {
                name: '回收率',
                type: 'line',
                yAxisIndex: 1,
                data: []
            }
        ]
    };

    batchReport1.setOption(option);
    batchReport2.setOption(option2);
    batchReport3.setOption(option3);
    batchReport4.setOption(option4);
    batchReport5.setOption(option5);





    function renderData() {
        batchReport1.showLoading();
        $.ajax({
            url: URL + "/report/getPsiData",
            type: 'GET',
            dataType: 'json'
        }).done(function (res) {
            if(res.returnCode === 0){
                batchReport1.setOption(
                    {
                        xAxis: [
                            {
                                data: res['data']['time']
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value',
                                name: '总值',
                                min: 0,
                                max: 5000000,
                                interval: 500000
                            },
                            {
                                type: 'value',
                                name: '库存周转率',
                                max: 1000,
                                interval: 100
                            }
                        ],
                        // 金额和分类
                        series: [{
                            name: '采购',
                            data: res['data']['purchaseAmount']
                        },
                            {
                                name: '销售',
                                data: res['data']['saleAmount']
                            },
                            {
                                name: '库存',
                                data: res['data']['inventoryAmount']
                            },
                            {
                                name: '库存周转率',
                                data: res['data']['inventoryTurnoverRate']
                            }]
                    }
                );
            }
            batchReport1.hideLoading();
        });
    }

    function renderData2(params) {
        batchReport2.showLoading();
        // 异步加载数据
        $.ajax({
            url: URL + "/wholeinventoryAge/getInventoryAge",
            type: 'GET',
            dataType: 'json',
            data: params
        }).done(function (res) {
            if(res.returnCode === 0){
                batchReport2.setOption(
                    {
                        xAxis: {
                            type: 'category',
                            data: res['data']['stage']
                        },
                        // 金额和分类
                        series: [
                            {
                                name: '总金额',
                                data: res['data']['sumOfmoney'],
                                type: 'bar'
                            },
                            {
                                name: '金额占比情况',
                                data: res['data']['proportion'],
                                type: 'line',
                                yAxisIndex: 1
                            }
                        ]
                    }
                );
            }
            batchReport2.hideLoading();
        });
    }

    function renderData3(params) {
        batchReport3.showLoading();
        // 异步加载数据
        $.ajax({
            url: URL + "/inventory180Age/get180Age",
            type: 'GET',
            dataType: 'json',
            data: params
        }).done(function (res) {
            if(res.returnCode === 0){
                batchReport3.setOption(
                    {
                        xAxis: {
                            type: 'category',
                            data: res['data']['productCode']
                        },
                        yAxis: [
                            {
                                type: 'value',
                                name: '总金额',
                                min: 0,
                                max: 5000000,
                                interval: 500000
                            }
                        ],
                        // 金额和分类
                        series: [
                            {
                                name: '总金额',
                                data: res['data']['sumOfmoney'],
                                type: 'bar'
                            },
                            {
                                name: '金额占库存比重',
                                data: res['data']['proportion'],
                                type: 'line',
                                yAxisIndex: 1
                            }
                        ]
                    }
                );
            }
            batchReport3.hideLoading();
        });
    }

    function renderData4(params) {
        batchReport4.showLoading();
        // 异步加载数据
        $.ajax({
            url: URL + "/prepaid/report/leftOne",
            type: 'GET',
            dataType: 'json',
            data: params
        }).done(function (res) {
            if(res.returnCode === 0){
                batchReport4.setOption(
                    {
                        legend: {
                            orient: 'vertical',
                            data: res['data']['dateStrList']
                        },
                        // 金额和分类
                        series: [{
                            name: '代垫未收金额',
                            data: res["data"]["amountList"]
                        }]
                    }
                );
            }
            batchReport4.hideLoading();
        });
    }

    function renderData5(params) {
        batchReport5.showLoading();
        // 异步加载数据
        $.ajax({
            url: URL + "/prepaid/report/rightOne",
            type: 'GET',
            dataType: 'json',
            data: params
        }).done(function (res) {
            if(res.returnCode === 0){

                batchReport5.setOption(
                    {
                        xAxis: {
                            data: res['data'][0]['monthList']
                        },
                        series: [{
                            name: '代垫实收金额',
                            data: res['data'][0]['receiptList']
                        },
                            {
                                name: '代垫应收金额',
                                data: res['data'][0]['receiveAmountList']
                            },
                            {
                                name: '回收率',
                                data: res['data'][0]['rateList']
                            }]
                    }
                );
            }
            batchReport5.hideLoading();
        });
    }

    renderData();
    $.getSelectOptions("projectlist", URL + "/project/getProjectList", "projectId", "projectName")
        .then(function () {
            var params = {
                projectId: $("#projectlist").val()
            };
            renderData2(params);
            renderData3(params);
            renderData4(params);
            renderData5(params)
        });
});
