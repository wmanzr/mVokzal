package RUT.vokzal.Model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vokzal", schema = "public")
public class Vokzal extends BaseEntity {
  private String name;
  private String city;
  private int capacity;
  private boolean del;
  public Vokzal() {}
  public Vokzal(String name, String city, int capacity) {
		this.name = name;
		this.city = city;
		this.capacity = capacity;
  }
	@Column(name = "name", nullable = false, unique = true)
	public String getName() {
		return name;
	}
  	@Column(name = "city", nullable = false)
	public String getCity() {
		return city;
	}
  	@Column(name = "capacity", nullable = false)
	public int getCapacity() {
		return capacity;
	}
	public void setName(String name) {
		this.name = name;
	}
  	public void setCity(String city) {this.city = city;}
	public void setCapacity(int capacity) {this.capacity = capacity;}
	public boolean getDel() {return del;}
	public void setDel(boolean del) {this.del = del;}
}