<%--
  Created by IntelliJ IDEA.
  User: letingoo
  Date: 2016/11/23
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>上传头像</title>
</head>
<body>

    <form method="post" action="${pageContext.request.contextPath}/user/avatar" enctype="multipart/form-data">
        <input type="file" name="avatar" /> <br>
        <input type="submit" value="上传Avatar"/>
    </form>


</body>
</html>
