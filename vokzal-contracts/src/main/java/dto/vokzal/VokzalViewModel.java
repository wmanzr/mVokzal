package dto.vokzal;

public record VokzalViewModel(
        Integer id,
        String name,
        String city,
        Integer capacity,
        boolean del
) {}