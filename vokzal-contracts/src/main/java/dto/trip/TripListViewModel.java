package dto.trip;

import dto.base.BaseViewModel;

import java.util.List;

public record TripListViewModel(
        BaseViewModel base,
        List<TripViewModel> trips,
        Integer totalPages
) {}