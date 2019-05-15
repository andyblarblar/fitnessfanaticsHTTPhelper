import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class HTTPService {

    private ApplicationContext context;

    public HTTPService(ApplicationContext context){
        this.context = context;
    }

    //TODO create methods for all forms of CRUD, returning

    public Response doGET(){
        WebTarget temp = context.getClient().path("/show");
        /* Add headers to request and then fire */

    }







}
