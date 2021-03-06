package servlets;

import ejb.entities.Rameur;
import ejb.entities.Utilisateur;
import ejb.objects.Defis;
import ejb.sessions.CourseBean;
import ejb.sessions.ManagerBeanLocal;
import ejb.sessions.SessionBeanLocal;
import stats.SimilarCos;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CourseControleur", value = "/CourseControleur")
public class CourseControleur extends HttpServlet
{
    @EJB
    private SessionBeanLocal sb;

    @EJB
    private CourseBean cb;

    @EJB
    private ManagerBeanLocal mg;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("courses", true);

        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute("Utilisateur");

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur)session.getAttribute("Utilisateur");
        int idUtil = utilisateur.getId();
        System.out.println(idUtil);
        SimilarCos conseil = new SimilarCos(idUtil);
        List<Integer> utilisateurs = conseil.getListeUserConseil();
        List<String> utils = new ArrayList<>();
        for (int i = 0; i < utilisateurs.size(); i++) {
            utils.add(mg.getUtilisateur(utilisateurs.get(i)).getLogin());
        }

        for (int i = 0; i < utils.size(); i++) {
            System.out.println(utils.get(i));
        }
        request.setAttribute("utilisateurs",utils);
        this.doGet(request, response);
    }
}
