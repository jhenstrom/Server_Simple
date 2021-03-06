package handlers;

import com.google.gson.Gson;


import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import com.sun.net.httpserver.*;


import data_access_classes.AuthTokenAccess;
import model_classes.Person;
import server.Facade;

/**
 * Created by jhenstrom on 10/25/17.
 */

public class PersonHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        Gson gson = new Gson();
        try
        {

            if (exchange.getRequestMethod().toLowerCase().equals("get"))
            {
                Headers requestHeaders = exchange.getRequestHeaders();
                if (requestHeaders.containsKey("Authorization"))
                {
                    String authToken = requestHeaders.getFirst("Authorization");
                    if (AuthTokenAccess.getInstance().getAuthToken(authToken) != null)
                    {
                        String relativePath = exchange.getRequestURI().toString();
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        if (relativePath.equals("/person/"))
                        {
                            try
                            {
                                ArrayList<Person> response = Facade.getInstance().person(AuthTokenAccess.getInstance().getAuthToken(authToken));
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
                        else
                        {
                            try
                            {
                                String[] rry = relativePath.split("/");
                                if (rry.length > 3) throw new Exception();
                                System.out.println(rry[2]);
                                Person response = Facade.getInstance().personByID(rry[2]);
                                if (!AuthTokenAccess.getInstance().getAuthToken(authToken).getUserName().equals(response.getDescendant()))
                                {
                                    throw(new Exception());
                                }
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
                                gson.toJson("Invalid AuthToken", w);
                                w.close();
                            }
                        }
                    }
                }
            }
            if(!success)
            {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respBody = exchange.getResponseBody();
                Writer w = new OutputStreamWriter(respBody);
                gson.toJson("INVALID", w);
                w.close();
            }
        }
        catch(Exception e)
        {

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            OutputStream respBody = exchange.getResponseBody();
            Writer w = new OutputStreamWriter(respBody);
            gson.toJson(e.getMessage(), w);
            w.close();
        }

    }
}
