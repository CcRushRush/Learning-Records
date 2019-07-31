<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="o" uri="http://www.opensymphony.com/oscache" %>
<%--
  Created by IntelliJ IDEA.
  User: CD
  Date: 2019/7/28
  Time: 19:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table border="1">
    <tr>
        <td>学号</td>
        <td>姓名</td>
        <td>密码</td>
        <td>性别</td>
        <td>年龄</td>
        <td>个人照</td>
        <td>操作</td>
    </tr>
    <c:forEach items="${managerList}" var="manager">
        <tr>
            <td>${manager.mid}</td>
            <td>${manager.name}</td>
            <td>${manager.password}</td>
            <td>${manager.gender}</td>
            <td>${manager.age}</td>
            <td><img src="${manager.picture}" width="100px" height="100px"></td>
            <td><a href="${pageContext.request.contextPath}/managerController/toEditManager/${manager.mid}.do">修改</a>
                <a href="${pageContext.request.contextPath}/managerController/toDeleteManager/${manager.mid}.do">删除</a></td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="6">
            <a href="${pageContext.request.contextPath}/managerController/toAddManager.do"><p style="text-align: center">添加</p></a>
        </td>
    </tr>
</table>
</body>
</html>
