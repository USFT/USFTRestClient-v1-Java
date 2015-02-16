/**
 * Created with IntelliJ IDEA.
 * User: krudy
 * Date: 11/18/13
 * Time: 8:57 AM
 * To change this template use File | Settings | File Templates.
 */
package com.usft.rest;

import org.joda.time.DateTime;
import org.joda.time.format.*;
import javax.crypto.spec.SecretKeySpec;
import java.net.*;
import java.io.*;
import java.util.*;
import java.security.*;
import java.text.*;
import javax.crypto.Mac;
import java.lang.reflect.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

public class RestClient {
    private static final String auth_Prefix = "USFT ";
    private static final String hashDataPrefix_default = "USFTRESTv1";

    //region HTTP/URL information
    // this standard was adopted from microsoft live, as detailed inhttp://msdn.microsoft.com/en-us/library/live/hh243648.aspx#http_verbs
    private static final String verb_Create = "POST";
    private static final String verb_Read = "GET";
    private static final String verb_Update = "PUT";
    private static final String verb_Delete = "DELETE";

    private static final String uriBase_default = "http://beta.usft.com/api/v1/";

    private static final String uriPath_Test = "/Test";

    private static final String uriPath_ReadDevice = "/Device/%d";
    private static final String uriPath_UpdateDevice = "/Device";
    private static final String uriPath_ReadAllDevices = "/Device";
    private static final String uriPath_CreateDevice = "/Device";

    private static final String uriPath_ReadLocationData = "/Location/%d";
    private static final String uriPath_ReadAllLocationData = "/Location";
    private static final String uriPath_UpdateLocationData = "/Location";

    private static final String uriPath_ReadAddress = "/Address/%d";
    private static final String uriPath_ReadAllAddresses = "/Address";
    private static final String uriPath_ReadAddressesByGroup = "/Address?f=bygroup&fid=%d";
    private static final String uriPath_UpdateAddress = "/Address/%d";
    private static final String uriPath_CreateAddress = "/Address";
    private static final String uriPath_DeleteAddress = "/Address/%d";

    private static final String uriPath_ReadAddressGroup = "/AddressGroup/%d";
    private static final String uriPath_ReadAllAddressGroups = "/AddressGroup";
    private static final String uriPath_UpdateAddressGroup = "/AddressGroup/%d";
    private static final String uriPath_CreateAddressGroup = "/AddressGroup";
    private static final String uriPath_DeleteAddressGroup = "/AddressGroup/%d";

    private static final String uriPath_CreateAddressFence = "/AddressFence";
    private static final String uriPath_DeleteAddressFence = "/AddressFence/%d";
    private static final String uriPath_ReadAddressFence = "/AddressFence/%d";
    private static final String uriPath_ReadAddressFences = "/AddressFence";
    private static final String uriPath_ReadAddressFencesParams = "/AddressFence?addressid=%d&alertid=%d";
    private static final String uriPath_UpdateAddressFence = "/AddressFence";

    private static final String uriPath_CreateAlert = "/Alert";
    private static final String uriPath_DeleteAlert = "/Alert/%d";
    private static final String uriPath_ReadAlert = "/Alert/%d";
    private static final String uriPath_ReadAlertByType = "/Alert?alerttype=%s";
    private static final String uriPath_ReadAlerts = "/Alert";
    private static final String uriPath_UpdateAlert = "/Alert";

    private static final String uriPath_ImmediateReport = "/ImmediateReport/%s";
    private static final String uriPath_ImmediateReportFromTo = "/ImmediateReport/historyfromto?from=%s&to=%s&interval=%s";

    private static final String uriPath_CreateDispatch = "/Dispatch";
    private static final String uriPath_ReadDispatch = "/Dispatch/%d";
    private static final String uriPath_ReadDispatches = "/Dispatch";

    private static final String uriPath_SendDeviceMessage = "/DeviceMessage";
    private static final String uriPath_ReadDeviceMessage = "/DeviceMessage/%d";
    private static final String uriPath_ReadDeviceMessages = "/DeviceMessage";
    private static final String uriPath_ReadDeviceMessagesFiltered = "/DeviceMessage?type=%s&status=%s";

    private static final String uriPath_CreateServiceCall = "/ServiceCall";
    private static final String uriPath_DeleteServiceCall = "/ServiceCall/%d";
    private static final String uriPath_ReadServiceCall = "/ServiceCall/%d";
    private static final String uriPath_ReadServiceCalls = "/ServiceCall";
    private static final String uriPath_ReadServiceCallsByStatus = "/ServiceCall?status=%s";
    private static final String uriPath_UpdateServiceCall = "/ServiceCall";

    private static final String uriPath_ReadTime = "/Time";

    private static final String uriPath_ReadAccount = "/Account/%d";
    private static final String uriPath_ReadAccounts = "/Account";

    private static final String uriAppend_Account = "?account=%d";
    private static final String uriAppend_AccountAdditional = "&account=%d";
    //endregion

