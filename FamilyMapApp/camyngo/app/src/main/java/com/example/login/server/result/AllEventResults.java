package com.example.login.server.result;

import com.example.login.models.Events;

import java.util.ArrayList;

public class AllEventResults {

    private ArrayList<Events> data;
    private String message;

    // ========================== Constructors ========================================
    public AllEventResults()
    {
        this.data = null;
        this.message = null;
    }

    public AllEventResults(ArrayList<Events> eList)
    {
        this.data = eList;
        this.message = null;
    }

    public AllEventResults(String errors)
    {
        this.message = errors;
        this.data = null;
    }


    //_______________________________ Getters and Setters __________________________________________
    public ArrayList<Events> getEventsArray()
    {
        return data;
    }

    public void setPersonsArray(ArrayList<Events> eventsArray)
    {
        this.data = eventsArray;
    }

    public String getErrorMessage()
    {
        return message;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.message = errorMessage;
    }

}