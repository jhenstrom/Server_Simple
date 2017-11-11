package data_access_classes;

import model_classes.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** Used to access the User database table*/
public class UserAccess
{

  private static UserAccess userDAO;
  String driver = "org.sqlite.JDBC";
  String dbname = "jdbc:sqlite:familyMapServer.db";

  /**
   * Method to grab a specific user from the table
   * @param username username of user you want to access
   * @return User that corresponds the the relevant username
   */
  public User getUser(String username)
  {
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet results = null;
    User user = null;

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
      String query = "select * from User where username = ?";
      stmt = connection.prepareStatement(query);
      stmt.setString(1, username);
      results = stmt.executeQuery();
      if(!results.next())
      {
        results.close();
        stmt.close();
        connection.close();
        return null;
      }
      String thisUsername = results.getString(1);
      String password = results.getString(2);
      String email = results.getString(3);
      String firstname = results.getString(4);
      String lastname = results.getString(5);
      String gender = results.getString(6);
      String personID = results.getString(7);

      user = new User(thisUsername, password, email, firstname, lastname, gender, personID);
      results.close();
      stmt.close();
      connection.close();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return user;
  }

  /**
   * Method to clear the User table
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
      String drop = "drop table if exists User";
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
  * Method to add an AuthToken to the database
  * @param user AuthToken to add to the database
  */
  public void add(User user) throws Exception
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
      String insert = "insert into User values ( ?, ?, ?, ?, ?, ?, ? )";
      stmt = connection.prepareStatement(insert);
      stmt.setString(1, user.getUserName());
      stmt.setString(2, user.getPassword());
      stmt.setString(3, user.getEmail());
      stmt.setString(4, user.getFirstName());
      stmt.setString(5, user.getLastName());
      stmt.setString(6, user.getGender());
      stmt.setString(7, user.getPersonID());
      stmt.executeUpdate();
      stmt.close();
      connection.close();
    }
    catch(SQLException e)
    {
      throw(e);
    }
  }

  /**
  * Method accessing the rows of the AuthToken table in the database
  * @return An ArrayList of the AuthToken in database
  */
  public ArrayList<User> getRows()
  {
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet results = null;
    ArrayList<User> users = new ArrayList<User>();

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
      String query = "select * from User";
      stmt = connection.prepareStatement(query);
      results = stmt.executeQuery();
      while(results.next()) {
        String thisUsername = results.getString(1);
        String password = results.getString(2);
        String email = results.getString(3);
        String firstname = results.getString(4);
        String lastname = results.getString(5);
        String gender = results.getString(6);
        String personID = results.getString(7);
        users.add(new User(thisUsername, password, email, firstname, lastname, gender, personID));
      }
      results.close();
      stmt.close();
      connection.close();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return users;
  }

  /**
  * Method checking User table for the given username
  * @param username username you want to check for
  * @return false if username doesn't exsist and true if it does
  */
  public boolean userExists(String username)
  {
    return (getUser(username) != null);
  }

  /**
  * Method returning instance of UserAccess
  * @return UserAccess object to use
  */
  public static UserAccess getInstance()
  {
    if (userDAO == null)
      userDAO = new UserAccess();
    return userDAO;
  }
}
