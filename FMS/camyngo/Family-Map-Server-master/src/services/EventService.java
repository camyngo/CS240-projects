package services;

import dao.AuthTokenDao;
import dao.EventDao;
import models.AuthorizationToken;
import models.Events;
import results.AllEventResults;
import results.SingleEventResult;

import java.sql.SQLException;
import java.util.ArrayList;

/** EventService class contains methods to call the DAO for events depending on which command is given, contains
 * EventDao
 * AuthTokenDao
 */
public class EventService {

    private EventDao eventDao = new EventDao();
    private AuthTokenDao authTokenDao = new AuthTokenDao();

//______________________________________ Find Single Event _________________________________________________
    /** singleEvent grabs a single event from the database and returns its info
     * @param eventID   with a personID in it, and an AuthorizationToken object
     * @param authToken to be determined as valid or not, also identify user
     * @return SingleEventResult object containing one event or an error message
     */
    public SingleEventResult singleEvent(String eventID, String authToken)
    {
        try {
            AuthorizationToken checkedAuthToken = authTokenDao.checkToken(authToken);
            if (checkedAuthToken == null) {
                return new SingleEventResult("Invalid auth token error");
            }
            else {
                Events foundEvent = eventDao.findSingleEvent(eventID);
                if (foundEvent == null) {
                    return new SingleEventResult("Invalid eventID parameter error");
                }
                else if (!checkedAuthToken.getUsername().equals(foundEvent.getEventDescendantID())) {
                    return new SingleEventResult("Requested event does not belong to this user error");
                }
                else {
                    SingleEventResult resultToReturn = new SingleEventResult(foundEvent, checkedAuthToken.getUsername());
                    return resultToReturn;
                }
            }

        }
        catch (SQLException databaseError) {
            return new SingleEventResult("Internal server error");
        }

    }

//______________________________________ Find All Events _________________________________________________
    /** allEvent grabs all events under a user and returns all of them
     * @param authToken AuthorizationToken object that is used to identify user
     * @return EventResults object containing all events for person correlated with the AuthToken or an error message
     */
    public AllEventResults allEvents(String authToken)
    {
        try {
            AuthorizationToken checkedAuthToken = authTokenDao.checkToken(authToken);
            if (checkedAuthToken == null) {
                return new AllEventResults("Invalid auth token error");
            }
            else {
                ArrayList<Events> allFoundEvents = eventDao.findAllEvents(checkedAuthToken.getUsername());
                if (allFoundEvents == null) {
                    return new AllEventResults("Requested event does not belong to this user error ");
                }
                else {
                    AllEventResults resultToReturn = new AllEventResults(allFoundEvents);
                    return resultToReturn;
                }
            }
        }
        catch (SQLException databaseError) {
            return new AllEventResults("Internal server error");
        }
    }
}
