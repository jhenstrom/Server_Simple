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

import com.sun.net.httpserver.*;


import data_access_classes.AuthTokenAccess;
import model_classes.User;
import server.Facade;
import server.LoadRequest;
import server.LoginResponse;
import server.RegisterRequest;

/**
 * Created by jhenstrom on 10/25/17.
 */

public class LoadHandler implements HttpHandler {

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
                    //read request body into object
                    Gson gson = new Gson();
                    InputStream stream = exchange.getRequestBody();
                    Reader reader = new InputStreamReader(stream);
                    LoadRequest request = gson.fromJson(reader, LoadRequest.class);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    try
                    {
                        Facade.getInstance().load(request);
                        String response = "Successfully added " + request.getUsers().length +
                                " users, " + request.getPeople().length + " persons, and " +
                                request.getEvents().length + " events to the database";
                        OutputStream respBody = exchange.getResponseBody();
                        Writer w = new OutputStreamWriter(respBody);
                        gson.toJson(response, w);
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
