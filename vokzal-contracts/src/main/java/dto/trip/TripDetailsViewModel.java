package dto.trip;

import dto.base.BaseViewModel;

public record TripDetailsViewModel(
        BaseViewModel base,
        TripViewModel trip
) {}