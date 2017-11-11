package server;

import java.io.*;
import java.net.*;

import com.sun.corba.se.pept.transport.EventHandler;
import com.sun.net.httpserver.*;

import handlers.ClearHandler;
import handlers.*;

/**
 * Created by jhenstrom on 10/25/17.
 */

public class Server
{
    private static final int MAX_WAITING_CONNECTIONS = 12;

    private HttpServer server;

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

        server.createContext("/clear", new ClearHandler());
        server.createContext("/event", new handlers.EventHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/", new RootHandler());

        System.out.println("Starting server");

        server.start();

        System.out.println("Server started");
    }

}
