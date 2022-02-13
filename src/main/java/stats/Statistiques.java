package stats;

import ejb.entities.Performance;
import ejb.sessions.ManagerBean;
import ejb.sessions.ManagerBeanLocal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
    Classe objet permettant de réaliser les divers calculs de statistiques des performances des utilisateurs.
 */
public class Statistiques {
    //Déclaration des variables
    private ManagerBeanLocal mg;

    private List<Performance> perfs;
    private int nbSession;

    //Constructeur
    public Statistiques(int idUtil){
        this.mg = new ManagerBean();
        this.perfs = mg.getListeCinqDernieresPerformances(idUtil);
        this.nbSession = mg.getDerniereSession(idUtil);
    }

    //Accesseurs en lecture
    public List<Performance> getPerfs() {
        return perfs;
    }

    public int getSession() {return nbSession;}

    public String getPerfSessionfDate(int idSession){
        String res = "";
        for (Performance perf: this.getPerfs()) {
            if(perf.getId().getIdSession() == idSession) {
                res = perf.getId().getDateTime();
                res = res.substring(0,8);
            }
        }
        return res;
    }

    //Calcul de la puissance moyenne d'une session
    public double PuissanceMoyenne(int idSession) {
        int puissance = 0;
        int index = 0;
        for (Performance p:perfs) {
            if(p.getId().getIdSession() == idSession) {
                puissance += p.getPuissanceW();
                index++;
            }
        }
        return puissance/index;
    }

    //Calcul des calories perdues à chaque session
    public double CaloriesTotales(int idSession){
        int calories = 0;
        for (Performance p:perfs) {
            if(p.getId().getIdSession() == idSession) {
                calories += p.getCalories();
            }
        }
        return calories;
    }

    //Calcul des distances parcourues à chaque session
    public double DistancesTotales(int idSession){
        int distance = 0;
        for (Performance p:perfs) {
            if(p.getId().getIdSession() == idSession) {
                distance += p.getCalories();
            }
        }
        return distance;
    }

    //Renvoie un tableau de double de données
    public double[][] getPuissanceMoySessions(int idSession) {
        List<Performance> performances = new ArrayList<>();
        for (Performance p:perfs) {
            if(p.getId().getIdSession() == idSession) {
                performances.add(p);
            }
        }

        double dataset[][] = new double[2][performances.size()];

        for (int i = 0; i < performances.size(); i++) {
            dataset[0][i] = performances.get(i).getTempsCs();
            dataset[1][i] = performances.get(i).getPuissanceW();
        }

        return dataset;
    }
}
