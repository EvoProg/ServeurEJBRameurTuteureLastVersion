package ejb.sessions;

import ejb.entities.Utilisateur;
/*import ejb.message.Defis;
import ejb.message.QueueListener;*/

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.jms.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;


/*@JMSDestinationDefinition(
        name="java:/queue/Defis",
        interfaceName = "javax.jms.Queue",
        destinationName = "QueueListener"
)*/

@Stateful
public class CourseBean {

    /*@Resource(lookup = "java:/queue/Defis")
    private Queue queue;

    @Inject
    private JMSContext context;

    private QueueListener ql;*/

    private int idUtil;
    private int idAdversaire;
    private int DistanceCourse;

    //Déclaration des variables
    //Variable correspondant à une unité de persistance définie dans le fichier persistence.xml
    private final String PERSISTENCE_UNIT_NAME = "rameur";

    //Le point d'entrée d'un code permettant de manipuler des entités JPA est un objet de type EntityManagerFactory.
    private final EntityManagerFactory emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    //Cet objet de type EntityManagerFactory modélise notre unité de persistance.
    //C'est à partir de lui que l'on peut construire des objets de type EntityManager, qui nous permettent d'interagir avec la base.

    private final EntityManager em = emFactoryObj.createEntityManager();

    /*public void init(int idUtil){
        this.idUtil = idUtil;
        ql = new QueueListener(idUtil);
    }*/

    //Retourne la liste des utilisateurs prêts pour une course
    public List<Utilisateur> getUtilisateursDispos()
    {
        List<Utilisateur> utilisateurs = null;

        Query query = em.createNamedQuery("utilisateurs_dispos");
        try{
            utilisateurs = query.getResultList();
        }catch (Exception e){
            //Si les performances n'existent pas
            System.out.println("Erreur dans getUtilisateursDispos connection BD : "+e.getMessage());
        }

        return utilisateurs;
    }

    /*public void lancerDefis(int Distance, int idAdversaire){
        this.idAdversaire = idAdversaire;
        this.DistanceCourse = Distance;
        Destination destination = queue;
        Defis d = new Defis(idUtil,this.idAdversaire,this.DistanceCourse);
        context.createProducer().send(destination, d);
    }

    public List<Defis> recevoirDefis() throws JMSException, InterruptedException {
        Destination destination = queue;
        return ql.recupererDefis();
    }*/
}
