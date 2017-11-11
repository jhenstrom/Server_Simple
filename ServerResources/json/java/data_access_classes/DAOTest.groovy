package data_access_classes


/**
 * Created by jhenstrom on 10/27/17.
 */
class DAOTest  {
    void testCreateDatabase()
    {
        DAO.CreateDatabase();
        assert con.getMetaData().getTables(null, null, "Person", null).next();
        assert con.getMetaData().getTables(null, null, "User", null).next();
        assert con.getMetaData().getTables(null, null, "Event", null).next();
        assert con.getMetaData().getTables(null, null, "AuthToken", null).next();
    }
}
