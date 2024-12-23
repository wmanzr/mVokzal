package dto.trip;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TripViewModel(
        Integer id,
        String dateDep,
        String dateArr,
        String statusTrip,
        Integer trainId,
        Integer routeId,
        Boolean isDelayed,
        String delayTime
) {}