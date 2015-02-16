package com.usft.rest;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.joda.time.DateTime;

/**
* Created with IntelliJ IDEA.
* User: krudy
* Date: 11/20/13
* Time: 2:07 PM
* To change this template use File | Settings | File Templates.
*/ /// <summary>
/// This class represents Messages sent to active Devices, usually commands for the vehicle or text for the driver.
/// </summary>
public class DeviceMessage
{
    /// <summary>
    /// The AccountId associated with the Message
    /// </summary>
    public long AccountId;
    /// <summary>
    /// The DeviceId targeted by the Message, or the originator of a Message sent from the device to the API.
    /// </summary>
    public long DeviceId;
    /// <summary>
    /// A unique identifier for the DeviceMessage. Use this when making further API calls specific to this message.
    /// </summary>
    public long MessageId;

    /// <summary>
    /// The Type of the Message. This must be set to send a successful message.
    /// </summary>
    public MessageTypes MessageType;
    /// <summary>
    /// The Status of the Message. If this is set prior to sending, it will be ignored.
    /// </summary>
    public MessageStatuses MessageStatus;
    /// <summary>
    /// The Text of a Message to be read by the driver.
    /// </summary>
    public String MessageText;

    /// <summary>
    /// The time the Message was sent.
    /// </summary>
    public DateTime TimeSent;

    /// <summary>
    /// A list of prepared responses to the message. If set, this allows users of the X5-Nav to respond with the push of a button.
    /// </summary>
    public List<String> CannedResponses = new LinkedList<String>();

    /// <summary>
    /// A response supplied by a X5-Nav.
    /// </summary>
    public String X5Response;
}
