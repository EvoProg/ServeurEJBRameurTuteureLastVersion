<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- Page de connexion -->
<!DOCTYPE html>
<html lang="fr" dir="ltr">

<head>
    <meta charset="utf-8">
    <title>Projet Tutoré - Authentification</title>
    <link rel="stylesheet" href="CSS/authentication_registration.css">
</head>

<body>

    <section class="header-field"></section>

    <div class="titre-mire">
        <h2>Rameurs connectés - Authentification</h2>
    </div>

    <div class="loggin-field">

        <section>

                <fieldset>

                    <form class="authentication" action="ConnexionControleur" method="post">

                        <section class="field"></section>

                        <section class="authentication-username-form">
                            <label class="name">Nom d'utilisateur :</label>
                            <div>
                                <input type="text" name="username" id="username" class="username-txt-field">
                            </div>
                        </section>

                        <section class="field"></section>

                        <section class="authentication-password-form">
                            <label class="password">Mot de passe:</label>
                            <div>
                                <input type="password" name="password" id="password" class="password-txt-field">
                            </div>
                        </section>

                        <section class="field"></section>

                        <div>
                            <button type="submit" name="connection" value="CONNEXION" class="connection">
                                <span class="connection-button">CONNEXION</span>
                            </button>
                        </div>

                    </form>

                    <hr>

                    <!--Permet l'affichage de messages d'erreur-->
                    <div>
                        <p class="message">${Message}</p>
                        <br>
                    </div>

                    <div class="link">
                        <a href="EnregistrerControleur">S'enregistrer</a>
                    </div>

                </fieldset>

        </section>
    </div>

    <section class="field-footer"></section>

    <footer>
        <p>ALLAIN Aymeric - CHEMAIN Baptiste - HURDEBOURCQ Jérémy</p>
    </footer>
</body>

</html>
