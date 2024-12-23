package RUT.vokzal.DTO;

import java.io.Serializable;

public class EmployeeDTO implements Serializable {
    private int id;
    private String post;
    private String login;
    private int experience;
    private int trainId;
    private boolean del;
    public EmployeeDTO() {
    }
    public EmployeeDTO(int id, String login, String post, int experience, int trainId, boolean del) {
        this.id = id;
        this.login = login;
        this.post = post;
        this.experience = experience;
        this.trainId = trainId;
        this.del = del;
    }

    public int getId() {
        return id;
    }

    public String getPost() {
        return post;
    }

    public String getLogin() {return login;}
    public int getExperience() {
        return experience;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public boolean getDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }
}