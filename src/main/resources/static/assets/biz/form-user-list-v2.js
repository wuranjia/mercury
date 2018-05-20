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


function init() {

    userId = checkSession();

    //初始化表格
    $("#userTable").dataTable({
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
            $("#userTable_paginate").addClass('right');
        },
        ajax: function (data, callback, settings) {
            //封装请求参数
            var param = {};
            param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.page = (data.start / data.length) + 1;//当前页码
            param.draw = data.draw;
            param.userId = userId;
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
                },
                "orderable": false
            },
            {
                "data": function (obj) {
                    return '<div class="am-btn-toolbar"><div class="am-btn-group am-btn-group-xs">'
                        + '<a class="am-btn am-btn-default am-btn-xs am-text-secondary" href="javascript:hrefEdit(' + obj.userId + ');">' +
                        '<span class="am-icon-pencil-square-o" >编辑 &nbsp;&nbsp;</span></a>  ' +
                        '<a class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only" href="javascript:hrefDetail(' + obj.userId + ');">' +
                        '<span class="am-icon-trash-o">详情 &nbsp;&nbsp;</span></a>' +
                        '<a class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only" href="javascript:showBuyWindow(\'' + obj.userId + "\',\'" + obj.userName + '\');">' +
                        '<span class="am-icon-trash-o">分配权限 &nbsp;&nbsp;</span></a>' +
                        '</div></div>';
                },
                "className": "center",
                "orderable": false
            }
        ]
    }).api();

}

function hrefEdit(userId) {
    var params = "queryUserId=" + userId;
    $.ajax({
        //几个参数需要注意一下
        type: "get",//方法类型
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",//预期服务器返回的数据类型
        url: "/user/find/one",//url
        data: params,
        success: function (result) {
            console.log(result);//打印服务端返回的数据(调试用)
            if (result.code == "0000") {
                //alert(result.code)
                var data = result.data;
                $('#editUserId').val(userId);
                $('#editUserName').val(data.userName);
                $('#editLoginPwd').val('');
                $('#editTrdPwd').val('');
                $('#editPhone').val(data.phone);
                $('#editCompany').val(data.company);
                $('#editAddress').val(data.address);
                $('#editMemo').val(data.memo);
                $('#editParentId').val(data.editParentId);

                $('#edit').modal({
                    closeOnConfirm: false,
                    onConfirm: function () {
                    },
                    onCancel: function () {
                    }
                });
            } else {
                alert("加载失败");
            }
        },
        error: function () {
            //alert("用户名或密码不正确！");
            //window.location.href = "/";
        }
    });
    //alert(userId);
    //window.location.href = "/fe/user/edit?quid=" + userId;
    //事件

}

function add() {
    //事件
    $('#add').modal({
        closeOnConfirm: false,
        onConfirm: function () {
        },
        onCancel: function () {
        }
    });
    // window.location.href = "/fe/user";
}

function hrefDetail(userId) {
    //window.location.href = "/fe/user/detail?quid=" + userId;
    //事件
    var params = "queryUserId=" + userId;
    $.ajax({
        //几个参数需要注意一下
        type: "get",//方法类型
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",//预期服务器返回的数据类型
        url: "/user/find/one",//url
        data: params,
        success: function (result) {
            console.log(result);//打印服务端返回的数据(调试用)
            if (result.code == "0000") {
                //alert(result.code)
                var data = result.data;
                $('#detailUserId').val(userId);
                $('#detailUserName').val(data.userName);
                $('#detailLoginPwd').val('******');
                $('#detailTrdPwd').val('******');
                $('#detailPhone').val(data.phone);
                $('#detailCompany').val(data.company);
                $('#detailAddress').val(data.address);
                $('#detailMemo').val(data.memo);

                $('#detail').modal({
                    closeOnConfirm: false,
                    onConfirm: function () {
                    },
                    onCancel: function () {
                    }
                });
            } else {
                alert("加载失败");
            }
        },
        error: function () {
            //alert("用户名或密码不正确！");
            //window.location.href = "/";
        }
    });

}

