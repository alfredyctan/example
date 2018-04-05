package org.afc.petstore.client;

import org.afc.petstore.client.PetstoreClient;

public class PetstoreClientLocal {
 
    public static void main(String[] args) throws Exception {
        PetstoreClient.main(new String[] {"--spring.profiles.active=local,eureka,eureka-local,hk,hk1"});
    }
}