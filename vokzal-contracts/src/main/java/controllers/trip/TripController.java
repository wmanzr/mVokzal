package controllers.trip;

import controllers.base.BaseController;
import dto.SearchForm;
import dto.trip.*;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/trips")
public interface TripController extends BaseController {

    @GetMapping
    String listTrips(
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
            @Valid @ModelAttribute("form") TripCreateForm form,
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
            @Valid @ModelAttribute("form") TripEditForm form,
            BindingResult bindingResult,
            Model model, Principal principal
    );
    @GetMapping("/{id}/delete")
    String delete(@PathVariable int id, Principal principal);
}