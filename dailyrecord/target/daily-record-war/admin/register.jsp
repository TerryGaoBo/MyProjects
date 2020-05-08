<%@page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../css/bootstrap-table.min.css"/>
    <link rel="stylesheet" href="../css/bootstrap-combined.min.css"/>
    <link rel="stylesheet" href="../css/bootstrap-datetimepicker.min.css"/>
    <link rel="stylesheet" href="../css/style.css"/>

</head>
<body>
<div id="app" >
<form class="login-container" action="register" method="post">
    <h3 class="title">注册用户</h3>
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
            <input name="password" class="input" type="text"  placeholder="等级大于2是管理员">
        </label>
    </div>
    <div class="item">
        <label >
            <input class="submit"  type="submit" value="注册">
        </label>
    </div>
</form>
</div>
</body>
</html>