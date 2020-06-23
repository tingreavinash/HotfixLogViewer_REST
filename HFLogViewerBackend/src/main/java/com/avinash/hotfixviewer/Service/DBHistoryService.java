package com.avinash.hotfixviewer.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avinash.hotfixviewer.Model.DBHistory;
import com.avinash.hotfixviewer.Model.ECPLog;
import com.avinash.hotfixviewer.Repository.DBHistoryRepository;

@Component
public class DBHistoryService {
	private static final Logger LOG = LoggerFactory.getLogger(DBHistoryService.class);
	
	@Autowired
	DBHistoryRepository dbhistoryRepo;
	
	public DBHistory addDBHistory(DBHistory dbhistory) {
		return dbhistoryRepo.save(dbhistory);
	}
	
	public DBHistory getLatestSummary() {
		return dbhistoryRepo.findTopByOrderByDatabaseCreatedAtDesc();
	}
}
