package model_classes

/**
 * Created by jhenstrom on 10/27/17.
 */
class EventTest {
    void testToString()
    {
        Event eventOne = new Event("id", new User("username1","pass1","email","firstname","lastname","f"), "pId", -36.77, 25.2, "country", "city","birth", 1994);
        String actual = eventOne.toString();
        String expected = "eventID: id\nEvent Type: birth\nDescendant: " + eventOne.getDecendent().getUserName() + "\nPerson ID: pId\nLocation:\n-36.77 25.2\ncity, country\nYear: 1994\n";
        assert actual.equals(expected);
    }
}
