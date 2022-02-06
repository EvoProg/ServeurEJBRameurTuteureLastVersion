package ejb.entities;

import javax.persistence.*;

/*
    EJB permettent de représenter et de gérer des données enregistrées dans une base de données.
    En l'occurence ici, EJB représentant la table "rameurs".
 */

@Entity
/*Requête sur la table*/
@NamedQueries({
        @NamedQuery(name = "rameurs", query = "select r from Rameur r"),  //Liste de touts les Rameurs
        @NamedQuery(name = "rameur_id", query = "select r from Rameur r where r.id = :idRameur") //Rameur selon un identifiant
})
@Table(name = "rameurs")
public class Rameur {
    //Déclaration des variables (Spécification des colonnes des tables)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "course", length = 50)
    private String course;

    @Column(name = "valeur")
    private Integer valeur;

    //Accesseurs en écriture et en lecture
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}