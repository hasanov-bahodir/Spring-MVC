<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Courses</title>
    <%@ include file="layouts/link.html" %>
</head>
<body>
<%@ include file="layouts/menu.jsp" %>
<!-- Start: Hero Section
==================================================-->
<!-- End: Hero Section
==================================================-->
<!-- Start: Popular Categories Section
==================================================-->
<section class="category-section">
    <!-- Container -->
    <!--/ Container - -->
</section>
<!--   End: Popular Categories Section
==================================================-->


<!-- Start: Featured Courses Section
==================================================-->
<section class="feat-course-section">
    <div class="container">
        <!-- Start: Heading -->
        <div class="base-header" style="margin-bottom: 2rem">
            <h3> Our Courses </h3>
        </div>
        <!-- End: Heading -->
        <div class="row">

            <c:forEach var="course" items="${courses}">

                <div class="col-lg-4 col-md-6 col-sm-12">
                    <a href="/courses/detail/${course.id}">
                        <div class="feat_course_item">
                            <a href="/courses/detail/${course.id}"> <img
                                    src="data:image/png;base64,${course.image_path}" alt="image"></a>
                            <div class="feat_cour_price">
                                <span class="feat_cour_tag"> ${course.name} </span>
                                <span class="feat_cour_p"> <c:if test="${course.price>0}">$${course.price}</c:if><c:if
                                        test="${course.price<1}">Free</c:if> </span>
                            </div>
                            <a href="/courses/detail/${course.id}"><h4 class="feat_cour_tit"> ${course.description} </h4></a>
                            <div class="feat_cour_lesson">
                                <span class="feat_cour_less"> <i class="fas fa-sticky-note"></i> ${course.lesson_count} lessons </span>
                                <span class="feat_cour_stu"> <i class="fas fa-user"></i> ${course.student_count} Students </span>
                            </div>
                            <div class="feat_cour_rating">
                            <span class="feat_cour_rat">
                            </span>
                                <a href="/courses/detail/${course.id}"> <i class="arrow_right"></i> </a>
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach>
            <!-- /. col-lg-4 col-md-6 col-sm-12-->

            <!-- /. col-lg-4 col-md-6 col-sm-12-->
        </div>
        <!-- /. row -->
    </div>
    <!-- /. container -->
</section>
<!-- End: Featured Courses Section
==================================================-->


<!-- End: Newsletter Section
==================================================-->

<%@ include file="layouts/footer.html" %>
<%@ include file="layouts/scripts.html" %>
</body>
</html>
