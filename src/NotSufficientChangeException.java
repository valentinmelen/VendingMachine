public class NotSufficientChangeException extends RuntimeException{

    String message;

    public NotSufficientChangeException(String name) {
        super(name);
    }

    @Override
    public String getMessage() {
        return message;
    }
}

