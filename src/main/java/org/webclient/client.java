package org.webclient;

import javax.websocket.*;
import javax.websocket.Session;

@ClientEndpoint
public class client {

    private Session session;

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        System.out.println("Connected");
    }

    @OnMessage
    public void onMessage(String message){
        System.out.println("message:" + message);
    }

    @OnClose
    public void onClose(){
        System.out.println("closed");
    }
    
    public void sendMessage(String message){
        try{
            session.getBasicRemote().sendText(message, false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
