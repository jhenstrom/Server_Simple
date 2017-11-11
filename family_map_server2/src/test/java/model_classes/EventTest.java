package model_classes;

import static org.junit.Assert.*;

/**
 * Created by jhenstrom on 11/1/17.
 */
public class EventTest {

    Event event = new Event("eventID","username", "personID", 54.6, 93.4, "country", "city", "type", 1994);
    @org.junit.Test
    public void getEventID() throws Exception {
        assert event.getEventID().equals("eventID");
    }

    @org.junit.Test
    public void getDescendant() throws Exception {
        assert event.getDescendant().equals("username");
    }

    @org.junit.Test
    public void getPersonID() throws Exception {
        assert event.getPersonID().equals("personID");
    }

    @org.junit.Test
    public void toStringTest() throws Exception {

        String expected = "Event ID: eventID\nEvent: type\nDecendent: username\n" +
                "Person ID: personID\nLocation 54.6 93.4\ncity, country\nYear: 1994";
        assert expected.equals(event.toString());
    }

}