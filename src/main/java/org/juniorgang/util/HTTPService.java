package org.juniorgang.util;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.ConnectException;

public class HTTPService {

    private ApplicationContext context;

    public HTTPService(ApplicationContext context){
        this.context = context;
    }

    //TODO create methods for all forms of CRUD, returning

    /**
     * sends a get request to the server configured in {@link ApplicationContext} with authorizations
     * @return the full request, null if server is offline
     */
    public Response doGET(){
        WebTarget trg = context.getClient().path("/show");
        Invocation.Builder builder = trg.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization","basic " + context.getAuths());
        try {
            return builder.get();
        }
        catch (Exception e){
            System.out.println("the server refused to connect");
            return null;
        }
    }




}
