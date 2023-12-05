<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="./js/jquery-3.6.0.min.js"></script>
    <style>
        .container {
            display: flex;
            justify-content: space-between;
            background-color: white;
            height: 100vh;
        }

        .left-box {
            width: 220px;
            background-color: #4472C4;
            padding: 20px;
            color: white;
        }

        .circle {
            width: 220px;
            height: 220px;
            border-radius: 50%;
            background-color: #C5E0B4;
            margin-bottom: 70px;
            /*margin-left: 8px;*/
            margin-top: 10px;
        }

        .text {
            font-size: 25px;
            margin-bottom: 20px;
        }

        .buttons-box {
            display: flex;
            flex-direction: column;
            gap: 30px;
            background-color: #4472C4
        }

        .left-button {
            height: 70px;
            font-size: 25px;
            background-color: #F5B094
        }

        .center-box {
            width: 1000px;
            height: 85vh;
            margin-top: 50px;
            margin-right: 100px;
            color: #ffffff;

            display: flex;
            justify-content: center;
            align-items: center;
        }

    </style>
    <script src="./js/echarts.min.js"></script>
    <script src="./js/jquery-3.6.0.min.js"></script>
    <script src="./js/echarts-wordcloud.min.js"></script>
    <script src="./js/walden.js"></script>
</head>
<body>
<div class="container">
    <div class="left-box">
        <div class="circle"></div>
        <div class="text">早上好</div>
        <div class="buttons-box">
            <script>
                function jumpIndex(){
                    window.location.href="./index.html";
                }
                function jumpSearch(){
                    window.location.href="./KnowledgeGraph.html";
                }
                function operateGraphDatabase(){
                    window.location.href="./index.html";
                }
                function jumpSciInf(){
                    window.location.href="./SciInf.html";
                }
            </script>
            <button class="left-button" onclick="javascript:operateGraphDatabase()">操作图数据库</button>
            <button class="left-button" onclick="javascript:jumpSearch()">查找知识图谱</button>
            <button class="left-button" onclick="javascript:jumpIndex()">查看科研信息汇总表</button>
            <button class="left-button" onclick="javascript:jumpSciInf()">检索科研信息</button>
        </div>
    </div>
    <div class="center-box" style="border: 1px solid black;">
        <div id="knowledgeGraph" style="width: 100%; height: 100%;"></div>
    </div>

</div>
</body>
<script>
    var kg = echarts.init(document.getElementById('knowledgeGraph'), 'macarons');
    var jsonObj = <%= (String)request.getAttribute("jsonObj") %>;

    var Links = jsonObj.link;
    var Sizes = jsonObj.size;

    console.log(typeof Links)
    console.log(typeof Sizes)

    var optionForKG = {
        title: {
             text: '知识图谱',
             left: 'center'
        },

        tooltip: {},
        animationDurationUpdate: 1500,
        animationEasingUpdate: 'quinticInOut',
        series: [{
             type: 'graph',
             layout: 'force',
             force: {
                  repulsion: 1001,
                  edgeLength: 150
             },
        color: 'mediumslateblue',
        data: Sizes,
        links: Links,

        roam: true,
                    label: {
                        show: true,
                    },
                    emphasis: {
                        lineStyle: {
                            width: 10
                        }
                    }
                }]
            };
    kg.setOption(optionForKG);
</script>
</html>
<script src="./js/getname.js"></script>