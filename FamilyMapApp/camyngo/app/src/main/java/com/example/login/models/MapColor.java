package com.example.login.models;

import android.graphics.Color;

/** MapColor
 * The MapColor class stores a float color value for each different event type, and is used
 * for marker colors
 */
public class MapColor extends Color {

    private float color;

    // ========================== Constructor ========================================
    public MapColor(String eventType)
    {
        color = Math.abs((eventType.hashCode() % 360));
        System.out.println("Color hash :" + color);
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public float getColor()
    {
        return color;
    }
}