package servlets;

import ejb.entities.Rameur;
import ejb.sessions.ManagerBeanLocal;
import ejb.sessions.SessionBeanLocal;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

/*
    Une servlet est un composant Web de Java EE.
    Elle permet de traiter une requête entrante sur un serveur et de générer une réponse dynamique.
    Les méthodes doXXX ont toutes deux paramètres : javax.servlet.http.HttpServletRequest et
    javax.servlet.http.HttpServletResponse qui représentent respectivement la requête HTTP entrante et
    la réponse renvoyée par le serveur.
 */

@WebServlet(name = "EntrainementControleur", value = "/EntrainementControleur")
public class EntrainementControleur extends HttpServlet {
    //Déclaration des variables
    //Appel de l'EJB depuis son interface
    @EJB
    private ManagerBeanLocal mg;

    @EJB
    private SessionBeanLocal sb;

    //Traitement de la requête HTTP Get.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //On renvoie les données dans la JSP
        sendRameursEnAttentes(request);
        request.setAttribute("entrainement", true);
        this.getServletContext().getRequestDispatcher("/WEB-INF/entrainement.jsp").forward(request, response);
    }

    //Traitement de la requête HTTP Post. Liée au formulaire de la jsp "entrainement" après sélection des paramètres.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //On renvoie les données dans la JSP
        sendRameursEnAttentes(request);
        request.setAttribute("entrainement", true);
        this.getServletContext().getRequestDispatcher("/WEB-INF/entrainement.jsp").forward(request, response);
    }

    //Méthode pour renvoyer les rameurs dans la jsp
    protected void sendRameursEnAttentes(HttpServletRequest request){
        //On vérifie si des rameurs sont en attentes
        List<Rameur> rameurs = sb.getListeRameursAttente(0);
        if(rameurs.size() == 0) {
            request.setAttribute("Message","Aucun rameur de disponible, veuillez réessayer dans quelques minutes ... ");
        }else{
            request.setAttribute("rameurs",rameurs);
        }
    }
}
