var userId;
var tree;
var zNodes = [
    /*{ id:1, pId:0, name:"随意勾选 1", open:true},
    { id:11, pId:1, name:"随意勾选 1-1", open:true},
    { id:111, pId:11, name:"随意勾选 1-1-1"},
    { id:112, pId:11, name:"随意勾选 1-1-2"},
    { id:12, pId:1, name:"随意勾选 1-2", open:true},
    { id:121, pId:12, name:"随意勾选 1-2-1"},
    { id:122, pId:12, name:"随意勾选 1-2-2"},
    { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
    { id:21, pId:2, name:"随意勾选 2-1"},
    { id:22, pId:2, name:"随意勾选 2-2", open:true},
    { id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
    { id:222, pId:22, name:"随意勾选 2-2-2"},
    { id:23, pId:2, name:"随意勾选 2-3"}*/
];

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

        //初始化表格
        var table = $("#smsTable").dataTable({
            language: lang, //提示信息
            autoWidth: false, //禁用自动调整列宽
            stripeClasses: ["odd", "even"], //为奇偶行加上样式，兼容不支持CSS伪类的场合
            processing: true, //隐藏加载提示,自行处理
            serverSide: true, //启用服务器端分页
            searching: false, //禁用原生搜索
            orderMulti: false, //启用多列排序
            order: [], //取消默认排序查询,否则复选框一列会出现小箭头
            renderer: "bootstrap", //渲染样式：Bootstrap和jquery-ui
            pagingType: "full", //分页样式：simple,simple_numbers,full,full_numbers
            bFilter: false,    //去掉搜索框方法三：这种方法可以
            bLengthChange: false,   //去掉每页显示多少条数据方法
            columnDefs: [{
                "targets": 'nosort', //列的样式名
                "orderable": false //包含上样式名‘nosort'的禁止排序
            }],
            fnInitComplete: function () {
                // 此方法，会在表格完全加载完成之后调用，
                // 为了防止异步表格还没有加载完，分页插件还没有生成，导致找不到的问题
                // 美化滚动条
                //niceScrollBar();
                // datatables 分页插件，放到左下角
                $("#smsTable_paginate").addClass('right');
            },
            ajax: function (data, callback, settings) {
                //封装请求参数
                var param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                param.draw = data.draw;
                param.userId = null;
                param.userName = $('#userName').val();
                param.company = $('#company').val();
                param.phone = $('#phone').val();
                //ajax请求数据
                $.ajax({
                    type: "GET",
                    url: "/user/list/data",
                    cache: false, //禁用缓存
                    data: param, //传入组装的参数
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
                {"data": "userId", "orderable": false},
                {"data": "userName", "orderable": false},
                {"data": "parentId", "orderable": false},
                {"data": "phone", "orderable": false},
                {"data": "company", "orderable": false},
                {"data": "address", "orderable": false},
                {"data": "memo", "orderable": false},
                {
                    "data": function (obj) {
                        return timeFormat(obj.createdTime)//update_time是实体类的属性
                    }
                },
                {
                    "data": function (obj) {
                        return '<div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs">'
                            + '<a class="am-btn am-btn-default am-btn-xs am-text-secondary" href="javascript:hrefEdit(' + obj.userId + ');">' +
                            '<span class="am-icon-pencil-square-o" >编辑</span></a>  ' +
                            '<a class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only" href="javascript:hrefDetail(' + obj.userId + ');">' +
                            '<span class="am-icon-trash-o">详情</span></a>' + '</div></div>';
                    },
                    "className": "center"
                }
            ]
        }).api();
        //此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象


    } catch (e) {
        alert(e);
    }


    $('#smsTable tbody').on('click', 'tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        } else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
        var rows = table.rows('.selected').data();
        //alert(rows.length + ' row(s) selected');
        //alert(rows[0].communication);
        var obj = rows[0];
        // alert(JSON.stringify(obj));
        var params = {};
        params.userId = userId;
        params.preUserId = obj.userId;

        $('#preUserId').html(params.preUserId);

        $.ajax({
            //几个参数需要注意一下
            type: "get",//方法类型
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            dataType: "json",//预期服务器返回的数据类型
            url: "/menu/tree",//url
            data: params,
            success: function (result) {
                console.log(result);//打印服务端返回的数据(调试用)
                if (result.code == "0000") {
                    //alert(result.code)
                    var data = result.data;
                    zNodes = data;
                    tree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                    setCheck();
                    $("#py").bind("change", setCheck);
                    $("#sy").bind("change", setCheck);
                    $("#pn").bind("change", setCheck);
                    $("#sn").bind("change", setCheck);
                } else {
                    //alert(result.msg);
                    // window.location.href = "/";
                }
            },
            error: function () {
                //alert("用户名或密码不正确！");
                //window.location.href = "/";
            }
        });
    });

});

function hrefEdit(userId) {
    //alert(userId);
    window.location.href = "/fe/user/edit?quid=" + userId;

}

function hrefDetail(userId) {
    window.location.href = "/fe/user/detail?quid=" + userId;
}

//获得年月日      得到日期oTime
function timeFormat(str) {
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        //oHour = oDate.getHours(),
        //oMin = oDate.getMinutes(),
        //oSen = oDate.getSeconds(),
        oTime = oYear + '-' + getZf(oMonth) + '-' + getZf(oDay);//+ ' ' + getZf(oHour) + ':' + getZf(oMin) + ':' + getZf(oSen);//最后拼接时间
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
    $('#smsTable').DataTable().ajax.reload();
}

function add() {
    window.location.href = "/fe/user";
}

var setting = {
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true
        }
    }
};


var code;

function setCheck() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        /* py = $("#py").attr("checked") ? "p" : "",
         sy = $("#sy").attr("checked") ? "s" : "",
         pn = $("#pn").attr("checked") ? "p" : "",
         sn = $("#sn").attr("checked") ? "s" : "",*/
        type = {"Y": "ps", "N": "ps"};
    zTree.setting.check.chkboxType = type;
    showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
}

function showCode(str) {
   // alert(str);
}

function saveMenu() {
    var preUserId = $('#preUserId').html();
    var checkedNodes = tree.getCheckedNodes();
    //alert(preUserId);
    //alert(JSON.stringify(checkedNodes));
    var menuIds = "";
    for (var i = 0; i < checkedNodes.length; i++) {
        var obj = checkedNodes[i];
        var menuId = obj.id;
        menuIds += menuId + ",";
    }
    var paramsMid = menuIds.substr(0, menuIds.length - 1);
    var params = {};
    params.menuIds = paramsMid;
    params.preUserId = preUserId;
    params.userId = userId;
    $.ajax({
        //几个参数需要注意一下
        type: "post",//方法类型
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",//预期服务器返回的数据类型
        url: "/menu/ref/save",//url
        data: params,
        success: function (result) {
            console.log(result);//打印服务端返回的数据(调试用)
            if (result.code == "0000") {
                //alert(result.code)
                alert("分配成功");

            } else {
                //alert(result.msg);
            }
        },
        error: function () {
            //alert("用户名或密码不正确！");
            //window.location.href = "/";
        }
    });
}



function exit() {
    delCookie("userId", userId);
    window.location.href = "/";
}

function delCookie(name, value) {
    var today = new Date();
    var expires = new Date();
    expires.setTime(today.setTime(today.getTime() - 10000));
    document.cookie = name + "=" + escape(value) + "; expires=" + expires.toGMTString() + ";path=/";
}
