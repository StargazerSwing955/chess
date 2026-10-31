package exceptions;

public class FailedToConnectException extends DataAccessException {
    public FailedToConnectException(String message) {
        super(message);
    }
}
