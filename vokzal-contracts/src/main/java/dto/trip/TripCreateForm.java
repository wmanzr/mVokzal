package dto.trip;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TripCreateForm(
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Дата должна быть в формате 2025-02-24") String dateDep,
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Дата должна быть в формате 2025-02-24") String dateArr,
    @Pattern(regexp = "SCHEDULED|COMPLETED|CANCELLED",
            message = "Статус поездки должен быть одним из: SCHEDULED, COMPLETED, CANCELLED")
    String statusTrip,
    @NotNull(message = "ID поезда обязателен") Integer trainId,
    @NotNull(message = "ID маршрута обязателен") Integer routeId,
    @NotNull(message = "Задержка обязательна") Boolean isDelayed,
    String delayTime
) {}