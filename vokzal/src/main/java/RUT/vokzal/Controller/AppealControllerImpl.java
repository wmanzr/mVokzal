package RUT.vokzal.Controller;

import RUT.vokzal.DTO.AppealDTO;
import RUT.vokzal.Model.entity.User;
import RUT.vokzal.Repository.UserRepository;
import RUT.vokzal.Service.AppealService;
import controllers.apeeal.AppealController;
import dto.appeal.*;
import dto.base.BaseViewModel;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/appeals")
public class AppealControllerImpl implements AppealController {
    private AppealService appealService;
    private UserRepository userRepository;
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    @Autowired
    public void setAppealService(AppealService appealService) {
        this.appealService = appealService;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin-list")
    public String listAppealsAdmin(Principal principal, Model model) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел все обращения");
        var viewModel = createBaseViewModel("Обращения");
        model.addAttribute("model", viewModel);

        List<AppealDTO> appeals = appealService.getAllAppeals();
        model.addAttribute("appeals", appeals);

        return "appeal/admin-list";
    }

    @GetMapping("/my-list")
    public String listAppealsUser(Principal principal, Model model) {
        LOG.log(Level.INFO, principal.getName() + " посмотрел свои обращения");
        var viewModel = createBaseViewModel("Мои обращения");
        model.addAttribute("model", viewModel);

        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        List<AppealDTO> appeals = appealService.getMyAppeals(user.getId());
        model.addAttribute("appeals", appeals);
        return "appeal/my-list";
    }

    @Override
    @GetMapping("/{id}/details")
    public String details(int id, Model model, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " детальную информацию обращения с id: " + id);
        AppealDTO appeal = appealService.getAppealById(id);
        var viewModel = new AppealDetailsViewModel(
                createBaseViewModel("Детали обращения"),
                new AppealViewModel(appeal.getId(), appeal.getHeader(), appeal.getQuestion(),
                        appeal.getFeedback(), appeal.getDateStart(),appeal.getUserId(), appeal.getAnswer())
        );
        model.addAttribute("model", viewModel);
        return "appeal/details";
    }

    @Override
    @GetMapping("/create")
    public String createForm(Principal principal, Model model) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        var viewModel = new AppealCreateViewModel(createBaseViewModel("Создание обращения"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new AppealCreateForm(null, null, null,  user.getId()));
        return "appeal/create";
    }

    @Override
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("form") AppealCreateForm form,
                         BindingResult bindingResult,
                         Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            var viewModel = new AppealCreateViewModel(createBaseViewModel("Создание обращения"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "appeal/create";
        }
        LOG.log(Level.INFO, principal.getName() + " написал обращение");
        var id = appealService.createAppeal(
                form.header(),
                form.question(),
                form.feedback(),
                form.userId());
        return "redirect:/appeals/" + id + "/details";
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editForm(int id, Model model) {
        AppealDTO appeal = appealService.getAppealById(id);
        var viewModel = new AppealEditViewModel(createBaseViewModel("Ответ на обращение"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new AppealEditForm(appeal.getId(),
                appeal.getHeader(),
                appeal.getQuestion(),
                appeal.getFeedback(),
                appeal.getDateStart(),
                appeal.getUserId(),
                appeal.getAnswer()));
        return "appeal/edit";
    }

    @Override
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable int id, @Valid @ModelAttribute("form") AppealEditForm form, BindingResult bindingResult, Model model, Principal principal) {
        LOG.log(Level.INFO, principal.getName() + " ответил на обращение с id: " + id);
        if (bindingResult.hasErrors()) {
            var viewModel = new AppealEditViewModel(createBaseViewModel("Ответ на обращение"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "appeal/edit";
        }

        appealService.updateAppeal(id, form.answer());
        return "redirect:/appeals/admin-list";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(
                title
        );
    }
}
