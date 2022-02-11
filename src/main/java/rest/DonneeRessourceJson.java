package rest;
import org.json.simple.JSONObject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/donneeJson")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DonneeRessourceJson {

    @PUT
    public void putDonnee(JSONObject jo){
        int puissance = (Integer) jo.get("puissance");
        System.out.println(puissance);
    }
}
