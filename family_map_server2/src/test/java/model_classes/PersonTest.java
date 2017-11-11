package model_classes;

import static org.junit.Assert.*;

/**
 * Created by jhenstrom on 11/1/17.
 */
public class PersonTest {

    Person person = new Person("personID", "firstname", "lastname", "f", "username", null, null, null);
    @org.junit.Test
    public void getPersonID() throws Exception {
        assert person.getPersonID().equals("personID");
    }

    @org.junit.Test
    public void getDescendant() throws Exception {
        assert person.getDescendant().equals("username");
    }

    @org.junit.Test
    public void toStringTest() throws Exception {

        String expected = "Person ID: personID\nName: firstname lastname\nGender: f\nDecendent: username\n";
        assert expected.equals(person.toString());
    }

}