package com.example.login.models;

import java.util.ArrayList;
import java.util.List;

/** Filter
 * This class helped store values of displayed male/female/family side events
 * as well as some other boolean values
 */
public class Filter {

    private List<String> allEvents;
    private List<String> displayedEvents;
    private boolean fathersSide;
    private boolean mothersSide;
    private boolean males;
    private boolean females;

    // ========================== Constructor ========================================
    public Filter()
    {
        allEvents = new ArrayList<>(Model.initialize().getEventTypes());
        displayedEvents = new ArrayList<>(Model.initialize().getEventTypes());
        fathersSide = true;
        mothersSide = true;
        males = true;
        females = true;
    }

    //==========================  Getters and Setters ==========================

    public List<String> getDisplayedEvents()
    {
        return displayedEvents;
    }

    public void setDisplayedEvents(List<String> displayedEvents)
    {
        this.displayedEvents = displayedEvents;
    }

    public boolean isFathersSide()
    {
        return fathersSide;
    }

    public void setFathersSide(boolean fathersSide)
    {
        this.fathersSide = fathersSide;
    }

    public boolean isMothersSide()
    {
        return mothersSide;
    }

    public void setMothersSide(boolean mothersSide)
    {
        this.mothersSide = mothersSide;
    }

    public boolean isMales()
    {
        return males;
    }

    public void setMales(boolean males)
    {
        this.males = males;
    }

    public boolean isFemales()
    {
        return females;
    }

    public void setFemales(boolean females)
    {
        this.females = females;
    }

    public boolean containsEventType(String eventType)
    {
        eventType = eventType.toLowerCase();
        for (String event: displayedEvents) {
            if (event.toLowerCase().equals(eventType)){
                return true;
            }
        }
        return false;
    }
}