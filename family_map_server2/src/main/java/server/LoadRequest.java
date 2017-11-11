package server;

import model_classes.*;
import java.util.ArrayList;

/** Object used for requesting a load action in the Facade*/
public class LoadRequest
{
  /** Array of Users to be loaded*/
  private User[] users;
  /** Array of Persons to help create Users*/
  private Person[] persons;
  /** Array of Events to help create Users*/
  private Event[] events;

  /**
  * Constructer creating LoadRequest object
  * @param users Array of Users to be loaded
  * @param people Array of Persons to help create Users
  * @param events Array of Events to help create Users
  */
  LoadRequest(User[] users, Person[] persons, Event[] events)
  {
    this.users = users;
    this.persons = persons;
    this.events = events;
  }

  public User[] getUsers(){ return users; }

  public Person[] getPeople(){ return persons; }

  public Event[] getEvents(){ return events; }
}
