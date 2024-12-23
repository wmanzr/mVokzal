package dto;

import jakarta.validation.constraints.Min;

public record SearchForm(
    String searchTerm,
    @Min(value = 1, message = "Страница должна быть больше 0")
    Integer page,
    @Min(value = 1, message = "Размер страницы должен быть больше 0")
    Integer size
) {}