<%--
  Created by IntelliJ IDEA.
  User: utilisateur
  Date: 08/02/2022
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="presentation">

    <c:choose>
        <c:when test="${ menu }"><h2>Rameurs Connectés - Menu</h2></c:when>
        <c:when test="${ courses }"><h2>Rameurs Connectés - Courses</h2></c:when>
        <c:when test="${ stats }"><h2>Rameurs Connectés - Statistiques</h2></c:when>
        <c:when test="${ entrainement }"><h2>Rameurs Connectés - Entrainements</h2></c:when>
    </c:choose>


    <!-- Partie déconnexion -->
    <form method="get" action="DeconnexionControleur">
        <input type="submit" value="Déconnexion" name="action">
    </form>

</section>