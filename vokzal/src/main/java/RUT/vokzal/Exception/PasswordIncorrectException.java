package RUT.vokzal.Exception;

public class PasswordIncorrectException extends RuntimeException {
    public PasswordIncorrectException() {
        super("Пароли не совпадают!");
    }
}