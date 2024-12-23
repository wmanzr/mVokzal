package dto.route;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RouteCreateForm(
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):([0-5][0-9])$",
            message = "Время должно быть в формате 23:59")
    String timeDep,
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):([0-5][0-9])$",
            message = "Время должно быть в формате 23:59")
    String timeArr,
    @NotNull(message = "Платформа отправления обязательна")
    Integer depPlId,
    @NotNull(message = "Платформа прибытия обязательна")
    Integer arrPlId
) {}