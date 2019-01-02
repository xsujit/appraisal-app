package com.masteknet.appraisals.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name="EMPLOYEE")
@NamedEntityGraph(name="graph.Employee.user",
					attributeNodes = @NamedAttributeNode(value = "user", subgraph = "user"), 
					subgraphs = @NamedSubgraph(name = "user", attributeNodes = @NamedAttributeNode("project")))
public class Employee implements Serializable {
	
	/**
	 * Reason for implementing Serializable - If you want to make a relation between Hibernate objects, just use the primary key as the link column. 
	 * Otherwise, make the linked object Serializable and that'll work as well
	 */
	private static final long serialVersionUID = 1L;

	@NaturalId
	@Column(name="ID", nullable=false, unique=true)
	private long id;
	@Id
	@Column(name="USER_ID")
	private long userId;
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId()
	private User user;
	@Column(name="FIRST_NAME", nullable=false)
	private String firstName;
	@Column(name="LAST_NAME", nullable=false)
	private String lastName;
	@Column(name="LOCATION", nullable=false)
	private String location;
	
	public Employee() {
		super();
	}

	public Employee(long id, User user, String firstName, String lastName, String location) {
		super();
		this.id = id;
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.location = location;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(getId(), employee.getId());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

	@Override
	public String toString() {
		return  firstName + ' ' + lastName;
	}
}
