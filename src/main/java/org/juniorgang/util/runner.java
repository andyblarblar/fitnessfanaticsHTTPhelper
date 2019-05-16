package org.juniorgang.util;

import javax.ws.rs.core.Response;

public class runner {

    public static void main(String[] args) {
        HTTPService service = new HTTPService(ApplicationContext.initialize());
        Response response = service.doGET();
        if (response == null) {
            System.out.println("sever cannot connect");
        } else {
            System.out.println(response.getEntity());

        }

    }
}