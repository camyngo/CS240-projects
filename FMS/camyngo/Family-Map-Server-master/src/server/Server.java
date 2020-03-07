package server;

import handlers.*;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    public static void main(String [] args) {
        try{
            if(args[0] == null)
            {
                System.out.println("Proper Usage is: java program filename");
                System.exit(0);
            } else {
            String portNumber = args[0];
            new Server().run(portNumber);
            }
        } catch(IndexOutOfBoundsException e){
            System.out.println(e);
        }
    }

    public void run(String portNumber) {

        System.out.println("Initializing HTTP Server");

        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        System.out.println("Creating contexts");

        server.createContext("/", new DefaultHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());

        System.out.println("Starting server");

        server.start();

        System.out.println("Server started");

    }
}
