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

    public void lancerDefis(int Distance, int idAdversaire, int idUtil, int idRameur){
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

    public void addUtilDispo(int id){
        ld.addUtil(id);
    }

    public void supprUtilDispo(int id){
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
}
