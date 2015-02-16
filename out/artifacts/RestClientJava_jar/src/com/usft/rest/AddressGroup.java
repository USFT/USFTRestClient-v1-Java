package com.usft.rest;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: krudy
 * Date: 11/20/13
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */ /// <summary>
    /// This class represents an Address Group in your account
    /// </summary>
    public class AddressGroup
    {
        /// <summary>
        /// The AccountId associated with this Address Fence.
        /// </summary>
        public long AccountId;
        /// <summary>
        /// A unique identifier for the Address Group. Use this when making further API calls specific to this Address Group.
        /// </summary>
        public long AddressGroupId;
        /// <summary>
        /// The name assigned to this Address Group. This will show up in the Address Group editor in the webapp.
        /// </summary>
        public String Name;
        /// <summary>
        /// A collection of the AddressIds which belong to the Addresses in this Address Group. Addresses can be added or removed from the group by adding or removing their Ids from this List, then Updating the Address Group.
        /// </summary>
        public Set<Long> AddressIds = new HashSet<Long>();
        public AddressGroup() { }
        public AddressGroup(long AccountId, long AddressGroupId, String Name, Set<Long> AddressIds)
        {
            this.AccountId=AccountId;
            this.AddressGroupId=AddressGroupId;
            this.Name = Name;
            if (AddressIds == null)
                this.AddressIds = new HashSet<Long>();
            else this.AddressIds = AddressIds;
        }
    }
