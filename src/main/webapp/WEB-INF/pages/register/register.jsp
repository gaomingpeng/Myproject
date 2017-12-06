
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="shortcut icon" href="../../../static/img/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="../../../static/js/layui/css/layui.css">
    <link rel="stylesheet" href="../../../static/css/login.css">
    <script src="../../../static/js/layui/layui.js"></script>
    <script src="../../../static/js/jquery-1.8.3.js"></script>
    <script src="../../../static/js/common.js"></script>
</head>
<body>
<form class="layui-form" action="">
    <div class="layui-form-item" style="margin: 20px 0px">
        <label class="layui-form-label">用户昵称</label>
        <div class="layui-input-block" style="width: 300px;">
            <input type="text" name="username" required  lay-verify="required" placeholder="请输入昵称" autocomplete="off" class="layui-input" >
        </div>
    </div>
    <div class="layui-form-item" >
        <label class="layui-form-label">用户名</label>
        <div class="layui-input-block" style="width: 300px;">
            <input type="text" name="userid" required  lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input" >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">密码</label>
        <div class="layui-input-inline "   style="width: 300px;">
            <input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid layui-word-aux"></div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">确认密码</label>
        <div class="layui-input-inline"  style="width: 300px;">
            <input type="password" name="password2"  required lay-verify="required" placeholder="请确认密码" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid layui-word-aux" id="tipid"></div>
    </div>
   <%-- <div class="layui-form-item">
        <label class="layui-form-label">角色</label>
        <div class="layui-input-block" style="width: 300px;">
            <select name="role" lay-verify="required">
                <option value=""></option>
                <option value="0">管理员</option>
                <option value="1">用户</option>
                <option value="2">VIP</option>
                <option value="3">SVIP</option>
            </select>
        </div>
    </div>--%>




    <div class="layui-form-item">
        <div class="layui-input-block" >
            <button class="layui-btn" lay-submit lay-filter="formDemo">注册</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<script>
    //Demo
    layui.use('form', function(){
        var form = layui.form;

        //监听提交
        form.on('submit(formDemo)', function(data){
              if(data.field.password!=data.field.password2){
                  debugger
                  layer.alert("两次密码不一致", {
                      icon: 2,
                      skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
                  })
                  return false;
              }
            var ajaxReturnData;
            debugger
            $.ajax({
                url: '/user/adduser.do',
                type: 'post',
                dataType: 'json',
                async: false,
                data: data.field,
                success: function (result) {
                    debugger
                    ajaxReturnData = result;
                }
            });
            if(ajaxReturnData.code==200){
                layer.alert(ajaxReturnData.msg, {
                    icon: 1,
                    skin: 'layer-ext-moon' ,//该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅

                },function (index) {
                    parent.layer.closeAll();//关闭父界面
                    layer.close(index);//关闭当前弹出层
                })

                return false;
            }else{
                layer.alert(ajaxReturnData.msg, {
                    icon: 2,
                    skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
                })

                return false;
            }

        });
    });
</script>
</body>
</html>
