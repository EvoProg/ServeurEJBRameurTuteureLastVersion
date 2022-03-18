package ejb.entities;

import javax.persistence.*;
import java.io.Serializable;

/*
    EJB permettent de représenter et de gérer des données enregistrées dans une base de données.
    En occurrence ici, EJB représentant la table "utilisateurs".
 */

@Entity
/*Requête sur la table*/
@NamedQueries({
        @NamedQuery(name = "utilisateurs", query = "select u from Utilisateur u"),  //Liste de tous les utilisateurs
        @NamedQuery(name = "utilisateur_id", query = "select u from Utilisateur u where u.id = :idUtil"), //Utilisateur selon son id
        @NamedQuery(name = "utilisateur_login",query = "select u from Utilisateur u where u.login = :login"), //Utilisateur selon son Login
        @NamedQuery(name = "utilisateur_connexion", query = "select u from Utilisateur u where u.login = :login and u.mdp = :mdp"),//Utilisateur selon son Login et son Mdp
})
@Table(name = "utilisateurs")
public class Utilisateur implements Serializable {
    //Déclaration des variables (Spécification des colonnes des tables)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Stratégie d'incrémentation de l'ID utilisateur
    @Column(name = "id_util", nullable = false)
    private Integer id;

    @Column(name = "login", length = 50)
    private String login;

    @Column(name = "mdp")
    private byte[] mdp;

    @Column(name = "sel")
    private byte[] sel;

    //Accesseurs en écriture et en lecture
    public byte[] getSel() {
        return sel;
    }

    public void setSel(byte[] sel) {
        this.sel = sel;
    }

    public byte[] getMdp() {
        return mdp;
    }

    public void setMdp(byte[] mdp) {
        this.mdp = mdp;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}