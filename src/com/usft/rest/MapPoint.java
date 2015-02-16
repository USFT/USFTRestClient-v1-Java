package com.usft.rest;

/**
* Created with IntelliJ IDEA.
* User: krudy
* Date: 11/20/13
* Time: 2:08 PM
* To change this template use File | Settings | File Templates.
*/ /// <summary>
/// This class represents a point on the map for classes which require large collections of points
/// </summary>
public class MapPoint
{
    /// <summary>
    /// The Latitude value for the point
    /// </summary>
    public double Latitude;
    /// <summary>
    /// The Longitude value for the point
    /// </summary>
    public double Longitude;

    public MapPoint(double Latitude, double Longitude)
    {
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }

    public String ToString()
    {
        return String.valueOf(Latitude) + "," + String.valueOf(Longitude);
    }
}
