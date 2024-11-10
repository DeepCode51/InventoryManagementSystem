package com.cxstock.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.cxstock.biz.power.dto.UserDTO;
import com.cxstock.utils.pubutil.Page;
import com.cxstock.utils.system.Constants;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BaseAction extends ActionSupport {

	private int limit; // 每页显示多少行
	private int start; // 开始行

	public void outJsonString(String str) {
		getResponse().setContentType("text/json;charset=UTF-8");
		outString(str);
	}

	public void outString(String str) {
		try {
			PrintWriter out = getResponse().getWriter();
			out.print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void outListString(List list) {
		try {
			JSONArray jsonArray = new JSONArray();
			if (list.size() > 0) {
				jsonArray = JSONArray.fromObject(list);
			}
			String jsonString = "{total:" + list.size() + ",root:"
					+ jsonArray.toString() + "}";
			outString(jsonString);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void outObjectString(Object obj) {
		JSONObject josnobj = new JSONObject();
		if (obj != null) {
			josnobj = JSONObject.fromObject(obj);
		}
		String jsonString = "{success:true,data:" + josnobj.toString() + "}";
		outString(jsonString);
	}

	public void outObjString(Object obj) {
		JSONArray jsonArray = new JSONArray();
		if (obj != null) {
			jsonArray = JSONArray.fromObject(obj);
		}
		String jsonString = "{success:true,data:" + jsonArray.toString() + "}";
		outString(jsonString);
	}

	public void outPageString(Page page) {

		JSONArray jsonArray = new JSONArray();
		if (page.getRoot().size() > 0) {
			jsonArray = JSONArray.fromObject(page.getRoot());
		}
		String jsonString = "{total:" + page.getTotal() + ",root:"
				+ jsonArray.toString() + "}";
		outString(jsonString);
	}
	
	@SuppressWarnings("unchecked")
	public void outTreeJsonList(List list){
		JSONArray jsonArray = new JSONArray();
		if (list.size() > 0) {
			jsonArray = JSONArray.fromObject(list);
		}
		outString(jsonArray.toString());
	}

	public void outXMLString(String xmlStr) {
		getResponse().setContentType("application/xml;charset=UTF-8");
		outString(xmlStr);
	}
	
	public void outError() {
		outString("{success:false,errors:'操作失败！'}");
	}

	/**
	 * 获得request
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 获得response
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 获得session
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 获得servlet上下文
	 * 
	 * @return
	 */
	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	public String getRealyPath(String path) {
		return getServletContext().getRealPath(path);
	}

	public UserDTO getUserDTO() {
		return (UserDTO) getSession().getAttribute(Constants.USERINFO);
	}

	// 获得uploadfile路径的实际目录
	public String getUpdateFilePath() {
		return getRealyPath("/").concat(
				getServletContext().getInitParameter(Constants.FILE_DIRECTORY));
	}
	
	
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

}
