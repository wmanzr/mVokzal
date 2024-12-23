package RUT.vokzal.Controller;

import RUT.vokzal.DTO.VokzalDTO;
import RUT.vokzal.Service.VokzalService;
import controllers.vokzal.VokzalController;
import dto.SearchForm;
import dto.base.BaseViewModel;
import dto.vokzal.*;
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
@RequestMapping("/vokzals")
public class VokzalControllerImpl implements VokzalController {
    private VokzalService vokzalService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    @Autowired
    public void setVokzalService(VokzalService vokzalService) {
        this.vokzalService = vokzalService;
    }
    @Override
    @GetMapping()
    public String listVokzals(@ModelAttribute("form") SearchForm form, Model model, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел все вокзалы");
        var searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 11;
        form = new SearchForm(searchTerm, page, size);

        var vokzalsPage = vokzalService.getVokzals(searchTerm, page, size);
        var vokzalViewModels = vokzalsPage.stream()
                .map(b -> new VokzalViewModel(b.getId(), b.getName(), b.getCity(), b.getCapacity(), b.getDel()))
                .toList();

        var viewModel = new VokzalListViewModel(
                createBaseViewModel("Список вокзалов"),
                vokzalViewModels,
                vokzalsPage.getTotalPages()
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "vokzal/list";
    }

    @Override
    @GetMapping("/{id}")
    public String details(@PathVariable int id, Model model, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел детальную информацию о вокзале с id: " + id);
        VokzalDTO vokzal = vokzalService.getVokzalById(id);
        var viewModel = new VokzalDetailsViewModel(
                createBaseViewModel("Детали вокзала"),
                new VokzalViewModel(vokzal.getId(), vokzal.getName(), vokzal.getCity(), vokzal.getCapacity(), vokzal.getDel())
        );
        model.addAttribute("model", viewModel);
        return "vokzal/details";
    }

    @Override
    @GetMapping("/create")
    public String createForm(Model model) {
        var viewModel = new VokzalCreateViewModel(createBaseViewModel("Создание вокзала"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new VokzalCreateForm(null, null, null));
        return "vokzal/create";
    }

    @Override
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("form") VokzalCreateForm form,
                         BindingResult bindingResult,
                         Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            var viewModel = new VokzalCreateViewModel(createBaseViewModel("Создание вокзала"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "vokzal/create";
        }
        LOG.log(Level.INFO, principal.getName() + " добавил новый вокзал");
        var id = vokzalService.createVokzal(form.name(), form.city(), form.capacity());
        return "redirect:/vokzals/" + id;
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        VokzalDTO vokzal = vokzalService.getVokzalById(id);
        var viewModel = new VokzalEditViewModel(createBaseViewModel("Редактирование вокзала"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new VokzalEditForm(vokzal.getId(), vokzal.getName(), vokzal.getCity(), vokzal.getCapacity()));
        return "vokzal/edit";
    }

    @Override
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable int id, @Valid @ModelAttribute("form") VokzalEditForm form,
                       BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            var viewModel = new VokzalEditViewModel(createBaseViewModel("Редактирование вокзала"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "vokzal/edit";
        }
        LOG.log(Level.INFO, principal.getName() + " обновил информацию о вокзале с id: " + id);
        vokzalService.updateVokzal(id, form.name(), form.city(), form.capacity());
        return "redirect:/vokzals/" + id;
    }

    @Override
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " пометил вокзал с id " + id + " как удаленный");
        vokzalService.deleteVokzal(id);
        return "redirect:/vokzals/" + id;
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(
                title
        );
    }
}