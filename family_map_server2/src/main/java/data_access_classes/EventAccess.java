package data_access_classes;

import model_classes.Event;
import model_classes.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/** Accesses Event database table*/
public class EventAccess
{

  private static EventAccess eventDAO;
  String driver = "org.sqlite.JDBC";
  String dbname = "jdbc:sqlite:familyMapServer.db";

  /**
   * Method to access a specific event in the table
   * @param eventID ID of event you want to access
   * @return Event corresponding to the relevant eventID
   */
  public Event getEvent(String eventID)
  {
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet results = null;
    Event event = null;
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
      String query = "select * from Event where eventID = ?";
      stmt = connection.prepareStatement(query);
      stmt.setString(1, eventID);
      results = stmt.executeQuery();
      String eID = results.getString(1);
      String personID = results.getString(2);
      String username = results.getString(3);
      double latitude = (double)results.getInt(4);
      double longitude = (double)results.getInt(5);
      String country = results.getString(6);
      String city = results.getString(7);
      String eventType = results.getString(8);
      int year = results.getInt(9);
      event = new Event(eID, username, personID,latitude, longitude, country, city, eventType, year);
      results.close();
      stmt.close();
      connection.close();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return event;
  }

  /**
   * Method to clear Event table
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
      String drop = "drop table if exists Event";
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
  * Method adding an Event to database
  * @param event Event to be added to database
  */
  public void add(Event event)
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
      String insert = "insert into Event values ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
      stmt = connection.prepareStatement(insert);
      stmt.setString(1, event.getEventID());
      stmt.setString(2, event.getPersonID());
      stmt.setString(3, event.getDescendant());
      stmt.setDouble(4, event.getLatitude());
      stmt.setDouble(5, event.getLongitude());
      stmt.setString(6, event.getCountry());
      stmt.setString(7, event.getCity());
      stmt.setString(8, event.getEventType());
      stmt.setInt(9, event.getYear());
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
  * Method removing an Event from database
  * @param event Event to be removed from database
  */
  public void remove(String userName)
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
      String delete = "delete from Event where descendant = ?";
      stmt = connection.prepareStatement(delete);
      stmt.setString(1, userName);
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
  * Method accessing the row(s) of the Event table in the database
  * @return An ArrayList of the Event's currently in the database
  */
  public ArrayList<Event> getRows()
  {
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet results = null;
    ArrayList<Event>  events = new ArrayList<Event>();
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
      String query = "select * from Event";
      stmt = connection.prepareStatement(query);
      results = stmt.executeQuery();

      while(results.next())
      {
        String eID = results.getString(1);
        String personID = results.getString(2);
        String username = results.getString(3);
        double latitude = (double) results.getInt(4);
        double longitude = (double) results.getInt(5);
        String country = results.getString(6);
        String city = results.getString(7);
        String eventType = results.getString(8);
        int year = results.getInt(9);
        events.add(new Event(eID, username, personID, latitude, longitude, country, city, eventType, year));
      }
      results.close();
      stmt.close();
      connection.close();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return events;
  }

  /**
  * Method checking Event table for the given eventID
  * @param eventID eventID you want to check for
  * @return false if eventID doesn't exsist and true if it does
  */
  public boolean eventLookup(String eventID)
  {
    return (getEvent(eventID) != null);
  }

  /**
  * Method returning instance of EventAccess
  * @return EventAccess object to use
  */
  public static EventAccess getInstance()
  {
    if (eventDAO == null)
      eventDAO = new EventAccess();
    return eventDAO;
  }
}
