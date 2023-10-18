<%--
  Created by IntelliJ IDEA.
  User: coldkey
  Date: 13.10.2023
  Time: 19:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</head>
<body>
<%
    String emailError = request.getParameter("emailError");
    if(emailError!=null){
%>
<h1 class="text-danger text-center">INCORRECT EMAIL!</h1>
<%
    }
%>

<%
    String passwordError = request.getParameter("passwordError");
    if(passwordError!=null){
%>
<h1 class="text-danger text-center">INCORRECT PASSWORD!</h1>
<%
    }
%>

<%
    String regEmailError = request.getParameter("regEmailError");
    if(regEmailError!=null){
%>
<h1 class="text-danger text-center">EMAIL USED!</h1>
<%
    }
%>

<%
    String regPasswordError = request.getParameter("regPasswordError");
    if(regPasswordError!=null){
%>
<h1 class="text-danger text-center">PASSWORD ARE NOT THE SAME!</h1>
<%
    }
%>

<%
    String success = request.getParameter("success");
    if(success!=null){
%>
<h1 class="text-success text-center">SUCCESS!</h1>
<%
    }
%>


<div class="col-6 mx-auto">
<form action="/sign-in" method="post">
    <div class="mb-3">
        <label for="exampleInputEmail1" class="form-label">Email address</label>
        <input name="user_email" type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
        <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
    </div>
    <div class="mb-3">
        <label for="exampleInputPassword1" class="form-label">Password</label>
        <input name="user_password" type="password" class="form-control" id="exampleInputPassword1">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>

</form>
    <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#signUpModel">
        Make a new account
    </button>
    <form action="/sign-up" method="post">
    <div class="modal fade" id="signUpModel" tabindex="-1" aria-labelledby="signUpModel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="exampleInputEmail1" class="form-label">Email address</label>
                        <input name="user_email" type="email" class="form-control" id="exampleInputEmail2" aria-describedby="emailHelp">
                    </div>
                    <div class="mb-3">
                        <label for="exampleInputPassword1" class="form-label">Password</label>
                        <input name="user_password" type="password" class="form-control" id="exampleInputPassword3">
                    </div>
                    <div class="mb-3">
                        <label for="exampleInputPassword1" class="form-label">Repeat password</label>
                        <input name="user_re_password" type="password" class="form-control" id="exampleInputPassword4">
                    </div>
                    <div class="mb-3">
                        <label for="exampleInputPassword1" class="form-label">Fullname</label>
                        <input name="user_full_name" type="text" class="form-control" id="exampleInputPassword5">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button class="btn btn-primary">Create new account</button>
                </div>
            </div>
        </div>
    </div>
    </form>
</div>
</body>
</html>
