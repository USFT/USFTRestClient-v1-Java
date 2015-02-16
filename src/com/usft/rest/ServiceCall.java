package com.usft.rest;

import java.util.Date;
import org.joda.time.DateTime;
/**
* Created with IntelliJ IDEA.
* User: krudy
* Date: 11/20/13
* Time: 2:08 PM
* To change this template use File | Settings | File Templates.
*/ /// <summary>
/// This class represents a Service Call created in your account.
/// </summary>
public class ServiceCall
{
    /// <summary>
    /// The AccountId associated with this ServiceCall
    /// </summary>
    public long AccountId;
    /// <summary>
    /// A unique identifier for the Service Call. Use this when making further API calls specific to this Service Call.
    /// </summary>
    public long ServiceCallId;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String WorkOrder;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String Description;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public DateTime DispatchDate;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public DateTime RequestDate;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public DateTime EstimatedStartDate;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String CustomerName;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String Caller;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String EquipmentId;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String Item;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String TerritoryDesc;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String Technician;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String BillCode;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String CallType;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String Priority;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String Status;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String Comment;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String Street;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String City;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String State;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String ZipCode;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String EquipmentRemarks;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String Contact;
    /// <summary>
    /// An optional parameter that can be set for your Service Calls.
    /// </summary>
    public String ContactPhone;
    /// <summary>
    /// If Latitude and Longitude are left null, the API will attempt to retrieve their values from the Street, City, State, and ZipCoded values provided.
    /// </summary>
    public double Latitude;
    /// <summary>
    /// If Latitude and Longitude are left null, the API will attempt to retrieve their values from the Street, City, State, and ZipCoded values provided.
    /// </summary>
    public double Longitude;
    /// <summary>
    /// The color of the label this service call will display in the webapp, in 6-character RGB format (eg, "FF0000" for bright red)
    /// </summary>
    public String FlagColor;
    /// <summary>
    /// The color of the label text this service call will display in the webapp, in 6-character RGB format (eg, "0000FF" for bright green)
    /// </summary>
    public String TextColor;
    /// <summary>
    /// The content of the label text this service call will display in the webapp
    /// </summary>
    public String MarkerName;
}
