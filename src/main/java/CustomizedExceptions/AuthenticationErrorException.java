package CustomizedExceptions;

public class AuthenticationErrorException extends RuntimeException {
    public AuthenticationErrorException(String msg) {
        super(msg);
    }
}
