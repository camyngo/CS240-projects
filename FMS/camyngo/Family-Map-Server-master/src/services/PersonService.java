package services;

import dao.AuthTokenDao;
import dao.PersonDao;
import models.AuthorizationToken;
import models.Persons;
import results.AllEventResults;
import results.AllPersonResults;
import results.SingleEventResult;
import results.SinglePersonResults;

import java.sql.SQLException;
import java.util.ArrayList;

/** PersonService class contains functions to call the DAO with different commmands regarding people, contains
 * AuthTokenDao
 * PersonDao
 */
public class PersonService {

    private AuthTokenDao authTokenDao = new AuthTokenDao();
    private PersonDao personDao = new PersonDao();

//______________________________________ Find Single Person _________________________________________________
    /** singlePerson
     * @param personID personID which is the id of a person and an AuthToken object
     * @param authToken that will be determined as valid or not and identify the user
     * @return PersonResults object that contains a single person or an error message
     */
    public SinglePersonResults singlePerson(String personID, String authToken)
    {
        try {
            AuthorizationToken checkedAuthToken = authTokenDao.checkToken(authToken);

            if (checkedAuthToken == null){
                return new SinglePersonResults("Invalid auth token error");
            }
            else {
                Persons foundPerson = personDao.findSinglePerson(personID);
                if (foundPerson == null){
                    return new SinglePersonResults("Invalid personID parameter error ");
                }
                // is this the one that will fix the problem
                else if (!checkedAuthToken.getUsername().equals(foundPerson.getDescendantID())) {
                    return new SinglePersonResults("Requested person does not belong to this user error");
                }
                else {
                    SinglePersonResults resultToReturn = new SinglePersonResults(foundPerson, checkedAuthToken.getUsername());
                    return resultToReturn;
                }
            }
        }
        catch (SQLException databaseError){
            return new SinglePersonResults("Internal server error");
        }
    }

//______________________________________ Find All People _________________________________________________
    /** allPerson
     * @param  authToken AuthToken object that will be used to identify the user
     * @return PersonResults object that contains a all persons under said user or an error message
     */
    public AllPersonResults allPersons(String authToken)
    {
        try {
            AuthorizationToken checkedAuthToken = authTokenDao.checkToken(authToken);

            if (checkedAuthToken == null){
                return new AllPersonResults("Invalid auth token error");
            }
            else {
                ArrayList<Persons> allFoundPersons = personDao.findAllPersons(checkedAuthToken.getUsername());

                if (allFoundPersons == null){
                    return new AllPersonResults("Requested person does not belong to this user error");
                }
                else {
                    AllPersonResults resultToReturn = new AllPersonResults(allFoundPersons);
                    return resultToReturn;
                }
            }
        }
        catch (SQLException databaseError){
            return new AllPersonResults("Internal server error");
        }
    }

}
