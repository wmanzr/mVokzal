package RUT.vokzal.Repository;

import RUT.vokzal.Model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeRepository {
    void create(Employee employee);
    Employee findById(int id);
    Employee update(Employee employee);
    List<Employee> findAll();
    Page<Employee> findByPostContainingIgnoreCase(String searchTerm, Pageable pageable);
    Page<Employee> findAll(Pageable pageable);
}