package com.chamika.twittersms;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SMS implements Serializable {
	
	private String body;
	private String dateString;
	private Date date;
	
	
	
	public SMS(String body, Date date) {
		super();
		this.body = body;
		setDate(date);
	}
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getDateString() {
		return dateString;
	}
	
	private void setDateString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		this.dateString = formatter.format(date);
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
		setDateString(date);
	}
	
	
}
