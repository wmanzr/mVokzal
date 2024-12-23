package RUT.vokzal.DTO;

import java.io.Serializable;

public class VokzalDTO  implements Serializable {
    private int id;
    private String name;
    private String city;
    private int capacity;
    private int departuresCount;
    private boolean del;
    public VokzalDTO() {}
    public VokzalDTO(int id, String name, String city, int capacity, boolean del) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.capacity = capacity;
        this.del = del;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCity() {
        return city;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public int getDeparturesCount() {
        return departuresCount;
    }
    public void setDeparturesCount(int departuresCount) {
        this.departuresCount = departuresCount;
    }
    public boolean getDel() {
        return del;
    }
    public void setDel(boolean del) {
        this.del = del;
    }
}