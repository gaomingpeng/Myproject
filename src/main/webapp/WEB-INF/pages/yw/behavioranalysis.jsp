
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
    <script src="../../static/js/echarts.js"></script>
</head>
<body>
<div id="echarts" style="height: 100%;width: 100%">

</div>

</body>

<script>
 $(function () {
     initEchart();
 });
 function  initEchart() {
    var ajaxReturnData;
     $.ajax({
         url: '/userop/getLoginCount.do',
         type: 'post',
         dataType: 'json',
         async: false,
         success: function (result) {

             ajaxReturnData = result;
         }
     });
     var xdata = [];
     var ydata = [];
    if(ajaxReturnData){
        for (var i =0;i<ajaxReturnData.length;i++){
            xdata.push(ajaxReturnData[i].userid);
            ydata.push(ajaxReturnData[i].logincount);
        }

    }else {
        return;
    }

     var myChart = echarts.init(document.getElementById('echarts'));

     var  option = {
         color: ['#db0d27'],
         tooltip : {
             trigger: 'axis',
             axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                 type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
             }
         },
         grid: {
             left: '3%',
             right: '4%',
             bottom: '3%',
             containLabel: true
         },
         xAxis : [
             {
                 type : 'category',
                 data :xdata,
                 axisTick: {
                     alignWithLabel: true
                 },
                 name:"用户ID"
             }
         ],
         yAxis : [
             {
                 type : 'value',
                 name:"登录次数"
             }
         ],
         series : [
             {
                 name:'登录次数',
                 type:'bar',
                 barWidth: '60%',
                 data:ydata
             }
         ]
     };
     myChart.setOption(option);
 }
</script>

</html>

