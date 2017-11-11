package server;

import java.util.ArrayList;

import data_access_classes.DAO;
import data_access_classes.EventAccess;
import data_access_classes.PersonAccess;
import data_access_classes.UserAccess;
import model_classes.AuthToken;
import model_classes.Event;
import model_classes.Person;
import model_classes.User;

import static org.junit.Assert.*;

/**
 * Created by jhenstrom on 11/1/17.
 */
public class FacadeTest {
    @org.junit.Test
    public void register() throws Exception {
        DAO.CreateDatabase();
        RegisterRequest request =  new RegisterRequest("username", "password", "email", "firstname","lastname","f");
        LoginResponse response = null;
        try {
            response = Facade.getInstance().register(request);

        } catch(Exception e)
        {
            assert false;
        }
        int peopleCount = PersonAccess.getInstance().getRows().size();
        assert peopleCount == 31;
        assert response.getUserName() != null;
        assert response.getToken() != null;
        assert response.getPersonID() != null;
        Facade.getInstance().clear();
    }

    @org.junit.Test
    public void login() throws Exception {

        DAO.CreateDatabase();
        RegisterRequest regRequest =  new RegisterRequest("username", "password", "email", "firstname","lastname","f");
        Facade.getInstance().register(regRequest);
        LoginRequest invRequest = new LoginRequest("invalid","invalid");
        LoginRequest request = new LoginRequest("username","password");

        try{
            LoginResponse r = Facade.getInstance().login(invRequest);
            assert false;
        }
        catch (Exception e)
        {
            assert true;
        }
        LoginResponse response = Facade.getInstance().login(request);
        assert response.getUserName() != null;
        assert response.getToken() != null;
        assert response.getPersonID() != null;
        Facade.getInstance().clear();

    }

    @org.junit.Test
    public void clear() throws Exception {

        try{
            Facade.getInstance().clear();

        }catch (Exception e)
        {
            assert false;
        }

    }

    @org.junit.Test
    public void fill() throws Exception {

        DAO.CreateDatabase();
        RegisterRequest regRequest =  new RegisterRequest("username", "password", "email", "firstname","lastname","f");
        Facade.getInstance().register(regRequest);
        Facade.getInstance().fill("username", 4);
        int peopleCount = PersonAccess.getInstance().getRows().size();
        int eventCount = EventAccess.getInstance().getRows().size();
        assert peopleCount == 31;
        assert eventCount == 52;
        Facade.getInstance().fill("username", 2);
        peopleCount = PersonAccess.getInstance().getRows().size();
        eventCount = EventAccess.getInstance().getRows().size();
        assert peopleCount == 7;
        assert eventCount == 8;
        Facade.getInstance().clear();

    }

    @org.junit.Test
    public void load() throws Exception {

        DAO.CreateDatabase();
        User user1 = new User("username", "password", "email", "firstname", "lastname", "f", "personID");
        User user2 = new User("username1", "password", "email", "firstname", "lastname", "f", "personID1");
        User user3 = new User("username2", "password", "email", "firstname", "lastname", "f", "personID2");
        Person person1 = new Person(user1);
        Person person2 = new Person(user2);
        Person person3 = new Person(user3);
        Event event1 = new Event("eventID","username", "personID", 54.6, 93.4, "country", "city", "type", 1994);
        Event event2 = new Event("eventID","username1", "personID", 54.6, 93.4, "country", "city", "type", 1994);

        User[] users = {user1, user2, user3};
        Person[] persons = {person1, person2, person3};
        Event[] events = {event1, event2};
        LoadRequest loadRequest = new LoadRequest(users, persons, events);
        Facade.getInstance().load(loadRequest);
        int peopleCount = PersonAccess.getInstance().getRows().size();
        int eventCount = EventAccess.getInstance().getRows().size();
        int userCount = UserAccess.getInstance().getRows().size();
        assert peopleCount == 3;
        assert eventCount == 2;
        assert userCount == 3;
        Facade.getInstance().clear();
    }

    @org.junit.Test
    public void personByID() throws Exception {

        DAO.CreateDatabase();
        RegisterRequest regRequest =  new RegisterRequest("username", "password", "email", "firstname","lastname","f");
        LoginResponse token = Facade.getInstance().register(regRequest);
        Person person = Facade.getInstance().personByID(token.getPersonID());
        assert person.getPersonID().equals(token.getPersonID()) && person.getDescendant().equals(token.getUserName());
        Facade.getInstance().clear();


    }

    @org.junit.Test
    public void person() throws Exception {

        DAO.CreateDatabase();
        RegisterRequest regRequest =  new RegisterRequest("username", "password", "email", "firstname","lastname","f");
        LoginResponse response = Facade.getInstance().register(regRequest);
        AuthToken token = new AuthToken(response.getToken(), response.getUserName(), response.getPersonID());
        ArrayList<Person> list = Facade.getInstance().person(token);
        assert list.size() == 52;
        Facade.getInstance().clear();

    }

    @org.junit.Test
    public void eventByID() throws Exception {

        DAO.CreateDatabase();
        RegisterRequest regRequest =  new RegisterRequest("username", "password", "email", "firstname","lastname","f");
        LoginResponse response = Facade.getInstance().register(regRequest);
        AuthToken token = new AuthToken(response.getToken(), response.getUserName(), response.getPersonID());
        ArrayList<Event> list = Facade.getInstance().event(token);
        Event event = Facade.getInstance().eventByID(list.get(5).getEventID());
        assert event.getPersonID().equals(token.getPersonID()) && event.getDescendant().equals(token.getUserName());
        Facade.getInstance().clear();
    }

    @org.junit.Test
    public void event() throws Exception {
        DAO.CreateDatabase();
        RegisterRequest regRequest =  new RegisterRequest("username", "password", "email", "firstname","lastname","f");
        LoginResponse response = Facade.getInstance().register(regRequest);
        AuthToken token = new AuthToken(response.getToken(), response.getUserName(), response.getPersonID());
        ArrayList<Event> list = Facade.getInstance().event(token);
        assert list.size() == 52;
        Facade.getInstance().clear();
    }

}