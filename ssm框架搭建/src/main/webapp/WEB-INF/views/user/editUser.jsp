<%--
  Created by IntelliJ IDEA.
  User: CD
  Date: 2019/7/25
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/userController/editUser.do" method="get">
    <input name="username" type="text" value="${user.username}"><br>
    <input name="password"  type="text" value="${user.password}"><br>
    <input name="age" type="text" value="${user.age}"><br>
    <input type="submit"> <br>
</form>
</body>
</html>
