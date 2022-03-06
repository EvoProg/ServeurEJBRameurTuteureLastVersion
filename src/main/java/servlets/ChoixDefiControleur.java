package servlets;

import ejb.objects.Defis;
import ejb.sessions.CourseBean;
import ejb.sessions.SessionBeanLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ChoixDefiControleur", value = "/choixDefi")
public class ChoixDefiControleur extends HttpServlet
{
    @EJB
    private SessionBeanLocal sb;

    @EJB
    private CourseBean cb;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("courses", true);

        HttpSession session = request.getSession();
        int idRameur = (Integer)session.getAttribute("rameurChoisi");

        String idsDefi = request.getParameter("radio-choix-defi");
        String[] ids = idsDefi.split(",");
        int idDefieur = Integer.parseInt(ids[0]);
        int idDefie = Integer.parseInt(ids[1]);

        Defis defi = cb.getDefi(idDefieur, idDefie);

        System.out.println(idDefieur + "==============" + idDefie);
        cb.lancerCourse(defi, idRameur);


        /*
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute("Utilisateur");
        int idUtilisateur = utilisateur.getId();

        int idAdversaire = Integer.parseInt(request.getParameter("radio-choix-adversaire"));
        cb.supprUtilDispo(idAdversaire);

        int distance = Integer.parseInt(request.getParameter("inputDistance"));
        cb.lancerDefis(idUtilisateur, idAdversaire, distance);

        //Récupération de la liste des utilisateurs disponibles pour une course
        List<Utilisateur> utilisateursDispos = cb.getUtilisateursDispos();

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
        }
        */



        this.getServletContext().getRequestDispatcher("/WEB-INF/course.jsp").forward(request, response);
    }


}
