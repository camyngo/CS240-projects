package daoTests;

import dao.PersonDao;
import dao.UserDao;
import org.junit.*;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import models.Persons;
import dao.DataBaseManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class PersonDaoTest {
    private DataBaseManager db;
    PersonDao personDao = new PersonDao();
    Persons testPerson = new Persons();

    @BeforeEach
    public void setUp() throws Exception {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new DataBaseManager();
        db.initializeDatabase();
        //and make sure to initialize our tables since we don't know if our database files exist yet
        personDao = new PersonDao();
        testPerson = new Persons("yes", "no", "false", "john", "doe","m","1234", null);
        personDao.initializeDatabase();
        personDao.clearTables();
        db.closeDatabase();
    }

    @AfterEach
    public void tearDown()
    {
        try {
            PersonDao personDao = new PersonDao();
            personDao.clearTables();
            db.clearTables();
            db.closeDatabase();
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void insertPerson() //Inserting a basic person object
    {
        try {
            Assert.assertTrue(personDao.insertPerson(testPerson));
            Persons initPer = personDao.findSinglePerson("yes");
            Assert.assertEquals(initPer, testPerson);
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test(expected = SQLException.class)
    public void insertPersonFail() throws SQLException //Inserting a person object that already exists
    {
        try {
            personDao.insertPerson(testPerson);
            personDao.insertPerson(testPerson);
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
            throw databaseError;
        }
    }

    @Test
    public void findSinglePerson() //Finding a basic person with a simple userID
    {
        try {
            Assert.assertTrue(personDao.insertPerson(testPerson));
            Persons initPer = personDao.findSinglePerson("yes");
            Assert.assertEquals(initPer, testPerson);
            personDao.clearTables();
            Assert.assertNull(personDao.findSinglePerson("yes"));
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void findSinglePersonFail() //Finding a person that doesn't have a userID
    {
        try {
            personDao.initializeDatabase();
            personDao.clearTables();
            Assert.assertNull(personDao.findSinglePerson("yes"));
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void findAllPersons() //Finding all people under a userID
    {
        Persons personOne = new Persons("yes", "no", "false", "john", "doe","m","1234", null);
        Persons personTwo = new Persons("no", "no","Jack","Frost","f",null,null,null);
        Persons personThree = new Persons("123", "no","jenny","F.","x",null,"yup",null);
        Persons personFour = new Persons("1010", "false","jenny","F.","x",null,"yup",null);

        ArrayList<Persons> currArray = new ArrayList<Persons>();
        currArray.add(personOne);
        currArray.add(personTwo);
        currArray.add(personThree);
        try {
            Assert.assertTrue(personDao.insertPerson(personOne));
            Assert.assertTrue(personDao.insertPerson(personTwo));
            Assert.assertTrue(personDao.insertPerson(personThree));
            Assert.assertTrue(personDao.insertPerson(personFour));

            ArrayList<Persons> newPersonArrayFromDao = personDao.findAllPersons("no");
            for (int i = 0; i < currArray.size(); i++){
                Assert.assertEquals(newPersonArrayFromDao.get(i), currArray.get(i));
            }
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void findAllPersonsFail() //Checking to see if it pulled other People not under the same userID
    {
        //initialize the data
        Persons personOne = new Persons("yes", "no", "false", "john", "doe","m","1234", null);
        Persons personTwo = new Persons("no", "no","Jack","Frost","f",null,null,null);
        Persons personThree = new Persons("123", "no","jenny","F.","x",null,"yup",null);
        Persons personFour = new Persons("1010", "false","jenny","F.","x",null,"yup",null);

        ArrayList<Persons> currArray = new ArrayList<Persons>();
        currArray.add(personOne);
        currArray.add(personTwo);
        currArray.add(personFour);
        try {
            Assert.assertTrue(personDao.insertPerson(personOne));
            Assert.assertTrue(personDao.insertPerson(personTwo));
            Assert.assertTrue(personDao.insertPerson(personThree));
            Assert.assertTrue(personDao.insertPerson(personFour));

            ArrayList<Persons> newPersonArrayFromDao = personDao.findAllPersons("no");
            for (int i = 0; i < currArray.size(); i++){
                if (currArray.get(i) == personFour){
                    Assert.assertNotEquals(newPersonArrayFromDao.get(i), currArray.get(i));
                }
                else {
                    Assert.assertEquals(newPersonArrayFromDao.get(i), currArray.get(i));
                }
            }
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }

    @Test
    public void clearPerson() //Test on clearing the person table
    {
        try {
            Assert.assertTrue(personDao.insertPerson(testPerson));
            Persons initP = personDao.findSinglePerson("yes");
            Assert.assertEquals(initP, testPerson);
            personDao.deletePersons();
            Assert.assertNull(personDao.findSinglePerson("yes"));
        }
        catch (SQLException databaseError){
            databaseError.printStackTrace();
        }
    }
    @Test
    public void clearPersonFail() //Test on clearing the person table
    {
        clearPerson();
        if(personDao!= null || testPerson!= null){
            System.out.println("Clear fail");
        }
        else
            System.out.println("Clear success");
    }
}