package servlets;

import ejb.entities.Utilisateur;
import stats.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CalorieGraphiqueControleur", value = "/CalorieGraphiqueControleur")
public class CalorieGraphiqueControleur extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("Utilisateur");
        Graphiques graph = new Graphiques(utilisateur.getId());
        graph.getCaloriesGraph(response);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
