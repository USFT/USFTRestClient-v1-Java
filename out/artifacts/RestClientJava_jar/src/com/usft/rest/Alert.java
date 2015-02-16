package com.usft.rest;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: krudy
 * Date: 11/20/13
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */
/// <summary>
/// This class represents Alerts in your account
/// </summary>
public class Alert
{
    /// <summary>
    /// The Type of this Alert. It can be changed after creation. Persistence of old parameters is attempted but not guaranteed.
    /// </summary>
    public AlertTypes AlertType;

    /// <summary>
    /// The AccountId associated with this Alert
    /// </summary>
    public long AccountId;
    /// <summary>
    /// c
    /// </summary>
    public long AlertId;
    /// <summary>
    /// The name of this Alert. This is what will be listed in the Alert Editor in the webapp.
    /// </summary>
    public String AlertName;

    /// <summary>
    /// (Optional) The vehicle this Alert tracks. If set to null, 0, or -1, the Alert will apply to all vehicles in the account.
    /// </summary>
    public int VehicleId;

    /// <summary>
    /// Whether or not email is enabled as a valid output for the Alert.
    /// </summary>
    public boolean EmailEnabled;
    /// <summary>
    /// Whether or not sms is enabled as a valid output for the Alert.
    /// </summary>
    public boolean SMSEnabled;

    /// <summary>
    /// The email address to be contacted when the Alert is triggered.
    /// </summary>
    public String Email;
    /// <summary>
    /// The SMS number to be messaged when the Alert is trigered.
    /// </summary>
    public String SMSNumber;
    /// <summary>
    /// The carrier for the SMSNumber parameter.
    /// </summary>
    public String SMSCarrier;

    /// <summary>
    /// The hour of the day, 0-23, that the Alert should begin monitoring. Note that overnight Alerts (beginning in the evening and ending in the morning) are not supported in v1.
    /// </summary>
    public long StartHour = 0;
    /// <summary>
    /// The minute of the hour, 0-59, that the Alert should begin monitoring. Note that overnight Alerts (beginning in the evening and ending in the morning) are not supported in v1.
    /// </summary>
    public long StartMinute = 0;
    /// <summary>
    /// The hour of the day, 0-23, that the Alert should stop monitoring. Note that overnight Alerts (beginning in the evening and ending in the morning) are not supported in v1.
    /// </summary>
    public long EndHour = 23;
    /// <summary>
    /// The minute of the hour, 0-59, that the Alert should stop monitoring. Note that overnight Alerts (beginning in the evening and ending in the morning) are not supported in v1.
    /// </summary>
    public long EndMinute = 59;
    /// <summary>
    /// The TimeZone to be used for alert reporting.
    /// </summary>
    public String TimeZone;

    /// <summary>
    /// (Geofence) This is a list of points for a polygonal geofence alert.
    /// </summary>
    public List<MapPoint> Points = new LinkedList<MapPoint>();
    /// <summary>
    /// (Geofence, Route) This is the distance from the center of a circular geofence or a route path which will trigger the alert.
    /// </summary>
    public int Radius;
    /// <summary>
    /// (Geofence, Route) This is the color of the alert zone when rendered in the webapp, in six-character RGB format (eg, "00FF00" for bright green)
    /// </summary>
    public String ZoneColor;

    /// <summary>
    /// (Speed) This is the maximum speed a speed alert will allow before triggering the alert.
    /// </summary>
    public double SpeedThreshhold;

    /// <summary>
    /// (Ignition) This indicates whether the alert should activate when the Ignition is turned on
    /// </summary>
    public boolean WhenIgnitionTurnedOn;
    /// <summary>
    /// (Ignition) This indicates whether the alert should activate when the Ignition is turned off
    /// </summary>
    public boolean WhenIgnitionTurnedOff;

    /// <summary>
    /// (Power) If the device's reported power descends below this value, a power alert will be sent
    /// </summary>
    public long PowerMinimum;

    /// <summary>
    /// (Route) The unique Id of the Route for this Alert
    /// </summary>
    public long RouteId;
}