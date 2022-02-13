package servlets;

import ejb.entities.Utilisateur;
import stats.Graphiques;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PuissanceGraphiqueControleur", value = "/PuissanceGraphiqueControleur")
public class PuissanceGraphiqueControleur extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("Utilisateur");
        Graphiques graph = new Graphiques(utilisateur.getId());
        graph.getCaloriesGraph(response);
    }

    public void destroy() {
        super.destroy();
    }
}
