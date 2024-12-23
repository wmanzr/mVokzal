package RUT.vokzal.Exception;
public class PlatformNotFoundException extends RuntimeException {
    public PlatformNotFoundException(int platformId) {
        super("Платформа с  ID '" + platformId + "' не существует");
    }
}