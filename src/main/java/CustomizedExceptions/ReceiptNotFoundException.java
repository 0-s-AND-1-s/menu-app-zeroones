package CustomizedExceptions;

public class ReceiptNotFoundException extends RuntimeException {
    public ReceiptNotFoundException(String msg) {
        super(msg);
    }
}
