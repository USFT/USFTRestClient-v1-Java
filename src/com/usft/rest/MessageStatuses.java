package com.usft.rest;

/**
* Created with IntelliJ IDEA.
* User: krudy
* Date: 11/20/13
* Time: 2:07 PM
* To change this template use File | Settings | File Templates.
*/ /// <summary>
/// The Status of an existing Device Message. Generally, this should not be set by API users.
/// </summary>
public enum MessageStatuses
{
    /// <summary>
    /// A message request initiated on the webapp has been received and is awaiting processing.
    /// </summary>
    WebappReceived(0),
    /// <summary>
    /// A message request initiated on the webapp has been received and is in processing.
    /// </summary>
    WebappProcessing(1),
    /// <summary>
    /// A message request initiated on the webapp has been processed and sent.
    /// </summary>
    WebappProcessed(2),
    /// <summary>
    /// A message request initiated on the webapp has encountered an unknown error.
    /// </summary>
    WebappErrorUnknown(3),
    /// <summary>
    /// A message request initiated on the webapp has encountered an error with its SIM Number.
    /// </summary>
    WebappErrorSIMNumber(4),
    /// <summary>
    /// A message request initiated on the webapp has encountered an error retrieving its MSISDN.
    /// </summary>
    WebappErrorMSISDN(5),
    /// <summary>
    /// A message request initiated on the webapp has encountered an error: the device command requested is not supported.
    /// </summary>
    WebappErrorCommand(6),
    /// <summary>
    /// A message request initiated on the webapp has encountered an error with smtp configuration.
    /// </summary>
    WebappErrorNoEmailAddress(7),
    /// <summary>
    /// A message request initiated on the webapp has encountered an error with the telematic APIs.
    /// </summary>
    WebappErrorDeviceSIMInformation(8),
    /// <summary>
    /// The text message has been sent to the device.
    /// </summary>
    TextSent(40),
    /// <summary>
    /// The text message has been read by the device.
    /// </summary>
    TextRead(41),
    /// <summary>
    /// The text message has been deleted by the device.
    /// </summary>
    TextDeleted(42),
    /// <summary>
    /// A message request initiated through the API has been processed and sent.
    /// </summary>
    APIProcessed(52),
    /// <summary>
    /// A message request initiated through the API has encountered an unknkown error.
    /// </summary>
    APIErrorUnknown(53),
    /// <summary>
    /// A message request initiated through the API has encountered an error with its SIM Number.
    /// </summary>
    APIErrorSimNumber(54),
    /// <summary>
    /// A message request initiated through the API has encountered an error with its MSISDN.
    /// </summary>
    APIErrorMSISDN(55),
    /// <summary>
    /// A message request initiated through the API has encountered an error: the device command requested is not supported.
    /// </summary>
    APIErrorCommand(56),
    /// <summary>
    /// A message request initiated through the API has encountered an error with SMTP configuration.
    /// </summary>
    APIErrorNoEmail(57),
    /// <summary>
    /// A message request initiated through the API has encountered an error with the telematic API.
    /// </summary>
    APIErrorNoDeviceSimInfo(58),
    /// <summary>
    /// A message request initiated through the API for a X5-Nav has been created and is awaiting processing.
    /// </summary>
    APIX5Created(101),
    /// <summary>
    /// A message request initiated through the API for a X5-Nav has been processed and sent.
    /// </summary>
    APIX5Sent(102),
    /// <summary>
    /// A message request initiated through the API for a X5-Nav has been received and acknowledged.
    /// </summary>
    APIX5Ack(103),
    /// <summary>
    /// A message request initiated through the API for a X5-Nav has been answered.
    /// </summary>
    APIX5Answered(104),
    /// <summary>
    /// A message request initiated through the API for a X5-Nav has prompted a response.
    /// </summary>
    APIX5Responded(105);

    private final int myID;

    public int GetID()
    { return myID; }

    MessageStatuses(int ID)
    {
        myID = ID;
    }
}
