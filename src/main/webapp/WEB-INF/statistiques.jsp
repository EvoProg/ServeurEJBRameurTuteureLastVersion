<%--
  Created by IntelliJ IDEA.
  User: evoprog
  Date: 03/02/2022
  Time: 20:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr" dir="ltr">

<head>
    <meta charset="utf-8">
    <title>Projet Tutoré - Menu</title>
    <link rel="stylesheet" href="CSS/menu_operation.css">
</head>

<body>

    <%@include file="navigation.jsp"%>

    <%@include file="deconnexion.jsp"%>

    <br>
        <div class="container">
            <section class="boxcolonne">
                <img src="DistanceGraphiqueControleur">
            </section>
            <section class="boxcolonne">
                <img src="CoupsGraphiqueControleur">
            </section>
        </div>

    <br>
        <div class="container">
            <section class="boxcolonne">
                <img src="PuissanceGraphiqueControleur">
            </section>
            <section class="boxcolonne">
                <img src="RythmeGraphiqueControleur">
            </section>
        </div>

    <br>
        <div class="container">
            <section class="boxcolonne">
                <img src="CalorieGraphiqueControleur">
            </section>
            <section class="boxcolonne">
                <img src="FrequenceGraphiqueControleur">
            </section>
        </div>

</body>
</html>

