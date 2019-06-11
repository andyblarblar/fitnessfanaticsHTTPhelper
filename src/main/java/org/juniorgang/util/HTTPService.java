package org.juniorgang.util;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.ConnectException;
import java.util.Scanner;

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
    public <T> Response doPOST(T user){
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
    public <T> Response doPUT(T user){
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

    /**
     * tests the auths and if the server is up
     * @return true if auths are usable
     */
    public boolean testAuths(){
        try {
            if (doGET().getStatus() != 200) return false;
            return true;
        }
        catch (Exception e){return false;}//return if offline
    }

    /**
     * sets the authorizations in memory and on disk.
     * @param auths the auths seperated as ".* .*"  or "username password"
     */
    public void setAuths(String auths){
        auths = auths.replace(' ',':');//replace spaces with a colon
        this.context.setAuths(auths);//sets in memory

        try(BufferedWriter in = new BufferedWriter(new FileWriter(new File("src/main/resources/configs.txt")))){//sets on disk
            in.write("auths:\n"+auths);//auths
            in.append("\nserver:\n").append(context.getServerAddress());//server
        } catch (IOException e) {
            /* create configs file */
        }
    }

    /**
     * sets the server address in memory and on disk formatted for use
     * @param serverAddress the server ip/adress just raw ip.
     */
    public void setServerAddress(String serverAddress){
        serverAddress = "http://"+serverAddress+":8080/users";//formating

        try(BufferedWriter in = new BufferedWriter(new FileWriter(new File("src/main/resources/configs.txt")))){//sets on disk
            in.write("auths:\n"+this.context.getAuths());//auths
            in.append("\nserver:\n").append(serverAddress);//server
        } catch (IOException e) {
            /* create configs file */
        }
        this.context.setAuths(serverAddress);//memory
    }

    /**
     * creates the configs file for the first time, needs to be detected in application
     * @param auths the "username password"
     * @param serverAdd the raw ip of the server
     */
    public static void createConfigsFile(String auths,String serverAdd) {
        File configs = new File("src/main/resources/configs.txt");
        try {
            if (configs.createNewFile()) {
                return;
            }//stops if the file is already made, this meas you can call every boot.

            auths = auths.replace(' ', ':');//replace spaces with a colon
            serverAdd = "http://" + serverAdd + ":8080/users";//formating

            try (BufferedWriter in = new BufferedWriter(new FileWriter(new File("src/main/resources/configs.txt")))) {//sets on disk
                in.write("auths:\n" + auths);
                in.append("\nserver:\n").append(serverAdd);//server
            }
        }
        catch (Exception e){}
    }
    /**
     * creates the configs file for the first time, needs to be detected in application. sets all empty.
     */
    public static void createConfigsFile() {
        File configs = new File("src/main/resources/configs.txt");
        try {
            if (!configs.createNewFile()) {
                return;
            }//stops if the file is already made, this meas you can call every boot.
            try (BufferedWriter in = new BufferedWriter(new FileWriter(new File("src/main/resources/configs.txt")))) {//sets on disk
                in.write("auths:\n" + "");
                in.append("\nserver:\n" + "");
            }
        }
        catch (Exception e){}
    }

    public String getAuths(){
        return this.context.getAuths();
    }

    public String getServerAddress(){
        return this.context.getServerAddress();
    }




}
