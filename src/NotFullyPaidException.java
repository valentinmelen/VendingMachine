public class NotFullyPaidException extends RuntimeException {

    String message;
    long remaining;

    public NotFullyPaidException(String message, long remaining) {
        this.message = message;
        this.remaining = remaining;
    }

    @Override
    public String getMessage() {
        return message + remaining;
    }

    public long getRemaining() {
        return remaining;
    }
}
