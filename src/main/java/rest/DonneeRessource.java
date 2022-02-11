package rest;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/donnee")
public class DonneeRessource {
    private String id;
    private String power;
    private String temps;
    private String distance;
    private String coups_pm;
    private String rythme;
    private String calories_h;
    private String calories;
    private String frequence_bpm;

    @Path("/puissance")
    @PUT
    @Consumes("text/plain")
    public void setPower(String power) {
        this.power = power;
    }

    @Path("/temps")
    @PUT
    @Consumes("text/plain")
    public void setTemps(String temps) {
        this.temps = temps;
    }

    @Path("/distance")
    @PUT
    @Consumes("text/plain")
    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Path("/coups_pm")
    @PUT
    @Consumes("text/plain")
    public void setCoups_pm(String coups_pm) {
        this.coups_pm = coups_pm;
    }

    @Path("/rythme")
    @PUT
    @Consumes("text/plain")
    public void setRythme(String rythme) {
        this.rythme = rythme;
    }

    @Path("/calories_h")
    @PUT
    @Consumes("text/plain")
    public void setCalories_h(String calories_h) {
        this.calories_h = calories_h;
    }

    @Path("/calories")
    @PUT
    @Consumes("text/plain")
    public void setCalories(String calories) {
        this.calories = calories;
    }

    @Path("/frequence_bpm")
    @PUT
    @Consumes("text/plain")
    public void setFrequence_bpm(String frequence_bpm) {
        this.frequence_bpm = frequence_bpm;
    }

    //Méthode pour récupérer les données performances
    @Path("/performances")
    @PUT
    @Consumes("text/plain")
    public void setPerformances(String param1){
        System.out.println(" "+param1);
    }
}
