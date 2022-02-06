package ejb.entities;


import javax.persistence.*;
import java.io.Serializable;

/*
    EJB permettent de représenter et de gérer des données enregistrées dans une base de données.
    En l'occurence ici, EJB représentant la table "performances".
 */

@Entity
/*Requête sur la table*/
@NamedQueries({
        @NamedQuery(name = "performances", query = "select p from Performance p"),  //Liste de toutes les Performances
        @NamedQuery(name = "performances_utilisateur",query = "select p from Performance p where p.id.idUtil = :idUtil"), //Performances selon un Utilisateur
        @NamedQuery(name = "performances_utilisateur_session", query = "select p from Performance  p where p.id.idUtil = :idUtil and p.id.idSession = :idSession"),
        @NamedQuery(name = "dernieres_performances", query = "select p from Performance p where p.id.idUtil = :idUtil ORDER BY id.idSession")
})
@Table(name = "performances")
public class Performance implements Serializable {
    //Déclaration des variables (Spécification des colonnes des tables)
    @EmbeddedId
    private PerformanceId id;

    @Column(name = "temps_cs")
    private Integer tempsCs;

    @Column(name = "distance_cm")
    private Integer distanceCm;

    @Column(name = "coups_pm")
    private Integer coupsPm;

    @Column(name = "puissance_w")
    private Integer puissanceW;

    @Column(name = "rythme_ms")
    private Integer rythmeMs;

    @Column(name = "calories_h")
    private Integer caloriesH;

    @Column(name = "calories")
    private Integer calories;

    @Column(name = "frequence_bpm")
    private Integer frequenceBpm;

    //Accesseurs en écriture et en lecture
    public Integer getFrequenceBpm() {
        return frequenceBpm;
    }

    public void setFrequenceBpm(Integer frequenceBpm) {
        this.frequenceBpm = frequenceBpm;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getCaloriesH() {
        return caloriesH;
    }

    public void setCaloriesH(Integer caloriesH) {
        this.caloriesH = caloriesH;
    }

    public Integer getRythmeMs() {
        return rythmeMs;
    }

    public void setRythmeMs(Integer rythmeMs) {
        this.rythmeMs = rythmeMs;
    }

    public Integer getPuissanceW() {
        return puissanceW;
    }

    public void setPuissanceW(Integer puissanceW) {
        this.puissanceW = puissanceW;
    }

    public Integer getCoupsPm() {
        return coupsPm;
    }

    public void setCoupsPm(Integer coupsPm) {
        this.coupsPm = coupsPm;
    }

    public Integer getDistanceCm() {
        return distanceCm;
    }

    public void setDistanceCm(Integer distanceCm) {
        this.distanceCm = distanceCm;
    }

    public Integer getTempsCs() {
        return tempsCs;
    }

    public void setTempsCs(Integer tempsCs) {
        this.tempsCs = tempsCs;
    }

    public PerformanceId getId() {
        return id;
    }

    public void setId(PerformanceId id) {
        this.id = id;
    }
}