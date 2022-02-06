package ejb.sessions;

/*
    Les EJB session stateful sont capables de conserver l'état du bean dans des variables d'instance
    durant toute la conversation avec un client. Mais ces données ne sont pas persistantes : à la fin
    de l'échange avec le client, l'instance de l'EJB est détruite et les données sont perdues.
 */

import ejb.entities.Utilisateur;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

@Stateful
public class ConnectionBean implements ConnectionBeanLocal{
    //Déclaration des variables
    //Variable correspondant à une unité de persistance définie dans le fichier persistence.xml
    private final String PERSISTENCE_UNIT_NAME = "rameur";

    //Le point d'entrée d'un code permettant de manipuler des entités JPA est un objet de type EntityManagerFactory.
    private final EntityManagerFactory emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    //Cet objet de type EntityManagerFactory modélise notre unité de persistance.
    //C'est à partir de lui que l'on peut construire des objets de type EntityManager, qui nous permettent d'interagir avec la base.

    private final EntityManager em = emFactoryObj.createEntityManager();

    //On récupère l'EJB déployé
    @EJB
    private ManagerBeanLocal mb;

    //Méthode renvoyant un Utilisateur
    public Utilisateur getUtilisateur(String login){
        //Variable renvoyée
        Utilisateur utilisateur = null;

        //Récupération de l'utilisateur depuis la BD
        Query q = em.createNamedQuery("utilisateur_login");
        q.setParameter("login",login);

        try{
            utilisateur = (Utilisateur) q.getSingleResult();
        }catch (Exception e) {
            //Si l'utilisateur n'existe pas
            System.out.println("Erreur dans verifyUtilisateur connection BD : "+e.getMessage());
        }
        return utilisateur;
    }

    //Méthode permettant à un utilisateur de se connecter
    public boolean verifyConnexion(HttpServletRequest request){
        //Variable renvoyée
        boolean verify = false;

        //On récupère les données de la page
        String login = request.getParameter("username");
        String password = request.getParameter("password");

        //Récupération du sel de l'utilisateur par requête
        Utilisateur utilisateur = getUtilisateur(login);

        //Si l'utilisateur n'existe pas
        if(utilisateur == null) {
            verify = false;
        }
        else {
            //Variables permettant le déchiffrement des mdp (Tableau d'octets)
            byte[] salt = utilisateur.getSel();
            byte[] userHash = utilisateur.getMdp();
            byte[] hash = null;

            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            try {
                //Génération d'une clé de chiffrement suivant "Password-Based Key Derivation Function"
                SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                //Génération du mot de passe chiffré
                hash = factory.generateSecret(spec).getEncoded();
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                System.out.println("Erreur dans verifyConnexion déchiffrement: " + e.getMessage());
            }

            //On renvoie dans la requête Http les variables Utilisateur et ses dernières performances
            request.setAttribute("Utilisateur", utilisateur);
            request.setAttribute("DernieresPerformances", mb.getDerniereSession(utilisateur.getId()));

            //Vérification si bon mdp
            verify = Arrays.equals(hash, userHash);
        }
        return verify;
    }

    //Méthode ajoutant un utilisateur dans la BD après s'être enregistré
    public boolean addUtilisateur(HttpServletRequest request)
    {
        //Variable renvoyée
        boolean enreg = false;

        //On récupère les données de la page
        String login = request.getParameter("username");
        String password = request.getParameter("password");

        //Chiffrement du mot de passe
        //Génération de sel aléatoire
        SecureRandom random = new SecureRandom();

        byte[] salt = new byte[16];
        random.nextBytes(salt);

        byte[] hash = null;

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536,128);
        try{
            //Génération d'une clé de chiffrement suivant "Password-Based Key Derivation Function"
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            //Génération du mot de passe chiffré
            hash = factory.generateSecret(spec).getEncoded();
        }catch (NoSuchAlgorithmException | InvalidKeySpecException e){
            System.out.println("Erreur dans addUtilisateur déchiffrement: "+e.getMessage());
        }

        //Vérification si l'utilisateur est déja dans la BD
        Utilisateur utilisateur = getUtilisateur(login);

        //Si l'utilisateur n'existe pas on l'ajoute à la BD
        if(utilisateur == null)
        {
            //Création d'un utilisateur
            utilisateur = new Utilisateur();
            //Affectations
            utilisateur.setLogin(login);
            utilisateur.setMdp(hash);
            utilisateur.setSel(salt);

            //Transaction à la BD
            em.getTransaction().begin();

            boolean transactionOk = false;
            try {
                em.persist(utilisateur);
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
}
