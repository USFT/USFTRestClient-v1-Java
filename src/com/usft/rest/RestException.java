package com.usft.rest;

/**
 * Created with IntelliJ IDEA.
 * User: krudy
 * Date: 11/20/13
 * Time: 7:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class RestException extends Exception {

    public RestException(String message)
    {
        super(message);
    }

    public RestException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public RestException(Exception innerException)
    {
        super(innerException.getMessage(), innerException.getCause());
    }

}
