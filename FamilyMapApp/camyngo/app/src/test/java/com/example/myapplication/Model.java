package com.example.myapplication;

import com.example.login.models.Events;
import com.example.login.models.Filter;
import com.example.login.models.Persons;

import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

/**
 *Calculates family relationships (i.e., spouses, parents, children) - done
 * Filters events according to the current filter settings
 * Chronologically sorts a person’s individual events (birth first, death last, etc.) - done
 * Correctly searches for people and events******/

public class Model {
    @Before
    public void setUp()
    {
        //String eventID, String eventDescendantID, String eventPersonID, double eventLatitude,
        //  double eventLongitude, String eventCountry, String eventCity, String eventType, int eventYear
        Events eventOne = new Events("1", "no", "1", 1000, 4000,"m","tokyo", "give birth", 1980);
        Events eventTwo = new Events("2", "no", "1", 999, 3333,"stuff","yessir", "marriage", 1960);
        Events eventThree = new Events("3", "no", "1", 494, 102904,"America","New York", "birth", 1870);
        Events eventFour = new Events("4", "no", "1", 4293, 4059309,"Iraq","1234", "death", 2010);

        // String personID, String descendantID, String personFirstName, String personLastName,String personGender,
        // String personFatherID, String personMotherID, String personSpouseID
        Persons personOne = new Persons("1", "no", "false", "john", "f","4","3", "2");
        Persons personTwo = new Persons("2", "no","Jack","Frost","f",null,null,"1");
        Persons personThree = new Persons("3", "no","jenny","F.","m","2",null,"4");
        Persons personFour = new Persons("4", "no","jenny","F.","m",null,null,"3");
        Persons onlyMom = new Persons("mom", "no", "mumma", "gump", "f",null,null,null);


        // user has no spouse ID
        Persons userOne = new Persons("no", "nope", "false", "john", "m","1","mom", null);

        Events[] eventsArray = new Events[] {eventOne, eventTwo, eventThree, eventFour};
        Persons[] personArray = new Persons[] {userOne, personOne, personTwo, personThree, personFour, onlyMom};

        Map<String, Persons> personsMap = new HashMap<String, Persons>();
        com.example.login.models.Model model = com.example.login.models.Model.initialize();
        model.setUsers(userOne);

        for(int i = 0; i < personArray.length; i++){
            String personID = personArray[i].getPersonID();
            personsMap.put(personID, personArray[i]);
        }
        Map<String, Events> eventsMap = new HashMap<String, Events>();

        for(int i = 0; i < eventsArray.length; i++){
            String eventID = eventsArray[i].getEventID();
            eventsMap.put(eventID, eventsArray[i]);
        }

        model.setEvents(eventsMap);
        model.setPeople(personsMap);
        model.initializeAllData();

    }

    @Test
    public void getPeople()             // getting all people
    {
        com.example.login.models.Model model = com.example.login.models.Model.initialize();
        Map<String, Persons> test = model.getPeople();
        assertNotNull(test);
        assertEquals(6, test.size());

        Persons personOne = new Persons("1", "no", "false", "john", "doe","4","3", "2");
        assertEquals(personOne.getPersonFatherID(), test.get("1").getPersonFatherID());
        assertEquals(personOne.getPersonMotherID(),"3");
        assertEquals(personOne.getPersonSpouseID(),"2");




        Persons personTwo = new Persons("2", "no","Jack","Frost","f",null,null,"1");
        assertEquals(personTwo.getPersonGender(), test.get("2").getPersonGender());

        Persons personThree = new Persons("3", "no","jenny","F.","m",null,"yup","4");
        assertEquals(personThree.getPersonLastName(), test.get("3").getPersonLastName());
        assertEquals(personThree.getDescendantID(),"no");

        Persons personFour = new Persons("4", "no","jenny","F.","m",null,"yup","3");
        assertEquals(personFour.getPersonFirstName(), test.get("4").getPersonFirstName());

        Persons onlyMom = new Persons("mom", "no", "mumma", "gump", "m",null,null,null);
        assertEquals(onlyMom.getDescendantID(), test.get("mom").getDescendantID());

        assertEquals(personFour.getPersonFirstName(), test.get("4").getPersonFirstName());
    }

