package model_classes

/**
 * Created by jhenstrom on 10/27/17.
 */
class AuthTokenTest {
    void testToString()
    {
        AuthToken authTokenOne = new AuthToken( "token", "username", "personID");
        String actual = eventOne.toString();
        String expected = "Authorization Token: token\nUser Name: username\nPerson ID: personID\n";
        assert actual.equals(expected);
    }
}
