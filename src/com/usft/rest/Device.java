package com.usft.rest;
import com.google.gson.*;
import java.lang.reflect.*;

public class Device
{
    /// <summary>
    /// The AccountId associated with the Message
    /// </summary>
    public long AccountId;
    /// <summary>
    /// A unique identifier for the Device. Use this when making further API calls specific to this device.
    /// </summary>
    public long DeviceId;
    /// <summary>
    /// The name of the device. This will show up on the device's label on the webapp.
    /// </summary>
    public String DeviceName;
    /// <summary>
    /// The color of the label's backdrop on the webapp.
    /// </summary>
    public String FlagColor;
    /// <summary>
    /// The color of the label's text on the webapp.
    /// </summary>
    public String TextColor;
    public Device() { }
    public Device(long AccountId, long DeviceId, String DeviceName, String FlagColor, String TextColor)
    {
        this.AccountId = AccountId;
        this.DeviceId = DeviceId;
        this.DeviceName = DeviceName;
        this.FlagColor = FlagColor;
        this.TextColor = TextColor;
    }


}
