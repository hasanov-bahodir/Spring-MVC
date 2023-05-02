<%--
  Created by IntelliJ IDEA.
  User: koh
  Date: 2/24/22
  Time: 9:32 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${user.email}</title>
    <%@ include file="../layouts/link.html" %>
</head>
<body>
<div style="margin-top: -4rem">
<%@ include file="../layouts/menu.jsp" %>
<section class="account-section" >
    <div class="container">
        <div class="row">
            <div class="col">
                <div class="reg_wrap">
                    <div class="login-form">
                        <form method="post" action="/user/profile" enctype="multipart/form-data">
                            <input type="hidden" value="${user.id}" name="id">
                            <input class="login-field" id="lname" type="text"
                                   value="${user.userDetail.firstName}" name="firstName">
                            <input class="login-field" id="llastname" type="text"
                                   value="${user.userDetail.lastName}" name="lastName">
                            <c:if test="${user.userDetail.image_path!=null}">
                                <img alt="Avatar" class="table-avatar"
                                     src="data:image/png;base64,${user.userDetail.image_path}" width="80px">
                            </c:if>
                            <label for="exampleInputFile">File input</label>
                            <div class="input-group">
                                <div class="custom-file">
                                    <input type="file" name="file" class="custom-file-input"
                                           id="exampleInputFile">
                                    <label class="custom-file-label" for="exampleInputFile">Choose
                                        file</label>
                                </div>
                            </div>
                            <div class="submit-area">
                                <button type="submit" class="submit more-link"> Update</button>
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
</div>
<%@ include file="../layouts/scripts.html" %>
</body>
</html>
