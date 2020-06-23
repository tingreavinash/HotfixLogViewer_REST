package com.avinash.hotfixviewer.Model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;


@Document("UserSearchHistory")
public class UserDetails {
	private Date date;
	private String requestPath;
	private String clientHost;
	private String backendHost;
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
	public String getClientHost() {
		return clientHost;
	}
	public void setClientHost(String clientHost) {
		this.clientHost = clientHost;
	}
	public String getBackendHost() {
		return backendHost;
	}
	public void setBackendHost(String backendHost) {
		this.backendHost = backendHost;
	}
	public List<String> getSearchInput() {
		return searchInput;
	}
	public void setSearchInput(List<String> searchInput) {
		this.searchInput = searchInput;
	}
	
	
}
