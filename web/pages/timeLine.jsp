<%--
  Created by IntelliJ IDEA.
  User: BASA
  Date: 2017/2/28
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<html>
<head>
    <title>Title</title>
</head>
<body>

    <div class="container">
        <ul>
            <c:forEach items="${timeLines}" var="blog">
                <li style="margin-bottom: 30px">
                <span class="title">
                    <a href="${pageContext.request.contextPath}/blog/blogDetail/${blog.id}">${blog.title}</a>
                </span>
                </li>
            </c:forEach>
        </ul>

    </div>

</body>
</html>
