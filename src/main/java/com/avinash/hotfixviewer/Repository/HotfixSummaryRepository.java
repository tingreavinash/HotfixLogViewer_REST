package com.avinash.hotfixviewer.Repository;

import com.avinash.hotfixviewer.Model.HotfixSummary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HotfixSummaryRepository extends MongoRepository<HotfixSummary, Long> {
    HotfixSummary findTopByOrderByDatabaseCreatedAtDesc();
}
