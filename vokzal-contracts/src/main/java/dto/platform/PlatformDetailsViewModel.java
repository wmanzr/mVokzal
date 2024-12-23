package dto.platform;

import dto.base.BaseViewModel;


public record PlatformDetailsViewModel(
        BaseViewModel base,
        PlatformViewModel platform
) {}
