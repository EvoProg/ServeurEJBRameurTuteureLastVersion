package servlets;

import ejb.entities.Performance;
import ejb.entities.Rameur;
import ejb.entities.Utilisateur;
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

@WebServlet(name = "MenuControleur", value = "/MenuControleur")
public class MenuControleur extends HttpServlet {
    //Déclaration des variables
    //Appel de l'EJB depuis son interface
    @EJB
    private ManagerBeanLocal mg;

    @EJB
    private SessionBeanLocal sb;

    //Traitement de la requête HTTP Get.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("menu", true);

        this.getServletContext().getRequestDispatcher("/WEB-INF/menu.jsp").forward(request,response);
    }

    //Traitement de la requête HTTP Post. Liée au formulaire de la jsp "menu" après interaction avec les boutons radios.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //On récupère les données de la jsp "menu" dans le formulaire post
        String option = request.getParameter("opt");
        String identifiant = request.getParameter("identifiant");

        int idUtilisateur = 0;

        //TODO: supprimer les commentaires
        //On vérifie si la donnée identifiant n'est pas vide
       /*if(identifiant.equals("")){
            //Si celle-ci est vide, on renvoie vers la page d'index avec un message d'erreur
            request.setAttribute("Message","Une erreur est survenue, veuillez réessayer ultérieurement");
            this.getServletContext().getRequestDispatcher("/");
        }else{
            //Sinon, on cast le paramètre identifiant en entier
            idUtilisateur = Integer.parseInt(identifiant);
        }*/

        //Boolean de passage à la jsp pour afficher les entrées de paramètres pour l'utilisateur selon son choix
        Boolean session = true;

        //On vérifie si des rameurs sont en attentes
        List<Rameur> rameurs = sb.getListeRameursAttente(0);
        if(rameurs.size() == 0) {
            session = false;
            request.setAttribute("Message","Aucun rameur de disponible, veuillez réessayer dans quelques minutes ... ");
        }

        //On vérifie quelle option a été sélectionnée
        if(option.equals("temps")){
            request.setAttribute("temps",session);
            request.setAttribute("rameurs",sb.getListeRameursAttente(0));
        }else{
            request.setAttribute("distance",session);
            request.setAttribute("rameurs",sb.getListeRameursAttente(0));
        }

        //On set les variables à renvoyées à la jsp
        /*affichageDernierePerformance(idUtilisateur, request);
        passageUtilisateur(idUtilisateur, request, response);*/

        //On renvoie ensuite vers la page avec les nouvelles données
        this.getServletContext().getRequestDispatcher("/WEB-INF/menu.jsp").forward(request, response);
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
