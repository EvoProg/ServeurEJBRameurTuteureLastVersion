package servlets;

import ejb.sessions.ConnectionBeanLocal;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/*
    Une servlet est un composant Web de Java EE.
    Elle permet de traiter une requête entrante sur un serveur et de générer une réponse dynamique.
    Les méthodes doXXX ont toutes deux paramètres : javax.servlet.http.HttpServletRequest et
    javax.servlet.http.HttpServletResponse qui représentent respectivement la requête HTTP entrante et
    la réponse renvoyée par le serveur.
 */
@WebServlet(name = "EnregistrerControleur", value = "/EnregistrerControleur")
public class EnregistrerControleur extends HttpServlet {
    //Déclaration des variables
    //Appel de l'EJB depuis son interface
    @EJB
    private ConnectionBeanLocal cb;

    //Traitement de la requête HTTP Get.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response);
    }

    //Traitement de la requête HTTP Post. Liée au formulaire de la jsp "registration" après interaction avec le bouton ENREGISTREMENT.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Ajout d'un utilisateur
        if(cb.addUtilisateur(request)) {
            //Si l'ajout à été réalisé, alors on renvoie vers la page principale
            request.setAttribute("Message","Vous avez bien été enregistré !");
            this.getServletContext().getRequestDispatcher("/").forward(request, response);
        }else{
            //Sinon on renvoie vers la page en cours.
            request.setAttribute("Message","Pseudo déjà utilisé, veuillez changer !");
            this.getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response);
        }
    }
}
