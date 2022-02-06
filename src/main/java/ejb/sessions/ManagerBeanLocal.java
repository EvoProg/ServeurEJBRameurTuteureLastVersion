package ejb.sessions;

import ejb.entities.Performance;
import ejb.entities.Utilisateur;

import javax.ejb.Local;
import java.util.List;

/*
    Interface de l'EJB ManagerBean en LOCAL.
 */

@Local
public interface ManagerBeanLocal {
    //Méthode pour récupérer une Liste de Performances
    public List<Performance> getListeToutePerformances();
    public List<Performance> getListePerformancesParUtilisateur(int idUtil);
    public List<Performance> getListePerformancesParUtilisateurSession(int idUtil, int idSession);
    public List<Performance> getListeDernieresPerformances(int idUtil);
    public List<Performance> getListeCinqDernieresPerformances(int idUtil);

    //Méthode pour récupérer un Utilisateur
    public Utilisateur getUtilisateur(int idUtil);

    //Méthode permettant de rechercher la dernière session effectuer par un Utilisateur
    public int getDerniereSession(int idUtil);

    //Méthode ajoutant les valeurs dans la base de données
    public void addPerformances(int temps, int distance, int coups, int rythme, int caloriesH, int calories,
                                int frequence, int idUtil, int idSession);
}
