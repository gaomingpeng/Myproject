
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>系统后台</title>
    <link rel="shortcut icon" href="../../static/img/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="../../static/js/layui/css/layui.css">
    <link rel="stylesheet" href="../../static/ztree/css/zTreeStyle/zTreeStyle.css">
    <script src="../../static/js/layui/layui.js"></script>
    <script src="../../static/js/jquery-1.8.3.js"></script>
    <script src="../../static/ztree/jquery.ztree.all.js"></script>


</head>
<body>
<form class="layui-form" action="">
    <div class="layui-form-item" style="margin: 40px 0px;width: 22%">

            <label class="layui-form-label">选择角色</label>
            <div class="layui-input-block">
                <select name="city" lay-verify="required">
                    <option value=""></option>
                    <option value="0">管理员</option>
                    <option value="1">用户</option>
                    <option value="2">VIP</option>
                    <option value="3">SVIP</option>
                </select>

        </div>
    </div>
    <div  >

        <label class="layui-form-label">选择父级</label>

        <div  >

            <ul id="TreeID" ></ul>

        </div>

    </div>
    <div class="layui-form-item" style="margin: 40px 0px;width: 30%">
        <label class="layui-form-label">菜单名</label>
        <div class="layui-input-inline">
            <input type="text" name="menuname" required lay-verify="required" placeholder="请输入菜单名" autocomplete="off" class="layui-input">
        </div>

    </div>

    <div class="layui-form-item" style="margin: 40px 0px;width: 30%">
        <label class="layui-form-label">请求地址</label>
        <div class="layui-input-inline">
            <input type="text" name="url" required lay-verify="required" placeholder="请输入地址" autocomplete="off" class="layui-input">
        </div>

    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">保存</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>




<script>
    //Demo
    layui.use(['form','tree'], function(){
        debugger
        var form = layui.form;

        //监听提交
        form.on('submit(formDemo)', function(data){
            layer.msg(JSON.stringify(data.field));
            return false;
        });

        var data;
        $.ajax({
            url: '/user/getMenu.do',
            type: 'post',
            dataType: 'json',
            async: false,
            success: function (result) {
                debugger
                data = result;
            }
        });
        if(!data){
            return;
        }
        for(var i=0;i<data.length;i++)
            for(var key in data[i]){
                if(key=="decription"){

                    data[i].name = data[i][key];

                }
            }
        function parseList (list) {
            var map = {};
            list.forEach(function (item) {
                if (!map[item.id]) {
                    map[item.id] = item;
                }
            });
            list.forEach(function (item) {
                if (item.parentid != 0) {
                    map[item.parentid].children ? map[item.parentid].children.push(item) : map[item.parentid].children = [item];
                }
            });

            return list.filter(function (item) {
                return item.parentid == 0;
            });
        }

        var newList = parseList(data);
        layui.tree({
            elem: '#TreeID', //传入元素选择器
            //skin:'shihuang',
            nodes: newList,
            checked:true,
            click: function(node){


            }
        });


    });
</script>
</body>
</html>
