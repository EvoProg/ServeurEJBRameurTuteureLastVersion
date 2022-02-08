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
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Stateless
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

            sb.updateRameur(identifiantRameur,"",0);
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
        System.out.println("Sortie de la boucle getRameurSessionType");
        System.out.println(type);
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
            System.out.println("Je suis là ! : "+val);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        valeur = "" + val;

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
