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
                        <h1>Modules</h1>
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
                                <h3 class="card-title">Modules List</h3>
                                <form method="GET" action="/modules" style="float:right">
                                    <div class="form-group">
                                        <input type="text" placeholder="search" name="search" class="form-control" >
                                    </div>
                                </form>
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body">
                                <table id="example2" class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Name</th>
                                        <th>description</th>
                                        <th>Course</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="module" items="${modules}" varStatus="loop">
                                    <tr>
                                        <td>${loop.count}</td>
                                        <td>${module.name}</td>
                                        <td>${module.description}
                                        </td>
                                        <td>${module.courseName}
                                        </td>
                                        <td><a class="btn btn-info btn-sm" href="/modules/update/${module.id}">
                                            <i class="fas fa-pencil-alt">
                                            </i>
                                            Edit
                                        </a>
                                            <a class="btn btn-danger btn-sm" href="/modules/delete/${module.id}">
                                                <i class="fas fa-trash-alt">
                                                </i>
                                                Delete
                                            </a></td>
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
                                                    out.print("<li class=\"page-item \"><a class=\"page-link\" href=\"modules?pageid=" + floor2 + "\">" + floor2 + "</a></li>\n");
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

