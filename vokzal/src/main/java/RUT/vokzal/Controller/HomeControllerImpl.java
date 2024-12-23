package RUT.vokzal.Controller;

import RUT.vokzal.DTO.TripDTO;
import RUT.vokzal.Service.HomeService;
import controllers.home.HomeController;
import dto.base.BaseViewModel;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeControllerImpl implements HomeController {
    private HomeService homeService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    @Autowired
    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/")
    public String upcomingTripsAndLoad(@RequestParam(required = false) String vokzalName, Model model, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " посетил главную страницу");
        var viewModel = createBaseViewModel("Главная страница");
        model.addAttribute("model", viewModel);

        if (vokzalName != null && !vokzalName.isBlank()) {
            List<TripDTO> upcomingTrips = homeService.getUpcomingTripsWithTimeByStationName(vokzalName);
            model.addAttribute("upcomingTrips", upcomingTrips);

            int loadPercentage = homeService.getStationLoadByDayAndTime(vokzalName);
            model.addAttribute("stationLoad", loadPercentage);
        }

        model.addAttribute("vokzalName", vokzalName);
        return "home/home";
    }

    @Override
    @GetMapping("/canceled-trips")
    public String canceledTrips(String vokzalName, Model model, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел альтернативные поездки");
        var viewModel = createBaseViewModel("Отмененные поездки");
        model.addAttribute("model", viewModel);


        List<TripDTO> canceledTrips = homeService.getCanceledTrips (vokzalName);
        List<TripDTO> alternativeTrips = homeService.getAlternativeTripsIfCanceled(vokzalName);
        model.addAttribute("canceledTrips", canceledTrips);
        model.addAttribute("alternativeTrips", alternativeTrips);
        model.addAttribute("vokzalName", vokzalName);

        return "home/canceled-trips";
    }

    @Override
    @GetMapping("/403")
    public String not(Principal principal){
        LOG.log(Level.INFO, principal.getName() + " попытался посетить страницу, для которой у него нет доступа");
        return "/403";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(
                title
        );
    }
}