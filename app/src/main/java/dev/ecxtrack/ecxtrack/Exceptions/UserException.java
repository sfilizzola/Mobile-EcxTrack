package dev.ecxtrack.ecxtrack.Exceptions;

/**
 * Created by Samuel on 13/08/2014.
 */
public class UserException extends Exception {

    public UserException (){

    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Exception inner) {
        super(message, inner);
    }

}
