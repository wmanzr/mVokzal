package RUT.vokzal.Service;

import java.util.List;
import RUT.vokzal.DTO.TripDTO;

public interface TopTripService {
    List<TripDTO> getTop5Trips();
}