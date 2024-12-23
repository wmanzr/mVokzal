package RUT.vokzal.Model.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import RUT.vokzal.Model.enums.StatusTrip;
import jakarta.persistence.*;

@Entity
@Table(name = "trip", schema = "public")
public class Trip extends BaseEntity {
  private LocalDate dateArr;
  private LocalDate dateDep;
  private Train trainId;
  private Route routeId;
  private StatusTrip statusTrip;
  private boolean isDelayed;
  private LocalTime delayTime;

  public Trip() {}
  public Trip(LocalDate dateDep, LocalDate dateArr, StatusTrip statusTrip, Train trainId, Route routeId, boolean isDelayed, LocalTime delayTime) {
        this.dateDep = dateDep;
        this.dateArr = dateArr;
        this.statusTrip = statusTrip;
        this.trainId = trainId;
        this.routeId = routeId;
        this.isDelayed = isDelayed;
        this.delayTime = delayTime;
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "status_trip", nullable = false)
  public StatusTrip getStatusTrip() {
      return this.statusTrip;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trainId", referencedColumnName = "id")
  public Train getTrain() {
    return this.trainId;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "routeId", referencedColumnName = "id")
  public Route getRoute() {
    return this.routeId;
  }

  @Column(name = "date_arr", nullable = false)
	public LocalDate getDateArr() {
		return dateArr;
	}

  @Column(name = "date_dep", nullable = false)
	public LocalDate getDateDep() {
		return dateDep;
	}

  @Column(name = "is_delayed", nullable = false)
    public boolean isDelayed() {
        return isDelayed;
    }

  @Column(name = "delay_time", nullable = true)
  public LocalTime getDelayTime() {
      return delayTime;
    }

  public void setStatusTrip(StatusTrip statusTrip) {
    this.statusTrip = statusTrip;
    } 

  public void setTrain(Train train) {
		this.trainId = train;
	}

  public void setRoute(Route route) {
		this.routeId = route;
	}

  public void setDateArr(LocalDate dateArr) {
		this.dateArr = dateArr;
	}

  public void setDateDep(LocalDate dateDep) {
		this.dateDep = dateDep;
	}

  public void setDelayed(boolean isDelayed) {
    this.isDelayed = isDelayed;
  }

  public void setDelayTime(LocalTime delayTime) {
    this.delayTime = delayTime;
  }
}