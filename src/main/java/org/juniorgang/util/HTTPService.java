package org.juniorgang.util;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.ConnectException;

/**
 * A util class user to perform CRUD operations to the REST database
 */
public class HTTPService {

    private ApplicationContext context;

    /**
     * @param context create using ApplicationContext.initialize. Sets the global variables from the configs file.
     */
    public HTTPService(ApplicationContext context){
        this.context = context;
    }

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

    /**
     * performs a POST request to the server, including the passed {@link User} as a JSON payload.
     * This creates a new User AND Auth, use update to change an existing user.
     * @return the Response, null if server is offline
     */
    public Response doPOST(User user){
        WebTarget trg = context.getClient();
        Invocation.Builder builder = trg.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization","basic " + context.getAuths());
        try {
            return builder.post(Entity.entity(user, MediaType.APPLICATION_JSON));
        }
        catch (Exception e){System.out.println("the server refused");
        e.printStackTrace();}
        return null;
    }

    /**
     * performes a PUT request to the server, updating the User associated with the registered auth with
     * the one passed as a param.
     * @return the response, null if server is offline
     */
    public Response doPUT(User user){
        WebTarget trg = context.getClient().path("/update");
        Invocation.Builder builder = trg.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization","basic " + context.getAuths());
        try {
            return builder.put(Entity.entity(user, MediaType.APPLICATION_JSON));
        }
        catch (Exception e){System.out.println("the server refused");
            e.printStackTrace();}
        return null;
    }

    /**
     * deletes the auth and user associated with the configured auth. This action is irreversible, and
     * must be carefully done to avoid state issues.
     * @return the Response, null if the serve is offline
     */
    public Response doDELETE(){
        WebTarget trg = context.getClient();
        Invocation.Builder builder = trg.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization","basic " + context.getAuths());
        try{
            return builder.delete();
        }
        catch (Exception e){ System.out.println("the server refused");
        e.printStackTrace();}
        return null;
    }






}
