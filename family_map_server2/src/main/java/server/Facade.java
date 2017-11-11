package server;

import data_access_classes.AuthTokenAccess;
import data_access_classes.DAO;
import data_access_classes.EventAccess;
import data_access_classes.PersonAccess;
import data_access_classes.UserAccess;
import model_classes.*;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/** Class extending IOException to specify error was a duplicate */
class Duplicate extends Exception{

  @Override
  public String getMessage() {
    return "Duplicate found";
  }
}

/** class extending IO exception to specify error was an invalid request for a user*/
class UserNotFound extends Exception{
  @Override
  public String getMessage() {
    return "User Not Found";
  }

}

class PersonNotFound extends Exception{
  @Override
  public String getMessage() {
    return "Person Not Found";
  }

}

class EventNotFound extends Exception
{
  @Override
  public String getMessage() {
    return "Event Not Found";
  }

}

class InvalidRequest extends Exception
{
  @Override
  public String getMessage() {
    return "Invalid Request";
  }
}

/** Simple interface for user to execute commands on the server*/
public class Facade
{
  private MaleNames mNames = new MaleNames();
  private FemaleNames fNames = new FemaleNames();
  private Surnames sNames = new Surnames();
  private Locations locations = new Locations();
  private static Facade facade;

  /**
  * Creates a new user account, generates 4 generations of ancestors for the new user, logs the user in, and returns an auth token
  * @param register RegisterRequest containing user fields to create a new user and person to add to database
  * @return LoginResponse containing token, username, and personID of registered user
  */
  public LoginResponse register(RegisterRequest register) throws Exception
  {
    System.out.println(register.getFirstName());
    UserAccess userDAO = UserAccess.getInstance();
    PersonAccess personDAO = PersonAccess.getInstance();
    if(userDAO.userExists(register.getUserName()))
    {
      Duplicate e =  new Duplicate();
      throw(e);
    }

    User user = new User(register.getUserName(), register.getPassword(), register.getEmail(),
                        register.getFirstName(), register.getLastName(), register.getGender());
    userDAO.add(user);
    Person person =  new Person(user);
    generateData(user, person, 4, 0, 1994, null);
    LoginRequest loginR = new LoginRequest(user.getUserName(), user.getPassword());
    try
    {
      LoginResponse response = login(loginR);
      return response;
    }
    catch (UserNotFound e)
    {
      e.printStackTrace();
      throw(e);
    }
  }

  /**
  * Logs user in and returns the an authorization token for their session
  * @param login LoginRequest containing username and password of user trying to log in
  * @return LoginResponse containig token, username, and personID of logged in user
  */
  public LoginResponse login(LoginRequest login) throws Exception
  {
    UserAccess userDAO = UserAccess.getInstance();
    AuthTokenAccess authTokenDAO = AuthTokenAccess.getInstance();
    if (!userDAO.userExists(login.getUserName()))
    {
      UserNotFound e =  new UserNotFound();
      throw(e);
    }
    User user = userDAO.getUser(login.getUserName());
    if( !user.getPassword().equals(login.getPassword()))
    {
      InvalidRequest e = new InvalidRequest();
      throw(e);
    }
    AuthToken token = new AuthToken(user.getUserName(), user.getPersonID());
    authTokenDAO.add(token);
    LoginResponse response =  new LoginResponse(token.getToken(), token.getUserName(), token.getPersonID());
    return response;
  }

  /**
  * Deletes all data from database, including user accounts, AuthTokens, and generated person and event data
  */
  public void clear()
  {
    UserAccess userDAO = UserAccess.getInstance();
    EventAccess eventDAO = EventAccess.getInstance();
    AuthTokenAccess authTokenDAO =  AuthTokenAccess.getInstance();
    PersonAccess personDAO = PersonAccess.getInstance();
    userDAO.clear();
    eventDAO.clear();
    authTokenDAO.clear();
    personDAO.clear();
    DAO.CreateDatabase();
  }

