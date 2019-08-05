<%--
  Created by IntelliJ IDEA.
  User: CD
  Date: 2019/7/28
  Time: 19:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                    $('#hiddenPic').val(respData.imgUrl);
                }

            }
            $('#managerForm').ajaxSubmit(options)
        }
    </script>
</head>
<body>
<form id="managerForm" action="${pageContext.request.contextPath}/managerController/editManager.do">
    <table border="1">
        <tr>
            <td>学号</td>
            <td><input name="mid" type="text" value="${manager.mid}" readonly="readonly"></td>
        </tr>
        <tr>
            <td>姓名</td>
            <td><input name="name" type="text" value="${manager.name}"></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input name="password"  type="text" value="${manager.password}"></td>
        </tr>
        <tr>
            <td>性别</td>
            <td><input name="gender"  type="text" value="${manager.gender}"></td>
        </tr>
        <tr>
            <td>年龄</td>
            <td><input name="age" type="text" value="${manager.age}"></td>
        </tr>
        <tr>
            <td>个人照</td>
            <td><img id="pic" src="${manager.picture}" width="100px" height="100px">
                <input type="file" name="managerPicture" onchange="submitImage()">
                <input id="hiddenPic" type="hidden" name="picture" value="${manager.picture}"></td>
        </tr>
        <tr><td><input type="submit" value="提交"></td></tr>
    </table>
</form>
</body>
</html>
