package RUT.vokzal.Service.Impl;

import RUT.vokzal.DTO.AppealDTO;
import RUT.vokzal.Model.entity.Appeal;
import RUT.vokzal.Model.entity.User;
import RUT.vokzal.Repository.AppealRepository;
import RUT.vokzal.Repository.UserRepository;
import RUT.vokzal.Service.AppealService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppealServiceImpl implements AppealService {
    private AppealRepository appealRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper)  {
        this.modelMapper = modelMapper;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setAppealRepository(AppealRepository appealRepository) {
        this.appealRepository = appealRepository;
    }

    @Override
    public Integer createAppeal(String header, String question, int feedback, int userId) {
        User user = userRepository.findById(userId);
        LocalDate dateStart = LocalDate.now();
        Appeal appeal = new Appeal(header, question, feedback, dateStart, user);
        appealRepository.create(appeal);
        return appeal.getId();
    }

    @Override
    public AppealDTO getAppealById(int id) {
        Appeal appeal = appealRepository.findById(id);
        AppealDTO appealDTO = modelMapper.map(appeal, AppealDTO.class);
        return new AppealDTO(
                appealDTO.getId(),
                appealDTO.getHeader(),
                appealDTO.getQuestion(),
                appealDTO.getFeedback(),
                appealDTO.getDateStart(),
                appealDTO.getUserId(),
                appealDTO.getAnswer());
    }

    @Override
    public void updateAppeal(Integer id, String answer) {
        Appeal appealI = appealRepository.findById(id);
        AppealDTO appeal = modelMapper.map(appealI, AppealDTO.class);
        appeal.setAnswer(answer);
        Appeal appealC = modelMapper.map(appeal, Appeal.class);
        appealRepository.update(appealC);
    }

    @Override
    public List<AppealDTO> getAllAppeals() {
        List<AppealDTO> result = new ArrayList<>();
        List<Appeal> appeals = appealRepository.findAll();
        for (Appeal appeal : appeals) {
            result.add(modelMapper.map(appeal, AppealDTO.class));
        }
        return result;
    }

    @Override
    public List<AppealDTO> getMyAppeals(int userId) {
        List<AppealDTO> result = new ArrayList<>();
        List<Appeal> appeals = appealRepository.findByUserId(userId);
        for (Appeal appeal : appeals) {
            result.add(modelMapper.map(appeal, AppealDTO.class));
        }
        return result;
    }
}