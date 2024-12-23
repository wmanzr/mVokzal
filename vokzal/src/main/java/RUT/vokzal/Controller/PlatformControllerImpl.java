package RUT.vokzal.Controller;

import RUT.vokzal.DTO.PlatformDTO;
import RUT.vokzal.Service.PlatformService;
import controllers.platform.PlatformController;
import dto.SearchForm;
import dto.base.BaseViewModel;
import dto.platform.*;
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
@RequestMapping("/platforms")
public class PlatformControllerImpl implements PlatformController {
    private PlatformService platformService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    @Autowired
    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping()
    public String listPlatforms(@ModelAttribute("form") SearchForm form, Model model, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел все платформы");
        var searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 11;
        form = new SearchForm(searchTerm, page, size);

        var platformsPage = platformService.getPlatforms(searchTerm, page, size);
        var platformViewModels = platformsPage.stream()
                .map(p -> new PlatformViewModel(
                        p.getId(),
                        p.getNumber(),
                        p.getType(),
                        p.getStatusPlatform(),
                        p.getVokzalId()))
                .toList();

        var viewModel = new PlatformListViewModel(
                createBaseViewModel("Список платформ"),
                platformViewModels,
                platformsPage.getTotalPages()
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "platform/list";
    }

    @Override
    @GetMapping("/{id}")
    public String details(@PathVariable int id, Model model, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел детальную информацию о платформе с id: " + id);
        PlatformDTO platform = platformService.getPlatformById(id);
        var viewModel = new PlatformDetailsViewModel(
                createBaseViewModel("Детали платформы"),
                new PlatformViewModel(platform.getId(), platform.getNumber(), platform.getType(),
                        platform.getStatusPlatform(), platform.getVokzalId())
        );
        model.addAttribute("model", viewModel);
        return "platform/details";
    }

    @Override
    @GetMapping("/create")
    public String createForm(Model model) {
        var viewModel = new PlatformCreateViewModel(createBaseViewModel("Создание платформы"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new PlatformCreateForm(null, null, null, null));
        return "platform/create";
    }

    @Override
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("form") PlatformCreateForm form,
                         BindingResult bindingResult,
                         Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            var viewModel = new PlatformCreateViewModel(createBaseViewModel("Создание платформы"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "platform/create";
        }
        LOG.log(Level.INFO, principal.getName() + " добавил новую платформу");
        var id = platformService.createPlatform(
                form.number(),
                form.type(),
                form.statusPlatform(),
                form.vokzalId());
        return "redirect:/platforms/" + id;
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        PlatformDTO platform = platformService.getPlatformById(id);
        var viewModel = new PlatformEditViewModel(createBaseViewModel("Редактирование платформы"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new PlatformEditForm(
                platform.getId(),
                platform.getNumber(),
                platform.getType(),
                platform.getStatusPlatform(),
                platform.getVokzalId()));
        return "platform/edit";
    }

    @Override
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable int id,
                       @Valid @ModelAttribute("form") PlatformEditForm form,
                       BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            var viewModel = new PlatformEditViewModel(createBaseViewModel("Редактирование платформы"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "platform/edit";
        }
        LOG.log(Level.INFO, principal.getName() + " обновил информацию о платформе с id: " + id);
        platformService.updatePlatform(
                id,
                form.number(),
                form.type(),
                form.statusPlatform(),
                form.vokzalId());
        return "redirect:/platforms/" + id;
    }

    @Override
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " пометил платформу с id " + id + " как удаленная");
        platformService.deletePlatform(id);
        return "redirect:/platforms/" + id;
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}