  /**
  * Populates sever's database with generated data for specified userName. userName must already correspond to a user
  * in the system. If there is any data related to user, deletes it. optional generation field lets caller specify the
  * number of generations to be created
  * @param userName user's respective userName
  * @param generations non-negative integer
  */
  public void fill(String userName, int generations) throws UserNotFound
  {
    UserAccess userDAO = UserAccess.getInstance();
    PersonAccess personDAO = PersonAccess.getInstance();
    if (!userDAO.userExists(userName))
    {
      UserNotFound e = new UserNotFound();
      throw(e);
    }
    User user = userDAO.getUser(userName);
    Person person = personDAO.getPerson(user.getPersonID());
    EventAccess.getInstance().remove(userName);
    PersonAccess.getInstance().remove(userName);
    //PersonAccess.getInstance().add(person);
    generateData(user, person, generations, 0, 1994, null);
  }

  /**
  * clears data from database and loads in the posted user, person, and event data into the database
  * @param load LoadRequest containing ArrayLists to create data for database
  */
  public void load(LoadRequest load) throws Exception
  {
    UserAccess userDAO = UserAccess.getInstance();
    EventAccess eventDAO = EventAccess.getInstance();
    PersonAccess personDAO = PersonAccess.getInstance();
    clear();
    for (User user : load.getUsers())
    {
      if (!userDAO.userExists(user.getUserName()))
        userDAO.add(user);
    }
    for (Person person : load.getPeople())
    {
      if(!personDAO.personIDLookup(person.getPersonID()))
        personDAO.add(person);
    }
    for (Event event : load.getEvents())
    {
      if (!eventDAO.eventLookup(event.getEventID()))
        eventDAO.add(event);
    }
  }

  /**
  * Returns corresponding person of personID
  * @param personID used to find Person object with same personID
  * @return Person object with corresponding personID
  */
  public Person personByID(String personID) throws PersonNotFound
  {
    PersonAccess personDAO = PersonAccess.getInstance();
    Person person = personDAO.getPerson(personID);
    if(person == null)
    {
      throw(new PersonNotFound());
    }
    return person;
  }

  /**
  * Returns all family members of current user
  * @param token AuthToken used to determine user
  * @return ArrayList of all family members of current user
  */
  public ArrayList<Person> person(AuthToken token)
  {
    ArrayList<Person> response = new ArrayList<>();
    UserAccess userDAO =  UserAccess.getInstance();
    PersonAccess personDAO = PersonAccess.getInstance();
    User user = userDAO.getUser(token.getUserName());
    ArrayList<Person> array =  personDAO.getRows();
    for(Person person : array)
    {
      if(person.getDescendant().equals(user.getUserName()))
        response.add(person);
    }
    return response;
  }

  /**
  * Returns corrosponding event of eventID
  * @param eventID eventID used to locate corresponding Event
  * @return Event object with corresponding eventID
  */
  public Event eventByID(String eventID) throws EventNotFound
  {
    EventAccess eventDAO =  EventAccess.getInstance();
    Event event =  eventDAO.getEvent(eventID);
    if(event == null)
    {
      throw(new EventNotFound());
    }
    return event;
  }

  /**
  * Returns all events of current user's family
  * @param token AuthToken used to determine user
  * @return ArrayList of all events of all family members of current user
  */
  public ArrayList<Event> event(AuthToken token)
  {
    ArrayList<Event> response = new ArrayList<Event>();
    UserAccess userDAO = UserAccess.getInstance();
    EventAccess eventDAO = EventAccess.getInstance();
    User user = userDAO.getUser(token.getUserName());
    ArrayList<Event> array = eventDAO.getRows();
    for(Event event: array)
    {
      if(event.getDescendant().equals(user.getUserName()))
        response.add(event);
    }
    return response;
  }

