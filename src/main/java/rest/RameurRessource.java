package rest;

/*
    Les classes de ressources sont des "plain old Java objects" (POJO) qui sont soit annotées avec
    @Path, soit ont au moins une méthode annotée avec @Path ou une signature de méthode de requête,
    tel que @GET, @PUT, @POST ou @DELETE. Les méthodes de ressources sont des méthodes d'une classe de
    ressources annotées avec une signature de méthode de requête.
 */


import ejb.entities.Rameur;
import ejb.sessions.SessionBeanLocal;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/ressource")
public class RameurRessource {
    //Déclaration des variables
    //Appel de l'EJB depuis son interface
    @EJB
    private SessionBeanLocal sb;

/*
    private int identifiantRameur;

    //Méthode permettant de set l'identifiant d'un rameur
    @Path("/identifiantRameur")
    @PUT
    @Produces("text/plain")
    public void setIdentifiantRameur(String idRameur){
        if(idRameur != "")
            this.identifiantRameur = Integer.parseInt(idRameur);
        else
            identifiantRameur = 0;
    }
*/

    //Méthode permettant d'ajouter un rameur à la BD
    @Path("/ajoutRameur")
    @PUT
    @Produces({"text/plain", "text/plain", "text/plain"})
    public void addRameur(String idRameur, String type, String valeur){
        //Variables
        int identifiantRameur, valeurRameur;

        //Vérification des variables passées en paramètres et traitement de la requête
        if (idRameur != "" && valeur != ""){
            identifiantRameur = Integer.parseInt(idRameur);
            valeurRameur = Integer.parseInt(valeur);

            sb.updateRameur(identifiantRameur,type,valeurRameur);
        }
    }

    //Méthode permettant de récupérer le type de la session d'un rameur selon son id
    @Path("/rameur")
    @GET
    @Produces("text/plain")
    public String getRameurSessionType(String idRameur) {
        //Variables
        int identifiantRameur;
        String type = "";
        //Vérification des variables passées en paramètres et traitement de la requête
        if (idRameur != ""){
            identifiantRameur = Integer.parseInt(idRameur);
            type = sb.getRameur(identifiantRameur).getCourse();
        }

        return type;
    }


    @Path("/Hello")
    @GET
    @Produces("text/plain")
    public String helloWorld(){
        return "Hello World";
    }

}
