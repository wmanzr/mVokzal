package dto;

public record UserProfileViewModel(
        String username,
        String email,
        String fullName,
        int age
) {}