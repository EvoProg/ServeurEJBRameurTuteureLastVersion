package ejb.entities;

import javax.persistence.*;
import java.io.Serializable;

/*
    EJB permettent de représenter et de gérer des données enregistrées dans une base de données.
    En l'occurence ici, EJB représentant la table "rameurs".
 */

@Entity
/*Requête sur la table*/
@NamedQueries({
        @NamedQuery(name = "rameurs", query = "select r from Rameur r"),  //Liste de touts les Rameurs
        @NamedQuery(name = "rameur_id", query = "select r from Rameur r where r.id = :idRameur"), //Rameur selon un identifiant
        @NamedQuery(name = "rameurs_valeur", query = "select r from Rameur r where r.valeur = :valeur") //Liste des rameurs selon leurs valeurs
})
@Table(name = "rameurs")
public class Rameur implements Serializable {
    //Déclaration des variables (Spécification des colonnes des tables)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "id_util")
    private Integer idUtil;

    @Column(name = "id_session")
    private Integer idSession;

    @Column(name = "course", length = 50)
    private String course;

    @Column(name = "valeur")
    private Integer valeur;

    @Column(name = "repos")
    private Integer repos;

    @Column(name = "repetition")
    private Integer repetition;

    public Integer getRepetition() {
        return repetition;
    }

    public void setRepetition(Integer repetition) {
        this.repetition = repetition;
    }

    public Integer getRepos() {
        return repos;
    }

    public void setRepos(Integer repos) {
        this.repos = repos;
    }

    public Integer getValeur() {
        return valeur;
    }

    public void setValeur(Integer valeur) {
        this.valeur = valeur;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Integer getIdSession() {
        return idSession;
    }

    public void setIdSession(Integer idSession) {
        this.idSession = idSession;
    }

    public Integer getIdUtil() {
        return idUtil;
    }

    public void setIdUtil(Integer idUtil) {
        this.idUtil = idUtil;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}