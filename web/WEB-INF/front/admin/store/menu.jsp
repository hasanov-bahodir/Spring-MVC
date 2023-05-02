<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<div class="preloader flex-column justify-content-center align-items-center">--%>
<%--    <img class="animation__shake" src="/css/dist/img/AdminLTELogo.png" alt="AdminLTELogo" height="60" width="60">--%>
<%--</div>--%>

<!-- Navbar -->
<nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
        </li>
    </ul>

    <!-- Right navbar links -->
</nav>
<!-- /.navbar -->

<!-- Main Sidebar Container -->
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="/" class="brand-link">
        <img src="/css/dist/img/AdminLTELogo.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3"
             style="opacity: .8">
        <c:if test="${authUser.role.id==1}">
            <span class="brand-text font-weight-light">Administration</span>
        </c:if>
        <c:if test="${authUser.role.id==2}">
            <span class="brand-text font-weight-light">Mentor panel</span>
        </c:if>

    </a>

    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar user panel (optional) -->
        <!-- SidebarSearch Form -->

        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                <!-- Add icons to the links using the .nav-icon class
                     with font-awesome or any other icon font library -->
                <li class="nav-item menu-close">
                    <a href="/admin" class="nav-link ${adminActive}">
                        <i class="nav-icon fas fa-tachometer-alt"></i>
                        <p>
                            Dashboard
                        </p>
                    </a>
                </li>
                <li class="nav-item menu-<c:if test='${activeCourseClose!=null}'>${activeCourseClose}</c:if>">
                    <a href="#" class="nav-link ${activeCourseMenu}">
                        <i class="nav-icon fas fa-laptop-code"></i>
                        <p>
                            Courses
                            <i class="right fas fa-angle-left"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="/courses" class="nav-link ${activeCourse}">
                                <i class="fas fa-laptop-code nav-icon"></i>
                                <p>Courses</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="/courses/create" class="nav-link ${activeCourseCreate}">
                                <i class="fas fa-plus nav-icon"></i>
                                <p>Create Course</p>
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="nav-item menu-<c:if test='${activeModuleClose!=null}'>${activeModuleClose}</c:if>">
                    <a href="#" class="nav-link ${activeModuleMenu}">
                        <i class="nav-icon fas fa-chalkboard"></i>
                        <p>
                            Modules
                            <i class="right fas fa-angle-left"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="/modules" class="nav-link ${activeModule}">
                                <i class="fas fa-chalkboard nav-icon"></i>
                                <p>Modules</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="/modules/create" class="nav-link ${activeModuleCreate}">
                                <i class="fas fa-plus nav-icon"></i>
                                <p>Create Module</p>
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="nav-item menu-<c:if test='${activeLessonClose!=null}'>${activeLessonClose}</c:if>">
                    <a href="#" class="nav-link ${activeLessonSide}">
                        <i class="nav-icon fas fa-laptop-code"></i>
                        <p>
                            Lessons
                            <i class="right fas fa-angle-left"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="/lessons" class="nav-link ${activeLesson}">
                                <i class="fas fa-laptop-code nav-icon"></i>
                                <p>Lessons</p>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="/lessons/create" class="nav-link ${activeLessonCreate}">
                                <i class="fas fa-plus nav-icon"></i>
                                <p>Create Lessons</p>
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="nav-item menu-<c:if test='${activeCourseUserClose!=null}'>${activeCourseUserClose}</c:if>">
                    <a href="#" class="nav-link ${adminUserMenuActive}">
                        <i class="nav-icon fas fa-users"></i>
                        <p>
                            Users
                            <i class="right fas fa-angle-left"></i>
                        </p>
                    </a>

                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="/admin/users" class="nav-link ${adminUserActive}">
                                <i class="fas fa-users nav-icon"></i>
                                <p>Users</p>
                            </a>
                        </li>
                        <c:if test="${authUser.role.id==1}">
                            <li class="nav-item">
                                <a href="/admin/users/create" class="nav-link ${adminCreateActive}">
                                    <i class="fas fa-plus nav-icon"></i>
                                    <p>Add User</p>
                                </a>
                            </li>
                        </c:if>
                    </ul>

                </li>
                <li class="nav-item menu-close">
                    <a href="/comments" class="nav-link ${commentActive}">
                        <i class="nav-icon fas fa-comment"></i>
                        <p>
                            Comments
                        </p>
                    </a>
                </li>
                <li class="nav-item menu-close">
                    <a href="/user/profiles" class="nav-link ${profileActive}">
                        <i class="nav-icon fas fa-user"></i>
                        <p>
                            Profile
                        </p>
                    </a>
                </li>
                <li class="nav-item menu-close">
                    <a href="/auth/logout" class="nav-link">
                        <i class="nav-icon fas fa-sign-in-alt"></i>
                        <p>
                            Logout
                        </p>
                    </a>
                </li>
            </ul>
        </nav>
        <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
</aside>