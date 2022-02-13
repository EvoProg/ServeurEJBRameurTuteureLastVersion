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
    private List<Integer> session;

    //Constructeur
    public Statistiques(int idUtil){
        this.mg = new ManagerBean();
        this.perfs = mg.getListeCinqDernieresPerformances(idUtil);
        this.session = this.initSession();
    }

    public List<Integer> initSession(){
        List<Integer> session = new ArrayList<>();
        int idSession = 0;
        for (int i = 0; i < this.perfs.size(); i++) {
            //System.out.println(performances.get(i).getId().getIdSession());
            if(idSession < this.perfs.get(i).getId().getIdSession()) {
                idSession =  this.perfs.get(i).getId().getIdSession();
                //System.out.println(idSession);
                session.add(idSession);
            }
        }

        return session;
    }

    //Accesseurs en lecture
    public List<Performance> getPerfs() {
        return perfs;
    }

    public List<Integer> getSession() {return session;}

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
    public double CoupsPmMoyenne(int idSession) {
        int coups = 0;
        int index = 0;
        for (Performance p:perfs) {
            if(p.getId().getIdSession() == idSession) {
                coups += p.getCoupsPm();
                index++;
            }
        }
        return coups/index;
    }

    //Calcul de la puissance moyenne d'une session
    public double RythmeMoyenne(int idSession) {
        int rythme = 0;
        int index = 0;
        for (Performance p:perfs) {
            if(p.getId().getIdSession() == idSession) {
                rythme += p.getRythmeMs();
                index++;
            }
        }
        return rythme/index;
    }

    //Calcul de la puissance moyenne d'une session
    public double FrequenceMoyenne(int idSession) {
        int frequence = 0;
        int index = 0;
        for (Performance p:perfs) {
            if(p.getId().getIdSession() == idSession) {
                frequence += p.getFrequenceBpm();
                index++;
            }
        }
        return frequence/index;
    }


    //Calcul des calories perdues à chaque session
    public double CaloriesTotales(int idSession){
        int calories = 0;
        for (Performance p:perfs) {
            if(p.getId().getIdSession() == idSession) {
                //System.out.println("Calories : "+calories);
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
                distance += p.getDistanceCm();
            }
        }
        return distance;
    }

    public List<Performance> getPerfSession(int idSession){
        List<Performance> performances = new ArrayList<>();

        for (int i = 0; i < this.perfs.size(); i++) {
            if(perfs.get(i).getId().getIdSession() == idSession) {
                performances.add(perfs.get(i));
            }
        }

        return performances;
    }

}
