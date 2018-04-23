var userId;

$(document).ready(function () {
    userId = $.cookie('userId');
    if (isNull(userId) != "暂无") {
        var params = "userId=" + userId;
        $.ajax({
            //几个参数需要注意一下
            type: "get",//方法类型
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            dataType: "json",//预期服务器返回的数据类型
            url: "/user/login/valid",//url
            data: params,
            success: function (result) {
                console.log(result);//打印服务端返回的数据(调试用)
                if (result.code == "0000") {
                    //alert(result.code)
                    var flag = result.data;
                    if (flag) {
                        SetCookie("userId", userId);
                    } else {
                        window.location.href = "/";
                    }
                } else {
                    //alert(result.msg);
                    window.location.href = "/";
                }
            },
            error: function () {
                //alert("用户名或密码不正确！");
                //window.location.href = "/";
            }
        });
    } else {
        window.location.href = "/";
    }
    var params = "userId=" + userId;
    $.ajax({
        //几个参数需要注意一下
        type: "get",//方法类型
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",//预期服务器返回的数据类型
        url: "/sim/matrix",//url
        data: params,
        success: function (result) {
            console.log(result);//打印服务端返回的数据(调试用)
            if (result.code == "0000") {
                //alert(result.code)
                setFormData(result.data);
            } else {
            }
        },
        error: function () {
        }
    });


    try {

        //提示信息
        var lang = {
            "sProcessing": "处理中...",
            "sLengthMenu": "每页 _MENU_ 项",
            "sZeroRecords": "没有匹配结果",
            "sInfo": '<span class="  am-text-sm">当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项</span>',
            "sInfoEmpty": '<span class=" am-text-sm">当前显示第 0 至 0 项，共 0 项</span>',
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": '<span class="  am-text-sm">首页</span>',
                "sPrevious": '<span class="  am-text-sm">上页</span>',
                "sNext": '<span class="  am-text-sm">下页</span>',
                "sLast": '<span class="  am-text-sm">末页</span>',
                "sJump": '<span class="  am-text-sm">跳转</span>'
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        };

        var lang2 = copy(lang);
        lang2.sInfo = '<span class="  am-text-sm">第 _START_ 至 _END_ 项</span>';
        lang2.sInfoEmpty = '';

        //初始化表格 smsTable
        var smsTable = $("#smsTable").dataTable({
            language: lang2, //提示信息
            autoWidth: false, //禁用自动调整列宽
            stripeClasses: ["odd", "even"], //为奇偶行加上样式，兼容不支持CSS伪类的场合
            processing: true, //隐藏加载提示,自行处理
            serverSide: true, //启用服务器端分页
            searching: false, //禁用原生搜索
            orderMulti: false, //启用多列排序
            order: [], //取消默认排序查询,否则复选框一列会出现小箭头
            renderer: "bootstrap", //渲染样式：Bootstrap和jquery-ui
            pagingType: "simple_numbers", //分页样式：simple,simple_numbers,full,full_numbers
            bFilter: false,    //去掉搜索框方法三：这种方法可以
            bLengthChange: false,   //去掉每页显示多少条数据方法
            columnDefs: [{
                "targets": 'nosort', //列的样式名
                "orderable": false //包含上样式名‘nosort'的禁止排序
            }],
            pageLength: 5,//显示个数table
            ajax: function (data, callback, settings) {
                //封装请求参数
                var param = {};
                param.limit = 5;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                param.draw = data.draw;
                param.userId = null;
                param.sim = $('#phone').val();
                //ajax请求数据
                $.ajax({
                    type: "GET",
                    url: "/sms/list/view",
                    cache: false, //禁用缓存
                    data: param, //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        if (result == 'redirect:/') {
                            window.location.href = "/";
                        }
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
                {
                    "data": function (obj) {
                        return timeFormat(obj.createdTime)//update_time是实体类的属性
                    }
                },
                {
                    "data": function (obj) {
                        return typeFormat(obj.memo)//类型
                    }
                },
                {
                    "data": function (obj) {
                        return statusFormat(obj.memo)//状态
                    }
                },
                {
                    "data": function (obj) {
                        return formatStr(obj.smsContent)//
                    }
                }
            ]
        }).api();
        //此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象


        var simTable = $("#simTable").dataTable({
            language: lang, //提示信息
            autoWidth: false, //禁用自动调整列宽
            stripeClasses: ["odd", "even"], //为奇偶行加上样式，兼容不支持CSS伪类的场合
            processing: true, //隐藏加载提示,自行处理
            serverSide: true, //启用服务器端分页
            searching: false, //禁用原生搜索
            orderMulti: false, //启用多列排序
            order: [], //取消默认排序查询,否则复选框一列会出现小箭头
            renderer: "bootstrap", //渲染样式：Bootstrap和jquery-ui
            pagingType: "simple_numbers", //分页样式：simple,simple_numbers,full,full_numbers
            bFilter: false,    //去掉搜索框方法三：这种方法可以
            bLengthChange: false,   //去掉每页显示多少条数据方法
            columnDefs: [{
                "targets": 'nosort', //列的样式名
                "orderable": false //包含上样式名‘nosort'的禁止排序
            }],
            pageLength: 5,//显示个数table
            ajax: function (data, callback, settings) {
                //封装请求参数
                var param = {};
                param.limit = 5;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                param.draw = data.draw;
                param.userId = userId;
                param.sim = $('#sim').val();
                param.iccid = $('#iccid').val();
                param.supplier = -99;
                //ajax请求数据
                $.ajax({
                    type: "GET",
                    url: "/sim/list",
                    cache: false, //禁用缓存
                    data: param, //传入组装的参数
                    dataType: "json",
                    success: function (result) {

                        if (result == 'redirect:/') {
                            window.location.href = "/";
                        }
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
                {"data": "simId", "orderable": false},
                {"data": "iccid", "orderable": false},
                {"data": "communication", "orderable": false},
                {"data": "suitName", "orderable": false},
                {"data": "flowTotal", "orderable": false},
                {"data": "flowUse", "orderable": false},
                {"data": "flowLess", "orderable": false},
                {"data": "smsUse", "orderable": false},
                {
                    "data": function (obj) {
                        return timeFormatYYYYMMdd(obj.limitDate)//update_time是实体类的属性
                    }
                }]
        }).api();

        $('#simTable tbody').on('click', 'tr', function () {
            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
            } else {
                simTable.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
            }
            var rows = simTable.rows('.selected').data();
            //alert(rows.length + ' row(s) selected');
            //alert(rows[0].communication);
            var obj = rows[0];
            $('#simId').text(obj.simId);
            $('#suitName').text(obj.suitName)
            $('#communication').text(obj.communication);
            $('#iccidInfo').text(obj.iccid);
            $('#imsi').text(obj.imsi);
            $('#open').text(formatOpenInfo(obj.openFlag));
            $('#openDate').text(timeFormatYYYYMMdd(obj.openDate));
            $('#activateStatus').text(formatActiveStatus(obj.activateStatus));
            $('#limitDate').text(timeFormatYYYYMMdd(obj.limitDate));
            $('#smsUse').text(obj.smsUse);
            $('#status').text(formatStatusInfo(obj.status));
            $('#statusDeliver').text(formatStatusDeliver(obj.statusDeliver));
            $('#flowUseMonth').text(obj.flowUseMonth);
            $('#statusOnline').text(formatStatusOnline(obj.statusOnline));
            $('#statusArrearage').text(formatStatusArrearage(obj.statusArrearage));
            $('#phone').val(obj.simId);
        });

    } catch (e) {
        alert(e);
    }
});

function formatStatusArrearage(str) {
    if (str == 1) {
        return "正常";
    } else {
        return "欠费";
    }
}

function formatStatusOnline(str) {
    if (str == 1) {
        return "在线";
    } else {
        return "离线";
    }
}

function formatStatusDeliver(str) {
    if (str == 1) {
        return "开机";
    } else {
        return "关机";
    }
}

function formatStatusInfo(str) {
    if (str == 1) {
        return "正常";
    } else {
        return "不正常";
    }
}

function formatActiveStatus(str) {
    if (str == 1) {
        return "激活";
    } else {
        return "未激活";
    }
}

function formatOpenInfo(str) {
    if (str == 1) {
        return "已开通";
    } else {
        return "未开通";
    }
}

function timeFormatYYYYMMdd(str) {
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        oTime = oYear + '-' + getZf(oMonth) + '-' + getZf(oDay);//最后拼接时间
    return oTime;
}

//获得年月日      得到日期oTime
function timeFormat(str) {
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear + '-' + getZf(oMonth) + '-' + getZf(oDay) + ' ' + getZf(oHour) + ':' + getZf(oMin) + ':' + getZf(oSen);//最后拼接时间
    return oTime;
};

//补0操作
function getZf(num) {
    if (parseInt(num) < 10) {
        num = '0' + num;
    }
    return num;
}

/**
 * 判断是否null
 * @param data
 */
function isNull(data) {
    return (data == "" || data == undefined || data == null) ? "暂无" : data;
}

function SetCookie(name, value) {
    var today = new Date();
    var expires = new Date();
    expires.setTime(today.getTime() + 1000 * 60 * 60 * 24 * 7);
    document.cookie = name + "=" + escape(value) + "; expires=" + expires.toGMTString() + ";path=/";
}

function query() {
    $('#simTable').DataTable().ajax.reload();
}

function querySms() {
    $('#smsTable').DataTable().ajax.reload();
}

function send() {

    var param = {};
    param.receiveIds = $('#phone').val();
    param.smsContent = $('#content').val();
    param.userId = userId;
    $.ajax({
        //几个参数需要注意一下
        type: "POST",//方法类型
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",//预期服务器返回的数据类型
        url: "/sms/send",//url
        data: param,
        success: function (result) {
            console.log(result);//打印服务端返回的数据(调试用)
            if (result.code == 0000) {
                alert("提交成功!");
                // $("#receiveIds").val("");
                // $("#smsContent").val("");
                querySms();
            }
        },
        error: function () {
            alert("异常！");
        }
    });
}


function statusFormat(str) {
    return str;
}

function typeFormat(str) {
    return str;
}

function setFormData(obj) {
    $('#d1').text(obj.d1);
    $('#d2').text(obj.d2);
    $('#d3').text(obj.d3);
    $('#d4').text(obj.d4);
    $('#d5').text(obj.d5);
    $('#d6').text(obj.d6);
    $('#d7').text(obj.d7);
    $('#d8').text(obj.d8);
    $('#d9').text(obj.d9);
    $('#d10').text(obj.d10);
}

function copy(obj) {
    var newobj = {};
    for (var attr in obj) {
        newobj[attr] = obj[attr];
    }
    return newobj;
}

function delCookie(name, value) {
    var today = new Date();
    var expires = new Date();
    expires.setTime(today.setTime(today.getTime() - 10000));
    document.cookie = name + "=" + escape(value) + "; expires=" + expires.toGMTString() + ";path=/";
}

function exit() {
    delCookie("userId", userId);
    window.location.href = "/";
    window.location.href = "/";
}


function formatStr(str) {
    var show = str.replace(/</, '&lt;').replace(/>/, '&gt;');
    if (show.length > 8) {
        return show.substr(0, 7);
    }
}