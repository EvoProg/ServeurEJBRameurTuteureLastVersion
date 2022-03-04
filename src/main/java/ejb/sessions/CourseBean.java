package ejb.sessions;

import ejb.entities.Utilisateur;
import ejb.message.Defis;
import ejb.message.ListDefis;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CourseBean {
    @EJB
    private ManagerBeanLocal mb;

    @EJB
    private SessionBeanLocal sb;

    private ListDefis ld;

    public CourseBean(){
        ld = ListDefis.getInstance();
    }

    public void lancerDefis(int idUtil, int idAdversaire, int Distance, int idRameur){
        Defis d = new Defis(idUtil,idAdversaire,Distance, idRameur);
        ld.addDefis(d);
        ld.supprUtil(idUtil);
    }

    public List<Defis> recevoirDefis(int idUtil){
        return ld.getDefis(idUtil);
    }

    public List<Utilisateur> getUtilisateursDispos()
    {
        List<Integer> listeIdDispos = ld.getUtil();

        List<Utilisateur> listeUtilisateursDispos = new ArrayList<>();
        for(int i=0; i<listeIdDispos.size(); i++)
        {
            listeUtilisateursDispos.add(mb.getUtilisateur(listeIdDispos.get(i)));
        }

        return listeUtilisateursDispos;
    }

    public void addUtilDispo(int id)
    {
        ld.addUtil(id);
    }

    public void supprUtilDispo(int id)
    {
        ld.supprUtil(id);
    }

    public void lancerCourse(Defis d, int idRameur){
        d.setIdRameurDefier(idRameur);
        sb.updateRameur(
                d.getIdRameurDefier(),
                "distance",
                d.getDistanceCourse(),
                d.getIdUtilDefier(),
                mb.getDerniereSession(d.getIdUtilDefier())+1,
                0,
                0
        );
        sb.updateRameur(
                d.getIdRameurDefieur(),
                "distance",
                d.getDistanceCourse(),
                d.getIdRameurDefieur(),
                mb.getDerniereSession(d.getIdUtilDefieur())+1,
                0,
                0
        );
        ld.supprUtil(d.getIdRameurDefier());
        ld.supprDefis(d);
    }

    public String ajoutTempsEtAfficheResultat(int temps, Defis d, int idUtil) throws InterruptedException {
        //cas ou l'utilisateur envoie ses résultats en dernier
        if(ld.getDefi(d.getIdUtilDefieur(),d.getIdUtilDefier())!= null)
        {
            if(idUtil == d.getIdUtilDefieur()){
                ld.getDefi(d.getIdUtilDefieur(),d.getIdUtilDefier()).setTempsDefieur(temps);
            }
            if(idUtil == d.getIdUtilDefier()){
                ld.getDefi(d.getIdUtilDefieur(),d.getIdUtilDefier()).setTempsDefier(temps);
            }
            return resultatCourse(ld.getDefi(d.getIdUtilDefieur(),d.getIdUtilDefier()), idUtil);
        }
        //cas ou l'utilisateur envoie ses résultats en premier
        else{
            if(idUtil == d.getIdUtilDefieur()){
                d.setTempsDefieur(temps);
            }
            if(idUtil == d.getIdUtilDefier()){
                d.setTempsDefier(temps);
            }
            ld.addDefis(d);
            while(ld.getDefi(d.getIdUtilDefieur(),d.getIdUtilDefier()).getTempsDefier() == 0 || ld.getDefi(d.getIdUtilDefieur(),d.getIdUtilDefier()).getTempsDefieur() == 0){
                Thread.sleep(1000);
            }
            return resultatCourse(ld.getDefi(d.getIdUtilDefieur(),d.getIdUtilDefier()), idUtil);
        }
    }

    public String resultatCourse(Defis d, int idutil){
        if(idutil == d.getIdUtilDefieur()){
            if(d.getTempsDefier()>d.getTempsDefieur()){
                return "vous avez perdu";
            }
            else{
                return "vous avez gagné";
            }
        }
        if(idutil == d.getIdRameurDefier()){
            if(d.getTempsDefieur()>d.getTempsDefier()){
                return "vous avez perdu";
            }
            else{
                return "vous avez gagné";
            }
        }
        return "erreur";
    }
}
