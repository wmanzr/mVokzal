package dto.platform;

import dto.base.BaseViewModel;
import java.util.List;

public record PlatformListViewModel(
        BaseViewModel base,
        List<PlatformViewModel> platforms,
        Integer totalPages
) {}