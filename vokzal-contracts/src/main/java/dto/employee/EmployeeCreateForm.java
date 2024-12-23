package dto.employee;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EmployeeCreateForm(
    @Size(min = 5, message = "Логин должен содержать минимум 5 символов")
    String login,
    @NotBlank(message = "Должность обязательна")
    String post,
    @NotNull(message = "Опыт работы обязателен")
    Integer experience,
    @NotNull(message = "ID поезда обязателен")
    Integer trainId
) {}