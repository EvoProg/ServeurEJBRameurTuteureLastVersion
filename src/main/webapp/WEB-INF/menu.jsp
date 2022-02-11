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
    <%@include file="navigation.jsp" %>

<div class="menu">
    <%@include file="deconnexion.jsp"%>

    <section class="field"></section>

    <!-- Partie Menu interaction entre le temps et la distance pour lancer une session -->
    <section class="options">
        <h3>Lancement d'une session</h3>
        <hr>

        <form class="test" action="MenuControleur" method="post">
            <div class="radio-options">
                <input type="radio" id="temps" name="opt" value="temps" required>
                <label for="temps">Temps</label>

                <input type="radio" id="distance" name="opt" value="distance" required>
                <label for="distance">Distance</label>

                <!-- Input caché contenant l'identifiant de l'utilisateur récupéré dans la servlet MenuControleur -->
                <input type="hidden" name="identifiant" value="${Utilisateur.getId()}">

                <input type="submit" name="action" value="Selection">
            </div>
        </form>

        <p class="message">${Message}</p>

        <section class="field"></section>

        <c:if test="${(temps == true || distance == true)}">
            <fieldset>
                <legend>Session : </legend>
                <form class="test" action="SessionControleur" method="post">

                    <c:if test="${rameurs.size() != 0}">
                        <c:forEach items="${rameurs}" var="rameur">
                            <label for="rameur${rameur.getId()}">Rameur n°${rameur.getId()}</label>
                            <input type="radio" id="rameur${rameur.getId()}" name="rameur" value="${rameur.getId()}" required>
                        </c:forEach>
                    </c:if>

                    <c:if test="${temps == true}">
                        <section class="field"></section>
                        <label class="temps_label">Temps (en secondes) : </label>
                        <input type="number" name="temps_s" id="temps_s" class="temps-txt-field" required min="60" max="1800">
                        <br>
                    </c:if>

                    <c:if test="${distance == true}">
                        <section class="field"></section>
                        <label class="distance_label">Distance (en mètres) : </label>
                        <input type="number"  name="distance_s" id="distance_s" class="distance-txt-field" required min="100" max="4000">
                        <br>
                    </c:if>

                    <section class="field"></section>

                    <input type="hidden" name="identifiant" value="${Utilisateur.getId()}">

                    <input type="submit" name="action" value="Confirmer" class="launch">

                </form>
            </fieldset>
        </c:if>

    </section>

    <section class="field"></section>

    <section class="statistiques">
        <h3>Performances Globales de la dernière session</h3>
        <hr>
        <table>
            <thead>
            <tr>
                <th>ID_Session</th>
                <th>Temps</th>
                <th>Distance</th>
                <th>Coups</th>
                <th>Puissance</th>
                <th>Rythme</th>
                <th>Calories</th>
                <th>Frequence</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </section>


</div>

</body>
</html>

