package RUT.vokzal.Model.entity;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
	private int id;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(insertable = false, name = "id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}