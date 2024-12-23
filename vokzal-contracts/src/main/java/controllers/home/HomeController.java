package controllers.home;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import controllers.base.BaseController;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequestMapping("/")
public interface HomeController extends BaseController {

    @GetMapping("/")
    String upcomingTripsAndLoad(@RequestParam String vokzalName, Model model, Principal principal);

    @GetMapping("/canceled-trips")
    String canceledTrips(@RequestParam String vokzalName, Model model, Principal principal);

    @GetMapping("/403")
    String not(Principal principal);
}