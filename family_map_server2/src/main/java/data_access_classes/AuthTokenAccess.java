package data_access_classes;

import model_classes.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/** Accesses Authentication Token database table*/
public class AuthTokenAccess
{


  private static AuthTokenAccess authTokenDAO;
  String driver = "org.sqlite.JDBC";
  String dbname = "jdbc:sqlite:familyMapServer.db";

  /**
   * Method to access a specific AuthToken in the table
   * @param token the token of the AuthToken you want to access
   * @return AuthToken corresponding to the relevant token
   */
  public AuthToken getAuthToken(String token)
  {
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet results = null;
    AuthToken authToken = null;
    UserAccess userDAO = UserAccess.getInstance();

    try
    {
      Class.forName(driver);
    }
    catch(ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    try
    {
      connection = DriverManager.getConnection(dbname);
      String query = "select * from AuthToken where token = ?";
      stmt = connection.prepareStatement(query);
      stmt.setString(1, token);
      results = stmt.executeQuery();
      if(!results.next())
      {
        results.close();
        stmt.close();
        connection.close();
        return null;
      }
      String thisToken = results.getString(1);
      String username = results.getString(2);
      String personID = results.getString(3);
      authToken = new AuthToken(thisToken, username, personID);
      results.close();
      stmt.close();
      connection.close();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return authToken;
  }

  /**
   * Method to clear the AuthToken Table
   */
  public void clear()
  {
    Connection connection = null;
    PreparedStatement stmt = null;
    try
    {
      Class.forName(driver);
    }
    catch(ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    try
    {
      connection = DriverManager.getConnection(dbname);
      String drop = "drop table if exists AuthToken";
      stmt = connection.prepareStatement(drop);
      stmt.executeUpdate();
      stmt.close();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
  * Method adding an AuthToken to database
  * @param token AuthToken to be added to database
  */
  public void add(AuthToken token)
  {
    Connection connection = null;
    PreparedStatement stmt = null;

    try
    {
      Class.forName(driver);
    }
    catch(ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    try
    {
      connection = DriverManager.getConnection(dbname);
      String insert = "insert into AuthToken values ( ?, ?, ? )";
      stmt = connection.prepareStatement(insert);
      stmt.setString(1, token.getToken());
      stmt.setString(2, token.getUserName());
      stmt.setString(3, token.getPersonID());
      stmt.executeUpdate();
      stmt.close();
      connection.close();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
  }

  /**
  * Method accessing the rows of the AuthToken table in the database
  * @return An ArrayList of the AuthToken's currently in database
  */
  public ArrayList<AuthToken> getRows()
  {
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet results = null;
    ArrayList<AuthToken> authTokens = new ArrayList<AuthToken>();
    UserAccess userDAO = UserAccess.getInstance();

    try
    {
      Class.forName(driver);
    }
    catch(ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    try
    {
      connection = DriverManager.getConnection(dbname);
      String query = "select * from AuthToken";
      stmt = connection.prepareStatement(query);
      results = stmt.executeQuery();

      while(results.next())
      {
        String thisToken = results.getString(1);
        String username = results.getString(2);
        String personID = results.getString(3);
        authTokens.add(new AuthToken(thisToken, username, personID));
      }
      results.close();
      stmt.close();
      connection.close();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return authTokens;
  }

  /**
  * Method returning instance of AuthTokenAccess
  * @return AuthTokenAccess object to use
  */
  public static AuthTokenAccess getInstance()
  {
    if (authTokenDAO == null)
      authTokenDAO = new AuthTokenAccess();
    return authTokenDAO;
  }
}
