package handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.net.httpserver.*;



/**
 * Created by jhenstrom on 10/30/17.
 */

public class RootHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        System.out.println("got here");
        String relativePath = exchange.getRequestURI().toString();
        if (relativePath.equals("/"))
            relativePath = "/index.html";
        String filePath = "ServerResources/web" + relativePath;
        Path path = Paths.get(filePath);
        exchange.sendResponseHeaders(200, 0);
        try
        {

            Files.copy(path, exchange.getResponseBody());
            exchange.getResponseBody().close();
        }
        catch(Exception e)
        {
            path = Paths.get("ServerResources/web/HTML/404.html");
            exchange.sendResponseHeaders(200, 0);
            try
            {
                Files.copy(path, exchange.getResponseBody());
                exchange.getResponseBody().close();
            }
            catch(Exception f)
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                f.printStackTrace();
            }
        }
    }
}