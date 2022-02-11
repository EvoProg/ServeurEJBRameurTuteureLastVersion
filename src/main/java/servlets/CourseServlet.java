package servlets;

import ejb.entities.Rameur;
import ejb.sessions.SessionBeanLocal;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CourseServlet", value = "/course")
public class CourseServlet extends HttpServlet
{
    @EJB
    private SessionBeanLocal sb;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("courses", true);

        //Récupération de la liste des rameurs disponibles
        List<Rameur> rameurs = sb.getListeRameursAttente(0);

        request.setAttribute("rameurs", rameurs);

        this.getServletContext().getRequestDispatcher("/WEB-INF/course.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
