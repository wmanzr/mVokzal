package RUT.vokzal.Controller;

import RUT.vokzal.DTO.RouteDTO;
import RUT.vokzal.Service.RouteService;
import controllers.route.RouteController;
import dto.SearchForm;
import dto.base.BaseViewModel;
import dto.route.*;
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
@RequestMapping("/routes")
public class RouteControllerImpl implements RouteController {
    private RouteService routeService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    @Autowired
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping()
    public String listRoutes(@ModelAttribute("form") SearchForm form, Model model, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел все маршруты");
        var depPlId = form.searchTerm() != null && !form.searchTerm().isEmpty()
                ? Integer.parseInt(form.searchTerm())
                : null;
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 11;

        form = new SearchForm(form.searchTerm(), page, size);

        var routesPage = routeService.getRoutes(depPlId, page, size);
        var routeViewModels = routesPage.stream()
                .map(r -> new RouteViewModel(r.getId(), r.getTimeDep(), r.getTimeArr(),
                        r.getDepPlId(), r.getArrPlId(), r.getDel()))
                .toList();

        var viewModel = new RouteListViewModel(
                createBaseViewModel("Список маршрутов"),
                routeViewModels,
                routesPage.getTotalPages()
        );

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "route/list";
    }

    @Override
    @GetMapping("/{id}")
    public String details(@PathVariable int id, Model model, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел детальную информацию о маршруте с id: " + id);
        RouteDTO route = routeService.getRouteById(id);
        var viewModel = new RouteDetailsViewModel(
                createBaseViewModel("Детали маршрута"),
                new RouteViewModel(route.getId(), route.getTimeDep(), route.getTimeArr(),
                        route.getDepPlId(), route.getArrPlId(), route.getDel())
        );
        model.addAttribute("model", viewModel);
        return "route/details";
    }

    @Override
    @GetMapping("/create")
    public String createForm(Model model) {
        var viewModel = new RouteCreateViewModel(createBaseViewModel("Создание маршрута"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new RouteCreateForm(null, null, null, null));
        return "route/create";
    }

    @Override
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("form") RouteCreateForm form,
                         BindingResult bindingResult,
                         Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            var viewModel = new RouteCreateViewModel(createBaseViewModel("Создание маршрута"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "route/create";
        }
        LOG.log(Level.INFO, principal.getName() + " добавил новый маршрут");
        var id = routeService.createRoute(
                form.timeDep(),
                form.timeArr(),
                form.depPlId(),
                form.arrPlId());
        return "redirect:/routes/" + id;
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        RouteDTO route = routeService.getRouteById(id);
        var viewModel = new RouteEditViewModel(createBaseViewModel("Редактирование маршрута"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new RouteEditForm(route.getId(), route.getTimeDep(), route.getTimeArr(),
                route.getDepPlId(), route.getArrPlId()));
        return "route/edit";
    }

    @Override
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable int id, @Valid @ModelAttribute("form") RouteEditForm form,
                       BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            var viewModel = new RouteEditViewModel(createBaseViewModel("Редактирование маршрута"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "route/edit";
        }
        LOG.log(Level.INFO, principal.getName() + " обновил информацию о маршруте с id: " + id);
        routeService.updateRoute(id, form.timeDep(), form.timeArr(), form.depPlId(), form.arrPlId());
        return "redirect:/routes/" + id;
    }

    @Override
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " пометил маршрут с id " + id + " как удаленный");
        routeService.deleteRoute(id);
        return "redirect:/routes/" + id;
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}