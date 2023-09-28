package org.webclient;

import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class clientmain {
    
    public static void main(String[] args) {
        String endpoint = "ws://crazyeight";

        try{
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            client client = new client();
            
            container.connectToServer(client.class, new URI(endpoint));

            client.sendMessage(endpoint);


            ((Session) container).close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
