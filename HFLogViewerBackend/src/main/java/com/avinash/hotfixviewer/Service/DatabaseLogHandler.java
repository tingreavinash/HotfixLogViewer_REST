package com.avinash.hotfixviewer.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avinash.hotfixviewer.Model.HotfixSummary;
import com.avinash.hotfixviewer.Model.ECPLog;
import com.avinash.hotfixviewer.Model.UserDetails;
import com.avinash.hotfixviewer.Repository.HotfixSummaryRepository;
import com.avinash.hotfixviewer.Repository.UserDetailsRepository;

@Component
public class DatabaseLogHandler {
	
	@Autowired
	HotfixSummaryRepository dbhistoryRepo;
	@Autowired
	UserDetailsRepository userDetailsRepo;
	
	public UserDetails addUserDetails(UserDetails userDetails) {
		return userDetailsRepo.save(userDetails);
	}
	
	public List<UserDetails> getUserDetails(String ntnet){
		ntnet = ".*"+ntnet+".*";
		return userDetailsRepo.findByNtnet(ntnet);
	}
	
	public HotfixSummary addSummary(HotfixSummary dbhistory) {
		return dbhistoryRepo.save(dbhistory);
	}
	
	public HotfixSummary getSummary() {
		return dbhistoryRepo.findTopByOrderByDatabaseCreatedAtDesc();
	}
}
