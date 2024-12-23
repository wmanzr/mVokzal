package RUT.vokzal.Controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    @GetMapping
    public String adminPanel(Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " открыл администраторскую панель");
        return "admin-panel";
    }
}