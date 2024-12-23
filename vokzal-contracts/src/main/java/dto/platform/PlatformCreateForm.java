package dto.platform;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PlatformCreateForm(
    @NotNull(message = "Номер платформы обязателен")
    Integer number,
    @NotBlank(message = "Тип платформы обязателен")
    String type,
    @Pattern(regexp = "IN_OPERATION|NOT_USED|UNDER_CONSTRUCTION",
            message = "Статус платформы должен быть одним из: IN_OPERATION, UNDER_CONSTRUCTION, NOT_USED")
    String statusPlatform,
    @NotNull(message = "ID вокзала обязателен")
    Integer vokzalId
) {}