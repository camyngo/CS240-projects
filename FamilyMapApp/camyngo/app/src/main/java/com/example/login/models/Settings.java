package com.example.login.models;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;

public class Settings {

    private boolean storyLines;
    private boolean familyLines;
    private boolean spouseLines;
    private int storyColor;
    private int familyColor;
    private int spouseColor;
    private int currMapType;


    // ========================== Constructor ========================================
    public Settings()
    {
        storyLines = true;
        familyLines = true;
        spouseLines = true;
        storyColor = Color.BLUE;
        familyColor = Color.GREEN;
        spouseColor = Color.MAGENTA;
        currMapType = GoogleMap.MAP_TYPE_NORMAL;
    }

    //_______________________________ Getters and Setters __________________________________________

    public boolean isStoryLines()
    {
        return storyLines;
    }

    public void setStoryLines(boolean storyLines)
    {
        this.storyLines = storyLines;
    }

    public boolean isFamilyLines()
    {
        return familyLines;
    }

    public void setFamilyLines(boolean familyLines)
    {
        this.familyLines = familyLines;
    }

    public boolean isSpouseLines()
    {
        return spouseLines;
    }

    public void setSpouseLines(boolean spouseLines)
    {
        this.spouseLines = spouseLines;
    }

    public int getStoryColor()
    {
        return storyColor;
    }

    public int getFamilyColor()
    {
        return familyColor;
    }


    public int getSpouseColor()
    {
        return spouseColor;
    }
}