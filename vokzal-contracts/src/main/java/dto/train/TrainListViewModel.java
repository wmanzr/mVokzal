package dto.train;

import dto.base.BaseViewModel;

import java.util.List;

public record TrainListViewModel(
        BaseViewModel base,
        List<TrainViewModel> trains,
        Integer totalPages
) {}