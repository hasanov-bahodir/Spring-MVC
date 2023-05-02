<%@ page import="static uz.pdp.config.Constants.vimeo_url" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${lesson.course}</title>
    <%@ include file="../layouts/link.html" %>
</head>
<body>
<%@ include file="../layouts/menu.jsp" %>

<div class="single_course" style="margin-top: -3rem">
    <div class="container">
        <div class="row">
            <div style="margin-top: -3rem;float:right;padding-bottom: 2rem">
                <a style="width: 10%;" href="/courses/continuestudy/${courseId}" class="more-link">Back</a>
            </div>
            <div class="col-lg-12 col-sm-12">
                <div class="sing_course_wrap">
                    <iframe src="<%=vimeo_url%>${lesson.vide_path}" width="100%" height="560" frameborder="0"
                            webkitallowfullscreen mozallowfullscreen
                            allowfullscreen></iframe>
                    <br>

                    <h4>${lesson.name}</h4>


                    <div class="course_tab">
                        <ul class="nav nav-pills" id="pills-tab" role="tablist">
                            <li class="nav-item">
                                <button class="nav-link active" id="pills-discription-tab" data-bs-toggle="tab"
                                        data-bs-target="#pills-discription" role="tab" aria-controls="pills-discription"
                                        aria-selected="true" type="button">Description
                                </button>
                            </li>
                            <li class="nav-item">
                                <button class="nav-link" id="pills-curriculum-tab" data-bs-toggle="tab"
                                        data-bs-target="#pills-curriculum" role="tab" aria-controls="pills-curriculum"
                                        aria-selected="false" type="button">Tasks
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
                                    ${lesson.description}
                                </p>
                            </div>
                            <!-- End:: discription-tab  -->
                            <div class="tab-pane fade" id="pills-curriculum" aria-labelledby="pills-curriculum-tab"
                                 role="tabpanel">
                                <span> Tasks : </span>
                            </div>
                            <div class="tab-pane fade" id="pills-reviews" aria-labelledby="pills-reviews-tab"
                                 role="tabpanel">
                                <span> Students Reviews :</span>
                                <div class="comments-section-title">
                                    <h4>${lesssonComment.size()} Comments</h4>
                                </div>
                                <!-- .section-title -->
                                <ul style="display: block; background-color: #F8F7F3">
                                    <c:forEach items="${lesssonComment}" var="commet">
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
                                <!--/ .comments -->
                                <!--/ col-md-12  -->
                                <div class="review_form">
                                    <form method="post" action="/comments/send/lesson/${lesson.id}"
                                          id="contact-form">
                                        <div class="row">
                                            <div class="col-lg-12 col-sm-12 rev_textarea">
                                                    <textarea class="con-field" name="message" id="rmessage" rows="6"
                                                              placeholder="Your Comment"></textarea>
                                            </div>
                                            <div class="col-lg-12 col-sm-12 submit-area">
                                                <input type="submit" class="submit-contact" value="Submit">
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
<script>
    var iframe = document.querySelector('iframe');
    var player = new Vimeo.Player(iframe);

    player.on('play', function () {
        console.log('Played the video');
    });

    player.getVideoTitle().then(function (title) {
        console.log('title:', title);
    });
</script>
<%@ include file="../layouts/footer.html" %>
<%@ include file="../layouts/scripts.html" %>
</body>
</html>
