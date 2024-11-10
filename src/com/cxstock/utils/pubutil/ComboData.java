package com.cxstock.utils.pubutil;

public class ComboData {
	
	private String value;
	private String text;
	
	public ComboData() {
		super();
	}
	
	public ComboData(String value, String text) {
		super();
		this.value = value;
		this.text = text;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	

}
