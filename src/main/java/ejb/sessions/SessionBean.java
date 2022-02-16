package ejb.sessions;

/*
    ’EJB session stateless (sans état conversationnel) représente des traitements de l’application indépendants de
    l’état entre le client et le serveur. N’importe quel utilisateur peut avoir recours à un EJB stateless et donc,
    il ne faut pas stocker dans un EJB stateless d’information liée à la requête où à la session d’un utilisateur.
    On retrouve ainsi les mêmes restrictions que pour le développement de Servlet. Néanmoins, les EJB stateless
    fournissent un modèle d’exécution concurrent (threading model) sûr. En effet, le conteneur d’EJB crée un pool
    d’instances pour chaque classe d’EJB stateless. Ainsi à un instant T, toutes les requêtes qui s’exécutent en
    parallèle sur un serveur utilisent une instance particulière d’un EJB stateless. Lorsque la requête est achevée,
    le conteneur d’EJB récupère l’instance de l’EJB stateless dans le pool afin de la réutiliser pour le traitement
    d’une requête à venir. Lorsqu’on développe un EJB stateless, il n’est donc pas nécessaire de protéger le code
    contre les accès concurrents.
 */

import ejb.entities.Performance;
import ejb.entities.Rameur;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class SessionBean implements SessionBeanLocal{
    //Déclaration des variables
    //Variable correspondant à une unité de persistance définie dans le fichier persistence.xml
    private final String PERSISTENCE_UNIT_NAME = "rameur";

    //Le point d'entrée d'un code permettant de manipuler des entités JPA est un objet de type EntityManagerFactory.
    private final EntityManagerFactory emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    //Cet objet de type EntityManagerFactory modélise notre unité de persistance.
    //C'est à partir de lui que l'on peut construire des objets de type EntityManager, qui nous permettent d'interagir avec la base.

    private final EntityManager em = emFactoryObj.createEntityManager();

    //Méthode permettant de récupérer tous les rameurs
    @Override
    public List<Rameur> getListeRameurs() {
        //Variable renvoyée
        List<Rameur> rameurs = null;

        //Récupération des rameurs depuis la BD
        Query q = em.createNamedQuery("rameurs");
        try{
            rameurs = q.getResultList();
        }catch (Exception e){
            //Si les rameurs n'existent pas
            System.out.println("Erreur dans getListeRameurs connection BD : "+e.getMessage());
        }

        return rameurs;
    }

    //Méthode permettant de récupérer la liste des rameurs en attente
    public List<Rameur> getListeRameursAttente(int valeur){
        //Variable renvoyée
        List<Rameur> rameurs = null;

        //Récupération des rameurs depuis la BD
        Query q = em.createNamedQuery("rameurs_valeur");
        q.setParameter("valeur",valeur);
        try{
            rameurs = q.getResultList();
        }catch (Exception e){
            //Si les rameurs n'existent pas
            System.out.println("Erreur dans getListeRameursAttente connection BD : "+e.getMessage());
        }

        return rameurs;
    }

    //Méthode permettant de récupérer un rameur à partir d'un identifiant
    @Override
    public synchronized Rameur getRameur(int idRameur) {
        //Variable renvoyée
        Rameur rameur = null;

        //Récupération du rameur selon un Id depuis la BD
        Query q = em.createNamedQuery("rameur_id");
        q.setParameter("idRameur",idRameur);

        try{
            rameur = (Rameur) q.getSingleResult();
        }catch (Exception e){
            //Si le rameur n'existe pas
            System.out.println("Erreur dans getRameur connection BD : "+e.getMessage());
        }

        return rameur;
    }

    //Méthode ajoutant un rameur dans la base de données
    @Override
    public boolean addRameur(int idRameur) {
        //Variable renvoyée
        boolean enreg = false;

        //Vérification si le rameur est déja dans la BD
        Rameur rameur = getRameur(idRameur);

        //Si le rameur n'existe pas on l'ajoute à la BD
        if (rameur == null){
            //Création d'un rameur
            rameur = new Rameur();
            //Affectations
            rameur.setId(idRameur);

            //Transaction à la BD
            em.getTransaction().begin();

            boolean transactionOk = false;
            try{
                em.persist(rameur);
                transactionOk = true;
                //Si l'ajout a bien eu lieu
            }finally {
                if(transactionOk){
                    em.getTransaction().commit();
                }else{
                    em.getTransaction().rollback();
                }
            }
            enreg = true;
        }

        return enreg;
    }

    //Méthode permettant de mettre à jour le rameur dans la base de données selon son id
    @Override
    public void updateRameur(int idRameur, String type, int valeur, int idutil, int session, int repos, int repetition) {
        //On vérifie si le rameur est présent dans la BD
        Rameur rameur = getRameur(idRameur);

        if(rameur != null){
            //On supprime le rameur de la BD
            em.getTransaction().begin();
            em.remove(rameur);

            //On met à jour les données
            rameur.setCourse(type);
            rameur.setValeur(valeur);
            rameur.setIdUtil(idutil);
            rameur.setIdSession(session);
            rameur.setRepos(repos);
            rameur.setRepetition(repetition);

            //System.out.println("Session du rameur : "+session);
            //System.out.println("Identifiant de l'utilisateur utilisant le rameur : "+idutil);

            //On remet le rameur dans la BD
            //Transaction à la BD
            em.getTransaction().begin();
            boolean transactionOk = false;

            try{
                em.persist(rameur);
                transactionOk = true;
            }finally {
                //Si l'ajout a bien eu lieu
                if (transactionOk)
                    em.getTransaction().commit();
                else
                    em.getTransaction().rollback();
            }
        }else{
            //Si le rameur n'est pas présent, on l'ajoute à la BD puis on rappelle la fonction
            addRameur(idRameur);
            updateRameur(idRameur, type, valeur,idutil,session,repos,repetition);
        }
    }

    //Méthode permettant de renvoyer le dernier ID Rameur utilisé et de l'incrémenter
    @Override
    public int getDernierIdRameur() {
        List<Rameur> rameurs = null;
        //On récupère la liste des rameurs
        rameurs = this.getListeRameurs();

        //On recherche le dernier id du rameur utilisé
        int idRameur = 0;

        if(rameurs.size() != 0)
            idRameur = rameurs.size();

        return idRameur;
    }

    @Override
    public void deleteRameur(String idRameur) {
        int idR = Integer.parseInt(idRameur);
        Rameur r = getRameur(idR);
        boolean transactionOk = false;
        try{
            em.remove(r);
            transactionOk = true;
        }finally {
            //Si l'ajout a bien eu lieu
            if (transactionOk)
                em.getTransaction().commit();
            else
                em.getTransaction().rollback();
        }
    }
}
