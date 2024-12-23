package RUT.vokzal.Controller;

import RUT.vokzal.Model.entity.User;
import RUT.vokzal.Service.AuthService;
import controllers.user.AuthController;
import dto.UserProfileViewModel;
import dto.UserRegistrationCreateForm;
import dto.base.BaseViewModel;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class AuthControllerImpl implements AuthController {

    private AuthService authService;

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("form", new UserRegistrationCreateForm(null,  null, null, null, null, null));
        return "user/register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("form") UserRegistrationCreateForm form,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("form", form);

            return "user/register";
        }

        this.authService.register(
                form.username(),
                form.fullName(),
                form.email(),
                form.age(),
                form.password(),
                form.confirmPassword());

        return "redirect:/users/success";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/success")
    public String success(Principal principal)  {
        LOG.log(Level.INFO,  principal.getName() + " зарегистировался");
        return "user/success";
    };


    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел свой профиль");
        String username = principal.getName();
        User user = authService.getUser(username);

        UserProfileViewModel userProfile = new UserProfileViewModel(
                username,
                user.getEmail(),
                user.getFullName(),
                user.getAge()
        );

        model.addAttribute("user", userProfile);

        return "user/profile";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(
                title
        );
    }
}