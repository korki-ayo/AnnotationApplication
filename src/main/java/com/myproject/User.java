package com.myproject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="users")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;


	private int id;
	private String login;
	private String password;
	private String email;
	private boolean enabled;
	private Set<UserRole> userRole = new HashSet<UserRole>(0);
	//private Set<UserPersonal> userPersonal = new HashSet<UserPersonal>(0);

	public User(){
		this.login = "mystery";
		this.password = "password";
		this.email = "email";
		this.enabled = false;
	}
	public User(String login){
		this.login = login;
		this.password = "password";
		this.email = "email";
		this.enabled = false;
		
	}
	public User(String login, String password, String email, boolean enabled){
		this.login = login;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
	}
	public User(String login, String password, String email, boolean enabled, Set<UserRole> userRole){
		this.login = login;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		this.userRole = userRole;
	}
	// public User(String login, String password, String email, boolean enabled, Set<UserRole> userRole, Set<UserPersonal> userPersonal){
	// 	this.login = login;
	// 	this.password = password;
	// 	this.email = email;
	// 	this.enabled = enabled;
	// 	this.userRole = userRole;
	// 	this.userPersonal = userPersonal;
	// }

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="id", unique=true)
	public int getId(){
		return this.id;
	}

	public void setId(int id){
		this.id = id;
	}

	@Column(name="login", nullable=false, length = 50)
	public String getLogin(){
		return this.login;
	}

	public void setLogin(String login){
		this.login = login;
	}

	@Column(name="password", nullable = false, length = 60)
	public String getPassword(){
		return this.password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	@Column(name = "email", nullable = false, length = 100)
	public String getEmail(){
		return this.email;
	}

	public void setEmail(String email){
		this.email = email;
	}

	@Column(name = "enabled", nullable = false)
	public boolean isEnabled(){
		return this.enabled;
	}

	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<UserRole> getUserRole(){
		return this.userRole;
	}

	public void setUserRole(Set<UserRole> userRole){
		this.userRole = userRole;
	}
	
	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	// public Set<UserPersonal> getUserPersonal(){
	// 	return this.userPersonal;
	// }

	// public void setUserPersonal(Set<UserPersonal> userPersonal){
	// 	this.userPersonal = userPersonal;
	// }

}