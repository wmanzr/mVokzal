package RUT.vokzal.Exception;

public class DeleteAlreadyException extends RuntimeException {
    public DeleteAlreadyException(int id) {
        super("Объект с '" + id + "' уже удален!");
    }
}