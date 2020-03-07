
package requests;

import models.*;

/** LoadRequest is an object that received from the client that contains:
 * An Users array
 * An Events array
 * A Persons array to load into the database
 */
public class LoadRequest {

    private Users[] users;
    private Persons[] persons;
    private Events[] events;

    public LoadRequest(Users[] userArray, Persons[] personArray, Events[] eventArray)
    {
        this.users = userArray;
        this.persons = personArray;
        this.events = eventArray;
    }

    //_______________________________ Getters and Setters __________________________________________
    public Users[] getUserArray()
    {
        return users;
    }

    public void setUserArray(Users[] userArray)
    {
        this.users = userArray;
    }

    public Persons[] getPersonArray()
    {
        return persons;
    }

    public void setPersonArray(Persons[] personArray)
    {
        this.persons = personArray;
    }

    public Events[] getEventArray()
    {
        return events;
    }

    public void setEventArray(Events[] eventArray)
    {
        this.events = eventArray;
    }
}
