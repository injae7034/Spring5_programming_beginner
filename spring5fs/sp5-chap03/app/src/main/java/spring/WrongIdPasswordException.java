package spring;

public class WrongIdPasswordException extends RuntimeException {

    public WrongIdPasswordException(String message) {
        super(message);
    }
}
