package org.juniorgang.util;

import javax.ws.rs.core.Response;

public class runner {

    public static void main(String[] args) {
        HTTPService service = new HTTPService(ApplicationContext.initialize());
        Response response = service.doGET();
        if (response == null || response.getStatus() != 200) {
            System.out.println(response.getStatus()+" server cannot connect");
        } else {
            System.out.println(response.readEntity(User.class).getFname());
            /*other tests*/
        }

    }
}