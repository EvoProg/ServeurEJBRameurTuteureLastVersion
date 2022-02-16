package servlets;

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
        //On récupère les données de la jsp "menu" dans le formulaire post
        String action = request.getParameter("action");
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("Utilisateur");
        int identifiant = utilisateur.getId();

        //On vérifie quelle action a été sélectionnée
        if(action.equals("Confirmer")){
            //On met à jour les données
            confirmerEntrainement(identifiant,request, response);
        }

        //On renvoie les données dans la JSP
        sendRameursEnAttentes(request);
        request.setAttribute("entrainement", true);
        this.getServletContext().getRequestDispatcher("/WEB-INF/entrainement.jsp").forward(request, response);
    }

    //Methode confirmant les paramètres d'un entrainement
    protected void confirmerEntrainement(int identifiant, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //On récupère les données
        String temps_repos = request.getParameter("temps_repos");
        String distance_m = request.getParameter("distance_s");
        String nb_repetition = request.getParameter("nb_repetition");
        String rameur = request.getParameter("rameur");

        int id = 0;

        if(rameur != "") {
            id = Integer.parseInt(rameur);
        }else{
            request.setAttribute("Message","Une erreur est survenue, veuillez réessayer ultérieurement");
            //On renvoie vers la page de connexion
            this.getServletContext().getRequestDispatcher("/").forward(request,response);
        }

        //On récupère la dernière session de l'utilisateur
        int session = mg.getDerniereSession(identifiant)+1;

        //System.out.println("Identifiant de l'utilisateur : " + identifiant);
        System.out.println("Session de l'utilisateur : " + session);

        //Temps de repos
        int tps_rps = Integer.parseInt(temps_repos);

        //Valeur de la distance a parcourir
        int dist = Integer.parseInt(distance_m);

        //Nombre de répétition
        int repetition = Integer.parseInt(nb_repetition);

        sb.updateRameur(id,"entrainement",dist,identifiant,session,tps_rps,repetition);
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
