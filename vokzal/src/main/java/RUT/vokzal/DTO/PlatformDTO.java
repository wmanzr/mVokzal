package RUT.vokzal.DTO;

import java.io.Serializable;

public class PlatformDTO  implements Serializable {
    private int id;
    private int vokzalId;
    private int number;
    private String type;
    private String statusPlatform;

    public PlatformDTO() {
    }

    public PlatformDTO(int id, int number, String type, String statusPlatform, int vokzalId) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.statusPlatform = statusPlatform;
        this.vokzalId = vokzalId;
    }

    public int getId() {
        return id;
    }

    public int getVokzalId() {
        return vokzalId;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getStatusPlatform() {
        return statusPlatform;
    }

    public void setId(int id) {this.id = id;}

    public void setVokzalId(int vokzalId) {
        this.vokzalId = vokzalId;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatusPlatform(String statusPlatform) {
        this.statusPlatform = statusPlatform;
    }
}