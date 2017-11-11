package data_access_classes;

import static org.junit.Assert.*;

/**
 * Created by jhenstrom on 11/1/17.
 */
public class DAOTest {

    @org.junit.Test
    public void CreateDatabase() throws Exception {

        try {

            DAO.CreateDatabase();

        } catch (Exception e)
        {
            assert false;
        }

    }

}