<%--
  Created by IntelliJ IDEA.
  User: utilisateur
  Date: 04/03/2022
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Projet Tutoré - Course Attente</title>
    <link rel="stylesheet" href="CSS/menu_operation.css">
</head>
<body>
    <!--Affichage de la barre de navigation sur la gauche de la page-->
    <%@include file="navigation.jsp" %>

    <!--Affichage du nom de la page ainsi que du bouton de déconnexion-->
    <%@include file="deconnexion.jsp"%>

    <br>

    <section class="options">
        <p>${resultat}</p>
    </section>


</body>
</html>
