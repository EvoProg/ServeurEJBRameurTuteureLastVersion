package servlets;

import ejb.entities.Performance;
import ejb.entities.Rameur;
import ejb.entities.Utilisateur;
import ejb.objects.Defis;
import ejb.sessions.CourseBean;
import ejb.sessions.ManagerBeanLocal;
import ejb.sessions.ManagerBeanLocal;
import ejb.sessions.SessionBeanLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ChoixAdversaireControleur", value = "/choixAdversaire")
public class ChoixAdversaireControleur extends HttpServlet
{
    @EJB
    private SessionBeanLocal sb;

    @EJB
    private CourseBean cb;

    @EJB
    private ManagerBeanLocal mb;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("test");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("courses", true);

        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute("Utilisateur");
        int idUtilisateur = utilisateur.getId();

        int idAdversaire = Integer.parseInt(request.getParameter("radio-choix-adversaire"));
        cb.supprUtilDispo(idAdversaire);

        int distance = Integer.parseInt(request.getParameter("inputDistance"));
        int idRameurChoisi = (Integer)session.getAttribute("rameurChoisi");
        cb.lancerDefis(idUtilisateur, idAdversaire, distance, idRameurChoisi);
        Defis defi = cb.getDefi(idUtilisateur, idAdversaire);

        while(!defi.isAccepte()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/animation.jsp").forward(request,response);

        while(sb.getRameur(idRameurChoisi).getValeur() != 0)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<Performance> performances = mb.getListeDernieresPerformances(idUtilisateur);


        String res = "";
        try {
            res = cb.ajoutTempsEtAfficheResultat(performances.get(performances.size()-1).getTempsCs(), defi, idUtilisateur);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(res.equals(""))
        {
            System.out.println("résultat vide");
        }
        request.setAttribute("resultat", res);

        //Récupération de la liste des utilisateurs disponibles pour une course
        /*List<Utilisateur> utilisateursDispos = cb.getUtilisateursDispos();


        //Si l'utilisateur n'a pas déjà choisi un rameur
        if(session.getAttribute("rameurChoisi") == null)
        {
            //Récupération de la liste des rameurs disponibles
            List<Rameur> rameurs = sb.getListeRameursAttente(0);
            if(rameurs.size() > 0)
            {
                request.setAttribute("rameurs", rameurs);
                request.setAttribute("pasDeRameur", false);
            }
            else
            {
                request.setAttribute("pasDeRameur", true);
            }
        }

        if(utilisateursDispos.size() > 0)
        {
            request.setAttribute("utilisateursDispos", utilisateursDispos);
            request.setAttribute("pasDAdversaire", false);
        }
        else
        {
            request.setAttribute("pasDAdversaire", true);
        }

        List<Defis> defis = cb.recevoirDefis(idUtilisateur);
        if(defis.size() > 0)
        {
            request.setAttribute("defis", defis);
            request.setAttribute("pasDeDefis", false);
        }
        else
        {
            request.setAttribute("pasDeDefis", true);
        }*/



        this.getServletContext().getRequestDispatcher("/WEB-INF/testCourse.jsp").forward(request, response);
    }


}
