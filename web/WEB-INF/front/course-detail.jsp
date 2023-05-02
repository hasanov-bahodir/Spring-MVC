<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${course.name}</title>
    <%@ include file="layouts/link.html" %>
    <style>
        .content {
            display: none;
            overflow: hidden;
        }
    </style>
</head>
<body>
<%@ include file="layouts/menu.jsp" %>

<!-- header -->
<header class="single-header"
        style="background-image: linear-gradient(rgba(0,0,0,0.6),rgba(0,0,0,0.6),rgba(0,0,0,0.6)),url(data:image/png;base64,${course.image_path});
                background-position: center;
                background-repeat: no-repeat;
                background-size: cover">
    <!-- Start: Header Content -->
    <div class="container">
        <div class="row text-center wow fadeInUp" data-wow-delay="0.5s">
            <div class="col-sm-12">
                <!-- Headline Goes Here -->
                <h3>${course.name}</h3>
                <h4><a href="/"> Home </a> <span> &vert; </span> Courses </h4>
            </div>
        </div>
        <!-- End: .row -->
    </div>
    <!-- End: Header Content -->
</header>
<!--/. header -->
<!--/
==================================================-->


<!-- Start : Blog Page Content
==================================================-->
<div class="single_course">
    <div class="container">
        <div class="row">
            <!-- Blog Area -->
            <div class="col-lg-8 col-sm-12">
                <div class="sing_course_wrap">
                    <div class="sin_course_img">
                        <img class="img-responsive" src="data:image/png;base64,${course.image_path}" alt="">
                        <span>${course.name}</span>
                    </div>
                    <h4>${course.name}</h4>


                    <div class="course_tab">
                        <ul class="nav nav-pills" id="pills-tab" role="tablist">
                            <li class="nav-item">
                                <button class="nav-link active" id="pills-discription-tab" data-bs-toggle="tab"
                                        data-bs-target="#pills-discription" role="tab" aria-controls="pills-discription"
                                        aria-selected="true" type="button">Discription
                                </button>
                            </li>
                            <li class="nav-item">
                                <button class="nav-link" id="pills-curriculum-tab" data-bs-toggle="tab"
                                        data-bs-target="#pills-curriculum" role="tab" aria-controls="pills-curriculum"
                                        aria-selected="false" type="button">Modules
                                </button>
                            </li>
                            <li class="nav-item">
                                <button class="nav-link" id="pills-instructors-tab" data-bs-toggle="tab"
                                        data-bs-target="#pills-instructors" role="tab" aria-controls="pills-instructors"
                                        aria-selected="false" type="button">Instructors
                                </button>
                            </li>
                            <li class="nav-item">
                                <button class="nav-link" id="pills-reviews-tab" data-bs-toggle="tab"
                                        data-bs-target="#pills-reviews" role="tab" aria-controls="pills-reviews"
                                        aria-selected="false" type="button">Comments
                                </button>
                            </li>
                        </ul>
                        <div class="tab-content course_tab_cont" id="pills-tabContent">
                            <div class="tab-pane fade show active" id="pills-discription"
                                 aria-labelledby="pills-discription-tab" role="tabpanel">
                                <span> Description : </span>
                                <p>
                                    ${course.description}
                                </p>
                            </div>
                            <!-- End:: discription-tab  -->
                            <div class="tab-pane fade" id="pills-curriculum" aria-labelledby="pills-curriculum-tab"
                                 role="tabpanel">
                                <span> Modules : </span>
                                <c:if test="${modules.size()<1}">
                                    <p>No Modules Yet</p>
                                </c:if>

                                <c:forEach var="module" items="${modules}">
                                    <button type="button"
                                            style="display: flex;width: 100%;color:#000; border-radius: 10px; background-color: #00938D; color: #fff; border: none; font-size: 1rem"
                                            class="btn btn-outline-success collapsible mb-2">${module.name}</button>
                                    <div class="content">
                                        <br>
                                        <c:forEach var="lesson" items="${module.lessonDtos}">
                                            <a href="/lessons/course/${course.id}/watch/${lesson.id}"><p
                                                    style="font-weight: 500;font-size: 18px"> ${lesson.name} </p></a>
                                            <hr>
                                        </c:forEach>
                                        <c:if test="${module.lessonDtos.size()<1}">
                                            <p>No Lessons Yet</p>
                                        </c:if>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="tab-pane fade" id="pills-instructors"
                                 aria-labelledby="pills-instructors-tab" role="tabpanel">
                                <span> Course Instructors : </span>
                                <div class="row">
                                    <c:forEach var="author" items="${course.authors}">
                                        <div class="col-md-4">
                                            <div class="card text-center">
                                                <div class="card-header">
                                                    <a href="/mentor/${author.id}"> <img
                                                            src="data:image/png;base64,${author.image_path}"
                                                            alt="image" height="100px" width="100px"
                                                            style="border-radius: 50%"></a>
                                                </div>
                                                <div class="card-body">
                                                    <h5 class="card-title">${author.firstname} ${author.lastname}</h5>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="pills-reviews" aria-labelledby="pills-reviews-tab"
                                 role="tabpanel">
                                <span> Students Reviews :</span>
                                <div class="comments-section-title">
                                    <h4>${courseComments.size()} Comments</h4>
                                </div>
                                <!-- .section-title -->
                                <ul style="display: block; background-color: #F8F7F3">
                                    <c:forEach items="${courseComments}" var="commet">
                                        <li style="margin-bottom: 10px;padding:2rem; background-color: #E3EEEA">
                                            <div class="comment">
                                                <div class="comment_imgg">
                                                    <img src="data:image/png;base64,${commet.userDto.image_path}"
                                                         style="border-radius:50%;width:80px;height: 80px"
                                                         alt="" class="comment-avatar">
                                                </div>
                                                <div class="comment_cont_wrp">
                                                    <div class="commenter-title">
                                                        <a href=""
                                                           class="comments_name">${commet.userDto.firstname} ${commet.userDto.lastname}</a>
                                                    </div>
                                                    <p>${commet.body}</p>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                                <!--/ col-md-12  -->
                                <div class="review_form">
                                    <span> Write A Review</span>
                                    <form method="post" action="/comments/send/${course.id}" id="contact-form">
                                        <div class="row">
                                            <div class="col-lg-12 col-sm-12 rev_textarea">
                                                <textarea class="con-field" name="message" id="rmessage" rows="6"
                                                          placeholder="Your Comment"></textarea>
                                            </div>
                                            <div class="col-lg-12 col-sm-12 submit-area">
                                                <input type="submit" class="submit-contact" value="Send">
                                                <div id="msg" class="message"></div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <!--/ article -->
            </div>
            <!--/ Left Side Area -->

            <!-- Widget Area -->
            <div class="col-lg-4 col-sm-12 single_curs_right">
                <!-- Widget Course Details -->
                <aside class="widget-course-details">
                    <h2> Price <span>
                        <c:if test="${course.price>0}">$${course.price}</c:if>
                        <c:if test="${course.price<1}">Free</c:if></span></h2>
                    <div class="course-detail-list">
                        <span> <i class="far fa-journal-whills"></i>Modules </span>
                        <span> ${course.module_count} </span>
                    </div>
                    <div class="course-detail-list">
                        <span> <i class="far fa-journal-whills"></i>Lessons </span>
                        <span> ${course.lesson_count} </span>
                    </div>
                    <div class="course-detail-list">
                        <span> <i class="far fa-layer-group"></i>Students </span>
                        <span> ${course.student_count} </span>
                    </div>
                    <c:if test="${isEnrolled>0}">
                        <a href="/courses/continuestudy/${course.id}" class="more-link"> Continue Study</a>
                    </c:if>
                    <c:if test="${isEnrolled<1}">
                        <c:if test="${authUser!=null && authUser.role.id!=1}">
                            <form action="/courses/enroll/${course.id}" method="post">
                                <input type="submit" class="submit-contact" value="Enroll Now">
                            </form>
                        </c:if>
                        <c:if test="${authUser==null}">
                            <form action="/courses/enroll/${course.id}" method="post">
                                <input type="submit" class="submit-contact" value="Enroll Now">
                            </form>
                        </c:if>
                    </c:if>

                </aside>
                <!-- Widget Course Details /- -->


            </div>
            <!-- Widget Area /- -->
        </div>
    </div>
    <!-- Container /- -->
</div>

<script>
    var coll = document.getElementsByClassName("collapsible");
    var i;

    for (i = 0; i < coll.length; i++) {
        coll[i].addEventListener("click", function () {
            this.classList.toggle("active");
            var content = this.nextElementSibling;
            if (content.style.display === "block") {
                content.style.display = "none";
            } else {
                content.style.display = "block";
            }
        });
    }
</script>

<%@ include file="layouts/footer.html" %>
<%@ include file="layouts/scripts.html" %>
</body>
</html>
