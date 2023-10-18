<%@ page import="kz.bitlab.models.Item" %>
<%@ page import="java.util.List" %>
<%@ page import="kz.bitlab.models.City" %><%--
  Created by IntelliJ IDEA.
  User: coldkey
  Date: 06.10.2023
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</head>

<body>
<%@include file="navbar.jsp"%>
    <div class="col-8 mx-auto">
        <h1 class="text-center">WELCOME TO BITLAB SHOP</h1>
        <h5 class="text-center">Electronic devices with high quality and service</h5>
        <!-- Button trigger modal -->
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addItemModal">
            + ADD ITEM
        </button>

        <form action="/add-item" method="post">
        <div class="modal fade" id="addItemModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input type="text" name="item_name" placeholder="Insert name..." class="form-control mt-1">
                        <input type="text" name="item_description" placeholder="Insert description..." class="form-control mt-1">
                        <input type="number" name="item_price" placeholder="Insert price..." class="form-control mt-1">
                        <select name="item_city_id" class="form-select">
                            <%
                                List<City> cities = (List<City>) request.getAttribute("cities");
                                for (City city : cities){
                            %>
                            <option value="<%=city.getId()%>"><%=city.getName() + " / " + city.getCode()%></option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button class="btn btn-primary">ADD ITEM</button>
                    </div>
                </div>
            </div>
        </div>
        </form>
        <div class="d-flex flex-wrap justify-content-center">
            <%
                List<Item> items = (List<Item>) request.getAttribute("items");
                for (Item item : items) {
            %>
            <div class="card col-3 m-2">
                <div class="card-header"><%=item.getName()%></div>
                <div class="card-body">
                    <h5>$<%=item.getPrice()%></h5>
                    <p><%=item.getDescription()%></p>
                    <p><%=item.getCity().getName() + " / " + item.getCity().getCode()%></p>
                    <img src="img.png" class="img-thumbnail" alt="...">
                    <a href="#" class="btn btn-primary w-100" style="margin-bottom: 10px">BUY NOW</a><br>

                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteItemModal<%=item.getId()%>">
                        - DELETE ITEM
                    </button>

                    <form action="/delete-item" method="post">
                        <div class="modal fade" id="deleteItemModal<%=item.getId()%>" tabindex="-1" aria-labelledby="deleteItemModalLabel<%=item.getId()%>" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" id="deleteItemModalLabel<%=item.getId()%>">Delete Item</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <input type="hidden" name="id" value="<%=item.getId()%>">
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                        <button class="btn btn-danger">DELETE ITEM</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>

                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#updateItemModal<%=item.getId()%>">
                        - UPDATE ITEM
                    </button>

                    <form action="/update-item" method="post">
                        <div class="modal fade" id="updateItemModal<%=item.getId()%>" tabindex="-1" aria-labelledby="updateItemModalLabel<%=item.getId()%>" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" id="updateItemModalLabel<%=item.getId()%>">Delete Item</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <input name="item_name_update" type="text" class="form-control mt-1"
                                                placeholder="Insert name...">
                                        <input name="item_description_update" type="text" class="form-control mt-1"
                                               placeholder="Insert description...">
                                        <input name="item_price_update" type="number" class="form-control mt-1"
                                               placeholder="Insert price...">
                                        <select name="item_city_id_update" class="form-select">
                                            <%
                                                List<City> cities1 = (List<City>) request.getAttribute("cities");
                                                for (City city : cities1){
                                            %>
                                            <option value="<%=city.getId()%>"><%=city.getName() + " / " + city.getCode()%></option>
                                            <%
                                                }
                                            %>
                                        </select>
                                        <input type="hidden" name="id" value="<%=item.getId()%>">
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                        <button class="btn btn-primary">UPDATE ITEM</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>
