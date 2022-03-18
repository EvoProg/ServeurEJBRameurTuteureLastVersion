package ejb.sessions;

/*
    EJB session stateless (sans état conversationnel) représente des traitements de l’application indépendants de
    l’état entre le client et le serveur. N’importe quel utilisateur peut avoir recours à un EJB stateless et donc,
    il ne faut pas stocker dans un EJB stateless d’information liée à la requête ou à la session d’un utilisateur.
    On retrouve ainsi les mêmes restrictions que pour le développement de Servlet. Néanmoins, les EJB stateless
    fournissent un modèle d’exécution concurrent (threading model) sûr. En effet, le conteneur d’EJB crée un pool
    d’instances pour chaque classe d’EJB stateless. Ainsi à un instant T, toutes les requêtes qui s’exécutent en
    parallèle sur un serveur utilisent une instance particulière d’un EJB stateless. Lorsque la requête est achevée,
    le conteneur d’EJB récupère l’instance de l’EJB stateless dans le pool afin de la réutiliser pour le traitement
    d’une requête à venir. Lorsqu’on développe un EJB stateless, il n’est donc pas nécessaire de protéger le code
    contre les accès concurrents.
 */

import ejb.entities.Utilisateur;
import ejb.objects.Defis;
import ejb.objects.ListDefis;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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
        d.setAccepte(true);
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
                d.getIdUtilDefieur(),
                mb.getDerniereSession(d.getIdUtilDefieur())+1,
                0,
                0
        );
        ld.supprUtil(d.getIdRameurDefier());
    }

    public String ajoutTempsEtAfficheResultat(int temps, Defis d, int idUtil) throws InterruptedException {
        if(idUtil == d.getIdUtilDefieur()){
            d.setTempsDefieur(temps);
        }
        if(idUtil == d.getIdUtilDefier()){
            d.setTempsDefier(temps);
        }
        while(d.getTempsDefieur() == 0 || d.getTempsDefier() == 0){
            Thread.sleep(1000);
        }
        return resultatCourse(d , idUtil);
    }

    public String resultatCourse(Defis d, int idutil){
        System.out.println("idUtil" + idutil);
        System.out.println("idDefier " + d.getIdUtilDefier());
        System.out.println("idDefieur " + d.getIdUtilDefieur());
        System.out.println("temps Defier " + d.getTempsDefier());
        System.out.println("temps Defier " + d.getTempsDefieur());
        if(idutil == d.getIdUtilDefieur()){
            System.out.println("je suis dans le Defieur");
            if(d.getTempsDefier()<d.getTempsDefieur()){
                return "vous avez perdu avec un temps de "+(d.getTempsDefieur()/100)+"s";
            }
            else{
                return "vous avez gagné avec un temps de "+(d.getTempsDefieur()/100)+"s";
            }
        }
        if(idutil == d.getIdUtilDefier()){
            System.out.println("je suis dans le Defier");
            if(d.getTempsDefieur()<d.getTempsDefier()){
                return "vous avez perdu avec un temps de "+(d.getTempsDefier()/100)+"s";
            }
            else{
                return "vous avez gagné avec un temps de "+(d.getTempsDefier()/100)+"s";
            }
        }
        return "erreur";
    }

    public Defis getDefi(int idDefieur, int idDefie)
    {
        return ld.getDefi(idDefieur, idDefie);
    }
}
