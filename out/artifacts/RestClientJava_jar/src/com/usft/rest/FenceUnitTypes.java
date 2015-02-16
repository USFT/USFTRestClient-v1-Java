package com.usft.rest;

/**
* Created with IntelliJ IDEA.
* User: krudy
* Date: 11/20/13
* Time: 2:07 PM
* To change this template use File | Settings | File Templates.
*/ /// <summary>
/// The supported units for Address Fence dimensions
/// </summary>
public enum FenceUnitTypes {
    /// <summary>
    /// Do not use this value. This is returned for some entries which do not have a proper Fence Unit Type set.
    /// </summary>
    Unknown(0),
    /// <summary>
    /// The Address Fence dimensions will be measured in feet.
    /// </summary>
    Feet(1),
    /// <summary>
    /// The Address Fence dimensions will be measured in yards.
    /// </summary>
    Yards(2),
    /// <summary>
    /// The Address Fence dimensions will be measured in miles.
    /// </summary>
    Miles(3),
    /// <summary>
    /// The Address Fence dimensions will be measured in meters.
    /// </summary>
    Meters(4),
    /// <summary>
    /// The Address Fence dimensions will be measured in kilometers.
    /// </summary>
    Kilometers(5);
    private final int myID;

    public int GetID()
    { return myID; }

    FenceUnitTypes(int ID)
    {
        myID = ID;
    }
}
