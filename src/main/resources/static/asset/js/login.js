layui.use(['form', 'admin', 'jquery'], function () {
    var form = layui.form;
    var $ = layui.jquery;

    form.verify({
        username: function (value) {
            var reg = /^[a-zA-Z0-9]\w{3,19}$/;
            if (isEmptyString(value)) {
                return "请输入用户名";
            } else if(!reg.test(value)){
                return "帐号只能为4-20位的数字字母下划线组合，且不能以下划线开头。";
            }
        },
        password: function (value) {
            if (isEmptyString(value)) {
                return "请输入密码";
            }
        },
        captcha: function (value) {
            if (isEmptyString(value)) {
                return "请输入验证码";
            }
        }
    });

    form.on('submit(login)', function (data) {
        $.ajaxSetup({
            data:{
                "password": hex_md5($("#password").val())
            }
        });
        $.ajax({
            url:'/doLogin',
            method:'POST',
            data:data.field,
            success:function(data){
                if(data.status === 200){
                    window.location.href='/'
                }else{
                    layer.msg(data.msg, {time: 2000, icon:5});
                    $(".captcha").click();
                }
            },
            error:function (error) {
                data = JSON.parse(error.responseText);
                if(data.detail === 1){
                    if (!isEmptyString(data.data["userName"])){
                        layer.msg(data.data["userName"], {time: 2000, icon:5});
                    } else if (!isEmptyString(data.data["password"])) {
                        layer.msg(data.data["password"], {time: 2000, icon:5});
                    } else if (!isEmptyString(data.data["captcha"])) {
                        layer.msg(data.data["captcha"], {time: 2000, icon:5});
                    }
                } else {
                    layer.msg(data.msg, {time: 2000, icon:5});
                }
                $(".captcha").click();
            }
        });
        return false;
    });

});