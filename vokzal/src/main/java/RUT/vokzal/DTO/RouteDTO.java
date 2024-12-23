package RUT.vokzal.DTO;

import java.io.Serializable;

public class RouteDTO implements Serializable {
    private int id;
    private String timeDep;
    private String timeArr;
    private int depPlId;
    private int arrPlId;
    private boolean del;

    public RouteDTO() {
    }
    public RouteDTO(int id, String timeDep, String timeArr, int depPlId, int arrPlId, boolean del) {
        this.id = id;
        this.timeDep = timeDep;
        this.timeArr = timeArr;
        this.depPlId = depPlId;
        this.arrPlId = arrPlId;
        this.del = del;
    }

    public int getId() {
        return id;
    }

    public String getTimeDep() {
        return timeDep;
    }

    public String getTimeArr() {
        return timeArr;
    }

    public int getDepPlId() {
        return depPlId;
    }

    public int getArrPlId() {
        return arrPlId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimeDep(String timeDep) {
        this.timeDep = timeDep;
    }

    public void setTimeArr(String timeArr) {
        this.timeArr = timeArr;
    }

    public void setDepPlId(int depPlId) {
        this.depPlId = depPlId;
    }

    public void setArrPlId(int arrPlId) {
        this.arrPlId = arrPlId;
    }
    public boolean getDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }
}