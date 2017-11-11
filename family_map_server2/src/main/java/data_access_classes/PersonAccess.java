package data_access_classes;

import model_classes.*;
import java.sql.*;

import java.util.ArrayList;
/** Used to access the Person database table*/
public class PersonAccess
{
  private static PersonAccess personDAO;
  private String driver = "org.sqlite.JDBC";
  private String dbname = "jdbc:sqlite:familyMapServer.db";

  /**
   * Method to access a specific person in the table
   * @param personID ID of person you want to access
   * @return Person corresponding to relevant personID
   */
  public Person getPerson(String personID)
  {
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet results = null;
    Person person = null;

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
      String query = "select * from Person where PersonID = ?";
      stmt = connection.prepareStatement(query);
      stmt.setString(1, personID);
      results = stmt.executeQuery();
      String pID = results.getString(1);
      String firstName = results.getString(2);
      String lastName = results.getString(3);
      String gender = results.getString(4);
      String username = results.getString(5);
      String father = results.getString(6);
      String mother = results.getString(7);
      String spouce = results.getString(8);
      person = new Person(pID, firstName, lastName, gender, username, father, mother, spouce);
      results.close();
      stmt.close();
      connection.close();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return person;
  }

  /**
   * Method to clear the Person table
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
      String drop = "drop table if exists Person";
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
  * Method to add a Person to the database
  * @param person Person to add to database
  */
  public void add(Person person)
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
      String insert = "insert into Person values ( ?, ?, ?, ?, ?, ?, ?, ? )";
      stmt = connection.prepareStatement(insert);
      stmt.setString(1, person.getPersonID());
      stmt.setString(2, person.getFirstName());
      stmt.setString(3, person.getLastName());
      stmt.setString(4, person.getGender());
      stmt.setString(5, person.getDescendant());
      stmt.setString(6, person.getFather());
      stmt.setString(7, person.getMother());
      stmt.setString(8, person.getSpouce());
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
  * Method to remove a Person from the database
  * @param person Person to remove from database
  */
  public void remove(String username)
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
      String delete = "delete from Person where descendant = ?";
      stmt = connection.prepareStatement(delete);
      stmt.setString(1, username);
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
  * Method accessing the rows of the Person table in the database
  * @return An ArrayList of the Person's in the database
  */
  public ArrayList<Person> getRows()
  {
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet results = null;
    ArrayList<Person> people = new ArrayList<Person>();
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
      String query = "select * from Person";
      stmt = connection.prepareStatement(query);
      results = stmt.executeQuery();

      while(results.next())
      {
        String pID = results.getString(1);
        String firstName = results.getString(2);
        String lastName = results.getString(3);
        String gender = results.getString(4);
        String username = results.getString(5);
        String father = results.getString(6);
        String mother = results.getString(7);
        String spouce = results.getString(8);
        people.add(new Person(pID, firstName, lastName, gender, username, father, mother, spouce));
      }
      results.close();
      stmt.close();
      connection.close();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return people;
  }

  /**
  * Method checking Person table for the given personID
  * @param personID personID you want to check for
  * @return false if personID doesn't exsist and true if it does
  */
  public boolean personIDLookup(String personID)
  {
    return (getPerson(personID) != null);
  }

  /**
  * Method returning instance of PersonAccess
  * @return PersonAccess object to use
  */
  public static PersonAccess getInstance()
  {
    if (personDAO == null)
      personDAO = new PersonAccess();
    return personDAO;
  }
}
