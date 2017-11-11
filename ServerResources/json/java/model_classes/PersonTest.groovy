package model_classes

/**
 * Created by jhenstrom on 10/27/17.
 */
class PersonTest {
    void testToString()
    {
        Person personOne = new Person("firstname","lastname","f", new User("username1","pass1","email","firstname","lastname","f"), "fads", "sfd", "dsaf");
        String actual = personOne.toString();
        String expected = "PersonID: " + personOne.getPersonID() + "\nName: firstname lastname\nGender: f\nDecendent: " + personOne.getDecendent().getUserName() + "\n " +
                "Father: fads\nMother: sfd\nSpouce: dsaf\n";
        assert actual.equals(expected);
    }
}
