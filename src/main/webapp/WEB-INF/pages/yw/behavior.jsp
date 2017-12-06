
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>系统后台</title>
    <link rel="shortcut icon" href="../../static/img/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="../../static/js/layui/css/layui.css">
    <script src="../../static/js/layui/layui.js"></script>
    <script src="../../static/js/jquery-1.8.3.js"></script>
</head>
<body>
<table class="layui-hide" id="test"></table>

</body>

<script>
    layui.use('table', function(){
        var table = layui.table;

        table.render({
            elem: '#test',
            url:'/userop/getPagequery.do',
            page: true,
            cellMinWidth: 80,
            cols: [[
                {field:'userid',  title: '用户ID', sort: true}
                ,{field:'username',  title: '用户名',sort: true}
                ,{field:'action',  title: '访问请求', sort: true}
                ,{field:'optime',  title: '操作时间',sort: true}

            ]]
        });
    });
</script>

</html>

