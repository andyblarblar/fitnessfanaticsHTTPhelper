package org.juniorgang.util;

import javax.ws.rs.core.Response;

  class runner {

    public static void main(String[] args) {
        HTTPService service = new HTTPService(ApplicationContext.initialize());
        Response postResponse = service.doPOST(new User("foo","bar"));
        Response response = service.doGET();

        try {
            System.out.println(response.readEntity(User.class).getFname());
            System.out.println(postResponse.getStatus());
            /*other tests*/
        }
        catch (NullPointerException e){System.out.println(postResponse.getStatus());}
        }

    }
