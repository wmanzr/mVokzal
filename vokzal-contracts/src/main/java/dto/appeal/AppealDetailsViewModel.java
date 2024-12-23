package dto.appeal;

import dto.base.BaseViewModel;

public record AppealDetailsViewModel(
        BaseViewModel base,
        AppealViewModel appeal
) {}