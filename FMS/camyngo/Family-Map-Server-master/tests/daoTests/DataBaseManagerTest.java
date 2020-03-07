package daoTests;

import dao.DataBaseManager;
import dao.UserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import models.Users;

import java.sql.SQLException;

public class DataBaseManagerTest {

    @Before
    public void setUp() {
        UserDao userDao = new UserDao();
        Users testUser = new Users("yes", "no", "false", "john", "doe", "m", "1234");
        try {
            userDao.initializeDatabase();
            userDao.insertUser(testUser);
        } catch (SQLException databaseError) {
            databaseError.printStackTrace();
        }
    }

    @Test
    public void clearTables() //Classic clearing tables test
    {
        UserDao userDao = new UserDao();
        DataBaseManager dBManager = new DataBaseManager();
        try {
            Assert.assertNotNull(userDao.findUser("yes"));
            Assert.assertTrue(dBManager.clearTables());
            Assert.assertNull(userDao.findUser("yes"));
        } catch (SQLException databaseError) {
            databaseError.printStackTrace();
        }
    }

    @Test
    //Classic clearing tables fail test
    public void clearTablesFail()
    {
        UserDao userDao = new UserDao();
        DataBaseManager dBManager = new DataBaseManager();
        clearTables();
        if( userDao!= null || dBManager!=null ){
            System.out.println("Clear failed");
        }
    }
}