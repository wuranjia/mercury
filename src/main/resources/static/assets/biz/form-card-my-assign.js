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
                param.supplier = $('#supplier').val();
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
                {
                    "data":
                        function (obj) {
                            return "<input type ='checkbox' name='test' class='icheckbox_minimal' value=" + obj.simId + ">";
                        },
                },
                {"data": "simId", "orderable": false},
                {"data": "iccid", "orderable": false},
                {"data": "supplier", "orderable": false}
                /*{"data": "suitName", "orderable": false},
                {"data": "flowTotal", "orderable": false},
                {"data": "flowUse", "orderable": false},
                {"data": "flowLess", "orderable": false},
                {"data": "smsUse", "orderable": false},
                {
                    "data": function (obj) {
                        return timeFormatYYYYMMdd(obj.limitDate)//update_time是实体类的属性
                    }
                }*/]
        }).api();

        //实现全选功能
        $("#all_checked").click(function () {
            $('[name=test]:checkbox').prop('checked', this.checked);//checked为true时为默认显示的状态
        });

        //实现反选功能
        $("#checkrev").click(function () {
            $('[name=test]:checkbox').each(function () {
                this.checked = !this.checked;
            });
        });

    } catch (e) {
        alert(e);
    }
});

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

function assign() {
    //选中的卡片
    var a = [];
    $('input[name="test"]:checked').each(function () {
        a.push($(this).val());
        // console.log($(this).val());
    });
    console.log(a);

    var assignUserId = $("#userId").val();

    var params = {};
    params.assignUserId = assignUserId;
    params.cardArray = a.toString();
    params.userId = userId;

    $.ajax({
        //几个参数需要注意一下
        type: "post",//方法类型
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",//预期服务器返回的数据类型
        url: "/sim/assign",//url
        data: params,
        success: function (result) {
            console.log(result);//打印服务端返回的数据(调试用)
            if (result.code == "0000") {
                alert("分配成功！");
                query();
            }
        },
        error: function () {
            //alert("用户名或密码不正确！");
            //window.location.href = "/";
        }
    });


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
