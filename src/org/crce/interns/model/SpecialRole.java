package org.crce.interns.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name = "ftpc_special_role",catalog="role_function_schema")
public class SpecialRole implements Serializable{
	@Id
	@Column(name = "username" )
	String userName;
	@Id
	@Column(name = "special_role" )
	String specialRole;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSpecialRole() {
		return specialRole;
	}
	public void setSpecialRole(String specialRole) {
		this.specialRole = specialRole;
	}
	
}
