package stats;

import ejb.entities.Performance;
import ejb.entities.Utilisateur;
import ejb.sessions.CourseBean;
import ejb.sessions.ManagerBean;
import ejb.sessions.ManagerBeanLocal;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.List;

public class SimilarCos {

    //Déclaration des variables
    List<Integer> listeIdUser;

    List<Integer> listeUserConseil;

    @EJB
    private ManagerBeanLocal mg;

    //Constructeur
    public SimilarCos(int idUtil){
        mg = new ManagerBean();
        listeUserConseil = new ArrayList<>();
        init(idUtil);
    }

    public List<Integer> getListeUserConseil() {
        return listeUserConseil;
    }

    public void init(int idUtil){
        listeIdUser = new ArrayList<>();
        List<Performance> userA = new ArrayList<>();

        userA = mg.getListeCinqDernieresPerformances(idUtil);

        List<Utilisateur> utilisateurs = null;
        utilisateurs = mg.getListeUtilisateurs();

        for (int i = 0; i < utilisateurs.size(); i++) {
            if(utilisateurs.get(i).getId() != idUtil)
                listeIdUser.add(utilisateurs.get(i).getId());
        }

        List<Performance> performances = new ArrayList<>();
        double[] vecteurA = initVecteur(userA);

        for (int i = 0; i < listeIdUser.size(); i++) {
            performances = mg.getListeCinqDernieresPerformances(listeIdUser.get(i));
            double[] vecteurB = initVecteur(performances);
            SimilarCosinus(vecteurA, vecteurB, listeIdUser.get(i));
        }
    }

    public double[] initVecteur(List<Performance> perfs){
        double[] vecteur = new double[3];
        int j = 0;

        if(perfs != null && perfs.size() != 0){
            vecteur[0] = perfs.get(0).getPuissanceW();
            vecteur[1] = perfs.get(0).getDistanceCm();
            vecteur[2] = perfs.get(0).getTempsCs();
            j++;
        }

        for (int i = 0; i < perfs.size(); i++) {
            vecteur[0] += perfs.get(i).getPuissanceW();
            vecteur[1] += perfs.get(i).getDistanceCm();
            vecteur[2] += perfs.get(i).getTempsCs();
            j++;
        }

        vecteur[0] = vecteur[0]/j;
        vecteur[1] = vecteur[1]/j;
        vecteur[2] = vecteur[2]/j;

        return vecteur;
    }

    public double produitScalaire(double[] vecteurA ,double[] vecteurB){
        double res = 0;
        for (int i = 0; i < vecteurA.length; i++) {
            res += vecteurA[i] * vecteurB[i];
        }
        return res;
    }

    public double vecteurNorme(double[] vecteur){
        double norme = 0;
        for (int i = 0; i < vecteur.length; i++) {
            norme += vecteur[i] * vecteur[i];
        }
        norme = Math.sqrt(norme);
        return norme;
    }

    public void SimilarCosinus(double[] vecteurA ,double[] vecteurB, int id){
        double prodscal = produitScalaire(vecteurA, vecteurB);
        double normeVecA = vecteurNorme(vecteurA);
        double normeVecB = vecteurNorme(vecteurB);

        double res = prodscal/(normeVecA * normeVecB);

        //System.out.println(res);

        if(res >= 0.5 && res <= 1){
            //System.out.println(res);
            //System.out.println(id);
            this.listeUserConseil.add(id);
        }
    }
}
