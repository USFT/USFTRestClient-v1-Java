package com.usft.rest;

import com.google.gson.*;
import com.google.gson.reflect.*;
import java.util.*;
import java.lang.reflect.*;

/**
 * Created with IntelliJ IDEA.
 * User: krudy
 * Date: 11/20/13
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
    /// <summary>
    /// This class represents an Address in your account.
    /// </summary>
    public class Address
    {
        /// <summary>
        /// The AccountId associated with this Address Fence.
        /// </summary>
        public long AccountId;
        /// <summary>
        /// A unique identifier for the Address. Use this when making further API calls specific to this Address.
        /// </summary>
        public long AddressId;
        /// <summary>
        /// The name assigned to this address. It will be printed as a label on the Webapp when the Address is on screen.
        /// </summary>
        public String Name;
        /// <summary>
        /// The address itself, eg: "123 Example St., Oklahoma City, OK 73111"
        /// </summary>
        public String AddressBlock;
        /// <summary>
        /// The Latitude of the Address. If Latitude and Longitude are set to 0 for the creation of a new Address, they will be automatically generated from the AddressBlock.
        /// </summary>
        public double Latitude;
        /// <summary>
        /// The Latitude of the Address. If Latitude and Longitude are set to 0 for the creation of a new Address, they will be automatically generated from the AddressBlock.
        /// </summary>
        public double Longitude;
        /// <summary>
        /// The USFT Image to be used when displaying the address on screen.
        /// </summary>
        public String Image;
        /// <summary>
        /// A list of AddressGroupIds referencing Address Groups to which this Address belongs. This Address can be added to an Address Group by adding the appropriate AddressGroupId to this List, then Updating the Address.
        /// </summary>
        public Set<Long> AddressGroupIds = new HashSet<Long>();

        public Address() { }
        public Address(long AccountId, long AddressId, String Name, String AddressBlock, double Latitude, double Longitude, String Image,
                       Set<Long> AddressGroupIds)
        {
            this.AccountId = AccountId;
            this.AddressId = AddressId;
            this.Name = Name;
            this.AddressBlock = AddressBlock;
            this.Latitude = Latitude;
            this.Longitude = Longitude;
            this.Image = Image;
            if (AddressGroupIds == null)
                this.AddressGroupIds = new HashSet<Long>();
            else this.AddressGroupIds = AddressGroupIds;
        }



    }
