package com.example.login.models;

import java.util.Objects;
import java.util.UUID;

/** Persons class, contains information for a person such as:
 * Username who is a Descendant of said person
 * First and last name
 * Gender of person
 * Father person ID (optional)
 * Mother person ID (optional)
 * Spouse person ID (optional)
 */
public class Persons {

    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String father;
    private String mother;
    private String spouseID;

    // ___________________________________________ Constructors ___________________________________________
    public Persons()
    {
        this.personID = UUID.randomUUID().toString();
        this.associatedUsername = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.father = null;
        this.mother = null;
        this.spouseID = null;
    }

    public Persons(String personID, String descendantID, String personFirstName, String personLastName,
                   String personGender, String personFatherID, String personMotherID, String personSpouseID)
    {
        this.personID = personID;
        this.associatedUsername = descendantID;
        this.firstName = personFirstName;
        this.lastName = personLastName;
        this.gender = personGender;
        this.father = personFatherID;
        this.mother = personMotherID;
        this.spouseID = personSpouseID;
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

    public String getDescendantID()
    {
        return associatedUsername;
    }

    public void setDescendantID(String descendantID)
    {
        this.associatedUsername = descendantID;
    }

    public String getPersonFirstName()
    {
        return firstName;
    }

    public void setPersonFirstName(String personFirstName)
    {
        this.firstName = personFirstName;
    }

    public String getPersonLastName()
    {
        return lastName;
    }

    public void setPersonLastName(String personLastName)
    {
        this.lastName = personLastName;
    }

    public String getPersonGender()
    {
        return gender;
    }

    public void setPersonGender(String personGender)
    {
        this.gender = personGender;
    }

    public String getPersonFatherID()
    {
        return father;
    }

    public void setPersonFatherID(String personFatherID)
    {
        this.father = personFatherID;
    }

    public String getPersonMotherID()
    {
        return mother;
    }

    public void setPersonMotherID(String personMotherID)
    {
        this.mother = personMotherID;
    }

    public String getPersonSpouseID()
    {
        return spouseID;
    }

    public void setPersonSpouseID(String personSpouseID)
    {
        this.spouseID = personSpouseID;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Persons persons = (Persons) o;
        return personID.equals(persons.personID) && associatedUsername.equals(persons.associatedUsername) &&
                firstName.equals(persons.firstName) && lastName.equals(persons.lastName) &&
                gender.equals(persons.gender) && Objects.equals(father, persons.father) &&
                Objects.equals(mother, persons.mother) && Objects.equals(spouseID, persons.spouseID);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(personID, associatedUsername, firstName, lastName, gender, father, mother, spouseID);
    }
}
