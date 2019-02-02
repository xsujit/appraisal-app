package com.masteknet.appraisal.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name="USER")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	@Column(name="PASSWORD", nullable=false)
	private String password;
	@NaturalId
	@Column(name="EMAIL", nullable=false, unique=true)
	private String email;
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name="PROJECT_ID")
	private Project project;
	@Column(name="ENABLED", nullable=false)
	private boolean enabled = false;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AuthUserGroup> usergroups = new ArrayList<>();
	
	public User() {
		super();
	}

	public User(String password, String email, Project project) {
		this.password = password;
		this.email = email;
		this.project = project;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<AuthUserGroup> getUsergroups() {
		return usergroups;
	}

	public void setUsergroups(List<AuthUserGroup> usergroups) {
		this.usergroups = usergroups;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getEmail(), user.getEmail());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}
