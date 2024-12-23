package RUT.vokzal.Controller;

import RUT.vokzal.DTO.TrainDTO;
import RUT.vokzal.Service.TrainService;
import controllers.train.TrainController;
import dto.SearchForm;
import dto.base.BaseViewModel;
import dto.train.*;
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
@RequestMapping("/trains")
public class TrainControllerImpl implements TrainController {
    private TrainService trainService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    @Autowired
    public void setTrainService(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping()
    public String listTrains(@ModelAttribute("form") SearchForm form, Model model, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел все поезда");
        var searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 11;
        form = new SearchForm(searchTerm, page, size);

        var trainsPage = trainService.getTrains(searchTerm, page, size);
        var trainViewModels = trainsPage.stream()
                .map(t -> new TrainViewModel(
                        t.getId(),
                        t.getNumber(),
                        t.getType(),
                        t.getModel(),
                        t.getCapacity(),
                        t.getStatusTrain(),
                        t.getMaxSpeed()
                ))
                .toList();

        var viewModel = new TrainListViewModel(
                createBaseViewModel("Список поездов"),
                trainViewModels,
                trainsPage.getTotalPages()
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "train/list";
    }

    @Override
    @GetMapping("/{id}")
    public String details(@PathVariable int id, Model model, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел детальную информацию о поезде с id: " + id);
        TrainDTO train = trainService.getTrainById(id);
        var viewModel = new TrainDetailsViewModel(
                createBaseViewModel("Детали поезда"),
                new TrainViewModel(
                        train.getId(),
                        train.getNumber(),
                        train.getType(),
                        train.getModel(),
                        train.getCapacity(),
                        train.getStatusTrain(),
                        train.getMaxSpeed()
                )
        );
        model.addAttribute("model", viewModel);
        return "train/details";
    }

    @Override
    @GetMapping("/create")
    public String createForm(Model model) {
        var viewModel = new TrainCreateViewModel(createBaseViewModel("Создание поезда"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new TrainCreateForm(null, null, null, null, null, null));
        return "train/create";
    }

    @Override
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("form") TrainCreateForm form,
                         BindingResult bindingResult,
                         Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            var viewModel = new TrainCreateViewModel(createBaseViewModel("Создание поезда"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "train/create";
        }
        LOG.log(Level.INFO, principal.getName() + " добавил новый поезд");
        var id = trainService.createTrain(
                form.number(),
                form.type(),
                form.model(),
                form.capacity(),
                form.statusTrain(),
                form.maxSpeed()
        );
        return "redirect:/trains/" + id;
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        TrainDTO train = trainService.getTrainById(id);
        var viewModel = new TrainEditViewModel(createBaseViewModel("Редактирование поезда"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new TrainEditForm(
                train.getId(),
                train.getNumber(),
                train.getType(),
                train.getModel(),
                train.getCapacity(),
                train.getStatusTrain(),
                train.getMaxSpeed()
        ));
        return "train/edit";
    }

    @Override
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable int id, @Valid @ModelAttribute("form") TrainEditForm form,
                       BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            var viewModel = new TrainEditViewModel(createBaseViewModel("Редактирование поезда"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "train/edit";
        }
        LOG.log(Level.INFO, principal.getName() + " обновил информацию о поезде с id: " + id);
        trainService.updateTrain(
                id,
                form.number(),
                form.type(),
                form.model(),
                form.capacity(),
                form.statusTrain(),
                form.maxSpeed()
        );
        return "redirect:/trains/" + id;
    }

    @Override
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " пометил поезд с id " + id + " как удаленный");
        trainService.deleteTrain(id);
        return "redirect:/trains/" + id;
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}