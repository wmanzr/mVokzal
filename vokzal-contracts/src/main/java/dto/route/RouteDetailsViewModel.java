package dto.route;

import dto.base.BaseViewModel;

public record RouteDetailsViewModel(
        BaseViewModel base,
        RouteViewModel route
) {}