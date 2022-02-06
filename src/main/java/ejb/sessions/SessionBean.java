package ejb.sessions;

/*
    Les EJB session stateful sont capables de conserver l'état du bean dans des variables d'instance
    durant toute la conversation avec un client. Mais ces données ne sont pas persistantes : à la fin
    de l'échange avec le client, l'instance de l'EJB est détruite et les données sont perdues.
 */

import ejb.entities.Rameur;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

@Stateful
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

    //Méthode permettant de récupérer un rameur à partir d'un identifiant
    @Override
    public Rameur getRameur(int idRameur) {
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
    public void updateRameur(int idRameur, String type, int valeur) {
        //On vérifie si le rameur est présent dans la BD
        Rameur rameur = getRameur(idRameur);

        if(rameur != null){
            //On supprime le rameur de la BD
            em.remove(rameur);

            //On met à jour les données
            rameur.setCourse(type);
            rameur.setValeur(valeur);

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
            updateRameur(idRameur, type, valeur);
        }
    }

}
