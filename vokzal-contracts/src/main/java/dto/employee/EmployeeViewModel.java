package dto.employee;

public record EmployeeViewModel(
    Integer id,
    String login,
    String post,
    Integer experience,
    Integer trainId,
    boolean del
) {}