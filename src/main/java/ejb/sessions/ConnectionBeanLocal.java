package ejb.sessions;

import ejb.entities.Utilisateur;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;

/*
    Interface de l'EJB ConnectionBean en LOCAL.
 */

@Local
public interface ConnectionBeanLocal {
    //Méthode renvoyant un Utilisateur
    public Utilisateur getUtilisateur(String login);

    //Méthode permettant à un utilisateur de se connecter
    public boolean verifyConnexion(HttpServletRequest request);

    //Méthode ajoutant un utilisateur dans la BD après s'être enregistré
    public boolean addUtilisateur(HttpServletRequest request);
}
