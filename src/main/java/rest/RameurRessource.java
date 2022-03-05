package rest;

/*
    Les classes de ressources sont des "plain old Java objects" (POJO) qui sont soit annotées avec
    @Path, soit ont au moins une méthode annotée avec @Path ou une signature de méthode de requête,
    tel que @GET, @PUT, @POST ou @DELETE. Les méthodes de ressources sont des méthodes d'une classe de
    ressources annotées avec une signature de méthode de requête.
 */


import ejb.entities.Rameur;
import ejb.sessions.SessionBeanLocal;
import org.json.simple.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;

@Path("/ressource")
public class RameurRessource {
    //Déclaration des variables
    //Appel de l'EJB depuis son interface
    @EJB
    private SessionBeanLocal sb;

    //Méthode permettant de set l'identifiant d'un rameur
    @Path("/identifiant")
    @GET
    @Produces("text/plain")
    public String getIdentifiantRameur(){
        String identifiant = "";
        int id = sb.getDernierIdRameur();
        identifiant += id;
        //System.out.println(identifiant);
        return identifiant;
    }

    //Méthode permettant d'ajouter un rameur à la BD
    @Path("/ajoutRameur")
    @PUT
    @Produces("text/plain")
    public void addRameur(String idRameur){
        //Variables
        int identifiantRameur, valeurRameur;

        //Vérification des variables passées en paramètres et traitement de la requête
        if (idRameur != ""){
            identifiantRameur = Integer.parseInt(idRameur);

            sb.updateRameur(identifiantRameur,"",0,0,0,0,0);
        }
    }

    //Méthode permettant de récupérer le type de la session d'un rameur
    @Path("/type")
    @PUT
    @Produces("text/plain")
    public String getRameurSessionType(String idRameur) {
        //Variables
        int identifiantRameur = Integer.parseInt(idRameur);;
        String type = "";

        //Vérification des variables passées en paramètres et traitement de la requête
        while (type.equals("")){
            Rameur rameur = sb.getRameur(identifiantRameur);
            if(rameur != null)
                type = rameur.getCourse();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("Sortie de la boucle getRameurSessionType");
        //System.out.println(type);
        return type;
    }

    //Méthode permettant de récupérer la valeur de la session d'un rameur
    @Path("/valeur")
    @PUT
    @Produces("text/plain")
    public String getRameurSessionValeur(String idRameur) {
        //Variables
        String valeur = "";
        int identifiantRameur = Integer.parseInt(idRameur);
        System.out.println(identifiantRameur);
        int val = 0;
        //Vérification des variables passées en paramètres et traitement de la requête
        while (val == 0){
            Rameur rameur = sb.getRameur(identifiantRameur);
            if(rameur != null) {
                val = rameur.getValeur();
            }
            //System.out.println("Je suis là ! : "+val);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        valeur = "" + val;

        //System.out.println("Sortie de la boucle getRameurSessionValeur");
        //System.out.println(valeur);
        return valeur;
    }

    //Méthode permettant de récupérer la valeur de la session d'un rameur
    @Path("/repos")
    @PUT
    @Produces("text/plain")
    public String getRameurSessionRepos(String idRameur) {
        //Variables
        String repos = "";
        int identifiantRameur = Integer.parseInt(idRameur);
        System.out.println(identifiantRameur);
        int val = 1;
        //Vérification des variables passées en paramètres et traitement de la requête
        while (val == 1){
            Rameur rameur = sb.getRameur(identifiantRameur);
            if(rameur != null) {
                val = rameur.getRepos();
            }
            //System.out.println("Je suis là ! : "+val);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        repos = "" + val;

        //System.out.println("Sortie de la boucle getRameurSessionValeur");
        //System.out.println(valeur);
        return repos;
    }

    //Méthode permettant de récupérer la valeur de la session d'un rameur
    @Path("/repetition")
    @PUT
    @Produces("text/plain")
    public String getRameurSessionRepetition(String idRameur) {
        //Variables
        String repetition = "";
        int identifiantRameur = Integer.parseInt(idRameur);
        System.out.println(identifiantRameur);
        int val = 1;
        //Vérification des variables passées en paramètres et traitement de la requête
        while (val == 1){
            Rameur rameur = sb.getRameur(identifiantRameur);
            if(rameur != null) {
                val = rameur.getRepetition();
            }
            //System.out.println("Je suis là ! : "+val);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        repetition = "" + val;

        //System.out.println("Sortie de la boucle getRameurSessionValeur");
        //System.out.println(valeur);
        return repetition;
    }

    @DELETE
    @Consumes("text/plain")
    public void deleteRameur(String id){
        sb.deleteRameur(id);
    }
}
