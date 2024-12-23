package dto.vokzal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VokzalCreateForm(
    @NotBlank(message = "Название вокзала обязательно") String name,
    @NotBlank(message = "Город вокзала обязателен") String city,
    @NotNull(message = "Вместимость вокзала обязательна") Integer capacity
) {}