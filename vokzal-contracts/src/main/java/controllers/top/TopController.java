package controllers.top;

import controllers.base.BaseController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/top")
public interface TopController extends BaseController {

    @GetMapping("/trips")
    String getTop5Trips(Model model, Principal principal);

    @GetMapping("/vokzals")
    String getTop5Vokzals(Model model, Principal principal);
}