function query() {
    $('#userTable').DataTable().ajax.reload();
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

function formInit() {

    $('#addForm').bootstrapValidator(
        {
            message: 'This value is not valid',
            fields: {
                addUserName: {
                    validators: {
                        notEmpty: {
                            message: 'The addUserName is required and can\'t be empty'
                        }
                    }
                },
                addLoginPwd: {
                    validators: {
                        notEmpty: {
                            message: 'The addLoginPwd is required and can\'t be empty'
                        }
                    }
                },
                addTrdPwd: {
                    validators: {
                        notEmpty: {
                            message: 'The addTrdPwd is required and can\'t be empty'
                        }
                    }
                },
                addPhone: {
                    validators: {
                        notEmpty: {
                            message: 'The addPhone is required and can\'t be empty'
                        }
                    }
                },
                addCompany: {
                    validators: {
                        notEmpty: {
                            message: 'The addCompany is required and can\'t be empty'
                        }
                    }
                },
                addAddress: {
                    validators: {
                        notEmpty: {
                            message: 'The addAddress is required and can\'t be empty'
                        }
                    }
                },
                addMemo: {
                    validators: {
                        notEmpty: {
                            message: 'The addMemo is required and can\'t be empty'
                        }
                    }
                }

            }
        }
    );

    $('#editForm').bootstrapValidator(
        {
            message: 'This value is not valid',
            fields: {
                editUserName: {
                    validators: {
                        notEmpty: {
                            message: 'The editUserName is required and can\'t be empty'
                        }
                    }
                },
                editLoginPwd: {
                    validators: {
                        notEmpty: {
                            message: 'The editLoginPwd is required and can\'t be empty'
                        }
                    }
                },
                editTrdPwd: {
                    validators: {
                        notEmpty: {
                            message: 'The editTrdPwd is required and can\'t be empty'
                        }
                    }
                },
                editPhone: {
                    validators: {
                        notEmpty: {
                            message: 'The editPhone is required and can\'t be empty'
                        }
                    }
                },
                editCompany: {
                    validators: {
                        notEmpty: {
                            message: 'The editCompany is required and can\'t be empty'
                        }
                    }
                },
                editAddress: {
                    validators: {
                        notEmpty: {
                            message: 'The editAddress is required and can\'t be empty'
                        }
                    }
                },
                editMemo: {
                    validators: {
                        notEmpty: {
                            message: 'The editMemo is required and can\'t be empty'
                        }
                    }
                }

            }
        }
    );
}

function saveEdit() {
    var params = {};
    params.userName = $('#edtUserId').val();
    params.userName = $('#editUserName').val();
    params.loginPwd = $('#editLoginPwd').val();
    params.trdPwd = $('#editTrdPwd').val();
    params.phone = $('#editPhone').val();
    params.company = $('#editCompany').val();
    params.address = $('#editAddress').val();
    params.memo = $('#editMemo').val();
    params.parentId = $('#editParentId').val();
    params.userId = $.cookie('userId');

    var bootstrapValidator = $("#editForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        $.ajax({
            //几个参数需要注意一下
            type: "post",//方法类型
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            dataType: "json",//预期服务器返回的数据类型
            url: "/user/edit",//url
            data: params,
            success: function (result) {
                console.log(result);//打印服务端返回的数据(调试用)
                //alert(result.msg);
                if (result.code == "0000") {
                    //alert(result.code)
                    alert("修改成功");
                    window.location.reload();
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
    }
}

function saveAdd() {
    var params = {};
    params.userName = $('#addUserName').val();
    params.loginPwd = $('#addLoginPwd').val();
    params.trdPwd = $('#addTrdPwd').val();
    params.phone = $('#addPhone').val();
    params.company = $('#addCompany').val();
    params.address = $('#addAddress').val();
    params.memo = $('#addMemo').val();
    params.parentId = $.cookie('userId');

    var bootstrapValidator = $("#addForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        $.ajax({
            //几个参数需要注意一下
            type: "post",//方法类型
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            dataType: "json",//预期服务器返回的数据类型
            url: "/user/add",//url
            data: params,
            success: function (result) {
                console.log(result);//打印服务端返回的数据(调试用)

                if (result.code == "0000") {
                    //alert(result.code)
                    alert("新增成功");
                    window.location.reload();
                } else {
                    alert("新增失败");
                    // window.location.href = "/";
                }
            },
            error: function () {
                //alert("用户名或密码不正确！");
                //window.location.href = "/";
            }
        });
    } else {
        alert("请填入必填项！");
    }
}

function showBuyWindow(preUserId, userName) {
    $('#preUserId').val(preUserId);
    $('#userId').val(userId);
    $('#preUserName').val(userName);
    var params = {};
    params.userId = userId;
    params.preUserId = preUserId;
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

    //事件
    $('#menu').modal({
        closeOnConfirm: false,
        onConfirm: function () {
        },
        onCancel: function () {
        }
    });
}




