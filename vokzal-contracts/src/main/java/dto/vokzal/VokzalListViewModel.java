package dto.vokzal;

import dto.base.BaseViewModel;
import dto.employee.EmployeeViewModel;

import java.util.List;

public record VokzalListViewModel(
        BaseViewModel base,
        List<VokzalViewModel> vokzals,
        Integer totalPages
) {}