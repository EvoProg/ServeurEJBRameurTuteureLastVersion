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

import ejb.entities.Performance;
import ejb.entities.PerformanceId;
import ejb.entities.Rameur;
import ejb.entities.Utilisateur;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ManagerBean implements ManagerBeanLocal{
    //Déclaration des variables
    //Variable correspondant à une unité de persistance définie dans le fichier persistence.xml
    private final String PERSISTENCE_UNIT_NAME = "rameur";

    //Le point d'entrée d'un code permettant de manipuler des entités JPA est un objet de type EntityManagerFactory.
    private final EntityManagerFactory emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    //Cet objet de type EntityManagerFactory modélise notre unité de persistance.
    //C'est à partir de lui que l'on peut construire des objets de type EntityManager, qui nous permettent d'interagir avec la base.

    private final EntityManager em = emFactoryObj.createEntityManager();

    @EJB
    private SessionBeanLocal sb;


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
            //System.out.println("Erreur dans getListeToutePerformances connection BD : "+e.getMessage());
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
            //System.out.println("Erreur dans getListePerformancesParUtilisateur connection BD : "+e.getMessage());
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
            //System.out.println("Erreur dans getListePerformancesParUtilisateurSession connection BD : "+e.getMessage());
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
        //System.out.println(idSession);

        //On vérifie si la dernière Session n'est pas égale à 0
        if(idSession != 0)
        {
            //On récupère les dernières Sessions de l'utilisateur en appelant la méthode
            // pour récupérer une Liste de Performances selon un Utilisateur et une Session depuis la BD
            if(idSession > 5)
            {
                performances = new ArrayList<>();
                for (int i = (idSession-4); i <= idSession; i++) {
                    List<Performance> p = this.getListePerformancesParUtilisateurSession(idUtil,i);
                    for (int j = 0; j < p.size(); j++) {
                        performances.add(p.get(j));
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
            //System.out.println("Erreur dans verifyUtilisateur connection BD : "+e.getMessage());
        }

        return utilisateur;
    }

    @Override
    public List<Utilisateur> getListeUtilisateurs() {
        //Variable renvoyée
        List<Utilisateur> utilisateurs = null;

        //Récupération des performances depuis la BD
        Query q = em.createNamedQuery("utilisateurs");
        try{
            utilisateurs = q.getResultList();
        }catch (Exception e){
            //Si les performances n'existent pas
            //System.out.println("Erreur dans getListeToutePerformances connection BD : "+e.getMessage());
        }

        return utilisateurs;
    }

    //Méthode permettant de rechercher la dernière session effectuer par un Utilisateur
    @Override
    public synchronized int getDerniereSession(int idUtil) {
        List<Performance> performances = null;
        //On récupère la liste des dernières performances de l'utilisateur
        performances = this.getListePerformancesParUtilisateur(idUtil);

        int idSession = 0;
        if (performances != null){
            for (int i = 0; i < performances.size(); i++) {
                //System.out.println(performances.get(i).getId().getIdSession());
                if (idSession <= performances.get(i).getId().getIdSession())
                    idSession = performances.get(i).getId().getIdSession();
            }
        }

        //System.out.println("Identifiant terminal : "+idSession);

        return idSession;
    }

    //Méthode ajoutant une performance et ses valeurs dans la BD
    @Override
    public void addPerformances(Performance p) {
        //On crée une variable Performance
        //Et son Id
        //Performance performance = new Performance();
        PerformanceId performanceId = new PerformanceId();

        //Variable de temps
        long timer = System.currentTimeMillis();

        //On ajoute les valeurs dans les classes
        //Id
        int idRameur = p.getIdRameur();

        Rameur rameur = sb.getRameur(idRameur);

        performanceId.setTimestamp(timer);
        performanceId.setIdSession(rameur.getIdSession());
        performanceId.setIdUtil(rameur.getIdUtil());

        //System.out.println("addPerf/Identifiant de l'utilisateur : " + performanceId.getIdUtil());
        //System.out.println("addPerf/Session de l'utilisateur : " + performanceId.getIdSession());

        //On ajoute l'Id à la performance
        //performance.setId(performanceId);
        p.setId(performanceId);

        //On ajoute les données dans la BD
        em.getTransaction().begin();

        boolean transactionOk = false;
        try{
            em.persist(p);
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
