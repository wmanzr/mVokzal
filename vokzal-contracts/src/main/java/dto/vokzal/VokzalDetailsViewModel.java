package dto.vokzal;

import dto.base.BaseViewModel;

public record VokzalDetailsViewModel(
        BaseViewModel base,
        VokzalViewModel vokzal
) {}