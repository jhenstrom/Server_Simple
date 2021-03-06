package handlers;

import com.google.gson.Gson;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import com.sun.net.httpserver.*;


import data_access_classes.AuthTokenAccess;
import model_classes.Person;
import server.Facade;
import server.LoginResponse;
import server.RegisterRequest;
/**
 * Created by jhenstrom on 10/25/17.
 */

public class FillHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try
        {
            if (exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                Headers requestHeaders = exchange.getRequestHeaders();
                if (requestHeaders.containsKey("Authorization"))
                {
                    String authToken = requestHeaders.getFirst("Authorization");
                    Gson gson = new Gson();
                    String relativePath = exchange.getRequestURI().toString();
                    if (relativePath == "")
                        throw new Exception();
                    String[] parameters = relativePath.split("/");
                    if(parameters[2] == null)
                        throw new Exception();
                    String username = parameters[2];
                    int generations = -1;
                    if (parameters.length == 4)
                        generations = Integer.parseInt(parameters[3]);
                    if (generations == -1)
                        generations = 4;
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    try
                    {
                        Facade.getInstance().fill(username, generations);
                        OutputStream respBody = exchange.getResponseBody();
                        Writer w = new OutputStreamWriter(respBody);
                        gson.toJson("Fill Successfully executed", w);
                        w.close();
                        success = true;
                    }
                    catch(Exception e)
                    {
                        OutputStream respBody = exchange.getResponseBody();
                        Writer w = new OutputStreamWriter(respBody);
                        gson.toJson(e.getMessage(), w);
                        w.close();
                    }
                }
            }
            if(!success)
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch(Exception e)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
