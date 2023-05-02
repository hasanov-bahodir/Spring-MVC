<%@ page import="uz.pdp.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <%@ include file="../layouts/link.html" %>
    <style>
        .card {
            box-shadow: rgba(0, 0, 0, 0.15) 0px 5px 15px 0px;
            padding: 30px;
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 500px;
        }

        .card .text{
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .card .text img{
            height: 170px;
            border-radius: 50%;
            margin-bottom:10px;
        }

        .card .text h3{
            font-size: 40px;
            font-weight: 400;
        }

        .card .text p:nth-of-type(1){
            color: rgb(35, 182, 219);
            font-size: 15px;
            margin-top: -5px;
        }

        .card .text p:nth-of-type(2){
            margin: 10px;
            width: 90%;
            text-align: center;
        }

        .card .links{
            width: 30%;
            display: flex;
            justify-content: space-evenly;
        }

        .card .links i{
            color: rgb(35, 182, 219);
            font-size: 20px;
            cursor: pointer;
        }

        .card .links i:hover{
            color:rgb(29, 157, 189) ;
        }
    </style>
</head>
<body>
<%@ include file="../layouts/menu.jsp" %>
<div class="container">
    <div class="col-md-6" style="padding-top: 5rem;">
        <div class="card">
            <div class="text">
                <img src="data:image/png;base64,${user.userDetail.image_path}" alt="" style="border-radius: 50%;width: 170px;height: 170px">
                <h3>${user.userDetail.firstName} ${user.userDetail.lastName}</h3>
                <p>Role | ${user.role.name}</p>
            </div>
            <div class="links">
                <a class="more-link" href="/user/profile/edit/${user.email}"><i class="fas fa-edit"></i></a>
            </div>
        </div>
    </div>
    <div class="col-md-6"></div>
</div>
<%@ include file="../layouts/scripts.html" %>
</body>
</html>
