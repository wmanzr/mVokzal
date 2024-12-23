package RUT.vokzal.Service.Impl;

import RUT.vokzal.DTO.TripDTO;
import RUT.vokzal.Exception.DeleteAlreadyException;
import RUT.vokzal.Exception.RouteNotFoundException;
import RUT.vokzal.Exception.TrainNotFoundException;
import RUT.vokzal.Model.entity.Route;
import RUT.vokzal.Model.entity.Train;
import RUT.vokzal.Model.entity.Trip;
import RUT.vokzal.Model.enums.StatusTrip;
import RUT.vokzal.Repository.RouteRepository;
import RUT.vokzal.Repository.TrainRepository;
import RUT.vokzal.Repository.TripRepository;
import RUT.vokzal.Service.TripService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableCaching
public class TripServiceImpl implements TripService {

    private TripRepository tripRepository;
    private TrainRepository trainRepository;
    private RouteRepository routeRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setTripRepository(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }
    @Autowired
    public void setTrainRepository(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }
    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Autowired
    public void setRouteRepository(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }


    @Override
    @CacheEvict(cacheNames = "trips", allEntries = true)
    public Integer createTrip(String dateArr, String dateDep, String statusTrip, int trainId, int routeId, Boolean isDelayed, String delayTime) {
        Train train = trainRepository.findById(trainId);
        if (train == null) {
            throw new TrainNotFoundException(trainId);
        }

        Route route = routeRepository.findById(routeId);
        if (route == null) {
            throw new RouteNotFoundException(routeId);
        }

        LocalDate dateArrival = LocalDate.parse(dateArr);
        LocalDate dateDeparture = LocalDate.parse(dateDep);

        LocalTime delay = null;
        if (delayTime != null && !delayTime.isEmpty()) {
            delay = LocalTime.parse(delayTime);
        }
        StatusTrip status = StatusTrip.valueOf(statusTrip.toUpperCase());
        Trip trip = new Trip(dateArrival, dateDeparture, status, train, route, isDelayed, delay);
        tripRepository.create(trip);
        return trip.getId();
    }

    @Override
    public TripDTO getTripById(Integer id) {
        Trip trip = tripRepository.findById(id);
        TripDTO tripDTO = modelMapper.map(trip, TripDTO.class);
        return new TripDTO(tripDTO.getId(), tripDTO.getDateArr(), tripDTO.getDateDep(), tripDTO.getStatusTrip(), tripDTO.getTrainId(),
                tripDTO.getRouteId(), tripDTO.getIsDelayed(), tripDTO.getDelayTime());
    }

    @Override
    @Cacheable("trips")
    public Page<TripDTO> getTrips(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Trip> tripsPage = searchTerm.isEmpty()
                ? tripRepository.findAll(pageable)
                : tripRepository.findByStatusContainingIgnoreCase(searchTerm, pageable);

        return tripsPage.map(trip -> modelMapper.map(trip, TripDTO.class));
    }

    @Override
    @CacheEvict(cacheNames = "trips", allEntries = true)
    public void updateTrip(Integer id, String dateArr, String dateDep, String statusTrip, int trainId, int routeId, Boolean isDelayed, String delayTime) {
        Train train = trainRepository.findById(trainId);
        if (train == null) {
            throw new TrainNotFoundException(trainId);
        }

        Route route = routeRepository.findById(routeId);
        if (route == null) {
            throw new RouteNotFoundException(routeId);
        }

        Trip tripI = tripRepository.findById(id);
        TripDTO trip = modelMapper.map(tripI, TripDTO.class);

        trip.setDateArr(dateArr);
        trip.setDateDep(dateDep);
        trip.setStatusTrip(statusTrip);
        trip.setTrainId(trainId);
        trip.setRouteId(routeId);
        trip.setDelayed(isDelayed);
        trip.setDelayTime(delayTime);
        Trip tripC = modelMapper.map(trip, Trip.class);
        tripRepository.update(tripC);
    }

    @Override
    public List<TripDTO> getAllTrips() {
        List<TripDTO> result = new ArrayList<>();
        List<Trip> trips = tripRepository.findAll();
        for (Trip trip : trips) {
            result.add(modelMapper.map(trip, TripDTO.class));
        }
        return result;
    }

    @Override
    @CacheEvict(cacheNames = "trips", allEntries = true)
    public void deleteTrip(Integer id) {
        Trip trip = tripRepository.findById(id);
        if (trip.getStatusTrip() != StatusTrip.CANCELLED) {
            trip.setStatusTrip(StatusTrip.CANCELLED);
            tripRepository.update(trip);
        } else throw new DeleteAlreadyException(id);
    }
}