package RUT.vokzal.Service;

import RUT.vokzal.DTO.TripDTO;
import org.springframework.data.domain.Page;
import java.util.List;

public interface TripService {
    Integer createTrip(String dateArr, String dateDep, String statusTrip, int trainId, int routeId, Boolean isDelayed, String delayTime);
    TripDTO getTripById(Integer id);
    void updateTrip(Integer id, String dateArr, String dateDep, String statusTrip, int trainId, int routeId, Boolean isDelayed, String delayTime);
    List<TripDTO> getAllTrips();
    Page<TripDTO> getTrips(String searchTerm, int page, int size);
    void deleteTrip(Integer id);
}