package data_access_classes;

/**
 * Created by jhenstrom on 10/26/17.
 */
import java.sql.*;

public class DAO {



    public static void CreateDatabase()
    {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet results = null;

        try
        {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        try
        {
            String dbname = "jdbc:sqlite:familyMapServer.db";
            connection = DriverManager.getConnection(dbname);
            String createUser = "create table if not exists User " +
                    "( " +
                    "userName text not null primary key, " +
                    "password text not null, " +
                    "email text not null, " +
                    "firstName text not null, " +
                    "lastName text not null, " +
                    "gender text not null, " +
                    "personID text not null " +
                    ")";
            stmt = connection.prepareStatement(createUser);
            stmt.executeUpdate();
            String createPerson = "create table if not exists Person " +
                    "( " +
                    "personID text not null primary key, " +
                    "firstName text not null, " +
                    "lastName text not null, " +
                    "gender text not null, " +
                    "descendant text not null, " +
                    "father text, " +
                    "mother text, " +
                    "spouce text " +
                    ")";
            stmt = connection.prepareStatement(createPerson);
            stmt.executeUpdate();
            String createEvent = "create table if not exists Event " +
                    "( " +
                    "eventID text not null primary key, " +
                    "personID text not null, " +
                    "descendant text not null, " +
                    "latitude real not null, " +
                    "longitude real not null, " +
                    "country text not null, " +
                    "city text not null, " +
                    "eventType text not null, " +
                    "year text not null " +
                    ");";
            stmt = connection.prepareStatement(createEvent);
            stmt.executeUpdate();
            String createAuthToken = "create table if not exists AuthToken " +
                    "( " +
                    "token text not null primary key, " +
                    "username text not null, " +
                    "personID text not null " +
                    ")";
            stmt = connection.prepareStatement(createAuthToken);
            stmt.executeUpdate();
            stmt.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
