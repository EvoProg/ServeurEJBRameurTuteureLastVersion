package ejb.sessions;

import ejb.entities.Rameur;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import java.util.List;

/*
    Interface de l'EJB SessionBean en LOCAL.
 */

@Local
public interface SessionBeanLocal {
    //Méthode permettant de récupérer tous les rameurs
    public List<Rameur> getListeRameurs();

    //Méthode permettant de récupérer la liste des rameurs en attente
    public List<Rameur> getListeRameursAttente(int valeur);

    //Méthode permettant de récupérer un rameur à partir d'un identifiant
    public Rameur getRameur(int idRameur);

    //Méthode ajoutant un rameur dans la base de données
    public boolean addRameur(int idRameur);

    //Méthode permettant de mettre à jour le rameur dans la base de données
    public void updateRameur(int idRameur, String type, int valeur, int idutil, int session, int repos, int repetition);

    //Méthode permettant de renvoyer le dernier ID Rameur utilisé et de l'incrémenter
    public int getDernierIdRameur();

    public void deleteRameur(String idRameur);

    //TODO vérifier si cela fonctionne bien dans le temps
    public void deleteAllRameur();
}
