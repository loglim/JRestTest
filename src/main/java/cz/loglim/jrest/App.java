package cz.loglim.jrest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {
        System.out.println("Server starting...");
        Server server = new Server(new InetSocketAddress("127.0.0.1", 8080));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        ServletHolder sh = context.addServlet(ServletContainer.class, "/api/*");
        sh.setInitOrder(1);
        sh.setInitParameter("jersey.config.server.provider.classnames", "org.glassfish.jersey.jackson.JacksonFeature");
        sh.setInitParameter("jersey.config.server.provider.packages", "cz.loglim.jrest.controller");

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            server.destroy();
        }

        System.out.println("Server thread finished!");
    }

}
