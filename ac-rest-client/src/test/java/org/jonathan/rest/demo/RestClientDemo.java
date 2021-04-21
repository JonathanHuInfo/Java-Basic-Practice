package org.jonathan.rest.demo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

public class RestClientDemo {

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://127.0.0.1:8080/hello/world")      // WebTarget
                .request() // Invocation.BuilderJerseyClientBuilder
                .get();                                     //  Response

        String contentGet = response.readEntity(String.class);
        System.out.println("Content Get : " + contentGet);

        Entity<String> stringEntity = Entity.text("Bao Nan");
        Response responsePost = client
                .target("http://127.0.0.1:8080/hello/world")      // WebTarget
                .request() // Invocation.Builder
                .header("Content-Type", "text/plain")
                .post(stringEntity);                              //  Response
        String contentPost = responsePost.readEntity(String.class);
        System.out.println("Content Post : " + contentPost);

    }
}
