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
    <!--Affichage de la barre de navigation sur la gauche de la page-->
    <%@include file="navigation.jsp" %>

    <!--Affichage du nom de la page ainsi que du bouton de déconnexion-->
    <%@include file="deconnexion.jsp"%>

<div class="menu">


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

        <!--Affichage des messages d'erreur-->
        <p class="message">${Message}</p>

        <section class="field"></section>

        <!-- Sélection du rameur et de la valeur de la session -->
        <c:if test="${(temps == true || distance == true)}">
            <fieldset>
                <legend>Session</legend>
                <form class="test" action="SessionControleur" method="post">

                    <!-- Tableau Rameurs -->
                    <c:if test="${rameurs.size() != 0}">
                        <fieldset>
                            <legend>Choisissez votre rameur :</legend>
                            <div class="cadre_table_scroll">
                                <table class="table_scroll">
                                    <thead>
                                    <tr>
                                        <th>Selection</th>
                                        <th>Numéro du rameur</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${rameurs}" var="rameur">
                                        <tr class="inputrameur">
                                            <td><input type="radio" id="rameur${rameur.getId()}" name="rameur" value="${rameur.getId()}" required></td>
                                            <td><label for="rameur${rameur.getId()}">${rameur.getId()}</label> </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <td colspan="3"></td>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                        </fieldset>
                    </c:if>

                    <c:if test="${temps == true}">
                        <fieldset>
                            <legend>Entrer une valeur :</legend>
                            <section class="field"></section>
                            <label class="temps_label">Temps (en secondes) : </label>
                            <input type="number" name="temps_s" id="temps_s" class="temps-txt-field" required min="60" max="1800">
                            <br>
                        </fieldset>
                    </c:if>

                    <c:if test="${distance == true}">
                        <fieldset>
                            <legend>Entrer une valeur :</legend>
                            <section class="field"></section>
                            <label class="distance_label">Distance (en mètres) : </label>
                            <input type="number"  name="distance_s" id="distance_s" class="distance-txt-field" required min="100" max="4000">
                            <br>
                        </fieldset>
                    </c:if>

                    <section class="field"></section>

                    <input type="hidden" name="identifiant" value="${Utilisateur.getId()}">

                    <input type="submit" name="action" value="Confirmer" class="launch">

                </form>
            </fieldset>
        </c:if>
    </section>



    <section class="field"></section>



    <!--Affichage des stats de la dernière session-->
    <section class="statistiques">
        <h3>Performances Globales de la dernière session</h3>
        <hr>
        <div class="cadre_table_scroll">
            <table class="table_scroll">
                <thead>
                    <tr>
                        <th>Numéro de la session</th>
                        <th>Temps en seconde</th>
                        <th>Distance en centimètre</th>
                        <th>Coups par minute</th>
                        <th>Puissance en watt</th>
                        <th>Rythme</th>
                        <th>Calories perdues</th>
                        <th>Frequence en btmp</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${DernieresPerformances.size() != 0}">
                        <c:forEach items="${DernieresPerformances}" var="perf">
                            <td>${perf.getId().getIdSession()}</td>
                            <td>${perf.getId().getTimestamp()}</td>
                            <td>${perf.getDistanceCm()}</td>
                            <td>${perf.getCoupsPm()}</td>
                            <td>${perf.getPuissanceW()}</td>
                            <td>${perf.getRythmeMs()}</td>
                            <td>${perf.getCalories()}</td>
                            <td>${perf.getFrequenceBpm()}</td>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
        </div>
    </section>

</body>
</html>

