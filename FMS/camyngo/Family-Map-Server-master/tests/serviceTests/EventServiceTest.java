package serviceTests;

import dao.AuthTokenDao;
import dao.EventDao;
import models.AuthorizationToken;
import models.Events;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import results.AllEventResults;
import results.SingleEventResult;
import services.ClearService;
import services.EventService;

import java.sql.SQLException;
import java.util.ArrayList;

public class EventServiceTest {

    @Before
    public void setUp() throws SQLException
    {
        ClearService clearService = new ClearService();
        clearService.clearDb();
        AuthTokenDao authTokenDao = new AuthTokenDao();
        authTokenDao.insertToken(new AuthorizationToken("1234", "no"));
        authTokenDao.insertToken(new AuthorizationToken("10", "nonexistant"));
    }

    @Test
    public void singleEvents() throws SQLException //Finding a single event
    {
        EventDao eventDao = new EventDao();
        Events testEvent = new Events("yes", "no", "false", 1000, 4000,"m","1234", "death", 1969);
        eventDao.insertEvent(testEvent);

        EventService eventService = new EventService();
        SingleEventResult testSingleResult = eventService.singleEvent("yes", "1234");

        Assert.assertNotNull(testSingleResult.getDescendant());
        Assert.assertNotNull(testSingleResult.getCity());
        Assert.assertNotNull(testSingleResult.getCountry());
        Assert.assertNotNull(testSingleResult.getEventType());
        Assert.assertNotNull(testSingleResult.getEventID());
        Assert.assertEquals(1969, testSingleResult.getYear());
        Assert.assertEquals(1000, testSingleResult.getLatitude(), .5);
        Assert.assertEquals(4000, testSingleResult.getLongitude(), .5);
        Assert.assertNull(testSingleResult.getErrorMessage());
        Assert.assertEquals(testSingleResult.getDescendant(), "no");

    }

    @Test
    public void singleEventFail() throws SQLException //Finding an event that doesn't exist, faulty eventID
    {

        EventDao eventDao = new EventDao();
        Events testEvent = new Events("yes", "no", "false", 1000, 4000,"m","1234", "death", 1969);
        eventDao.insertEvent(testEvent);
        EventService eventService = new EventService();
        SingleEventResult singleEventResult = eventService.singleEvent("yes", "1234");
        //clear after insert to make sure all data is null -> test fail
        eventDao.clearTables();
        Assert.assertNotNull(singleEventResult.getDescendant());
        Assert.assertNotNull(singleEventResult.getCity());
        Assert.assertNotNull(singleEventResult.getCountry());
        Assert.assertNotNull(singleEventResult.getEventType());
        Assert.assertNotNull(singleEventResult.getEventID());
        Assert.assertNotNull(singleEventResult.getYear());
        Assert.assertNotNull(singleEventResult.getLatitude());
        Assert.assertNotNull(singleEventResult.getLongitude());
    }

    @Test
    public void allEvents() throws SQLException //Finding all events and checking to see if they are right
    {
        EventDao eventDao = new EventDao();
        Events eventOne = new Events("yes", "no", "false", 1000, 4000,"m","tokyo", "death", 1969);
        Events eventTwo = new Events("no", "no", "can", 999, 3333,"stuff","yessir", "more death", 1900);
        Events eventThree = new Events("nope", "no", "yup", 494, 1029304,"not America","not New York", "birth", 1870);
        Events eventFour = new Events("1010", "yes", "whocares", 4293, 4059309,"Iraq","1234", "death", 1400);

        eventDao.insertEvent(eventOne);
        eventDao.insertEvent(eventTwo);
        eventDao.insertEvent(eventThree);
        eventDao.insertEvent(eventFour);

        EventService eventService = new EventService();
        AllEventResults eventResults = eventService.allEvents("1234");

        Assert.assertNotNull(eventResults.getEventsArray());
        Assert.assertEquals(eventResults.getEventsArray().size(), 3);
        Assert.assertNull(eventResults.getErrorMessage());

        ArrayList<Events> eventArray = new ArrayList<Events>();
        eventArray.add(eventOne);
        eventArray.add(eventTwo);
        eventArray.add(eventThree);

        for (int i = 0; i < eventArray.size(); i++){
            Assert.assertEquals(eventArray.get(i), eventResults.getEventsArray().get(i));
        }

    }

    @Test
    public void allEventsFail() throws SQLException //Using services to make sure only events under users were found
    {
        EventDao eventDao = new EventDao();
        Events eventOne = new Events("yes", "no", "false", 1000, 4000,"m","tokyo", "death", 1969);
        Events eventTwo = new Events("no", "no", "can", 999, 3333,"stuff","yessir", "more death", 1900);
        Events eventThree = new Events("nope", "no", "yup", 494, 1029304,"not America","not New York", "birth", 1870);
        Events eventFour = new Events("1010", "yes", "whocares", 4293, 4059309,"Iraq","1234", "death", 1400);

        eventDao.insertEvent(eventOne);
        eventDao.insertEvent(eventTwo);
        eventDao.insertEvent(eventThree);
        eventDao.insertEvent(eventFour);

        EventService eventService = new EventService();
        AllEventResults eventResults = eventService.allEvents("10");

        Assert.assertNull(eventResults.getEventsArray());
        Assert.assertNotNull(eventResults.getErrorMessage());
        Assert.assertEquals(eventResults.getErrorMessage(), "Requested event does not belong to this user error ");


    }
}