    @Test
    public void getPeopleFail()             // getting all people failing test
    {
        com.example.login.models.Model model = com.example.login.models.Model.initialize();
        Map<String, Persons> test = model.getPeople();
        assertNotNull(test);
        assertNotEquals(test.size(), 9);

        Persons personOne = new Persons("1", "no", "false", "john", "doe","4","3", "2");
        assertNotEquals(personOne.getPersonFatherID(), 5);

        Persons personTwo = new Persons("2", "no","Jack","Frost","f",null,null,null);
        assertNotEquals(personTwo.getPersonGender(), "m");

        Persons personThree = new Persons("3", "no","jenny","F.","m",null,"yup",null);
        assertNotEquals(personThree.getPersonLastName(), "me");

        Persons personFour = new Persons("4", "no","jenny","F.","m",null,"yup",null);
        assertNotEquals(personFour.getPersonFirstName(), "hawk");

        Persons onlyMom = new Persons("mom", "no", "mumma", "gump", "m",null,null,null);
        assertNotEquals(onlyMom.getDescendantID(), "yes");

        assertNotEquals(personFour.getPersonFirstName(), test.get("2").getPersonFirstName());
    }

    @Test
    public void testingRelatioship()             // testing the relationship
    {
        com.example.login.models.Model model = com.example.login.models.Model.initialize();
        Map<String, Persons> test = model.getPeople();
        assertNotNull(test);
        assertEquals(6, test.size());

        Persons personOne = new Persons("1", "no", "false", "john", "doe","4","3", "2");
        Persons personTwo = new Persons("2", "no","Jack","Frost","f",null,null,"1");
        Persons personThree = new Persons("3", "no","jenny","F.","m",null,"yup","4");
        Persons personFour = new Persons("4", "no","jenny","F.","m",null,"yup","3");
        //testing parents of person 1
        assertEquals(personOne.getPersonFatherID(), test.get("1").getPersonFatherID());
        assertEquals(personOne.getPersonMotherID(),"3");


        // is the spouse matching?
        assertEquals(personTwo.getPersonSpouseID(),"1");
        assertEquals(personOne.getPersonSpouseID(),"2");
        assertEquals(personThree.getPersonSpouseID(),"4");
        assertEquals(personFour.getPersonSpouseID(),"3");

        // checking for matching descendant ID
        assertEquals(personThree.getDescendantID(),"no");
        assertEquals(personFour.getDescendantID(),"no");
    }

    @Test
    public void testingRelatioshipFail()             // testing the relationship fail
    {
        com.example.login.models.Model model = com.example.login.models.Model.initialize();
        Map<String, Persons> test = model.getPeople();
        assertNotNull(test);
        assertEquals(6, test.size());

        Persons personOne = new Persons("1", "no", "false", "john", "doe","4","3", "2");
        Persons personTwo = new Persons("2", "no","Jack","Frost","f",null,null,"1");
        Persons personThree = new Persons("3", "no","jenny","F.","m",null,"yup","4");
        Persons personFour = new Persons("4", "no","jenny","F.","m",null,"yup","3");
        //testing parents of person 1
        assertNotEquals(personOne.getPersonFatherID(), test.get("2").getPersonFatherID());
        assertNotEquals(personOne.getPersonMotherID(),"2");


        // is the spouse matching?
        assertNotEquals(personTwo.getPersonSpouseID(),"42");
        assertNotEquals(personOne.getPersonSpouseID(),"23");
        assertNotEquals(personThree.getPersonSpouseID(),"31");
        assertNotEquals(personFour.getPersonSpouseID(),"11");

        // checking for matching descendant ID
        assertNotEquals(personThree.getDescendantID(),"36");
        assertNotEquals(personFour.getDescendantID(),"y36");
    }


