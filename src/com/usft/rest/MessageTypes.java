package com.usft.rest;

/**
* Created with IntelliJ IDEA.
* User: krudy
* Date: 11/20/13
* Time: 2:07 PM
* To change this template use File | Settings | File Templates.
*/ /// <summary>
/// The supported Message Types for Device Messages
/// </summary>
public enum MessageTypes {
    /// <summary>
    /// Do not use this value. This is returned for some entries which do not have a proper Message Type set.
    /// </summary>
    Unknown(0),
    /// <summary>
    /// Command: The device receiving this message should lock its doors.
    /// </summary>
    LockDoor(1),
    /// <summary>
    /// Command: The device receiving this message should unlock its doors.
    /// </summary>
    UnlockDoor(2),
    /// <summary>
    /// Command: The device receiving this message should honk its horn.
    /// </summary>
    HonkHorn(3),
    /// <summary>
    /// Command: The device receiving this message should disable its engine.
    /// </summary>
    DisableEngine(4),
    /// <summary>
    /// Command: The device receiving this message should enable its engine.
    /// </summary>
    EnableEngine(5),
    /// <summary>
    /// Command: The device receiving this message should start its engine.
    /// </summary>
    StartEngine(6),
    /// <summary>
    /// Command: The device receiving this message should immediately transmit a location update.
    /// </summary>
    ForceTransmit(7),
    /// <summary>
    /// Command: The device receiving this message should immediately resync with available satellites.
    /// </summary>
    Resync(8),
    /// <summary>
    /// Command: An paramaterized command that varies depending on device.
    /// </summary>
    AdHoc(14),
    /// <summary>
    /// Message: The device receiving this message should display the provided text to the driver.
    /// </summary>
    Text(100),
    /// <summary>
    /// Message: This is a message from the driver of the device to the user of the API or Webclient.
    /// </summary>
    FromDevice(101);
    private final int myID;

    public int GetID()
    { return myID; }

    MessageTypes(int ID)
    {
        myID = ID;
    }
}
