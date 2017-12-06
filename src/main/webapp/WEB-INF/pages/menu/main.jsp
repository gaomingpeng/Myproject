
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>系统后台</title>
    <link rel="shortcut icon" href="../../static/img/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="../../static/js/layui/css/layui.css">
    <script src="../../static/js/layui/layui.js"></script>
   <script src="../../static/js/jquery-1.8.3.js"></script>
    <style type="text/css" >
        body .layui-tree-skin-shihuang .layui-tree-branch{color: #EDCA50;}
    </style>
</head>
<body class="layui-layout-body">

<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">系统后台</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
     <%--   <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="">控制台</a></li>
            <li class="layui-nav-item"><a href="">商品管理</a></li>
            <li class="layui-nav-item"><a href="">用户</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">其它系统</a>
                <dl class="layui-nav-child">
                    <dd><a href="">邮件管理</a></dd>
                    <dd><a href="">消息管理</a></dd>
                    <dd><a href="">授权管理</a></dd>
                </dl>
            </li>
        </ul>--%>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="../../static/img/tx.jpg" class="layui-nav-img">
                    ${sessionScope.USER.username}
                    <span style="color: #d9534f">${sessionScope.roleName}</span>
                </a>

                <dl class="layui-nav-child">
                    <dd><a href="">基本资料</a></dd>
                    <dd><a href="">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="/user/exit.do">退出</a></li>
        </ul>
    </div>

    <div class="layui-side " style="background-color: white">

        <ul id="demo" ></ul>

    </div>

    <div class="layui-body">
        <iframe id="content" src="" style="width: 100%;height: 100%">

        </iframe>

    </div>
 <input  type="hidden" value="${sessionScope.Permissions}"  id="Permissons"/>
        <!-- 内容主体区域 -->


    <div class="layui-footer">
        <!-- 底部固定区域 -->
        © layui.com
    </div>
</div>

<script>

    //JavaScript代码区域
    layui.use(['element','tree'], function(){
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


        function toForward(url) {
            $("#content").attr('src',url);
        }


        var element = layui.element;
        layui.tree({
            elem: '#demo', //传入元素选择器
            skin:'shihuang',
            nodes: newList,
            click: function(node){

                toForward(node.url);
            }
        });
    });





</script>
</body>
</html>
