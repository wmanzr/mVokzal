package dto.appeal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AppealCreateForm(
    @NotBlank(message = "Ваше сообщение не должно быть пустым")
    String header,
    @NotBlank(message = "Ваше сообщение не должно быть пустым")
    String question,
    @Min(value = 1, message = "Оставьте отзыв от 1 до 5, где 1 - очень плохо, а 5 - отлично")
    @Max(value = 5, message = "Оставьте отзыв от 1 до 5, где 1 - очень плохо, а 5 - отлично")
    Integer feedback,
    @NotNull
    Integer userId
) {}