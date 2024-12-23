package controllers.platform;

import controllers.base.BaseController;
import dto.SearchForm;
import dto.platform.PlatformCreateForm;
import dto.platform.PlatformEditForm;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RequestMapping("/platforms")
public interface PlatformController extends BaseController {

    @GetMapping
    String listPlatforms(
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
            @Valid @ModelAttribute("form") PlatformCreateForm form,
            BindingResult bindingResult, Model model, Principal principal
    );


    @GetMapping("/{id}/edit")
    String editForm(
            @PathVariable int id,
            Model model
    );

    @PostMapping("/{id}/edit")
    String edit(
            @PathVariable int id,
            @Valid @ModelAttribute("form") PlatformEditForm form,
            BindingResult bindingResult,
            Model model, Principal principal
    );

    @GetMapping("/{id}/delete")
    String delete(@PathVariable int id, Principal principal);
}