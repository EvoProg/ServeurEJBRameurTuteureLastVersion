package servlets;

import ejb.entities.Performance;
import ejb.entities.Utilisateur;
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

@WebServlet(name = "SessionControleur", value = "/SessionControleur")
public class SessionControleur extends HttpServlet {
    //Déclaration des variables
    //Appel de l'EJB depuis son interface
    @EJB
    private ManagerBeanLocal mg;

    //Traitement de la requête HTTP Get.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/menu.jsp").forward(request, response);
    }

    //Traitement de la requête HTTP Post. Liée au formulaire de la jsp "menu" après sélection des paramètres d'une session.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //On récupère les données de la jsp "menu" dans le formulaire post
        String action = request.getParameter("action");
        String identifiant = request.getParameter("identifiant");

        int idUtilisateur = 0;

        //On vérifie si la donnée identifiant n'est pas vide
        if(identifiant.equals("")){
            //Si celle-ci est vide, on renvoie vers la page d'index avec un message d'erreur
            request.setAttribute("Message","Une erreur est survenue, veuillez réessayer ultérieurement");
            this.getServletContext().getRequestDispatcher("/");
        }else{
            //Sinon, on cast le paramètre identifiant en entier
            idUtilisateur = Integer.parseInt(identifiant);
        }

        //On vérifie quelle action a été sélectionnée
        if(action.equals("Confirmer")){
            //On met à jour les données
            confirmerSession(idUtilisateur, request, response);
            //On affiche les dernières performances
            affichageDernierePerformance(idUtilisateur,request);
            //On passe l'utilisateur
            passageUtilisateur(idUtilisateur, request, response);

            //On renvoie vers la jsp menu
            this.getServletContext().getRequestDispatcher("/WEB-INF/menu.jsp").forward(request, response);
        }
    }

    //Methode confirmant les paramètres d'une session
    protected void confirmerSession(int identifiant, HttpServletRequest request, HttpServletResponse response){

    }

    //Méthode appellant l'EJB pour récupérer les dernières performances de l'utilisateur
    // et faire passer les données dans la jsp
    protected void affichageDernierePerformance(int identifiant, HttpServletRequest request){
        //On récupère les données
        List<Performance> performances = mg.getListeDernieresPerformances(identifiant);

        //On les passe dans les paramètres de la jsp
        request.setAttribute("DernieresPerformances",performances);
    }

    //Méthode appellant l'EJB pour récupérer l'utilisateur actuel
    // et faire passer les données dans la jsp
    protected void passageUtilisateur(int identifiant, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //On récupère les données
        Utilisateur utilisateur = mg.getUtilisateur(identifiant);

        //On met en place une sécurité pour savoir si l'utilisateur n'a pas été trouvé
        if (utilisateur == null){
            request.setAttribute("Message","Une erreur est survenue, veuillez réessayer ultérieurement");
            //On renvoie vers la page de connexion
            this.getServletContext().getRequestDispatcher("/").forward(request,response);
        }else{
            //Sinon on passe les données dans la jsp
            request.setAttribute("Utilisateur",utilisateur);
        }
    }
}
