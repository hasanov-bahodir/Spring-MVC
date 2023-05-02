<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: koh
  Date: 2/18/22
  Time: 2:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../store/link.jsp" %>
</head>
<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">

    <!-- Preloader -->


    <%@ include file="../store/menu.jsp" %>

    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1>Courses Edit</h1>
                    </div>
                </div>
            </div><!-- /.container-fluid -->
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="container-fluid">
                <div class="row">
                    <!-- left column -->
                    <div class="col-md-6">
                        <!-- general form elements -->
                        <div class="card card-primary">
                            <div class="card-header">
                                <h3 class="card-title">Fill Form</h3>
                            </div>
                            <!-- /.card-header -->
                            <!-- form start -->
                            <form method="post" action="/courses/update/${course.id}" enctype="multipart/form-data">
                                <div class="card-body">
                                    <div class="form-group">
                                        <input type="hidden" name="id" value="${course.id}">
                                        <label for="exampleInputEmail1">Course Name</label>
                                        <input type="text" name="name" value="${course.name}" class="form-control"
                                               id="exampleInputEmail1"
                                               required>
                                    </div>
                                    <div class="form-group">
                                        <label for="exampleInputPassword1">Description</label>
                                        <textarea type="text" name="description" class="form-control"
                                                  id="exampleInputPassword1"
                                                  required>${course.description}</textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="exampleInputEmail5">Price</label>
                                        <input type="number" value="${course.price}" name="price" class="form-control"
                                               id="exampleInputEmail5"
                                               required>
                                    </div>
                                    <div class="form-group">
                                        <c:if test="${course.image_path!=null}">
                                            <img src="data:image/png;base64,${course.image_path}" width="100px"
                                                 height="80px">
                                        </c:if>
                                        <br>
                                        <label for="exampleInputFile">File input</label>
                                        <div class="input-group">
                                            <div class="custom-file">
                                                <input type="file" name="file" class="custom-file-input"
                                                       id="exampleInputFile">
                                                <label class="custom-file-label" for="exampleInputFile">Choose
                                                    file</label>
                                                <br>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="form-label select-label">Authors</label>
                                        <div class="input-group">
                                            <select class="form-control" name="authors" multiple>
                                                <c:forEach items="${authors}" var="user">
                                                    <option value="${user.id}">${user.userDetail.firstName} ${user.userDetail.lastName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="form-check">
                                            <input type="checkbox"
                                            <c:if test="${course.isFree}">
                                                   checked
                                            </c:if> name="free" class="form-check-input"
                                                   id="exampleCheck3">
                                            <label class="form-check-label" for="exampleCheck3">isFree</label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="form-check">
                                            <input type="checkbox" name="active" class="form-check-input"
                                                   id="exampleCheck1" <c:if test="${course.isActive}">
                                                   checked
                                            </c:if>>
                                            <label class="form-check-label" for="exampleCheck1">isActive</label>
                                        </div>
                                    </div>
                                </div>
                                <!-- /.card-body -->

                                <div class="card-footer">
                                    <button type="submit" class="btn btn-primary">Edit Course</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <footer class="main-footer">
        <strong>Copyright &copy; 2014-2021 <a href="https://adminlte.io">AdminLTE.io</a>.</strong>
        All rights reserved.
        <div class="float-right d-none d-sm-inline-block">
            <b>Version</b> 3.2.0
        </div>
    </footer>

    <!-- Control Sidebar -->
    <aside class="control-sidebar control-sidebar-dark">
        <!-- Control sidebar content goes here -->
    </aside>
    <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->
<%@ include file="../store/script.html" %>
</body>
</html>

