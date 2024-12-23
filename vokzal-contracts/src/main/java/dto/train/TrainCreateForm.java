package dto.train;

import jakarta.validation.constraints.*;

public record TrainCreateForm(
    @Min(value = 1000, message = "Номер поезда должен состоять из 4 цифр")
    @Max(value = 9999, message = "Номер поезда должен состоять из 4 цифр")
    Integer number,
    @NotBlank(message = "Тип поезда обязателен")
    String type,
    @NotBlank(message = "Модель поезда обязательна")
    String model,
    @NotNull(message = "Вместимость поезда обязательна")
    Integer capacity,
    @Pattern(regexp = "IN_OPERATION|UNDER_MAINTENANCE|NOT_USED|STANDBY",
            message = "Статус поезда должен быть одним из: IN_OPERATION, UNDER_MAINTENANCE, STANDBY, NOT_USED")
    String statusTrain,
    @NotNull(message = "Максимальная скорость поезда обязательна")
    Integer maxSpeed
) {}