    @Test
    public void getEvents()                 //getting all events
    {
        com.example.login.models.Model model = com.example.login.models.Model.initialize();
        Map<String, Events> test = model.getEvents();
        assertNotNull(test);
        assertEquals(4, test.size());

        Events eventOne = new Events("1", "no", "1", 1000, 4000,"m","tokyo", "give birth", 1980);
        Events eventTwo = new Events("2", "no", "1", 999, 3333,"stuff","yessir", "marriage", 1960);
        Events eventThree = new Events("3", "no", "1", 494, 102904,"America","New York", "birth", 1870);
        Events eventFour = new Events("4", "no", "1", 4293, 4059309,"Iraq","1234", "death", 2010);

        assertEquals(eventOne.getEventCity(), test.get("1").getEventCity());
        assertEquals(eventTwo.getEventCountry(), test.get("2").getEventCountry());
        assertEquals(eventThree.getEventID(), test.get("3").getEventID());
        assertEquals(eventFour.getEventLatitude(), test.get("4").getEventLatitude(), .05);
        assertEquals(eventFour.getEventID(), test.get("4").getEventID());
    }

    @Test
    public void getEventFail()             // getting all people failing test
    {
        com.example.login.models.Model model = com.example.login.models.Model.initialize();
        Map<String, Events> test = model.getEvents();
        assertNotNull(test);
        assertNotEquals(5, test.size());
        
        Events eventOne = new Events("1", "no", "1", 1000, 4000,"m","tokyo", "give birth", 1980);
        Events eventTwo = new Events("2", "no", "1", 999, 3333,"stuff","yessir", "marriage", 1960);
        Events eventThree = new Events("3", "no", "1", 494, 102904,"America","New York", "birth", 1870);
        Events eventFour = new Events("4", "no", "1", 4293, 4059309,"Iraq","1234", "death", 2010);

        assertNotEquals(eventOne.getEventCity(), test.get("2").getEventCity());
        assertNotEquals(eventTwo.getEventCountry(), test.get("3").getEventCountry());
        assertNotEquals(eventThree.getEventID(), test.get("4").getEventID());
        assertNotEquals(eventFour.getEventLatitude(), test.get("1").getEventLatitude(), .05);
        assertNotEquals(eventFour.getEventID(), test.get("2").getEventID());
    }

    @Test
    public void getPaternalAncestors()              //father ancestor check
    {
        com.example.login.models.Model model = com.example.login.models.Model.initialize();
        Set<String> paternalAncestors = model.getPaternalAncestors();

        assertNotNull(paternalAncestors);
        assertEquals(4, paternalAncestors.size());
        assertTrue(paternalAncestors.contains("1"));
        assertTrue(paternalAncestors.contains("2"));
        assertTrue(paternalAncestors.contains("3"));
        assertTrue(paternalAncestors.contains("4"));
        System.out.println(paternalAncestors);

    }

    @Test
    public void getPaternalAncestorsFail()              //father ancestor check fail
    {
        com.example.login.models.Model model = com.example.login.models.Model.initialize();
        Set<String> paternalAncestors = model.getPaternalAncestors();

        assertNotNull(paternalAncestors);
        assertEquals(4, paternalAncestors.size());
        assertFalse(paternalAncestors.contains("hawk"));
        assertFalse(paternalAncestors.contains("dad"));
        assertFalse(paternalAncestors.contains("me"));
        assertFalse(paternalAncestors.contains("no"));
        assertFalse(paternalAncestors.contains("mom"));
        System.out.println(paternalAncestors);
    }

    @Test
    public void getMaternalAncestors()          //mother ancestor check
    {
        com.example.login.models.Model model = com.example.login.models.Model.initialize();
        Set<String> maternalAncestors = model.getMaternalAncestors();

        assertNotNull(maternalAncestors);
        assertEquals(1, maternalAncestors.size());
        assertTrue(maternalAncestors.contains("mom"));
        assertFalse(maternalAncestors.contains("4"));
        System.out.println(maternalAncestors);
    }

    @Test
    public void getMaternalAncestorsFail()          //mother ancestor check fail
    {
        com.example.login.models.Model model = com.example.login.models.Model.initialize();
        Set<String> maternalAncestors = model.getMaternalAncestors();

        assertNotNull(maternalAncestors);
        assertEquals(1, maternalAncestors.size());
        assertFalse(maternalAncestors.contains("4"));
        System.out.println(maternalAncestors);
    }

