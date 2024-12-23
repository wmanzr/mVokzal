package dto.appeal;

import jakarta.validation.constraints.NotNull;

public record AppealViewModel(
        Integer id,
        String header,
        String question,
        Integer feedback,
        String dateStart,
        Integer userId,
        String answer
) {}