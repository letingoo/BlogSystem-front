<%--
  Created by IntelliJ IDEA.
  User: BASA
  Date: 2017/4/5
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>


<form action="${pageContext.request.contextPath}/autoCompletion" method="get">

    <input type="text" name="word" />

    <input type="submit" value="提交"/>
</form>



</body>
</html>
