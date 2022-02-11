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

        <div class="presentation" id="div-course">

            <div id="div-course-partie-gauche">
                <!--Partie choix du rameur pour la course-->
                <!--Seuls les rameurs disponibles sont affichés-->
                <div id="div-choix-rameur-course">
                    <!--TODO: lier le formulaire-->
                    <form>
                        <fieldset>
                            <legend>Choisissez votre rameur:</legend>
                            <div class="scrollable-list">
                                <ul>
                                    <c:forEach items="${rameurs}" var="rameur">
                                        <li>
                                            <input type="radio" id="radio-choix-rameur${rameur.getId()}" name="radio-choix-rameur" required>
                                            <label for="radio-choix-rameur${rameur.getId()}">Rameur n°${rameur.getId()}</label>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>

                            <br>

                            <button id="bouton-valider-rameur" value="valider-rameur" type="submit">Valider</button>
                        </fieldset>


                    </form>
                </div>

                <div id="div-choix-type-course">
                    <form>
                        <fieldset>
                            <legend>Choisissez un type de course:</legend>

                            <input type="radio" id="radio-temps" name="radio-choix-course" required>
                            <label for="radio-temps">Temps</label>

                            <input type="radio" id="radio-distance" name="radio-choix-course" required>
                            <label for="radio-distance">Distance</label>

                            <input type="number" required>

                            <button id="bouton-valider-type" value="valider-type" type="submit">Valider</button>
                        </fieldset>
                    </form>
                </div>
            </div>

            <div id="div-course-partie-droite">
                <!--Partie choix du type de course-->
                <div id="div-choix-adversaire">
                    <!--TODO: Lier le formulaire-->
                    <form>
                        <fieldset>
                            <legend>Liste des adversaires disponibles:</legend>
                            <!--TODO: afficher les utilisateurs disponibles pour une course-->
                            <button id="bouton-valider-adversaire" value="valider-adversaire" type="button">Valider</button>
                        </fieldset>
                    </form>
                </div>

                <div id="div-notifs-course">
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

