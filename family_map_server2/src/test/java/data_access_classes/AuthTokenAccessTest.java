package data_access_classes;

import java.util.ArrayList;

import model_classes.AuthToken;

import static org.junit.Assert.*;

/**
 * Created by jhenstrom on 11/1/17.
 */
public class AuthTokenAccessTest {
    AuthToken token = new AuthToken("token", "username", "personID");
    @org.junit.Test
    public void getAuthToken(){
        DAO.CreateDatabase();
        AuthToken currToken = AuthTokenAccess.getInstance().getAuthToken("token");
        assert currToken == null;
        AuthTokenAccess.getInstance().add(token);
        currToken = AuthTokenAccess.getInstance().getAuthToken("token");
        assert currToken != null;
        AuthTokenAccess.getInstance().clear();
    }

    @org.junit.Test
    public void clear() {
        DAO.CreateDatabase();
        try {

            AuthTokenAccess.getInstance().clear();
        }
        catch (Exception e)
        {
            assert false;
        }
    }

    @org.junit.Test
    public void add(){
        DAO.CreateDatabase();
        AuthTokenAccess.getInstance().add(token);
        AuthToken currToken = AuthTokenAccess.getInstance().getAuthToken(token.getToken());
        assert currToken.getToken().equals(token.getToken());
        AuthTokenAccess.getInstance().clear();
    }

    @org.junit.Test
    public void getRows(){
        DAO.CreateDatabase();
        ArrayList<AuthToken> list;
        list = AuthTokenAccess.getInstance().getRows();
        assert list.size() == 0;
        AuthTokenAccess.getInstance().add(token);
        list = AuthTokenAccess.getInstance().getRows();
        assert  list.size() == 1;
        AuthTokenAccess.getInstance().clear();

    }

}