    //region ClassTokens
    final private Type DeviceArray = new TypeToken<Device[]>(){}.getType();
    final private Type LocationArray = new TypeToken<DeviceLocation[]>(){}.getType();
    final private Type AddressArray = new TypeToken<Address[]>(){}.getType();
    final private Type AddressGroupArray = new TypeToken<AddressGroup[]>(){}.getType();
    final private Type AddressFenceArray = new TypeToken<AddressFence[]>(){}.getType();
    final private Type AlertArray = new TypeToken<Alert>(){}.getType();
    final private Type DeviceMessageArray = new TypeToken<DeviceMessage[]>(){}.getType();
    final private Type DispatchArray = new TypeToken<Dispatch[]>(){}.getType();
    final private Type ServiceCallArray = new TypeToken<ServiceCall[]>(){}.getType();
    final private Type AccountArray = new TypeToken<Account[]>(){}.getType();
    //endregion

    //region Protected variables
    protected String uriBase = uriBase_default;
    protected String userName = null;
    protected String privateKey = null;
    protected DateTimeFormatter formatter = ISODateTimeFormat.dateTime();

    /// <summary>
    /// Prefix for building the hash
    /// </summary>
    protected String HashDataPrefix = hashDataPrefix_default;
    //endregion
    //region Constructors, etc.
    public RestClient(String UserName, String PrivateKey)
    {
        userName = UserName;
        privateKey = PrivateKey;
    }

    /// <summary>
    /// Creates a new instance of the USFT Rest Client.
    /// </summary>
    /// <param name="UserName">User Name for your Account</param>
    /// <param name="privateKey">Private Token for your Account</param>
    /// <param name="UriBase">Base URI for the service</param>
    public RestClient(String UserName, String PrivateKey, String UriBase)
    {
        userName = UserName;
        privateKey = PrivateKey;
        SetUriBase(UriBase);
    }

    public void SetUriBase(String NewUriBase)
    {
        uriBase = NewUriBase;
        while (uriBase.endsWith("/"))
            uriBase = uriBase.substring(0, uriBase.length() - 1);
    }
    //endregion
    //region Security class

    protected static class Security
    {
        private static final char[] hexCharacters = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

        private static String HexString(byte [] data)
        {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < data.length; i++)
            {
                sb.append(hexCharacters[(data[i] & 0xF0) >> 4]);
                sb.append(hexCharacters[data[i] & 0x0F]);
            }
            return sb.toString();
        }

