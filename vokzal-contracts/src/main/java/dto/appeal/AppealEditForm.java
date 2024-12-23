package dto.appeal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AppealEditForm(
        Integer id,
        String header,
        String question,
        Integer feedback,
        String dateStart,
        Integer userId,
        @NotBlank(message = "Ваш ответ не должен быть пустым")
        String answer
) {}