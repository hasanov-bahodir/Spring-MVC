<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <%@ include file="../layouts/link.html" %>
    <style>
        .divider:after,
        .divider:before {
            content: "";
            flex: 1;
            height: 1px;
            background: #eee;
        }
    </style>
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
                        <h2> Register </h2>
                        <c:if test="${email_invalid!=null}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">${email_invalid}

                            </div>
                        </c:if>
                        <form method="post" action="/auth/register">
                            <input class="login-field" name="email" id="lemail" type="text"
                                   placeholder="Enter Your Email">
                            <input class="login-field" name="password" id="lpassword" type="password"
                                   placeholder="Enter Your Password">
                            <input class="login-field" name="confirm_password" id="qpassword" type="password"
                                   placeholder="Confirm Your Password">
                            <div class="submit-area">
                                <button type="submit" class="submit more-link"> Register</button>
                                <a href="/auth/login" class="submit more-link"> Login</a>
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
