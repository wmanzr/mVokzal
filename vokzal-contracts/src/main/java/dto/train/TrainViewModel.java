package dto.train;

public record TrainViewModel(
        Integer id,
        Integer number,
        String type,
        String model,
        Integer capacity,
        String statusTrain,
        Integer maxSpeed
) {}