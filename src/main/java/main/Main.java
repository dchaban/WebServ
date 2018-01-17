package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.Servlet;

public class Main {
    public static void main(String[] args) throws Exception {
        Servlet servlet = new Servlet();

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(servlet), "/mirror");

        Server server = new Server(8080);
        server.setHandler(contextHandler);

        System.out.println("Server started");
        server.start();
        server.join();
    }
}
