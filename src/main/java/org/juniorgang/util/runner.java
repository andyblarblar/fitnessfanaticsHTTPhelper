package org.juniorgang.util;

import javax.ws.rs.core.Response;

public class runner {
    public static void main(String[] args){
 HTTPService service = new HTTPService(ApplicationContext.initialize());
    Response response = service.doGET();
    System.out.println(response.getEntity());


    }



}
