package data_access_classes;

import java.util.ArrayList;

import model_classes.User;

import static org.junit.Assert.*;

/**
 * Created by jhenstrom on 11/1/17.
 */
public class UserAccessTest {
    User user = new User("username", "password", "email", "firstname", "lastname", "f", "personID");
    @org.junit.Test
    public void getUser() throws Exception {

        DAO.CreateDatabase();
        User currUser = UserAccess.getInstance().getUser("username");
        assert currUser == null;
        UserAccess.getInstance().add(user);
        currUser = UserAccess.getInstance().getUser("username");
        assert currUser != null;
        UserAccess.getInstance().clear();
    }

    @org.junit.Test
    public void clear() throws Exception {
        DAO.CreateDatabase();
        try {

            UserAccess.getInstance().clear();
        }
        catch (Exception e)
        {
            assert false;
        }
    }

    @org.junit.Test
    public void add() throws Exception {

        DAO.CreateDatabase();
        UserAccess.getInstance().add(user);
        User currUser = UserAccess.getInstance().getUser(user.getUserName());
        assert currUser.getUserName().equals(user.getUserName());
        UserAccess.getInstance().clear();
    }

    @org.junit.Test
    public void getRows() throws Exception {

        DAO.CreateDatabase();
        ArrayList<User> list;
        list = UserAccess.getInstance().getRows();
        assert list.size() == 0;
        UserAccess.getInstance().add(user);
        list = UserAccess.getInstance().getRows();
        assert list.size() == 1;
        UserAccess.getInstance().clear();
    }
}