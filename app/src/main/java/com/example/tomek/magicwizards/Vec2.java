package com.example.tomek.magicwizards;

public class Vec2
{
    private float x,y;
    public Vec2(float xPos,float yPos)
    {
        x = xPos;
        y = yPos;
    }
    public float X()
    {
        return x;
    }
    public float Y()
    {
        return y;
    }
    public void Set(float xPos,float yPos)
    {
        x = xPos;
        y = yPos;
    }
    public void SetX(float xPos)
    {
        x = xPos;
    }
    public void SetY(float yPos)
    {
        y = yPos;
    }
    public String toString()
    {
        return "X: " + x + " " + "Y: " + y;
    }
}
