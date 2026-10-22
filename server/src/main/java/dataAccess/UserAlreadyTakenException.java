package dataAccess;

public class UserAlreadyTakenException extends RuntimeException {
    public UserAlreadyTakenException(String message) {
        super(message);
    }
}
