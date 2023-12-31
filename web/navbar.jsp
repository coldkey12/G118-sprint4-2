<%@ page import="kz.bitlab.models.User" %><%--
  Created by IntelliJ IDEA.
  User: coldkey
  Date: 06.10.2023
  Time: 19:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a class="navbar-brand">BITLAB</a>
        <button name="sign_button" class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item" style="margin-left: 470px">
                    <a class="nav-link" href="/sign-in">Sign in</a>
                </li>
                <li class="nav-item" style="margin-left: 100px">
                    <a class="nav-link" href="/">All products</a>
                </li>
                <li class="nav-item" style="margin-left: 100px">
                    <a class="nav-link" href="/blogs">Blogs</a>
                </li>
            </ul>
            <%
                User user = (User) session.getAttribute("currentUser");
                if(user != null){
            %>
            <form class="d-flex" action="/sign-out" method="post" style="margin-left: 500px">
                <h1><%=user.getFullName()%></h1>
                <button class="btn btn-outline-success" type="submit">SIGN OUT</button>
            </form>
            <%
                }
            %>
        </div>
    </div>
</nav>
</html>
