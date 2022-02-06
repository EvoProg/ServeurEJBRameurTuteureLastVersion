<%--
  Created by IntelliJ IDEA.
  User: evoprog
  Date: 02/02/2022
  Time: 21:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr" dir="ltr">
<head>
    <meta charset="utf-8">
    <title>Projet Tutoré - Enregistrement</title>
    <link rel="stylesheet" href="CSS/authentication_registration.css">
</head>
<body>

<section class="header-field"></section>

<div class="titre-mire">
    <h2>Rameurs connectés - Enregistrement</h2>
</div>

<div class="loggin-field">

    <section>
        <div>
            <div class="form-wrapper">
                <fieldset>

                    <form class="authentication" action="EnregistrerControleur" method="post">

                        <section class="field"></section>

                        <section class="authentication-username-form">
                            <label for="username" class="name">Nom d'utilisateur :</label>
                            <div>
                                <input type="text" name="username" id="username" class="username-txt-field" minlength="4" required>
                            </div>
                        </section>

                        <section class="field"></section>

                        <section class="authentication-password-form">
                            <label for="password" class="password">Mot de passe:</label>
                            <div>
                                <input type="password" name="password" id="password" class="password-txt-field" minlength="4" required>
                            </div>
                        </section>

                        <section class="field"></section>

                        <div>
                            <button type="submit" value="send" class="connection">
                                <span class="connection-button">ENREGISTRER</span>
                            </button>
                        </div>

                    </form>

                    <hr>

                    <div>
                        <p class="message">${Message}</p>
                        <br>
                    </div>

                    <div class="link">
                        <a href="ConnexionControleur">Retour à la page de connexion</a>
                    </div>

                </fieldset>
            </div>
        </div>
    </section>
</div>

<section class="field-footer"></section>

<footer>
    <p>ALLAIN Aymeric - CHEMAIN Baptiste - HURDEBOURCQ Jérémy</p>
</footer>

</body>
</html>