var userId;

$(function () {

    userId = checkSession();

    $('#transForm').validator(validator);

    //初始化表格
    var table = $("#orderTable").dataTable({
        language: lang, //提示信息
        autoWidth: false, //禁用自动调整列宽
        stripeClasses: ["odd", "even"], //为奇偶行加上样式，兼容不支持CSS伪类的场合
        processing: true, //隐藏加载提示,自行处理
        serverSide: true, //启用服务器端分页
        searching: false, //禁用原生搜索
        orderMulti: false, //启用多列排序
        order: [], //取消默认排序查询,否则复选框一列会出现小箭头
        renderer: "jquery-ui", //渲染样式：Bootstrap和jquery-ui
        pagingType: "full", //分页样式：simple,simple_numbers,full,full_numbers
        bFilter: false,    //去掉搜索框方法三：这种方法可以
        bLengthChange: false,   //去掉每页显示多少条数据方法
        columnDefs: [{
            "targets": 'nosort', //列的样式名
            "orderable": false //包含上样式名‘nosort'的禁止排序
        }],
        //dom: 'rt<"#pBottom"p>',     // 千万不要把 't' 去除，t 是 table ，必须要有
        fnInitComplete: function () {
            // 此方法，会在表格完全加载完成之后调用，
            // 为了防止异步表格还没有加载完，分页插件还没有生成，导致找不到的问题
            // 美化滚动条
            //niceScrollBar();
            // datatables 分页插件，放到左下角
            $("#orderTable_paginate").addClass('right');
        },
        ajax: function (data, callback, settings) {
            //封装请求参数
            var param = {};
            param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.page = (data.start / data.length) + 1;//当前页码
            param.draw = data.draw;
            param.seller = userId;

            //ajax请求数据
            $.ajax({
                type: "POST",
                url: "/order/sell/list",
                contentType: "application/json; charset=UTF-8",
                cache: false, //禁用缓存
                data: JSON.stringify(param), //传入组装的参数
                dataType: "json",
                success: function (result) {
                    //封装返回数据
                    var returnData = {};
                    //          alert(result);
                    returnData.draw = result.data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                    returnData.recordsTotal = result.data.total;//返回数据全部记录
                    returnData.recordsFiltered = result.data.total;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = result.data.items;//返回的数据列表
                    console.log(returnData);
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    //            alert(returnData);
                    callback(returnData);
                }
            });
        },
        //列表表头字段
        columns: [
            //订单号
            {"data": "id", "orderable": false},
            //产品ID
            {"data": "productName", "orderable": false},
            //订单时间
            {"data": "createdTime", "orderable": false},
            //收件地址
            {"data": "transAddress", "orderable": false},
            //油费
            {
                "data":
                    function (obj) {
                        return feeFormat(obj.transFee);
                    }
            },
            //支付金额
            {
                "data": function (obj) {
                    return feeFormat(obj.total);
                }
            },
            //订单状态
            {
                "data": function (obj) {
                    return orderStatusFormat(obj.status);
                }
            },
            //处理状态
            {"data": "transStatus", "orderable": false},
            //备注
            {"data": "memo", "orderable": false},
            {
                "data": function (obj) {
                    if (obj.status == 20) {
                        return '<div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs">'
                            + '<a class="am-btn am-btn-default am-btn-xs am-text-secondary" href="javascript:showBuyWindow(\''
                            + obj.id + '\',\'' + obj.transAddress
                            + '\',\'' + obj.transPhone
                            + '\',\'' + obj.transPerson
                            + '\',\'' + obj.transNum
                            + '\',\'' + obj.transStatus + '\');">' +
                            '<span class="am-icon-pencil-square-o">发货</span></a>' + '</div></div>';
                    } else {
                        return '';
                    }
                },
                "className": "center"
            }
        ]
    }).api();

    function feeFormat(fee) {
        return fee + "元";
    }
});


function showBuyWindow(id, transAddress, transPhone, transPerson, num, status) {
    //填值
    $('#transAddress').val(transAddress);
    $('#transPhone').val(transPhone);
    $('#transPerson').val(transPerson);
    $('#transNum').val(num);


    //事件
    $('#trans').modal({
        closeOnConfirm: false,
        onConfirm: function () {
            var transNum = $('#transNum').val();

            if ($('#transForm').validator('isFormValid')) {
                // 請求後台
                var req = {};
                req.orderId = id;
                req.transNum = transNum;
                req.transAddress = transAddress;
                req.transPhone = transPhone;
                req.transPerson = transPerson;
                req.transStatus = status;
                $.ajax({
                    //几个参数需要注意一下
                    type: "post",//方法类型
                    contentType: "application/json; charset=UTF-8",
                    dataType: "json",//预期服务器返回的数据类型
                    url: "/order/trans/edit",//url
                    data: JSON.stringify(req),
                    success: function (result) {
                        console.log(result);//打印服务端返回的数据(调试用)
                        if (result.code == "0000") {
                            //alert(result.code)
                            alert("确认成功");
                            // 验证成功的逻辑
                            $('#trans').modal('close');
                            //$('#orderTable').DataTable().ajax.reload();
                            window.location.reload();
                        } else {
                            alert("失败");
                        }
                    },
                    error: function () {
                        //alert("用户名或密码不正确！");
                        //window.location.href = "/";
                    }
                });


            }
        },
        onCancel: function () {
        }
    });
}