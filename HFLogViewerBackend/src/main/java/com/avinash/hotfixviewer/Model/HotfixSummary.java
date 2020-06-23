package com.avinash.hotfixviewer.Model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("HotfixSummary")
public class HotfixSummary {
	
	private Date databaseCreatedAt;
	private long totalHotfixes;
	private long newlyAddedHotfixes;
	public Date getDatabaseCreatedAt() {
		return databaseCreatedAt;
	}
	public void setDatabaseCreatedAt(Date databaseCreatedAt) {
		this.databaseCreatedAt = databaseCreatedAt;
	}
	public long getTotalHotfixes() {
		return totalHotfixes;
	}
	public void setTotalHotfixes(long totalHotfixes) {
		this.totalHotfixes = totalHotfixes;
	}
	public long getNewlyAddedHotfixes() {
		return newlyAddedHotfixes;
	}
	public void setNewlyAddedHotfixes(long newlyAddedHotfixes) {
		this.newlyAddedHotfixes = newlyAddedHotfixes;
	}

	

	
}
