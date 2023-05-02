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
                        <h1>Users</h1>
                    </div>
                </div>
            </div><!-- /.container-fluid -->
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h3 class="card-title">User List</h3>
                                <form method="GET" action="/admin/users" style="float:right">
                                    <div class="form-group">
                                        <input type="text" name="search" class="form-control" placeholder="search">
                                    </div>
                                </form>
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body">
                                <table id="example2" class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>FirstName</th>
                                        <th>LastName</th>
                                        <th>Image</th>
                                        <th>Role</th>
                                        <c:if test="${id==1}">
                                            <th>Action</th>
                                        </c:if>
                                        <c:if test="${id==2}">
                                            <th>Courses</th>
                                        </c:if>

                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="user" items="${allUser}" varStatus="loop">
                                    <tr>
                                        <td>${loop.count}</td>
                                        <td>${user.firstname}
                                        </td>
                                        <td>${user.lastname}</td>
                                        <td>
                                            <ul class="list-inline">
                                                <li class="list-inline-item">
                                                    <c:if test="${user.image_path!=null}">
                                                        <img alt="Avatar" class="table-avatar" width="40px"
                                                             src="data:image/png;base64,${user.image_path}">
                                                    </c:if>
                                                </li>
                                            </ul>
                                        </td>
                                        <td>${user.role}</td>
                                        <c:if test="${id==1}">
                                            <td class="project-actions text-center">
                                                <a class="btn btn-info btn-sm" href="">
                                                    <i class="fas fa-pencil-alt">
                                                    </i>
                                                    Edit
                                                </a>
                                                <a class="btn btn-danger btn-sm" href="">
                                                    <i class="fas fa-trash-alt">
                                                    </i>
                                                    Delete
                                                </a>
                                                <a class="btn btn-success btn-sm" href="/emails/${user.id}">
                                                    <i class="fas fa-paper-plane">
                                                    </i>
                                                    Send Email
                                                    <Message></Message>

                                                </a>
                                                <c:choose>
                                                    <c:when test="${user.is_blocked == true}">
                                                        <a class="btn btn-success btn-sm"
                                                           href="/admin/users/unblock/${user.id}">
                                                            <i class="fas fa-unlock"></i>
                                                            Unblock
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a class="btn btn-danger btn-sm"
                                                           href="/admin/users/block/${user.id}">
                                                            <i class="fas fa-lock"></i>
                                                            Block
                                                        </a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </c:if>
                                        <c:choose>
                                            <c:when test="${id==2}">
                                                <td>
                                                    <ul class="list-inline">
                                                        <c:forEach var="course" items="${user.courseDto}">
                                                            <li class="list-inline-item">
                                                                <p>${course.name}</p>
                                                            </li><br>
                                                        </c:forEach>
                                                    </ul>
                                                </td>
                                            </c:when>
                                        </c:choose>
                                    </tr>
                                    </c:forEach>
                                    </tfoot>
                                </table>
                                <div style="padding-left:40%;padding-right:40%;padding-top:1rem">
                                    <nav aria-label="Page navigation example">
                                        <ul class="pagination">
                                            <%
                                                int size = (int) request.getAttribute("size");
                                                int floor = (size / 3);
                                                int h = size % 3;
                                                if (h > 0) {
                                                    floor += 1;
                                                }
                                                int floor2 = 1;
                                                for (int j = 1; j <= floor; j++) {
                                                    out.print("<li class=\"page-item \"><a class=\"page-link\" href=\"users?pageid=" + floor2 + "\">" + floor2 + "</a></li>\n");
                                                    floor2 += 1;
                                                }
                                            %>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                            <!-- /.card-body -->
                        </div>
                        <!-- /.card -->
                        <!-- /.card -->
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container-fluid -->
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

