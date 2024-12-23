package RUT.vokzal.Service.Impl;

import java.util.ArrayList;
import java.util.List;
import RUT.vokzal.Exception.DeleteAlreadyException;
import RUT.vokzal.Model.entity.Train;
import RUT.vokzal.Exception.TrainNotFoundException;
import RUT.vokzal.Repository.TrainRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import RUT.vokzal.DTO.EmployeeDTO;
import RUT.vokzal.Model.entity.Employee;
import RUT.vokzal.Repository.EmployeeRepository;
import RUT.vokzal.Service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;
    private TrainRepository trainRepository;

    public ModelMapper getModelMapper() {
        return this.modelMapper;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setTrainRepository(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @Override
    public Integer createEmployee(String login, String post, Integer experience, Integer trainId) {
        Train train = trainRepository.findById(trainId);
        if (train == null) {
            throw new TrainNotFoundException(trainId);
        }
        Employee employee = new Employee(login, post, experience, train);
        employeeRepository.create(employee);
        return employee.getId();
    }

    @Override
    public EmployeeDTO getEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id);
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        return new EmployeeDTO(employeeDTO.getId(), employeeDTO.getLogin(), employeeDTO.getPost(),
                employeeDTO.getExperience(), employeeDTO.getTrainId(), employeeDTO.getDel());
    }

    @Override
    public Page<EmployeeDTO> getEmployees(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Employee> employeesPage = searchTerm.isEmpty()
                ? employeeRepository.findAll(pageable)
                : employeeRepository.findByPostContainingIgnoreCase(searchTerm, pageable);
        return employeesPage.map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }



    @Override
    public void updateEmployee(Integer id, String login, String post, Integer experience, Integer trainId) {
        Train train = trainRepository.findById(trainId);
        if (train == null) {
            throw new TrainNotFoundException(trainId);
        }

        Employee employeeI = employeeRepository.findById(id);
        EmployeeDTO employee = modelMapper.map(employeeI, EmployeeDTO.class);
        employee.setLogin(login);
        employee.setExperience(experience);
        employee.setPost(post);
        employee.setTrainId(trainId);
        Employee employeeC = modelMapper.map(employee, Employee.class);
        employeeRepository.update(employeeC);
    }
 
    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeDTO> result = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            result.add(modelMapper.map(employee, EmployeeDTO.class));
        }
        return result;
    }

    @Override
    public void deleteEmployee(Integer id) {
        Employee employee = employeeRepository.findById(id);
        if (!employee.getDel()) {
            employee.setDel(true);
            employeeRepository.update(employee);
        } else throw new DeleteAlreadyException(id);
    }
}