  /**
  * Method to generate family history data randomly for a specified amount of
  * generations
  * @param user User to whom the generated data belongs to
  * @param generations Integer specifying how many generations need to be created
  */
  public void generateData(User user, Person person, int generations, int currGeneration, int year, Location lMarriage)
  {
    PersonAccess personDAO = PersonAccess.getInstance();
    EventAccess eventDAO = EventAccess.getInstance();
    if (currGeneration == generations)
    {
      personDAO.add(person);
      return;
    }
    //generate events (birth, baptism, death) from THIS person
    Location birthPlace = locations.getLocations().get(ThreadLocalRandom.current().nextInt(0, locations.getLocations().size()));
    Location deathPlace = locations.getLocations().get(ThreadLocalRandom.current().nextInt(0, locations.getLocations().size()));
    Location baptismPlace = locations.getLocations().get(ThreadLocalRandom.current().nextInt(0, locations.getLocations().size()));
    Location marriageLocation = locations.getLocations().get(ThreadLocalRandom.current().nextInt(0, locations.getLocations().size()));

    Event birth = new Event(user.getUserName(), person.getPersonID(), Double.valueOf(birthPlace.getLatitude()), Double.valueOf(birthPlace.getLongitude()),
                            birthPlace.getCountry(), birthPlace.getCity(), "birth", year);
    if (year + 80 <= 2017)
    {
      Event death = new Event(user.getUserName(), person.getPersonID(), Double.valueOf(deathPlace.getLatitude()), Double.valueOf(deathPlace.getLongitude()),
              deathPlace.getCountry(), deathPlace.getCity(), "death", year + 80);
      eventDAO.add(death);
    }
    Event baptism = new Event(user.getUserName(), person.getPersonID(), Double.valueOf(baptismPlace.getLatitude()), Double.valueOf(baptismPlace.getLongitude()),
                            baptismPlace.getCountry(), baptismPlace.getCity(), "baptism", year + 8);
    if (currGeneration != 0)
    {
      Event marriage = new Event(user.getUserName(), person.getPersonID(), Double.valueOf(lMarriage.getLatitude()), Double.valueOf(lMarriage.getLongitude()),
              lMarriage.getCountry(), lMarriage.getCity(), "marriage", year + 19);
      eventDAO.add(marriage);
    }
    //add events to Database
    eventDAO.add(birth);
    eventDAO.add(baptism);

    //generate father and mother Person objects and connect them as person's parents and associate with User.

    String firstNameFather = mNames.getMaleNames().get(ThreadLocalRandom.current().nextInt(0, mNames.getMaleNames().size()));
    String lastName = sNames.getSurNames().get(ThreadLocalRandom.current().nextInt(0, sNames.getSurNames().size()));
    String firstNameMother = fNames.getFemaleNames().get(ThreadLocalRandom.current().nextInt(0, fNames.getFemaleNames().size()));
    Person father;
    Person mother;
    if(person.getGender().equals("f"))
    {
      father = new Person(firstNameFather, lastName, "m", user.getUserName(), null, null, null);
      mother = new Person(firstNameMother, lastName, "f", user.getUserName(), null, null, null);
    }
    else
    {
      father = new Person(firstNameFather, person.getLastName(), "m", user.getUserName(), null, null, null);
      mother = new Person(firstNameMother, person.getLastName(), "f", user.getUserName(), null, null, null);
    }
    father.setSpouce(mother.getPersonID());
    mother.setSpouce(father.getPersonID() );
    //personDAO.add(father);
    //personDAO.add(mother);

    person.setFather(father.getPersonID());
    person.setMother(mother.getPersonID());
    personDAO.add(person);

    generateData(user, mother, generations, ++currGeneration, year - 20, marriageLocation);
    currGeneration--;
    generateData(user, father, generations, ++currGeneration, year - 20, marriageLocation);
  }

  public static Facade getInstance()
  {
    if (facade == null)
      return new Facade();
    return facade;
  }
}
