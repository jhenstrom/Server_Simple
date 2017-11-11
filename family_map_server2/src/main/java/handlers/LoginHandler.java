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
import server.Facade;
import server.LoginRequest;
import server.LoginResponse;
import server.RegisterRequest;

/**
 * Created by jhenstrom on 10/25/17.
 */

public class LoginHandler implements HttpHandler {

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
                    if (true)
                    {
                        //read request body into object
                        Gson gson = new Gson();
                        InputStream stream = exchange.getRequestBody();
                        Reader reader = new InputStreamReader(stream);
                        LoginRequest request = gson.fromJson(reader, LoginRequest.class);

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        if(!request.valid())
                        {

                            OutputStream respBody = exchange.getResponseBody();
                            Writer w = new OutputStreamWriter(respBody);
                            gson.toJson("Invalid Request", w);
                            w.close();
                        }
                        else
                        {
                            try
                            {

                                LoginResponse response = Facade.getInstance().login(request);
                                OutputStream respBody = exchange.getResponseBody();
                                Writer w = new OutputStreamWriter(respBody);
                                gson.toJson(response, w);
                                w.close();
                                success = true;
                            }
                            catch (Exception e)
                            {
                                OutputStream respBody = exchange.getResponseBody();
                                Writer w = new OutputStreamWriter(respBody);
                                gson.toJson(e.getMessage(), w);

                                w.close();
                            }
                        }
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

            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }

    }
}
