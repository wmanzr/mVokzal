package dto.platform;

public record PlatformViewModel(
        Integer id,
        Integer number,
        String type,
        String statusPlatform,
        Integer vokzalId
) {}