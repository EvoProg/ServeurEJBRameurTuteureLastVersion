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
    @Produces("text/plain")
    public void addRameur(String idRameur){
        //Variables
        int identifiantRameur, valeurRameur;

        //Vérification des variables passées en paramètres et traitement de la requête
        if (idRameur != ""){
            identifiantRameur = Integer.parseInt(idRameur);

            sb.updateRameur(identifiantRameur,"",0);
        }
    }

    //Méthode permettant de récupérer le type de la session d'un rameur
    @Path("/type")
    @GET
    @Produces("text/plain")
    public String getRameurSessionType() {
        //Variable
        String type = "";
        //Vérification des variables passées en paramètres et traitement de la requête
        while (type.equals("")){
            Rameur rameur = sb.getRameur(1);
            if(rameur != null)
                type = rameur.getCourse();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Sortie de la boucle getRameurSessionType");
        System.out.println(type);
        return type;
    }

    //Méthode permettant de récupérer la valeur de la session d'un rameur
    @Path("/valeur")
    @GET
    @Produces("text/plain")
    public String getRameurSessionValeur() {
        //Variable
        String valeur = "";
        //Vérification des variables passées en paramètres et traitement de la requête
        while (valeur.equals("0")){
            Rameur rameur = sb.getRameur(1);
            if(rameur != null)
                valeur = ""+rameur.getValeur();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Sortie de la boucle getRameurSessionValeur");
        System.out.println(valeur);
        return valeur;
    }


    @Path("/Hello")
    @GET
    @Produces("text/plain")
    public String helloWorld(){
        return "Hello World";
    }

}
