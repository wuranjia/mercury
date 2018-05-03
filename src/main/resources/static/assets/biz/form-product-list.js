$(function () {
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
    var table = $("#productTable").dataTable({
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
            $("#productTable_paginate").addClass('right');
        },
        ajax: function (data, callback, settings) {
            //封装请求参数
            var param = {};
            param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;//开始的记录序号
            param.page = (data.start / data.length) + 1;//当前页码
            param.draw = data.draw;
            param.userId = null;
            param.type = '';

            //ajax请求数据
            $.ajax({
                type: "GET",
                url: "/product/list",
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
            {"data": "id", "orderable": false},
            {"data": "name", "orderable": false},
            {"data": "flow", "orderable": false},
            {"data": "type", "orderable": false},
            {"data": "price", "orderable": false}
        ]
    }).api();
});