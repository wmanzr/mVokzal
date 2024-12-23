package RUT.vokzal.Service;

import RUT.vokzal.DTO.AppealDTO;
import java.util.List;

public interface AppealService {
    Integer createAppeal(String header, String question, int feedback, int userId);
    AppealDTO getAppealById(int id);
    void updateAppeal(Integer id, String answer);
    List<AppealDTO> getAllAppeals();
    List<AppealDTO> getMyAppeals(int userId);
}