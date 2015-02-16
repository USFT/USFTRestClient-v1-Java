package com.usft.rest;

/**
* Created with IntelliJ IDEA.
* User: krudy
* Date: 11/20/13
* Time: 2:06 PM
* To change this template use File | Settings | File Templates.
*/ /// <summary>
/// The possible types of fence for automatically-refreshing Address Fences.
/// </summary>
public enum FenceTypes {
    /// <summary>
    /// Do not use this value. This is returned for some entries which do not have a proper Fence Type set.
    /// </summary>
    Unknown(0),
    /// <summary>
    /// The Address Fence will redraw itself as a circle of radius [Dims] around the Address when the Address moves
    /// </summary>
    Circle(1),
    /// <summary>
    /// The Address Fence will redraw itself as a square of dimensions [Dims]x[Dims] centered on the Address when the Address moves.
    /// </summary>
    Square(2),
    /// <summary>
    /// The Address Fence will redraw itself as a rectangle of dimensions [Dims] centered on the Address when the Address moves.
    /// </summary>
    Rectangle(3);

    private final int myID;

    public int GetID()
    { return myID; }

    FenceTypes(int ID)
    {
        myID = ID;
    }
}
