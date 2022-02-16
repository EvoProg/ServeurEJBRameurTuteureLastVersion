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

@WebServlet(name = "SessionControleur", value = "/SessionControleur")
public class SessionControleur extends HttpServlet {
    //Déclaration des variables
    //Appel de l'EJB depuis son interface
    @EJB
    private ManagerBeanLocal mg;

    @EJB
    private SessionBeanLocal sb;

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
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("Utilisateur");
        int identifiant = utilisateur.getId();

        //On vérifie quelle action a été sélectionnée
        if(action.equals("Confirmer")){
            //On met à jour les données
            confirmerSession(identifiant,request, response);
            //On affiche les dernières performances
            affichageDernierePerformance(identifiant,request);
            //On passe l'utilisateur
            //passageUtilisateur(identifiant, request, response);

            request.setAttribute("menu",true);
            //On renvoie vers la jsp menu
            this.getServletContext().getRequestDispatcher("/WEB-INF/menu.jsp").forward(request, response);
        }
    }

    //Methode confirmant les paramètres d'une session
    protected void confirmerSession(int identifiant, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //On récupère les données
        String temps_s = request.getParameter("temps_s");
        String distance_s = request.getParameter("distance_s");
        String rameur = request.getParameter("rameur");
        Rameur rameur_en_attente = null;

        int id = 0;

        if (rameur != "") {
            id = Integer.parseInt(rameur);
            rameur_en_attente = sb.getRameur(id);
        } else {
            request.setAttribute("Message", "Une erreur est survenue, veuillez réessayer ultérieurement");
            //On renvoie vers la page de connexion
            this.getServletContext().getRequestDispatcher("/").forward(request, response);
        }


        //On récupère la dernière session de l'utilisateur
        int session = mg.getDerniereSession(identifiant) + 1;

        //System.out.println("Identifiant de l'utilisateur : " + identifiant);
        System.out.println("Session de l'utilisateur : " + session);

        if (rameur_en_attente.getValeur() == 0 && rameur_en_attente != null){
            //Vérification
            if (temps_s != null) {
                //Session sur le temps
                int tps = Integer.parseInt(temps_s);
                //Vérification
                sb.updateRameur(id, "temps", tps, identifiant, session, 0, 0);
            } else {
                //Session sur la distance
                int dist = Integer.parseInt(distance_s);
                //Vérification
                sb.updateRameur(id, "distance", dist, identifiant, session, 0, 0);
            }
        }else{
            request.setAttribute("Message","Le rameur est déjà utilisé, veuillez en sélectionner un autre");
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
