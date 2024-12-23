package RUT.vokzal.Exception;

public class UserNameIsPresentException extends RuntimeException {
    public UserNameIsPresentException(String username) {
        super("Имя '" + username + "' уже существует");
    }
}