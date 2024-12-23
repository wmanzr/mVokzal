package RUT.vokzal.Exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AllExceptionHandler { //new GenericJackson2JsonRedisSerializer())

    @ExceptionHandler(TrainNotFoundException.class)
    public String trainNotFoundException(TrainNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "exception";
    }

    @ExceptionHandler(PlatformNotFoundException.class)
    public String platformNotFoundException(PlatformNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "exception";
    }

    @ExceptionHandler(RouteNotFoundException.class)
    public String routeNotFoundException(RouteNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "exception";
    }

    @ExceptionHandler(VokzalNotFoundException.class)
    public String vokzalNotFoundException(VokzalNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "exception";
    }

    @ExceptionHandler(VokzalNameNotFoundException.class)
    public String vokzalNameNotFoundException(VokzalNameNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "exception";
    }

    @ExceptionHandler(EmailIsPresentException.class)
    public String emailIsPresentException(EmailIsPresentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "exception";
    }

    @ExceptionHandler(PasswordIncorrectException.class)
    public String passwordIncorrectException(PasswordIncorrectException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "exception";
    }

    @ExceptionHandler(UserNameIsPresentException.class)
    public String userNameIsPresentException(UserNameIsPresentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "exception";
    }

    @ExceptionHandler(DeleteAlreadyException.class)
    public String deleteAlreadyException(DeleteAlreadyException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "exception";
    }
}