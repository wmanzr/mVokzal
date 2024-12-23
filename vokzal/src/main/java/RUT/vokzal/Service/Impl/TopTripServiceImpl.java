package RUT.vokzal.Service.Impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import RUT.vokzal.DTO.TripDTO;
import RUT.vokzal.Model.entity.Trip;
import RUT.vokzal.Repository.TripRepository;
import RUT.vokzal.Service.TopTripService;

@Service
public class TopTripServiceImpl implements TopTripService{

    private ModelMapper modelMapper;
    private TripRepository tripRepository;

    @Autowired
    public void setTripRepository(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TripDTO> getTop5Trips() {
        List<Trip> trips = tripRepository.findTop5Trips();

        trips.sort((trip1, trip2) -> {
            int speedComparison = Integer.compare(
                    trip2.getTrain().getMaxSpeed(),
                    trip1.getTrain().getMaxSpeed()
            );

            if (speedComparison != 0) {
                return speedComparison;
            }

            Duration duration1 = Duration.between(
                    LocalDateTime.of(trip1.getDateDep(), trip1.getRoute().getTimeDep()),
                    LocalDateTime.of(trip1.getDateArr(), trip1.getRoute().getTimeArr())
            );

            Duration duration2 = Duration.between(
                    LocalDateTime.of(trip2.getDateDep(), trip2.getRoute().getTimeDep()),
                    LocalDateTime.of(trip2.getDateArr(), trip2.getRoute().getTimeArr())
            );

            return Long.compare(duration1.getSeconds(), duration2.getSeconds());
        });

        List<Trip> top5Trips = trips.stream().limit(5).collect(Collectors.toList());

        return top5Trips.stream()
                .map(trip -> {
                    TripDTO tr = modelMapper.map(trip, TripDTO.class);
                    var from = trip.getRoute().getDepPlId().getVokzalId().getCity();
                    var to = trip.getRoute().getArrPlId().getVokzalId().getCity();
                    var trnum = trip.getTrain().getNumber();
                    var timedep = trip.getRoute().getTimeDep();
                    var speed = trip.getTrain().getMaxSpeed();
                    Duration duration = Duration.between(
                            LocalDateTime.of(trip.getDateDep(), trip.getRoute().getTimeDep()),
                            LocalDateTime.of(trip.getDateArr(), trip.getRoute().getTimeArr())
                    );
                    String timeInTrip = String.format("%d ч %d мин",
                            duration.toHours(),
                            duration.toMinutesPart()
                    );

                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                    String timeDeparture = timedep.format(timeFormatter);

                    tr.setCityFrom(from);
                    tr.setCityTo(to);
                    tr.setNumTrain(trnum);
                    tr.setTimeDep(timeDeparture);
                    tr.setMaxSpeedTrain(speed);
                    tr.setTimeInTrip(timeInTrip);
                    return tr;
                })
                .collect(Collectors.toList());
    }
}