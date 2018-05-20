var userId;

function initSession() {
    userId = checkSession();
}

//库存管理
function dxGraph1() {
    var params = {};
    params.userId = userId;

    $.ajax({
        //几个参数需要注意一下
        type: "post",//方法类型
        contentType: "application/json; charset=UTF-8",
        dataType: "json",//预期服务器返回的数据类型
        url: "/store/matrix",//url
        data: JSON.stringify(params),
        success: function (result) {
            console.log(result);//打印服务端返回的数据(调试用)
            if (result.code == "0000") {
                //alert(result.code)
                var obj = result.data;
                initChartA(obj);
            }
        },
        error: function () {
            //alert("用户名或密码不正确！");
            //window.location.href = "/";
        }
    });


}

//卡片状态
function dxGraph2() {
    var params = "userId=" + userId;

    $.ajax({
        //几个参数需要注意一下
        type: "get",//方法类型
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",//预期服务器返回的数据类型
        url: "/sim/index/card",//url
        data: params,
        success: function (result) {
            console.log(result);//打印服务端返回的数据(调试用)
            if (result.code == "0000") {
                //alert(result.code)
                var obj = result.data;

                initChartB(obj);

            }
        },
        error: function () {
            //alert("用户名或密码不正确！");
            //window.location.href = "/";
        }
    });
}

//每日短信流量
function dxGraph3() {
    var params = {};
    params.userId = userId;

    $.ajax({
        //几个参数需要注意一下
        type: "post",//方法类型
        contentType: "application/json; charset=UTF-8",
        dataType: "json",//预期服务器返回的数据类型
        url: "/sms/matrix",//url
        data: JSON.stringify(params),
        success: function (result) {
            console.log(result);//打印服务端返回的数据(调试用)
            if (result.code == "0000") {
                //alert(result.code)
                var obj = result.data;
                initChartC(obj);
            }
        },
        error: function () {
            //alert("用户名或密码不正确！");
            //window.location.href = "/";
        }
    });
}

function initChartC(obj) {
    var optionC =
        {
            title: {
                //text: '未来一周气温变化',
                //subtext: '纯属虚构'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data: ['消耗短信']
            },
            toolbox: {
                show: true,
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: obj.day
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} °C'
                    }
                }
            ],
            series: [
                {
                    name: '短信发送数量',
                    type: 'line',
                    data: obj.num,
                    markPoint: {
                        data: [
                            {type: 'max', name: '最大值'},
                            {type: 'min', name: '最小值'}
                        ]
                    },
                    markLine: {
                        data: [
                            {type: 'average', name: '平均值'}
                        ]
                    }
                }
            ]
        };
    var chartC = echarts.init(document.getElementById('xchart-3'));
    chartC.setOption(optionC);
}

function initChartB(obj) {
    var optionB = {
        title: {
            //text: '某站点用户访问来源',
            //subtext: '纯属虚构',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data: ['正常', '待续费']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
                    }
                },
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        series: [
            {
                name: '数量',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: obj.normal, name: '正常'},
                    {value: obj.arrearage, name: '待续费'}
                ]
            }
        ]
    };
    var chartB = echarts.init(document.getElementById('xchart-2'));
    chartB.setOption(optionB);
}

function initChartA(obj) {
    var optionA = {
        title: {
            //text: '库存：2',
            //subtext: '纯属虚构',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data: ['入库', '出库']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
                    }
                },
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        series: [
            {
                name: '数量',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: obj.in, name: '入库'},
                    {value: obj.out, name: '出库'}
                ]
            }
        ]
    };
    var chartA = echarts.init(document.getElementById('xchart-1'));
    chartA.setOption(optionA);
}




