package com.avinash.hotfixviewer.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.avinash.hotfixviewer.Model.HotfixSummary;

public interface HotfixSummaryRepository extends MongoRepository<HotfixSummary, Long> {
	HotfixSummary findTopByOrderByDatabaseCreatedAtDesc();
}
