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
<!-- Partie interaction entre les différentes options pour lancer un entraînement personnalisé -->
<section class="options">
    <h3>Lancement d'un entraînement personnalisé</h3>
    <hr>

    <!--Affichage des messages d'erreur-->
    <p class="message">${Message}</p>

    <section class="field"></section>

    <!-- Sélection du rameur et de la valeur de la session -->
        <fieldset>
            <legend>Session</legend>
            <form class="test" action="EntrainementControleur" method="post">

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

                <fieldset>
                    <legend>Entrés les différentes valeurs : </legend>
                    <br>
                    <label class="temps_label">Temps de repos : </label>
                    <input type="number" name="temps_repos" id="temps_s" class="temps-txt-field" required min="60" max="180">
                    <label class="distance_label">Distance (en mètres) : </label>
                    <input type="number"  name="distance_s" id="distance_s" class="distance-txt-field" required min="100" max="4000">
                    <label class="nombre_session">Nombre de répétitions : </label>
                    <input type="number"  name="nombre_s" id="nombre_ses" class="distance-txt-field" required min="2" max="10">
                    <br>
                </fieldset>

                <section class="field"></section>

                <input type="hidden" name="identifiant" value="${Utilisateur.getId()}">

                <input type="submit" name="action" value="Confirmer" class="launch">

            </form>
        </fieldset>
</section>

</body>
</html>

