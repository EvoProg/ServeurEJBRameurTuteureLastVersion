package servlets;

import ejb.entities.Utilisateur;
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
import java.util.List;

@WebServlet(name = "ChoixRameurControleur", value = "/choixRameur")
public class ChoixRameurControleur extends HttpServlet
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

        //Récupération de la liste des rameurs disponibles
        /*List<Rameur> rameurs = sb.getListeRameursAttente(0);
        if(rameurs.size() > 0)
        {
            request.setAttribute("rameurs", rameurs);
            request.setAttribute("pasDeRameur", false);
        }
        else
        {
            request.setAttribute("pasDeRameur", true);
        }*/
        int idRameur = Integer.parseInt(request.getParameter("radio-choix-rameur"));
        session.setAttribute("rameurChoisi", idRameur);

        Utilisateur utilisateur = (Utilisateur)session.getAttribute("Utilisateur");
        cb.addUtilDispo(utilisateur.getId());


        //Récupération de la liste des utilisateurs disponibles pour une course
        List<Utilisateur> utilisateursDispos = cb.getUtilisateursDispos();

        if(utilisateursDispos.size() > 0)
        {
            request.setAttribute("utilisateursDispos", utilisateursDispos);
            request.setAttribute("pasDAdversaire", false);
        }
        else
        {
            request.setAttribute("pasDAdversaire", true);
        }

        List<Defis> defis = cb.recevoirDefis(utilisateur.getId());
        if(defis.size() > 0)
        {
            request.setAttribute("defis", defis);
            request.setAttribute("pasDeDefis", false);
        }
        else
        {
            request.setAttribute("pasDeDefis", true);
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/course.jsp").forward(request, response);
    }


}
