package RUT.vokzal.DTO;

import java.io.Serializable;

public class TripDTO implements Serializable {
    private int id;
    private String dateArr;
    private String timeDep;
    private String dateDep;
    private String statusTrip;
    private int trainId;
    private int routeId;
    private boolean isDelayed;
    private String delayTime;
    private String cityFrom;
    private String cityTo;
    private int numTrain;

    private int maxSpeedTrain;

    private String timeInTrip;

    public TripDTO() {
    }
    public TripDTO(int id, String dateDep, String dateArr, String statusTrip, int trainId, int routeId, boolean isDelayed, String delayTime) {
        this.id = id;
        this.dateDep = dateDep;
        this.dateArr = dateArr;
        this.statusTrip = statusTrip;
        this.trainId = trainId;
        this.routeId = routeId;
        this.isDelayed = isDelayed;
        this.delayTime = delayTime;
    }
    public int getNumTrain() {
        return numTrain;
    }
    public void setNumTrain(int numTrain) {
        this.numTrain = numTrain;
    }

    public String getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public String getCityTo() {
        return cityTo;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public int getId() {
        return id;
    }

    public String getDateArr() {
        return dateArr;
    }

    public String getDateDep() {return dateDep;}

    public String getTimeDep() {return timeDep;}

    public String getStatusTrip() {
        return statusTrip;
    }

    public int getTrainId() {
        return trainId;
    }

    public int getRouteId() {
        return routeId;
    }

    public boolean getIsDelayed() {
        return isDelayed;
    }

    public String getDelayTime() {
        return delayTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateArr(String dateArr) {
        this.dateArr = dateArr;
    }

    public void setDateDep(String dateDep) {
        this.dateDep = dateDep;
    }

    public void setTimeDep(String timeDep) {
        this.timeDep = timeDep;
    }

    public void setStatusTrip(String statusTrip) {
        this.statusTrip = statusTrip;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public void setDelayed(boolean isDelayed) {
        this.isDelayed = isDelayed;
    }

    public void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }

    public int getMaxSpeedTrain() {
        return maxSpeedTrain;
    }

    public void setMaxSpeedTrain(int maxSpeedTrain) {
        this.maxSpeedTrain = maxSpeedTrain;
    }

    public String getTimeInTrip() {
        return timeInTrip;
    }

    public void setTimeInTrip(String timeInTrip) {
        this.timeInTrip = timeInTrip;
    }
}