package model_classes;

import static org.junit.Assert.*;

/**
 * Created by jhenstrom on 11/1/17.
 */
public class AuthTokenTest {
    AuthToken token = new AuthToken("token", "username", "personID");
    @org.junit.Test
    public void getToken() throws Exception {
        assert token.getToken().equals("token");
    }

    @org.junit.Test
    public void toStringTest() throws Exception {

        String expected = "Authorization Token: token\nUser Name: username\nPerson ID: personID\n";
        assert expected.equals(token.toString());
    }

}