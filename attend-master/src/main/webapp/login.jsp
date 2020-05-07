<%@page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <link rel="stylesheet" href="css/bootstrap.min.css?v=1" type="text/css"/>
    <link rel="stylesheet" href="css/style.css?v=1" type="text/css"  />
</head>
<body>
<div id="app" >
    <form class="login-container" action="login" method="post">
        <h3 class="title">系统登录</h3>
        <div class="item">
            <label >
                <input name="id" class="input" type="text"  placeholder="用户工号">
            </label>
        </div>
        <div class="item">
            <label >
                <input name="password" class="input" type="password"  placeholder="密码">
            </label>
        </div>
        <div class="item">
            <label >
                <input class="submit"  type="submit" value="登录">
            </label>
        </div>

    </form>
</div>
<%--<script src="static/js/jquery.min.js"></script>--%>
<%--<script src="static/js/bootstrap.min.js"></script>--%>
<%--<script src="static/js/vue.js"></script>--%>
<%--<script src="static/js/app.js"></script>--%>
</body>
</html>
