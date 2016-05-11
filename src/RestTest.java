/**
 * Created with IntelliJ IDEA.
 * User: krudy
 * Date: 11/20/13
 * Time: 1:27 PM
 * To change this template use File | Settings | File Templates.
 */
import com.usft.rest.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import org.joda.time.DateTime;

public class RestTest {
    // change these values to your UserId and API Key
    private final static String MyUserId = "YourUserIdHere";
    private final static String MyAPIKey =  "YourAPIKeyHere";
    private final static String BaseURL = "https://api.usft.com/v1/";

    // change this function to alter how the program displays output
    private static void Print(String what)
    {
        System.out.println(what);
    }

    public static void main( String [] arguments)
    {
        // the basic use pattern is as simple as this: Create a RestClient...
        RestClient client = new RestClient(MyUserId, MyAPIKey, BaseURL);

        // ... prepare for RestExceptions...
        try
        {
            // ... and make use of the methods.
            String d = client.ServerTime();
            Print(d);
        }
        catch(RestException e)
        {
            Print(e);
        }

        // An exception is TestConnection(), which eats various exceptions and returns a simple true/false.
        // At a later point, I intend to extend RestClient to capture and report more detailed information
        // about failed calls. The server provides more information than a simple HTTP200 / 401 / 403 / 404.
        Print(".TestConnection() " + (client.TestConnection() ? "Succeeded!" : "Failed!"));

        try
        {
            DeviceLocation ret[] = client.GetDeviceLocations();
            Print("Found " + ret.length);
            for(int i =0;i<ret.length;i++)
                Print("Last Updated: " + org.joda.time.format.ISODateTimeFormat.dateTime().print(ret[i].LastUpdated));
            return;
        }
        catch(RestException e)
        {
            Print(e);
        }

    }

    private static void Print(Throwable what, int level)
    {
        Print("Exception: " + what.getMessage(), level);
        final StackTraceElement [] ste = what.getStackTrace();
        if(ste.length > 0)
        {
            Print("\tStack Trace:", level);
            for(int i=0;i<ste.length;i++)
                Print("\t" + ste[i].toString(), level);
        }
        if(what.getCause() != null)
            Print(what.getCause(), level+1);
    }

    private static void Print(Throwable what)
    {
        Print(what, 0);
    }

    private static void Print(String what, int level)
    {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<level;i++) sb.append('\t');
        sb.append(what);
        Print(sb.toString());
    }

}
