package cz.loglim.jrest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {
        System.out.println("[JRestTest Server Application]");

        InetSocketAddress address = null;
        Scanner scanner = new Scanner(System.in);
        while(address == null) {
            System.out.println("Please enter target IPv4 address (and press ENTER):");
            String ip = scanner.next();
            try {
                address = new InetSocketAddress(InetAddress.getByName(ip), 8080);
            } catch (Exception e) {
                System.out.println("Wrong IPv4 format \"***.***.***.***\"");
                address = null;
            }
        }

        System.out.println("Server is running at " + address.toString());
        Server server = new Server(address);
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
