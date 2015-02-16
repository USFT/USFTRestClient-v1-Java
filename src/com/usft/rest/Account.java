package com.usft.rest;

/**
* Created with IntelliJ IDEA.
* User: krudy
* Date: 11/20/13
* Time: 2:07 PM
* To change this template use File | Settings | File Templates.
*/ /// <summary>
/// This class represents an account in the USFT database. The information is limited in scope and read-only.
/// </summary>
public class Account
{
    /// <summary>
    /// A unique identifier for this AccountId. This can be included as a parameter for many methods, to allow a parent account to act on behalf of a child account.
    /// </summary>
    public long AccountId;
    public long [] ViewableAccountIds;
    /// <summary>
    /// The login name for the Account
    /// </summary>
    public String Login;
    /// <summary>
    /// The name associated with the Account
    /// </summary>
    public String Name;
    /// <summary>
    /// The email associated with Account
    /// </summary>
    public String Email;
}
