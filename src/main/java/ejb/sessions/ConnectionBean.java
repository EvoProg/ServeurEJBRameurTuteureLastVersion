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

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
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

@Stateless
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
            //System.out.println("Erreur dans verifyUtilisateur connection BD : "+e.getMessage());
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
