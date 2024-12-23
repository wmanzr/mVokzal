package controllers.route;

import controllers.base.BaseController;
import dto.SearchForm;
import dto.route.RouteCreateForm;
import dto.route.RouteEditForm;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/routes")
public interface RouteController extends BaseController {

    @GetMapping
    String listRoutes(
            @ModelAttribute("form") SearchForm form,
            Model model, Principal principal
    );

    @GetMapping("/{id}")
    String details(
            @PathVariable int id,
            Model model, Principal principal
    );

    @GetMapping("/create")
    String createForm(Model model);

    @PostMapping("/create")
    String create(
            @Valid @ModelAttribute("form") RouteCreateForm form,
            BindingResult bindingResult,
            Model model, Principal principal
    );


    @GetMapping("/{id}/edit")
    String editForm(
            @PathVariable int id,
            Model model
    );

    @PostMapping("/{id}/edit")
    String edit(
            @PathVariable int id,
            @Valid @ModelAttribute("form") RouteEditForm form,
            BindingResult bindingResult,
            Model model, Principal principal
    );

    @GetMapping("/{id}/delete")
    String delete(@PathVariable int id, Principal principal);
}