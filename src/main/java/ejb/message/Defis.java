package ejb.message;

import java.io.Serializable;

public class Defis implements Serializable {
    private int idUtilDefieur;
    private int idUtilDefier;
    private int distanceCourse;
    private int tempsDefieur;
    private int tempsDefier;
    private int idRameurDefieur;
    private int idRameurDefier;

    public Defis(int idUtilDefieur, int idUtilDefier, int distanceCourse, int idRameurDefieur) {
        this.idUtilDefieur = idUtilDefieur;
        this.idUtilDefier = idUtilDefier;
        this.distanceCourse = distanceCourse;
        this.idRameurDefieur = idRameurDefieur;
        this.tempsDefier = 0;
        this.tempsDefieur = 0;
    }

    public int getIdRameurDefieur() {
        return idRameurDefieur;
    }

    public void setIdRameurDefieur(int idRameurDefieur) {
        this.idRameurDefieur = idRameurDefieur;
    }

    public int getIdRameurDefier() {
        return idRameurDefier;
    }

    public void setIdRameurDefier(int idRameurDefier) {
        this.idRameurDefier = idRameurDefier;
    }

    public int getTempsDefieur() {
        return tempsDefieur;
    }

    public void setTempsDefieur(int tempsDefieur) {
        this.tempsDefieur = tempsDefieur;
    }

    public int getTempsDefier() {
        return tempsDefier;
    }

    public void setTempsDefier(int tempsDefier) {
        this.tempsDefier = tempsDefier;
    }

    public int getIdUtilDefieur() {
        return idUtilDefieur;
    }

    public void setIdUtilDefieur(int idUtilDefieur) {
        this.idUtilDefieur = idUtilDefieur;
    }

    public int getIdUtilDefier() {
        return idUtilDefier;
    }

    public void setIdUtilDefier(int idUtilDefier) {
        this.idUtilDefier = idUtilDefier;
    }

    public int getDistanceCourse() {
        return distanceCourse;
    }

    public void setDistanceCourse(int distanceCourse) {
        this.distanceCourse = distanceCourse;
    }
}