    @Test
    public void sortEventsByYear()              //sort persons events by year
    {
        com.example.login.models.Model model = com.example.login.models.Model.initialize();

        Events eventOne = new Events("1", "no", "1", 1000, 4000,"m","tokyo", "give birth", 1980);
        Events eventTwo = new Events("2", "no", "1", 999, 3333,"stuff","yessir", "marriage", 1960);
        Events eventThree = new Events("3", "no", "1", 494, 102904,"America","New York", "birth", 1870);
        Events eventFour = new Events("4", "no", "1", 4293, 4059309,"Iraq","1234", "death", 2010);

        List<Events> eventsArrayList = model.getAllPersonEvents().get("1");
        assertNotNull(eventsArrayList);

         //sorting all the events by order of person id = "1"
        eventsArrayList = model.sortEventsByYear(eventsArrayList);
        assertEquals(eventThree, eventsArrayList.get(0)); // birth
        assertEquals(eventTwo, eventsArrayList.get(1)); // marriage
        assertEquals(eventOne, eventsArrayList.get(2)); // give birth
        assertEquals(eventFour, eventsArrayList.get(3)); // death

        System.out.println(eventsArrayList.get(0).getEventType());
        System.out.println(eventsArrayList.get(1).getEventType());
        System.out.println(eventsArrayList.get(2).getEventType());
        System.out.println(eventsArrayList.get(3).getEventType());
    }

    @Test
    public void sortEventsByYearFail()              //sort persons events by year fail
    {
        com.example.login.models.Model model = com.example.login.models.Model.initialize();

        Events eventOne = new Events("1", "no", "1", 1000, 4000,"m","tokyo", "give birth", 1980);
        Events eventTwo = new Events("2", "no", "1", 999, 3333,"stuff","yessir", "marriage", 1960);
        Events eventThree = new Events("3", "no", "1", 494, 102904,"America","New York", "birth", 1870);
        Events eventFour = new Events("4", "no", "1", 4293, 4059309,"Iraq","1234", "death", 2010);

        List<Events> eventsArrayList = model.getAllPersonEvents().get("1");
        assertNotNull(eventsArrayList);

        //sorting all the events by order of person id = "1"
        eventsArrayList = model.sortEventsByYear(eventsArrayList);
        assertNotEquals(eventThree, eventsArrayList.get(1)); // birth
        assertNotEquals(eventTwo, eventsArrayList.get(0)); // marriage
        assertNotEquals(eventOne, eventsArrayList.get(3)); // give birth
        assertNotEquals(eventFour, eventsArrayList.get(2)); // death
    }

    @Test
    public void filterEvents()              //filter by event type
    {
        com.example.login.models.Model model = com.example.login.models.Model.initialize();
        Filter filter = model.getFilter();

        Map<String, Events> test = model.getDisplayedEvents();
        assertNotNull(test);
        assertEquals(4, test.size());
        Events eventOne = new Events("1", "no", "1", 1000, 4000,"m","tokyo", "give birth", 1980);
        Events eventTwo = new Events("2", "no", "1", 999, 3333,"stuff","yessir", "marriage", 1960);
        Events eventThree = new Events("3", "no", "1", 494, 102904,"America","New York", "birth", 1870);
        Events eventFour = new Events("4", "no", "1", 4293, 4059309,"Iraq","1234", "death", 2010);


        assertEquals(eventOne.getEventCity(), test.get("1").getEventCity());
        assertEquals(eventTwo.getEventCountry(), test.get("2").getEventCountry());
        assertEquals(eventThree.getEventID(), test.get("3").getEventID());
        assertEquals(eventFour.getEventLatitude(), test.get("4").getEventLatitude(), .05);
        assertNotEquals(eventFour.getEventID(), test.get("2").getEventID());

        filter.getDisplayedEvents().remove("death");

        test = model.getDisplayedEvents();
        assertNotNull(test);
        assertEquals(3, test.size());

        assertEquals(eventTwo.getEventCity(), test.get("2").getEventCity());
        assertEquals(eventThree.getEventCountry(), test.get("3").getEventCountry());
        assertTrue(test.containsKey("1"));
        assertTrue(test.containsKey("2"));
        assertTrue(test.containsKey("3"));

//
        // after removing please add it back hahahah
        filter.getDisplayedEvents().add("death");
    }

