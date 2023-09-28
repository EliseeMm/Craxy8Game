package org;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;



public class WebSocketServer extends WebSocketServlet{

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.register(Websocket.class);
    }


    
 
}
