package RUT.vokzal.Service.Impl;

import RUT.vokzal.DTO.TripDTO;
import RUT.vokzal.Model.entity.Trip;
import RUT.vokzal.Model.entity.Vokzal;
import RUT.vokzal.Exception.VokzalNameNotFoundException;
import RUT.vokzal.Repository.TripRepository;
import RUT.vokzal.Repository.VokzalRepository;
import RUT.vokzal.Service.HomeService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class HomeServiceImpl implements HomeService {

    private TripRepository tripRepository;
    private VokzalRepository vokzalRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setTripRepository(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Autowired
    public void setVokzalRepository(VokzalRepository vokzalRepository) {
        this.vokzalRepository = vokzalRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TripDTO> getUpcomingTripsWithTimeByStationName(String vokzalName) {
        Vokzal vokzal = vokzalRepository.findByName(vokzalName);
        if (vokzal == null) {
            throw new VokzalNameNotFoundException(vokzalName);
        }
    LocalDate nowDate = LocalDate.now();
    LocalTime nowTime = LocalTime.now();
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    
    List<Trip> upcomingTrips = tripRepository.findUpcomingTripsWithTimeByStation(vokzal.getId(), nowDate, nowTime);
    List<TripDTO> result = new ArrayList<>();

    for (Trip trip : upcomingTrips) {
        var from = trip.getRoute().getDepPlId().getVokzalId().getCity();
        var to = trip.getRoute().getArrPlId().getVokzalId().getCity();
        var trnum = trip.getTrain().getNumber();
        var timedep = trip.getRoute().getTimeDep();
        String timeDeparture = timedep.format(timeFormatter);

        TripDTO tripDTO = modelMapper.map(trip, TripDTO.class);

        tripDTO.setCityFrom(from);
        tripDTO.setCityTo(to);
        tripDTO.setNumTrain(trnum);
        tripDTO.setTimeDep(timeDeparture);

        result.add(tripDTO);
        }
        return result;
    }

    @Override
    public int getStationLoadByDayAndTime(String vokzalName) {
    Vokzal vokzal = vokzalRepository.findByName(vokzalName);
    if (vokzal == null) {
        throw new VokzalNameNotFoundException("Вокзал с таким названием не найден");
    }

    LocalTime currentTime = LocalTime.now();
    DayOfWeek currentDay = LocalDate.now().getDayOfWeek();
    int loadPercentage;

    if (currentDay != DayOfWeek.SATURDAY && currentDay != DayOfWeek.SUNDAY) {
        if (currentTime.isAfter(LocalTime.of(0, 0)) && currentTime.isBefore(LocalTime.of(7, 0))) {
            loadPercentage = 10;
        } else if (currentTime.isAfter(LocalTime.of(7, 0)) && currentTime.isBefore(LocalTime.of(10, 0))) {
            loadPercentage = 90;
        } else if (currentTime.isAfter(LocalTime.of(10, 0)) && currentTime.isBefore(LocalTime.of(16, 0))) {
            loadPercentage = 45;
        } else if (currentTime.isAfter(LocalTime.of(16, 0)) && currentTime.isBefore(LocalTime.of(18, 0))) {
            loadPercentage = 85;
        } else if (currentTime.isAfter(LocalTime.of(18, 0)) && currentTime.isBefore(LocalTime.of(22, 0))) {
            loadPercentage = 50;
        } else {
            loadPercentage = 20;
        }
    } else {
        if (currentTime.isAfter(LocalTime.of(0, 0)) && currentTime.isBefore(LocalTime.of(10, 0))) {
            loadPercentage = 25;
        } else if (currentTime.isAfter(LocalTime.of(10, 0)) && currentTime.isBefore(LocalTime.of(18, 0))) {
            loadPercentage = 65;
        } else if (currentTime.isAfter(LocalTime.of(18, 0)) && currentTime.isBefore(LocalTime.of(22, 0))) {
            loadPercentage = 50;
        } else {
            loadPercentage = 40;
        }
    }

    return loadPercentage;
}

    @Override
    public List<TripDTO> getAlternativeTripsIfCanceled(String vokzalName) {
        LocalDate nowDate = LocalDate.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        List<Trip> canceledTrips = tripRepository.findCanceledTrips(vokzalName, nowDate);

        List<TripDTO> alternativeTrips = new ArrayList<>();

        for (Trip canceledTrip : canceledTrips) {
            List<Trip> alternatives = tripRepository.findAlternativeTrips(canceledTrip.getRoute().getId(), nowDate);

            for (Trip alternativeTrip : alternatives) {
                var from = alternativeTrip.getRoute().getDepPlId().getVokzalId().getCity();
                var to = alternativeTrip.getRoute().getArrPlId().getVokzalId().getCity();
                var trnum = alternativeTrip.getTrain().getNumber();
                var timedep = alternativeTrip.getRoute().getTimeDep();
                String timeDeparture = timedep.format(timeFormatter);

                TripDTO tripDTO = modelMapper.map(alternativeTrip, TripDTO.class);

                tripDTO.setCityFrom(from);
                tripDTO.setCityTo(to);
                tripDTO.setNumTrain(trnum);
                tripDTO.setTimeDep(timeDeparture);

                boolean isDuplicate = alternativeTrips.stream()
                        .anyMatch(existingTrip -> existingTrip.getId() == tripDTO.getId());

                if (!isDuplicate) {
                    alternativeTrips.add(tripDTO);
                }
            }

        }
        return alternativeTrips;
    }

    @Override
    public List<TripDTO> getCanceledTrips (String vokzalName) {
        LocalDate nowDate = LocalDate.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        List<Trip> canceledTrips = tripRepository.findCanceledTrips(vokzalName, nowDate);
        List<TripDTO> result = new ArrayList<>();
        for (Trip canceledTrip : canceledTrips) {
            var from = canceledTrip.getRoute().getDepPlId().getVokzalId().getCity();
            var to = canceledTrip.getRoute().getArrPlId().getVokzalId().getCity();
            var trnum = canceledTrip.getTrain().getNumber();
            var timedep = canceledTrip.getRoute().getTimeDep();
            String timeDeparture = timedep.format(timeFormatter);

            TripDTO tr = modelMapper.map(canceledTrip, TripDTO.class);

            tr.setCityFrom(from);
            tr.setCityTo(to);
            tr.setNumTrain(trnum);
            tr.setTimeDep(timeDeparture);
            result.add(tr);
        }
        return result;
    }
}