package RUT.vokzal.Service.Impl;

import RUT.vokzal.DTO.TrainDTO;
import RUT.vokzal.Exception.DeleteAlreadyException;
import RUT.vokzal.Model.enums.StatusTrain;
import RUT.vokzal.Model.entity.Train;
import RUT.vokzal.Repository.TrainRepository;
import RUT.vokzal.Service.TrainService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {

    private TrainRepository trainRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setTrainRepository(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Integer createTrain(Integer number, String type, String model, Integer capacity, String statusTrain, Integer maxSpeed) {
        StatusTrain status = StatusTrain.valueOf(statusTrain.toUpperCase());
        Train train = new Train(number, type, model, capacity, status, maxSpeed);
        trainRepository.create(train);
        return train.getId();
    }

    @Override
    public TrainDTO getTrainById(Integer id) {
        Train train = trainRepository.findById(id);
        return modelMapper.map(train, TrainDTO.class);
    }

    @Override
    public Page<TrainDTO> getTrains(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Train> trainsPage = searchTerm.isEmpty()
                ? trainRepository.findAll(pageable)
                : trainRepository.findByNumberContainingIgnoreCase(searchTerm, pageable);

        return trainsPage.map(train -> modelMapper.map(train, TrainDTO.class));
    }

    @Override
    public void updateTrain(Integer id, Integer number, String type, String model, Integer capacity, String statusTrain, Integer maxSpeed) {
        Train trainI = trainRepository.findById(id);
        TrainDTO train = modelMapper.map(trainI, TrainDTO.class);
        train.setNumber(number);
        train.setType(type);
        train.setModel(model);
        train.setCapacity(capacity);
        train.setMaxSpeed(maxSpeed);
        train.setStatusTrain(statusTrain);
        Train trainC = modelMapper.map(train, Train.class);
        trainRepository.update(trainC);
    }

    @Override
    public List<TrainDTO> getAllTrains() {
        List<TrainDTO> result = new ArrayList<>();
        List<Train> trains = trainRepository.findAll();
        for (Train train : trains) {
            result.add(modelMapper.map(train, TrainDTO.class));
        }
        return result;
    }

    @Override
    public void deleteTrain(Integer id) {
        Train train = trainRepository.findById(id);
        if (train.getStatusTrain() != StatusTrain.NOT_USED) {
            train.setStatusTrain(StatusTrain.NOT_USED);
            trainRepository.update(train);
        } else throw new DeleteAlreadyException(id);
    }
}