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

        <%@include file="navigation.jsp"%><

        <%@include file="deconnexion.jsp"%>

        <br>

        <div class="presentation" id="div-course">

            <div id="div-course-partie-gauche">
                <!--Partie choix du rameur pour la course-->
                <!--Seuls les rameurs disponibles sont affichés-->
                <div id="div-choix-rameur-course">
                    <fieldset>
                        <legend>Choisissez votre rameur:</legend>
                        <div class="scrollable-list">
                            <!--TODO: afficher le numéro du rameur choisi-->
                            <form id="form-rameurs" action="choixRameur", method="post">
                                <ul>
                                    <c:choose>
                                        <c:when test="${pasDeRameur}">
                                            <li style="color: #c0001a">
                                                Aucun rameur disponible pour le moment
                                            </li>
                                        </c:when>
                                        <c:when test="${rameurChoisi != null}">
                                            <li style="color: #c0001a">
                                                Vous avez choisi le rameur n°${rameurChoisi}
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach items="${rameurs}" var="rameur">
                                                <li>
                                                    <input type="radio" id="radio-choix-rameur${rameur.getId()}" name="radio-choix-rameur" required>
                                                    <label for="radio-choix-rameur${rameur.getId()}">Rameur n°${rameur.getId()}</label>
                                                </li>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </form>
                        </div>

                        <br>

                        <button form="form-rameurs" id="bouton-valider-rameur" value="valider-rameur" type="submit">Valider</button>
                    </fieldset>
                </div>

                <!--div id="div-choix-type-course">
                    <form>
                        <fieldset>
                            <legend>Choisissez la distance à parcourir:</legend>

                            <input type="number" min="100" required>

                            <button id="bouton-valider-distance" value="valider-distance" type="submit">Valider</button>
                        </fieldset>
                    </form>
                </div-->
            </div>

            <div id="div-course-partie-droite">
                <!--Partie choix du type de course-->
                <div id="div-choix-adversaire">
                    <button id="bouton-maj-adversaires">Rafraîchir</button>

                    <fieldset>
                        <legend>Liste des adversaires disponibles:</legend>

                        <!--TODO: Lier le formulaire-->
                        <form>
                            <ul>
                                <c:choose>
                                    <c:when test="${pasDAdversaire}">
                                        <li style="color: #c0001a">
                                            Aucun adversaire disponible pour le moment
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${utilisateursDispos}" var="utilisateur">
                                            <li>
                                                <input type="radio" id="radio-choix-adversaire${utilisateur.getId()}" name="radio-choix-adversaire" required>
                                                <label for="radio-choix-adversaire${utilisateur.getId()}">${utilisateur.getLogin()}</label>
                                            </li>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </ul>
                            <label for="inputDistance">Choisissez la distance à parcourir (mètres)</label>
                            <input id="inputDistance" type="number" min="100" required>

                            <button id="bouton-valider-distance" value="valider-distance" type="submit">Valider</button>
                        </form>
                    </fieldset>
                </div>

                <div id="div-notifs-course">
                    <!--TODO: Lier le formulaire-->
                    <form>
                        <fieldset>
                            <legend>Liste des défis reçus:</legend>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>

    </body>
</html>

