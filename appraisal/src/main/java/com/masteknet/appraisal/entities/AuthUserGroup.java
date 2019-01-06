package com.masteknet.appraisal.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="AUTH_USER_GROUP")
public class AuthUserGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id()
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	@Column(name="AUTH_GROUP", nullable=false)
	private String authGroup;
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name = "USER_ID")
	private User user;

	public AuthUserGroup() {
	}
	
	public AuthUserGroup(String authGroup, User user) {
		this.authGroup = authGroup;
		this.user = user;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAuthGroup() {
		return authGroup;
	}
	public void setAuthGroup(String authGroup) {
		this.authGroup = authGroup;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
