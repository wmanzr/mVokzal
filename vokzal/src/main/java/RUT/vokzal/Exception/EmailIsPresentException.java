package RUT.vokzal.Exception;

public class EmailIsPresentException extends RuntimeException {
    public EmailIsPresentException(String email) {
        super("Почта '" + email + "' уже существует");
    }
}