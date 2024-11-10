package com.cxstock.pojo;

/**
 * Rolemenu entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Rolemenu implements java.io.Serializable {

	// Fields

	private RolemenuId id;
	private Role role;
	private Menu menu;

	// Constructors

	/** default constructor */
	public Rolemenu() {
	}

	/** full constructor */
	public Rolemenu(RolemenuId id, Role role, Menu menu) {
		this.id = id;
		this.role = role;
		this.menu = menu;
	}

	// Property accessors

	public RolemenuId getId() {
		return this.id;
	}

	public void setId(RolemenuId id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	

}