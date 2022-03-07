package servlets;

import ejb.entities.Performance;
import ejb.entities.Utilisateur;
import ejb.objects.Defis;
import ejb.sessions.CourseBean;
import ejb.sessions.ManagerBeanLocal;
import ejb.sessions.SessionBeanLocal;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ResultatCourseControleur", value = "/resultatCourse")
public class ResultatCourseControleur extends HttpServlet {

    @EJB
    private SessionBeanLocal sb;

    @EJB
    private ManagerBeanLocal mb;

    @EJB
    private CourseBean cb;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //this.getServletContext().getRequestDispatcher("/WEB-INF/animation.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        int idRameurChoisi = (int) session.getAttribute("rameurChoisi");

        Utilisateur utilisateur = (Utilisateur)session.getAttribute("Utilisateur");
        int idUtilisateur = utilisateur.getId();

        Defis defi = (Defis) session.getAttribute("defi");

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

        this.getServletContext().getRequestDispatcher("/WEB-INF/testCourse.jsp").forward(request, response);
    }
}
