<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>恭喜，注册成功！</title>
    <style>
        .holder{
            width:1000px;
            height:650px;
            margin-left:auto;
            margin-right:auto;
        }
        .pic-holder{
            width:500px;
            height:500px;
            margin-left:auto;
            margin-right:auto;
        }
        img{
            width:500px;
            height:500px;
        }
        .text{
            margin-left:180px;
            font-size:20px;
        }
    </style>
    <script src="./js/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="holder">
        <div class="text">
            恭喜，注册成功！
            您的ID是<p id="uid"><%= (String)request.getAttribute("uid") %></p>
            <a href="./loginpage.html">点击此处返回登录界面</a>!
        </div>
        <div class="pic-holder">
            <img src="./img_src/success.jpg">
        </div>
    </div>
</body>
</html>
