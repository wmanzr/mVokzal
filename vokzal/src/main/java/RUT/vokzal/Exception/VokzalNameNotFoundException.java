package RUT.vokzal.Exception;
public class VokzalNameNotFoundException extends RuntimeException {
    public VokzalNameNotFoundException(String vokzalName) {
        super("Вокзал с именем '" + vokzalName + "' не существует");
    }
}