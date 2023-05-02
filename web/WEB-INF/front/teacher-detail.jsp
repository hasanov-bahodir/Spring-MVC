<%--
  Created by IntelliJ IDEA.
  User: koh
  Date: 2/23/22
  Time: 5:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${mentor.firstname} ${mentor.lastname}</title>
    <%@ include file="layouts/link.html" %>
</head>
<body>
<%@ include file="layouts/menu.jsp" %>
<header class="single-header">
    <!-- Start: Header Content -->
    <div class="container">
        <div class="row text-center wow fadeInUp" data-wow-delay="0.5s">
            <div class="col-sm-12">
                <!-- Headline Goes Here -->
                <h3>Instructor Details</h3>
                <h4><a href="index.html"> Home </a> <span> &vert; </span> Instructor Details </h4>
            </div>
        </div>
        <!-- End: .row -->
    </div>
    <!-- End: Header Content -->
</header>
<section class="single-teacher-section">
    <div class="container">
        <div class="row">
            <div class="col-lg-4 col-md-5 col-sm-12">
                <div class="teacher_left">
                    <div class="teacher_avatar">
                        <img src="data:image/png;base64,${mentor.image_path}" alt="" height="293px" width="293px">
                        <h3> ${mentor.firstname} ${mentor.lastname} </h3>
                        <span> </span>
                        <span> ${mentor.email}</span>
                        <br>
                    </div>
                    <div class="teacher_achieve">
                    </div>
                </div>
            </div>
            <!-- /. col-lg-4 col-md-5 col-sm-12 -->

            <div class="col-lg-8 col-md-7 col-sm-12 teach_course_tab">
                <ul class="nav nav-tabs" id="myTab" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active" id="course-tab" data-bs-toggle="tab"
                                data-bs-target="#course" type="button" role="tab" aria-controls="course"
                                aria-selected="true">Course List
                        </button>
                    </li>
                </ul>
                <!-- End:  nav-tabs -->

                <div class="tab-content" id="myTabContent">
                    <div class="tab-pane fade show active" id="course" role="tabpanel" aria-labelledby="course-tab">
                        <div class="row teacher_course">
                            <c:forEach var="course" items="${mentor.courseDtos}">
                                <div class="col-lg-6 col-sm-12">
                                    <a href="/courses/detail/${course.id}">
                                        <div class="feat_course_item">
                                            <a href="/courses/detail/${course.id}"> <img
                                                    src="data:image/png;base64,${course.image_path}" alt="image"></a>
                                            <div class="feat_cour_price">
                                                <span class="feat_cour_tag"> ${course.name} </span>
                                                <span class="feat_cour_p"> <c:if
                                                        test="${course.price>0}">$${course.price}</c:if><c:if
                                                        test="${course.price<1}">Free</c:if> </span>
                                            </div>
                                            <a href="/courses/detail/${course.id}"><h4
                                                    class="feat_cour_tit"> ${course.description} </h4></a>
                                            <div class="feat_cour_lesson">
                                                <span class="feat_cour_less"> <i
                                                        class="fas fa-sticky-note"></i> ${course.lesson_count} lessons </span>
                                                <span class="feat_cour_stu"> <i
                                                        class="fas fa-user"></i> ${course.student_count} Students </span>
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
                        </div>
                    </div>

                </div>
            </div>
            <!-- /. col-lg-8 col-md-7 col-sm-12 -->
        </div>
        <!-- /. row -->
    </div>
    <!-- /. container -->
</section>
<%@ include file="layouts/footer.html" %>
<%@ include file="layouts/scripts.html" %>
</body>
</html>
