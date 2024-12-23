package RUT.vokzal.Service;

import java.util.List;
import RUT.vokzal.DTO.EmployeeDTO;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    Integer createEmployee(String login, String post, Integer experience, Integer trainId);
    EmployeeDTO getEmployeeById(int id);
    void updateEmployee(Integer id, String login, String post, Integer experience, Integer trainId);
    List<EmployeeDTO> getAllEmployees();
    Page<EmployeeDTO> getEmployees(String searchTerm, int page, int size);
    void deleteEmployee(Integer id);
}