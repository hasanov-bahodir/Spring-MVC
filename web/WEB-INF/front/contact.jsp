<%--
  Created by IntelliJ IDEA.
  User: koh
  Date: 2/23/22
  Time: 12:13 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contact</title>
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
                <h3>Contact Us</h3>
                <h4><a href="index.html"> Home </a> <span> &vert; </span> Contact </h4>
            </div>
        </div>
        <!-- End: .row -->
    </div>
    <!-- End: Header Content -->
</header>
<!--/. header -->
<!--/
==================================================-->


<!-- Start:  contact US  Section
==================================================-->
<section class="contact-section">
    <div class="container">
        <div class="contact_wrp">
            <div class="row">
                <div class="col-md-5 col-lg-4 col-sm-12">
                    <div class="contact_pg_address">
                        <h3>Hello there <br>got a project?</h3>
                        <div class="single_con_add">
                            <a href="contact.html#"><i class="pe-7s-home"></i></a>
                            <p>Company Address :</p>
                            <span>3567 New Alaska, <br> Washington, United State</span>
                        </div>
                        <div class="single_con_add">
                            <a href="contact.html#"><i class="pe-7s-mail-open-file"></i></a>
                            <p>Email Address : </p>
                            <span>example@email .com</span>
                            <span>exmtwo@email .com</span>
                        </div>
                        <div class="single_con_add">
                            <a href="contact.html#"><i class="pe-7s-headphones"></i></a>
                            <p>Contact Us :</p>
                            <span>+77-00-222-1111</span>
                            <span>+88-00-222-3333</span>
                        </div>
                        <div class="contact_social">
                            <a href="contact.html#"><i class="fab fa-facebook-f"></i></a>
                            <a href="contact.html#"><i class="fab fa-twitter"></i></a>
                            <a href="contact.html#"><i class="fab fa-instagram"></i></a>
                            <a href="contact.html#"><i class="fab fa-pinterest-p"></i></a>
                        </div>
                    </div>
                </div>
                <div class="col-md-7 col-lg-8 col-sm-12 inner-contact">
                    <!--  Contact Form  -->
                    <div class="contact-form">
                        <h3>Send Us Message</h3>
                        <form method="post" action="https://santhemes.com/tidytheme/aducat/mailer.php"
                              id="contact-form">
                            <div class="row">
                                <div class="col-lg-6 col-sm-12">
                                    <input class="con-field" name="name" id="name" type="text" placeholder="Name">
                                </div>
                                <div class="col-lg-6 col-sm-12">
                                    <input class="con-field" name="email" id="email2" type="text"
                                           placeholder="Email">
                                </div>
                                <div class="col-lg-6 col-sm-12">
                                    <input class="con-field" name="phone" id="phone" type="text"
                                           placeholder="Phone">
                                </div>
                                <div class="col-lg-6 col-sm-12">
                                    <input class="con-field" name="website" id="website" type="text"
                                           placeholder="Website">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-12 col-sm-12">
                                        <textarea class="con-field" name="message" id="message2" rows="6"
                                                  placeholder="Your Message"></textarea>
                                    <div class="submit-area">
                                        <input type="submit" class="submit-contact" value="submit Now">
                                        <div id="msg" class="message"></div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <!-- End:Contact Form  -->
                </div>

                <div class="col-sm-12 map-container">
                    <iframe
                            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3765.023938894304!2d-81.38341548467582!3d19.324767486945657!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x8f2587a9a0b98737%3A0x38fe616bb6df631f!2s638+W+Bay+Rd%2C+Cayman+Islands!5e0!3m2!1sen!2sus!4v1549260798680"></iframe>
                </div>
            </div>
            <!-- row /- -->
        </div>
    </div>
    <!-- container /- -->
</section>
<%@ include file="layouts/footer.html" %>
<%@ include file="layouts/scripts.html" %>
</body>
</html>
