package servlets;

import ejb.entities.Rameur;
import ejb.entities.Utilisateur;
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
        String idRameur = request.getParameter("radio-choix-rameur");
        request.setAttribute("rameurChoisi", idRameur);

        HttpSession session = request.getSession();
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

        this.getServletContext().getRequestDispatcher("/WEB-INF/course.jsp").forward(request, response);
    }


}
