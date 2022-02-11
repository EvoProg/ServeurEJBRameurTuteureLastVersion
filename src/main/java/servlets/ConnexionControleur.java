package servlets;

import ejb.entities.Performance;
import ejb.entities.Utilisateur;
import ejb.sessions.ConnectionBeanLocal;
import ejb.sessions.ManagerBeanLocal;

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

@WebServlet(name = "ConnexionControleur", value = "/ConnexionControleur")
public class ConnexionControleur extends HttpServlet {
    //Déclaration des variables
    //Appel de l'EJB depuis son interface
    @EJB
    private ManagerBeanLocal mg;

    @EJB
    private ConnectionBeanLocal cb;

    //Traitement de la requête HTTP Get.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Renvoie vers la page de Connexion
        this.getServletContext().getRequestDispatcher("/").forward(request, response);
    }

    //Traitement de la requête HTTP Post. Liée au formulaire de la jsp "index" après interaction avec le bouton CONNEXION.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Boolean verify = cb.verifyConnexion(request);

        //Si la connexion n'est pu être établie
        if (!verify){
            request.setAttribute("Message","Login ou mot de passe incorrect !");
            this.getServletContext().getRequestDispatcher("/").forward(request, response);
        }else{
            //On récupère l'utilisateur depuis l'EJB de Connexion
            String pseudo = request.getParameter("username");
            Utilisateur utilisateur = cb.getUtilisateur(pseudo);

            //On passe l'utilisateur dans les paramètres de la page appelée
            //request.setAttribute("Utilisateur",utilisateur);
            HttpSession session = request.getSession();
            session.setAttribute("Utilisateur", utilisateur);
            request.setAttribute("menu", true);

            //On passe les données des dernières performances
            affichageDernierePerformance(utilisateur.getId(), request);

            //On appelle la nouvelle page
            this.getServletContext().getRequestDispatcher("/WEB-INF/menu.jsp").forward(request, response);
            //System.out.println("CONNEXION RÉUSSIE");
        }
    }

    //Méthode appellant l'EJB pour récupérer les dernières performances de l'utilisateur
    // et faire passer les données dans la jsp
    protected void affichageDernierePerformance(int identifiant, HttpServletRequest request){
        //On récupère les données
        List<Performance> performances = mg.getListeDernieresPerformances(identifiant);

        //On les passe dans les paramètres de la jsp
        request.setAttribute("DernieresPerformances",performances);
    }
}
