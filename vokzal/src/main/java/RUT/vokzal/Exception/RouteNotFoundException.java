package RUT.vokzal.Exception;
public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(int routeId) {
        super("Маршрут с  ID '" + routeId + "' не существует");
    }
}