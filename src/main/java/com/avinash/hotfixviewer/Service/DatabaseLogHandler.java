package com.avinash.hotfixviewer.Service;

import com.avinash.hotfixviewer.Model.HotfixSummary;
import com.avinash.hotfixviewer.Model.UserDetails;
import com.avinash.hotfixviewer.Repository.HotfixSummaryRepository;
import com.avinash.hotfixviewer.Repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseLogHandler {

    @Autowired
    HotfixSummaryRepository dbhistoryRepo;
    @Autowired
    UserDetailsRepository userDetailsRepo;

    public UserDetails addUserDetails(UserDetails userDetails) {
        return userDetailsRepo.save(userDetails);
    }

    public List<UserDetails> getUserDetails(String host) {
        host = ".*" + host + ".*";
        return userDetailsRepo.findByHostname(host);
    }

    public HotfixSummary addSummary(HotfixSummary dbhistory) {
        return dbhistoryRepo.save(dbhistory);
    }

    public HotfixSummary getSummary() {
        return dbhistoryRepo.findTopByOrderByDatabaseCreatedAtDesc();
    }
}