    @Test
    public void filterEventsFail()              //filter by event type fail
    {
        com.example.login.models.Model model = com.example.login.models.Model.initialize();
        Filter filter = model.getFilter();

        Map<String, Events> test = model.getDisplayedEvents();
        assertNotNull(test);
        assertEquals(4, test.size());
        Events eventOne = new Events("1", "no", "1", 1000, 4000,"m","tokyo", "give birth", 1980);
        Events eventTwo = new Events("2", "no", "1", 999, 3333,"stuff","yessir", "marriage", 1960);
        Events eventThree = new Events("3", "no", "1", 494, 102904,"America","New York", "birth", 1870);
        Events eventFour = new Events("4", "no", "1", 4293, 4059309,"Iraq","1234", "death", 2010);


        assertEquals(eventOne.getEventCity(), test.get("1").getEventCity());
        assertEquals(eventTwo.getEventCountry(), test.get("2").getEventCountry());
        assertEquals(eventThree.getEventID(), test.get("3").getEventID());
        assertEquals(eventFour.getEventLatitude(), test.get("4").getEventLatitude(), .05);
        assertNotEquals(eventFour.getEventID(), test.get("2").getEventID());

        filter.getDisplayedEvents().remove("mimi");

        test = model.getDisplayedEvents();
        assertNotNull(test);
        //nothing getting filter
        assertEquals(4, test.size());

    }

    @Test
    public void filterPeople()              //filter by people
    {
        com.example.login.models.Model model = com.example.login.models.Model.initialize();
        Filter filter = model.getFilter();

        Map<String, Events> test = model.getDisplayedEvents();
        assertNotNull(test);
        assertEquals(4, test.size());

        Events eventOne = new Events("1", "no", "1", 1000, 4000,"m","tokyo", "death", 1969);
        assertEquals(eventOne.getEventCity(), test.get("1").getEventCity());

        Events eventTwo = new Events("2", "no", "1", 999, 3333,"stuff","yessir", "more death", 1900);
        assertEquals(eventTwo.getEventCountry(), test.get("2").getEventCountry());

        Events eventThree = new Events("3", "no", "1", 494, 102904,"not America","not New York", "birth", 1870);
        assertEquals(eventThree.getEventID(), test.get("3").getEventID());

        Events eventFour = new Events("4", "no", "1", 4293, 4059309,"Iraq","1234", "death", 1400);
        assertEquals(eventFour.getEventLatitude(), test.get("4").getEventLatitude(), .05);
        assertNotEquals(eventFour.getEventID(), test.get("2").getEventID());

        filter.setFemales(false);

        test = model.getDisplayedEvents();
        assertNotNull(test);
        // test size is sucessfully filter
        assertEquals(0, test.size());
    }

    @Test
    public void filterPeopleFail()              //filter by people fail
    {
        com.example.login.models.Model model = com.example.login.models.Model.initialize();
        Filter filter = model.getFilter();

        Map<String, Events> test = model.getDisplayedEvents();
        assertNotNull(test);
        assertEquals(4, test.size());

        Events eventOne = new Events("1", "no", "1", 1000, 4000,"m","tokyo", "death", 1969);
        assertEquals(eventOne.getEventCity(), test.get("1").getEventCity());

        Events eventTwo = new Events("2", "no", "1", 999, 3333,"stuff","yessir", "more death", 1900);
        assertEquals(eventTwo.getEventCountry(), test.get("2").getEventCountry());

        Events eventThree = new Events("3", "no", "1", 494, 102904,"not America","not New York", "birth", 1870);
        assertEquals(eventThree.getEventID(), test.get("3").getEventID());

        Events eventFour = new Events("4", "no", "1", 4293, 4059309,"Iraq","1234", "death", 1400);
        assertEquals(eventFour.getEventLatitude(), test.get("4").getEventLatitude(), .05);
        assertNotEquals(eventFour.getEventID(), test.get("2").getEventID());

        filter.setMales(false);
        // doing nothing with the people filter and the events
        test = model.getDisplayedEvents();
        assertNotNull(test);
        // test size is not being filter
        assertEquals(4, test.size());
    }
}
