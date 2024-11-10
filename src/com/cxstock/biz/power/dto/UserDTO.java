package com.cxstock.biz.power.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.cxstock.pojo.Users;

public class UserDTO {

	private Integer userid;
	private String logincode;
	private String password;
	private String username;
	private Integer roleid;
	private String rolename;
	private Integer state;
	private String bz;
	private String usermenu;

	public UserDTO() {
		super();
	}

	public UserDTO(Integer userid, String logincode, String password,
			String username, Integer state, String bz) {
		super();
		this.userid = userid;
		this.logincode = logincode;
		this.password = password;
		this.username = username;
		this.state = state;
		this.bz = bz;
	}

	public UserDTO(Integer userid, String logincode, String password,
			String username, Integer roleid, String rolename, Integer state,
			String bz) {
		super();
		this.userid = userid;
		this.logincode = logincode;
		this.password = password;
		this.username = username;
		this.roleid = roleid;
		this.rolename = rolename;
		this.state = state;
		this.bz = bz;
	}

	public static UserDTO createDto(Users pojo) {
		UserDTO dto = null;
		if (pojo != null) {
			dto = new UserDTO(pojo.getUserid(), pojo.getLogincode(),
					pojo.getPassword(), pojo.getUsername(),
					pojo.getState(),pojo.getBz());
		}
		return dto;
	}

	@SuppressWarnings("unchecked")
	public static List createDtos(Collection pojos) {
		List<UserDTO> list = new ArrayList<UserDTO>();
		if (pojos != null) {
			Iterator iterator = pojos.iterator();
			while (iterator.hasNext()) {
				Users user = (Users)iterator.next();
				UserDTO dto = createDto(user);
				if(user.getRole()!=null){
					dto.setRoleid(user.getRole().getRoleid());
					dto.setRolename(user.getRole().getRolename());
				}
				list.add(dto);
			}
		}
		return list;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getLogincode() {
		return logincode;
	}

	public void setLogincode(String logincode) {
		this.logincode = logincode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getUsermenu() {
		return usermenu;
	}

	public void setUsermenu(String usermenu) {
		this.usermenu = usermenu;
	}

}
