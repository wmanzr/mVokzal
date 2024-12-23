package RUT.vokzal.Exception;
public class VokzalNotFoundException extends RuntimeException {
    public VokzalNotFoundException(int vokzalId) {
        super("Вокзал с  ID '" + vokzalId + "' не существует");
    }
}