        public static String GenerateHash(String apiKey, String data)
                throws RestException
        {
            String result;
            try {
                byte [] bKey = apiKey.getBytes();
                final SecretKeySpec secretKey = new SecretKeySpec( bKey, "HmacSHA512" );
                Mac mac = Mac.getInstance("HmacSHA512");
                mac.init( secretKey );
                final byte [] macData = mac.doFinal(data.getBytes());
                result = HexString(macData);
            }
            catch ( final NoSuchAlgorithmException e )
            {
                throw new RestException(e);
            }
            catch ( final InvalidKeyException e )
            {
                throw new RestException(e);
            }
            return result;
        }
    }
    //endregion

    //region Private methods
    private boolean isNullOrEmpty(String totest)
    {
        return totest == null || (totest.length() == 0);
    }

    private String AppendAccount(Account AsAccount)
    {
        return AppendAccount(AsAccount, false);
    }


    private String AppendAccount(Account AsAccount, boolean IsAdditional)
    {
        if(AsAccount == null)
            return "";
        else if(IsAdditional)
            return String.format(uriAppend_AccountAdditional, AsAccount.AccountId);
        else return String.format(uriAppend_Account, AsAccount.AccountId);
    }

    private String FormatDateRFC1123(Date toFormat)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(toFormat);
    }

    private <ReturnType> ReturnType RetrieveResponse(Class<ReturnType> TargetClass, String Path, String Method)
            throws RestException
    {
        return RetrieveResponse(TargetClass, Path, Method, null, true);
    }
    private <ReturnType> ReturnType RetrieveResponse(Class<ReturnType> TargetClass, String Path, String Method, Object BodyContent)
            throws RestException
    {
        return RetrieveResponse(TargetClass, Path, Method, BodyContent, true);
    }
    private <ReturnType> ReturnType RetrieveResponse(Class<ReturnType> TargetClass, String Path, String Method, Object BodyContent, boolean DoAuthentication)
            throws RestException
    {
        InputStream resp = AttemptRequest(Path, Method, BodyContent, DoAuthentication);
        InputStreamReader reader = new InputStreamReader(resp);
        Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();
        return gson.fromJson(reader, TargetClass);
    }

    private <ReturnType> ReturnType RetrieveResponse(Type TargetClass, String Path, String Method)
            throws RestException
    {
        return RetrieveResponse(TargetClass, Path, Method, null, true);
    }
    private <ReturnType> ReturnType RetrieveResponse(Type TargetClass, String Path, String Method, Object BodyContent)
            throws RestException
    {
        return RetrieveResponse(TargetClass, Path, Method, BodyContent, true);
    }
    private <ReturnType> ReturnType RetrieveResponse(Type TargetClass, String Path, String Method, Object BodyContent, boolean DoAuthentication)
            throws RestException
    {
        InputStream resp = AttemptRequest(Path, Method, BodyContent, DoAuthentication);
        InputStreamReader reader = new InputStreamReader(resp);
        Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();
        return gson.fromJson(reader, TargetClass);
    }

    private InputStream AttemptRequest(String Path, String Method)
            throws RestException
    { return AttemptRequest(Path, Method, null, true); }

    private InputStream AttemptRequest(String Path, String Method, Object BodyContent)
            throws RestException
    { return AttemptRequest(Path, Method, BodyContent, true); }

    private InputStream AttemptRequest(String Path, String Method, Object BodyContent, boolean DoAthentication)
            throws RestException
    {

        final String address = uriBase + Path;

        URL url;
        HttpURLConnection conn;

        try {
            url = new URL(address);
        }
        catch (MalformedURLException e) {
            throw new RestException(e);
        }

        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(Method);
            conn.setUseCaches(false);
            conn.setDoInput(true);

            if(DoAthentication)
            {
                final Date d = new Date();
                final String formattedDate = FormatDateRFC1123(d);
                conn.addRequestProperty("Date", formattedDate);
                final String hash = Security.GenerateHash(privateKey, HashDataPrefix + url.getPath() + conn.getRequestProperty("Date"));
                final String AuthString = auth_Prefix + userName + ":" + hash;
                conn.setRequestProperty("Authorization", AuthString);
            }
            if(BodyContent != null)
            {
                conn.setDoOutput(true);
                Gson gson = new Gson();
                final String contentString = gson.toJson(BodyContent);
                final byte[] contentBytes = contentString.getBytes();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Content-Length", String.valueOf(contentBytes.length));
                DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
                outputStream.write(contentBytes);
                outputStream.flush();
                outputStream.close();
            }
            else
                conn.setDoOutput(false);

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                return conn.getInputStream();
            }
            else throw new RestException("Request to " + url.getPath() + " failed. Server returned: " + String.valueOf(conn.getResponseCode()));
        }
        catch(IOException e)
        {
            throw new RestException(e);
        }
    }
    //endregion

    //region Utility Methods
    public boolean TestConnection()
    {
        return TestConnection(null);
    }

    public boolean TestConnection(Account AsAccount)
    {
        try {
            AttemptRequest(uriPath_Test + AppendAccount(AsAccount), verb_Read);
        }
        catch(RestException e)
        {
            return false;
        }
        return true;
    }

    public String ServerTime()
            throws RestException
    {
        return RetrieveResponse(String.class, uriPath_ReadTime, verb_Read, null, false);
    }

    //endregion

    //region Devices

    public Device GetDevice(long Id)
            throws RestException
    {
        return GetDevice(Id, null);
    }

    /// <summary>
    /// Gets information for a single device from USFT
    /// </summary>
    /// <param name="Id">ID of the device</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>The requested Device</returns>
    /// <exception cref="RestException">Contains details of an unsuccessful request</exception>
    public Device GetDevice(long Id, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(Device.class, String.format(uriPath_ReadDevice, Id) + AppendAccount(AsAccount), verb_Read);
    }

    public Device [] GetDevices()
            throws RestException
    {   return GetDevices(null); }

    /// <summary>
    /// Gets information for all devices viewable by your account
    /// </summary>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>The list of all devices viewable by your account</returns>
    /// <    exception cref="RestException">Contains details of an unsuccessful request</exception>
    public Device [] GetDevices(Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(DeviceArray, uriPath_ReadAllDevices + AppendAccount(AsAccount), verb_Read);
    }

    public Device UpdateDevice(Device DeviceToUpdate)
            throws RestException
    {
        return UpdateDevice(DeviceToUpdate, null);
    }

    /// <summary>
    /// Update a device's entry with new information.
    /// </summary>
    /// <param name="DeviceToUpdate">A device entry with modified name, flag color, or text color. All other changes will be ignored.</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>The device's entry, as of the most recent update.</returns>
    /// <exception cref="RestException">Contains details of an unsuccessful request</exception>
    public Device UpdateDevice(Device DeviceToUpdate, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(Device.class, uriPath_UpdateDevice + AppendAccount(AsAccount), verb_Update, DeviceToUpdate);
    }
    //endregion

    public Device CreateDevice(Device DeviceToCreate, Account AsAccount)
        throws RestException
    {
        return RetrieveResponse(Device.class, uriPath_CreateDevice + AppendAccount(AsAccount), verb_Create, DeviceToCreate);
    }

    //region Device Location


    public DeviceLocation [] GetDeviceLocations()
            throws RestException
    {
        return GetDeviceLocations(null);
    }

    /// <summary>
    /// Get the latest location update from all of your account's gps devices
    /// </summary>
    /// <returns>A list of device ids and their most recent gps information</returns>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <exception cref="RestException">Contains details of an unsuccessful request</exception>
    public DeviceLocation [] GetDeviceLocations(Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(LocationArray, uriPath_ReadAllLocationData + AppendAccount(AsAccount), verb_Read);
    }

    public boolean UpdateDeviceLocation(long Id, DeviceLocation UpdatedLocation)
            throws RestException
    {
        // right now, the serialization for these fields is not behaving
        UpdatedLocation.LastUpdated = null;
        UpdatedLocation.LastMoved = null;
        return RetrieveResponse(boolean.class, uriPath_UpdateLocationData, verb_Update, UpdatedLocation);
    }


    public DeviceLocation GetDeviceLocation(long Id)
            throws RestException
    {
        return GetDeviceLocation(Id, null);
    }
    /// <summary>
    /// Get the latest location update for a given Device Id
    /// </summary>
    /// <param name="Id">The Id of the Device to query for location data</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>The latest gps information for the specified Device</returns>
    /// <exception cref="RestException">Contains details of an unsuccessful request</exception>
    public DeviceLocation GetDeviceLocation(long Id, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(DeviceLocation.class, String.format(uriPath_ReadLocationData, Id) + AppendAccount(AsAccount), verb_Read);
    }

    //endregion

    //region Addresses

    public Address [] GetAddresses()
            throws RestException
    {
        return GetAddresses(null);
    }


    /// <summary>
    /// Retrieves all addresses viewable by your account
    /// </summary>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>A List of Address entries viewable by your account</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public Address [] GetAddresses(Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(AddressArray, uriPath_ReadAllAddresses + AppendAccount(AsAccount), verb_Read);
    }

    public Address [] GetAddressesByGroup(long Id)
            throws RestException
    {
        return GetAddressesByGroup(Id, null);
    }

    /// <summary>
    /// Retrives all addresses associated with a certain Address Group
    /// </summary>
    /// <param name="Id">Id of the Address Group to search for Addresses</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>A List of Address entries associated with the Address Group</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public Address [] GetAddressesByGroup(long Id, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(AddressArray, String.format(uriPath_ReadAddressesByGroup, Id) + AppendAccount(AsAccount, true), verb_Read);
    }

    public Address [] GetAddressesByGroup(AddressGroup Group)
            throws RestException
    {
        return GetAddressesByGroup(Group, null);
    }
    /// <summary>
    /// Retrives all addresses associated with a certain Address Group
    /// </summary>
    /// <param name="Id">The Address Group to search for Addresses</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>A List of Address entries associated with the Address Group</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public Address [] GetAddressesByGroup(AddressGroup Group, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(AddressArray, String.format(uriPath_ReadAddressesByGroup, Group.AddressGroupId) + AppendAccount(AsAccount, true), verb_Read);
    }

    public Address GetAddress(long Id)
            throws RestException
    {
        return GetAddress(Id, null);
    }
    /// <summary>
    /// Retrieves information about a single Address
    /// </summary>
    /// <param name="Id">The Id of the Address to retrieve</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>The Address requested</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public Address GetAddress(long Id, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(Address.class, String.format(uriPath_ReadAddress, Id) + AppendAccount(AsAccount), verb_Read);
    }

    public Address CreateAddress(Address ToCreate)
            throws RestException{
        return CreateAddress(ToCreate, null);
    }
    /// <summary>
    /// Creates a new Address entry for your account
    /// </summary>
    /// <param name="ToCreate">The Address object to create. AddressId should be set to 0, which is its default</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>The newly created Address object as it exists in the database</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public Address CreateAddress(Address ToCreate, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(Address.class, uriPath_CreateAddress + AppendAccount(AsAccount), verb_Create, ToCreate);
    }

    public Address UpdateAddress(Address ToUpdate)
            throws RestException
    {
        return UpdateAddress(ToUpdate, null);
    }
    /// <summary>
    /// Updates an existing Address with new information
    /// </summary>
    /// <param name="ToUpdate">The Address object to update, with its new modifications</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>The Address object as it exists in the database, post-update</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public Address UpdateAddress(Address ToUpdate, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(Address.class, String.format(uriPath_UpdateAddress, ToUpdate.AddressId) + AppendAccount(AsAccount), verb_Update, ToUpdate);
    }

    public boolean DeleteAddress(long Id)
            throws RestException
    {
        return DeleteAddress(Id, null);
    }
    /// <summary>
    /// Deletes an existing Address from your account
    /// </summary>
    /// <param name="ToDelete">The Id of the Address to delete from your account</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>True if the deletion succeeded, False if it failed</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public boolean DeleteAddress(long Id, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(boolean.class, String.format(uriPath_DeleteAddress, Id) + AppendAccount(AsAccount), verb_Delete);
    }


    public boolean DeleteAddress(Address ToDelete)
            throws RestException
    {
        return DeleteAddress(ToDelete, null);
    }
    /// <summary>
    /// Deletes an existing Address from your account
    /// </summary>
    /// <param name="ToDelete">The Address to delete from your account</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>True if the deletion succeeded, False if it failed</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public boolean DeleteAddress(Address ToDelete, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(boolean.class, String.format(uriPath_DeleteAddress, ToDelete.AddressId) + AppendAccount(AsAccount), verb_Delete);
    }
    //endregion

    //region Address Groups

    public AddressGroup [] GetAddressGroups()
            throws RestException
    {
        return GetAddressGroups(null);
    }

    /// <summary>
    /// Retrieves the list of Address Groups from your account
    /// </summary>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>The list of Address Groups viewable by your account</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public AddressGroup [] GetAddressGroups( Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(AddressGroupArray, uriPath_ReadAllAddressGroups + AppendAccount(AsAccount), verb_Read);
    }

    public AddressGroup GetAddressGroup(long Id)
            throws RestException
    { return GetAddressGroup(Id, null); }

    /// <summary>
    /// Retrieves a specific Address Group from your account
    /// </summary>
    /// <param name="Id">Id of the Address Group to retrieve</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>The requested Address Group</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public AddressGroup GetAddressGroup(long Id,Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(AddressGroup.class, String.format(uriPath_ReadAddressGroup, Id) + AppendAccount(AsAccount), verb_Read);
    }

    public AddressGroup UpdateAddressGroup(AddressGroup ToUpdate)
            throws RestException
    {
        return UpdateAddressGroup(ToUpdate, null);
    }

    /// <summary>
    /// Updates an existing Address Group in your account
    /// </summary>
    /// <param name="ToUpdate">The Address Group to update, with its new modifications</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>The updated Address Group, as it exists in the database</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public AddressGroup UpdateAddressGroup(AddressGroup ToUpdate, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(AddressGroup.class, String.format(uriPath_UpdateAddressGroup, ToUpdate.AddressGroupId) + AppendAccount(AsAccount), verb_Update, ToUpdate);
    }

    public AddressGroup CreateAddressGroup(AddressGroup ToCreate)
            throws RestException
    {
        return CreateAddressGroup(ToCreate, null);
    }
    /// <summary>
    /// Creates a new Address Group in your account
    /// </summary>
    /// <param name="ToCreate">The Address Group to create in your account. AddressGroupId should be 0, which is its default</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>The new Address Group object as it exists in the database</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public AddressGroup CreateAddressGroup(AddressGroup ToCreate, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(AddressGroup.class, uriPath_CreateAddressGroup + AppendAccount(AsAccount), verb_Create, ToCreate);
    }
    public boolean DeleteAddressGroup(AddressGroup ToDelete)
            throws RestException
    {
        return DeleteAddressGroup(ToDelete.AddressGroupId, null);
    }
    public boolean DeleteAddressGroup(long AddressGroupId)
            throws RestException
    {
        return DeleteAddressGroup(AddressGroupId, null);
    }
    /// <summary>
    /// Removes an existing Address Group from your account
    /// </summary>
    /// <param name="ToDelete">The Address Group to remove</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>True if the deletion succeeded, false if it failed</returns>
    public boolean DeleteAddressGroup(AddressGroup ToDelete, Account AsAccount)
            throws RestException
    {
        return DeleteAddressGroup(ToDelete.AddressGroupId, AsAccount);
    }

    /// <summary>
    /// Removes an existing Address Group from your account
    /// </summary>
    /// <param name="AddressGroupId">The Id of the Address Group to remove</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>True if the deletion succeeded, false if it failed</returns>
    public boolean DeleteAddressGroup(long AddressGroupId, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(boolean.class, String.format(uriPath_DeleteAddressGroup, AddressGroupId) + AppendAccount(AsAccount), verb_Delete);
    }
    //endregion

    //region Address Fences

    public AddressFence [] GetAddressFences()
            throws RestException
    {
        return GetAddressFences(0,0, null);
    }

    public AddressFence [] GetAddressFences(long AddressId)
            throws RestException
    {
        return GetAddressFences(AddressId, 0, null);
    }

    public AddressFence [] GetAddressFences(long AddressId, long AlertId)
            throws RestException
    {
        return GetAddressFences(AddressId, AlertId, null);
    }

    /// <summary>
    /// Retrieves Address Fences from your account
    /// </summary>
    /// <param name="AddressId">If specified, it filters for Fences associated with a specific Address ID</param>
    /// <param name="AlertId">If specified, it filters for the Fence associated with a specific Alert ID</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>The Address Fences in your account that match any provided parameters</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public AddressFence [] GetAddressFences(long AddressId, long AlertId, Account AsAccount)
            throws RestException
    {
        //strictly speaking, we can always use the bottom request, but why not shave off a few bytes?
        if(AddressId==0 && AlertId==0)
            return RetrieveResponse(AddressFenceArray, uriPath_ReadAddressFences + AppendAccount(AsAccount), verb_Read);
        else
            return RetrieveResponse(AddressFenceArray, String.format(uriPath_ReadAddressFencesParams, AddressId, AlertId) + AppendAccount(AsAccount), verb_Read);
    }

    public AddressFence GetAddressFence(long AddressFenceId)
            throws RestException
    {
        return GetAddressFence(AddressFenceId, null);
    }
    /// <summary>
    /// Retrieves a specific Address Fence from your account
    /// </summary>
    /// <param name="AddressFenceId">ID of the Address Fence to retrieve</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>The Address Fence for the given ID</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public AddressFence GetAddressFence(long AddressFenceId, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(AddressFence.class, String.format(uriPath_ReadAddressFence, AddressFenceId) + AppendAccount(AsAccount), verb_Read);
    }

    public AddressFence UpdateAddressFence(AddressFence ToUpdate)
            throws RestException
    {
        return UpdateAddressFence(ToUpdate, null);
    }
    /// <summary>
    /// Updates an existing Address Fence in your account
    /// </summary>
    /// <param name="ToUpdate">The Address Fence to update, with changes in place</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>The Address Fence after its update</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public AddressFence UpdateAddressFence(AddressFence ToUpdate, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(AddressFence.class, uriPath_UpdateAddressFence + AppendAccount(AsAccount), verb_Update, ToUpdate);
    }

    public AddressFence CreateAddressFence(AddressFence ToCreate)
            throws RestException
    {
        return CreateAddressFence(ToCreate, null);
    }
    /// <summary>
    /// Create a new Address Fence for your account
    /// </summary>
    /// <param name="ToCreate">The Address Fence to create. AddressId must be set to an existing address, AlertId must be set to 0.</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>The new Address Fence created in your account</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public AddressFence CreateAddressFence(AddressFence ToCreate, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(AddressFence.class, uriPath_CreateAddressFence + AppendAccount(AsAccount), verb_Create, ToCreate);
    }

    public boolean DeleteAddressFence(AddressFence ToDelete)
            throws RestException
    {
        return DeleteAddressFence(ToDelete.AddressFenceId, null);
    }
    public boolean DeleteAddressFence(long AddressFenceId)
            throws RestException
    {
        return DeleteAddressFence(AddressFenceId, null);
    }
    /// <summary>
    /// Delete an existing Address Fence in your account
    /// </summary>
    /// <param name="ToDelete">The Address Fence to delete</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>True if the deletion succeeds, false otherwise</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public boolean DeleteAddressFence(AddressFence ToDelete, Account AsAccount)
            throws RestException
    {
        return DeleteAddressFence(ToDelete.AddressFenceId, AsAccount);
    }

    /// <summary>
    /// Delete an existing Address Fence in your account
    /// </summary>
    /// <param name="AddressFenceId">The ID of the Address Fence to delete</param>
    /// <param name="AsAccount">(Optional) If this parameter is included, the method will be run on behalf of the specified child Account.</param>
    /// <returns>True if the deletion succeeds, false otherwise</returns>
    /// <exception cref="RestException">A RestException containing the information returned by the server for an unsuccessful request</exception>
    public boolean DeleteAddressFence(long AddressFenceId, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(boolean.class, String.format(uriPath_DeleteAddressFence, AddressFenceId) + AppendAccount(AsAccount), verb_Delete);
    }
    //endregion

    //region Alerts
    public Alert GetAlert(long AlertId)
            throws RestException
    {
        return GetAlert(AlertId, null);
    }
    public Alert GetAlert(long AlertId, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(Alert.class, String.format(uriPath_ReadAlert, AlertId) + AppendAccount(AsAccount), verb_Read);
    }
    public Alert [] GetAlerts()
            throws RestException
    {
        return GetAlerts(null, null);
    }
    public Alert [] GetAlerts(String alerttype)
            throws RestException
    {
        return GetAlerts(alerttype, null);
    }
    public Alert [] GetAlerts(String alerttype, Account AsAccount)
            throws RestException
    {
        if (isNullOrEmpty(alerttype))
            return RetrieveResponse(AlertArray, uriPath_ReadAlerts + AppendAccount(AsAccount), verb_Read);
        else
        {
            String p;
            try {
                p = URLEncoder.encode(alerttype, "UTF-8");
            } catch(UnsupportedEncodingException e)
            {
                throw new RestException(e);
            }
            return RetrieveResponse(AlertArray, String.format(uriPath_ReadAlertByType, p) + AppendAccount(AsAccount), verb_Read);
        }
    }
    public Alert CreateAlert(Alert ToCreate)
            throws RestException
    {
        return CreateAlert(ToCreate, null);
    }
    public Alert CreateAlert(Alert ToCreate, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(Alert.class, uriPath_CreateAlert + AppendAccount(AsAccount), verb_Create, ToCreate);
    }
    public Alert UpdateAlert(Alert ToUpdate)
            throws RestException
    {
        return UpdateAlert(ToUpdate, null);
    }
    public Alert UpdateAlert(Alert ToUpdate, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(Alert.class, uriPath_UpdateAlert + AppendAccount(AsAccount), verb_Update, ToUpdate);
    }
    public boolean DeleteAlert(Alert ToDelete)
            throws RestException
    {
        return DeleteAlert(ToDelete.AlertId, null);
    }
    public boolean DeleteAlert(Alert ToDelete, Account AsAccount)
            throws RestException
    {
        return DeleteAlert(ToDelete.AlertId, AsAccount);
    }
    public boolean DeleteAlert(long AlertId)
            throws RestException
    {
        return DeleteAlert(AlertId, null);
    }
    public boolean DeleteAlert(long AlertId, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(boolean.class, String.format(uriPath_DeleteAlert, AlertId) + AppendAccount(AsAccount), verb_Delete);
    }
    //endregion

    //region Device Messages
    public DeviceMessage GetDeviceMessage(long DeviceMessageId)
            throws RestException
    {
        return GetDeviceMessage(DeviceMessageId, null);
    }
    public DeviceMessage GetDeviceMessage(long DeviceMessageId, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(DeviceMessage.class, String.format(uriPath_ReadDeviceMessage, DeviceMessageId) + AppendAccount(AsAccount), verb_Read);
    }
    public DeviceMessage [] GetDeviceMessages(MessageTypes MessageType, MessageStatuses MessageStatus)
            throws RestException
    {
        return GetDeviceMessages(MessageType, MessageStatus, null);
    }
    public DeviceMessage [] GetDeviceMessages(MessageTypes MessageType)
            throws RestException
    {
        return GetDeviceMessages(MessageType, null, null);
    }
    public DeviceMessage [] GetDeviceMessages()
            throws RestException
    {
        return GetDeviceMessages(null,null,null);
    }
    public DeviceMessage [] GetDeviceMessages(MessageTypes MessageType, MessageStatuses MessageStatus, Account AsAccount)
            throws RestException
    {
        if(MessageType != null || MessageStatus != null)
            return RetrieveResponse(DeviceMessageArray, String.format(uriPath_ReadDeviceMessagesFiltered, MessageType == null ? "" : String.valueOf(MessageType.GetID()), MessageStatus == null ? "" : String.valueOf(MessageStatus.GetID())) + AppendAccount(AsAccount, true), verb_Read);
        else
            return RetrieveResponse(DeviceMessageArray, uriPath_ReadDeviceMessages + AppendAccount(AsAccount), verb_Read);
    }
    public DeviceMessage SendDeviceMessage(DeviceMessage ToSend)
            throws RestException
    {
        return SendDeviceMessage(ToSend, null);
    }
    public DeviceMessage SendDeviceMessage(DeviceMessage ToSend, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(DeviceMessage.class, uriPath_SendDeviceMessage + AppendAccount(AsAccount), verb_Create, ToSend);
    }
    //endregion

    //region Dispatch

    public Dispatch CreateDispatch(Dispatch ToCreate)
            throws RestException
    {
        return CreateDispatch(ToCreate, null);
    }

    public Dispatch GetDispatch(long DispatchId)
            throws RestException
    {
        return GetDispatch(DispatchId, null);
    }
    public Dispatch [] GetDispatches()
            throws RestException
    {
        return GetDispatches(null);
    }
    public Dispatch GetDispatch(long DispatchId, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(Dispatch.class, String.format(uriPath_ReadDispatch, DispatchId) + AppendAccount(AsAccount), verb_Read);
    }
    public Dispatch [] GetDispatches(Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(DispatchArray, uriPath_ReadDispatches + AppendAccount(AsAccount), verb_Read);
    }
    public Dispatch CreateDispatch(Dispatch ToCreate, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(Dispatch.class, uriPath_CreateDispatch + AppendAccount(AsAccount), verb_Create, ToCreate);
    }
    //endregion

    //region Service Calls
    public ServiceCall GetServiceCall(long ServiceCallId)
            throws RestException
    {
        return GetServiceCall(ServiceCallId, null);
    }
    public ServiceCall GetServiceCall(long ServiceCallId, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(ServiceCall.class, String.format(uriPath_ReadServiceCall, ServiceCallId) + AppendAccount(AsAccount), verb_Read);
    }
    public ServiceCall [] GetServiceCalls()
            throws RestException
    {
        return GetServiceCalls(null,null);
    }
    public ServiceCall [] GetServiceCalls(String status)
            throws RestException
    {
        return GetServiceCalls(status, null);
    }
    public ServiceCall [] GetServiceCalls(String status, Account AsAccount)
            throws RestException
    {
        if(isNullOrEmpty(status))
            return RetrieveResponse(ServiceCallArray, uriPath_ReadServiceCalls + AppendAccount(AsAccount), verb_Read);
        else
        {
            String p;
            try {
                p = URLEncoder.encode(status, "UTF-8");
            } catch (UnsupportedEncodingException e)
            {
                throw new RestException(e);
            }
            return RetrieveResponse(ServiceCallArray, String.format(uriPath_ReadServiceCallsByStatus, p) + AppendAccount(AsAccount, true), verb_Read);
        }
    }
    public ServiceCall CreateServiceCall(ServiceCall ToCreate)
            throws RestException
    {
        return CreateServiceCall(ToCreate, null);
    }
    public ServiceCall CreateServiceCall(ServiceCall ToCreate, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(ServiceCall.class, uriPath_CreateServiceCall + AppendAccount(AsAccount), verb_Create, ToCreate);
    }
    public ServiceCall UpdateServiceCall(ServiceCall ToUpdate)
            throws RestException
    {
        return UpdateServiceCall(ToUpdate, null);
    }
    public ServiceCall UpdateServiceCall(ServiceCall ToUpdate, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(ServiceCall.class, uriPath_UpdateServiceCall + AppendAccount(AsAccount), verb_Update, ToUpdate);
    }
    public boolean DeleteServiceCall(long ServiceCallId)
            throws RestException
    {
        return DeleteServiceCall(ServiceCallId, null);
    }
    public boolean DeleteServiceCall(ServiceCall ToDelete)
            throws RestException
    {
        return DeleteServiceCall(ToDelete.ServiceCallId, null);
    }
    public boolean DeleteServiceCall(long ServiceCallId, Account AsAccount)
            throws RestException
    {
        return RetrieveResponse(boolean.class, String.format(uriPath_DeleteServiceCall, ServiceCallId) + AppendAccount(AsAccount), verb_Delete);
    }
    public boolean DeleteServiceCall(ServiceCall ToDelete, Account AsAccount)
            throws RestException
    {
        return DeleteServiceCall(ToDelete.ServiceCallId, AsAccount);
    }
    //endregion

    //region ImmediateReports

    public DeviceLocation [] GetHistory24(long [] DeviceIds)
            throws RestException
    {
        return GetHistory24(DeviceIds, null);
    }
    public DeviceLocation [] GetHistory24()
            throws RestException
    {
        return GetHistory24(null, null);
    }
    public DeviceLocation [] GetHistory24(long [] DeviceIds, Account AsAccount)
            throws RestException
    {
        if (DeviceIds == null || DeviceIds.length == 0)
            return RetrieveResponse(LocationArray, String.format(uriPath_ImmediateReport, "history24") + AppendAccount(AsAccount), verb_Read);
        else
            return RetrieveResponse(LocationArray, String.format(uriPath_ImmediateReport, "history24") + AppendAccount(AsAccount), "POST", DeviceIds);
    }
    public DeviceLocation [] GetHistoryFromTo(long [] DeviceIds, DateTime From, DateTime To, long Interval)
            throws RestException
    {
        return GetHistoryFromTo(DeviceIds, From, To, Interval, null);
    }
    public DeviceLocation [] GetHistoryFromTo(long [] DeviceIds, DateTime From, DateTime To)
            throws RestException
    {
        return GetHistoryFromTo(DeviceIds, From, To, 0, null);
    }
    public DeviceLocation [] GetHistoryFromTo(long [] DeviceIds, DateTime From)
            throws RestException
    {
        return GetHistoryFromTo(DeviceIds, From, null, 0, null);
    }
    public DeviceLocation [] GetHistoryFromTo(long [] DeviceIds)
            throws RestException
    {
        return GetHistoryFromTo(DeviceIds, null, null, 0, null);
    }
    public DeviceLocation [] GetHistoryFromTo()
            throws RestException
    {
        return GetHistoryFromTo(null, null, null, 0, null);
    }
    public DeviceLocation [] GetHistoryFromTo(long [] devices, DateTime From, DateTime To, long Interval, Account AsAccount)
            throws RestException
    {
         try

         {
        if (devices == null || devices.length == 0)
            return RetrieveResponse(LocationArray, String.format(uriPath_ImmediateReportFromTo, URLEncoder.encode(formatter.print(From), "UTF-8"), URLEncoder.encode(formatter.print(To), "UTF-8"), Interval == 0? "" : String.valueOf(Interval)) + AppendAccount(AsAccount, true), verb_Read);
        else
        {
            return RetrieveResponse(LocationArray, String.format(uriPath_ImmediateReportFromTo, URLEncoder.encode(formatter.print(From), "UTF-8"), URLEncoder.encode(formatter.print(To), "UTF-8"), Interval == 0? "" : String.valueOf(Interval)) + AppendAccount(AsAccount, true), "POST", cheapJoin(devices));
        }
         }
         catch(UnsupportedEncodingException exc)
         {
            //I'm just eating this one, because I don't think UTF-8 is going to be an unknown encoding scheme
         }
         return null;
    }

    private static String cheapJoin(long[] src)
    {
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<src.length;i++)
        {
            builder.append(src[i]);
            if(i < src.length - 1)
                builder.append(",");
        }
        return builder.toString();
    }
    //endregion
    
    //region Accounts
    public Account GetAccount(long AccountId)
            throws RestException
    {
        return RetrieveResponse(Account.class, String.format(uriPath_ReadAccount, AccountId), verb_Read);
    }
    public Account [] GetAccounts()
            throws RestException
    {
        return RetrieveResponse(AccountArray, uriPath_ReadAccounts , verb_Read);
    }
    //endregion
}
