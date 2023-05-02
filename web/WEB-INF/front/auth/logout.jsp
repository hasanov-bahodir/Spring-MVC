<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static uz.pdp.validation.ValidationResult.SUCCESSFULLY_LOGOUT" %><%--
  Created by IntelliJ IDEA.
  User: koh
  Date: 2/5/22
  Time: 6:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logout</title>
    <%@ include file="../layouts/link.html" %>
</head>
<body>
<%
    session.invalidate();
%>
<div class="container" style="padding-top: 3%">
    <a href="/auth/login">Login</a>
</div>

<%@ include file="../layouts/scripts.html" %>
</body>
</html>
