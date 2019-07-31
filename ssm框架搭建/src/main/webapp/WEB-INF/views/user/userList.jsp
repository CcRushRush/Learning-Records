<%@page contentType="text/html;charset=utf-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <title>title</title>
</head>
<body>
<table border="1">
    <tr>
        <td>姓名</td>
        <td>密码</td>
        <td>年龄</td>
        <td>操作</td>
    </tr>
    <c:forEach items="${userList}" var="user">
        <tr>
            <td>${user.username}</td>
            <td>${user.password}</td>
            <td>${user.age}</td>
            <td><a href="${pageContext.request.contextPath}/userController/toEditUser.do?name=${user.username}">修改</a>
                <a href="${pageContext.request.contextPath}/userController/toEditUser1/${user.username}.do">通过username路径修改</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>