package dto.route;

import dto.base.BaseViewModel;
import java.util.List;

public record RouteListViewModel(
        BaseViewModel base,
        List<RouteViewModel> routes,
        Integer totalPages
) {}