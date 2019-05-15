import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.util.Scanner;

/**
 * holder class that provides data to {@link HTTPService} from the config file
 */
public class ApplicationContext {
    private String serverAddress;//could delete
    private WebTarget client;
    private String auths;

    private ApplicationContext(String serverAddress, WebTarget client) {
        this.serverAddress = serverAddress;
        this.client = client;
    }

    public ApplicationContext(String serverAddress, WebTarget client, String auths) {
        this.serverAddress = serverAddress;
        this.client = client;
        this.auths = auths;
    }

    /**
     * parses configs.txt at resources/configs.txt for settings.
     * format:
     * "server:": the line following indicates the full URI of the server
     *
     * @return an instance of ApplicationContext with settings configured from configs
     */
    public ApplicationContext initialize(){
        String temp;
        String serverAdd = "";
        Client client;
        String auths = "";
        try(Scanner sc = new Scanner(new File("resources/configs.txt"))){

            while(sc.hasNextLine()){//checks each line for an identifier

                temp = sc.nextLine();

                switch (temp){
                    case "server:":
                        serverAdd = sc.nextLine();
                        break;
                    case "auths:":
                        auths = sc.nextLine();
                        break;

                }

            }

        }
        catch(Exception e){System.out.println("no configs file found");}
        client = ClientBuilder.newClient();
        WebTarget trg= client.target(serverAdd);
        if (auths.isEmpty()){return new ApplicationContext(serverAdd,trg);}
        return new ApplicationContext(serverAdd,trg,auths);
    }

    public String getServerAddress() {
        return serverAddress;
    }

    /**
     *
     * @return a {@link WebTarget}
     */
    public WebTarget getClient() {
        return client;
    }

    public String getAuths() {
        return auths;
    }
}
