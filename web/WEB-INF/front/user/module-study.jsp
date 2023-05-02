<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${course.name}</title>
    <%@ include file="../layouts/link.html" %>
    <style>
        .content {
            display: none;
            overflow: hidden;
        }
    </style>
</head>
<body>
<%@ include file="../layouts/menu.jsp" %>


<div class="single_course">
    <div class="container">
        <div class="row">
            <!-- Blog Area -->
            <div class="col-lg-8 col-sm-12">
                <div class="sing_course_wrap">
                    <h4>${course.name} Modules</h4>


                    <c:forEach var="module" items="${modules}">
                        <button type="button" style="display: flex;width: 100%;color:#000; border-radius: 10px; background-color: #00938D; color: #fff; border: none; font-size: 1rem" class="btn btn-outline-success collapsible mb-2">${module.name}</button>
                        <div class="content">
                            <br>
                            <c:forEach var="lesson" items="${module.lessonDtos}">
                                <a href="/lessons/course/${course.id}/watch/${lesson.id}"> <p style="font-weight: 500;font-size: 18px"> ${lesson.name} </p><p style="float: right;margin-top: -3rem;color:#000; border-radius: 10px; background-color: #00938D; color: #fff; border: none; font-size: 0.8rem" class="btn btn-primary">START</p> </a>
                                <br>
                                <hr>
                            </c:forEach>
                            <c:if test="${module.lessonDtos.size()<1}">
                                <p>No Lessons Yet</p>
                            </c:if>
                        </div>
                    </c:forEach>

                </div>
                <!--/ article -->
            </div>
            <!--/ Left Side Area -->

            <!-- Widget Area -->
            <div class="col-lg-4 col-sm-12 single_curs_right">
                <!-- Widget Course Details -->
                <aside class="widget-course-details">
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
                        <span>${course.student_count}</span>
                    </div>
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

<%@ include file="../layouts/footer.html" %>
<%@ include file="../layouts/scripts.html" %>
</body>
</html>
