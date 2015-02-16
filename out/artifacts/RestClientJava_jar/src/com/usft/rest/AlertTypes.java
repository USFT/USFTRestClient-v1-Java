package com.usft.rest;

/**
* Created with IntelliJ IDEA.
* User: krudy
* Date: 11/20/13
* Time: 2:07 PM
* To change this template use File | Settings | File Templates.
*/ /// <summary>
/// The supported Alert Types
/// </summary>
public enum AlertTypes {
    /// <summary>
    /// Do not use this value. This is returned for some entries which do not have a proper Alert type set.
    /// </summary>
    Unknown(0),
    /// <summary>
    /// Geofence Alerts monitor the specified vehicles' locations, and trigger when the vehicles enter or exit the specified zone.
    /// </summary>
    GeofenceAlert(1),
    /// <summary>
    /// Speed Alerts monitor the specified vehicles' speeds, and trigger when the vehicles exceed a specified threshold.
    /// </summary>
    SpeedAlert(2),
    /// <summary>
    /// Maintenance Alerts monitor the specified vehicles' performance, and trigger when the vehicles should require further maintenance.
    /// </summary>
    MaintenanceAlert(3),
    /// <summary>
    /// Ignition Alerts mointor the specified vehicles' ignitions, and trigger when the ignition state changes during specified timespans.
    /// </summary>
    IgnitionAlert(4),
    /// <summary>
    /// TODO: Summary here.
    /// </summary>
    PowerAlert(5),
    /// <summary>
    /// Panic Alerts trigger when a specified device sends the Panic state with its location update. This requires PTO-capable devices.
    /// </summary>
    PanicAlert(6),
    /// <summary>
    /// Route alerts are similar to Geofence Alerts, except they monitor a vehicle's location in relation to a route, a series of points it is expected to follow.
    /// </summary>
    RouteAlert(7);
    private final int myID;

    public int GetID()
    { return myID; }

    AlertTypes(int ID)
    {
        myID = ID;
    }
}
