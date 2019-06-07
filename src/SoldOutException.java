public class SoldOutException extends RuntimeException {
    String message;

    public SoldOutException(String name) {
        super(name);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
