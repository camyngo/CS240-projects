package daoTests;

import dao.DataBaseManager;
import dao.PersonDao;
import dao.UserDao;
import models.Persons;
import models.Users;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


import java.sql.SQLException;

import static org.junit.Assert.*;


public class UserDaoTest {
    private DataBaseManager db;
    UserDao userDao = new UserDao();
    Users testUser = new Users();
    @BeforeEach
    public void setUp() throws Exception {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new DataBaseManager();
        db.initializeDatabase();
        //and make sure to initialize our tables since we don't know if our database files exist yet
        userDao = new UserDao();
        testUser = new Users("yes", "no", "false", "john", "doe","m","1234");
        userDao.initializeDatabase();
        userDao.clearTables();
        db.closeDatabase();
    }

    @AfterEach
    public void tearDown()
    {
        try {
            UserDao userDao = new UserDao();
            userDao.clearTables();
            db.clearTables();
            db.closeDatabase();
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void registerNewUser() //Registering a simple new user
    {
        try {
            userDao.insertUser(testUser);
            Users initUser = userDao.findUser("yes");
            Assert.assertEquals(initUser, testUser);
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }

    }

    @Test
    public void findUser() //Finding a simple user using userID
    {
        try {
            Assert.assertNull(userDao.findUser("yes"));
            userDao.insertUser(testUser);
            Users initUser = userDao.findUser("yes");
            Assert.assertEquals(initUser, testUser);
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test(expected = SQLException.class)
    public void registerNewUserFail() throws SQLException //Registering the same user twice
    {
        try {
            userDao.insertUser(testUser);
            userDao.insertUser(testUser);
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
            throw databaseError;
        }
    }


    @Test
    public void findUserFail() //Finding a user that does not exist
    {
        try {
            userDao.insertUser(testUser);
            Assert.assertNull(userDao.findUser("notthere"));
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void clearUsers() //Clearing the user information
    {
        try {
            Assert.assertNull(userDao.findUser("yes"));
            userDao.insertUser(testUser);
            Users initUser = userDao.findUser("yes");
            Assert.assertEquals(initUser, testUser);
            Assert.assertTrue(userDao.clearUsers());
            Assert.assertNull(userDao.findUser("yes"));
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }
    @Test
    public void clearUsersFail() //Clearing the user information
    {
        clearUsers();
        if(userDao!=null || testUser!= null){
            System.out.println("Clear fail");
        }
        else
            System.out.println("Clear success");
    }
}