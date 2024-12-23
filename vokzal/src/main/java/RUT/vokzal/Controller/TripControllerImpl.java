package RUT.vokzal.Controller;

import RUT.vokzal.DTO.TripDTO;
import RUT.vokzal.Service.TripService;
import controllers.trip.TripController;
import dto.SearchForm;
import dto.base.BaseViewModel;
import dto.trip.*;
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
@RequestMapping("/trips")
public class TripControllerImpl implements TripController {
    private TripService tripService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    @Autowired
    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    @Override
    @GetMapping
    public String listTrips(@ModelAttribute("form") SearchForm form, Model model, Principal principal) {
        if (principal != null) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел все поездки"); }
        var searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 11;
        form = new SearchForm(searchTerm, page, size);

        var tripsPage = tripService.getTrips(searchTerm, page, size);
        var tripViewModels = tripsPage.stream()
                .map(trip -> new TripViewModel(
                        trip.getId(),
                        trip.getDateArr(),
                        trip.getDateDep(),
                        trip.getStatusTrip(),
                        trip.getTrainId(),
                        trip.getRouteId(),
                        trip.getIsDelayed(),
                        trip.getDelayTime()))
                .toList();
        var viewModel = new TripListViewModel(
                createBaseViewModel("Список поездок"),
                tripViewModels,
                tripsPage.getTotalPages()
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "trip/list";
    }

    @Override
    @GetMapping("/{id}")
    public String details(@PathVariable int id, Model model, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел детальную информацию о поезде с id: " + id);
        TripDTO trip = tripService.getTripById(id);
        var viewModel = new TripDetailsViewModel(
                createBaseViewModel("Детали рейса"),
                new TripViewModel(
                        trip.getId(),
                        trip.getDateArr(),
                        trip.getDateDep(),
                        trip.getStatusTrip(),
                        trip.getTrainId(),
                        trip.getRouteId(),
                        trip.getIsDelayed(),
                        trip.getDelayTime())
        );
        model.addAttribute("model", viewModel);
        return "trip/details";
    }

    @Override
    @GetMapping("/create")
    public String createForm(Model model) {
        var viewModel = new TripCreateViewModel(createBaseViewModel("Создание рейса"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new TripCreateForm(null, null, null,
                null, null, null, null));
        return "trip/create";
    }

    @Override
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("form") TripCreateForm form, BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            var viewModel = new TripCreateViewModel(createBaseViewModel("Создание рейса"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "trip/create";
        }
        LOG.log(Level.INFO, principal.getName() + " добавил новую поездку");
        var id = tripService.createTrip(
                form.dateArr(),
                form.dateDep(),
                form.statusTrip(),
                form.trainId(),
                form.routeId(),
                form.isDelayed(),
                form.delayTime()
        );

        return "redirect:/trips/" + id;
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        TripDTO trip = tripService.getTripById(id);
        var viewModel = new TripEditViewModel(createBaseViewModel("Редактирование рейса"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new TripEditForm(
                trip.getId(),
                trip.getDateArr(),
                trip.getDateDep(),
                trip.getStatusTrip(),
                trip.getTrainId(),
                trip.getRouteId(),
                trip.getIsDelayed(),
                trip.getDelayTime())
        );
        return "trip/edit";
    }

    @Override
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable int id, @Valid @ModelAttribute("form") TripEditForm form,
                       BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            var viewModel = new TripEditViewModel(createBaseViewModel("Редактирование рейса"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "trip/edit";
        }
        LOG.log(Level.INFO, principal.getName() + " обновил информацию о поездке с id: " + id);
        tripService.updateTrip(id, form.dateArr(), form.dateDep(), form.statusTrip(), form.trainId(),
                form.routeId(), form.isDelayed(), form.delayTime());

        return "redirect:/trips/" + id;
    }

    @Override
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " пометил поездку с id " + id + " как удаленная");
        tripService.deleteTrip(id);
        return "redirect:/trips/" + id;
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}