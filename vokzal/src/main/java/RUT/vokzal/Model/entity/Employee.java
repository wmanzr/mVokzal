package RUT.vokzal.Model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employee", schema = "public")
public class Employee extends BaseEntity {
  private String post;
  private String login;
  private int experience;
  private Train trainId;
  private boolean del;

  public Employee() {
  }

	public Employee(String login, String post, int experience, Train trainId) {
		this.post = post;
		this.login = login;
		this.experience = experience;
		this.trainId = trainId;
	}

  @ManyToOne(fetch = FetchType.LAZY,  optional = true)
  @JoinColumn(name = "trainId", referencedColumnName = "id")
  public Train getTrainId() {
    return this.trainId;
  }

  @Column(name = "post", nullable = false)
	public String getPost() {
		return post;
	}

  @Column(name = "login", nullable = false)
	public String getLogin() {
		return login;
	}

  @Column(name = "experience", nullable = false)
	public int getExperience() {
		return experience;
	}

  public void setTrainId(Train train) {
		this.trainId = train;
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

  public boolean getDel() {
		return del;
	}

  public void setDel(boolean del) {
		this.del = del;
	}
}