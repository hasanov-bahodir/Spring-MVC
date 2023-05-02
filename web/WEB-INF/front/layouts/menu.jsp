<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<div id="preloader"></div>--%>


<div class="navigation navigation_two">
    <div class="container">
        <div class="logo">
            <a href="/">
<%--                <img class="img-responsive" src="/ducat/newPdpLogo.svg" alt="" style="width: 140px">--%>
            </a>
        </div>
        <div id="navigation" class="menu-wrap">
            <ul>
                <li class="${homePage}"><a href="/"> Home</a>
                <li class="${coursePage}"><a href="/coursePage"> Courses</a>
                </li>
                <c:if test="${authUser!=null && authUser.role.id==3}">
                    <li class="${myCourse}"><a href="/courses/mycourses"> My Courses</a>
                    </li>
                </c:if>
            </ul>
        </div>
        <!-- End: navigation  -->
        <div class="header_sign">
            <c:if test="${authUser==null}">
                <a href="/auth/login" class="more-link"> Sign in</a>
                <a href="/auth/register" class="more-link"> Sign Up</a>
            </c:if>
            <c:if test="${authUser!=null && authUser.role.id==3}">
                <a href="/user" class="more-link"> Profile</a>
                <a href="/auth/logout" class="more-link"> Logout</a>
            </c:if>
            <c:if test="${authUser!=null && authUser.role.id==1}">
                <a href="/user" class="more-link"> Dashboard</a>
                <a href="/auth/logout" class="more-link"> Logout</a>
            </c:if>
            <c:if test="${authUser!=null && authUser.role.id==2}">
                <a href="/user" class="more-link"> Mentor Profile</a>
                <a href="/auth/logout" class="more-link"> Logout</a>
            </c:if>
        </div>
        <!-- End: Sign in -->
    </div>
    <!--/ container -->
</div>


