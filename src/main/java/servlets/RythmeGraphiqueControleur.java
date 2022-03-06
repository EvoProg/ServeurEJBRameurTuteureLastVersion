package servlets;

import ejb.entities.Utilisateur;
import stats.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RythmeGraphiqueControleur", value = "/RythmeGraphiqueControleur")
public class RythmeGraphiqueControleur extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("Utilisateur");
        Graphiques graph = new Graphiques(utilisateur.getId());
        graph.getRythmeMoyenGraph(response);
    }

    public void destroy() {
        super.destroy();
    }
}