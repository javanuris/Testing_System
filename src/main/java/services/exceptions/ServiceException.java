package services.exceptions;

/**
 * Created by User on 20.07.2017.
 */
public class ServiceException extends Exception {

    public ServiceException(String message, Exception cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }
}