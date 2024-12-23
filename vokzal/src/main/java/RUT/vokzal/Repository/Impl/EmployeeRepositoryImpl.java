package RUT.vokzal.Repository.Impl;

import RUT.vokzal.Model.entity.Employee;
import RUT.vokzal.Repository.BaseRepository;
import RUT.vokzal.Repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryImpl extends BaseRepository<Employee, Integer> implements EmployeeRepository {

    public EmployeeRepositoryImpl() {
        super(Employee.class);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void create(Employee employee) {
        super.create(employee);
    }

    @Override
    public Employee findById(int id) {
        return super.findById(id);
    }

    @Override
    @Transactional
    public Employee update(Employee employee) {
        return super.update(employee);
    }

    @Override
    public List<Employee> findAll() { return super.findAll(); }

    @Override
    public Page<Employee> findByPostContainingIgnoreCase(String searchTerm, Pageable pageable) {
        List<Employee> employees = entityManager.createQuery(
                        "FROM Employee e WHERE LOWER(e.post) LIKE LOWER(:searchTerm)", Employee.class)
                .setParameter("searchTerm", "%" + searchTerm + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = entityManager.createQuery(
                        "SELECT COUNT(e) FROM Employee e WHERE LOWER(e.post) LIKE LOWER(:searchTerm)", Long.class)
                .setParameter("searchTerm", "%" + searchTerm + "%")
                .getSingleResult();

        return new PageImpl<>(employees, pageable, total);
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        List<Employee> employees = entityManager.createQuery("FROM Employee e ORDER BY e.post ASC", Employee.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = entityManager.createQuery("SELECT COUNT(e) FROM Employee e", Long.class)
                .getSingleResult();

        return new PageImpl<>(employees, pageable, total);
    }
}