package dto.train;

import dto.base.BaseViewModel;

public record TrainDetailsViewModel(
        BaseViewModel base,
        TrainViewModel train
) {}