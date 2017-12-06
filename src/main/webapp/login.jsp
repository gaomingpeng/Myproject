<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="tag.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>Myproject</title>
    <link rel="shortcut icon" href="static/img/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="static/js/layui/css/layui.css">
    <link rel="stylesheet" href="static/css/login.css">
    <script src="static/js/layui/layui.js"></script>
    <script src="static/js/jquery-1.8.3.js"></script>
    <script src="static/js/common.js"></script>
</head>
<body>
<div class="layui-carousel video_mask" id="login_carousel" >
    <div carousel-item>
        <div class="carousel_div1"></div>
        <div class="carousel_div2"></div>
        <div class="carousel_div3"></div>
    </div>
    <div class="login layui-anim layui-anim-up">
        <h1>Myproject</h1></p>
        <form class="layui-form"  method="post">
            <div class="layui-form-item">
                <input type="text" name="userid" lay-verify="required" placeholder="请输入账号" autocomplete="off"  value="" class="layui-input">
            </div>
            <div class="layui-form-item">
                <input type="password" name="password" lay-verify="required" placeholder="请输入密码" autocomplete="off" value="" class="layui-input">
            </div>
            <!--<div class="layui-form-item form_code">
                <input class="layui-input" name="code" placeholder="验证码" lay-verify="required" type="text" autocomplete="off">
                <div class="code"><img src="captcha.do" width="116" height="36"></div>
            </div>-->
           <div style="margin: 20px 0px">
               <button class="layui-btn login_btn" lay-submit="" lay-filter="login" >登陆系统</button>
           </div>
            <div>
                <button class="layui-btn login_btn" type="button" lay-filter="register" onclick="register()" >注册账号</button>
            </div>
        </form>

    </div>

</div>

</body>

</html>
<script>
    layui.use(['form','carousel'],function () {
           var form = layui.form,
        carousel = layui.carousel;
        /**背景图片轮播*/
        carousel.render({
            elem: '#login_carousel',
            width: '100%',
            height: '100%',
            interval:3000,
            arrow: 'none',
            anim: 'fade',
            indicator:'none'
        });


        form.on('submit(login)', function (data) {
            //弹出loading
            var loginLoading = top.layer.msg('登陆中，请稍候', {icon: 16, time: false, shade: 0.8});
            //记录ajax请求返回值
            var ajaxReturnData;
            debugger
            //登陆验证
            $.ajax({
                url: '/user/checklogin.do',
                type: 'post',
                dataType: 'json',
                async: false,
                data: data.field,
                success: function (result) {
                    ajaxReturnData = result;
                }
            });
            debugger
            //登陆成功
           if (ajaxReturnData.code == 200) {
                location.href="/user/main.do";
                top.layer.close(loginLoading);
                return false;
            } else {
                top.layer.close(loginLoading);
                top.layer.msg(ajaxReturnData.msg, {icon: 5});
                return false;
            }
        });


    });

    function  register() {

        layer.open({
            type: 2,
            title: '注册',
            shadeClose: true,
            shade: 0.8,
            area: ['480px', '50%'],
            content: '/user/register.do' //iframe的url
        });
        return ;
    }
</script>