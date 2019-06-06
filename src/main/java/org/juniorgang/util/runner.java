package org.juniorgang.util;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

class runner {

    public static void main(String[] args) {
        if(new File("src/main/resources/configs.txt").exists()) {
            HTTPService service = new HTTPService(ApplicationContext.initialize());
        }
        /*
        Response postResponse = service.doPOST(new User("foo","bar"));
        Response response = service.doGET();
        service.doPUT(new User("fooer","barer"));
        Response response2 = service.doGET();


        try {
            System.out.println(response.readEntity(User.class).getFname());
            System.out.println(postResponse.getStatus());
            System.out.println(response2.readEntity(User.class).getFname());
            System.out.println(response2.getStatus());

        }
        catch (NullPointerException e){System.out.println(postResponse.getStatus());}
        }
        */

            HTTPService.createConfigsFile("butt","your mom");



    }
  }
