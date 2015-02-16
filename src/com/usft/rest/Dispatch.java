package com.usft.rest;

/**
* Created with IntelliJ IDEA.
* User: krudy
* Date: 11/20/13
* Time: 2:07 PM
* To change this template use File | Settings | File Templates.
*/ /// <summary>
/// This class represents a Dispatch message sent to a device in your account
/// </summary>
public class Dispatch
{
    /// <summary>
    /// The AccountId associated with this Dispatch
    /// </summary>
    public long AccountId;
    /// <summary>
    /// A unique identifier for this Dispatch. Use this when making API calls specific to this Dispatch.
    /// </summary>
    public long DispatchId;
    /// <summary>
    /// The Device targeted by this Dispatch.
    /// </summary>
    public long DeviceId;
    /// <summary>
    /// (Optional) The Latitude targeted by the Dispatch. If Latitude and Longitude are left null, the API will attempt to find their values using the provided Address information.
    /// </summary>
    public double Latitude;
    /// <summary>
    /// (Optional) The Longitude targeted by the Dispatch. If Latitude and Longitude are left null, the API will attempt to find their values using the provided Address information.
    /// </summary>
    public double Longitude;
    /// <summary>
    /// The Address targeted by the Dispatch, for all devices EXCEPT the X5-Navs
    /// </summary>
    public String AddressBlock;
    /// <summary>
    /// The Street targeted by the Dispatch, ONLY for X5-Navs
    /// </summary>
    public String X5Street;
    /// <summary>
    /// The City targeted by the Dispatch, ONLY for X5-Navs
    /// </summary>
    public String X5City;
    /// <summary>
    /// The State targeted by the Dispatch, ONLY for X5-Navs
    /// </summary>
    public String X5State;
}
