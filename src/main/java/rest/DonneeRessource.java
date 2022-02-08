package rest;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/donnee")
public class DonneeRessource {

    //Méthode pour récupérer les données performances
    @Path("/performances")
    @PUT
    @Consumes("text/plain")
    public void setPerformances(String param1){
        System.out.println(" "+param1);
    }
}
