package model_classes;

import java.util.UUID;

/** Stores information about an event*/
public class Event
{
  /** Unique ID of this object*/
  private String eventID;
  /** User object event belongs to*/
  private String descendant;
  /** personID of the person who's event this is*/
  private String personID;
  /** latitude of event's location*/
  private double latitude;
  /** longitude of event's location*/
  private double longitude;
  /** country where event took place*/
  private String country;
  /** city where event took place*/
  private String city;
  /** the type of the event*/
  private String eventType;
  /** year the event took place*/
  private int year;

  /**
  * Contructor to create Event object
  * @param descendant User object event belongs to
  * @param personID personID of the person who's event this is
  * @param latitude latitude of event's location
  * @param longitude longitude of event's location
  * @param country country where event took place
  * @param city city where event took place
  * @param eventType the type of the event
  * @param year year the event took place
  */
  public Event(String descendant, String personID, double latitude, double longitude, String country, String city, String eventType, int year)
  {
    this.descendant = descendant;
    this.personID = personID;
    this.latitude = latitude;
    this.longitude = longitude;
    this.country = country;
    this.city = city;
    this.eventType = eventType;
    this.year = year;
    this.eventID = generateEventID();

  }

  /**
   * Contructor to create Event object
   * @param eventID event ID used for construction
   * @param descendant User object event belongs to
   * @param personID personID of the person who's event this is
   * @param latitude latitude of event's location
   * @param longitude longitude of event's location
   * @param country country where event took place
   * @param city city where event took place
   * @param eventType the type of the event
   * @param year year the event took place
   */
  public Event(String eventID, String descendant, String personID, double latitude, double longitude, String country, String city, String eventType, int year)
  {
    this.eventID = eventID;
    this.descendant = descendant;
    this.personID = personID;
    this.latitude = latitude;
    this.longitude = longitude;
    this.country = country;
    this.city = city;
    this.eventType = eventType;
    this.year = year;
  }

  public String getEventID(){ return eventID; }

  public String getDescendant(){ return descendant; }

  public String getPersonID(){ return personID; }

  public String getCountry(){ return country; }

  public String getCity(){ return city; }

  public String getEventType(){ return eventType; }

  public double getLongitude(){ return longitude; }

  public double getLatitude(){return latitude; }

  public int getYear(){ return year; }

  /**
  * Method to create string obj of Event
  * @return String of Event
  */
  @Override
  public String toString()
  {
    StringBuilder s = new StringBuilder();
    s.append("Event ID: " + eventID + "\n");
    s.append("Event: " + eventType + "\n");
    s.append("Decendent: " + descendant + "\n");
    s.append("Person ID: " + personID + "\n");
    s.append("Location:\n" + Double.toString(latitude) + " " + Double.toString(longitude) + "\n");
    s.append(city + ", " + country + "\n");
    s.append("Year: " + year + "\n");
    return s.toString();
  }

  /**
   * generates a unique 8 digit eventID
   * @return 8 char long string that is unique
   */
  private String generateEventID()
  {
    return UUID.randomUUID().toString();
  }
}
