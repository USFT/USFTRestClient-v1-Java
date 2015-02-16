package com.usft.rest;

/**
 * Created with IntelliJ IDEA.
 * User: krudy
 * Date: 11/20/13
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */ /// <summary>
    /// This class represents Address Fences, optional extensions of Addresses that automatically create and move Geofence Alerts centered on Addresses.
    /// </summary>
    public class AddressFence
    {
        /// <summary>
        /// A unique identifier for the Address Fence. Use this when making further API calls specific to this Address Fence.
        /// </summary>
        public long AddressFenceId;
        /// <summary>
        /// The AccountId associated with this Address Fence.
        /// </summary>
        public long AccountId;
        /// <summary>
        /// The Address for this Address Fence. This must be set when creating a new Address Fence.
        /// </summary>
        public long AddressId;
        /// <summary>
        /// The AlertId for the Alert created by this Address Fence. This should not be modified.
        /// </summary>
        public long AlertId;
        /// <summary>
        /// The shape the Address Fence will generate when created, or when the Address is moved.
        /// </summary>
        public FenceTypes FenceType;
        /// <summary>
        /// The dimensions of the Address Fence, in the format "[Number]" for circles and squares, or "[Number]x[Number]" for rectangles.
        /// </summary>
        public String FenceDims;
        /// <summary>
        /// The unit to be used when interpreting FenceDims.
        /// </summary>
        public FenceUnitTypes FenceUnits;
        public AddressFence() { }
        public AddressFence(long AccountId, long AddressFenceId, long AddressId, long AlertId, FenceTypes FenceType, String FenceDims, FenceUnitTypes FenceUnits)
        {
            this.AccountId = AccountId;
            this.AddressFenceId = AddressFenceId;
            this.AddressId = AddressId;
            this.AlertId = AlertId;
            this.FenceType = FenceType;
            this.FenceDims = FenceDims;
            this.FenceUnits = FenceUnits;
        }
    }
