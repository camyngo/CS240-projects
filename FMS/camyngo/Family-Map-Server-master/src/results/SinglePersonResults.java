package results;

import models.Persons;

/** SinglePersonResults is a result of the person/[person-ID] command, contains:
 * personID which is the id of the person
 * username who is a Descendant of said person,
 * first and last name,
 * gender of person,
 * father person ID (optional),
 * mother person ID (optional),
 * spouse person ID (optional),
 * an error message if something went wrong
 */
public class SinglePersonResults {
    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;
    private String userName;

    private String message;

    // ========================== Constructors ========================================
    public SinglePersonResults(Persons singlePerson, String username)
    {
        personID = singlePerson.getPersonID();
        userName = username;
        associatedUsername = singlePerson.getDescendantID();
        firstName = singlePerson.getPersonFirstName();
        lastName = singlePerson.getPersonLastName();
        gender = singlePerson.getPersonGender();
        fatherID = singlePerson.getPersonFatherID();
        motherID = singlePerson.getPersonMotherID();
        spouseID = singlePerson.getPersonSpouseID();
    }

    public SinglePersonResults()
    {}

    public SinglePersonResults(String error)
    {
        message = error;
    }


    //_______________________________ Getters and Setters __________________________________________
    public String getPersonID()
    {
        return personID;
    }

    public void setPersonID(String personID)
    {
        this.personID = personID;
    }

    public String getDescendant()
    {
        return associatedUsername;
    }

    public void setDescendant(String descendant)
    {
        this.associatedUsername = descendant;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getFatherID()
    {
        return fatherID;
    }

    public void setFatherID(String fatherID)
    {
        this.fatherID = fatherID;
    }

    public String getMotherID()
    {
        return motherID;
    }

    public void setMotherID(String motherID)
    {
        this.motherID = motherID;
    }

    public String getSpouseID()
    {
        return spouseID;
    }

    public void setSpouseID(String spouseID)
    {
        this.spouseID = spouseID;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getErrorMessage() {
        return message;
    }
}
