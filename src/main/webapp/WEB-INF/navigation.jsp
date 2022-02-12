<%--
  Created by IntelliJ IDEA.
  User: utilisateur
  Date: 08/02/2022
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<!-- Partie Profil -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="profil">
    <aside>
        <section class="field"></section>

        <h1>Profil</h1>

        <hr>

        
        <c:if test="${ !empty sessionScope.Utilisateur}">
            <!--h3 style="color: white">Bonjour ${Utilisateur.getLogin()}</h3-->
            <h3 style="color: white">Bonjour ${sessionScope.Utilisateur.getLogin()}</h3>
        </c:if>


        <hr>

        <!-- Menu -->

        <ul>
            <li><a href="MenuControleur">Accueil</a></li>
            <li><a href="StatsControleur">Mes statistiques</a></li>
            <li><a href="CourseControleur">Effectuer une course</a></li>
            <li><a href="EntrainementControleur">Effectuer un entraînement</a></li>
        </ul>
    </aside>
</div>
