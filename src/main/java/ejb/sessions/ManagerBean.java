package ejb.sessions;

import ejb.entities.Performance;
import ejb.entities.PerformanceId;
import ejb.entities.Utilisateur;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/*
    Les EJB session stateful sont capables de conserver l'état du bean dans des variables d'instance
    durant toute la conversation avec un client. Mais ces données ne sont pas persistantes : à la fin
    de l'échange avec le client, l'instance de l'EJB est détruite et les données sont perdues.
 */

@Stateful
public class ManagerBean implements ManagerBeanLocal{
    //Déclaration des variables
    //Variable correspondant à une unité de persistance définie dans le fichier persistence.xml
    private final String PERSISTENCE_UNIT_NAME = "rameur";

    //Le point d'entrée d'un code permettant de manipuler des entités JPA est un objet de type EntityManagerFactory.
    private final EntityManagerFactory emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    //Cet objet de type EntityManagerFactory modélise notre unité de persistance.
    //C'est à partir de lui que l'on peut construire des objets de type EntityManager, qui nous permettent d'interagir avec la base.

    private final EntityManager em = emFactoryObj.createEntityManager();


    //Méthode pour récupérer une Liste de Performances depuis la BD
    @Override
    public List<Performance> getListeToutePerformances() {
        //Variable renvoyée
        List<Performance> performances = null;

        //Récupération des performances depuis la BD
        Query q = em.createNamedQuery("performances");
        try{
            performances = q.getResultList();
        }catch (Exception e){
            //Si les performances n'existent pas
            System.out.println("Erreur dans getListeToutePerformances connection BD : "+e.getMessage());
        }

        return performances;
    }

    //Méthode pour récupérer une Liste de Performances selon un Utilisateur depuis la BD
    @Override
    public List<Performance> getListePerformancesParUtilisateur(int idUtil) {
        //Variable renvoyée
        List<Performance> performances = null;

        //Récupération des performances depuis la BD
        Query q = em.createNamedQuery("performances_utilisateur");
        q.setParameter("idUtil",idUtil);

        try{
            performances = q.getResultList();
        }catch (Exception e){
            //Si les performances n'existe pas
            System.out.println("Erreur dans getListePerformancesParUtilisateur connection BD : "+e.getMessage());
        }

        return performances;
    }

    //Méthode pour récupérer une Liste de Performances selon un Utilisateur et une Session depuis la BD
    @Override
    public List<Performance> getListePerformancesParUtilisateurSession(int idUtil, int idSession) {
        //Variable renvoyée
        List<Performance> performances = null;

        //Récupération des performances depuis la BD
        Query q = em.createNamedQuery("performances_utilisateur_session");
        q.setParameter("idUtil",idUtil);
        q.setParameter("idSession", idSession);

        try{
            performances = q.getResultList();
        }catch (Exception e){
            //Si les performances n'existe pas
            System.out.println("Erreur dans getListePerformancesParUtilisateurSession connection BD : "+e.getMessage());
        }

        return performances;
    }

    //Méthode pour récupérer la dernière Liste de Performances selon un Utilisateur depuis la BD
    @Override
    public List<Performance> getListeDernieresPerformances(int idUtil) {
        //Variable renvoyée
        List<Performance> performances = null;

        //On appel la méthode permettant de rechercher la dernière session effectuer par un Utilisateur
        int idSession = this.getDerniereSession(idUtil);

        //On vérifie si la dernière Session n'est pas égale à 0
        if(idSession != 0)
        {
            //On récupère la dernière Session de l'utilisateur en appelant la méthode
            // pour récupérer une Liste de Performances selon un Utilisateur et une Session depuis la BD
            performances = this.getListePerformancesParUtilisateurSession(idUtil, idSession);
        }

        return performances;
    }

    //Méthode pour récupérer les cinq dernières Listes de Performances selon un Utilisateur depuis la BD
    @Override
    public List<Performance> getListeCinqDernieresPerformances(int idUtil) {
        //Variable renvoyée
        List<Performance> performances = null;

        //On appel la méthode permettant de rechercher la dernière session effectuer par un Utilisateur
        int idSession = this.getDerniereSession(idUtil);

        //On vérifie si la dernière Session n'est pas égale à 0
        if(idSession != 0)
        {
            //On récupère les dernières Sessions de l'utilisateur en appelant la méthode
            // pour récupérer une Liste de Performances selon un Utilisateur et une Session depuis la BD
            if(idSession >= 5)
            {
                for (int i = idSession; i > idSession-5; i++) {
                    List<Performance> p = this.getListePerformancesParUtilisateurSession(idUtil,idSession);

                    for (int j = 0; j < p.size(); j++) {
                        performances.add(p.get(i));
                    }
                }
            }else{
                //Sinon on renvoie la liste des performances tout entière de l'utilisateur
                performances = this.getListePerformancesParUtilisateur(idUtil);
            }
        }

        return performances;
    }

    //Méthode pour récupérer un Utilisateur selon un ID depuis la BD
    @Override
    public Utilisateur getUtilisateur(int idUtil) {
        //Variable renvoyée
        Utilisateur utilisateur = null;

        //Récupération de l'utilisateur selon un Id depuis la BD
        Query q = em.createNamedQuery("utilisateur_id");
        q.setParameter("idUtil",idUtil);

        try{
            utilisateur = (Utilisateur) q.getSingleResult();
        }catch (Exception e) {
            //Si l'utilisateur n'existe pas
            System.out.println("Erreur dans verifyUtilisateur connection BD : "+e.getMessage());
        }

        return utilisateur;
    }

    //Méthode permettant de rechercher la dernière session effectuer par un Utilisateur
    @Override
    public int getDerniereSession(int idUtil) {
        List<Performance> performances = null;
        //On récupère la liste des performances de l'utilisateur
        performances = this.getListePerformancesParUtilisateur(idUtil);

        //On recherche la dernière session de l'utilisateur
        int idSession = 0;
        for (int i = 0; i < performances.size(); i++) {
            if(idSession > performances.get(i).getId().getIdSession())
                idSession = performances.get(i).getId().getIdSession();
        }

        return idSession;
    }

    //Méthode ajoutant une performance et ses valeurs dans la BD
    @Override
    public void addPerformances(int temps, int distance, int coups, int rythme, int caloriesH, int calories, int frequence, int idUtil, int idSession) {
        //On crée une variable Performance
        Performance performance = new Performance();
        //Et son Id
        PerformanceId performanceId = new PerformanceId();

        //Variable de temps
        long timer = System.currentTimeMillis();

        //On ajoute les valeurs dans les classes
        //Id
        performanceId.setTimestamp(timer);
        performanceId.setIdSession(idSession);
        performanceId.setIdUtil(idUtil);

        //Performance
        performance.setTempsCs(temps);
        performance.setDistanceCm(distance);
        performance.setCoupsPm(coups);
        performance.setRythmeMs(rythme);
        performance.setCaloriesH(caloriesH);
        performance.setCalories(calories);
        performance.setFrequenceBpm(frequence);

        //On ajoute l'Id à la performance
        performance.setId(performanceId);

        //On ajoute les données dans la BD
        em.getTransaction().begin();

        boolean transactionOk = false;
        try{
            em.persist(performance);
            transactionOk = true;
            //Si l'ajout a bien eu lieu
        }finally {
            if(transactionOk)
                em.getTransaction().commit();
            else
                em.getTransaction().rollback();
        }
    }
}
