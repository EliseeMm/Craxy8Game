package org;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/crazyeight")
public class Websocket {
    

    
    @OnOpen
    public void onOpen(Session session){

        
    }

    @OnClose
    public void onClose(Session session) throws IOException{

    }

    @OnError
    public void onError(Session session,Throwable throwable){

    }

    // @OnMessage
    // public void onMessage(Session session,Message message){

    // }
}

