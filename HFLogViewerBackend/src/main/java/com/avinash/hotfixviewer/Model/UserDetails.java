package com.avinash.hotfixviewer.Model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;


@Document("UserDetails")
public class UserDetails {
	private Date date;
	private String requestPath;
	private String ntnet;
	private String hostname;
	private String hostaddress;
	
	private List<String> searchInput;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getRequestPath() {
		return requestPath;
	}
	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	public String getNtnet() {
		return ntnet;
	}
	public void setNtnet(String ntnet) {
		this.ntnet = ntnet;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getHostaddress() {
		return hostaddress;
	}
	public void setHostaddress(String hostaddress) {
		this.hostaddress = hostaddress;
	}
	public List<String> getSearchInput() {
		return searchInput;
	}
	public void setSearchInput(List<String> searchInput) {
		this.searchInput = searchInput;
	}
	
	
	
	
}
