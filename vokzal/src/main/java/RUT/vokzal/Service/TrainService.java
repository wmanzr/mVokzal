package RUT.vokzal.Service;

import RUT.vokzal.DTO.TrainDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TrainService {
    Integer createTrain(Integer number, String type, String model, Integer capacity, String statusTrain, Integer maxSpeed);
    TrainDTO getTrainById(Integer id);
    void updateTrain(Integer id, Integer number, String type, String model, Integer capacity, String statusTrain, Integer maxSpeed);
    List<TrainDTO> getAllTrains();
    Page<TrainDTO> getTrains(String searchTerm, int page, int size);
    void deleteTrain(Integer id);
}