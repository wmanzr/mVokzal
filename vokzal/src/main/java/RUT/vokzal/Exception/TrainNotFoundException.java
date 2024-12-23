package RUT.vokzal.Exception;
public class TrainNotFoundException extends RuntimeException {
    public TrainNotFoundException(int trainId) {
        super("Поезд с  ID '" + trainId + "' не существует");
    }
}