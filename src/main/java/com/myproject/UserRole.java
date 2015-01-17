package com.myproject;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.FetchType;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="user_roles", uniqueConstraints = @UniqueConstraint(columnNames = {"role","login"}))
public class UserRole implements Serializable{

	private static final long serialVersionUID = 1L;

	private int roleId;

	@JsonBackReference
	private User user;
	private String role;

	public UserRole(){}
	public UserRole(User user, String role){
		this.user = user;
		this.role = role;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "role_id", unique = true, nullable = false)
	public int getRoleId(){
		return this.roleId;
	}

	public void setRoleId(int roleId){
		this.roleId = roleId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "login", nullable = false)
	public User getUser(){
		return this.user;
	}

	public void setUser(User user){
		this.user = user;
	}

	@Column(name = "role", nullable = false)
	public String getRole(){
		return this.role;
	}

	public void setRole(String role){
		this.role = role;
	}

}