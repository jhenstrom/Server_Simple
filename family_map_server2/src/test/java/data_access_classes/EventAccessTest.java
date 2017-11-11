package data_access_classes;

import java.util.ArrayList;

import model_classes.Event;

import static org.junit.Assert.*;

/**
 * Created by jhenstrom on 11/1/17.
 */
public class EventAccessTest {
    Event event = new Event("eventID","username", "personID", 54.6, 93.4, "country", "city", "type", 1994);
    @org.junit.Test
    public void getEvent() throws Exception {

        DAO.CreateDatabase();
        Event currEvent = EventAccess.getInstance().getEvent("eventID");
        assert currEvent == null;
        EventAccess.getInstance().add(event);
        currEvent = EventAccess.getInstance().getEvent("eventID");
        assert currEvent != null;
        AuthTokenAccess.getInstance().clear();
    }

    @org.junit.Test
    public void clear() throws Exception {
        DAO.CreateDatabase();
        try {

            EventAccess.getInstance().clear();
        }
        catch (Exception e)
        {
            assert false;
        }
    }

    @org.junit.Test
    public void add() throws Exception {

        DAO.CreateDatabase();
        EventAccess.getInstance().add(event);
        Event currEvent = EventAccess.getInstance().getEvent(event.getEventID());
        assert currEvent.getEventID().equals(event.getEventID());
        EventAccess.getInstance().clear();
    }

    @org.junit.Test
    public void remove() throws Exception {
        DAO.CreateDatabase();
        EventAccess.getInstance().add(event);
        EventAccess.getInstance().remove(event.getDescendant());
        Event currEvent = EventAccess.getInstance().getEvent(event.getEventID());
        assert currEvent == null;
        EventAccess.getInstance().clear();


    }

    @org.junit.Test
    public void getRows() throws Exception {

        DAO.CreateDatabase();
        ArrayList<Event> list;
        list = EventAccess.getInstance().getRows();
        assert list.size() == 0;
        EventAccess.getInstance().add(event);
        list = EventAccess.getInstance().getRows();
        assert list.size() == 1;
        EventAccess.getInstance().clear();

    }

}