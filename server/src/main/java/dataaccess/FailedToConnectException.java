package dataaccess;

public class FailedToConnectException extends DataAccessException {
    public FailedToConnectException(String message) {
        super(message);
    }
}
