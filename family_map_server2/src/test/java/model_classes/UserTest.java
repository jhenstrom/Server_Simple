package model_classes;

import static org.junit.Assert.*;

/**
 * Created by jhenstrom on 11/1/17.
 */
public class UserTest {
    User userOne = new User("username", "password", "email", "firstname", "lastname", "f", "personID");
    User userTwo = new User("username2", "password", "email", "firstname", "lastname", "f", "personID");
    User userThree = new User("username2", "password", "email", "firstname", "lastname", "f", "personID");
    @org.junit.Test
    public void getUserName() throws Exception {

    }

    @org.junit.Test
    public void getPersonID() throws Exception {

    }

    @org.junit.Test
    public void toStringTest() throws Exception {

        String expected = "UserName: username\nPassword: password\n" +
                "Email: email\nName: firstname lastname\bGender: f\nPerson ID: personID";
        assert expected.equals(userOne.toString());
    }

    @org.junit.Test
    public void equals() throws Exception {
        assert !userOne.equals(userTwo);
        assert userTwo.equals(userThree);

    }

}