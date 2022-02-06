package rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/*
    Une simple classe qui hérite de javax.ws.rs.core.Application qui est annotée par @ApplicationPath
    C'est le point d'entrée de notre application REST, toutes les ressources seront accessibles à partir de :
    /ServeurEJBRameurTutore-1.0-SNAPSHOT/rest
 */

@ApplicationPath("/rest")
public class RestApplication extends Application {
}
