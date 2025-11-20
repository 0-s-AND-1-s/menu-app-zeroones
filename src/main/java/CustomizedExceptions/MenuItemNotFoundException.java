package CustomizedExceptions;

public class MenuItemNotFoundException extends RuntimeException {
    public MenuItemNotFoundException(Integer itemId) {
        super("MenuItem is not available for the given id " + itemId);
    }
}
