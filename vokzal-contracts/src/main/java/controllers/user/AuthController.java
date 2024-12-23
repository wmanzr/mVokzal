package controllers.user;

import controllers.base.BaseController;
import dto.UserRegistrationCreateForm;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

public interface AuthController extends BaseController {

    @GetMapping("/register")
    String register(Model model);

    @PostMapping("/register")
    String doRegister(@Valid
                      UserRegistrationCreateForm form,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes);

    @GetMapping("/login")
    String login();

    @GetMapping("/success")
    String success(Principal principal);

    @PostMapping("/login-error")
    String onFailedLogin(String username,
            RedirectAttributes redirectAttributes);

    @GetMapping("/profile")
    String profile(Principal principal, Model model);
}
