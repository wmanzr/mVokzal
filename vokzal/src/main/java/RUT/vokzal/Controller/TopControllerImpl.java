package RUT.vokzal.Controller;

import RUT.vokzal.DTO.TripDTO;
import RUT.vokzal.DTO.VokzalDTO;
import RUT.vokzal.Service.TopTripService;
import RUT.vokzal.Service.TopVokzalService;
import controllers.top.TopController;
import dto.base.BaseViewModel;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/top")
public class TopControllerImpl implements TopController {
    private TopVokzalService topVokzalService;
    private TopTripService topTripService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    @Autowired
    public void setTopVokzalService(TopVokzalService topVokzalService) {
        this.topVokzalService = topVokzalService;
    }
    @Autowired
    public void setTopTripService(TopTripService topTripService) {
        this.topTripService = topTripService;
    }

    @GetMapping("/trips")
    public String getTop5Trips(Model model, Principal principal) {
        var viewModel = createBaseViewModel("Топ 5 поездок");
        LOG.log(Level.INFO, principal.getName() + " посмотрел топ-5 поездок");
        model.addAttribute("model", viewModel);
        List<TripDTO> topTrips = topTripService.getTop5Trips();
        model.addAttribute("topTrips", topTrips);

        return "top/top-trips";
    }


    @GetMapping("/vokzals")
    public String getTop5Vokzals(Model model, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел топ-5 вокзалов");
        var viewModel = createBaseViewModel("Топ 5 вокзалов");
        model.addAttribute("model", viewModel);
        List<VokzalDTO> topVokzals = topVokzalService.getTop5VokzalsByDepartures();
        model.addAttribute("topVokzals", topVokzals);

        return "top/top-vokzals";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(
                title
        );
    }
}