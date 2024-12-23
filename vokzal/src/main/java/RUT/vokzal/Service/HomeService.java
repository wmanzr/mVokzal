package RUT.vokzal.Service;

import RUT.vokzal.DTO.TripDTO;
import java.util.List;

public interface HomeService {
    List<TripDTO> getUpcomingTripsWithTimeByStationName(String vokzalName);
    int getStationLoadByDayAndTime(String vokzalName);
    List<TripDTO> getAlternativeTripsIfCanceled(String vokzalName);
    List<TripDTO> getCanceledTrips (String vokzalName);
}