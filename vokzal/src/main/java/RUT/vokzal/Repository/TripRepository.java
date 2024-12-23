package RUT.vokzal.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import RUT.vokzal.Model.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TripRepository {
    void create(Trip trip);
    Trip findById(Integer id);
    Trip update(Trip trip);
    List<Trip> findAll();
    List<Trip> findUpcomingTripsWithTimeByStation(int stationId, LocalDate nowDate, LocalTime nowTime);
    List<Trip> findCanceledTrips(String vokzalName, LocalDate nowDate);
    List<Trip> findAlternativeTrips(int routeId, LocalDate nowDate);
    List<Trip> findTop5Trips();
    Page<Trip> findAll(Pageable pageable);
    Page<Trip> findByStatusContainingIgnoreCase(String searchTerm, Pageable pageable);
}