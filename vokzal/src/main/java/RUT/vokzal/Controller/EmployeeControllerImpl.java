package RUT.vokzal.Controller;

import RUT.vokzal.DTO.EmployeeDTO;
import RUT.vokzal.Service.EmployeeService;
import controllers.employee.EmployeeController;
import dto.SearchForm;
import dto.base.BaseViewModel;
import dto.employee.*;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/employees")
public class EmployeeControllerImpl implements EmployeeController {
    private EmployeeService employeeService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public String listEmployees(@ModelAttribute("form") SearchForm form, Model model, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел всех сотрудников");
        var searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 11;
        form = new SearchForm(searchTerm, page, size);

        var employeesPage = employeeService.getEmployees(searchTerm, page, size);
        var employeeViewModels = employeesPage.stream()
                .map(b -> new EmployeeViewModel(b.getId(), b.getLogin(),
                        b.getPost(), b.getExperience(), b.getTrainId(), b.getDel()))
                .toList();
        var viewModel = new EmployeeListViewModel(
                createBaseViewModel("Список сотрудников"),
                employeeViewModels,
                employeesPage.getTotalPages()
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "employee/list";
    }

    @Override
    @GetMapping("/{id}")
    public String details(int id, Model model, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел детальную информацию о сотруднике с id: " + id);
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        var viewModel = new EmployeeDetailsViewModel(
                createBaseViewModel("Детали сотрудника"),
                new EmployeeViewModel(employee.getId(), employee.getLogin(),
                        employee.getPost(), employee.getExperience(), employee.getTrainId(), employee.getDel())
        );
        model.addAttribute("model", viewModel);
        return "employee/details";
    }

    @Override
    @GetMapping("/create")
    public String createForm(Model model) {
        var viewModel = new EmployeeCreateViewModel(createBaseViewModel("Создание сотрудника"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new EmployeeCreateForm(null,  null, null, null));
        return "employee/create";
    }

    @Override
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("form") EmployeeCreateForm form,
                         BindingResult bindingResult,
                         Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            var viewModel = new EmployeeCreateViewModel(createBaseViewModel("Создание сотрудника"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "employee/create";
        }
        LOG.log(Level.INFO, principal.getName() + " добавил нового сотрудника");
        var id = employeeService.createEmployee(
                form.login(),
                form.post(),
                form.experience(),
                form.trainId());
        return "redirect:/employees/" + id;
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(int id, Model model) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        var viewModel = new EmployeeEditViewModel(createBaseViewModel("Редактирование сотрудника"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new EmployeeEditForm(employee.getId(), employee.getLogin(),
                employee.getPost(), employee.getExperience(), employee.getTrainId()));
        return "employee/edit";
    }

    @Override
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable int id, @Valid @ModelAttribute("form") EmployeeEditForm form, BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            var viewModel = new EmployeeEditViewModel(createBaseViewModel("Редактирование сотрудника"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "employee/edit";
        }
        LOG.log(Level.INFO, principal.getName() + " обновил информацию о сотруднике с id: " + id);
        employeeService.updateEmployee(id, form.login(), form.post(), form.experience(), form.trainId());
        return "redirect:/employees/" + id;
    }

    @Override
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " пометил сотрудника с id " + id + " как удаленный");
        employeeService.deleteEmployee(id);
        return "redirect:/employees/" + id;
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(
                title
        );
    }
}