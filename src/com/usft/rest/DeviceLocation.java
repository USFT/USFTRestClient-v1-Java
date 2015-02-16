package com.usft.rest;
import org.joda.time.*;
import org.joda.time.format.*;

/**
 * Created with IntelliJ IDEA.
 * User: krudy
 * Date: 11/20/13
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */ /// <summary>
    /// This class represents the location of a Device
    /// </summary>
    public class DeviceLocation
    {
        /// <summary>
        /// The AccountId associated with the Device
        /// </summary>
        public long AccountId;
        /// <summary>
        /// The DeviceId associated with the Device
        /// </summary>
        public long DeviceId;
        /// <summary>
        /// The name of the Device at the time of the location transmission
        /// </summary>
        public String DeviceName;
        /// <summary>
        /// The Latitude of the Device at the time of the location transmission
        /// </summary>
        public double Latitude;
        /// <summary>
        /// The Longitude of the Device at the time of the location transmission
        /// </summary>
        public double Longitude;
        /// <summary>
        /// The Heading of the Device at the time of the location transmission, in degrees
        /// </summary>
        public int Heading;
        /// <summary>
        /// The Velocity of the Device at the time of the location transmission, in MPH
        /// </summary>
        public int Velocity;
        /// <summary>
        /// The number of satellites available to the Device at the time of transmission. Most Devices will only transmit when there are at least four.
        /// </summary>
        public int Satellites;
        /// <summary>
        /// The Ignition state of the Device at the time of transmission
        /// </summary>
        public int Ignition;
        /// <summary>
        /// The last time the Device had detectably moved, at the time of the location transmission
        /// </summary>
        public DateTime LastMoved;
        /// <summary>
        /// The last time the Device had sent an update status, at the time of the location transmission. This will almost always be the time of the location transmission.
        /// </summary>
        public DateTime LastUpdated;
        public int OutputFlags;
        public DeviceLocation() { }
        public DeviceLocation(long AccountId
                , long DeviceId
                , String DeviceName
                , double Latitude
                , double Longitude
                , int Heading
                , int Velocity
                , int Satellites
                , int Ignition
                , DateTime LastMoved
                , DateTime LastUpdated
                , int OutputFlags)
        {
            this.AccountId = AccountId;
            this.DeviceId = DeviceId;
            this.DeviceName = DeviceName;
            this.Latitude = Latitude;
            this.Longitude = Longitude;
            this.Heading = Heading;
            this.Velocity = Velocity;
            this.Satellites = Satellites;
            this.Ignition = Ignition;
            this.LastMoved = LastMoved;
            this.LastUpdated = LastUpdated;
            this.OutputFlags = OutputFlags;
        }

    }
