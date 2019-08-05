<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: CD
  Date: 2019/7/28
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="o" uri="http://www.opensymphony.com/oscache" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
    <script>
        function submitImage() {
            alert("提交图片到后台");
            // 封装请求数据
            var options = {
                type:'post',
                url:'${pageContext.request.contextPath}/upload/managerPicture.do',
                data:{
                    username:'cc'
                },
                dataType:'json',
                success:function (respData) {
                    console.log(respData.imgUrl);
                    $('#pic').attr('src',respData.imgUrl);
                    $('#pic').show();
                    $('#hiddenPic').val(respData.imgUrl);
                }

            }
            $('#addManagerForm').ajaxSubmit(options)
        }
    </script>
</head>
<body>
<%--现在时间：<%=new Date()%><br>--%>
<%--<o:cache scope="session" time="4">--%>
    <%--缓存时间：<%=new Date()%>--%>
<%--</o:cache>--%>
<form id="addManagerForm" action="${pageContext.request.contextPath}/managerController/addUser.do">
    <table border="1">
        <tr>
            <td>姓名</td>
            <td><input name="mid" type="hidden">
                <input name="name" type="text"></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input name="password"  type="text"></td>
        </tr>
        <tr>
            <td>性别</td>
            <td><input name="gender"  type="text"></td>
        </tr>
        <tr>
            <td>年龄</td>
            <td><input name="age" type="text"></td>
        </tr>
        <tr>
            <td>个人照</td>
            <td><img id="pic" style="display: none" src="" width="100px" height="100px">
                <input type="file" name="managerPicture" onchange="submitImage()">
                <input id="hiddenPic" type="hidden" name="picture">
            </td>
        </tr>
        <tr><td><input type="submit" value="提交"></td></tr>
    </table>
</form>
</body>
</html>
