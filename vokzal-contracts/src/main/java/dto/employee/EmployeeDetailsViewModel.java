package dto.employee;

import dto.base.BaseViewModel;

public record EmployeeDetailsViewModel(
    BaseViewModel base,
    EmployeeViewModel employee
) {}