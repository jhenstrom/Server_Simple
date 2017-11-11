package model_classes

/**
 * Created by jhenstrom on 10/26/17.
 */
class UserTest {
    void testToString()
    {
        User userOne = new User("username1","pass1","email","firstname","lastname","f")
        String actual = userOne.toString();
        String expected = "UserName: username1\nPassword: pass1\nEmail: email\nName: firstname lastname\nGender: f\nPersonID: " + userOne.getPersonID() + "\n";
        assert actual.equals(expected);
    }

    void testEquals()
    {

        User userOne = new User("user1", "asfd", "asdf", "asdf", "asdf", "asdf");
        User userTwo = new User("user2", "asfd", "asdf", "asdf", "asdf", "asdf");
        User userThree = new User("user2", "asfd", "asdf", "asdf", "asdf", "asdf", userTwo.getPersonID());

        assert !userOne.equals(userTwo);
        assert !userOne.equals(userThree);
        assert userTwo.equals(userThree);
    }
}
