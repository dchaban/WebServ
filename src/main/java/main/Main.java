package main;

import dbService.DBService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.Servlet;
import servlets.SignInServlet;
import servlets.SignUpServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        Servlet servlet = new Servlet();
        DBService dbService = new DBService();

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(servlet), "/mirror");
        contextHandler.addServlet(new ServletHolder(new SignUpServlet(dbService)), "/signup");
        contextHandler.addServlet(new ServletHolder(new SignInServlet(dbService)), "/signin");

        Server server = new Server(8080);
        server.setHandler(contextHandler);

        System.out.println("Server started");
        server.start();
        server.join();
    }
}
