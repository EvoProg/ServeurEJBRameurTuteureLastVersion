package rest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ejb.entities.Performance;
import ejb.sessions.ManagerBeanLocal;
import org.json.simple.JSONObject;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/donneeJson")
public class DonneeRessourceJson {
    @EJB
    private ManagerBeanLocal mg;

    @Path("/puissance")
    @PUT
    @Consumes("text/plain")
    @Produces("text/plain")
    public void putDonnee(String jo){
        System.out.println(jo);
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Performance p = gson.fromJson(jo, Performance.class);
        mg.addPerformances(p);
    }
}
