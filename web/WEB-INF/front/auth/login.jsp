<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: koh
  Date: 2/4/22
  Time: 3:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <%@ include file="../layouts/link.html" %>
</head>
<body>
<section class="account-section">
    <div class="container">
        <div class="row">
            <div class="col">
                <div class="reg_wrap">
                    <div class="reg_img">
                        <a href="/"> <img src="/ducat/logo.png" alt="" style="width: 250px"></a>
                    </div>
                    <!-- Start: Image -->
                    <!-- Start:  Login Form  -->
                    <div class="login-form">
                        <h2> Login to Your Account </h2>
                        <c:if test="${error!=null}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">${error}
                            </div>
                        </c:if>
                        <form method="post" action="/auth/login">
                            <input class="login-field" name="email" id="lemail" type="text"
                                   placeholder="Enter Your Email">
                            <input class="login-field" name="password" id="lpassword" type="password"
                                   placeholder="Enter Your Password">
                            <div class="lost_pass">
                                <input type="checkbox" id="rem-checkbox-input">
                                <label for="rem-checkbox-input" class="rem-checkbox">
                                    <span class="rem-me">Remember me</span>
                                </label>
                                <a href="" class="forget"> Lost your password? </a>
                            </div>
                            <div class="submit-area">
                                <button type="submit" class="submit more-link"> Login</button>
                                <a href="/auth/register" class="submit more-link"> Register</a>
                            </div>
                        </form>
                    </div>
                    <!-- End:Login Form  -->
                </div>
            </div>
            <!-- .col-md-6 .col-sm-12 /- -->
        </div>
        <!-- row /- -->
    </div>
    <!-- container /- -->
</section>


<%@ include file="../layouts/scripts.html" %>
</body>
</html>
