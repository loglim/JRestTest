package cz.loglim.jrest;

import cz.loglim.jrest.dao.ItemDao;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {
        System.out.println("Server starting...");
        Server server = new Server(8080);
        ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        ctx.setContextPath("/");
        server.setHandler(ctx);

        ServletHolder sh = ctx.addServlet(ServletContainer.class, "/api/*");
        sh.setInitOrder(1);
        sh.setInitParameter("jersey.config.server.provider.packages", "cz.loglim.jrest.controller");

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.ALL, null, ex);
        } finally {
            server.destroy();
        }

        ItemDao.finish();
        System.out.println("Server finished!");
    }

}
