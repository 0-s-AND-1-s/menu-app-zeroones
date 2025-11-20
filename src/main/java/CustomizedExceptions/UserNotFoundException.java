package CustomizedExceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userName) {
        super(userName + " not found");
    }
}
