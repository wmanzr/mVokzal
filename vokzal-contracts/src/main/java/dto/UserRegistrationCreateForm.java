package dto;

import jakarta.validation.constraints.*;

public record UserRegistrationCreateForm(
        @NotBlank(message = "Имя пользователя не должно быть пустым!")
        @Size(min = 5, max = 20, message = "Имя пользователя должно быть не меньше 5 и не больше 20!")
        String username,
        @NotBlank(message = "Полное имя не должно быть пустым!")
        @Size(min = 5, max = 20, message = "Полное имя должно быть не меньше 5 и не больше 20!")
        String fullName,
        @NotEmpty(message = "Email обязателен!")
        @Email
        String email,
        @NotNull(message = "Возраст должен быть больше 10!")
        @Min(10)
        Integer age,
        @NotBlank(message = "Пароль обязателен!")
        @Size(min = 5, max = 20, message = "Пароль должен быть от 5 до 20 символов!")
        String password,
        @NotBlank(message = "Подтверждение пароля обязательно!!!")
        @Size(min = 5, max = 20, message = "Пароли должны совпадать!")
        String confirmPassword
) {}