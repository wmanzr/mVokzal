package controllers.apeeal;

import dto.appeal.AppealCreateForm;
import dto.appeal.AppealEditForm;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import controllers.base.BaseController;
import dto.SearchForm;

import java.security.Principal;

@RequestMapping("/appeals")
public interface AppealController extends BaseController {

    @GetMapping("/admin-list")
    String listAppealsAdmin(
            Principal principal, Model model
    );

    @GetMapping("/my-list")
    String listAppealsUser(Principal principal,
            Model model
    );


    @GetMapping("/{id}")
    String details(
            @PathVariable int id,
            Model model, Principal principal
    );

    @GetMapping("/create")
    String createForm(Principal principal, Model model);

    @PostMapping("/create")
    String create(
            @Valid @ModelAttribute("form") AppealCreateForm form,
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
            @Valid @ModelAttribute("form") AppealEditForm form,
            BindingResult bindingResult,
            Model model, Principal principal
    );
}