package servlets;

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

@WebServlet(name = "ChoixAdversaireControleur", value = "/choixAdversaire")
public class ChoixAdversaireControleur extends HttpServlet
{
    @EJB
    private SessionBeanLocal sb;

    @EJB
    private CourseBean cb;

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
