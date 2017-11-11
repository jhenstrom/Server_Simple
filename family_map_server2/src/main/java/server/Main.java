package server;

import data_access_classes.DAO;

/**
 * Created by jhenstrom on 10/30/17.
 */

public class Main {

    public static void main(String[] args)
    {
        DAO.CreateDatabase();
        String portNumber = "8080";
        new Server().run(portNumber);
    }

}
