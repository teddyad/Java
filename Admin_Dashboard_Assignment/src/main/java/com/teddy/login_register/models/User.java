package com.teddy.login_register.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue
	private Long id;
	
	@Size(min=1)
	private String firstName;
	
	@Size(min=1)
	private String lastName;
	
	@Email
	@Size(min=10)
	private String email;
	
	@Size(min=6) 
	private String password;
	
	@Transient
	private String confirmPassword;
	
	@DateTimeFormat(pattern="MM/dd/yyyy HH:mm:ss")
	private Date lastSignIn;
	
	@DateTimeFormat(pattern="MM/dd/yyyy HH:mm:ss")
	private Date createdAt;
	
	@DateTimeFormat(pattern="MM/dd/yyyy HH:mm:ss")
	private Date updatedAt;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	        name = "users_roles", 
	        joinColumns = @JoinColumn(name = "user_id"), 
	        inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;
	
	public User() {}

	public User(Long id, String firstName, String lastName, String email, String password, String confirmPassword,
			Date lastSignIn, Date createdAt, Date updatedAt, List<Role> roles) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.lastSignIn = lastSignIn;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Date getLastSignIn() {
		return lastSignIn;
	}

	public void setLastSignIn(Date lastSignIn) {
		this.lastSignIn = lastSignIn;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
	
	
// validate if a user has an admin or super-admin privilege 
	
	public boolean isUser(){
		for(Role role:this.roles){			
			if(role.getName().equals("ROLE_USER")){
				return true;
			}
		}
		return false;
	}
	
	public boolean isAdmin(){
		for(Role role:this.roles){			
			if(role.getName().equals("ROLE_ADMIN")){
				return true;
			}
		}
		return false;
	}
	public boolean isSuperAdmin(){
		for(Role role:this.roles){			
			if(role.getName().equals("ROLE_SUPERADMIN")){
				return true;
			}
		}
		return false;
	}
}
