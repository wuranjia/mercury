function delCookie(name, value) {
    var today = new Date();
    var expires = new Date();
    expires.setTime(today.setTime(today.getTime() - 10000));
    document.cookie = name + "=" + escape(value) + "; expires=" + expires.toGMTString() + ";path=/";
}

function exit() {
    delCookie("userId", userId);
    window.location.href = "/";
}

function setCookie(name, value) {
    var today = new Date();
    var expires = new Date();
    expires.setTime(today.getTime() + 1000 * 60 * 60 * 24 * 7);
    document.cookie = name + "=" + escape(value) + "; expires=" + expires.toGMTString() + ";path=/";
}

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

//提示信息
var lang2 = {
    "sProcessing": "处理中...",
    "sLengthMenu": "每页 _MENU_ 项",
    "sZeroRecords": "没有匹配结果",
    "sInfo": '<span class="  am-text-sm">共 _TOTAL_ 项</span>',
    "sInfoEmpty": '<span class=" am-text-sm">共 0 项</span>',
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

var validator = {
    // 是否使用 H5 原生表单验证，不支持浏览器会自动退化到 JS 验证
    H5validation: false,

    // 内置规则的 H5 input type，这些 type 无需添加 pattern
    H5inputType: ['email', 'url', 'number'],

    // 验证正则
    // key1: /^...$/，包含 `js-pattern-key1` 的域会自动应用改正则
    patterns: {},

    // 规则 class 钩子前缀
    patternClassPrefix: 'js-pattern-',

    activeClass: 'am-active',

    // 验证不通过时添加到域上的 class
    inValidClass: 'am-field-error',

    // 验证通过时添加到域上的 class
    validClass: 'am-field-valid',

    // 表单提交的时候验证
    validateOnSubmit: true,

    // 表单提交时验证的域
    // Elements to validate with allValid (only validating visible elements)
    // :input: selects all input, textarea, select and button elements.
    // @since 2.5: move `:visible` to `ignore` option, became to `:hidden`
    allFields: ':input:not(:button, :disabled, .am-novalidate)',

    // 表单提交时验证的忽略的域
    // ignored elements
    // @since 2.5
    ignore: ':hidden:not([data-am-selected], .am-validate)',

    // 调用 validate() 方法的自定义事件
    customEvents: 'validate',

    // 下列元素触发以下事件时会调用验证程序
    keyboardFields: ':input:not(:button, :disabled,.am-novalidate)',
    keyboardEvents: 'focusout, change', // keyup, focusin

    // 标记为 `.am-active` (发生错误以后添加此 class)的元素 keyup 时验证
    activeKeyup: false,

    // textarea[maxlength] 的元素 keyup 时验证
    textareaMaxlenthKeyup: true,

    // 鼠标点击下列元素时会调用验证程序
    pointerFields: 'input[type="range"]:not(:disabled, .am-novalidate), ' +
    'input[type="radio"]:not(:disabled, .am-novalidate), ' +
    'input[type="checkbox"]:not(:disabled, .am-novalidate), ' +
    'select:not(:disabled, .am-novalidate), ' +
    'option:not(:disabled, .am-novalidate)',
    pointerEvents: 'click',

    // 域通过验证时回调
    onValid: function (validity) {
    },

    // 验证出错时的回调， validity 对象包含相关信息，格式通 H5 表单元素的 validity 属性
    onInValid: function (validity) {
    },

    // 域验证通过时添加的操作，通过该接口可定义各种验证提示
    markValid: function (validity) {
        // this is Validator instance
        var options = this.options;
        var $field = $(validity.field);
        var $parent = $field.closest('.am-form-group');
        $field.addClass(options.validClass).removeClass(options.inValidClass);

        $parent.addClass('am-form-success').removeClass('am-form-error');

        options.onValid.call(this, validity);
    },

    // 域验证失败时添加的操作，通过该接口可定义各种验证提示
    markInValid: function (validity) {
        var options = this.options;
        var $field = $(validity.field);
        var $parent = $field.closest('.am-form-group');
        $field.addClass(options.inValidClass + ' ' + options.activeClass).removeClass(options.validClass);

        $parent.addClass('am-form-error').removeClass('am-form-success');

        options.onInValid.call(this, validity);
    },

    // 自定义验证程序接口，详见示例
    validate: function (validity) {
        // return validity;
    },

    // 定义表单提交处理程序
    //   - 如果没有定义且 `validateOnSubmit` 为 `true` 时，提交时会验证整个表单
    //   - 如果定义了表单提交处理程序，`validateOnSubmit` 将会失效
    //        function(e) {
    //          // 通过 this.isFormValid() 获取表单验证状态
    //          // 注意： 如果自定义验证程序而且自定义验证程序中包含异步验证的话 this.isFormValid() 返回的是 Promise，不是布尔值
    //          // Do something...
    //        }
    submit: null
};

function checkSession() {
    var userId = $.cookie('userId');
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
    return userId;
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

function orderStatusFormat(obj) {
    if (obj == 10) {
        return "已下单";
    } else if (obj == 20) {
        return "已支付";
    } else if (obj == 30) {
        return "已发货";
    } else if (obj == 40) {
        return "已收货";
    } else if (obj == 90) {
        return "已取消";
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