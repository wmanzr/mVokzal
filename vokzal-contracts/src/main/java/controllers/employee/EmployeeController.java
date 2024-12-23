package controllers.employee;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import controllers.base.BaseController;
import dto.employee.EmployeeCreateForm;
import dto.employee.EmployeeEditForm;
import dto.SearchForm;

import java.security.Principal;

@RequestMapping("/employees")
public interface EmployeeController extends BaseController {

    @GetMapping
    String listEmployees(@ModelAttribute("form") SearchForm form,
                         Model model, Principal principal);
    @GetMapping("/{id}")
    String details(@PathVariable int id, Model model, Principal principal);
    @GetMapping("/create")
    String createForm(Model model);

    @PostMapping("/create")
    String create(@Valid @ModelAttribute("form") EmployeeCreateForm form,
                  BindingResult bindingResult, Model model, Principal principal);
    @GetMapping("/{id}/edit")
    String editForm(@PathVariable int id, Model model);

    @PostMapping("/{id}/edit")
    String edit(@PathVariable int id, @Valid @ModelAttribute("form") EmployeeEditForm form,
                BindingResult bindingResult, Model model, Principal principal);
    @GetMapping("/{id}/delete")
    String delete(@PathVariable int id, Principal principal);
}