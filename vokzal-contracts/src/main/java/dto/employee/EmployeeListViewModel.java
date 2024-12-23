package dto.employee;

import java.util.List;

import dto.base.BaseViewModel;

public record EmployeeListViewModel(
    BaseViewModel base,
    List<EmployeeViewModel> employees,
    Integer totalPages
) {}