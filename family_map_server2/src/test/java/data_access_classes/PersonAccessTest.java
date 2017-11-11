package data_access_classes;

import java.util.ArrayList;

import model_classes.Person;

import static org.junit.Assert.*;

/**
 * Created by jhenstrom on 11/1/17.
 */
public class PersonAccessTest {
    Person person = new Person("personID", "firstname", "lastname", "f", "username", null, null, null);
    @org.junit.Test
    public void getPerson() throws Exception {
        DAO.CreateDatabase();
        Person currPerson =  PersonAccess.getInstance().getPerson("personID");
        assert currPerson == null;
        PersonAccess.getInstance().add(person);
        currPerson = PersonAccess.getInstance().getPerson("personID");
        assert currPerson != null;
        PersonAccess.getInstance().clear();

    }

    @org.junit.Test
    public void clear() throws Exception {
        DAO.CreateDatabase();
        try {

            PersonAccess.getInstance().clear();
        }
        catch (Exception e)
        {
            assert false;
        }
    }

    @org.junit.Test
    public void add() throws Exception {

        DAO.CreateDatabase();
        PersonAccess.getInstance().add(person);
        Person currPerson = PersonAccess.getInstance().getPerson(person.getPersonID());
        assert currPerson.getPersonID().equals(person.getPersonID());
        PersonAccess.getInstance().clear();
    }

    @org.junit.Test
    public void remove() throws Exception {

        DAO.CreateDatabase();
        PersonAccess.getInstance().add(person);
        PersonAccess.getInstance().remove(person.getDescendant());
        Person currPerson = PersonAccess.getInstance().getPerson(person.getPersonID());
        assert currPerson == null;
        PersonAccess.getInstance().clear();
    }

    @org.junit.Test
    public void getRows() throws Exception {
        DAO.CreateDatabase();
        ArrayList<Person> list;
        list = PersonAccess.getInstance().getRows();
        assert list.size() == 0;
        PersonAccess.getInstance().add(person);
        list = PersonAccess.getInstance().getRows();
        assert list.size() == 1;
        PersonAccess.getInstance().clear();

    }
}