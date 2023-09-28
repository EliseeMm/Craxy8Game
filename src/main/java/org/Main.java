package org;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(1234);
        ServletContextHandler ctxt = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ctxt.setContextPath("/");
        server.setHandler(ctxt);

        ctxt.addServlet(new ServletHolder(new WebSocketServer()),"/crazyeight" );

        server.start();
        server.join();
    }
}
