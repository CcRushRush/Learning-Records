<%--
  Created by IntelliJ IDEA.
  User: CD
  Date: 2019/7/24
  Time: 21:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/userController/addUser.do" method="get">
    <input name="username" type="text"><br>
    <input name="password"  type="password"><br>
    <input name="age" type="text"><br>
    <input type="submit"> <br>
</form>
<br>表单使用model的扩展来接收
<form action="../userController/addUser2.do" method="get">
    <input name="user.username" type="text"><br>
    <input name="user.password"  type="password"><br>
    <input name="user.age" type="text"><br>
    <input type="submit"> <br>
</form>
<br>表单使用list来接收
<form action="../userController/addUser3.do" method="get">
    user1.username:<input name="userList[0].username" type="text"><br>
    user1.password:<input name="userList[0].password"  type="password"><br>
    user1.age:<input name="userList[0].age" type="text"><br>
    user2.username:<input name="userList[1].username" type="text"><br>
    user2.password:<input name="userList[1].password"  type="password"><br>
    user2.age:<input name="userList[1].age" type="text"><br>
    <input type="submit" value="save"> <br>
</form>
<br>表单使用Map来接收
<form action="../userController/addUser4.do" method="get">
    user1.username:<input name="userMap['username']" type="text"><br>
    user1.password:<input name="userMap['password']"  type="password"><br>
    user1.age:<input name="userMap['age']" type="text"><br>
    <input type="submit" value="save"> <br>
</form>
</body>
</html>
